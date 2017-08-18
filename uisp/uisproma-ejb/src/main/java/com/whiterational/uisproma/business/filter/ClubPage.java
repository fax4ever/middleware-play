package com.whiterational.uisproma.business.filter;

import java.util.List;

import com.whiterational.uisproma.business.entity.SportsClub;

public class ClubPage {

  private List<SportsClub> clubs;
  private Long             total;

  public ClubPage(List<SportsClub> clubs, Long total) {
    super();
    this.clubs = clubs;
    this.total = total;
  }

  public List<SportsClub> getClubs() {
    return clubs;
  }

  public Long getTotal() {
    return total;
  }

}
