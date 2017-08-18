package com.whiterational.uisproma.business.entity;

import java.io.Serializable;
import java.util.Calendar;

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
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Attendance implements Serializable, Comparable<Attendance> {

	/**
   * 
   */
	private static final long serialVersionUID = 7567942586555091766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@NotNull
	private Attache attache;

	@ManyToOne
	@NotNull
	protected Athlete athlete;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar moment = Calendar.getInstance();

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
		result = prime * result + ((athlete == null) ? 0 : athlete.hashCode());
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
		Attendance other = (Attendance) obj;
		if (athlete == null) {
			if (other.athlete != null)
				return false;
		} else if (!athlete.equals(other.athlete))
			return false;
		return true;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	public Calendar getMoment() {
		return moment;
	}

	public void setMoment(Calendar moment) {
		this.moment = moment;
	}

	public Attache getAttache() {
		return attache;
	}

	public void setAttache(Attache attache) {
		this.attache = attache;
	}

	@Transient
	public abstract Event getEvent();

	@Transient
	public abstract String getType();

	@Override
	public int compareTo(Attendance o) {
		int result;

		if (athlete != null) {
			result = athlete.compareTo(o.getAthlete());
			if (result != 0)
				return result;
		}

		return 0;
	}
	
}
