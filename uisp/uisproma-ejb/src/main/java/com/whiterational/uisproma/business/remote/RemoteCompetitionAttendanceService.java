package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.filter.CompetitionAttendanceFilter;
import com.whiterational.uisproma.business.filter.CompetitionAttendancePage;

@Remote
public interface RemoteCompetitionAttendanceService {

	boolean create(CompetitionAttendance att);

	CompetitionAttendance read(Long id);

	boolean update(CompetitionAttendance att);

	boolean delete(CompetitionAttendance att);

	List<CompetitionAttendance> findAll();

	Long count();

	CompetitionAttendancePage getPage(int start, int size, CompetitionAttendanceFilter filter);

}