package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.InputStreamController;
import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointRequest;
import com.example.demo.services.FileManagementImpl;

@SpringBootApplication
public class BackendServerApplication {
	

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BackendServerApplication.class, args);
		// get ports for udp stream
		setupUdpStream(context);
		setupEndpoints(context);
	}
	
	private static void setupEndpoints(ApplicationContext context) {
		Environment env = context.getEnvironment();
		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		List<Endpoint> list = new ArrayList<>();
		list.add(new Endpoint("/udpStream/", "get"));
		list.add(new Endpoint("/udpStream/", "post"));
		list.add(new Endpoint("/udpStream/", "delete"));
		list.add(new Endpoint("/udpStream/{port}", "get"));
		EndpointRequest request = new EndpointRequest();
		request.setServiceName("Udp configuration server");
		request.setEndpoints(list);
		HttpEntity<EndpointRequest> entity = new HttpEntity<EndpointRequest>(request, headers);
		String url = env.getProperty("endpoint.service.url");
		url += "/endpoint/";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
	}
	
	private static void setupUdpStream(ApplicationContext context) {
		Environment env = context.getEnvironment();
		List<String> ports = FileManagementImpl.readFromFile(env.getProperty("port.filepath"));
		// get the udp receiver ready 
		InputStreamController controller = (InputStreamController) context.getBean("inputStreamController");
		for (String s : ports) {
			controller.startInput(Integer.valueOf(s), "0.0.0.0");
		}
	}

}
