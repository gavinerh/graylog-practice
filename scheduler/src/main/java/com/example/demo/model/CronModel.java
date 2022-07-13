package com.example.demo.model;

public class CronModel {

	private String expression;

	public CronModel(String expression) {
		this.expression = expression;
	}
	

	public CronModel() {
		super();
	}


	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
