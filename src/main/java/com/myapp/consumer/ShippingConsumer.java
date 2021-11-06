package com.myapp.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.myapp.aggregates.ShippingAggregateService;
import com.myapp.contants.Commands;
import com.myapp.contants.Topics;
import com.myapp.dto.MessageDto;
import com.myapp.model.Command;
import com.myapp.model.Shipping;
import com.myapp.repo.ShippingRepo;

public class ShippingConsumer {
	
	@Autowired
	ShippingAggregateService service;
	
	@Autowired
	ShippingRepo shipRepo;

	@KafkaListener(topics = Topics.FROM_DS)
	public void listenForCommand(MessageDto mdto) {
		if(isDuplicateMessage(mdto.getUuid())) return;
		Command com =(Command) mdto.getMessage();
		switch(com.getCommandName()) {
		
		case Commands.DO_SHIPPING:
			service.processDoShippingCommand(mdto);
		}
		

	}
	
	private boolean isDuplicateMessage(String uuid) {
		List<Shipping> ll = shipRepo.findByUuid(uuid);
		if (ll.size() > 0)
			return true;
		return false;
	}
}
