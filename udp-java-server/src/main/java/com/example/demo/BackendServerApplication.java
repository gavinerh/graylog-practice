package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import com.example.demo.controller.InputStreamController;
import com.example.demo.services.FileManagementImpl;

@SpringBootApplication
public class BackendServerApplication {
	

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BackendServerApplication.class, args);
		// get ports for udp stream
		Environment env = context.getEnvironment();
		List<String> ports = FileManagementImpl.readFromFile(env.getProperty("port.filepath"));
		// get the udp receiver ready 
		InputStreamController controller = (InputStreamController) context.getBean("inputStreamController");
		for (String s : ports) {
			controller.startInput(Integer.valueOf(s), "0.0.0.0");
		}
	}

}
