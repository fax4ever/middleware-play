package com.whiterational.uisproma.presentation.home;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Curator;
import com.whiterational.uisproma.business.service.UispCuratorService;

@Home
public class CuratorHome implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6440748782129461163L;

  @Inject
  private UispCuratorService    service;

  private List<Curator>     curators;
  
  @PostConstruct
  private void init() {
    curators = service.findAll();
  }

  public List<Curator> getCurators() {
    return curators;
  }

}
