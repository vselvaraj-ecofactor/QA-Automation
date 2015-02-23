/*
 * ThermostatDetails.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.model;

/**
 * The Class ThermostatDetails.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatDetails {

    private Integer thermostatId;
    private String thermostatName;
    private String thermostatStatus;
    private boolean heatMode;
    private boolean coolMode;
    private boolean offMode;
    private String subscribedAlgorithm;
    private String selectedMode;
    private String currentProgramName;

    /**
     * Gets the thermostat status.
     * @return the thermostat status
     */
    public String getThermostatStatus() {

        return thermostatStatus;
    }

    /**
     * Sets the thermostat status.
     * @param thermostatStatus the new thermostat status
     */
    public void setThermostatStatus(final String thermostatStatus) {

        this.thermostatStatus = thermostatStatus;
    }

    /**
     * Checks if is heat mode.
     * @return true, if is heat mode
     */
    public boolean isHeatMode() {

        return heatMode;
    }

    /**
     * Sets the heat mode.
     * @param heatMode the new heat mode
     */
    public void setHeatMode(final boolean heatMode) {

        this.heatMode = heatMode;
    }

    /**
     * Checks if is cool mode.
     * @return true, if is cool mode
     */
    public boolean isCoolMode() {

        return coolMode;
    }

    /**
     * Sets the cool mode.
     * @param coolMode the new cool mode
     */
    public void setCoolMode(final boolean coolMode) {

        this.coolMode = coolMode;
    }

    /**
     * Checks if is off mode.
     * @return true, if is off mode
     */
    public boolean isOffMode() {

        return offMode;
    }

    /**
     * Sets the off mode.
     * @param offMode the new off mode
     */
    public void setOffMode(final boolean offMode) {

        this.offMode = offMode;
    }

    /**
     * Gets the thermostat id.
     * @return the thermostat id
     */
    public Integer getThermostatId() {

        return thermostatId;
    }

    /**
     * Sets the thermostat id.
     * @param thermostatId the new thermostat id
     */
    public void setThermostatId(final Integer thermostatId) {

        this.thermostatId = thermostatId;
    }

    /**
     * Gets the subscribed algorithm.
     * @return the subscribed algorithm
     */
    public String getSubscribedAlgorithm() {

        return subscribedAlgorithm;
    }

    /**
     * Sets the subscribed algorithm.
     * @param subscribedAlgorithm the new subscribed algorithm
     */
    public void setSubscribedAlgorithm(final String subscribedAlgorithm) {

        this.subscribedAlgorithm = subscribedAlgorithm;
    }

    /**
     * Gets the thermostat name.
     * @return the thermostat name
     */
    public String getThermostatName() {

        return thermostatName;
    }

    /**
     * Sets the thermostat name.
     * @param thermostatName the new thermostat name
     */
    public void setThermostatName(final String thermostatName) {

        this.thermostatName = thermostatName;
    }

    /**
     * Gets the selected mode.
     * @return the selected mode
     */
    public String getSelectedMode() {

        return selectedMode;
    }

    /**
     * Sets the selected mode.
     * @param selectedMode the new selected mode
     */
    public void setSelectedMode(final String selectedMode) {

        this.selectedMode = selectedMode;
    }

    /**
     * Gets the current program name.
     * @return the current program name
     */
    public String getCurrentProgramName() {

        return currentProgramName;
    }

    /**
     * Sets the current program name.
     * @param currentProgramName the new current program name
     */
    public void setCurrentProgramName(final String currentProgramName) {

        this.currentProgramName = currentProgramName;
    }
}
