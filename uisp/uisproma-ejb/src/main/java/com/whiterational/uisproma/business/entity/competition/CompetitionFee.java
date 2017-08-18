package com.whiterational.uisproma.business.entity.competition;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Fee;

@Entity
public class CompetitionFee extends Fee {

	/**
   * 
   */
	private static final long serialVersionUID = -3875367410358502467L;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CompetitionAttendance attendance;

	public CompetitionAttendance getAttendance() {
		return attendance;
	}

	public void setAttendance(CompetitionAttendance attendance) {
		this.attendance = attendance;
	}

	@Override
	public Attache getAttache() {
		return (attendance == null) ? null : attendance.getAttache();
	}

}
