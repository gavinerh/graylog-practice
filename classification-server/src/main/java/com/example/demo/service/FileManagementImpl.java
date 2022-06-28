package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileManagementImpl implements FileManagement {

	@Override
	public List<String> readFromFile(String path) {
		List<String> result = new ArrayList<>();
		System.out.println("Inside readFromFile");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		}catch(FileNotFoundException e) {
			createNewFile(path);
			return new ArrayList<String>();
		}
		catch(IOException e) {
			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public boolean clearFileContents(String path) {
		try {
			new FileWriter(path, false).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean writeToFile(String path, List<String> content) {
		try {
			FileWriter writer = new FileWriter(path);
			for(String s : content) {
				writer.write(s);
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createNewFile(String path) {
		File f = new File(path);
		try {
			if(f.createNewFile()) {
				System.out.println("File created");
			}else {
				System.out.println("File already exist");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
