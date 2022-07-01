package com.example.demo.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class DashboardItem {

	@PrimaryKey
	@Column("id")
	private UUID id;
	@Column("query_message")
	private String queryMessage;
	@Column("time")
	private String time;
	@Column("display_message")
	private String displayMessage;
	public DashboardItem(UUID id, String queryMessage, String time, String displayMessage) {
		super();
		this.id = id;
		this.queryMessage = queryMessage;
		this.time = time;
		this.displayMessage = displayMessage;
	}
	public DashboardItem() {
		super();
	}
	public String getQueryMessage() {
		return queryMessage;
	}
	
	public UUID getId() {
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
	
	public void setId(UUID id) {
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
