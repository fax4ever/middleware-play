package com.whiterational.uisproma.presentation.faces;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ExcelProperty {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelProperty.class);
	
	@Inject
	private Properties          config;
	
	public Integer getIndexOf(String propName) {
		Object valueIndex = config.get(propName);
	    if (!(valueIndex instanceof String))
	      return null;
	    
	    int index = 0;
	    try {
	      index = Integer.parseInt((String) valueIndex);
	    } catch (NumberFormatException ex) {
	      return null;
	    }
	    
		return index;
	}
	
	public Cell getCell(Row row, String propName) {
		Integer index = getIndexOf(propName);
		if (index == null) {
			LOGGER.info("property name not found " + propName);
			return null;
		}
	    
	    return row.getCell(index);
	}
	
	public String getString(Row row, String propName) {
		Cell cell = getCell(row, propName);
	    if (cell == null) {
	      LOGGER.info(row + " : " + propName + " # null value");
	      return null;
	    }
	    
	    int cellType = cell.getCellType();
	    if (Cell.CELL_TYPE_NUMERIC == cellType) {
	    	int numericCellValue = (int) cell.getNumericCellValue();
	    	return numericCellValue + "";
	    }
	    
	    RichTextString rich = cell.getRichStringCellValue();
	    if (rich == null) {
	      LOGGER.info(row + " : " + propName + " # invalid value");
	      return null;
	    }
	     
	    return rich.getString();
	}
	
	public String getString(Row row, String... props) {
		StringBuilder builder = new StringBuilder();
		
		for (int i=0; i < props.length; i++) {
			String string = getString(row, props[i]);
			if (string == null)
				continue;
			
			builder.append(string);
			
			if (i < props.length - 1) {
				builder.append(" ");
			}
		}
		
		return builder.toString();
	}
	
	public Calendar getCalendar(Row row, String propName) {
		Cell cell = getCell(row, propName);
	    if (cell == null) {
	      LOGGER.info(row + " : " + propName + " # null value");
	      return null;
	    }
	    
	    int cellType = cell.getCellType();
	    if (Cell.CELL_TYPE_NUMERIC == cellType) {
	    	Date dateCellValue = cell.getDateCellValue();
	    	Calendar instance = Calendar.getInstance();
	    	instance.setTime(dateCellValue);
	    	return instance;
	    }
	    
	    RichTextString rich = cell.getRichStringCellValue();
	    if (rich == null) {
	      LOGGER.info(row + " : " + propName + " # invalid value");
	      return null;
	    }
	    
	    return CalendarConverter.getAsCalendar(rich.getString());
	}

}
