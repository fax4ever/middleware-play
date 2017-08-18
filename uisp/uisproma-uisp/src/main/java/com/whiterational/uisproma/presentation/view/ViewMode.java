package com.whiterational.uisproma.presentation.view;

public enum ViewMode {
  
  CREATE("create"), READ("read"), UPDATE("update"), DELETE("delete");
  
  private String text;
  
  private ViewMode(String text) {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
  
}
