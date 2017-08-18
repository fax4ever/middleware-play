package com.whiterational.uisproma.business.filter;

public class ChampionshipAttendanceFilter {

  private String  uispCode;
  private String  surname;
  private Integer stepsNumber;
  private Integer stepsPaid;
  private Boolean paidAll;
  private Long    attache;
  private Long    club;
  private Long    championship;

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

  public Long getChampionship() {
    return championship;
  }

  public void setChampionship(Long championship) {
    this.championship = championship;
  }

  public Integer getStepsNumber() {
    return stepsNumber;
  }

  public void setStepsNumber(Integer stepsNumber) {
    this.stepsNumber = stepsNumber;
  }

  public Integer getStepsPaid() {
    return stepsPaid;
  }

  public void setStepsPaid(Integer stepsPaid) {
    this.stepsPaid = stepsPaid;
  }

  public Boolean getPaidAll() {
    return paidAll;
  }

  public void setPaidAll(Boolean paidAll) {
    this.paidAll = paidAll;
  }

  @Override
  public String toString() {
    return "ChampionshipAttendanceFilter [uispCode=" + uispCode + ", surname=" + surname + ", stepsNumber=" + stepsNumber
        + ", stepsPaid=" + stepsPaid + ", paidAll=" + paidAll + ", attache=" + attache + ", club=" + club + ", championship="
        + championship + "]";
  }

}
