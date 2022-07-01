package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.DashboardItem;

//@Repository
public interface DashboardItemRepository extends CrudRepository<DashboardItem, UUID> {
	
}
