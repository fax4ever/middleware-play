package com.whiterational.uisproma.presentation.faces;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@ApplicationScoped
public class SelectItemProducer {
  
  private SelectItem[] getPage(int start, int end) {
    SelectItem[] items = new SelectItem[end - start + 1];
    for (int i = 0; i < end - start + 1; i++) {
      items[i] = new SelectItem(start + i);
    }
    return items;
  }
  
  private SelectItem[] getLinear(int start, int end) {
    SelectItem[] items = new SelectItem[end - start + 2];
    items[0] = new SelectItem(0, "");
    for (int i = 0; i < end - start + 1; i++) {
      items[i+1] = new SelectItem(start + i, start + i + "");
    }
    return items;
  }

  public SelectItem[] produce(int start, int end, int max) {
    return (end < max) ? getPage(start, end) : getPage(start, max);
  }

  public SelectItem[] getPaid() {
    SelectItem[] items = new SelectItem[3];
    items[0] = new SelectItem(null, "");
    items[1] = new SelectItem(Boolean.TRUE, "Si");
    items[2] = new SelectItem(Boolean.FALSE, "No");

    return items;
  }
  
  public SelectItem[] getStepsNumber() {
    return this.getLinear(1, 5);
  }

}
