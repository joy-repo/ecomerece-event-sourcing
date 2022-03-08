package com.myapp.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.contants.Commands;
import com.myapp.contants.Events;
import com.myapp.model.Command;
import com.myapp.model.Event;


/// Routi8ng

//(state  && Command/Event ) -->

@Service
public class DomainService {
	@Autowired
	DomainServiceHandler executor;

	public void processCommand(Command command, String uuid) {

		switch (command.getCommandName()) {

		case Commands.ADD_TO_CART:
			executor.issueAddToCartCommand(command, uuid);
			
		case Commands.PERFORM_PAYMENT:
			executor.issuePaymentCommand(command,uuid);

		}

	}

	public void processEvent(Event event, String uuid) {
		switch (event.getEventName()) {

		case Events.ADDED_TO_CART:
			executor.handleEvent(event, uuid);
			
		case Events.ADDED_TO_CART_FAILED:
			executor.handleEvent(event, uuid);
			executor.handleFailedEvent(event,uuid);
			
		case Events.PAYMENT_DONE:
			executor.handleEvent(event,uuid);
			
		case Events.PAYMENT_DONE_FAILED:
			executor.handleEvent(event, uuid);
			
		case Events.SHIPPING_DONE:
			executor.handleEvent(event,uuid);
			
		case Events.SHIPPING_DONE_FAILED:
			executor.handleEvent(event,uuid);

		}

	}

	// public void handleCartEvent(Event<>)

}
