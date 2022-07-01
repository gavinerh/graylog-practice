package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.example.demo.service.CassandraConnector;

//@Component
public class MyCommandLineRunner implements CommandLineRunner {

	@Autowired
	Environment env;
	
	
	@Override
	public void run(String... args) throws Exception {
		CassandraConnector connector = new CassandraConnector();
		connector.connect(env.getProperty("spring.data.cassandra.contact-points"), Integer.valueOf(env.getProperty("spring.data.cassandra.port")));
		connector.createKeySpace(env.getProperty("spring.data.cassandra.keyspace-name"), "SimpleStrategy", 1);
	}

}
