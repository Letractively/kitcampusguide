package edu.kit.cm.kitcampusguide.data;

/**
 * 
 * 
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 *
 */
public interface LanguageLoader {
	
	/**
	 * Returns the translated string of a given expression by key and language id.
	 * 
	 * @param key key of the to be translated expression.
	 * @param lang id of the language to be translated in.
	 * @return translated string of the given expression.
	 */
	String getText(String key, Integer lang);


}
