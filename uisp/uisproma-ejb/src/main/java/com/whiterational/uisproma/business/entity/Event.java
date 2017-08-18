package com.whiterational.uisproma.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Event implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3447420641246798652L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long               id;
  
  private String             name;
  
  @Size(max=5000)
  @Column(length = 5000)
  private String             description;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar           start;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar           end;
  
  private BigDecimal         price;
  
  private Integer            season;
  
  @ManyToOne
  private Curator            curator;
  
  @ManyToOne
  private Sport              sport;
  
  @Embedded
  private Address address = new Address();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public Calendar getStart() {
    return start;
  }

  public void setStart(Calendar start) {
    this.start = start;
  }

  public Calendar getEnd() {
    return end;
  }

  public void setEnd(Calendar end) {
    this.end = end;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Event other = (Event) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Curator getCurator() {
    return curator;
  }

  public void setCurator(Curator curator) {
    this.curator = curator;
  }

  public Sport getSport() {
    return sport;
  }

  public void setSport(Sport sport) {
    this.sport = sport;
  }
  
  @Transient
  public abstract String getType();

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Integer getSeason() {
    return season;
  }

  public void setSeason(Integer season) {
    this.season = season;
  }

}
