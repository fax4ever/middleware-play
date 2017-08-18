package com.whiterational.uisproma.business.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("C")
public class CuratorPayment extends Payment {

  /**
   * 
   */
  private static final long serialVersionUID = -6035997225878939605L;
  
  private Curator curator;

  @Override
  @Transient
  public String getType() {
    return "Payment made by Curator";
  }

  public Curator getCurator() {
    return curator;
  }

  public void setCurator(Curator curator) {
    this.curator = curator;
  }

  @Override
  public String toString() {
    return getType() + " " + curator + ", Amount: " + getTotal();
  }

}
