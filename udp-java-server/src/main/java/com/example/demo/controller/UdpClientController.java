package com.example.demo.controller;

import java.net.DatagramPacket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.UdpClient;

@RestController
public class UdpClientController {
	@GetMapping("/testUdpClient")
	public void testClient(@RequestParam("port") int port, @RequestParam("message") String message) {
		UdpClient client = new UdpClient(port, "localhost");
		DatagramPacket packet = new DatagramPacket(message.getBytes(), 0, message.length(), client.getAddress(), port);
		
		client.sendEcho(packet);
	}
	
}
