/*
 * ThermostatData.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.model;

/**
 * The Class ThermostatData.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatData {

	private int thermostatId;
	private boolean connected;
	private boolean dataCollection;
	private String thermostatName;
	private String[] modes = new String[2];
	private String[] subsribedAlgorithms;
	private String activeAlgorithm;
	private String nextPhaseTime;
	private boolean validPhaseTime;
	private String locationName;
	private String savingsValue;

	/**
	 * Gets the thermostat id.
	 * @return the thermostat id
	 */
	public int getThermostatId() {
		return thermostatId;
	}

	/**
	 * Checks if is connected.
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Gets the thermostat name.
	 * @return the thermostat name
	 */
	public String getThermostatName() {
		return thermostatName;
	}

	/**
	 * Gets the modes.
	 * @return the modes
	 */
	public String[] getModes() {
		return modes;
	}

	/**
	 * Gets the subsribed algorithms.
	 * @return the subsribed algorithms
	 */
	public String[] getSubsribedAlgorithms() {
		return subsribedAlgorithms;
	}

	/**
	 * Gets the active algorithm.
	 * @return the active algorithm
	 */
	public String getActiveAlgorithm() {
		return activeAlgorithm;
	}

	/**
	 * Gets the next phase time.
	 * @return the next phase time
	 */
	public String getNextPhaseTime() {
		return nextPhaseTime;
	}

	/**
	 * Checks if is valid phase time.
	 * @return true, if is valid phase time
	 */
	public boolean isValidPhaseTime() {
		return validPhaseTime;
	}

	/**
	 * Sets the thermostat id.
	 * @param thermostatId the new thermostat id
	 */
	public void setThermostatId(final int thermostatId) {
		this.thermostatId = thermostatId;
	}

	/**
	 * Sets the connected.
	 * @param connected the new connected
	 */
	public void setConnected(final boolean connected) {
		this.connected = connected;
	}

	/**
	 * Sets the thermostat name.
	 * @param thermostatName the new thermostat name
	 */
	public void setThermostatName(final String thermostatName) {
		this.thermostatName = thermostatName;
	}

	/**
	 * Sets the modes.
	 * @param modes the new modes
	 */
	public void setModes(final String[] modes) {
		this.modes = modes;
	}

	/**
	 * Sets the subsribed algorithms.
	 * @param subsribedAlgorithms the new subsribed algorithms
	 */
	public void setSubsribedAlgorithms(final String[] subsribedAlgorithms) {
		this.subsribedAlgorithms = subsribedAlgorithms;
	}

	/**
	 * Sets the active algorithm.
	 * @param activeAlgorithm the new active algorithm
	 */
	public void setActiveAlgorithm(final String activeAlgorithm) {
		this.activeAlgorithm = activeAlgorithm;
	}

	/**
	 * Sets the next phase time.
	 * @param nextPhaseTime the new next phase time
	 */
	public void setNextPhaseTime(final String nextPhaseTime) {
		this.nextPhaseTime = nextPhaseTime;
	}

	/**
	 * Sets the valid phase time.
	 * @param validPhaseTime the new valid phase time
	 */
	public void setValidPhaseTime(final boolean validPhaseTime) {
		this.validPhaseTime = validPhaseTime;
	}

	/**
	 * Checks if is data collection.
	 * @return true, if is data collection
	 */
	public boolean isDataCollection() {
		return dataCollection;
	}

	/**
	 * Sets the data collection.
	 * @param dataCollection the new data collection
	 */
	public void setDataCollection(final boolean dataCollection) {
		this.dataCollection = dataCollection;
	}

    /**
     * Gets the location name.
     * @return the location name
     */
    public String getLocationName() {

        return locationName;
    }

    /**
     * Sets the location name.
     * @param locationName the new location name
     */
    public void setLocationName(String locationName) {

        this.locationName = locationName;
    }

    /**
     * Gets the savings value.
     * @return the savings value
     */
    public String getSavingsValue() {

        return savingsValue;
    }

    /**
     * Sets the savings value.
     * @param savingsValue the new savings value
     */
    public void setSavingsValue(String savingsValue) {

        this.savingsValue = savingsValue;
    }
}
