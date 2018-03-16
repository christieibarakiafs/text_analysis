package demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;

import Geocoding.GeoException;
import Geocoding.Location;
import Geocoding.OpenStreetMapUtils;
import ner.Annotations;
import ner.Entity;
import string.Tokenizer;
import util.ReadWriteJSON;
import util.Tuple;

public class GetGeocodesForCommonLocations {

	public static void main(String[] args) throws IOException, GeoException {

		/*
		 * Type listType = new TypeToken<List<Movie>>() {}.getType(); String
		 * file = "//Users//christineibaraki//Desktop//movieList.json"; Object
		 * obj = ReadWriteJSON.readFromJson(file, listType); List<Movie>
		 * movieList = (List<Movie>) obj;
		 * 
		 * Map<String, Integer> locationCount = new HashMap<String, Integer>();
		 * 
		 * for(Movie movie : movieList){
		 * 
		 * Annotations movieAnnotations = new Annotations(movie.getOverview());
		 * Set<String> locations = movieAnnotations.getLocationMap();
		 * if(locations != null){ for(String location : locations){
		 * 
		 * if(locationCount.containsKey(location)){ int count =
		 * locationCount.get(location); locationCount.put(location, count+1);
		 * }else{ locationCount.put(location, 1); } } }
		 * 
		 * } // end loop through movies
		 * 
		 * locationCount = sortMapDescending(locationCount);
		 */

		Type mapType = new TypeToken<Map<String, Integer>>() {
		}.getType();
		String file = "//Users//christineibaraki//Desktop//locationCount.json";
		Object obj = ReadWriteJSON.readFromJson(file, mapType);
		Map<String, Integer> locationCount = (Map<String, Integer>) obj;

		// mapType = new TypeToken<Map<String, Location>>() {}.getType();
		// file = "//Users//christineibaraki//Desktop//locationCoords.json";
		// obj = ReadWriteJSON.readFromJson(file, mapType);
		// Map<String, Location> coordMap = (Map<String, Location>) obj;

		Map<String, Location> coordMap = new HashMap<String, Location>();

		for (String location : locationCount.keySet()) {

			try {
				System.out.println(location);
				Location resultLocation = OpenStreetMapUtils.getInstance().getCoordinates(location);
				coordMap.put(location, resultLocation);
			} catch (GeoException e) {
				System.out.println(e.getMessage());
			}
		}

		for (String key : locationCount.keySet()) {
			if (!coordMap.containsKey(key)) {
				System.out.println(key);
			}
		}

		// ReadWriteJSON.write(locationCount,
		// "//Users//christineibaraki//Desktop//locationCount.json");
		ReadWriteJSON.write(coordMap, "//Users//christineibaraki//Desktop//locationCoords.json");

	}

}
