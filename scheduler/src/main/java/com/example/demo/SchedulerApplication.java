package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.controller.EndpointAggregator;
import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointRequest;

@SpringBootApplication
public class SchedulerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SchedulerApplication.class, args);
		EndpointAggregator aggregator = (EndpointAggregator) context.getBean("endpointAggregator");
		List<Endpoint> endpoints = new ArrayList<>();
		endpoints.add(new Endpoint("/endpoint", "get"));
		endpoints.add(new Endpoint("/endpoint", "post"));
		endpoints.add(new Endpoint("/endpoint/service", "get"));
		EndpointRequest request = new EndpointRequest();
		request.setServiceName("endpoint configurator");
		request.setEndpoints(endpoints);
		aggregator.createNewEndpoint(request);
	}

}
