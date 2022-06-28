package com.example.demo.model;

import java.util.List;

public class ResponseLogClassification {

	private List<String> results;
	private String timeMin;
	private String timeMax;
	public ResponseLogClassification(List<String> results, String timeMin, String timeMax) {
		super();
		this.results = results;
		this.timeMin = timeMin;
		this.timeMax = timeMax;
	}
	public ResponseLogClassification() {
		super();
	}
	public List<String> getResults() {
		return results;
	}
	public void setResults(List<String> results) {
		this.results = results;
	}
	public String getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(String timeMin) {
		this.timeMin = timeMin;
	}
	public String getTimeMax() {
		return timeMax;
	}
	public void setTimeMax(String timeMax) {
		this.timeMax = timeMax;
	}
	
	
}
