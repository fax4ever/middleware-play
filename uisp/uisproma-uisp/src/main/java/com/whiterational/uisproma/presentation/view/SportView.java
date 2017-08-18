package com.whiterational.uisproma.presentation.view;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Sport;

@View
public class SportView implements Serializable {
  
	/**
   * 
   */
  private static final long serialVersionUID = 1445871438919736468L;

  private Sport sport = new Sport();
	
	@Inject
  private transient ResourceBundle bundle;

	public Sport getSport() {
		return sport;
	}
	
	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public ViewMode getMode() {
		return (sport.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
	}
	
	public String getCode() {
	  return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : sport.getId() + "";
	}
	
	public String getTitle() {
	  return bundle.getString("sportHome." + getMode().getText() + ".title");
	}
	
	public String getActionName() {
	  return bundle.getString("action." + getMode().getText());
	}

}
