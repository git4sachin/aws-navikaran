package com.capgemini.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * 
 * @author abhandeg
 * @since 1.0
 */
@Component
public class SaveFilePathToProperties {

	private static Properties properties = null;
	private static BufferedInputStream STREAM = null;
	private static String PATH = ResourceFileLoader.getResourceFile("Navikaran.properties").getPath();

	private static void loadPropertiesFile() {
		properties = new Properties();
		try {
			STREAM = new BufferedInputStream(new FileInputStream(PATH));
			properties.load(STREAM);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				STREAM.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveFilePath(final String filePath) {
		loadPropertiesFile();
		properties.put("FilePath", filePath);
		FileOutputStream outputStrem;
		try {
			outputStrem = new FileOutputStream(PATH);
			properties.store(outputStrem, "This is a sample properties file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("FilePath saved to properties file......");
	}

	public static void saveFileName(final String fileName) {
		loadPropertiesFile();
		properties.put("FileName", fileName);
		FileOutputStream outputStrem;
		try {
			outputStrem = new FileOutputStream(PATH);
			properties.store(outputStrem, "This is a sample properties file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("FileName saved to properties file......");
	}

	public static String getFilePath() {
		loadPropertiesFile();
		return properties.getProperty("FilePath");
	}

	public static String getFileName() {
		loadPropertiesFile();
		return properties.getProperty("FileName");
	}

}
