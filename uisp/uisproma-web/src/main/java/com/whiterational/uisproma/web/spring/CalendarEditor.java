package com.whiterational.uisproma.web.spring;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalendarEditor extends PropertyEditorSupport {
	
	private DateFormat format;
	
	public CalendarEditor(DateFormat format) {
		this.format = format;
	}

	public void setAsText(String value) {
		Date date = null;
		
		try {
			date = format.parse(value);
		} catch (ParseException e) {
			setValue(null);
			return;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		setValue(cal);
	}
	
	public String getAsText() {
		Calendar value = (Calendar) getValue();
		
		if (value == null)
			return "";
		
		return format.format(value.getTime());
		
	}

}
