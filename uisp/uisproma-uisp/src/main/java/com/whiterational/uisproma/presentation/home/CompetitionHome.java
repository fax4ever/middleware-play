package com.whiterational.uisproma.presentation.home;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.competition.Competition;

@Home
public class CompetitionHome implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5533881325234783715L;
  
  @Inject
  private List<Competition> competitions;

  public List<Competition> getCompetitions() {
    return competitions;
  }

}
