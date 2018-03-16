package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import util.ReadWriteJSON;

public class StopwordList {
	
	private static StopwordList instance;
	private List<String> wordList;
	
	private StopwordList() throws FileNotFoundException{
		Type listType = new TypeToken<List<String>>() {}.getType();
		
		ClassLoader classLoader = StopwordList.class.getClassLoader();
		File file = new File(classLoader.getResource("stopwordList.json").getFile());
		Object obj = ReadWriteJSON.readFromJson(file.getAbsolutePath(), listType);
		
		this.wordList = (List<String>) obj;
	}
	
	public List<String> removeStopWords(List<String> tokens){
		List<String> result = new ArrayList<String>();
		for(String token : tokens){
			if(!this.wordList.contains(token.toLowerCase())){
				result.add(token);
			}
		}
		return result;
	}
	
	public static StopwordList getList() throws FileNotFoundException{
		if(instance==null){
			synchronized(StopwordList.class){
				if(instance==null){
					instance = new StopwordList();
				}
			}
		}
		
		return instance;
	}

}
