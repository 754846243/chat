package com.group.chat.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Document
public class Message implements Serializable {
	public enum MessageType {text, image, request};

	public Message() {
		this.setTime(new Date());
	}

	Long mid;

	Long from;
	Long to;
	MessageType type;
	String data;
	Date time;
}
