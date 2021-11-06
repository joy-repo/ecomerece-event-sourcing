package com.myapp.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.myapp.contants.Commands;
import com.myapp.contants.Constant;
import com.myapp.contants.Events;
import com.myapp.contants.Topics;
import com.myapp.dto.MessageDto;
import com.myapp.model.Command;
import com.myapp.model.Event;
import com.myapp.model.EventStore;
import com.myapp.repo.CommandRepo;
import com.myapp.repo.EventStoreRepo;

@Service
public class DomainServiceHandler {

	@Autowired
	EventStoreRepo eventStoreRepo;
	

	@Autowired
	KafkaTemplate<String, Object> template;

	public void issueAddToCartCommand(Command command, String uuid) {

		String commandName = command.getCommandName();

		EventStore es = new EventStore();
		es.setEntityType(Constant.COMMAND);
		es.setEntityId(0l);
		es.setEntityType("CART");
		es.setTriggeringCommand(commandName);
		es.setUuid(uuid);
		es.setEventName("<<Name>>");

		es.setEventData(command.getCommandData());

		eventStoreRepo.save(es);

		/// TODO: From Kafka Connnect

		MessageDto mdto = new MessageDto();
		mdto.setMessage(command);
		mdto.setMessageType(Constant.COMMAND);

		template.send(Topics.FROM_DS, mdto);
	}

	public void handleEvent(Event event, String uuid) {

		EventStore es = new EventStore();
		es.setEntityType(Constant.EVENT);
		es.setEntityId(0l);
		// es.setEntityType("CART");
		es.setTriggeringCommand(event.getTriggeringCommand());
		es.setUuid(uuid);
		es.setEventName("<<Name>>");

		es.setEventData(event.getEventData());

		eventStoreRepo.save(es);

	}

	public void issuePaymentCommand(Command command, String uuid) {
		String commandName = command.getCommandName();

		EventStore es = new EventStore();
		es.setEntityType(Constant.COMMAND);
		es.setEntityId(0l);
		// es.setEntityType("PAYMENT");
		es.setTriggeringCommand(commandName);
		es.setUuid(uuid);
		es.setEventName("<<Name>>");

		es.setEventData(command.getCommandData());

		eventStoreRepo.save(es);

		/// TODO: From Kafka Connnect

		MessageDto mdto = new MessageDto();
		mdto.setMessage(command);
		mdto.setMessageType(Constant.COMMAND);

		template.send(Topics.FROM_DS, mdto);

	}

	public void handleFailedEvent(Event event, String uuid) {

		String eventName = event.getEventName();

		switch (eventName) {

		case Events.ADDED_TO_CART_FAILED:
			System.out.println("Failed");

		case Events.PAYMENT_DONE_FAILED:
			issuePaymentDoneCompensation(event, uuid);

		case Events.SHIPPING_DONE_FAILED:
			issueShippingDoneCompensation(event, uuid);

		}

	}

	private void issueShippingDoneCompensation(Event event, String uuid) {
		// TODO Auto-generated method stub
		
	}

	private void issuePaymentDoneCompensation(Event event, String uuid) {
		String eventName = event.getEventName();

		EventStore es = new EventStore();
		es.setEntityType(Constant.COMMAND);
		es.setEntityId(0l);
		// es.setEntityType("PAYMENT");
		es.setTriggeringCommand(event.getTriggeringCommand());
		es.setUuid(uuid);
		es.setEventName(eventName);

		es.setEventData(event.getEventData());

		eventStoreRepo.save(es);

		/// TODO: From Kafka Connnect

		MessageDto mdto = new MessageDto();
		Command command = new Command();

		command.setCommandData(event.getEventData());
		command.setCommandName(Commands.ADD_TO_CART_CANCEL);
		command.setCommandType("COMPENSATING_ACTION");
		command.setEntityType("CART");
		command.setTopic(Topics.FROM_DS);

		mdto.setMessage(command);
		mdto.setMessageType(Constant.COMMAND);

		template.send(Topics.FROM_DS, mdto);

	}

	private void issueAddToCartCommandCompensaion(Event event, String uuid) {
		// TODO Auto-generated method stub

	}

}
