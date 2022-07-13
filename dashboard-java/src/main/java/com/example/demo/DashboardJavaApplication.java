package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.EndpointAggregator;
import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointRequest;

@SpringBootApplication
public class DashboardJavaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DashboardJavaApplication.class, args);
		setupEndpoints(context);
	}
	
	private static void setupEndpoints(ApplicationContext context) {
		Environment env = context.getEnvironment();
		HttpHeaders headers = new HttpHeaders();
		List<Endpoint> endpoints = new ArrayList<>();
		endpoints.add(new Endpoint("/kafka/", "get"));
		endpoints.add(new Endpoint("/kafka/", "post"));
		endpoints.add(new Endpoint("/kafka/", "delete"));
		endpoints.add(new Endpoint("/kafka/{name}", "get"));
		EndpointRequest request = new EndpointRequest();
		request.setServiceName("Kafka configuration");
		request.setEndpoints(endpoints);
		EndpointAggregator aggregator = (EndpointAggregator) context.getBean("endpointAggregator");
		aggregator.createNewEndpoint(request);
		endpoints = new ArrayList<>();
		endpoints.add(new Endpoint("/endpoint/", "get"));
		endpoints.add(new Endpoint("/endpoint/", "post"));
		endpoints.add(new Endpoint("/endpoint/service", "get"));
		request.setServiceName("Endpoints Maintainer");
		request.setEndpoints(endpoints);
		aggregator.createNewEndpoint(request);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
