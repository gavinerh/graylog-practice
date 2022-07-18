package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CronModel;
import com.example.demo.services.ClassificationReportTask;
import com.example.demo.services.DeleteElasticTask;

@RestController
@CrossOrigin
@RequestMapping("/schedule")
public class SchedulerController {

	@Autowired
	ThreadPoolTaskScheduler scheduler;
	
	@Autowired
	Environment env;

	private List<CronModel> cronModelList = new ArrayList<>();

	public ResponseEntity<Void> createDeleteSchedule(@RequestBody CronModel request){
		cronModelList.add(request);
		CronTrigger cronTrigger = new CronTrigger(request.getExpression());
		scheduler.schedule(new DeleteElasticTask(env), cronTrigger);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	public ResponseEntity<Void> createSchedule(@RequestBody CronModel request){
		cronModelList.add(request);
		CronTrigger cronTrigger = new CronTrigger(request.getExpression());
		scheduler.schedule(new ClassificationReportTask(env), cronTrigger);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<CronModel>> getSchedule(){
		if(cronModelList == null) {
			return new ResponseEntity<List<CronModel>>(cronModelList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<CronModel>>(cronModelList, HttpStatus.OK);
	}

}
