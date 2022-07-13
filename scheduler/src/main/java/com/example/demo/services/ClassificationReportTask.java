package com.example.demo.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ClassificationReportTask implements Runnable {

	private Environment env;

	public ClassificationReportTask(Environment env) {
		this.env = env;
	}

	@Override
	public void run() {
		String[] arr = getDateRange();
		callMethods(arr);
	}

	private String[] getDateRange() {
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date twoDaysBefore = cal.getTime();
		String[] arr = new String[2];
		arr[0] = dateFormatter(twoDaysBefore);
		arr[1] = dateFormatter(yesterday);
		return arr;
	}

	private void callMethods(String[] arr) {
		String classificationUrl = env.getProperty("classification.server.url");
		callGenerateClassificationResult(classificationUrl, arr);
		System.out.println("Finished call methods");
	}
	
	private void callGenerateClassificationResult(String url, String[] arr) {
		url += "/generateClassificationResult";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("timeMin", arr[0])
				.queryParam("timeMax", arr[1]).toUriString();
		
		// take note of the correct imported dependencies
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		Map<String, String> params = new HashMap<>();
		params.put("timeMin", arr[0]);
		params.put("timeMax", arr[1]);

		// create HttpEntity for header information
		HttpEntity<String> entity = new HttpEntity(headers);
		try {
			ResponseEntity<String> res = new RestTemplate().exchange(urlTemplate, HttpMethod.GET, entity, String.class,
					params);
			System.out.println("Request generated successfully");
		} catch (RestClientException e) {
			System.out.println("Error processing reclassify");
		}
	}


	private String dateFormatter(Date d) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		return ft.format(d);
	}

}
