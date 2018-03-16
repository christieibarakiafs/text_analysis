package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GenreList {
	
	private static GenreList instance;
	private Map<Integer, String> genreMap;
	
	public static GenreList getList(){
		if(instance == null){
			synchronized (GenreList.class){
				if(instance == null){
					instance = new GenreList();
				}
			}
		}
		
		return instance;
	}
	
	public String getGenre(Integer genreInt){
		if(this.genreMap.containsKey(genreInt)){
			return this.genreMap.get(genreInt);
		}
		return"Uknown";
	}
	
	public List<String> getGenreList(JSONArray array){
	    List<String> genreNames = new ArrayList<String>();
	    for(Object genreOb :array){
	    	genreNames.add(getGenre((Integer) genreOb));
	    }
	    return genreNames;
	}
	
	private GenreList(){
		this.genreMap = new HashMap<Integer, String>();
		
		try {
			HttpResponse<String> response = (Unirest.get("https://api.themoviedb.org/3/genre/movie/list?language=en-US&api_key=8af37a6b6193833bcc3e513c421c594e")).asString();
			String jsonString = response.getBody();	
			JSONObject obj = new JSONObject(jsonString);
			JSONArray arr = obj.getJSONArray("genres");
			for(int i = 0 ; i < arr.length() ; i++){
				JSONObject result = arr.getJSONObject(i);
				this.genreMap.put(result.getInt("id"), result.getString("name"));
			}
			
		} catch (UnirestException e) {
			
			// Values from 3/9/2018
			this.genreMap.put(28, "Action");
			this.genreMap.put(12, "Adventure");
			this.genreMap.put(16, "Animation");
			this.genreMap.put(35, "Comedy");
			this.genreMap.put(80, "Crime");
			this.genreMap.put(99, "Documentary");
			this.genreMap.put(18, "Drama");
			this.genreMap.put(10751, "Family");
			this.genreMap.put(14, "Fantasy");
			this.genreMap.put(36, "History");
			this.genreMap.put(27, "Horror");
			this.genreMap.put(10402, "Music");
			this.genreMap.put(9648, "Mystery");
			this.genreMap.put(10749, "Romance");
			this.genreMap.put(878, "Science Fiction");
			this.genreMap.put(10770, "TV Movie");
			this.genreMap.put(53, "Thriller");
			this.genreMap.put(10752, "War");
			this.genreMap.put(37, "Western");
		}
	}

}
