package com.whiterational.uisproma.presentation.crud;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.filter.ClubFilter;
import com.whiterational.uisproma.business.filter.ClubPage;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.presentation.faces.SelectItemProducer;

@ManagedBean(name = "clubCRUD")
@ViewScoped
public class ClubCRUD extends ApplicationCRUD implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1568886536123544617L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ClubCRUD.class);
  
  @Inject
  private UispClubService service;
  
  @Inject
  private SelectItemProducer items;
  
  private boolean init = false;
  private ClubFilter filter;
  private ClubPage page;

  @Override
  public void init() {
    init = true;
    super.init();
    
    filter = new ClubFilter();
    search();
  }

  public void search() {
    LOGGER.info("search: " + filter + ", index: " + 0 + ", size: " + pageSize);
    page = service.getPage(0, pageSize, filter);
    LOGGER.info("search performed: retrive " + page.getClubs().size() + " athletes on " + page.getTotal());
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
    LOGGER.info("search performed: retrive " + page.getClubs().size() + " athletes on " + page.getTotal());
  }
  
  public ClubFilter getFilter() {
    if (!init)
      init();
    
    return filter;
  }

  public SelectItem[] getMarkers() {
    if (!init)
      init();
    
    return items.produce(windowMark, totalMark, windowSize+windowMark-1);
  }
  
  public ClubPage getPage() {
    if (!init)
      init();
    
    return page;
  }

  public Integer getMark() {
    if (!init)
      init();
    
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

}
