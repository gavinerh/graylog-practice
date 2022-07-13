package com.example.demo.model;

public class Endpoint {

	private String name;
	private String method;
	public Endpoint(String name, String method) {
		super();
		this.name = name;
		this.method = method;
	}
	public Endpoint() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
