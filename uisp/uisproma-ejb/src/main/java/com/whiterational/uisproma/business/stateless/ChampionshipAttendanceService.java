package com.whiterational.uisproma.business.stateless;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.entity.championship.ChampionshipFee;
import com.whiterational.uisproma.business.entity.championship.Step;
import com.whiterational.uisproma.business.filter.ChampionshipAttendanceFilter;
import com.whiterational.uisproma.business.filter.ChampionshipAttendancePage;
import com.whiterational.uisproma.business.remote.RemoteChampionshipAttendanceService;
import com.whiterational.uisproma.business.service.UispChampionshipAttendanceService;
import com.whiterational.uisproma.business.store.ChampionshipAttendanceStore;

@Stateless
public class ChampionshipAttendanceService implements UispChampionshipAttendanceService, RemoteChampionshipAttendanceService {

	@Inject
	private ChampionshipAttendanceStore store;

	@Override
	public void create(ChampionshipAttendance att) {
		if (att.getChampionship() == null || att.getChampionship().getSteps().size() == 0)
			return;

		if (att.getMoment() == null)
			att.setMoment(Calendar.getInstance());

		for (Step step : att.getChampionship().getSteps()) {
			ChampionshipFee fee = new ChampionshipFee();
			fee.setPrice(step.getPrice());

			fee.setAttendance(att);
			att.getFees().put(step, fee);
		}
		att.getChampionship().getAttendances().add(att);

		store.create(att);
	}

	@Override
	public ChampionshipAttendance read(Long id) {
		return store.findById(id);
	}

	@Override
	public boolean update(ChampionshipAttendance att) {
		if (!att.alignFeesByChamp())
			return false;

		store.update(att);
		return true;
	}

	@Override
	public boolean delete(ChampionshipAttendance att) {
		if (!att.canRemove())
			return false;

		store.remove(att);
		return true;
	}

	@Override
	public List<ChampionshipAttendance> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	public ChampionshipAttendancePage getPage(int start, int size, ChampionshipAttendanceFilter filter) {
		List<ChampionshipAttendance> athletes = store.findByFilter(start, size, filter);
		Long total = store.countByFilter(filter);
		return new ChampionshipAttendancePage(athletes, total);
	}

}
