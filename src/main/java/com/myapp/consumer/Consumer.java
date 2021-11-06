package com.myapp.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class Consumer {

	@KafkaListener(topics = "joy1", groupId = "foo")
	public void listen(String message) {
		System.out.println("Received Messasge in group foo: " + message);
	}

	@KafkaListener(topics = "joy1")
	public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		System.out.println("Received Message: " + message + "from partition: " + partition);
	}

}
