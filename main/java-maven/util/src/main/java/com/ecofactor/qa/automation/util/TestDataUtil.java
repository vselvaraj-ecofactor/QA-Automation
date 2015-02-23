/*
 * TestDataUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * The Class TestDataUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestDataUtil extends BaseConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestDataUtil.class);
    private Properties testProperties = new Properties();
    private Properties purgeProperties = new Properties();
    private static final String TEST_PROP = "_test.properties";
    private static final String PURGE_PROP = "_purge.properties";
    private String env = "";

    @Inject
    public TestDataUtil(DriverConfig driverConfig) {
        env = driverConfig.get(DriverConfig.ENV);
        try {
            testProperties.load(new FileInputStream(env + TEST_PROP));
            purgeProperties.load(new FileInputStream(env + PURGE_PROP));
        } catch (Exception e) {
            LOGGER.warn("Error " + e.getMessage());
        }
    }

    /**
     * Sets the test property.
     * @param key the key
     * @param value the value
     */
    public void setTestProperty(String key, Object value) {

        testProperties.put(key, value.toString());
        writeTestProp();
    }

    /**
     * Gets the test property.
     * @param key the key
     * @return the test property
     */
    public Object getTestProperty(String key) {

        LOGGER.info(key + " : " + testProperties.get(key));
        return testProperties.get(key);
    }

    /**
     * Gets the string test property.
     * @param key the key
     * @return the string test property
     */
    public String getStringTestProperty(String key) {

        return testProperties.get(key).toString();
    }

    /**
     * Gets the double test property.
     * @param key the key
     * @return the double test property
     */
    public Double getDoubleTestProperty(String key) {

        LOGGER.info(key + " : " + testProperties.get(key));
        double doubleVal = Double.parseDouble(testProperties.get(key).toString());
        return doubleVal;
    }

    /**
     * Gets the integer test property.
     * @param key the key
     * @return the integer test property
     */
    public Integer getIntegerTestProperty(String key) {

        LOGGER.info(key + " : " + testProperties.get(key));
        int intVal = Integer.parseInt(testProperties.get(key).toString());
        return intVal;
    }

    /**
     * Clear test properties.
     */
    public void clearTestProperties() {

        testProperties.clear();
    }

    /**
     * Sets the purge property.
     * @param key the key
     * @param value the value
     */
    public void setPurgeProperty(String key, Object value) {

        purgeProperties.put(key, value);
        writePurgeProp();
    }

    /**
     * Gets the purge property.
     * @param key the key
     * @return the purge property
     */
    public Object getPurgeProperty(String key) {

        return purgeProperties.get(key);
    }

    /**
     * Clear purge properties.
     */
    public void clearPurgeProperties() {

        purgeProperties.clear();
    }

    /**
     * Sets the calendar property.
     * @param key the key
     * @param value the value
     */
    public void setCalendarProperty(String key, Calendar value) {

        testProperties.put(key, DateUtil.formatToUTC(value, DateUtil.DATE_FMT_FULL));
        writeTestProp();
    }

    /**
     * Gets the calendar property.
     * @param key the key
     * @return the calendar property
     */
    public Calendar getCalendarProperty(String key) {

        LOGGER.info(key + " : " + testProperties.get(key));
        String timeStamp = (String) testProperties.get(key);
        Calendar calendar = DateUtil.parseToCalendar(timeStamp, DateUtil.DATE_FMT_FULL);
        return calendar;
    }

    /**
     * Sets the map property.
     * @param key the key
     * @param phaseMap the phase map
     */
    public void setMapProperty(final String key, final Map<String, String> phaseMap) {

        StringBuffer phaseValue = new StringBuffer();
        Iterator<Entry<String, String>> phaseIterator = phaseMap.entrySet().iterator();
        while (phaseIterator.hasNext()) {
            Entry<String, String> pairs = phaseIterator.next();
            phaseValue.append(pairs.getKey() + "###" + pairs.getValue() + ",");
        }
        testProperties.put(key, phaseValue.toString());
        writeTestProp();
    }

    /**
     * Gets the map property.
     * @param propKey the prop key
     * @return the map property
     */
    public Map<String, String> getMapProperty(final String propKey) {

        HashMap<String, String> phaseMap = new HashMap<String, String>();
        String phaseValue = (String) testProperties.get(propKey);
        String[] valArray = phaseValue.split(",");
        for (String phaseArray : valArray) {
            int hashindex = phaseArray.indexOf("###");
            String key = phaseArray.substring(0, hashindex);
            String value = phaseArray.substring(hashindex + 3, phaseArray.length());
            phaseMap.put(key, value);

        }
        return phaseMap;
    }

    /**
     * Write test prop.
     */
    private void writeTestProp() {

        try {
            testProperties.store(new FileOutputStream(env+TEST_PROP), "");
        } catch (Exception e) {
            LOGGER.warn("Error " + e.getMessage());
        }
    }

    /**
     * Write purge prop.
     */
    private void writePurgeProp() {

        try {
            testProperties.store(new FileOutputStream(env+TEST_PROP), "");
        } catch (Exception e) {
            LOGGER.warn("Error " + e.getMessage());
        }
    }
}
