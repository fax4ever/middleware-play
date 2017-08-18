package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.filter.ChampionshipAttendanceFilter;
import com.whiterational.uisproma.business.filter.ChampionshipAttendancePage;

@Remote
public interface RemoteChampionshipAttendanceService {

	void create(ChampionshipAttendance att);

	ChampionshipAttendance read(Long id);

	boolean update(ChampionshipAttendance att);

	boolean delete(ChampionshipAttendance att);

	List<ChampionshipAttendance> findAll();

	Long count();

	ChampionshipAttendancePage getPage(int start, int size, ChampionshipAttendanceFilter filter);

}