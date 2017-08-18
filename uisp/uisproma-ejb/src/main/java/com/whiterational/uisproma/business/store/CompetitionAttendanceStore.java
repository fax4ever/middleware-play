package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.filter.CompetitionAttendanceFilter;

public interface CompetitionAttendanceStore extends Store<Long, CompetitionAttendance> {
  
  public List<CompetitionAttendance> findByFilter(int start, int size, CompetitionAttendanceFilter filter);
  
  public Long countByFilter(CompetitionAttendanceFilter filter);

}
