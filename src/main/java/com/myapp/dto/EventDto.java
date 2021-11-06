package com.myapp.dto;

import lombok.Data;

@Data
public class EventDto<T> {
	
	private String eventName;
	
	private T eventData;

}
