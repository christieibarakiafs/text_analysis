package Geocoding;
import java.util.HashSet;
import java.util.Set;

public class Planets {

	private static Planets instance;
	private Set<String> planets;

	private Planets(){
		this.planets = new HashSet<String>();

		this.planets.add("mercury");
		this.planets.add("venus");
		this.planets.add("earth");
		this.planets.add("mars");
		this.planets.add("jupiter");
		this.planets.add("saturn");
		this.planets.add("uranus");
		this.planets.add("neptune");
		this.planets.add("ceres");
		this.planets.add("pluto");
		this.planets.add("haumea");
		this.planets.add("makemake");
		this.planets.add("eris");
		
		this.planets.add("sun");
		this.planets.add("moon");
	}
	
	public boolean isPlanet(String location){
		return this.planets.contains(location.toLowerCase());
	}
	
	public static Planets get(){
		if(instance == null){
			synchronized(Planets.class){
				if(instance == null){
					instance = new Planets();
				}
			}
		}
		return instance;
	}

}
