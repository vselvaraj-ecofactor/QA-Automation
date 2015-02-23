/*
 * TestData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util;

import java.util.List;

import com.ecofactor.qa.automation.newapp.model.ThermostatDetails;

/**
 * The Class TestData.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestData {

	private String userId;
	private String password;
	private List<ThermostatDetails> thermostats;
	private List<String> features;
	private boolean userMarked;

	/**
	 * Gets the user id.
	 * @return the user id
	 */
	public String getUserId() {

		return userId;
	}

	/**
	 * Sets the user id.
	 * @param userId the new user id
	 */
	public void setUserId(final String userId) {

		this.userId = userId;
	}

	/**
	 * Gets the password.
	 * @return the password
	 */
	public String getPassword() {

		return password;
	}

	/**
	 * Sets the password.
	 * @param password the new password
	 */
	public void setPassword(final String password) {

		this.password = password;
	}

	/**
	 * Gets the thermostats.
	 * @return the thermostats
	 */
	public List<ThermostatDetails> getThermostats() {

		return thermostats;
	}

	/**
	 * Sets the thermostats.
	 * @param thermostats the new thermostats
	 */
	public void setThermostats(final List<ThermostatDetails> thermostats) {

		this.thermostats = thermostats;
	}

	/**
	 * Gets the features.
	 * @return the features
	 */
	public List<String> getFeatures() {

		return features;
	}

	/**
	 * Sets the features.
	 * @param features the new features
	 */
	public void setFeatures(final List<String> features) {

		this.features = features;
	}

	/**
	 * Checks if is user marked.
	 * @return true, if is user marked
	 */
	public boolean isUserMarked() {

		return userMarked;
	}

	/**
	 * Sets the user marked.
	 * @param userMarked the new user marked
	 */
	public void setUserMarked(final boolean userMarked) {

		this.userMarked = userMarked;
	}
}
