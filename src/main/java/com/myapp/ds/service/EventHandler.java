package com.myapp.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.dto.EventDto;
import com.myapp.model.Cart;
import com.myapp.model.Event;
import com.myapp.repo.EventRepo;

@Component
public class EventHandler {

	@Autowired
	EventRepo repo;

	public void processCartEvent(EventDto<Cart> eventDto) throws JsonProcessingException {

		Event event = new Event();

		event.setEntityId(eventDto.getEventData().getId());
		event.setEntityType("CART");
		event.setEventData(new ObjectMapper().writeValueAsString(eventDto.getEventData()));
		event.setTriggeringCommand(eventDto.getEventData().getTriggeringCommand());
		event.setEventName(eventDto.getEventName());

		repo.save(event);

	}

}
