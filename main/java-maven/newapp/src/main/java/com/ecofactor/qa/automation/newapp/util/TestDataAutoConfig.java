/*
 * TestDataAutoConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util;

import java.util.List;

/**
 * The Class TestDataAutoConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestDataAutoConfig {

	private List<TestDataConfig> testDataConfigs;

	/**
	 * Gets the test data configs.
	 * @return the test data configs
	 */
	public List<TestDataConfig> getTestDataConfigs() {

		return testDataConfigs;
	}

	/**
	 * Sets the test data configs.
	 * @param testDataConfigs the new test data configs
	 */
	public void setTestDataConfigs(final List<TestDataConfig> testDataConfigs) {

		this.testDataConfigs = testDataConfigs;
	}
}
