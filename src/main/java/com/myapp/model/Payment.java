package com.myapp.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@NodeEntity(label = "Payment")
public class Payment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String paymentType;
	
	private long cartID;
	
	private String cardType;
	
	private String uuid;
	
	
	

}
