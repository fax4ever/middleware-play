package com.whiterational.uisproma.business.valueobject;

import com.whiterational.uisproma.business.entity.championship.Championship;

public class ChampionshipValue {
  
  private Championship champ;

  public ChampionshipValue(Championship champ) {
    this.champ = champ;
  }

  public Championship getChamp() {
    return champ;
  }

  public void setChamp(Championship champ) {
    this.champ = champ;
  }
  
  public Integer getIscriptionNumeber() {
    return champ.getAttendances().size();
  }

}
