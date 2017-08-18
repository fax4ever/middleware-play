package com.whiterational.uisproma.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

@Entity
@XmlRootElement
@Cache(isolation=CacheIsolationType.ISOLATED)
public class Athlete extends Person implements Comparable<Athlete> {

	/**
   * 
   */
	private static final long serialVersionUID = -2930666735877455960L;

	public static final String CODE_REGEX = "\\d{9}";

	@Column(unique = true, nullable = false)
	private String uispCode;

	@ManyToOne
	private SportsClub club;

	public String getUispCode() {
		return uispCode;
	}

	public void setUispCode(String uispCode) {
		this.uispCode = uispCode;
	}

	@XmlTransient
	public SportsClub getClub() {
		return club;
	}

	public void setClub(SportsClub club) {
		this.club = club;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((uispCode == null) ? 0 : uispCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Athlete other = (Athlete) obj;
		if (uispCode == null) {
			if (other.uispCode != null)
				return false;
		} else if (!uispCode.equals(other.uispCode))
			return false;
		return true;
	}

	@Transient
	public String regexCode() {
		return CODE_REGEX;
	}

	@Override
	public int compareTo(Athlete o) {
		int result;

		if (club != null) {
			result = club.compareTo(o.getClub());
			if (result != 0)
				return result;
		}

		if (this.getSurname() != null) {
			result = this.getSurname().compareTo(o.getSurname());
			if (result != 0)
				return result;
		}

		if (this.getName() != null) {
			result = this.getName().compareTo(o.getName());
			if (result != 0)
				return result;
		}

		return 0;
	}

}
