package com.capgemini.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON format class
 * 
 * @author asrathod
 * @since 1.0
 */
public class ConvertToJSONFormat {

	private Map<String, Integer> tokenTypeMap = new HashMap<>();

	/**
	 * Converts the array data into JSON format
	 * 
	 * @param outDataType the array of data type
	 * @return collection of data
	 */
	public Map<String, Integer> convertDataToJSON(Object[][] outDataType) {
		convertOutputDataToMap(outDataType);
		if (!tokenTypeMap.isEmpty()) {
			mapToJSON();
		}
		System.out.println(tokenTypeMap.toString());
		return tokenTypeMap;
	}

	/**
	 * Converts map object into JSON format
	 */
	private void mapToJSON() {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String jsonString = objectMapper.writeValueAsString(tokenTypeMap);
			System.out.println("JSON Data :: " + jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Convert the array of collection data into Map
	 * 
	 * @param outDataType the out data type
	 */
	private void convertOutputDataToMap(Object[][] outDataType) {
		for (Object[] mapping : outDataType) {
			if (tokenTypeMap.isEmpty() || !tokenTypeMap.containsKey(String.valueOf(mapping[1]))) {
				tokenTypeMap.put(String.valueOf(mapping[1]), 1);
			} else {
				tokenTypeMap.put(String.valueOf(mapping[1]), tokenTypeMap.get(String.valueOf(mapping[1])) + 1);
			}
		}
	}
}
