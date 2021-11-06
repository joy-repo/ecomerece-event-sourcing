package com.myapp.dto;

import lombok.Data;

@Data
public class ShippingResponseDto {
	
	private String responseString;
	private String requestID;
	public ShippingResponseDto(String responseString, String requestID) {
		super();
		this.responseString = responseString;
		this.requestID = requestID;
	}
	
	

}
