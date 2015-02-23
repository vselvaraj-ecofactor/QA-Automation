/*
 * UserData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import java.util.List;

/**
 * The Class UserData.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserData {

	private boolean pageLoad;
	private int index;
	private String suiteName;
	private String sanity;
	private String userId;
	private String password;
	private boolean login;
	private boolean program;
	private boolean schedule;
	private boolean newScheduleUI;
	private boolean hasFailures;
	private List<ThermostatData> thermostatDatas;

    /**
     * Gets the index.
     * @return the index
     */
    public int getIndex() {

        return index;
    }

    /**
     * Sets the index.
     * @param index the new index
     */
    public void setIndex(int index) {

        this.index = index;
    }

    /**
     * Gets the suite name.
     * @return the suite name
     */
    public String getSuiteName() {
		return suiteName;
	}

	/**
	 * Sets the suite name.
	 * @param suiteName the new suite name
	 */
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

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
    public void setUserId(String userId) {

        this.userId = userId;
    }

    /**
     * Checks if is login.
     * @return true, if is login
     */
    public boolean isLogin() {

        return login;
    }

    /**
     * Sets the login.
     * @param login the new login
     */
    public void setLogin(boolean login) {

        this.login = login;
    }

    /**
     * Checks if is program.
     * @return true, if is program
     */
    public boolean isProgram() {

        return program;
    }

    /**
     * Sets the program.
     * @param program the new program
     */
    public void setProgram(boolean program) {

        this.program = program;
    }

    /**
     * Checks if is schedule.
     * @return true, if is schedule
     */
    public boolean isSchedule() {

        return schedule;
    }

    /**
     * Sets the schedule.
     * @param schedule the new schedule
     */
    public void setSchedule(boolean schedule) {

        this.schedule = schedule;
    }

    /**
     * Gets the thermostat datas.
     * @return the thermostat datas
     */
    public List<ThermostatData> getThermostatDatas() {

        return thermostatDatas;
    }

    /**
     * Sets the thermostat datas.
     * @param thermostatDatas the new thermostat datas
     */
    public void setThermostatDatas(List<ThermostatData> thermostatDatas) {

        this.thermostatDatas = thermostatDatas;
    }

	/**
	 * Checks if is page load.
	 * @return true, if is page load
	 */
	public boolean isPageLoad() {
		return pageLoad;
	}

	/**
	 * Sets the page load.
	 * @param pageLoad the new page load
	 */
	public void setPageLoad(boolean pageLoad) {
		this.pageLoad = pageLoad;
	}

	/**
	 * Gets the sanity.
	 * @return the sanity
	 */
	public String getSanity() {
		return sanity;
	}

	/**
	 * Sets the sanity.
	 * @param sanity the new sanity
	 */
	public void setSanity(String sanity) {
		this.sanity = sanity;
	}

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isHasFailures() {

        return hasFailures;
    }

    public void setHasFailures(boolean hasFailures) {

        this.hasFailures = hasFailures;
    }

    public boolean isNewScheduleUI() {

        return newScheduleUI;
    }

    public void setNewScheduleUI(boolean newScheduleUI) {

        this.newScheduleUI = newScheduleUI;
    }
}
