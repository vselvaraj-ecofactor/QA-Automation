/*
 * AdminHomePage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page;

import com.ecofactor.qa.automation.newapp.admin.page.impl.AdminHomePageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface AdminHomePage.
 */
@ImplementedBy(value = AdminHomePageImpl.class)
public interface AdminHomePage {

	/**
	 * Select accountslink.
	 */
	void selectAccountslink();

	/**
	 * Filter criteria.
	 * @param filterValue the filter value
	 */
	void filterCriteria(String filterValue);

	/**
	 * Gets the filtered record.
	 * @param recordNumber the record number
	 * @return the filtered record
	 */
	void clickFilteredRecord(int recordNumber);

	/**
	 * Gets the current temperature.
	 * @param thermostatName the thermostat name
	 * @return the current temperature
	 */
	String getCurrentTemperature(String thermostatName);

	/**
	 * Logout.
	 */
	void logout();

	/**
	 * Click Diag link.
	 * @param thermostatName the thermostat name
	 */
	void clickDiagLink(String thermostatName);
}
