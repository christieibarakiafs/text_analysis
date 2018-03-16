package demo;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import data.GenreList;
import data.Movie;
import util.CsvWriter;
import util.ReadWriteJSON;

public class ReadDataFromApi {
	
	public static void main(String[] args) throws UnirestException, InterruptedException, IOException{
		
		List<String[]> tabData = new ArrayList<String[]>();
		String[] header = {"Title", "Release Date", "Popularity", "Overview"};
		tabData.add(header);
		
		GenreList genreList = GenreList.getList();
		
		List<Movie> movieList = new ArrayList<Movie>();
		HttpResponse<String> response = (Unirest.get("https://api.themoviedb.org/3/discover/movie?"
				+ "with_genres=878"
				+ "&primary_release_date.lte=2018"
				+ "&primary_release_date.gte=1960"
				//+ "&page=10"
				+ "&include_video=false"
				+ "&include_adult=false"
				+ "&sort_by=popularity.desc"
				+ "&language=en-US"
				+ "&api_key=8af37a6b6193833bcc3e513c421c594e")).asString();
		
		String jsonString = response.getBody();	
		JSONObject obj = new JSONObject(jsonString);
		int totalPages = (Integer) obj.get("total_pages");
		int totalResults = (Integer) obj.get("total_results");
		System.out.println("pages: " + totalPages + " ; results: " + totalResults);
		
		int counter = 0;
		
		for ( int pageNum = 0 ; pageNum < totalPages ; pageNum++ )
		{
			StopWatch timer = new StopWatch();
			timer.start();
			double timeWaiting = 0.0;

			while(timeWaiting < 20.0){
				try{
					
					System.out.println("trying page: " + pageNum);
					HttpResponse<String> pageResponse = (Unirest.get("https://api.themoviedb.org/3/discover/movie?"
				
						+ "with_genres=878"
						+ "&primary_release_date.lte=2018"
						+ "&primary_release_date.gte=1960"
						+ "&page=" + Integer.toString(pageNum+1)
						+ "&include_video=false"
						+ "&include_adult=false"
						+ "&without_genres=10770"
						+ "&sort_by=release_date.asc"
						+ "&language=en-US"
						+ "&api_key=8af37a6b6193833bcc3e513c421c594e")).asString();
					
				
				String  pageJsonString = pageResponse.getBody();
				JSONObject pageObj = new JSONObject(pageJsonString);
				JSONArray arr = pageObj.getJSONArray("results");
				for (int i = 0; i < arr.length(); i++)
				{			
					
				    JSONObject result = arr.getJSONObject(i);
				    JSONArray genres = result.getJSONArray("genre_ids");
				    List<String> genreNames = genreList.getGenreList(genres);

				    String title = result.getString("title").replaceAll("[\t\n\r]+"," ").replaceAll("\\s+", " ");;
				    double popularity = (result.get("popularity").equals(null) ? -1.0 : result.getDouble("popularity"));
				    String overview = result.getString("overview").equals(null) ? "" : result.getString("overview").replaceAll("[\t\n\r]+"," ").replaceAll("\\s+", " ");
				    String release = result.get("release_date").equals(null) ? "" : result.getString("release_date");
				    String posterPath = result.get("poster_path").equals(null)? "" : result.getString("poster_path");
				    
				    Movie movie = new Movie(title,
				    						popularity,
				    						genreNames,
				    						overview,
				    						release,
				    						posterPath);
				    movieList.add(movie);
				    
				    String[] newRow = {"\"" + title + "\"", "\"" + release + "\"", "\"" + Double.toString(popularity) + "\"", "\"" + overview + "\""};
				    tabData.add(newRow);

				}
				
				timer.reset();
				timer.start();
				break;
				
				}catch(Exception e){
					TimeUnit.SECONDS.sleep(10);
					System.out.println(e.getClass() + e.getMessage());
					timeWaiting = timer.getTime()*0.001;
				}
			}
			
		}		
	
		System.out.println(movieList.size());
		System.out.println(tabData.size());
		CsvWriter.write(tabData, "//Users//christineibaraki//Desktop//movieList.csv");
		ReadWriteJSON.write(movieList, "//Users//christineibaraki//Desktop//movieList.json");
	}

}
