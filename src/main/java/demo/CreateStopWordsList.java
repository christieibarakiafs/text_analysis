package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ReadWriteJSON;

public class CreateStopWordsList {
	
	public static void main(String[] args) throws IOException{
		
		List<String> stopWords = new ArrayList<String>();
		
		BufferedReader br = new BufferedReader(new FileReader("//Users//christineibaraki//Desktop//stopwordlist.txt"));
		try{
			String line = br.readLine();
			while(line != null){
				String[] tokens = line.replaceAll("\\s+", " ").split("[\n\t\\s]");
				for(String token : tokens){
					stopWords.add(token.trim());
				}
				line = br.readLine();
			}
			

		}finally{
			br.close();
		}
		
		ReadWriteJSON.write(stopWords, "//Users//christineibaraki//Desktop//stopwordList.json");

		
	}

}
