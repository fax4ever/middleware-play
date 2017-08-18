/*
 * @(#)JPAPaymentStore.java        1.00	19/feb/2014
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

import com.whiterational.uisproma.business.entity.Payment;
import com.whiterational.uisproma.business.store.PaymentStore;

/**
 * La classe <code>JPAPaymentStore.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	19/feb/2014
 *
 */

public class JPAPaymentStore extends JPAStore<Long, Payment> implements PaymentStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8382868178571791559L;
	
}
