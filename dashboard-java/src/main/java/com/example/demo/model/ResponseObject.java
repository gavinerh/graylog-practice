package com.example.demo.model;

public class ResponseObject {
	private String _id;
	private PortInformation _source;
	public ResponseObject(String _id, PortInformation _source) {
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
	public PortInformation get_source() {
		return _source;
	}
	public void set_source(PortInformation _source) {
		this._source = _source;
	}
	
	
}

