package com.myapp.repo;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.Shipping;

public interface ShippingRepo extends Neo4jRepository<Shipping,Long> {

	List<Shipping> findByUuid(String uuid);

}
