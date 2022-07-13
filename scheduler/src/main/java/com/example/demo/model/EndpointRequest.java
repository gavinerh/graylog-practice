package com.example.demo.model;

import java.util.List;

public class EndpointRequest {

	private String serviceName;
	private List<Endpoint> endpoints;

	public EndpointRequest() {
		super();
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<Endpoint> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
}
