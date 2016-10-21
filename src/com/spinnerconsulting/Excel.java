package com.spinnerconsulting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public static final int NO_RECORD_LIMIT = -1;

	private int maxRecords = NO_RECORD_LIMIT;
	private WebDriver wd;

	void runQueries() throws Exception {

		InputStream inp = new FileInputStream("extras/demo.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		XSSFSheet sheet = wb.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			// For each row, iterate through each columns
			Cell cell = row.getCell(2);

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				System.out.print(cell.getStringCellValue() + "\t");
				break;
			case Cell.CELL_TYPE_NUMERIC:
				String cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
				System.out.print(cellValue + "\t");
				
				Cell cell2 = row.createCell(3);
				cell2.setCellType(Cell.CELL_TYPE_BOOLEAN);
				cell2.setCellValue(wd.valueExists(cellValue));
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				System.out.print(cell.getBooleanCellValue() + "\t");
				break;
			default:
			}

			System.out.println("");

		}

		inp.close();

		FileOutputStream os = new FileOutputStream("extras/demo.xlsx");
		wb.write(os);
		os.close();
		wd.close();

	}

	public void setMaxRecords(int i) {
		maxRecords = i;
	}

	public void setWebDriver(WebDriver s) {
		wd = s;
	}

	public static void main(String[] args) throws Exception {
		Excel e = new Excel();
		e.runQueries();
	}

}
