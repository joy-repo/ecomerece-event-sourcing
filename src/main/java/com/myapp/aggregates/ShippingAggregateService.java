package com.myapp.aggregates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.contants.Constant;
import com.myapp.contants.Events;
import com.myapp.contants.Topics;
import com.myapp.dto.MessageDto;
import com.myapp.dto.ShippingRequestDto;
import com.myapp.model.Command;
import com.myapp.model.Event;
import com.myapp.model.Shipping;
import com.myapp.producer.Producer;
import com.myapp.repo.ShippingRepo;

@Service
public class ShippingAggregateService {

	@Autowired
	ShippingRepo repo;

	@Autowired
	Producer producer;

	public void processDoShippingCommand(MessageDto mDto) {
		try {
			Command com = (Command) mDto.getMessage();

			ShippingRequestDto sDto = new ObjectMapper().readValue(com.getCommandData(), ShippingRequestDto.class);

			Shipping s = new Shipping();
			s.setAddress(sDto.getAddress());
			s.setShippingType(sDto.getShippingType());

			s.setUuid(mDto.getUuid());

			Shipping ship_r = repo.save(s);

			// TODO: To go into Kafka Connect

			Event event = new Event();
			event.setEventName(Events.SHIPPING_DONE);
			event.setEventData(new ObjectMapper().writeValueAsString(ship_r));
			event.setEntityType("SHIPPING");

			MessageDto mdto_r = new MessageDto();
			mdto_r.setMessage(event);
			mdto_r.setMessageType(Constant.EVENT);

			producer.sendMessage(Topics.FROM_SHIP, ship_r);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
