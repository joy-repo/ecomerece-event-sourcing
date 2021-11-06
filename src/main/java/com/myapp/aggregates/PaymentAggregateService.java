package com.myapp.aggregates;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.contants.Constant;
import com.myapp.contants.Events;
import com.myapp.contants.Topics;
import com.myapp.domain.PaymentDomain;
import com.myapp.domain.PaymentDomain.PaymentDomainBuilder;
import com.myapp.dto.AddToCartRequestDto;
import com.myapp.dto.MessageDto;
import com.myapp.dto.PaymentDto;
import com.myapp.model.Command;
import com.myapp.model.Event;
import com.myapp.model.Payment;
import com.myapp.model.Product;
import com.myapp.producer.Producer;
import com.myapp.repo.PaymentRepo;

@Service
public class PaymentAggregateService {

	@Autowired
	PaymentRepo repo;
	@Autowired
	Producer producer;

	public void processPerformPaymentCommand(MessageDto mDto) {
		try {
			Command com = (Command) mDto.getMessage();

			PaymentDto pDto = new ObjectMapper().readValue(com.getCommandData(), PaymentDto.class);
			
			PaymentDomainBuilder pdb  = PaymentDomain.builder();
			
			PaymentDomain pd =pdb.cardType(pDto.getCardType()).cartID(pDto.getCartID()).paymentType(pDto.getPaymentType()).build();

			Payment p = new Payment();
			p.setCardType(pd.getCardType());
			p.setCartID(pd.getCartID());
			p.setPaymentType(pd.getPaymentType());

			p.setUuid(mDto.getUuid());
			Payment pay_r = repo.save(p);

			// TODO: To go into Kafka Connect

			Event event = new Event();
			event.setEventName(Events.PAYMENT_DONE);
			event.setEventData(new ObjectMapper().writeValueAsString(pay_r));
			event.setEntityType("PAYMENT");

			MessageDto mdto_r = new MessageDto();
			mdto_r.setMessage(event);
			mdto_r.setMessageType(Constant.EVENT);

			producer.sendMessage(Topics.FROM_PAY, mdto_r);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	public void compensatePaymentDone(MessageDto mdto) {
		
		try {
			Command com = (Command) mdto.getMessage();

			// String commandData = com.getCommandData();

			List<Product> pList = null;

			Payment pay = new ObjectMapper().readValue(com.getCommandData(), Payment.class);

			Payment p = new Payment();
			p.setCardType(pay.getCardType());
			p.setId(com.getEntityId());
			p.setCartID(pay.getCartID());

			repo.delete(p);

			Event event = new Event();
			event.setEventName("REMOVED_FROM_CART");
			event.setEventData(new ObjectMapper().writeValueAsString(p));
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
