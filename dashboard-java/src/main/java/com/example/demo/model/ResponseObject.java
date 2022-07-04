package com.example.demo.model;

public class ResponseObject {
	private String _id;
	private DashboardItem _source;
	public ResponseObject(String _id, DashboardItem _source) {
		super();
		this._id = _id;
		this._source = _source;
	}
	public ResponseObject() {
		super();
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public DashboardItem get_source() {
		return _source;
	}
	public void set_source(DashboardItem _source) {
		this._source = _source;
	}
	
	
}

