/*
 * UserData.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.model;

import java.util.List;

import com.ecofactor.qa.automation.newapp.model.ThermostatData;

/**
 * The Class UserData.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserData {

	private boolean pageLoad;
	private int index;
	private String userName;
	private String password;
	private boolean login;
	private boolean hasFailures;
	private List<ThermostatData> thermostatDatas;
	private Integer noOfLocation;
	private Integer noOfThermostats;

	/**
	 * Checks if is page load.
	 * @return true, if is page load
	 */
	public boolean isPageLoad() {
		return pageLoad;
	}

	/**
	 * Gets the index.
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the user name.
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Checks if is login.
	 * @return true, if is login
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * Gets the thermostat datas.
	 * @return the thermostat datas
	 */
	public List<ThermostatData> getThermostatDatas() {
		return thermostatDatas;
	}

	/**
	 * Sets the page load.
	 * @param pageLoad the new page load
	 */
	public void setPageLoad(final boolean pageLoad) {
		this.pageLoad = pageLoad;
	}

	/**
	 * Sets the index.
	 * @param index the new index
	 */
	public void setIndex(final int index) {
		this.index = index;
	}

	/**
	 * Sets the user name.
	 * @param userName the new user name
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * Sets the login.
	 * @param login the new login
	 */
	public void setLogin(final boolean login) {
		this.login = login;
	}

	/**
	 * Sets the thermostat datas.
	 * @param thermostatDatas the new thermostat datas
	 */
	public void setThermostatDatas(final List<ThermostatData> thermostatDatas) {
		this.thermostatDatas = thermostatDatas;
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
	 * Checks if is checks for failures.
	 * @return true, if is checks for failures
	 */
	public boolean isHasFailures() {
		return hasFailures;
	}

	/**
	 * Sets the checks for failures.
	 * @param hasFailures the new checks for failures
	 */
	public void setHasFailures(final boolean hasFailures) {
		this.hasFailures = hasFailures;
	}

    /**
     * Gets the no of location.
     * @return the no of location
     */
    public Integer getNoOfLocation() {

        return noOfLocation;
    }

    /**
     * Sets the no of location.
     * @param noOfLocation the new no of location
     */
    public void setNoOfLocation(Integer noOfLocation) {

        this.noOfLocation = noOfLocation;
    }

    /**
     * Gets the no of thermostats.
     * @return the no of thermostats
     */
    public Integer getNoOfThermostats() {

        return noOfThermostats;
    }

    /**
     * Sets the no of thermostats.
     * @param noOfThermostats the new no of thermostats
     */
    public void setNoOfThermostats(Integer noOfThermostats) {

        this.noOfThermostats = noOfThermostats;
    }
}
