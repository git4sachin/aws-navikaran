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

	
	

	/**
	 * Creates the output data file. usually returns an excel file with review
	 * comments and category.
	 * 
	 * @throws Exception
	 */
	public static Object[][] createOutputDataFile() throws Exception {

		final String WRITE_FILE_NAME = FileSystems.getDefault().getPath("download", "ReviewCommentOutput.xlsx")
				.normalize().toAbsolutePath().toString();
		final String INPUT_FILE_PATH = Paths
				.get(SaveFilePathToProperties.getFilePath(), SaveFilePathToProperties.getFileName()).toAbsolutePath()
				.toString();

		Object[][] datatypes = ReadAndWriteToExcel.getDataFromFile(INPUT_FILE_PATH);		
		final Object[][] outDataType = new Object[datatypes.length][datatypes.length];

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
		
		return outDataType;
	}

	/**
	 * Gets the collection of data into JSON format for Eg.
	 * {[count:11,category:javadoc],[count:10,catgory:junit]}
	 * 
	 * @return a collection of data
	 */
	public static Map<String, Integer> getJSONConversion() {
		
		Object[][] outDataType = null;
		try {
			outDataType = createOutputDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ConvertToJSONFormat().convertDataToJSON(outDataType);
	}

}