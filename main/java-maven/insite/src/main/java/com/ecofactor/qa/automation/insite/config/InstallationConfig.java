/*
 * InstallationConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class InstallationConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InstallationConfig extends BaseConfig {

	public final static String STREET_ADDRESS = "streetAddress";
	public final static String STREET_DROP_DOWN_CLASS = "streetDropDownClass";
	public final static String OK_BUTTON = "okButton";
	public final static String START_INSTALLATION = "startInstallation";
	public final static String ANYTHING_WINDOW = "anythingWindow";
	public final static String NEXT_STEP_BUTTON = "nextStepButton";
	public final static String INTERNET_QUESTION = "internetQuestion";
	public final static String CHECKBOX = "checkbox";

	/**
     * Instantiates a new installation config.
     * @param driverConfig the driver config
     */
	@Inject
	public InstallationConfig(DriverConfig driverConfig) {

		super(driverConfig.get(DriverConfig.ENV));
		load("installationHardware.properties");
	}

}
