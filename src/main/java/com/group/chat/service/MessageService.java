package com.group.chat.service;

import com.group.chat.entity.Message;

import java.util.List;

public interface MessageService {
	List<Message> findByFromIsAndToIs(Long from, Long to);
	List<Message> findByFromIsAndToIsAndMidGreaterThanEqual(Long from, Long to, Long mid);
	void save(Message msg);
}
