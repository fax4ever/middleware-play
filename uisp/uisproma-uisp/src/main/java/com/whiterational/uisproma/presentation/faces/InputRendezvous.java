package com.whiterational.uisproma.presentation.faces;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("rendezvous")
public class InputRendezvous extends UIInput implements NamingContainer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InputRendezvous.class);
	
	private UIInput day = new UIInput();
	private UIInput hour = new UIInput();
	private UIInput minute = new UIInput();
	
	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		
		Calendar value = (Calendar) getValue();
		String pattern = (String) getAttributes().get("pattern");
		DateFormat df = new SimpleDateFormat(pattern);
		
		if (value == null)
			value = Calendar.getInstance();
		
		Date time = value.getTime();
		String format = df.format(time);
		
		day.setValue(format);
		hour.setValue(value.get(Calendar.HOUR_OF_DAY));
		
		final int minValue = value.get(Calendar.MINUTE);
		minute.setValue((minValue < 10) ? "0" + minValue : minValue);
	}

	@Override
	public Object getSubmittedValue() {
		
		Calendar value = (Calendar) getValue();
		if (value == null)
			value = Calendar.getInstance();
		
		String pattern = (String) getAttributes().get("pattern");
		DateFormat df = new SimpleDateFormat(pattern);
		
		String dayVal = (String) day.getSubmittedValue();
		if (dayVal != null) {
			try {
				Date parse = df.parse(dayVal);
				value.setTime(parse);
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
		}
		
		String hh = (String) hour.getSubmittedValue();
		String mm = (String) minute.getSubmittedValue();
		
		try {
			int h = Integer.parseInt(hh);
			value.set(Calendar.HOUR_OF_DAY, h);
			
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
		}
		
		try {
			int m = Integer.parseInt(mm);
			value.set(Calendar.MINUTE, m);
			
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
		}
		
		value.set(Calendar.SECOND, 0);
		value.set(Calendar.MILLISECOND, 0);
		
		return value;
	}

	public UIInput getDay() {
		return day;
	}

	public void setDay(UIInput day) {
		this.day = day;
	}

	public UIInput getHour() {
		return hour;
	}

	public void setHour(UIInput hour) {
		this.hour = hour;
	}

	public UIInput getMinute() {
		return minute;
	}

	public void setMinute(UIInput minute) {
		this.minute = minute;
	}

}
