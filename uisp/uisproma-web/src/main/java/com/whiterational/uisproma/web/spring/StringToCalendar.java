package com.whiterational.uisproma.web.spring;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToCalendar implements Converter<String, Calendar> {
	
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Calendar convert(String value) {
		Date date = null;
		
		try {
			date = format.parse(value);
		} catch (ParseException e) {
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

}
