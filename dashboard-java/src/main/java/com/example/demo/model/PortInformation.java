package com.example.demo.model;

public class PortInformation {

	private String id;
	private String queryMessage;
	private String time;
	private String displayMessage;
	public PortInformation(String id, String queryMessage, String time, String displayMessage) {
		super();
		this.id = id;
		this.queryMessage = queryMessage;
		this.time = time;
		this.displayMessage = displayMessage;
	}
	public PortInformation() {
		super();
	}
	public String getQueryMessage() {
		return queryMessage;
	}
	
	public String getId() {
		return id;
	}
	public void setQueryMessage(String queryMessage) {
		this.queryMessage = queryMessage;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDisplayMessage() {
		return displayMessage;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	@Override
	public String toString() {
		return "DashboardItem [queryMessage=" + queryMessage + ", time=" + time + ", displayMessage=" + displayMessage
				+ "]";
	}
	
	
}
