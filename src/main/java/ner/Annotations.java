package ner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class Annotations {
	
	private String PERSON = "PERSON";
	private String LOCATION = "LOCATION";
	private String ORGANIZATION = "ORGANIZATION";
	private String DATE = "DATE";
	
	private Map<String, Set<String>> entityMap = new HashMap<String, Set<String>>();
	
	public Annotations(String text){
		processText(text);
		
	}
	
	private Set<String> getTypeSet(String key){
		if(entityMap.containsKey(key)){
			return entityMap.get(key);
		}
		return null;
	}
	
	public Set<String> getPersonMap(){
		return getTypeSet(PERSON);
	}
	
	public Set<String> getLocationMap(){
		return getTypeSet(LOCATION);
	}
	
	public Set<String> getOrganizationMap(){
		return getTypeSet(ORGANIZATION);
	}
	
	public Set<String> getDateMap(){
		return getTypeSet(DATE);
	}
	
	public Map<String, Set<String>> getEntityMap(){
		return this.entityMap;
	}
	
	private void processText(String text){
		Annotation annotation = new Annotation(text);
		StanfordPipeline pipeline = StanfordPipeline.getStanfordPipeline();
		pipeline.annotate(annotation);
		
		List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
		
		for(CoreMap sentence : sentences){
			
			for(CoreMap entityMention : sentence.get(CoreAnnotations.MentionsAnnotation.class)){
				
				String word = entityMention.get(TextAnnotation.class).toUpperCase();
				String ne = entityMention.get(NamedEntityTagAnnotation.class);
				
				if(entityMap.containsKey(ne)){
					Set<String> wordSet = entityMap.get(ne);
					wordSet.add(word);
					entityMap.put(ne, wordSet);
				}else{
					Set<String> wordSet = new HashSet<String>();
					wordSet.add(word);
					entityMap.put(ne, wordSet);
				}
			}
			
		}
	}

}
