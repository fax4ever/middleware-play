package com.whiterational.uisproma.presentation.presenter;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Sport;
import com.whiterational.uisproma.business.service.UispSportService;
import com.whiterational.uisproma.presentation.view.SportView;
import com.whiterational.uisproma.presentation.view.ViewMode;

@Presenter
public class SportPresenter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -324503328533136632L;

  @Inject
  private SportView           view;

  @Inject
  private UispSportService        service;

  private List<Sport>         all    = null;
  
  @Inject
  private transient FacesContext ctx;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SportPresenter.class);

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());

    if (view.getSport().getId() == null)
      return "";

    final Sport entity = service.read(view.getSport().getId());
    view.setSport(entity);
    return "";
  }

  public String save() {
    LOGGER.debug("save");

    if (ViewMode.CREATE.equals(view.getMode()))
      service.create(view.getSport());
    else
      service.update(view.getSport());

    return "sporthome?faces-redirect=true";
  }

  public String delete() {
    LOGGER.debug("delete");

    if (ViewMode.UPDATE.equals(view.getMode()))
      service.delete(view.getSport());

    return "sporthome?faces-redirect=true";
  }

  public List<Sport> getAll() {
    if (all == null) {
      LOGGER.debug("getAll");
      all = service.findAll();
    }

    return all;
  }

}
