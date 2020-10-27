package com.capgemini.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Resource file loader class
 * 
 * @author abhandeg
 * @since 1.0
 *
 */
public class ResourceFileLoader {

	private static Object getClassLoader() {
		ResourceFileLoader resourceFileLoader = new ResourceFileLoader();
		return resourceFileLoader.getClass().getClassLoader();
	}

	/**
	 * Returns the resource file from the given file name
	 * 
	 * @param fileName the file name
	 * @return the file from resource folder
	 */
//	public static File getResourceFile(final String fileName) {
//
//		File file = null;
//		try {
//			file = ResourceUtils.getFile("classpath:" + fileName);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		return file;
//	}

	public static File getResourceFile(final String fileName) {

		ClassLoader classLoader = (ClassLoader) getClassLoader();

		String filePath = null;
		try {
			filePath = Paths.get(classLoader.getResource(fileName).toURI()).normalize().toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new File(filePath);
	}
}
