package com.example.demo.model;

import java.util.List;

public class ElasticsearchResponse {
	private float took;
	private boolean timed_out;
	private Shard _shards;
	private Hit hits;
	public ElasticsearchResponse() {
		super();
	}
	public float getTook() {
		return took;
	}
	public void setTook(float took) {
		this.took = took;
	}
	public boolean isTimed_out() {
		return timed_out;
	}
	public void setTimed_out(boolean timed_out) {
		this.timed_out = timed_out;
	}
	public Shard get_shards() {
		return _shards;
	}
	public void set_shards(Shard _shards) {
		this._shards = _shards;
	}
	public Hit getHits() {
		return hits;
	}
	public void setHits(Hit hits) {
		this.hits = hits;
	}
	
}






