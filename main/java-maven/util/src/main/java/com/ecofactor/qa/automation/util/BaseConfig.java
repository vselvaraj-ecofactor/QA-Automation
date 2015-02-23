/*
 * BaseConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseConfig is the abstract base class for loading the test configuration data from properties
 * file.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class BaseConfig {

    private static Logger logger = LoggerFactory.getLogger(BaseConfig.class);

    protected Properties config = new Properties();
    protected String environment;

    public BaseConfig() {

    }

    /**
     * Instantiates a new base config.
     * @param environment the environment
     */
    public BaseConfig(String environment) {

        this.environment = environment;
    }

    /**
     * Load.
     * @param propertiesFile the properties file
     */
    protected void load(String propertiesFile) {

        try {
            logger.info("Config " + getClass().getName());
            logger.info("Loading " + propertiesFile);
            config.load(getClass().getClassLoader().getResourceAsStream(propertiesFile));
        } catch (Exception e) {
            logger.warn("No generic config file " + propertiesFile);
        }

        try {
            if (environment != null && !environment.equals("")) {
                logger.info("Loading " + environment + "_" + propertiesFile);
                config.load(BaseConfig.class.getClassLoader().getResourceAsStream(environment + "_" + propertiesFile));
            } else {
                logger.warn("Initialize Environment to load the enviroment specific config file " + propertiesFile);
            }
        } catch (Exception e) {
            logger.warn("No Environment specific config file " + environment + "_" + propertiesFile);
        }

        List<String> validProperties = getValidProperties();
        logger.info("Valid " + validProperties);

        @SuppressWarnings("rawtypes")
        Set configProperties = config.keySet();

        for (String validProperty : validProperties) {
            if (!configProperties.contains(validProperty)) {
                logger.warn("Missing Property : " + validProperty);
            }
        }

        for (Object configProperty : configProperties) {
            if (!validProperties.contains(configProperty)) {
                logger.warn("Invalid Property : " + configProperty.toString());
            }
        }
    }

    /**
     * Gets the valid properties.
     * @return the valid properties
     */
    private List<String> getValidProperties() {

        List<String> properties = new ArrayList<String>();
        Field[] fields = this.getClass().getFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())
                    && Modifier.isPublic(f.getModifiers())) {
                try {
                    properties.add(f.get(null).toString());
                } catch (Exception e) {
                    logger.error("Error getting property name " + f.getName(), e);
                }
            }
        }

        return properties;
    }

    /**
     * Gets the config properties.
     * @return the config properties
     */
    public List<String> getConfigProperties() {

        List<String> properties = new ArrayList<String>();
        for(Object key : config.keySet()) {
            properties.add(key.toString());
        }

        return properties;
    }

    /**
     * Gets the.
     * @param key the key
     * @return the string
     */
    public String get(String key) {

        logger.info(key + " : " + config.getProperty(key));
        return config.getProperty(key);
    }

    /**
     * Gets the int.
     * @param key the key
     * @return the int
     */
    public Integer getInt(String key) {

        logger.info(key + " : " + config.getProperty(key));
        return Integer.valueOf(config.getProperty(key));
    }

    /**
	 * Gets the double.
	 *
	 * @param key the key
	 * @return the double
	 */
    public Double getDouble(String key) {

        logger.info(key + " : " + config.getProperty(key));
        return Double.valueOf(config.getProperty(key));
    }

    /**
     * Gets the environment.
     * @return the environment
     */
    public String getEnvironment() {

        return environment;
    }
}
