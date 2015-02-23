/*
 * FeatureUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.util.DriverConfig;

/**
 * FeatureUtil is used to record and generate the test and its features map.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class FeatureUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(FeatureUtil.class);
    private String testMethod;
    private List<String> features;
    private Map<String, List<String>> testFeaturesMap = new HashMap<>();

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
    public void setFeatures(List<String> features) {

        this.features = features;
    }

    /**
     * Gets the test features map.
     * @return the test features map
     */
    public Map<String, List<String>> getTestFeaturesMap() {

        return testFeaturesMap;
    }

    /**
     * Sets the test features map.
     * @param testFeaturesMap the test features map
     */
    public void setTestFeaturesMap(Map<String, List<String>> testFeaturesMap) {

        this.testFeaturesMap = testFeaturesMap;
    }

    /**
     * Inits the.
     * @param test the test
     */
    public void init(String test) {

        if (test == null || test.trim().equals("")) {
            LOGGER.error("Invalid Test Method " + test);
        } else {
            if (testFeaturesMap.containsKey(test)) {
                LOGGER.error("Duplicate Test Method " + test);
            }

            this.testMethod = test;
            this.features = new ArrayList<>();
        }
    }

    /**
     * Done.
     */
    public void done() {

        if (testMethod == null || testMethod.trim().equals("")) {
            LOGGER.error("Test Method not initialized and features recorded");
        }

        boolean logFeature = Boolean.parseBoolean(System.getProperty("logFeature", "false"));
        if(logFeature) {
            DriverConfig.setLogString("Required Features for " + testMethod, true);
            DriverConfig.setLogString("---------------------------------------------------------------------------------", true);
            for(String feature : features) {
                DriverConfig.setLogString(feature, true);
            }
            DriverConfig.setLogString("---------------------------------------------------------------------------------", true);
        }

        this.testFeaturesMap.put(testMethod, features);
        this.testMethod = null;
        this.features = null;
    }

    /**
     * Record.
     * @param feature the feature
     */
    public void record(String feature) {

        if (testMethod == null || testMethod.trim().equals("")) {
            LOGGER.error("Test Method not initialized to record the feature");
        } else if (feature == null || feature.trim().equals("")) {
            LOGGER.error("Invalid feature " + feature);
        } else {
            features.add(feature);
        }
    }
}
