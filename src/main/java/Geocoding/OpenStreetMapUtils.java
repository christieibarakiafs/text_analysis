package Geocoding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import util.Tuple;

public class OpenStreetMapUtils {

    public final static Logger log = Logger.getLogger("OpenStreeMapUtils");

    private static OpenStreetMapUtils instance = null;
    private JsonParser jsonParser;
    private Planets planets;

    private OpenStreetMapUtils() {
        jsonParser = new JsonParser();
        planets = Planets.get();
    }

    public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
        	synchronized(OpenStreetMapUtils.class){
        		if(instance == null){
	            	instance = new OpenStreetMapUtils();
        		}
        	}
        }
        return instance;
    }

    private String getRequest(String url) throws Exception {

        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        if (con.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public Location getCoordinates(String address) throws GeoException {
    	
    	if(planets.isPlanet(address)){
    		return new Location(address, null);
    	}
    	
    	if(CommonLocations.getInstance().getLocation(address)!=null){
    		return CommonLocations.getInstance().getLocation(address);
    	}
    	
        StringBuffer query;
        String[] split = address.split(" ");
        String queryResult = null;

        query = new StringBuffer();

        query.append("http://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
        	return new Location(address, null);
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");


        try {
            queryResult = getRequest(query.toString());
        } catch (Exception e) {
            throw new GeoException("Error with address: " + address);
        }

        if (queryResult == null) {
        	return new Location(address, null);
        }
        
        //System.out.println(queryResult);

		JsonElement obj = new JsonParser().parse(queryResult);
        if (obj instanceof JsonArray) {
            JsonArray array = (JsonArray) obj;
            if (array.size() > 0) {
                JsonObject jsonObject = (JsonObject) array.get(0);
                String lon = jsonObject.get("lon").getAsString();
                String lat = jsonObject.get("lat").getAsString();

                return new Location(address, new Tuple(Double.parseDouble(lat), Double.parseDouble(lon)));

            }
        }

        return new Location(address, null);
    }
    
    public static void main(String[] args){

    	try{
	        Location location = OpenStreetMapUtils.getInstance().getCoordinates("ussr");
	        if(location.coords != null){
	        	System.out.println(location.name);
	        	System.out.println(location.coords.x + " , " + location.coords.y);
	        }
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
}
