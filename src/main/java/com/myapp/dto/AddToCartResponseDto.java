package com.myapp.dto;

import lombok.Data;

@Data
public class AddToCartResponseDto {
	
	private String responseString;
	private String requestID;
	public AddToCartResponseDto(String responseString, String requestID) {
		super();
		this.responseString = responseString;
		this.requestID = requestID;
	}
	
	

}
