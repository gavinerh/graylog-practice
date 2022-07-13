package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointRequest;

@RestController
@CrossOrigin
@RequestMapping("/endpoint")
public class EndpointAggregator {
	
	public static HashMap<String, List<Endpoint>> map = new HashMap<>();

	@PostMapping("/")
	public ResponseEntity<Void> createNewEndpoint(@RequestBody EndpointRequest request) {
		map.put(request.getServiceName(), request.getEndpoints());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<Endpoint>> getAllEndpoints(){
		List<Endpoint> list = new ArrayList<Endpoint>();
		for(String s : map.keySet()) {
			list.addAll(map.get(s));
		}
		return new ResponseEntity<List<Endpoint>> (list, HttpStatus.OK);
	}
	
	@GetMapping("/services")
	public ResponseEntity<Map<String, List<Endpoint>>> getServiceWithEndpoints(){
		return new ResponseEntity<Map<String,List<Endpoint>>>(map, HttpStatus.OK);
	}
}
