/*
 * DeviceData.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.model;

/**
 * The Class DeviceData.
 */
public class DeviceData {

    private String deviceId;
    private String name;
    private String state;
    private String version;
    private String slave;
    private String deviceNo;
    private String testPlan;

    /**
     * Gets the device id.
     * @return the device id
     */
    public String getDeviceId() {

        return deviceId;
    }

    public String getDeviceNo() {

        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {

        this.deviceNo = deviceNo;
    }

    public String getTestPlan() {

        return testPlan;
    }

    public void setTestPlan(String testPlan) {

        this.testPlan = testPlan;
    }

    /**
     * Sets the device id.
     * @param deviceId the new device id
     */
    public void setDeviceId(final String deviceId) {

        this.deviceId = deviceId;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets the name.
     * @param name the new name
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * Gets the state.
     * @return the state
     */
    public String getState() {

        return state;
    }

    /**
     * Sets the state.
     * @param state the new state
     */
    public void setState(final String state) {

        this.state = state;
    }

    /**
     * Gets the version.
     * @return the version
     */
    public String getVersion() {

        return version;
    }

    /**
     * Sets the version.
     * @param apiLevel the new version
     */
    public void setVersion(final String apiLevel) {

        this.version = apiLevel;
    }

    /**
     * Gets the slave.
     * @return the slave
     */
    public String getSlave() {

        return slave;
    }

    /**
     * Sets the slave.
     * @param slave the new slave
     */
    public void setSlave(final String slave) {

        this.slave = slave;
    }

}
