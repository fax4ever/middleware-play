/*
 * @(#)PayPalInfo.java        1.00	09/feb/2014
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

package com.whiterational.uisproma.spring.paypal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * La classe <code>PayPalInfo.java</code> &egrave;
 * 
 * @author Fabio Massimo Ercoli fabiomassimo.ercoli@gmail.com
 * @version 1.00 09/feb/2014
 * 
 */

@Component
@Scope("prototype")
public class PaypalInfo {

	@Value("#{paypalProperties['paypal.actionUrl']}")
	private String actionUrl;
	
	@Value("#{paypalProperties['paypal.button']}")
	private String button;
	
	@Value("#{paypalProperties['paypal.business']}")
	private String business;
	
	@Value("#{paypalProperties['paypal.item_name']}")
	private String itemName;
	
	@Value("#{paypalProperties['paypal.item_number']}")
	private String itemNumber;
	
	@Value("#{paypalProperties['paypal.quantity']}")
	private String quantity;
	
	@Value("#{paypalProperties['paypal.amount']}")
	private String amount;
	
	@Value("#{paypalProperties['paypal.currency_code']}")
	private String currencyCode;
	
	@Value("#{paypalProperties['paypal.shipping']}")
	private String shipping;
	
	@Value("#{paypalProperties['paypal.tax']}")
	private String tax;
	
	@Value("#{paypalProperties['paypal.custom']}")
	private String custom;
	
	@Value("#{paypalProperties['paypal.return']}")
	private String returnVal;
	
	@Value("#{paypalProperties['paypal.cancel_return']}")
	private String cancelReturn;
	
	@Value("#{paypalProperties['paypal.env']}")
	private String env;
	
	@Value("#{paypalProperties['paypal.cmd']}")
	private String cmd;
	
	@Value("#{paypalProperties['paypal.bn']}")
	private String bn;

	public String getActionUrl() {
		return actionUrl;
	}

	public String getButton() {
		return button;
	}

	public String getBusiness() {
		return business;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getAmount() {
		return amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getShipping() {
		return shipping;
	}

	public String getTax() {
		return tax;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getReturnVal() {
		return returnVal;
	}

	public String getCancelReturn() {
		return cancelReturn;
	}

	public String getEnv() {
		return env;
	}

	public String getCmd() {
		return cmd;
	}

	public String getBn() {
		return bn;
	}

	public void setReturnVal(String returnVal) {
		this.returnVal = returnVal;
	}

	public void setCancelReturn(String cancelReturn) {
		this.cancelReturn = cancelReturn;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
