package com.capgemini.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;
/**
 * Resource file loader class
 * @author abhandeg
 * @since 1.0
 *
 */
public class ResourceFileLoader {
/**
 * Returns the resource file from the given file name
 * @param fileName
 *  the file name
 * @return the file from resource folder
 */
	public static File getResourceFile(final String fileName) {

		File file = null;
		try {
			file = ResourceUtils.getFile("classpath:" + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}

}
