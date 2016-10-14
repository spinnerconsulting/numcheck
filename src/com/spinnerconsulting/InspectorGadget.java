package com.spinnerconsulting;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InspectorGadget {

	public static void main(String[] args) throws Exception {
		System.out.println("===Start===");
		InspectorGadget inspectorGadget = new InspectorGadget();
		inspectorGadget.doIt();
		System.out.println("===End=====");

	}

	private void doIt() throws Exception {

		InputStream inp = new FileInputStream("extras/demo.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		XSSFSheet sheet = wb.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cell = row.getCell(2);

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				System.out.print(cell.getStringCellValue() + "\t");
				break;
			case Cell.CELL_TYPE_NUMERIC:
				System.out.print(NumberToTextConverter.toText(cell.getNumericCellValue()) + "\t");
				// System.out.print(cell.getNumericCellValue() + "\t");
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				System.out.print(cell.getBooleanCellValue() + "\t");
				break;
			default:
			}
			
			System.out.println("");

		}

		wb.close();
	}

}
