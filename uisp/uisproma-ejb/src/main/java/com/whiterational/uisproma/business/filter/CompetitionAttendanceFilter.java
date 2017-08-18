package com.whiterational.uisproma.business.filter;

public class CompetitionAttendanceFilter {

  private String  uispCode;
  private String  surname;
  private String  solutionName;
  private Boolean paid;
  private Long    attache;
  private Long    club;
  private Long    competition;

  public String getUispCode() {
    return uispCode;
  }

  public void setUispCode(String uispCode) {
    this.uispCode = uispCode;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getSolutionName() {
    return solutionName;
  }

  public void setSolutionName(String solutionName) {
    this.solutionName = solutionName;
  }

  public Boolean getPaid() {
    return paid;
  }

  public void setPaid(Boolean paid) {
    this.paid = paid;
  }

  public Long getAttache() {
    return attache;
  }

  public void setAttache(Long attache) {
    this.attache = attache;
  }

  public Long getClub() {
    return club;
  }

  public void setClub(Long club) {
    this.club = club;
  }

  public Long getCompetition() {
    return competition;
  }

  public void setCompetition(Long competition) {
    this.competition = competition;
  }

}
