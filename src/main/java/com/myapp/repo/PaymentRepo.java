package com.myapp.repo;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.Payment;

public interface PaymentRepo extends Neo4jRepository<Payment,Long> {

	List<Payment> findByUuid(String uuid);

}
