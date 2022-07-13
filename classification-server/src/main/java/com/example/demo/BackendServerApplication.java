package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointRequest;

@SpringBootApplication
public class BackendServerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BackendServerApplication.class, args);
		setupEndpoints(context);
	}
	
	private static void setupEndpoints(ApplicationContext context) {
		Environment env = context.getEnvironment();
		HttpHeaders headers = new HttpHeaders();
		List<Endpoint> list = new ArrayList<>();
		list.add(new Endpoint("/reclassify", "get"));
		list.add(new Endpoint("/getLogs", "get"));
		list.add(new Endpoint("/generateClassificationResult", "get"));
		EndpointRequest request = new EndpointRequest();
		request.setServiceName("Java Classification server");
		request.setEndpoints(list);
		HttpEntity<EndpointRequest> entity = new HttpEntity<EndpointRequest>(request, headers);
		String url = env.getProperty("endpoint.service.url");
		url += "/endpoint/";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
	}

}
