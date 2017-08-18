package com.whiterational.uisproma.business.entity.competition;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.Attendance;
import com.whiterational.uisproma.business.entity.Event;
import com.whiterational.uisproma.business.entity.Fee;
import com.whiterational.uisproma.business.entity.PaidInfo;
import com.whiterational.uisproma.business.valueobject.CompBook;

@Entity
public class CompetitionAttendance extends Attendance implements Cloneable {

	/**
   * 
   */
	private static final long serialVersionUID = 3529980608308164312L;

	@OneToOne(mappedBy = "attendance", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CompetitionFee fee;

	@ManyToOne
	@NotNull
	private Solution solution;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Competition competition;

	public Fee getFee() {
		return fee;
	}

	public void setFee(CompetitionFee fee) {
		this.fee = fee;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Override
	public String toString() {
		return solution + " " + competition + " " + getAthlete();
	}

	@Override
	@Transient
	public String getType() {
		return "Competition";
	}

	@Override
	@Transient
	public Event getEvent() {
		return competition;
	}

	public Boolean canRemove(Solution sol) {
		return (!sol.equals(solution));
	}

	@Transient
	public PaidInfo getPaid() {
		return (fee != null && fee.getPayment() != null) ? PaidInfo.Paid : PaidInfo.NotPaid;
	}

	@Transient
	public boolean canRemove() {
		return PaidInfo.NotPaid.equals(getPaid());
	}

	@Transient
	public boolean canUpdate() {
		return competition.getSolutions().contains(solution);
	}

	public void write(CompBook book, Set<Athlete> athletes) {
		boolean present = athletes.remove(this.athlete);
		if (!present)
			return;
		
		if (canRemove())
			book.addNotPaid(this);
	}
	
	public boolean createFee(String code) {
		if (solution == null || solution.getPrice() == null)
			return false;
		
		this.fee = new CompetitionFee();
		this.fee.setCode(code);
		this.fee.setPrice(solution.getPrice());
		this.fee.setAttendance(this);
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
