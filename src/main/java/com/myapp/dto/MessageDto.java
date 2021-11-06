package com.myapp.dto;

import java.util.UUID;

import com.myapp.model.MessageI;

import lombok.Data;

@Data
public class MessageDto {

	private String messageType;
	private MessageI message;
	private String uuid;

	public MessageDto() {

		uuid = UUID.randomUUID().toString();
	}

}
