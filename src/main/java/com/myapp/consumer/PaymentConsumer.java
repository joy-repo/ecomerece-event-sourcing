package com.myapp.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.myapp.aggregates.CartAggregateService;
import com.myapp.aggregates.PaymentAggregateService;
import com.myapp.contants.Commands;
import com.myapp.contants.Topics;
import com.myapp.dto.EventDto;
import com.myapp.dto.MessageDto;
import com.myapp.model.Cart;
import com.myapp.model.Command;
import com.myapp.model.Payment;
import com.myapp.repo.CartRepo;
import com.myapp.repo.PaymentRepo;

public class PaymentConsumer {

	@Autowired
	PaymentAggregateService service;

	@Autowired
	PaymentRepo payRepo;

	@KafkaListener(topics = Topics.FROM_DS)
	public void listenForCommand(MessageDto mdto) {
		if (isDuplicateMessage(mdto.getUuid()))
			return;
		Command com = (Command) mdto.getMessage();
		switch (com.getCommandName()) {

		case Commands.PERFORM_PAYMENT:
			service.processPerformPaymentCommand(mdto);

		case Commands.PERFORM_PAYMENT_CANCEL:
			service.compensatePaymentDone(mdto);
		}

	}

	private boolean isDuplicateMessage(String uuid) {
		List<Payment> ll = payRepo.findByUuid(uuid);
		if (ll.size() > 0)
			return true;
		return false;
	}

}
