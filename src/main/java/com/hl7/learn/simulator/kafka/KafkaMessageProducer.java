package com.hl7.learn.simulator.kafka;

import java.time.Instant;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 1013744
 *
 */
@Component
public class KafkaMessageProducer {
	
	/**
	 * 
	 */
	@Autowired
	private KafkaConfig kafkaConfig;

	/**
	 * 
	 */
	private KafkaProducer<String, String> kafkaProducer;

	/**
	 * 
	 */
	@PostConstruct
	public void initiaze() {
		kafkaProducer = new KafkaProducer<>(kafkaConfig.getKafkaProperties());
	}

	/**
	 * @param message
	 */
	public void publish(String topic,String key,String message) {
		ProducerRecord<String, String> recordDevice = new ProducerRecord(topic, key, message);
		kafkaProducer.send(recordDevice);
		kafkaProducer.flush();
		System.out.println("kafka publishing :topic>>"+topic+" : time >>"+Instant.now());
	}
}
