package com.whiterational.uisproma.business.filter;

import java.util.List;

import com.whiterational.uisproma.business.entity.Athlete;

public class AthletePage {

  private List<Athlete> athletes;
  private Long          total;

  public AthletePage(List<Athlete> athletes, Long total) {
    super();
    this.athletes = athletes;
    this.total = total;
  }

  public List<Athlete> getAthletes() {
    return athletes;
  }

  public Long getTotal() {
    return total;
  }

}
