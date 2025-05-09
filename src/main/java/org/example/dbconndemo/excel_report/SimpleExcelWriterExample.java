package org.example.dbconndemo.excel_report;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A very simple program that writes some data to an Excel file
 * using the Apache POI library.
 * @author www.codejava.net
 *
 */
public class SimpleExcelWriterExample {

	public static void createExcelReport(String Filename) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");

		Object[][] bookData = {
				{"Head First Java", "Kathy Serria", 79},
				{"Effective Java", "Joshua Bloch", 36},
				{"Clean Code", "Robert martin", 42},
				{"Thinking in Java", "Bruce Eckel", 35},
		};

		int rowCount = 0;

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(++rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(++columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}

		}


		try (FileOutputStream outputStream = new FileOutputStream(Filename)) {
			workbook.write(outputStream);
		}
    }

}
