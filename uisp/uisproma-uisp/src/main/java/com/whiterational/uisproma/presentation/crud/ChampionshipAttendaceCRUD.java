package com.whiterational.uisproma.presentation.crud;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.PaidInfo;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.filter.ChampionshipAttendanceFilter;
import com.whiterational.uisproma.business.filter.ChampionshipAttendancePage;
import com.whiterational.uisproma.business.service.UispChampionshipAttendanceService;
import com.whiterational.uisproma.presentation.faces.SelectItemProducer;

@ManagedBean(name = "champAttendanceCRUD")
@ViewScoped
public class ChampionshipAttendaceCRUD extends ApplicationCRUD implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7007991847697354774L;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipAttendaceCRUD.class);

  @Inject
  private UispChampionshipAttendanceService service;
  
  @Inject
  private SelectItemProducer items;
  
  @Inject
  private transient ResourceBundle      bundle;
  
  private ChampionshipAttendanceFilter filter;
  private ChampionshipAttendancePage page;
  
  @Override
  public void init() {
    super.init();
    filter = new ChampionshipAttendanceFilter();
  }

  public void search() {
    LOGGER.info("search: " + filter + ", index: " + 0 + ", size: " + pageSize);
    page = service.getPage(0, pageSize, filter);
    LOGGER.info("search performed: retrive " + page.getItems().size() + " athletes on " + page.getTotal());
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
    LOGGER.info("search performed: retrive " + page.getItems().size() + " athletes on " + page.getTotal());
  }
  
  public ChampionshipAttendanceFilter getFilter() {
    if (filter == null)
      init();
    
    return filter;
  }

  public SelectItem[] getMarkers() {
    if (page == null)
      search();
    
    return items.produce(windowMark, totalMark, windowSize+windowMark-1);
  }
  
  public ChampionshipAttendancePage getPage() {
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
  
  public String getPaidInfo(ChampionshipAttendance att, Integer feeNumber) {
    PaidInfo paidInfo = att.getPaidInfo(feeNumber);
    return bundle.getString(paidInfo.toString());
  }

}
