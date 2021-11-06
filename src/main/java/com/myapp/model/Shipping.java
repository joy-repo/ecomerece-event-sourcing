package com.myapp.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@NodeEntity(label = "Shipping")

public class Shipping {

	@Id
	@GeneratedValue
	private Long id;

	private String uuid;
	private String address;
	private String shippingType;
	
	private long cartID;

	private String cardType;

}
