package com.myapp.aggregates;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.contants.Constant;
import com.myapp.contants.Events;
import com.myapp.contants.Topics;
import com.myapp.dto.AddToCartRequestDto;
import com.myapp.dto.MessageDto;
import com.myapp.model.Cart;
import com.myapp.model.Command;
import com.myapp.model.Event;
import com.myapp.model.Product;
import com.myapp.producer.Producer;
import com.myapp.repo.CartRepo;

@Service
public class CartAggregateService {
	
	
	
	
	
	/// Request -> DS 
	/// DS -> Command -> TOPIC
	
	/// Aggregates ---> Consume -> doAction ---> Failed.. Success From_AGGG
	
	/// DS ---> event 

	@Autowired
	CartRepo cartRepo;
	@Autowired
	Producer producer;
	
	public void processAddToCartCommand(MessageDto mdto) {

		try {
			Command com = (Command) mdto.getMessage();

			List<Product> pList = new ArrayList<>();
			try {
				pList = new ObjectMapper().readValue(com.getCommandData(), AddToCartRequestDto.class).getProductList();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Cart c = new Cart();
			c.setProductList(pList);
			c.setUuid(mdto.getUuid());
			Cart cart_r = cartRepo.save(c);

			// TODO: To go into Kafka Connect

			Event event = new Event();
			event.setEventName(Events.ADDED_TO_CART);
			event.setEventData(new ObjectMapper().writeValueAsString(cart_r));
			event.setEntityType("CART");

			MessageDto mdto_r = new MessageDto();
			mdto_r.setMessage(event);
			mdto_r.setMessageType(Constant.EVENT);

			producer.sendMessage(Topics.FROM_CART, mdto_r);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public void compensateAddToCart(MessageDto mdto)  {
		try {
			Command com = (Command) mdto.getMessage();

			// String commandData = com.getCommandData();

			List<Product> pList = null;

			pList = new ObjectMapper().readValue(com.getCommandData(), AddToCartRequestDto.class).getProductList();
			
			
			//TODO: explore IOC to inject Cart
			Cart c = new Cart();
			c.setProductList(pList);
			c.setUuid(mdto.getUuid());
			c.setId(com.getEntityId());

			cartRepo.delete(c);
			
			//TODO: KAFKA connect
			
			//TODO: to create unique as UUID .. and delete this UUID once the message has been sent in kafka

			Event event = new Event();
			event.setEventName("REMOVED_FROM_CART");
			event.setEventData(new ObjectMapper().writeValueAsString(c));
			event.setEntityType("CART");

			MessageDto mdto_r = new MessageDto();
			mdto_r.setMessage(event);
			mdto_r.setMessageType(Constant.EVENT);

			producer.sendMessage(Topics.FROM_CART, mdto_r);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
