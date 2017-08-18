package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.competition.Competition;

public interface CompetitionStore extends Store<Long, Competition> {

	List<Competition> findBySportId(Long sportId);

}
