package com.whiterational.uisproma.business.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.whiterational.uisproma.business.entity.competition.CompetitionFee;

@Entity
@DiscriminatorValue("A")
public class AttachePayment extends Payment {

	/**
   * 
   */
	private static final long serialVersionUID = -6479331446165779381L;

	private Attache attache;

	@Override
	@Transient
	public String getType() {
		return "Payment made by Attache";
	}

	public Attache getAttache() {
		return attache;
	}

	public void setAttache(Attache attache) {
		this.attache = attache;
	}

	@Override
	public String toString() {
		return getType() + " " + attache + ", Amount: " + getTotal();
	}

	public void addFees(List<CompetitionFee> fees) {
		

	}

}
