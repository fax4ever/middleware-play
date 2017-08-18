package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.filter.ClubFilter;

public interface SportClubStore extends Store<Long, SportsClub> {
  
  SportsClub findByCode(String code);
  List<SportsClub> getAll();
  List<SportsClub> findByFilter(int start, int size, ClubFilter filter);
  Long countByFilter(ClubFilter filter);

}
