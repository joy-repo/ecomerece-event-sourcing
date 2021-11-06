package com.myapp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDomain {

	private String paymentType;

	private long cartID;

	private String cardType;

}
