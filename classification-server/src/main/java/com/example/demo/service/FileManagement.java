package com.example.demo.service;

import java.util.List;

public interface FileManagement {
	public List<String> readFromFile(String path);
	public boolean clearFileContents(String path);
	public boolean writeToFile(String path, List<String> content);
	public boolean createNewFile(String path);
}
