package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DashboardItem;
import com.example.demo.model.ResponseModel;
import com.example.demo.model.ResponseObject;
import com.example.demo.service.ElasticsearchAPI;

@RestController
@CrossOrigin
@RequestMapping("/dashboardItem")
public class DashboardController {

	@Autowired
	ElasticsearchAPI repo;
	
	@PostMapping("/")
	public ResponseEntity<ResponseModel> createNewItem(@RequestBody DashboardItem request){
		String uid = UUID.randomUUID().toString();
		request.setId(uid);
		boolean isAdded = repo.createDoc("dashboard", uid, request);
//		if (isAdded) {
//			
//		}
		ResponseModel response = new ResponseModel("success");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ResponseObject>> getAllItems(){
		List<ResponseObject> response = repo.getItems("dashboard");
		return new ResponseEntity<List<ResponseObject>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") String id){
		System.out.println("Delete method called");
		repo.deleteDoc("dashboard", id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}
	
//	@PutMapping("/")
//	public ResponseEntity<String> updateItem(@RequestBody DashboardItem request){
//		
//	}
	
}
