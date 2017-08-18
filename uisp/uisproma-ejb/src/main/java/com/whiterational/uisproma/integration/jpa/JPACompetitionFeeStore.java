/*
 * @(#)JPACompetitionFeeStore.java        1.00	19/feb/2014
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

import com.whiterational.uisproma.business.entity.competition.CompetitionFee;
import com.whiterational.uisproma.business.entity.competition.CompetitionFee_;
import com.whiterational.uisproma.business.store.CompetitionFeeStore;

/**
 * La classe <code>JPACompetitionFeeStore.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	19/feb/2014
 *
 */

public class JPACompetitionFeeStore extends JPAStore<Long, CompetitionFee> implements CompetitionFeeStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3278904904281084684L;

	@Override
	public List<CompetitionFee> findByCode(String code) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CompetitionFee> query = builder.createQuery(CompetitionFee.class);
		Root<CompetitionFee> fee = query.from(CompetitionFee.class);
		query.where(builder.equal(fee.get(CompetitionFee_.code), code));
		List<CompetitionFee> resultList = entityManager.createQuery(query).getResultList();
		
		return resultList;
	}

}
