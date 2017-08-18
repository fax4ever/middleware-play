package com.whiterational.uisproma.business.filter;

import java.util.HashSet;
import java.util.List;

import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.entity.championship.Step;

public class ChampionshipAttendancePage extends ApplicationPage<ChampionshipAttendance> {

  private Integer maxStep = null;

  public ChampionshipAttendancePage(List<ChampionshipAttendance> items, Long total) {
    super(items, total);
  }

  private void initMaxStep() {
    int result = 0;
    HashSet<Championship> champs = new HashSet<Championship>();

    for (ChampionshipAttendance att : items) {
      champs.add(att.getChampionship());
    }

    for (Championship champ : champs) {
      List<Step> steps = champ.getSteps();
      int number = (steps == null) ? 0 : steps.size();
      if (number > result)
        result = number;
    }
    maxStep = result;
  }

  public int getMaxStep() {
    if (maxStep == null)
      initMaxStep();

    return maxStep;
  }

}
