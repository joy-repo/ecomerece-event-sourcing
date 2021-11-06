package com.myapp.repo;



import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.myapp.model.EventStore;

public interface EventStoreRepo extends Neo4jRepository<EventStore,Long> {
	
	 List<EventStore> findByUuid(String uuid);
	 
	
	

}
