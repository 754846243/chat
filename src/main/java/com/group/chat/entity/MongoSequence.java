package com.group.chat.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document
public class MongoSequence {
	@Id
	private String id;
	private Long seq;
}
