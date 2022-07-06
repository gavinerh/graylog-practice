package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {
	
	private AdminClient admin = null;

	public boolean createTopic(String topicName, String kafkaUrl) {
		NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
		if (admin == null) {
			createAdmin(kafkaUrl);
		}
		admin.createTopics(Collections.singleton(newTopic));
		return true;
	}
	
	public List<String> getTopicList(String kafkaUrl){
		List<String> names = new ArrayList<>();
		if (admin == null) {
			createAdmin(kafkaUrl);
		}
		try {
			for(TopicListing topicListing : admin.listTopics().listings().get()) {
				names.add(topicListing.name());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	private void createAdmin(String kafkaUrl) {
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
		admin = AdminClient.create(properties);
	}
	
}
