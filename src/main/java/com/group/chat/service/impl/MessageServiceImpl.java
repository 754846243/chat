package com.group.chat.service.impl;

import com.group.chat.entity.Message;
import com.group.chat.repository.MessageRepository;
import com.group.chat.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	@Resource
	MessageRepository messageRepository;

	@Override
	public List<Message> findByFromIsAndToIs(Long from, Long to) {
		return messageRepository.findByFromIsAndToIs(from ,to);
	}

	@Override
	public List<Message> findByFromIsAndToIsAndMidGreaterThanEqual(Long from, Long to, Long mid) {
		return messageRepository.findByFromIsAndToIsAndMidGreaterThanEqual(from, to, mid);
	}

	@Override
	public void save(Message msg) {
		messageRepository.save(msg);
	}
}
