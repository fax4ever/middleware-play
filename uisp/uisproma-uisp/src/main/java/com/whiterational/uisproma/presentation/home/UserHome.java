package com.whiterational.uisproma.presentation.home;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.service.UispUserService;

@Home
public class UserHome implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 3478258947312942736L;

  @Inject
  private UispUserService            service;
  
  private List<User>             users;
  
  private static final Logger    LOGGER = LoggerFactory.getLogger(UserHome.class);
  
  @PostConstruct
  private void init() {
    users = service.findAll();
  }
  
  @Produces
  public List<User> getUsers() {
    LOGGER.debug("getUsers");
    return users;
  }

}
