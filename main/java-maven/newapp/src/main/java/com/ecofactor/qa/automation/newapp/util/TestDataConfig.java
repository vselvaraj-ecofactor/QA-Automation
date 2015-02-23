/*
 * TestDataConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util;

import java.util.List;
import java.util.Map;

/**
 * The Class TestDataConfig.
 *
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestDataConfig {

	private String id;
	private List<UserConfig> userConfigs;
	private Map<String, String> configs;
	private List<String> globalFeatures;

	/**
	 * Gets the id.
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * Sets the id.
	 * @param id the new id
	 */
	public void setId(final String id) {

		this.id = id;
	}

	/**
	 * Gets the user configs.
	 * @return the user configs
	 */
	public List<UserConfig> getUserConfigs() {

		return userConfigs;
	}

	/**
	 * Sets the user configs.
	 * @param userConfigs the new user configs
	 */
	public void setUserConfigs(final List<UserConfig> userConfigs) {

		this.userConfigs = userConfigs;
	}

	/**
	 * Gets the configs.
	 * @return the configs
	 */
	public Map<String, String> getConfigs() {

		return configs;
	}

	/**
	 * Sets the configs.
	 * @param configs the configs
	 */
	public void setConfigs(final Map<String, String> configs) {

		this.configs = configs;
	}

	/**
	 * Gets the global features.
	 * @return the global features
	 */
	public List<String> getGlobalFeatures() {

		return globalFeatures;
	}

	/**
	 * Sets the global features.
	 * @param globalFeatures the new global features
	 */
	public void setGlobalFeatures(final List<String> globalFeatures) {

		this.globalFeatures = globalFeatures;
	}
}
