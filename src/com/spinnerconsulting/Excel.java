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
		WebDriver wdTest = new WebDriver();

		// Traversing over each row of XLSX file
		//rowStart starts with 2, assuming 1st row is for the header info
		
		int rowStart= 2;
		//Max row numbers in Excel is 65536 we will iterate over all of them. 
		//Traditional for loop is used, instead of rowItearator that skips nulls and other values
		
		int rowEnd  = Math.max(1048576, sheet.getLastRowNum());
		int i = 0;
		for (int rowNum = 0; rowNum < rowEnd; rowNum++) {
			   
		       Row r = sheet.getRow(rowNum);
		       
		       if (r == null) {
		          // This whole row is empty
		          continue;
		       }
		       
		       int lastColumn = Math.max(r.getLastCellNum(), 5);

		       for (int cn = 0; cn < lastColumn; cn++) {
		    	  
		          Cell c = r.getCell(cn);
		          if (c == null) {
		             // The spreadsheet is empty in this cell
		          }  else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC){
		        	  
		        	  Cell cell2 = r.createCell(cn+1);
		        	  cell2.setCellValue("True");


					
		          } else if (c.getCellType() == Cell.CELL_TYPE_STRING){
		        	 
		        	  
		        			  
		        			  
		          } else if (c.getCellType() == Cell.CELL_TYPE_BLANK){
		        	  continue;
		          } else if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN){
		        	  continue;
		          } else if (c.getCellType() == Cell.CELL_TYPE_ERROR){
		        	  continue;
		          } else if (c.getCellType() == Cell.CELL_TYPE_FORMULA){
		        	  continue;
		          }
		          
		          
		      
		       }
		    }
		

		inp.close();

		FileOutputStream os = new FileOutputStream("extras/demo.xlsx");
		wb.write(os);
		os.close();		
		//wd.close();

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
