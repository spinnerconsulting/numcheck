package com.spinnerconsulting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	/**
	 * Value when no record limit is specified by the controller.
	 */
	public static final int NO_RECORD_LIMIT = -1;

	/**
	 * The maximum number of records configured to iterate over the rows.
	 */
	private int maxRecords = NO_RECORD_LIMIT;

	/**
	 * The WebDriver object doing the web interactions.
	 */
	private WebClient client;

	/**
	 * Controller set file path where the xlsx file resides.
	 */
	private String filePath;

	/**
	 * Executes the queries listed in the Excel file set in filePath.
	 * 
	 * @throws Exception
	 */
	void runQueries() throws Exception {

		InputStream inp = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		XSSFSheet sheet = wb.getSheetAt(0);

		int rowEnd = (maxRecords == NO_RECORD_LIMIT ? sheet.getLastRowNum()
				: Math.min(maxRecords, sheet.getLastRowNum()));
		// Excel rows start at 1, but POI starts at 0.
		// Slight adjustments to account for this.
		rowEnd++;

		for (int rowNum = 1; rowNum < rowEnd; rowNum++) {

			Row r = sheet.getRow(rowNum);

			if (r == null) {
				continue;
			}

			// always column C in the spreadsheet
			int cn = 2;
			Cell c = r.getCell(cn);
			if (c == null) {
				continue;
			}

			String cellValue;
			if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cellValue = NumberToTextConverter.toText(c.getNumericCellValue());
			} else if (c.getCellType() == Cell.CELL_TYPE_STRING) {
				cellValue = c.getStringCellValue().trim();
			} else {
				cellValue = null;
			}

			if (cellValue != null) {
				Cell cell2 = r.createCell(cn + 1);
				cell2.setCellValue((client.valueExists(cellValue) ? "Yes" : "No"));
			}

		}

		inp.close();

		FileOutputStream os = new FileOutputStream(filePath);
		wb.write(os);
		os.close();
		client.close();

	}

	void setMaxRecords(int i) {
		maxRecords = i;
	}

	void setWebClient(WebClient s) {
		client = s;
	}

	void setFilePath(String path) {
		filePath = path;
	}

}
