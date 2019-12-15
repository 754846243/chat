package com.group.chat.controller;

import com.group.chat.entity.Message;
import com.group.chat.service.MessageService;
import com.group.chat.utils.RedisUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

@RestController
public class WebSocketController {
	@Resource
	private MessageService messageService;

	@Resource
	private SimpMessagingTemplate template;

	@Resource
	private RedisUtil redis;

	// 未读消息字段
	private final String unreadKey = "unread";

	// 上线情况字段
	private final String onlineStatusKey = "online";

	@MessageMapping("/chat")
	public void chat(Principal principal, Message message) {
		message.setTime(new Date());
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

	@MessageMapping("/connect")
	@SendTo("/topic/online_status")
	public  connect(Principal principal, Message message) {
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
	}

	@MessageMapping("/flush")
	public void flush(Principal principal, Message message) {
		try {
			Map<String, List<Message>> msgs = (Map<String, List<Message>>) redis.hget(unreadKey, message.getTo().toString());
			msgs.remove(message.getFrom());
			redis.hset(unreadKey, message.getTo().toString(), msgs);
		} catch (NullPointerException e) {
			// do nothing
		}
	}

	@MessageMapping("/disconnect")
	public void disconnect(Principal principal, Message message) {
		long status = redis.setRemove(onlineStatusKey, message.getFrom());
	}
}
