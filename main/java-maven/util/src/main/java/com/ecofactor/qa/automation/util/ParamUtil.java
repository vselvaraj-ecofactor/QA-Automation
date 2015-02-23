/*
 * ParamUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;






/**
 * The Class ParamUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ParamUtil {

    /**
     * Gets the browser.
     * 
     * @return the browser
     */
    public static String getBrowser() {

        return System.getProperty("browser");
    }

    /**
     * Gets the mobile device id.
     * 
     * @return the mobile device id
     */
    public static String getMobileDeviceId() {

        return System.getProperty("mobile.device.id");
    }

    /**
     * Gets the mobile device name.
     * 
     * @return the mobile device name
     */
    public static String getMobileDeviceName() {

        return System.getProperty("mobile.device.name");
    }
    
    /**
     * Gets the appium start.
     * @return the appium start
     */
    public static String getAppiumStart() {

        return System.getProperty("mobile.start.appium");
    }

    /**
     * Checks for browser param.
     * 
     * @return true, if successful
     */
    public static boolean hasBrowserParam() {

        return getBrowser() != null && !getBrowser().isEmpty();
    }

    /**
     * Checks for mobile param.
     * 
     * @return true, if successful
     */
    public static boolean hasMobileParam() {

        return (getMobileDeviceName() != null && !getMobileDeviceName().isEmpty());
    }
    
    /**
     * Checks for start appium.
     * 
     * @return true, if successful
     */
    public static boolean hasStartAppium() {

        String appiumStart = getAppiumStart();
        return (appiumStart != null && !appiumStart.isEmpty() && appiumStart.equalsIgnoreCase("true"));
    }

   

    /**
     * Gets the test env.
     * 
     * @return the test env
     */
    public static String getTestEnv() {

        return System.getProperty("env");
    }

    /**
     * Checks for environment param.
     * 
     * @return true, if successful
     */
    public static boolean hasEnvironmentParam() {

        return  getTestEnv() != null && !getTestEnv().isEmpty();
    }

    /**
     * Gets the log level.
     * 
     * @return the log level
     */
    /*public static LogLevel getLogLevel() {

        final String logLevel = System.getProperty("logLevel");
        return logLevel == null ? LogLevel.HIGH : LogLevel.valueOf(logLevel.toUpperCase(Locale.getDefault()));
    }*/
}

