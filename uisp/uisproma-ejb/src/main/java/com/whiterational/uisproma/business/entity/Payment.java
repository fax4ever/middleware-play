package com.whiterational.uisproma.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.CHAR)
public abstract class Payment implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3748505308397150221L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long               id;
  
  @OneToMany(mappedBy = "payment")
  private List<Fee>          fees = new ArrayList<Fee>();
  
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar moment;
  
  @Transient
  public abstract String getType();
  
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
    Payment other = (Payment) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public List<Fee> getFees() {
    return fees;
  }

  public void setFees(List<Fee> fees) {
    this.fees = fees;
  }

  public Calendar getMoment() {
    return moment;
  }

  public void setMoment(Calendar moment) {
    this.moment = moment;
  }
  
  @Transient
  public BigDecimal getTotal() {
    BigDecimal result = new BigDecimal(0);
    
    for (Fee fee : fees) {
      result = result.add(fee.getPrice());
    }
    
    return result;
  }

  @Override
  public String toString() {
    return moment + " " + getTotal();
  }

}
