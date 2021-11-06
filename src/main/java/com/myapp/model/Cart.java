package com.myapp.model;

import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@NodeEntity(label = "Cart")
public class Cart {

	@Id
	@GeneratedValue
	private Long id;

	private List<Product> productList;
	
	private String triggeringCommand;
	
	private String uuid;
	
//	public Cart() {}
//	
//	public Cart(List<Product> productList) {
//		
//		this.productList=productList;
//	}

}
