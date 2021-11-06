	package com.myapp.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@NodeEntity(label = "Entity")
public class Entity {
	
	@Id
	@GeneratedValue	
	private Long id;
	
	private String entityType ;
	private long entityId;
	
	private String entityVersion ;
	

}
