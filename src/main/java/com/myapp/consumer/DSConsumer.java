
package com.myapp.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.myapp.contants.Constant;
import com.myapp.contants.Topics;
import com.myapp.ds.service.DomainService;
import com.myapp.dto.AddToCartRequestDto;
import com.myapp.dto.EventDto;
import com.myapp.dto.MessageDto;
import com.myapp.model.Cart;
import com.myapp.model.Command;
import com.myapp.model.Event;
import com.myapp.model.EventStore;
import com.myapp.repo.EventStoreRepo;

public class DSConsumer {

	// TODO: DomainService figureout

	//

	@Autowired
	EventStoreRepo eventStoreRepo;
	@Autowired
	DomainService service;

	@KafkaListener(topics = { Topics.FROM_API, Topics.FROM_CART, Topics.FROM_DS, Topics.FROM_PAY, Topics.FROM_SHIP })
	public void listenToApi(MessageDto mdto) {

		if (!duplicateMessage(mdto.getUuid()))
			return;

		if (mdto.getMessageType().equals(Constant.COMMAND))
			service.processCommand((Command) mdto.getMessage(), mdto.getUuid());

		service.processEvent((Event) mdto.getMessage(), mdto.getUuid());

	}

	private boolean duplicateMessage(String uuid) {
		List<EventStore> ll = eventStoreRepo.findByUuid(uuid);
		if (ll.size() == 0)
			return true;
		return false;
	}

}
