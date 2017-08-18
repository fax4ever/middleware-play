package com.whiterational.uisproma.presentation.faces;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("calendar")
public class CalendarConverter implements Converter {

  private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

  @Override
  public Object getAsObject(FacesContext ctx, UIComponent comp, String val) {
    return getAsCalendar(val);
  }

  @Override
  public String getAsString(FacesContext ctx, UIComponent comp, Object cal) {
    return (cal instanceof Calendar) ? getAsDisplay((Calendar)cal) : null;
  }
  
  public static Calendar getAsCalendar(String val) {
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(df.parse(val));
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
    return calendar;
  }
  
  public static String getAsDisplay(Calendar cal) {
    return df.format(cal.getTime());
  }

}
