package com.myapp.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.contants.Commands;
import com.myapp.contants.Constant;
import com.myapp.contants.Topics;
import com.myapp.dto.AddToCartRequestDto;
import com.myapp.dto.AddToCartResponseDto;
import com.myapp.dto.MessageDto;
import com.myapp.dto.PaymentDto;
import com.myapp.dto.PaymentResponseDto;
import com.myapp.dto.ShippingRequestDto;
import com.myapp.model.Command;
import com.myapp.producer.Producer;

@RestController("/myapp")
public class ApiResource {
	
	
	@Autowired
	Producer producer;
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	
	//TODO:ModelView missing
	
	
	@PostMapping("/addtocart")
	public ResponseEntity<AddToCartResponseDto> addToCart(@RequestBody AddToCartRequestDto requestDto){
		
		
		
		//CommandDto<AddToCartRequestDto> commandDto = new CommandDto<>("addToCart",requestDto);
		MessageDto mdto = new MessageDto();
		mdto.setMessageType(Constant.COMMAND);
		Command com = new Command();
		try {
			com.setCommandData(new ObjectMapper().writeValueAsString(requestDto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		com.setCommandName(Commands.ADD_TO_CART);
		com.setEntityType("CART");
		com.setEntityId(0l); // no usage in the domain as of now
		com.setCommandType("DS");
		com.setTopic(Topics.FROM_API);
		
		mdto.setMessage(com);
		
		AddToCartResponseDto responsedto = new AddToCartResponseDto("Create Cart Request Submitted",mdto.getUuid());
		
		kafkaTemplate.send(Topics.FROM_API, mdto);
		
		return new ResponseEntity<AddToCartResponseDto>(responsedto, HttpStatus.OK);
	}
	
	@PostMapping("/payment")
	public ResponseEntity<PaymentResponseDto> payment(@RequestBody PaymentDto paymentDto){
		
		
		MessageDto mdto = new MessageDto();
		mdto.setMessageType(Constant.COMMAND);
		Command com = new Command();
		try {
			com.setCommandData(new ObjectMapper().writeValueAsString(paymentDto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		com.setCommandName(Commands.PERFORM_PAYMENT);
		com.setEntityType("PAYMENT");
		com.setEntityId(0l);
		com.setCommandType("DS");
		com.setTopic(Topics.FROM_API);
		
		mdto.setMessage(com);

		PaymentResponseDto responsedto = new PaymentResponseDto("Payment Request Submitted", mdto.getUuid());

		kafkaTemplate.send(Topics.FROM_API, mdto);

		return new ResponseEntity<PaymentResponseDto>(responsedto, HttpStatus.OK);
	}

	@PostMapping("/shipping")
	public ResponseEntity<PaymentResponseDto> shipping(@RequestBody ShippingRequestDto shippingDto){
		
		
		MessageDto mdto = new MessageDto();
		mdto.setMessageType(Constant.COMMAND);
		Command com = new Command();
		try {
			com.setCommandData(new ObjectMapper().writeValueAsString(shippingDto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		com.setCommandName(Commands.DO_SHIPPING);
		com.setEntityType("SHIPPING");
		com.setEntityId(0l);
		com.setCommandType("DS");
		com.setTopic(Topics.FROM_API);
		
		mdto.setMessage(com);
		
		PaymentResponseDto responsedto = new PaymentResponseDto("Shipping Request Submitted",mdto.getUuid());
		
		kafkaTemplate.send(Topics.FROM_API, mdto);
		
		return new ResponseEntity<PaymentResponseDto>(responsedto, HttpStatus.OK);
	}

}
