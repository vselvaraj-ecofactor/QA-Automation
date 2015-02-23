/*
 * ThermostatData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

/**
 * The Class ThermostatData.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatData {

    private int thermostatId;
    private boolean connected;
    private String[] modes = new String[2];
    private String[] subsribedAlgorithms;
    private String activeAlgorithm;
    private String nextPhaseTime;
    private boolean validPhaseTime;

    /**
     * Gets the thermostat id.
     * @return the thermostat id
     */
    public int getThermostatId() {

        return thermostatId;
    }

    /**
     * Sets the thermostat id.
     * @param thermostatId the new thermostat id
     */
    public void setThermostatId(int thermostatId) {

        this.thermostatId = thermostatId;
    }

    /**
     * Checks if is connected.
     * @return true, if is connected
     */
    public boolean isConnected() {

        return connected;
    }

    /**
     * Sets the connected.
     * @param connected the new connected
     */
    public void setConnected(boolean connected) {

        this.connected = connected;
    }

    /**
     * Gets the modes.
     * @return the modes
     */
    public String[] getModes() {

        return modes;
    }

    /**
     * Sets the modes.
     * @param modes the new modes
     */
    public void setModes(String[] modes) {

        this.modes = modes;
    }

    /**
     * Gets the subsribed algorithms.
     * @return the subsribed algorithms
     */
    public String[] getSubsribedAlgorithms() {

        return subsribedAlgorithms;
    }

    /**
     * Sets the subsribed algorithms.
     * @param subsribedAlgorithms the new subsribed algorithms
     */
    public void setSubsribedAlgorithms(String[] subsribedAlgorithms) {

        this.subsribedAlgorithms = subsribedAlgorithms;
    }

    /**
     * Gets the active algorithm.
     * @return the active algorithm
     */
    public String getActiveAlgorithm() {

        return activeAlgorithm;
    }

    /**
     * Sets the active algorithm.
     * @param activeAlgorithm the new active algorithm
     */
    public void setActiveAlgorithm(String activeAlgorithm) {

        this.activeAlgorithm = activeAlgorithm;
    }

    /**
     * Gets the next phase time.
     * @return the next phase time
     */
    public String getNextPhaseTime() {

        return nextPhaseTime;
    }

    /**
     * Sets the next phase time.
     * @param nextPhaseTime the new next phase time
     */
    public void setNextPhaseTime(String nextPhaseTime) {

        this.nextPhaseTime = nextPhaseTime;
    }

	/**
	 * Checks if is valid phase time.
	 * @return true, if is valid phase time
	 */
	public boolean isValidPhaseTime() {
		return validPhaseTime;
	}

	/**
	 * Sets the valid phase time.
	 * @param validPhaseTime the new valid phase time
	 */
	public void setValidPhaseTime(boolean validPhaseTime) {
		this.validPhaseTime = validPhaseTime;
	}
}
