package com.example.demo.services;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.EndpointRequest;

public class DeleteElasticTask implements Runnable {

	private Environment env;
	
	public DeleteElasticTask(Environment env) {
		this.env = env;
	}
	
	@Override
	public void run() {
		deleteByQuery();
	}
	
	private void deleteByQuery() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<EndpointRequest> entity = new HttpEntity<EndpointRequest>(headers);
		String url = env.getProperty("base.url");
		url += "/delete";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println(res.getStatusCodeValue());
	}
	

}
