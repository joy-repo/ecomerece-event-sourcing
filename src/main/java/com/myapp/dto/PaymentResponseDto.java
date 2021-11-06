package com.myapp.dto;

public class PaymentResponseDto {
	

	private String responseString;
	private String requestID;
	public PaymentResponseDto(String responseString, String requestID) {
		super();
		this.responseString = responseString;
		this.requestID = requestID;
	}
}
