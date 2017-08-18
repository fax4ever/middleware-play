package com.whiterational.uisproma.business.service;

import java.util.List;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.filter.CompetitionAttendanceFilter;
import com.whiterational.uisproma.business.filter.CompetitionAttendancePage;

@Local
public interface UispCompetitionAttendanceService {

	boolean create(CompetitionAttendance att);

	CompetitionAttendance read(Long id);

	boolean update(CompetitionAttendance att);

	boolean delete(CompetitionAttendance att);

	List<CompetitionAttendance> findAll();

	Long count();

	CompetitionAttendancePage getPage(int start, int size, CompetitionAttendanceFilter filter);

}