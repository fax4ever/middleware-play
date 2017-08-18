package com.whiterational.uisproma.business.filter;

import java.util.List;

public abstract class ApplicationPage<T> {

  protected List<T> items;
  private Long    total;

  public ApplicationPage(List<T> items, Long total) {
    this.items = items;
    this.total = total;
  }

  public List<T> getItems() {
    return items;
  }

  public Long getTotal() {
    return total;
  }

}
