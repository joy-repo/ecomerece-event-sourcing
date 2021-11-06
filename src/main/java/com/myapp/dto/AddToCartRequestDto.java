package com.myapp.dto;

import java.util.List;

import com.myapp.model.Product;

import lombok.Data;

@Data
public class AddToCartRequestDto {
	
	private List<Product> productList;
	
	///TODO: add methods to get ProductList from ProductBounded context 

}
