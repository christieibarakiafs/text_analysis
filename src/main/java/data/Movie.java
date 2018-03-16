package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Geocoding.GeoException;
import Geocoding.Location;
import Geocoding.OpenStreetMapUtils;
import ner.Annotations;

public class Movie {
	
	private String title = "";
	private double popularity = -1;
	private List<String> genres;
	private String overview = "";
	private String releaseDate = "";
	private String posterUrl = "";
	
	private Set<String> personSet;
	private Set<String> dateSet;
	private Set<String> organizationSet;
	private Set<Location> locationSet;
	
	public Movie(String title,
				 double popularity,
				 List<String> genres,
				 String overview,
				 String releaseDate,
				 String posterUrl){
		this.title = title;
		this.popularity = popularity;
		this.genres = genres;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.posterUrl = posterUrl;
	}
	
	// Getters
	public String getTitle(){
		return this.title;
	}
	
	public String getOverview(){
		return this.overview;
	}
	
	public void setEntities(Annotations annotations) throws GeoException{
		this.personSet = annotations.getPersonMap();
		this.organizationSet = annotations.getOrganizationMap();
		this.dateSet = annotations.getDateMap();
		
		if(annotations.getLocationMap()!=null){
			locationSet = new HashSet<Location>();
			Set<String> locationStrings = annotations.getLocationMap();
			for(String locationString : locationStrings){
				Location location = OpenStreetMapUtils.getInstance().getCoordinates(locationString);
				locationSet.add(location);
			}
		}
	}
	

}
