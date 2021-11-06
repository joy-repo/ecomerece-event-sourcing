package com.myapp.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.myapp.aggregates.CartAggregateService;
import com.myapp.contants.Commands;
import com.myapp.contants.Topics;
import com.myapp.dto.MessageDto;
import com.myapp.model.Cart;
import com.myapp.model.Command;
import com.myapp.repo.CartRepo;

@Component
public class CartConsumer {

	@Autowired
	CartAggregateService service;
	
	@Autowired
	CartRepo cartRepo;

	@KafkaListener(topics = Topics.FROM_DS)
	public void listenForCommand(MessageDto mdto) {
		if(isDuplicateMessage(mdto.getUuid())) return;
		Command com =(Command) mdto.getMessage();
		switch(com.getCommandName()) {
		
		case Commands.ADD_TO_CART:
			service.processAddToCartCommand(mdto);
		
		
		case Commands.ADD_TO_CART_CANCEL:
			service.compensateAddToCart(mdto);
		}

	}
	
	
	private boolean isDuplicateMessage(String uuid) {
		List<Cart> ll = cartRepo.findByUuid(uuid);
		if (ll.size() > 0)
			return true;
		return false;
	}

}
