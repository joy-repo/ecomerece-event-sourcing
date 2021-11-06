package com.myapp.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@NodeEntity(label = "Command")
public class Command  implements MessageI{

	@Id
	@GeneratedValue
	private Long id;
	
	private String commandName;

	private String commandType;

	private String commandData;

	private long entityId;

	private String entityType;
	
	private String topic;

}
