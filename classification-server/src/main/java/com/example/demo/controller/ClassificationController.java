package com.example.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.ResponseLogClassification;
import com.example.demo.model.ResponseModel;
import com.example.demo.service.FileManagementImpl;

@RestController
@CrossOrigin
public class ClassificationController {
	// this controller will receive a maximum and minimum date object to be used to
	// this controller will send a request to python server trigger new
	// classifications
	
	@Autowired
	FileManagementImpl manageFiles;
	
	@Autowired
	Environment env;
	
	private List<String> timeRange = new ArrayList<String>();
	private List<String> classificationLogs;

	
	// method will create new classifications depending on the time range parsed into it
	@GetMapping("/reclassify")
	public ResponseEntity<ResponseModel> triggerReclassification(@RequestParam("timeMin") String timeMin,
			@RequestParam("timeMax") String timeMax) throws IOException, URISyntaxException, InterruptedException {
		// create a request to python server
		timeRange.clear();
		timeRange.add(timeMin); timeRange.add(timeMax);
		String timeRangeFilePath = env.getProperty("timerange.file");
		manageFiles.clearFileContents(timeRangeFilePath);
		manageFiles.writeToFile(timeRangeFilePath, timeRange);
		System.out.println("reClassify endpoint called from ClassificationController");
		String baseurl = env.getProperty("base.url");
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseurl).queryParam("timeMin", timeMin)
				.queryParam("timeMax", timeMax).toUriString();
		
		// take note of the correct imported dependencies
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		Map<String, String> params = new HashMap<>();
		params.put("timeMin", timeMin);
		params.put("timeMax", timeMax);

		// create HttpEntity for header information
		HttpEntity<String> entity = new HttpEntity(headers);
		try {
			ResponseEntity<String> res = new RestTemplate().exchange(urlTemplate, HttpMethod.POST, entity, String.class,
					params);
			int code = res.getStatusCode().value();
			ResponseModel response = new ResponseModel();

			response.setMessage("success");
			return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

		} catch (RestClientException e) {
			ResponseModel response = new ResponseModel("No entries was found in time range");
			return new ResponseEntity<ResponseModel>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	// method returns a list of classified logs that was saved previously
	@GetMapping("/getLogs")
	public ResponseEntity<ResponseLogClassification> getResultList(){
		if (timeRange.size() == 0) {
			List<String> timeRangeFromFile = manageFiles.readFromFile(env.getProperty("timerange.file"));
			if (timeRangeFromFile.size() == 0) {
				// no response is previously stored
				return new ResponseEntity<ResponseLogClassification>(new ResponseLogClassification(new ArrayList<String>(), "", ""), HttpStatus.OK);
			}
			timeRange = timeRangeFromFile;
		}
		classificationLogs = manageFiles.readFromFile(env.getProperty("combined.logfile"));
		ResponseLogClassification logsClassification = new ResponseLogClassification(classificationLogs, timeRange.get(0), timeRange.get(1));
		return new ResponseEntity<ResponseLogClassification>(logsClassification, HttpStatus.OK);
	}

}
