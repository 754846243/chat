package com.group.chat.controller;

import com.group.chat.entity.Message;
import com.group.chat.service.MessageService;
import com.group.chat.service.MongoSequenceService;
import com.group.chat.utils.RedisUtil;
import com.group.chat.utils.ResultVOUtil;
import com.group.chat.vo.ResultVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

@RestController
public class MessageController {
	@Resource
	private MessageService messageService;

	@Resource
	private MongoSequenceService mongoSequenceService;

	@Resource
	private SimpMessagingTemplate template;

	@Resource
	private RedisUtil redis;

	// 未读消息字段
	private final String unreadKey = "unread";

	// 上线情况字段
	private final String onlineStatusKey = "online";

	/**
	 * 消息处理（客户端->服务器）
	 * 一边存到redis，一边持久化
	 * @param principal Spring Security传递的username
	 * @param message 项目定义的消息体
	 */
	@MessageMapping("/chat")
	public void chat(Principal principal, Message message) {
		message.setTime(new Date());
		message.setMid(mongoSequenceService.getNextSequence("messageRecord"));
		messageService.save(message);

		Map<String, List<Message>> unreadMsgs = null;

		try {
			unreadMsgs = (Map<String, List<Message>>) redis.hget(unreadKey, message.getTo().toString());
		} catch (NullPointerException e) {
			unreadMsgs = new HashMap<>();
		}

		if(unreadMsgs.get(message.getFrom()) == null) {
			List<Message> msgs = new ArrayList<>();
			msgs.add(message);
			unreadMsgs.put(message.getFrom().toString(), msgs);
		} else {
			unreadMsgs.get(message.getFrom()).add(message);
		}
		redis.hset(unreadKey, message.getTo().toString(), unreadMsgs);

		template.convertAndSendToUser(message.getTo().toString(), "/user/queue/msg", message);
	}

	/**
	 * 上线通知（客户端->服务器）
	 * @param principal Spring Security传递的username
	 * @param message 项目定义的消息体
	 * @return 广播的上线通知，处理与否交给前端
	 */
	@MessageMapping("/connect")
	@SendTo("/topic/online_status")
	public Map<String, Object> connect(Principal principal, Message message) {
		Map<String, Object> info = new HashMap<>();

		long status = redis.sSet(onlineStatusKey, message.getFrom());
		if(status > 0) {
			Map<String, List<Message>> msgs = new HashMap<>();
			try {
				msgs = (Map<String, List<Message>>) redis.hget(unreadKey, message.getTo().toString());
			} catch (NullPointerException e) {
				// do nothing
			} finally {
				template.convertAndSendToUser(message.getFrom().toString(), "/user/queue/unread", msgs);
			}
		}

		info.put("from", message.getFrom().toString());
		info.put("data", "online");
		return info;
	}

	/**
	 * 下线通知（客户端->服务器）
	 * @param principal Spring Security传递的username
	 * @param message 项目定义的消息体
	 * @return 广播的上线通知，处理与否交给前端
	 */
	@MessageMapping("/disconnect")
	@SendTo("/topic/online_status")
	public Map<String, Object> disconnect(Principal principal, Message message) {
		Map<String, Object> info = new HashMap<>();

		long status = redis.setRemove(onlineStatusKey, message.getFrom());

		info.put("from", message.getFrom().toString());
		info.put("data", "offline");
		return info;
	}

	/**
	 * 清除未读信息
	 * @param principal Spring Security传递的username
	 * @param message 清除其中from->to方向的未读消息
	 */
	@PostMapping("/flush")
	public ResultVO flush(Principal principal, Message message) {
		List<Message> body = new ArrayList<>();

		try {
			Map<String, List<Message>> msgs = (Map<String, List<Message>>) redis.hget(unreadKey, message.getTo().toString());
			msgs.remove(message.getFrom().toString());
			redis.hset(unreadKey, message.getTo().toString(), msgs);
		} catch (NullPointerException e) {
			// do nothing
		}

		return ResultVOUtil.success(body);
	}

	@PostMapping("/unread")
	public ResultVO unread(Principal principal, Message message) {
		List<Message> msgs = new ArrayList<>();

		Long uid = message.getFrom();
		try{
			((Map<String, List<Message>>)redis.hget(unreadKey, message.getTo().toString())).forEach(
				(key, val) -> {
					val.forEach(msgs::add);
				}
			);
		} catch (NullPointerException e) {
			// do nothing
		}

		msgs.sort((a, b)->{
			return (int)(b.getTime().getTime() - a.getTime().getTime());
		});

		return ResultVOUtil.success(msgs);
	}
}
