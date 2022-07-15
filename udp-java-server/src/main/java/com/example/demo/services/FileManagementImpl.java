package com.example.demo.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FileManagementImpl {

	public static List<String> readFromFile(String path) {
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

	public static boolean clearFileContents(String path) {
		try {
			new FileWriter(path, false).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean writeToFile(String path, Map<String, UdpReceiver> content) {
		System.out.println("Inside write to file");
		try {
			FileWriter writer = new FileWriter(path);
			for(String s : content.keySet()) {
				writer.write(s); // writes the port number
				writer.write(" ");
				writer.write(content.get(s).getTopicName()); // writes the topic name
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean createNewFile(String path) {
		System.out.println("Inside create new file");
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
