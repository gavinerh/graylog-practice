package com.example.demo.model;

import java.util.List;

public class Hit{
	private Total total;
	private List<ResponseObject> hits;
	public Hit() {
		super();
	}
	public Total getTotal() {
		return total;
	}
	public void setTotal(Total total) {
		this.total = total;
	}
	public List<ResponseObject> getHits() {
		return hits;
	}
	public void setHits(List<ResponseObject> hits) {
		this.hits = hits;
	}
	
	
}
