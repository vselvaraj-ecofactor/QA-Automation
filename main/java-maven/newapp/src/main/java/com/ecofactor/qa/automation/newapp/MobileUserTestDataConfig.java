/*
 * MobileUserTestDataConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

public class MobileUserTestDataConfig extends BaseConfig {

	 public static final String USER_DETAILS = "userDetails";

	    /**
	     * Instantiates a new test data config.
	     * @param driverConfig the driver config
	     */
	    @Inject
	    public MobileUserTestDataConfig(DriverConfig driverConfig) {

	        super(driverConfig.get(DriverConfig.ENV));
	        load("testData.properties");
	    }
}
