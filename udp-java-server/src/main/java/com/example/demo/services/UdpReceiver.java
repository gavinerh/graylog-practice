package com.example.demo.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class UdpReceiver extends Thread {
	private DatagramSocket socket;
	private byte[] buffer = new byte[262144];
	private String port;
	private DatagramPacket packet;
	private String kafkaUrl;
	private String topicName;

	
	public UdpReceiver(String port, String address, String kafkaUrl, String topicName) {
		this.topicName = topicName;
		this.kafkaUrl = kafkaUrl;
		try {
			this.port = port;
			socket = new DatagramSocket(null);
			InetSocketAddress add = new InetSocketAddress(Integer.valueOf(port));
			socket.bind(add);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	

	public String getTopicName() {
		return topicName;
	}



	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}



	@Override
	public void run() {
		System.out.println("Data input stream for port " + port + " is now open");
		KafkaProducer producer = createProducer();
		while(!socket.isClosed()) {
			packet = new DatagramPacket(buffer, buffer.length);
			String dataStr = "";
			ProducerRecord<String, String> producerRecord = null;
			try {
				socket.receive(packet);
				System.out.println("Server Listening... from " + socket.getLocalPort());
				dataStr = new String(packet.getData(), 0, packet.getLength());
				System.out.println(dataStr);
				// send data to producer here
				producerRecord = new ProducerRecord<String, String>(topicName, dataStr);
				// send the data
				producer.send(producerRecord);
			} catch (IOException e) {
				break;
			}finally {
				producer.flush();
			}
		}
	}

	@Override
	public void interrupt() {
		System.out.println("Stream at port " + port + " interrupted");
		socket.close();
	}
	
	private KafkaProducer createProducer() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// create producer
		return new KafkaProducer<>(properties);
		
	}
	
	
	
}
