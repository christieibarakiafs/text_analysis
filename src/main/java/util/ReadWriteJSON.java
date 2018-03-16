package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.Movie;

import java.lang.reflect.Type;
import java.util.List;

public class ReadWriteJSON {
	
	public static Object readFromJson(String file, Type type) throws FileNotFoundException{
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		InputStream is =  new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		Object result = gson.fromJson(br, type);
		return result;
		
	}
	
	public static void write(Object object, String fileName) throws IOException{
		
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String listGson = gson.toJson(object);
		
		FileOutputStream os = new FileOutputStream(fileName);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		bw.append(listGson);
		bw.close();
	}

}
