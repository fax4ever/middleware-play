package com.whiterational.uisproma.business.filter;

public class AthleteFilter {

  private Long    club = 0l;
  private String  surname;
  private String  name;
  private String  uispCode;
  private Integer year = 0;

  public Long getClub() {
    return club;
  }

  public void setClub(Long club) {
    this.club = club;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUispCode() {
    return uispCode;
  }

  public void setUispCode(String uispCode) {
    this.uispCode = uispCode;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return "AthleteFilter [club=" + club + ", surname=" + surname + ", name=" + name + ", uispCode=" + uispCode + ", year=" + year
        + "]";
  }

}
