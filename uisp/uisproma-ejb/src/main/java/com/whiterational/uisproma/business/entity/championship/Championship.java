package com.whiterational.uisproma.business.entity.championship;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Event;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.valueobject.ChampBook;

@Entity
public class Championship extends Event {

	/**
   * 
   */
	private static final long serialVersionUID = -5329213731095204456L;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Step> steps = new ArrayList<Step>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "championship", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ChampionshipAttendance> attendances = new ArrayList<ChampionshipAttendance>();

	@Override
	@Transient
	public String getType() {
		return "Championship";
	}

	public List<ChampionshipAttendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<ChampionshipAttendance> attendances) {
		this.attendances = attendances;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public Boolean canRemove(Step step) {
		for (ChampionshipAttendance att : attendances) {
			if (att.canRemove(step))
				return false;
		}

		return true;
	}

	public void deleteStep(Step step) {
		if (canRemove(step) && steps.contains(step))
			steps.remove(step);

		applySum();
	}

	public void addStep() {
		steps.add(new Step());
	}

	public void applySum() {
		BigDecimal result = new BigDecimal(0);

		for (Step step : steps) {
			BigDecimal stepPrice = step.getPrice();
			if (stepPrice != null)
				result = result.add(stepPrice);
		}

		this.setPrice(result);
	}

	@Transient
	public boolean canRemove() {
		return (attendances.size() == 0);
	}

	@Transient
	public Set<Step> getConstrainedSteps() {
		HashSet<Step> steps = new HashSet<Step>();

		for (ChampionshipAttendance att : attendances) {
			steps.addAll(att.getConstrainedSteps());
		}

		return steps;
	}

	@Transient
	public boolean canUpdate() {
		return steps.containsAll(getConstrainedSteps());
	}

	public void alignAttendance() {
		for (ChampionshipAttendance att : attendances) {
			alignAtt(att);
		}
	}

	private void alignAtt(ChampionshipAttendance att) {
		for (Step step : steps) {
			if (!att.getFees().containsKey(step)) {
				ChampionshipFee fee = new ChampionshipFee();
				fee.setAttendance(att);
				fee.setPrice(step.getPrice());
				att.getFees().put(step, fee);
			}
		}
	}
	
	private void alignAtt(ChampionshipAttendance att, String code) {
		for (Step step : steps) {
			if (!att.getFees().containsKey(step)) {
				ChampionshipFee fee = new ChampionshipFee();
				fee.setAttendance(att);
				fee.setPrice(step.getPrice());
				fee.setCode(code);
				att.getFees().put(step, fee);
			}
		}
	}

	@Transient
	public Integer getSpepsNumber() {
		return steps.size();
	}

	public List<ChampionshipAttendance> findByClub(Long clubId) {
		ArrayList<ChampionshipAttendance> result = new ArrayList<ChampionshipAttendance>();

		for (ChampionshipAttendance att : attendances) {
			Attache attache = att.getAttache();
			if (attache == null)
				continue;

			SportsClub club = attache.getClub();
			if (club == null)
				continue;

			if (clubId.equals(club.getId()))
				result.add(att);
		}

		return result;
	}

	public void load(ChampBook book) {
		if (book.getLevel() == 0) {
			for (ChampionshipAttendance att : attendances) {
				att.load(book);
			}
		} else {
			book.getAthletes().clear();
			
			for (ChampionshipAttendance att : attendances) {
				att.loadLvl1up(book);
			}
		}
	}

	public void insertBook(ChampBook book) {
		book.clear();
		if (book.getLevel() == 0) {
			for (Athlete athlete : book.getAthletes()) {
				ChampionshipAttendance insert = this.insert(athlete, book.getAttache(), book.getCode());
				if (insert != null)
					book.addChange(athlete);
			}

		} else {
			
			for (ChampionshipAttendance att : attendances) {
				att.change(book);
			}
			
		}
	}

	private ChampionshipAttendance insert(Athlete athlete, Attache attache, String code) {
		ChampionshipAttendance att = createAtt(athlete, attache, code);
		boolean contains = this.attendances.contains(att);
		if (contains)
			this.attendances.remove(att);
		
		this.attendances.add(att);
		return att;
	}
	
	private ChampionshipAttendance createAtt(Athlete athlete, Attache attache, String code) {
		ChampionshipAttendance att = new ChampionshipAttendance();
		att.setAthlete(athlete);
		att.setAttache(attache);
		att.setChampionship(this);
		alignAtt(att, code);
		return att;
	}

	public void removeBook(ChampBook book) {
		if (book.getLevel() == 0) {
			removeLvl0Book(book);
		} else {
			removeLvl1plusBook(book);
		}
	}
	
	private void removeLvl0Book(ChampBook book) {
		Iterator<ChampionshipAttendance> it = this.attendances.iterator();
		
		while (it.hasNext()) {
			ChampionshipAttendance att = it.next();
			if (att.match(book))
				it.remove();
		}
		
		book.clear();
	}

	private void removeLvl1plusBook(ChampBook book) {
		for (ChampionshipAttendance att : attendances) {
			att.undo(book);
		}
	}

}
