package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ResponseModel;
import com.example.demo.services.FileManagementImpl;
import com.example.demo.services.UdpReceiver;

@RestController
@RequestMapping("/udpStream")
public class InputStreamController {
	
	@Autowired
	Environment env;
	
	private HashMap<Integer, Thread> map = new HashMap<>();
	
	@PostMapping("/")
	public ResponseEntity<ResponseModel> startInput(@RequestParam("port") Integer port, @RequestParam("address") String address) {
		if(map.containsKey(port)) {
			return new ResponseEntity<ResponseModel>(new ResponseModel("Port number is already created"), HttpStatus.BAD_REQUEST);
		}
		List<String> list = new ArrayList<String>();
		for(Integer i : map.keySet()) {
			list.add(i.toString());
		}
		list.add(port.toString());
		FileManagementImpl.writeToFile(env.getProperty("port.filepath"), list);
		UdpReceiver receiver = new UdpReceiver(port, address, env.getProperty("kafka.url"));
		receiver.start();
		map.put(port,  receiver);
		return new ResponseEntity<ResponseModel>(new ResponseModel("Stream created at port " + port), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<ResponseModel> stopInput(@RequestParam("port") int port){
		if(!map.containsKey(port)) {
			return new ResponseEntity<ResponseModel>(new ResponseModel("Port number is not yet created"), HttpStatus.BAD_REQUEST);
		}
		UdpReceiver receiver = (UdpReceiver) map.get(port);
		map.remove(port);
		receiver.interrupt();
		Set<Integer> ports = map.keySet();
		List<String> list = new ArrayList<>();
		for (Integer i : ports) {
			list.add(i.toString());
		}
		FileManagementImpl.writeToFile(env.getProperty("port.filepath"), list);
		return new ResponseEntity<ResponseModel>(new ResponseModel("Stream stopped at port " + port), HttpStatus.OK);
	}
	
	@GetMapping("/checkAlive")
	public void checkReceiverAlive(@RequestParam("port") int port) {
		if(!map.containsKey(port)) {
			System.out.println("Receiver not created at all");
			return;
		}
		UdpReceiver receiver = (UdpReceiver) map.get(port);
		String result = receiver.isAlive() ? "Receiver is alive at port " + port : "Receiver is dead at port " + port;
		System.out.println(result);
	}
	
	@GetMapping("/")
	public ResponseEntity<Set<Integer>> getReceivers(){
		Set<Integer> list = map.keySet();
		return new ResponseEntity<Set<Integer>>(list, HttpStatus.OK);
	}
	
}
