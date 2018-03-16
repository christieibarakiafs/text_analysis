package string;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import data.StopwordList;

public class Tokenizer {
	
	public static List<String> getNGrams(String text, int n) throws FileNotFoundException{
		
		text = text.replaceAll("[^A-Za-z0-9]", " ").replaceAll("\\s+", " ").trim();
		List<String> tokens = new ArrayList<String>();
		tokens.addAll(Arrays.asList(text.split(" ")));
		tokens = StopwordList.getList().removeStopWords(tokens);
		if(n==1){
			return tokens;
		}
		
		List<String> nGrams = new ArrayList<String>();
		for(int i = 0 ; i < tokens.size(); i++){
			if(i <= (tokens.size()-n)){
				String newNGram = tokens.subList(i, i+n).stream().collect(Collectors.joining(" "));
				nGrams.add(newNGram);
			}else{
				break;
			}
		}
		
		return nGrams;
	}
	
	public static List<String> getNGrams(List<String> text, int n) throws FileNotFoundException{
		String textString = text.stream().collect(Collectors.joining(" "));
		return getNGrams(textString, n);
	}

}
