package com.whiterational.uisproma.business.entity.championship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.whiterational.uisproma.business.entity.Attendance;
import com.whiterational.uisproma.business.entity.Event;
import com.whiterational.uisproma.business.entity.PaidInfo;
import com.whiterational.uisproma.business.valueobject.ChampBook;
import com.whiterational.uisproma.business.valueobject.ChampBookChange;

@Entity
public class ChampionshipAttendance extends Attendance {

	/**
   * 
   */
	private static final long serialVersionUID = -3863841099184699564L;

	@OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@MapKeyJoinColumn(name = "STEP_ID")
	private Map<Step, ChampionshipFee> fees = new LinkedHashMap<Step, ChampionshipFee>();

	@ManyToOne
	private Championship championship;

	public Map<Step, ChampionshipFee> getFees() {
		return fees;
	}

	public void setFees(Map<Step, ChampionshipFee> fees) {
		this.fees = fees;
	}

	@Override
	public String toString() {
		return championship + " " + getAthlete();
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	@Override
	@Transient
	public Event getEvent() {
		return championship;
	}

	@Override
	@Transient
	public String getType() {
		return "Championship";
	}

	public Boolean canRemove(Step step) {
		for (Step myStep : fees.keySet()) {
			if (step.equals(myStep))
				return (fees.get(myStep).getPayment() == null);
		}

		return true;
	}

	public PaidInfo getPaidInfo(int feeNumber) {
		if (feeNumber >= championship.getSteps().size())
			return PaidInfo.NotPresent;

		ChampionshipFee fee = getFeeByNumber(feeNumber);

		if (fee == null)
			return PaidInfo.NotPresent;

		return (fee.getPayment() == null) ? PaidInfo.NotPaid : PaidInfo.Paid;
	}

	private ChampionshipFee getFeeByNumber(int feeNumber) {
		if (feeNumber >= championship.getSteps().size())
			return null;
		
		Step step = championship.getSteps().get(feeNumber);
		ChampionshipFee fee = fees.get(step);
		return fee;
	}

	@Transient
	public List<PaidInfo> getPaidInfos() {
		ArrayList<PaidInfo> result = new ArrayList<PaidInfo>();

		for (Step step : championship.getSteps()) {
			ChampionshipFee fee = fees.get(step);

			result.add((fee == null) ? PaidInfo.NotPresent : (fee.getPayment() == null) ? PaidInfo.NotPaid
					: PaidInfo.Paid);
		}

		return result;
	}

	@Transient
	public boolean canRemove() {
		for (ChampionshipFee fee : fees.values()) {
			if (fee.getPayment() != null)
				return false;
		}

		return true;
	}

	@Transient
	public Set<Step> getConstrainedSteps() {
		HashSet<Step> steps = new HashSet<Step>();

		for (Entry<Step, ChampionshipFee> entry : fees.entrySet()) {
			if (entry.getValue().getPayment() != null)
				steps.add(entry.getKey());
		}

		return steps;
	}

	@Transient
	public boolean alignFeesByChamp() {
		if (this.getChampionship().getSteps().containsAll(this.getFees().keySet()))
			return true;

		if (!this.canRemove())
			return false;

		this.fees.clear();
		for (Step step : this.getChampionship().getSteps()) {
			ChampionshipFee fee = new ChampionshipFee();
			fee.setAttendance(this);
			fee.setPrice(step.getPrice());
			this.fees.put(step, fee);
		}

		return true;
	}

	public void load(ChampBook book) {
		PaidInfo paidInfo = this.getPaidInfo(book.getLevel());
		if (PaidInfo.Paid.equals(paidInfo))
			book.getAthletes().remove(athlete);
	}
	
	public void loadLvl1up(ChampBook book) {
		if (isComplient(book.getLevel()))
			book.getAthletes().add(athlete);
	}
	
	private boolean isComplient(int level) {
		List<PaidInfo> paidInfos = getPaidInfos();
		
		if (level >= paidInfos.size())
			return false;
		
		if (PaidInfo.Paid.equals(paidInfos.get(level)))
			return false;
		
		if (level > 0 && PaidInfo.NotPaid.equals(paidInfos.get(level-1)))
			return false;
		
		return true;
	}

	public void change(ChampBook book) {
		if (!book.getAthletes().contains(athlete))
			return;
		
		Integer level = book.getLevel();
		ChampionshipFee fee = getFeeByNumber(level);
		if (fee == null || fee.getPayment() != null)
			return;
		
		book.addChange(this.getAthlete(), this.getAttache(), fee.getCode());
		this.setAttache(book.getAttache());
		fee.setCode(book.getCode());
	}

	private ChampBookChange getChange(ChampBook book) {
		Integer level = book.getLevel();
		ChampionshipFee fee = getFeeByNumber(level);
		if (fee == null)
			return null;
		
		String code = fee.getCode();
		if (code == null || code.trim().isEmpty())
			return null;
		
		if (!code.equals(book.getCode()))
			return null;
		
		if (!getAttache().equals(book.getAttache()))
			return null;
		
		for (ChampBookChange change : book.getChanges()) {
			if (change.getAthlete().equals(this.athlete))
				return change;
		}
		
		return null;
	}
	
	public boolean match(ChampBook book) {
		return (getChange(book) != null);
	}

	public void undo(ChampBook book) {
		ChampBookChange change = getChange(book);
		if (change == null)
			return;
		
		ChampionshipFee fee = getFeeByNumber(book.getLevel());
		if (fee == null || fee.getPayment() != null)
			return;
		
		fee.setCode(change.getCode());
		this.setAttache(change.getAttache());
	}

}
