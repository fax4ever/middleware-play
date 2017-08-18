/*
 * @(#)JPAFeeStore.java        1.00	23/feb/2014
 *
 * Copyright (c) 2007-2013 Paybay Networks srl,
 * XX Settembre Road, Rome, Italy.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Paybay 
 * Networks srl, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Paybay Networks.
 */

package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Fee;
import com.whiterational.uisproma.business.entity.Fee_;
import com.whiterational.uisproma.business.store.FeeStore;

/**
 * La classe <code>JPAFeeStore.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	23/feb/2014
 *
 */

public class JPAFeeStore extends JPAStore<Long, Fee> implements FeeStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979946772810762589L;

	@Override
	public List<Fee> findByCode(String code) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Fee> query = builder.createQuery(Fee.class);
		Root<Fee> fee = query.from(Fee.class);
		query.where(builder.equal(fee.get(Fee_.code), code));
		List<Fee> resultList = entityManager.createQuery(query).getResultList();
		
		return resultList;
	}

}
