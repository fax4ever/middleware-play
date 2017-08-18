package com.whiterational.uisproma.business.entity.competition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Solution implements Serializable, Comparable<Solution> {
  
  /**
   * 
   */
  private static final long serialVersionUID = 2584998427237855503L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String name;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar deadline;
  
  private BigDecimal price;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    Solution other = (Solution) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public BigDecimal getPrice() {
    return price;
  }
  
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Calendar getDeadline() {
    return deadline;
  }

  public void setDeadline(Calendar deadline) {
    this.deadline = deadline;
  }

  @Override
  public String toString() {
    return name + " " + price;
  }

  @Override
  public int compareTo(Solution o) {
    if (price == null)
      return -1;
    
    if (o.price == null)
      return 1;
    
    return price.compareTo(o.getPrice());
  }
  
}
