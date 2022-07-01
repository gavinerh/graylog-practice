package com.example.demo;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.DashboardItem;
import com.example.demo.repository.DashboardItemRepository;

@SpringBootApplication
public class DashboardJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardJavaApplication.class, args);
	}

}
