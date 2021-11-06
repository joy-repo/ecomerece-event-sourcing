package com.myapp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingDomain {
	
	private String address;
	private String shippingType;
	private long cartID;

	private String cardType;

}
