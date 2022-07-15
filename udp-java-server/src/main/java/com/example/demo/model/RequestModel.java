package com.example.demo.model;

public class RequestModel {
	private String port;
	private String address;
	private String topicName;

	public RequestModel() {
		super();
	}
	

	public RequestModel(String port, String address, String topicName) {
		super();
		this.port = port;
		this.address = address;
		this.topicName = topicName;
	}


	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
