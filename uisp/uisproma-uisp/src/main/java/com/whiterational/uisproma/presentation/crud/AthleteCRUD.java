package com.whiterational.uisproma.presentation.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.filter.AthleteFilter;
import com.whiterational.uisproma.business.filter.AthletePage;
import com.whiterational.uisproma.business.service.UispAthleteService;
import com.whiterational.uisproma.presentation.faces.SelectItemProducer;

@ManagedBean(name = "athleteCRUD")
@ViewScoped
public class AthleteCRUD extends ApplicationCRUD implements Serializable {

  /**
   * 
   */
  private static final long      serialVersionUID = -7172516816838127567L;

  private static final Logger    LOGGER           = LoggerFactory.getLogger(AthleteCRUD.class);

  @Inject
  private UispAthleteService service;
  
  @Inject
  private SelectItemProducer items;
  
  @Inject
  private Map<Long, SportsClub> clubs;

  private AthleteFilter filter;
  private AthletePage page;
  
  @Override
  public void init() {
    super.init();
    filter = new AthleteFilter();
  }
  
  public void search() {
    LOGGER.info("search: " + filter + ", index: " + 0 + ", size: " + pageSize);
    page = service.getPage(0, pageSize, filter);
    LOGGER.info("search performed: retrive " + page.getAthletes().size() + " athletes on " + page.getTotal());
    mark = 1;
    long ratio = page.getTotal() / pageSize;
    long rest = page.getTotal() % pageSize;
    totalMark = (int) ( (rest == 0) ? ratio : ratio + 1 );
    windowMark = 1;
  }
  
  @Override
  public void change() {
    int index = (mark-1) * pageSize;
    LOGGER.info("search: " + filter + ", index: " + index + ", size: " + pageSize);
    page = service.getPage(index, pageSize, filter);
    LOGGER.info("search performed: retrive " + page.getAthletes().size() + " athletes on " + page.getTotal());
  }

  public List<SelectItem> getClubs() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    items.add(new SelectItem(0, ""));

    for (Entry<Long, SportsClub> entry : clubs.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }

    return items;
  }
  
  public List<SelectItem> getYears() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    items.add(new SelectItem(0, ""));
    
    for (int i=1913; i<2013; i++) {
      items.add(new SelectItem(new Integer(i), i + ""));
    }
    
    return items;
  }
  
  public AthleteFilter getFilter() {
    if (filter == null)
      init();
    
    return filter;
  }

  public SelectItem[] getMarkers() {
    if (page == null)
      search();
    
    return items.produce(windowMark, totalMark, windowSize+windowMark-1);
  }
  
  public AthletePage getPage() {
    if (page == null)
      search();
    
    return page;
  }

  public Integer getMark() {
    if (page == null)
      search();
    
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

}
