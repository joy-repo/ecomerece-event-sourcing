package com.myapp.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.Command;

public interface CommandRepo extends Neo4jRepository<Command,Long> {

	

}
