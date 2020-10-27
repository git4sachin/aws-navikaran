package com.capgemini.navikaran;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.capgemini.utils.CommonUtilities;
import com.capgemini.utils.ConvertToJSONFormat;
import com.capgemini.utils.ReadAndWriteToExcel;
import com.capgemini.utils.SaveFilePathToProperties;

import opennlp.tools.doccat.DoccatModel;

/**
 * Represents the review comment categorizer
 * 
 * @author abhandeg
 * @since 1.0
 */
@Component
public class ReviewCommentsCategorizer {

	private static final String WRITE_FILE_NAME = FileSystems.getDefault().getPath("download", "ReviewCommentOutput.xlsx")
			.normalize().toAbsolutePath().toString();
	private static final ConvertToJSONFormat CONVERT_TO_JSON_FORMAT = new ConvertToJSONFormat();
	private static final Object[][] outDataType = new Object[260][260];

	/**
	 * Creates the output data file. usually returns an excel file with review
	 * comments and category.
	 * 
	 * @throws Exception
	 */
	public static void createOutputDataFile() throws Exception {

		final String INPUT_FILE_PATH = Paths
				.get(SaveFilePathToProperties.getFilePath(), SaveFilePathToProperties.getFileName()).toAbsolutePath()
				.toString();
		System.out.println("Input File Path: " + INPUT_FILE_PATH);
		System.out.println("WRITE_FILE_NAME: " + WRITE_FILE_NAME);

		Object[][] datatypes = ReadAndWriteToExcel.getDataFromFile(INPUT_FILE_PATH);

		int rowNum = 0;
		DoccatModel model = CategorizeDocuments.prepareModel();
		String category = null;
		for (Object[] objects : datatypes) {
			int colNum = 0;
			for (Object object : objects) {
				String value = (String) object;
				if (value != null) {
					String sampleDocument = value.toLowerCase();
					sampleDocument = CommonUtilities.removeSpecialCharsFromString(sampleDocument);
					sampleDocument = CommonUtilities.removeStopWordsFromString(sampleDocument);
					category = CategorizeDocuments.getCaregories(model, sampleDocument);
					if (colNum == 0)
						outDataType[rowNum][colNum] = value;
					colNum++;
				} else {
					break;
				}
			}
			outDataType[rowNum][colNum] = category;
			rowNum++;
		}

		ReadAndWriteToExcel.setDataToFile(WRITE_FILE_NAME, outDataType);
	}

	/**
	 * Gets the collection of data into JSON format for Eg.
	 * {[count:11,category:javadoc],[count:10,catgory:junit]}
	 * 
	 * @return a collection of data
	 */
	public static Map<String, Integer> getJSONConversion() {
		try {
			createOutputDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CONVERT_TO_JSON_FORMAT.convertDataToJSON(outDataType);
	}

}