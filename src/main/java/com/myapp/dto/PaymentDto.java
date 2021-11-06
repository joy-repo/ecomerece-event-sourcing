package com.myapp.dto;

import lombok.Data;

@Data
public class PaymentDto {
	
private String paymentType;
	
	private long cartID;
	
	private String cardType;

}
