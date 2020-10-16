package com.capgemini.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.utils.SaveFilePathToProperties;

/**
 * Review comment categoriser controller
 * 
 * @author asrathod
 *
 */
@Controller
public class ReviewCommentCategoriserController {
	/**
	 * Navigates to reviews page
	 * 
	 * @param modelMap {@link ModelMap}
	 * @return the reviews page
	 */
	@RequestMapping(value = "/reviews")
	public ModelAndView getReviews(ModelMap modelMap) {
		ModelAndView model = new ModelAndView("reviews");
		return model;
	}

	/**
	 * Navigates to excel download page
	 * 
	 * @return excel page
	 */
	@RequestMapping(value = "/getExcel")
	public ResponseEntity<Resource> getFile() {
		String filename = "OutputFile.xlsx";
		// Create a FileInputStream object
		// for getting the information of the file
		FileInputStream fip = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			fip = new FileInputStream(SaveFilePathToProperties.getFilePath() + "\\WriteDataSet.xlsx");
			Workbook workbook = new XSSFWorkbook(fip);
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamResource fileOutput = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(fileOutput);
	}
}
