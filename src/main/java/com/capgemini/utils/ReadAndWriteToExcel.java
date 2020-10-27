package com.capgemini.utils;

/**
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Read write to excel file utility class
 * 
 * @author abhandeg
 * @since 1.0
 */
public class ReadAndWriteToExcel {

	/**
	 * Gets the data from input file
	 * 
	 * @param inputFileName the input file name
	 */
	@SuppressWarnings("resource")
	public static Object[][] getDataFromFile(final String inputFileName) {
		Cell currentCell = null;
		Object[][] datatypes = new Object[260][1];
		try {
			// Reading from a excel file
			FileInputStream excelFile = new FileInputStream(new File(inputFileName));
			Workbook inputWorkbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = inputWorkbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowNum = 0;
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				int colNum = 0;
				while (cellIterator.hasNext()) {
					currentCell = cellIterator.next();
					if (currentCell.getCellType() == CellType.STRING) {
						datatypes[rowNum][colNum] = currentCell.getStringCellValue();
					} else if (currentCell.getCellType() == CellType.NUMERIC) {
						datatypes[rowNum][colNum] = currentCell.getNumericCellValue();
					}
					colNum++;
				}
				rowNum++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datatypes;
	}

	/**
	 * Sets the data into excel file
	 * 
	 * @param outputFileName the output file
	 */
	public static void setDataToFile(final String outputFileName, final Object[][] datatypes) {
		Cell currentCell;
		XSSFWorkbook outputworkbook = new XSSFWorkbook();
		XSSFSheet sheet = outputworkbook.createSheet("Categories of Review's");
		int rowNum = 0;
		System.out.println("Creating excel from : "+ outputFileName);
		for (Object[] datatype : datatypes) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				currentCell = row.createCell(colNum++);
				if (field instanceof String) {
					currentCell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					currentCell.setCellValue((Integer) field);
				}
			}
		}
		try {		
			
			File file = new File(outputFileName);
			if(!file.exists())
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			outputworkbook.write(outputStream);
			outputworkbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
