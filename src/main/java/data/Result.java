package data;

import java.util.List;
import java.util.Map;

import util.MapUtils;

public class Result {
	
	private Map<String, Map<String, Integer>> queryLevelEntityMap;
	private Map<String, Integer> queryLevelTokenMap;
	
	private List<Movie> movieList;

	public Result(List<Movie> movieList, Map<String, Map<String, Integer>> queryEntityMap, Map<String, Integer> queryTokenMap){
		this.movieList = movieList;
		this.queryLevelEntityMap = queryEntityMap;
		for(String key : queryLevelEntityMap.keySet()){
			Map<String, Integer> innerMap = queryLevelEntityMap.get(key);
			queryLevelEntityMap.put(key, MapUtils.sortMapDescending(innerMap));
		}
		this.queryLevelTokenMap = MapUtils.sortMapDescending(queryTokenMap);
	}

}
