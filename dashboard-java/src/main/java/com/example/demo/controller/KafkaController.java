package com.example.demo.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ResponseModel;
import com.example.demo.service.KafkaClient;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	@Autowired
	KafkaClient client;
	
	@Autowired
	Environment env;
	

	@PostMapping("/")
	public ResponseEntity<Void> create(@RequestBody ResponseModel request){
		if (client.createTopic(request.getMessage(), env.getProperty("kafka.url"))) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<String>> getTopics(){
		System.out.println(env.getProperty("kafka.url"));
		return new ResponseEntity<List<String>>(client.getTopicList(env.getProperty("kafka.url")), HttpStatus.OK);
	}
	
	@PostMapping("/producer")
	public ResponseEntity<Void> producerPublish(@RequestBody ResponseModel request){
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.url"));
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// create producer
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		
		// create producer record
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("classification", request.getMessage());
		
		// send the data
		producer.send(producerRecord);
		
		// flush and close the producer
		producer.flush();
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public void consumer() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "graylog-2");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		// create consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		
		// subscribe consumer to topic
		consumer.subscribe(Collections.singletonList("classification"));
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			
			for(ConsumerRecord<String, String> record : records) {
				System.out.println(record.value());
			}
		}
	}
}
