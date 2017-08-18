/*
 * @(#)PaymentService.java        1.00	19/feb/2014
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

package com.whiterational.uisproma.business.stateless;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.AttachePayment;
import com.whiterational.uisproma.business.entity.Fee;
import com.whiterational.uisproma.business.remote.RemotePaymentService;
import com.whiterational.uisproma.business.service.UispPaymentService;
import com.whiterational.uisproma.business.store.FeeStore;
import com.whiterational.uisproma.business.store.PaymentStore;

/**
 * La classe <code>PaymentService.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	19/feb/2014
 *
 */

@Stateless
public class PaymentService implements UispPaymentService, RemotePaymentService {
	
	@Inject
	private FeeStore feeStore;
	
	@Inject
	private PaymentStore store;
	
	public void pay(String code) {
		List<Fee> fees = feeStore.findByCode(code);
		if (fees.isEmpty())
			return;
		
		Attache attache = fees.get(0).getAttache();
		AttachePayment pay = new AttachePayment();
		
		for (Fee fee : fees) {
			fee.setPayment(pay);
		}
		
		List<Fee> values = new ArrayList<Fee>(fees);
		
		pay.setFees(values);
		pay.setAttache(attache);
		pay.setMoment(Calendar.getInstance());
		
		store.create(pay);
	}

}
