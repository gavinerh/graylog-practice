package com.example.demo.model;

public class ResponseModel {
	private String message;

	public ResponseModel(String message) {
		super();
		this.message = message;
	}

	public ResponseModel() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
