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
		
			boolean value;
			
			if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				
				String cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
				Cell cell2 = row.createCell(3);
				cell2.setCellValue("True");
				
				//System.out.println("CellValue " + cellValue + "\t");
				//System.out.println(wd.valueExists("222"));
				//value is null, cant figure out why.
				//value = wd.valueExists(cellValue);
				
			}else if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING && cell.toString() != "Number"){
				String cellValue = (cell.getStringCellValue());
				Cell cell2 = row.createCell(3);
				cell2.setCellValue("True");
			}else if(cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
				boolean cellValue =(cell.getBooleanCellValue());
				Cell cell2 = row.createCell(3);
				cell2.setCellValue("True");
			}else if(cell != null && cell.getCellType() == Cell.CELL_TYPE_BLANK){
				System.out.println("BLANK VALUE");
			}else if(cell != null && cell.getCellType() == Cell.CELL_TYPE_ERROR){
				byte cellValue =(cell.getErrorCellValue());
				System.out.println(cellValue);
			}else if(cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA){
				System.out.println("Formula");
			}
			
			else{
				System.out.println(cell);
				
			}

			System.out.println("");
		
		}
		//
		
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
