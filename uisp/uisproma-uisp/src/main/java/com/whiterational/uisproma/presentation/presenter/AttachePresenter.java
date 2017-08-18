package com.whiterational.uisproma.presentation.presenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Role;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.security.Md5Helper;
import com.whiterational.uisproma.business.service.UispAttacheService;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.presentation.view.AttacheView;

@Presenter
public class AttachePresenter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6390726353066182169L;

  @Inject
  private AttacheView            view;

  @Inject
  private UispAttacheService         service;

  @Inject
  private UispClubService            club;

  @Inject
  private transient FacesContext ctx;

  private Map<Long, SportsClub>  choises;
  private List<Attache>          all;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AttachePresenter.class);

  @PostConstruct
  public void init() {
    all = service.findAll();
    choises = club.getChoises();

    Role role = new Role();
    role.setUser(view.getAttache().getUser());
    role.setGroupName("ATTACHE");
    view.getAttache().getUser().getRoles().add(role);
  }

  public AttacheView getView() {
    return view;
  }

  public void setView(AttacheView view) {
    this.view = view;
  }

  public Long getClub() {
    return (view.getAttache().getId() != null) ? view.getAttache().getId() : null;
  }

  public void setClub(Long id) {
    if (choises.containsKey(id))
      view.getAttache().setClub(choises.get(id));
  }

  public List<SelectItem> getClubs() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for (Entry<Long, SportsClub> entry : choises.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }

    return items;
  }

  public String register() {
    LOGGER.debug("register");
    
    if (!view.valid()) {
      ctx.addMessage(null, new FacesMessage("Le password non coincidono"));
      return "";
    }

    view.getAttache().getUser().setPassword(Md5Helper.hashPassword(view.getAttache().getUser().getPassword()));
    view.getAttache().getClub().getAttaches().add(view.getAttache());
    
    service.create(view.getAttache());
    club.update(view.getAttache().getClub());

    return "confirm";
  }

  public List<Attache> getAll() {
    return all;
  }

}
