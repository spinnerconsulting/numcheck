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
	public static final int NO_ROW_LIMIT = -1;

	/**
	 * The maximum number of records configured to iterate over the rows.
	 */
	private int maxRows = NO_ROW_LIMIT;

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

		// maxRows begins at 1. getLastRowNum begins at 0
		// reconcile rowEnd to begin at 0
		int rowEnd = (maxRows == NO_ROW_LIMIT ? sheet.getLastRowNum() : Math.min(maxRows - 1, sheet.getLastRowNum()));

		for (int rowNum = 0; rowNum <= rowEnd; rowNum++) {

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
				try {
					cell2.setCellValue((client.valueExists(cellValue) ? "Yes" : "No"));
				} catch (Exception e) {
					cell2.setCellValue("error");
					e.printStackTrace();
					continue;
				}

			}

		}

		inp.close();

		FileOutputStream os = new FileOutputStream(filePath);
		wb.write(os);
		os.close();
		client.close();

	}

	void setMaxRows(int i) {
		maxRows = i;
	}

	void setWebClient(WebClient s) {
		client = s;
	}

	void setFilePath(String path) {
		filePath = path;
	}

}
