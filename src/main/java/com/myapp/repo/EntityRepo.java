package com.myapp.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.Entity;

public interface EntityRepo extends Neo4jRepository<Entity,Long> {

}
