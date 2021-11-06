package com.myapp.model;

import org.neo4j.ogm.annotation.GeneratedValue;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data

public class Event implements MessageI{

	@Id
	@GeneratedValue
	private Long id;

	private String eventType;

	private String eventData;

	private Long entityId;

	private String entityType;

	private String triggeringCommand;

	private String eventName;

}
