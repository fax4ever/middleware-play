package com.whiterational.uisproma.business.entity.championship;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Fee;

@Entity
public class ChampionshipFee extends Fee {

	/**
   * 
   */
	private static final long serialVersionUID = -4329076639125315840L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ChampionshipAttendance attendance;

	public ChampionshipAttendance getAttendance() {
		return attendance;
	}

	public void setAttendance(ChampionshipAttendance attendance) {
		this.attendance = attendance;
	}

	@Override
	public Attache getAttache() {
		return (attendance == null) ? null : attendance.getAttache();
	}

}
