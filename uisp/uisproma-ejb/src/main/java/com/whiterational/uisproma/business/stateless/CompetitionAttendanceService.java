package com.whiterational.uisproma.business.stateless;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.entity.competition.CompetitionFee;
import com.whiterational.uisproma.business.filter.CompetitionAttendanceFilter;
import com.whiterational.uisproma.business.filter.CompetitionAttendancePage;
import com.whiterational.uisproma.business.remote.RemoteCompetitionAttendanceService;
import com.whiterational.uisproma.business.service.UispCompetitionAttendanceService;
import com.whiterational.uisproma.business.store.CompetitionAttendanceStore;

@Stateless
public class CompetitionAttendanceService implements UispCompetitionAttendanceService, RemoteCompetitionAttendanceService {

	@Inject
	private CompetitionAttendanceStore store;

	@Override
	public boolean create(CompetitionAttendance att) {
		if (att.getSolution() == null || att.getSolution().getPrice() == null)
			return false;

		if (att.getCompetition() == null)
			return false;

		if (!att.getCompetition().getSolutions().contains(att.getSolution()))
			return false;

		if (att.getMoment() == null)
			att.setMoment(Calendar.getInstance());

		CompetitionFee fee = new CompetitionFee();
		fee.setAttendance(att);
		fee.setPrice(att.getSolution().getPrice());
		att.setFee(fee);
		att.getCompetition().getAttendances().add(att);

		store.create(att);
		return true;
	}

	@Override
	public CompetitionAttendance read(Long id) {
		return store.findById(id);
	}

	@Override
	public boolean update(CompetitionAttendance att) {
		if (!att.canUpdate())
			return false;

		store.update(att);
		return true;
	}

	@Override
	public boolean delete(CompetitionAttendance att) {
		if (!att.canRemove())
			return false;

		store.remove(att);
		return true;
	}

	@Override
	public List<CompetitionAttendance> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	public CompetitionAttendancePage getPage(int start, int size, CompetitionAttendanceFilter filter) {
		List<CompetitionAttendance> athletes = store.findByFilter(start, size, filter);
		Long total = store.countByFilter(filter);
		return new CompetitionAttendancePage(athletes, total);
	}

}
