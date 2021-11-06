package com.myapp.domain;

import java.util.List;

import com.myapp.model.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDomain {

	private List<Product> productList;

}
