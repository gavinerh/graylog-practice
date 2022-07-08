package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ResponseModel;
import com.example.demo.service.KafkaClient;

@RestController
@CrossOrigin
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
	
	@DeleteMapping("/")
	public ResponseEntity<Void> deleteTopic(@RequestParam("topicName") String topicName){
		if (client.deleteTopic(topicName, env.getProperty("kafka.url"))) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<Void> isTopicCreated(@PathVariable("name")String name) {
		if(isTopicAlreadyCreated(name)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	// return true means already created
	private boolean isTopicAlreadyCreated(String name) {
		List<String> topiclist = client.getTopicList(env.getProperty("kafka.url"));
		name = name.toLowerCase();
		System.out.println(name);
		for(String s : topiclist) {
			String temp = s.toLowerCase();
			System.out.println(temp);
			if(name.equals(temp)) {
				return true;
			}
		}
		return false;
	}

}
