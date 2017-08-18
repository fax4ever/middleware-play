package com.whiterational.uisproma.presentation.crud;

import java.util.Properties;

import javax.inject.Inject;

public abstract class ApplicationCRUD {
  
  protected int pageSize;
  protected int windowSize;
  protected int fastSize;
  
  protected Integer mark;
  protected int totalMark;
  protected int windowMark;
  
  @Inject
  private Properties          config;
  
  public void init() {
    String pageSizeString = (String) config.get("athlete.pageSize");
    pageSize = Integer.parseInt(pageSizeString);
    String windowSizeString = (String) config.get("athlete.windowSize");
    windowSize = Integer.parseInt(windowSizeString);
    String fastSizeString = (String) config.get("athlete.fastSize");
    fastSize = Integer.parseInt(fastSizeString);
  }
  
  public void first() {
    if (mark == 1)
      return;
    
    mark = 1;
    windowMark = 1;
    change();
  }
  
  public void fastRewind() {
    if (mark == 1)
      return;
    
    if (mark <= fastSize)
      mark = 1;
    else
      mark -= fastSize;
    
    if (windowMark > mark)
      windowMark = mark;
    
    change();
  }
  
  public void previous() {
    if (mark == 1)
      return;
    
    if (mark == windowMark)
      windowMark--;
    
    mark--;
    change();
  }
  
  public void next() {
    if (mark == totalMark)
      return;
    
    if (mark == windowMark+windowSize-1)
      windowMark++;
    
    mark++;
    change();
  }
  
  public void fastForward() {
    if (mark == totalMark)
      return;
    
    if (mark+fastSize > totalMark)
      mark = totalMark;
    else
      mark += fastSize;
    
    if (windowMark <= mark-fastSize)
      windowMark = mark-fastSize+1;
    
    change();
  }
  
  public void last() {
    if (mark == totalMark)
      return;
    
    mark = totalMark;
    windowMark = (totalMark < windowSize) ? 1 : totalMark - windowSize + 1;
    change();
  }

  public abstract void change();

}
