package com.group.chat.service;

public interface MongoSequenceService {
	Long getNextSequence(String seqName);
}
