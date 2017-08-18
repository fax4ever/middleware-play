package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.filter.AthleteFilter;

public interface AthleteStore extends Store<Long, Athlete> {
  
  Athlete findByCode(String code);
  List<Athlete> getAll();
  List<Athlete> findByFilter(int start, int size, AthleteFilter filter);
  Long countByFilter(AthleteFilter filter);

}
