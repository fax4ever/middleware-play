package com.whiterational.uisproma.business.service;

import java.util.List;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.filter.ChampionshipAttendanceFilter;
import com.whiterational.uisproma.business.filter.ChampionshipAttendancePage;

@Local
public interface UispChampionshipAttendanceService {

	void create(ChampionshipAttendance att);

	ChampionshipAttendance read(Long id);

	boolean update(ChampionshipAttendance att);

	boolean delete(ChampionshipAttendance att);

	List<ChampionshipAttendance> findAll();

	Long count();

	ChampionshipAttendancePage getPage(int start, int size, ChampionshipAttendanceFilter filter);

}