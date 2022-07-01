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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DashboardItem;
import com.example.demo.model.ResponseModel;
import com.example.demo.repository.DashboardItemRepository;

@RestController
@CrossOrigin
@RequestMapping("/dashboardItem")
public class DashboardController {
	
	@Autowired
	DashboardItemRepository repo;

	
	@PostMapping("/")
	public ResponseEntity<ResponseModel> createNewItem(@RequestBody DashboardItem request){
		UUID uid = UUID.randomUUID();
		request.setId(uid);
		DashboardItem item = repo.save(request);
		ResponseModel response = new ResponseModel("success");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<DashboardItem>> getAllItems(){
		List<DashboardItem> result = (List<DashboardItem>) repo.findAll();
		for(DashboardItem d : result) {
			System.out.println(d);
		}
		return new ResponseEntity<List<DashboardItem>>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") String id){
		UUID uid = UUID.fromString(id);
		boolean result = repo.existsById(uid);
		if (result) {
			repo.deleteById(uid);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
	}
	
}
