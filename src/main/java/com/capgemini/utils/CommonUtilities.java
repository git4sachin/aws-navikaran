package com.capgemini.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.capgemini.dao.ReviewComment;
/**
 * Common utility class
 * @author asrathod
 *@since 1.0
 */
public class CommonUtilities {
	
	/**
	 * Read the file and convert each word into lower case
	 * 
	 * @param fileName the file to be read
	 * @return file which contains all lower case data
	 */
	public static File getFileCharsConvertToLowerCase(String fileName) {
		try {
			String newFileName = fileName.substring(0, fileName.indexOf(".") - 1) + "_formated."
					+ fileName.substring(fileName.indexOf(".") + 1);
			File file2 = new File(newFileName);

			BufferedReader in = (new BufferedReader(new FileReader(fileName)));
			PrintWriter out = (new PrintWriter(new FileWriter(file2)));
			int ch;
			while ((ch = in.read()) != -1) {
				if (Character.isUpperCase(ch)) {
					ch = Character.toLowerCase(ch);// convert assign variable
				}
				out.write(ch);
			}

			in.close();
			out.close();

			return file2;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Read the file and remove all stop words for eg. a, any, this , that etc.
	 * 
	 * @param sampleFile file to be read
	 * @return file without stop words
	 */
	public static File removeStopWordsFromFile(File sampleFile) {

		String testText, stopWords = "";

		testText = readFileToString(sampleFile.getAbsolutePath());
		stopWords = readFileToString(ResourceFileLoader.getResourceFile("nlp_en_stop_words.txt").getAbsolutePath());

		String[] stopWordsSet = stopWords.split("\n"); // new String[]{"a", "able", "about", "above", "according",
														// "accordingly", "across", "actually", "after", "afterwards",
														// "again", "against", "all"};
		String stopWordsPattern = String.join("|", stopWordsSet);
		Pattern pattern = Pattern.compile("\\b(?:" + stopWordsPattern + ")\\b\\s*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(testText);
		testText = matcher.replaceAll("");
		writeToFile(testText, sampleFile);
		return sampleFile;

	}
/**
     * Read the sample statement and remove all stop words for eg. a, any, this , that etc.
	 * 
	 * @param sampleString sample String to be read
	 * @return String without stop words
 */
	public static String removeStopWordsFromString(String sampleString) {

		String stopWords = "";

		stopWords = readFileToString(ResourceFileLoader.getResourceFile("nlp_en_stop_words.txt").getAbsolutePath());

		String[] stopWordsSet = stopWords.split("\n"); // new String[]{"a", "able", "about", "above", "according",
														// "accordingly", "across", "actually", "after", "afterwards",
														// "again", "against", "all"};
		String stopWordsPattern = String.join("|", stopWordsSet);
		Pattern pattern = Pattern.compile("\\b(?:" + stopWordsPattern + ")\\b\\s*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sampleString);
		sampleString = matcher.replaceAll("");

		return sampleString;

	}
/**
 * Read the file and return file content in text
 * @param sampleFileName
 *  the sample file name
 * @return String text
 */
	public static String readFileToString(String sampleFileName) {
		try {
			String testText = "";
			BufferedReader br = new BufferedReader(new FileReader(sampleFileName));

			int val;
			while ((val = br.read()) != -1) {
				testText += (char) val;
			}
			
			br.close();
			return testText;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
/**
 * Write the data into given file
 * @param data
 * the data
 * @param sampleFile
 * the sample file
 */
	public static void writeToFile(String data, File sampleFile) {
		try {
			PrintWriter out = (new PrintWriter(new FileWriter(sampleFile)));
			out.write(data);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Removes the special characters from the given file.
 * for eg. !#$%&'()*
 * @param sampleFile
 * the sample file
 * @return the file without special characters
 */
	public static File removeSpecialCharsFromFile(File sampleFile) {

		String removedSpecialChars = StringUtils.stripAccents(readFileToString(sampleFile.getAbsolutePath()));

		String specialCharacters = "!#$%&'()*+,-/:;<=>?@[]^_`{|}";

		for (int i = 0; i < removedSpecialChars.length(); i++) {

			if (specialCharacters.contains(Character.toString(removedSpecialChars.charAt(i)))) {
				removedSpecialChars = removedSpecialChars.replace(removedSpecialChars.charAt(i), ' ');
			}
		}
	

		writeToFile(removedSpecialChars, sampleFile);
		return sampleFile;
	}
/**
 * Removes the special characters from sample text
 * @param sampleString
 * the sample text
 * @return text without special characters
 */
	public static String removeSpecialCharsFromString(String sampleString) {

		String specialCharacters = "!#$%&'()*+,-/:;<=>?@[]^_`{|}";

		for (int i = 0; i < sampleString.length(); i++) {

			if (specialCharacters.contains(Character.toString(sampleString.charAt(i)))) {
				sampleString = sampleString.replace(sampleString.charAt(i), ' ');
			}
		}
		
		return sampleString;
	}
/**
 * Converts the given Map into json object
 * @param dataMap
 * collection of data
 * @return json object
 */
	public static List<ReviewComment> convertMapIntoJSonObject(final Map<String, Integer> dataMap) {
		List<ReviewComment> reviewCommentList = new ArrayList<ReviewComment>();
		for (Entry<String, Integer> reviewComment : dataMap.entrySet()) {
			if (!reviewComment.getKey().equals("null")) {
				reviewCommentList.add(new ReviewComment(reviewComment.getKey(), reviewComment.getValue()));
			}
		}
		return reviewCommentList;
	}
}
