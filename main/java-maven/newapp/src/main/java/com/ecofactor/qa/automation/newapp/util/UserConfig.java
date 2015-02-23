/*
 * UserConfig.java
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
 * The Class UserConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserConfig {

    private String description;
    private List<String> testMethods;
    private String userIdKey;
    private String passwordKey;
    private String thermostatIdKey;
    private List<String> features;
    private List<String> optionalFeatures;
    private TestData testData;

    /**
     * Gets the description.
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets the description.
     * @param description the new description
     */
    public void setDescription(final String description) {

        this.description = description;
    }

    /**
     * Gets the test methods.
     * @return the test methods
     */
    public List<String> getTestMethods() {

        return testMethods;
    }

    /**
     * Sets the test methods.
     * @param testMethods the new test methods
     */
    public void setTestMethods(final List<String> testMethods) {

        this.testMethods = testMethods;
    }

    /**
     * Gets the user id key.
     * @return the user id key
     */
    public String getUserIdKey() {

        return userIdKey;
    }

    /**
     * Sets the user id key.
     * @param userIdKey the new user id key
     */
    public void setUserIdKey(final String userIdKey) {

        this.userIdKey = userIdKey;
    }

    /**
     * Gets the password key.
     * @return the password key
     */
    public String getPasswordKey() {

        return passwordKey;
    }

    /**
     * Sets the password key.
     * @param passwordKey the new password key
     */
    public void setPasswordKey(final String passwordKey) {

        this.passwordKey = passwordKey;
    }

    /**
     * Gets the thermostat id key.
     * @return the thermostat id key
     */
    public String getThermostatIdKey() {

        return thermostatIdKey;
    }

    /**
     * Sets the thermostat id key.
     * @param thermostatIdKey the new thermostat id key
     */
    public void setThermostatIdKey(final String thermostatIdKey) {

        this.thermostatIdKey = thermostatIdKey;
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
     * Gets the test data.
     * @return the test data
     */
    public TestData getTestData() {

        return testData;
    }

    /**
     * Sets the test data.
     * @param testData the new test data
     */
    public void setTestData(final TestData testData) {

        this.testData = testData;
    }

    /**
     * Gets the optional features.
     * @return the optional features
     */
    public List<String> getOptionalFeatures() {

        return optionalFeatures;
    }

    /**
     * Sets the optional features.
     * @param optionalFeatures the new optional features
     */
    public void setOptionalFeatures(final List<String> optionalFeatures) {

        this.optionalFeatures = optionalFeatures;
    }
}
