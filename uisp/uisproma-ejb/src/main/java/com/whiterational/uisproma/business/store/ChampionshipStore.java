package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.championship.Championship;

public interface ChampionshipStore extends Store<Long, Championship>{

  List<Championship> findWithName(String name);

}
