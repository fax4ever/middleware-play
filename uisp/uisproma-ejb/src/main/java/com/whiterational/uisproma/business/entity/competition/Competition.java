package com.whiterational.uisproma.business.entity.competition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import com.whiterational.uisproma.business.valueobject.CompBook;

@Entity
public class Competition extends Event {

	/**
   * 
   */
	private static final long serialVersionUID = -7994084649271717868L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Solution> solutions = new ArrayList<Solution>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "competition", fetch = FetchType.EAGER)
	private List<CompetitionAttendance> attendances = new ArrayList<CompetitionAttendance>();

	@Override
	@Transient
	public String getType() {
		return "Competition";
	}

	public List<CompetitionAttendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<CompetitionAttendance> attendances) {
		this.attendances = attendances;
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

	public void addSolution() {
		solutions.add(new Solution());
	}

	public void applyMax() {
		if (solutions.size() == 0)
			return;

		BigDecimal max = Collections.max(solutions).getPrice();
		if (max == null)
			return;

		this.setPrice(max);
	}

	public Boolean canRemove(Solution sol) {
		for (CompetitionAttendance att : attendances) {
			if (att.canRemove(sol))
				return false;
		}

		return true;
	}

	public void deleteSolution(Solution sol) {
		if (canRemove(sol) && solutions.contains(sol))
			solutions.remove(sol);

		applyMax();
	}

	@Transient
	public Integer getAttendanceNumber() {
		return attendances.size();
	}

	@Transient
	public boolean canRemove() {
		return (attendances.size() == 0);
	}

	@Transient
	public Set<Solution> getUsedSolutions() {
		HashSet<Solution> solutions = new HashSet<Solution>();

		for (CompetitionAttendance att : attendances) {
			solutions.add(att.getSolution());
		}

		return solutions;
	}

	@Transient
	public boolean canUpdate() {
		return solutions.containsAll(getUsedSolutions());
	}

	public List<CompetitionAttendance> findByClub(Long clubId) {
		ArrayList<CompetitionAttendance> result = new ArrayList<CompetitionAttendance>();

		for (CompetitionAttendance att : attendances) {
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

	@Transient
	public Solution getMaxSolution() {
		return (solutions == null) ? null : Collections.max(solutions);
	}

	@Transient
	public Solution getMinSolution() {
		return (solutions == null) ? null : Collections.min(solutions);
	}

	public CompBook getBook(Attache attache) {
		CompBook book = new CompBook();
		Set<Athlete> athletes = new LinkedHashSet<Athlete>(attache.getClub().getAthletes());

		for (CompetitionAttendance att : attendances) {
			att.write(book, athletes);
		}

		for (Athlete athlete : athletes) {
			CompetitionAttendance att = new CompetitionAttendance();
			att.setAthlete(athlete);
			att.setAttache(attache);
			att.setMoment(Calendar.getInstance());
			att.setCompetition(this);
			
			if (!solutions.isEmpty())
				att.setSolution(solutions.get(0));
			
			book.addNotBooked(att);
		}

		return book;
	}

	public void insert(CompetitionAttendance att) {
		boolean contains = this.attendances.contains(att);
		if (contains)
			return;
		
		this.attendances.add(att);
	}

	public void update(CompetitionAttendance att) {
		this.attendances.remove(att);
		this.attendances.add(att);
	}
	
	public void remove(CompetitionAttendance att) {
		this.attendances.remove(att);
	}

}
