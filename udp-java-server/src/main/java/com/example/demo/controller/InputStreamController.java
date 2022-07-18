package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.example.demo.model.RequestModel;
import com.example.demo.model.ResponseModel;
import com.example.demo.services.FileManagementImpl;
import com.example.demo.services.UdpReceiver;

@RestController
@CrossOrigin
@RequestMapping("/udpStream")
public class InputStreamController {
	
	@Autowired
	Environment env;
	
	private Map<String, UdpReceiver> map = new HashMap<>();
	
	@PostMapping("/")
	public ResponseEntity<ResponseModel> startInput(@RequestBody RequestModel request) {
		System.out.println("Code got here");
		if(map.containsKey(request.getPort())) {
			return new ResponseEntity<ResponseModel>(new ResponseModel("Port number is already created"), HttpStatus.BAD_REQUEST);
		}
		UdpReceiver receiver = new UdpReceiver(request.getPort(), request.getAddress(), env.getProperty("kafka.url"), request.getTopicName());
		receiver.start();
		map.put(request.getPort(),  receiver);
		FileManagementImpl.writeToFile(env.getProperty("port.filepath"), map);
		return new ResponseEntity<ResponseModel>(new ResponseModel("Stream created at port " + request.getPort()), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<ResponseModel> stopInput(@RequestParam("port") int port){
		if(!map.containsKey(String.valueOf(port))) {
			System.out.println("Inside bad response");
			return new ResponseEntity<ResponseModel>(new ResponseModel("Port number is not yet created"), HttpStatus.BAD_REQUEST);
		}
		UdpReceiver receiver = (UdpReceiver) map.get(String.valueOf(port));
		receiver.interrupt();
		map.remove(String.valueOf(port));
		FileManagementImpl.writeToFile(env.getProperty("port.filepath"), map);
		return new ResponseEntity<ResponseModel>(new ResponseModel("Stream stopped at port " + port), HttpStatus.OK);
	}
	
	@GetMapping("/{port}")
	public void checkReceiverAlive(@PathVariable("port") int port) {
		if(!map.containsKey(port)) {
			System.out.println("Receiver not created at all");
			return;
		}
		UdpReceiver receiver = (UdpReceiver) map.get(port);
		String result = receiver.isAlive() ? "Receiver is alive at port " + port : "Receiver is dead at port " + port;
		System.out.println(result);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<RequestModel>> getReceivers(){
		Set<String> set = map.keySet();
		List<RequestModel> list = new ArrayList<RequestModel>();
		for(String i : set) {
			list.add(new RequestModel(i, "0.0.0.0", map.get(i).getTopicName()));
		}
		return new ResponseEntity<List<RequestModel>>(list, HttpStatus.OK);
	}
	
}
