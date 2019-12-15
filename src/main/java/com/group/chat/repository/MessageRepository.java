package com.group.chat.repository;

import com.group.chat.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {
	List<Message> findByFromIsAndToIs(Long from, Long to);
	List<Message> findByFromIsAndToIsAndMidGreaterThanEqual(Long from, Long to, Long mid);
}
