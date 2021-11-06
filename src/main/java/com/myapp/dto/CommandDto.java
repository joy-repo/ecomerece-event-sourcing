package com.myapp.dto;

import lombok.Data;

@Data
public class CommandDto<T> {
	
	private T t;
	private String command;
	
	public CommandDto(String command, T t) {
		this.command=command;
		this.t=t;
	}

}
