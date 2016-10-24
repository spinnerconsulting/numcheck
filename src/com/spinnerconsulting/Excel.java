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
	private String filePath = "";
	void runQueries() throws Exception {

		InputStream inp = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		XSSFSheet sheet = wb.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();
		//WebDriver wdTest = new WebDriver();

		// Traversing over each row of XLSX file
		//rowStart starts with 2, assuming 1st row is for the header info
		
		int rowStart= 2;
		//Max row numbers in Excel is 1048576 we will iterate over all of them. 
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
		        	  
		        	  String cellValue = NumberToTextConverter.toText(c.getNumericCellValue());
		        	  
		        	  if (wd.valueExists(cellValue) == true && Integer.parseInt(cellValue) % 2 == 1){
		        		  cell2.setCellValue("True and odd");
		        	  }else if (wd.valueExists(cellValue) == true && Integer.parseInt(cellValue) % 2 == 0){
		        		  cell2.setCellValue("True and even");
		        	  }else if (wd.valueExists(cellValue) == false && Integer.parseInt(cellValue) % 2 == 1){
		        		  cell2.setCellValue("False and odd");
		        	  }else if (wd.valueExists(cellValue) == false && Integer.parseInt(cellValue) % 2 == 0){
		        		  cell2.setCellValue("False and even");
		        	  }

					
		          } else if (c.getCellType() == Cell.CELL_TYPE_STRING){
		        	  Cell cell2 = r.createCell(cn+1);
		        	  
		        	  String cellValue = c.getStringCellValue().trim();
		        	  if (wd.valueExists(cellValue) == true){
		        		  cell2.setCellValue("True");
		        	  } else if (wd.valueExists(cellValue) == false){
		        		  cell2.setCellValue("False");
		        	  }
		        	  
		        			  
		        			  
		          } else if (c.getCellType() == Cell.CELL_TYPE_BLANK){
		        	  //do something if blank
		        	  continue;
		          } else if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN){
		        	  //do something if bool
		        	  continue;
		          } else if (c.getCellType() == Cell.CELL_TYPE_ERROR){
		        	  //do something if excell error cell
		        	  continue;
		          } else if (c.getCellType() == Cell.CELL_TYPE_FORMULA){
		        	  //do something if formula
		        	  continue;
		          }
		          
		          
		      
		       }
		    }
		

		inp.close();

		FileOutputStream os = new FileOutputStream(filePath);
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

	public void setFilePath(String path) {
		filePath = path;
		
	}

}
