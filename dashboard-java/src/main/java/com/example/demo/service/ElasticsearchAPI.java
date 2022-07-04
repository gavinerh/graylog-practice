package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DashboardItem;
import com.example.demo.model.ResponseObject;

public interface ElasticsearchAPI {
	boolean createDoc(String indexName, String docID, DashboardItem item);
	boolean deleteIndex(String indexName);
	boolean modifyItem(String indexName, String docID, DashboardItem item);
	boolean deleteDoc(String indexName, String docID);
	List<ResponseObject> getItems(String indexName);
}
