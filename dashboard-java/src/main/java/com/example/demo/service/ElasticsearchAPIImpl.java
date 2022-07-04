package com.example.demo.service;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.DashboardItem;
import com.example.demo.model.ElasticsearchResponse;
import com.example.demo.model.ResponseObject;

@Service
public class ElasticsearchAPIImpl implements ElasticsearchAPI {
	
	@Autowired
	Environment env;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public boolean createDoc(String indexName, String docID, DashboardItem item) {
		HttpEntity<DashboardItem> entity = create(item);
		String baseURL = env.getProperty("base.url");
		baseURL = "http://localhost:10000/search/dashboard";
		baseURL += String.format("/_doc/%s", docID);
		String response = restTemplate.exchange(baseURL, HttpMethod.POST, entity, String.class).getBody();
		System.out.println(response);
		return true;
	}

	@Override
	public boolean deleteIndex(String indexName) {
		return false;
	}

	@Override
	public boolean modifyItem(String indexName, String docID, DashboardItem item) {
		HttpEntity<DashboardItem> entity = create(item);
		String baseURL = env.getProperty("base.url");
		baseURL = "http://localhost:10000/search/dashboard";
		baseURL += String.format("/_doc/%s", docID);
		restTemplate.exchange(baseURL, HttpMethod.PUT, entity, String.class).getBody();
		return true;
	}

	@Override
	public boolean deleteDoc(String indexName, String docID) {
		HttpEntity<DashboardItem> entity = get();
		String baseURL = env.getProperty("base.url");
		baseURL = "http://localhost:10000/search/dashboard";
		baseURL += String.format("/_doc/%s", docID);
		String response = restTemplate.exchange(baseURL, HttpMethod.DELETE, entity, String.class).getBody();
		return true;
	}
	
	private HttpEntity<DashboardItem> create(DashboardItem item) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<DashboardItem> entity = new HttpEntity<DashboardItem>(item, headers);
		return entity;
	}
	
	// can be used for get and delete requests
	private HttpEntity<DashboardItem> get(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return new HttpEntity<DashboardItem>(headers);
	}

	@Override
	public List<ResponseObject> getItems(String indexName) {
		HttpEntity<DashboardItem> entity = get();
		String baseURL = env.getProperty("base.url");
		baseURL = "http://localhost:10000/search/dashboard";
		baseURL += "/_search";
		ElasticsearchResponse response = restTemplate.exchange(baseURL, HttpMethod.GET, entity, ElasticsearchResponse.class).getBody();
		return response.getHits().getHits();
	}

}
