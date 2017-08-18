/*
 * @(#)FeeStore.java        1.00	23/feb/2014
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

package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.Fee;

/**
 * La classe <code>FeeStore.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	23/feb/2014
 *
 */

public interface FeeStore extends Store<Long, Fee> {
	
	List<Fee> findByCode(String code);

}
