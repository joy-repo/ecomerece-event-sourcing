package com.myapp.repo;



import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.Event;

public interface EventRepo extends Neo4jRepository<Event,Long> {
	
	 List<Event> findByUuid(String uuid);
	 
	
	

}
