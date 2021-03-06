package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.SchedulerController;
import com.example.demo.model.CronModel;
import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointRequest;

@SpringBootApplication
public class SchedulerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SchedulerApplication.class, args);
		EndpointRequest request = setupEndpoint();
		sendEndpointInfo(context.getEnvironment(), request);
		setupClassificationScheduler(context);
		setupDeleteElasticDataScheduler(context);
		System.out.println("Basic set up finished");
	}
	
	private static void setupClassificationScheduler(ApplicationContext context) {
		SchedulerController scheduler = (SchedulerController) context.getBean("schedulerController");
		String cronStr = "0 1 0 * * *";
		scheduler.createSchedule(new CronModel(cronStr));
	}
	
	private static void setupDeleteElasticDataScheduler(ApplicationContext context) {
		SchedulerController scheduler = (SchedulerController) context.getBean("schedulerController");
		String schedule1 = "0 0 16 7 * *";
		String schedule2 = "0 0 16 14 * *";
		String schedule3 = "0 0 16 21 * *";
		String schedule4 = "0 0 16 27 * *";
		String schedule5 = "0 0 16 1 * *";
		scheduler.createDeleteSchedule(new CronModel(schedule1));
		scheduler.createSchedule(new CronModel(schedule2));
		scheduler.createSchedule(new CronModel(schedule3));
		scheduler.createSchedule(new CronModel(schedule4));
		scheduler.createSchedule(new CronModel(schedule5));
	}
	
	
	private static void sendEndpointInfo(Environment env, EndpointRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<EndpointRequest> entity = new HttpEntity<EndpointRequest>(request, headers);
		String url = env.getProperty("endpoint.service.url");
		url += "/endpoint/";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
	}
	
	private static EndpointRequest setupEndpoint() {
		List<Endpoint> endpoints = new ArrayList<>();
		endpoints.add(new Endpoint("/schedule/", "get"));
		EndpointRequest request = new EndpointRequest();
		request.setServiceName("Scheduler endpoint");
		request.setEndpoints(endpoints);
		return request;
	}
	

	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
	  ThreadPoolTaskScheduler threadPoolTaskScheduler
	    = new ThreadPoolTaskScheduler();
	  threadPoolTaskScheduler.setPoolSize(7);
	  threadPoolTaskScheduler.setThreadNamePrefix(
	    "ThreadPoolTaskScheduler");
	  return threadPoolTaskScheduler;
	}
	

}


