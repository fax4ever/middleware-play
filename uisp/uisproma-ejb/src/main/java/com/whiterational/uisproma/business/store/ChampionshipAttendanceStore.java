package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.filter.ChampionshipAttendanceFilter;

public interface ChampionshipAttendanceStore extends Store<Long, ChampionshipAttendance> {
  
  public List<ChampionshipAttendance> findByFilter(int start, int size, ChampionshipAttendanceFilter filter);
  
  public Long countByFilter(ChampionshipAttendanceFilter filter);

}
