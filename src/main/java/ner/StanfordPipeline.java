package ner;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class StanfordPipeline {
	
	private static StanfordPipeline instance;
	private StanfordCoreNLP pipeline;
	
	private StanfordPipeline() {
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, entitymentions");
		this.pipeline = new StanfordCoreNLP(properties);
	}
	
	public static StanfordPipeline getStanfordPipeline() {
		if(instance == null){
			synchronized(StanfordPipeline.class){
				if(instance == null){
					instance = new StanfordPipeline();
				}
			}
		}
		
		return instance;
	}
	
	public void annotate(Annotation document){
		pipeline.annotate(document);
	}

}
