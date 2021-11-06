package com.myapp.repo;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.Cart;
import com.myapp.model.EventStore;

public interface CartRepo extends Neo4jRepository<Cart,Long> {

	
	List<Cart> findByUuid(String uuid);
}
