package com.whiterational.uisproma.business.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

@Entity
@Cache(isolation=CacheIsolationType.ISOLATED)
public class SportsClub implements Serializable, Comparable<SportsClub> {

	/**
   * 
   */
	private static final long serialVersionUID = -1566849457199491628L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String freshman;
	private String taxCode;
	private String vatNumber;

	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Athlete> athletes = new LinkedHashSet<Athlete>();

	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Attache> attaches = new LinkedHashSet<Attache>();

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

	public Set<Athlete> getAthletes() {
		return athletes;
	}

	public void setAthletes(Set<Athlete> athletes) {
		this.athletes = athletes;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFreshman() {
		return freshman;
	}

	public void setFreshman(String freshman) {
		this.freshman = freshman;
	}

	public Set<Attache> getAttaches() {
		return attaches;
	}

	public void setAttaches(Set<Attache> attaches) {
		this.attaches = attaches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((freshman == null) ? 0 : freshman.hashCode());
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
		SportsClub other = (SportsClub) obj;
		if (freshman == null) {
			if (other.freshman != null)
				return false;
		} else if (!freshman.equals(other.freshman))
			return false;
		return true;
	}

	@Transient
	public Integer getAttacheNumber() {
		return attaches.size();
	}

	@Transient
	public Integer getAthleteNumber() {
		return athletes.size();
	}

	@Transient
	public List<Attache> getAttacheList() {
		List<Attache> res = new ArrayList<Attache>();

		for (Attache att : attaches) {
			res.add(att);
		}

		return res;
	}

	public List<Athlete> getAthleteList() {
		List<Athlete> res = new ArrayList<Athlete>();

		for (Athlete ath : athletes) {
			res.add(ath);
		}

		return res;
	}

	@Override
	public int compareTo(SportsClub o) {
		if (o == null && name == null)
			return 0;

		if (o == null)
			return -1;

		if (name == null)
			return 1;

		return name.compareTo(o.getName());
	}
	
	public void addAthlete(Athlete athlete) {
		this.athletes.add(athlete);
	}

}
