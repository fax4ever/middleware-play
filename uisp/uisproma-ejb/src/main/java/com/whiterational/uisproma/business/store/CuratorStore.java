package com.whiterational.uisproma.business.store;

import com.whiterational.uisproma.business.entity.Curator;

public interface CuratorStore extends Store<Long, Curator> {
  
  Curator findByUsername(String username);

}
