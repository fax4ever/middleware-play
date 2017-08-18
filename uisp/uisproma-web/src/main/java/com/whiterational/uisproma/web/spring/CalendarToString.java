package com.whiterational.uisproma.web.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.core.convert.converter.Converter;

public class CalendarToString implements Converter<Calendar, String> {

	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public String convert(Calendar value) {
		return format.format(value.getTime());
	}

}
