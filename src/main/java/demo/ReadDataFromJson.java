package demo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

import Geocoding.GeoException;
import data.Movie;
import data.Result;
import ner.Annotations;
import ner.Entity;
import string.Tokenizer;
import util.CsvWriter;
import util.ReadWriteJSON;

public class ReadDataFromJson {
	
	private static String PERSON = "PERSON";
	private static String LOCATION = "LOCATION";
	private static String ORGANIZATION = "ORGANIZATION";
	private static String DATE = "DATE";
	
	
	private static void updateMap(Map<String, Map<String,Integer>> mapToUpdate, Set<String> movieStringSet, String type){
		
		
		if(movieStringSet != null){
			Map<String, Integer> innerMap = mapToUpdate.get(type);
		
			for(String movieString : movieStringSet){
				if(innerMap.containsKey(movieString)){
					int count = innerMap.get(movieString);
					innerMap.put(movieString, count+1);
				}else{
					innerMap.put(movieString, 1);
				}
			}
			
			mapToUpdate.put(type, innerMap);
		}
	}
	
	public static void main(String args[]) throws IOException, GeoException{
			
		Type listType = new TypeToken<List<Movie>>() {}.getType();
		String file = "//Users//christineibaraki//Desktop//movieList.json";
		Object obj = ReadWriteJSON.readFromJson(file, listType);
		List<Movie> movieList = (List<Movie>) obj;
		
		Map<Entity, Integer> entityCounts = new HashMap<Entity, Integer>();
		Map<String, Integer> tokenCounts = new HashMap<String, Integer>();
		
		Map<String, Map<String, Integer>> queryLevelEntityMap = new HashMap<String, Map<String, Integer>>();
		queryLevelEntityMap.put(PERSON, new HashMap<String, Integer>());
		queryLevelEntityMap.put(LOCATION, new HashMap<String, Integer>());
		queryLevelEntityMap.put(ORGANIZATION, new HashMap<String, Integer>());
		queryLevelEntityMap.put(DATE, new HashMap<String, Integer>());

		
		for(Movie movie : movieList){
			
			List<String> tokens = Tokenizer.getNGrams(movie.getOverview(), 2);
			
			for(String token : tokens){
				token = token.toUpperCase();
				if(tokenCounts.containsKey(token)){
					int count = tokenCounts.get(token);
					tokenCounts.put(token, count+1);
				}else{
					tokenCounts.put(token, 1);
					
				}
			}
			
//			Annotations movieAnnotations = new Annotations(movie.getOverview());
//			movie.setEntities(movieAnnotations);
//			updateMap(queryLevelEntityMap, movieAnnotations.getPersonMap(), PERSON);
//			updateMap(queryLevelEntityMap, movieAnnotations.getLocationMap(), LOCATION);
//			updateMap(queryLevelEntityMap, movieAnnotations.getOrganizationMap(), ORGANIZATION);
//			updateMap(queryLevelEntityMap, movieAnnotations.getDateMap(), DATE);
//			
//			// Populate entity map
//			Map<String, Set<String>> entityMap = movieAnnotations.getEntityMap();
//			for(String key : entityMap.keySet()){
//				Set<String> innerSet = entityMap.get(key);
//				for(String word : innerSet){
//					Entity entity = new Entity(word, key);
//					if(entityCounts.containsKey(entity)){
//						int counts = entityCounts.get(entity);
//						entityCounts.put(entity, counts + 1);
//					}else{
//						entityCounts.put(entity, 1);
//					}
//				}
//			}
		}
		
		Result resultObj = new Result(movieList, queryLevelEntityMap, tokenCounts);
		ReadWriteJSON.write(resultObj, "//Users//christineibaraki//Desktop//movieResult.json");

		
//		System.out.println("write file");
//		
//		List<String[]> entityResults = new ArrayList<String[]>();
//		String[] header = {"TYPE", "ENTITY", "COUNT"};
//		entityResults.add(header);
//		
//		for(Entity entity : entityCounts.keySet()){
//			
//			String[] newRow = {entity.getType(), entity.getName().replaceAll(",", " "), Integer.toString(entityCounts.get(entity))};
//			entityResults.add(newRow);
//			
//		}
//		
//		System.out.println(entityResults.size());
//		CsvWriter.write(entityResults, "//Users//christineibaraki//Desktop//movie_entity_counts.csv");
//		
		List<String[]> results = new ArrayList<String[]>();
		String[] ngramheader= {"TOKEN", "COUNT"};
		results.add(ngramheader);
		
		for(String token : tokenCounts.keySet()){
			if(tokenCounts.get(token)>1){
				String[] newRow = {token, Integer.toString(tokenCounts.get(token))};
				results.add(newRow);
			}
		}
		
		System.out.println(results.size());
		CsvWriter.write(results, "//Users//christineibaraki//Desktop//movie_token_counts.csv");
		System.out.println("file written");
		
	}

}
