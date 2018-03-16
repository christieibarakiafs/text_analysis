package Geocoding;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import data.StopwordList;
import util.ReadWriteJSON;

public class CommonLocations {
	
	private static CommonLocations instance;
	private Map<String, Location> locationMap;
	
	private CommonLocations(){
		this.locationMap = new HashMap<String, Location>();
		
		Type mapType = new TypeToken<Map<String, Location>>() {}.getType();
		
		ClassLoader classLoader = CommonLocations.class.getClassLoader();
		File file = new File(classLoader.getResource("locationCoords.json").getFile());
				
		try{
			Object obj = ReadWriteJSON.readFromJson(file.getAbsolutePath(), mapType);
			Map<String, Location> coordMap = (Map<String, Location>) obj;
			this.locationMap.putAll(coordMap);
		}catch(Exception e){
			// do nothing
		}
	}
	
	public Location getLocation(String location){
		if(locationMap.containsKey(location.toUpperCase())){
			return locationMap.get(location.toUpperCase());
		}
		return null;
	}
	
	public static CommonLocations getInstance(){
		if(instance == null){
			synchronized(CommonLocations.class){
				if(instance == null){
					instance = new CommonLocations();
				}
			}
		}
		
		return instance;
	}
	

}
