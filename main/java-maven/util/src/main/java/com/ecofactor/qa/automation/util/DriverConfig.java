/*
 * DriverConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import static org.testng.Reporter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

/**
 * DriverConfig is a singleton which uses the driver.properties and loads the selenium webdriver. It
 * is also used to close the webdriver.
 * @author $Author: rvinoopraj $
 * @version $Rev: 25107 $ $Date: 2013-11-11 16:10:31 +0530 (Mon, 11 Nov 2013) $
 */
public class DriverConfig extends BaseConfig {

    public static final String BROWSER = "browser";
    public static final String FIREFOX = "firefox";
    public static final String IE = "ie";
    public static final String CHROME = "chrome";
    public static final String SAFARI = "safari";
    public static final String ENV = "env";
    public static final String CONSOLE = "console";
    public static final String LOG = "log";
    public static final String INFO = "info";
    public static final String WARN = "warn";
    public static final String ERROR = "error";
    public static final String DEFAULT_WAIT_MILLISECS = "defaultBrowserWaitInMilliSecs";
    public static final String BROWSER_REOPEN_INTERVAL = "reOpenBrowserTestCaseInterval";
    public static final String FORCE_PREPARE = "forcePrepare";
    // private WebDriver driver;
    private String browser;
    private String forcePrepare;
    private static String consoleOutput;

    private static List<WebDriver> threadLocal = new ArrayList<>();
    // private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<String> logStringList = new ThreadLocal<String>();

    public static HashMap<Long, String> reporterLogMap = new HashMap<Long, String>();
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DriverConfig.class);

    /**
     * Instantiates a new driver config.
     */
    public DriverConfig() {

        load("driver.properties");
        browser = System.getProperty(BROWSER);
        environment = System.getProperty(ENV);
        setForcePrepare(System.getProperty(FORCE_PREPARE));
        consoleOutput = System.getProperty(CONSOLE);
        if (environment == null || environment.length() == 0) {
            environment = get(ENV);
        } else {
            config.put(ENV, environment);
        }

        if (browser == null || browser.length() == 0) {
            browser = get(BROWSER);
        } else {
            config.put(BROWSER, browser);
        }

        Logger logger = Logger.getRootLogger();
        if (config.get(LOG).equals(ERROR)) {
            logger.setLevel(Level.ERROR);
        } else if (config.get(LOG).equals(WARN)) {
            logger.setLevel(Level.WARN);
        } else if (config.get(LOG).equals(INFO)) {
            logger.setLevel(Level.INFO);
        } else {
            logger.setLevel(Level.ERROR);
        }
    }

    /**
     * Load driver.
     */
    public synchronized void loadDriver() {

        WebDriver driver = LocalDriverFactory.createInstance(browser);
        threadLocal.add(driver);
        LocalDriverManager.setWebDriver(driver);
        LocalDriverManager.getDriver().manage().window().maximize();
        setLogString("Wait for few seconds after driver initialize", true);

        String driverWait = System.getProperty(DEFAULT_WAIT_MILLISECS);
        setLogString("Wait for driver startup " + driverWait, true);
        if (driverWait != null && !driverWait.isEmpty()) {
            WaitUtil.waitUntil(Long.valueOf(driverWait));
        } else {
            WaitUtil.largeWait();
        }

    }

    /**
     * Close driver.
     */
    public static void closeDriver() {

        closeAllWebDriver();

        // LocalDriverManager.getDriver().manage().deleteAllCookies();
        // LocalDriverManager.getDriver().quit();
    }

    /**
     * Close all web driver.
     */
    public static void closeAllWebDriver() {

        logTestResults();
        for (WebDriver thWebDriver : threadLocal) {
            // thWebDriver.manage().deleteAllCookies();
            thWebDriver.quit();
        }

    }

    /**
     * Log test results.
     */
    public static void logTestResults() {

        LOGGER.error("Console writeline " + consoleOutput);
        if (consoleOutput != null && consoleOutput.equalsIgnoreCase("true")) {
            LOGGER.error("Duplicate copy of logs to poulate the content in testNG");
            Iterator<Entry<Long, String>> iterator = reporterLogMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<Long, String> pairs = iterator.next();
                String[] val = pairs.getValue().split("\n");
                for (String string : val) {
                    log(string, true);
                }

            }
        }
    }

    /**
     * Gets the driver.
     * @return the driver
     */
    public static synchronized WebDriver getDriver() {

        /*
         * if (driver == null) { loadDriver(); } return driver;
         */

        return LocalDriverManager.getDriver();
    }

    /**
     * Gets the force prepare.
     * @return the force prepare
     */
    public String getForcePrepare() {

        return forcePrepare;
    }

    /**
     * Sets the force prepare.
     * @param forcePrepare the new force prepare
     */
    public void setForcePrepare(String forcePrepare) {

        this.forcePrepare = forcePrepare;
    }

    /**
     * Gets the log string.
     * @return the log string
     */
    public static String getLogString() {

        return logStringList.get();
    }

    /**
     * Sets the log string.
     * @param logStringVal the log string val
     * @param val the val
     */
    public static void setLogString(String logStringVal, boolean val) {

        if (consoleOutput == null || consoleOutput.equalsIgnoreCase("false")) {
            Reporter.log(logStringVal, val);
        } else if (consoleOutput != null && consoleOutput.equalsIgnoreCase("true")) {
            LOGGER.error(logStringVal);
            logStringVal = new String(logStringVal + "\n");
            if (getLogString() == null) {
                logStringList.set(logStringVal);
                reporterLogMap.put(Thread.currentThread().getId(), logStringVal);
            } else {
                reporterLogMap.put(Thread.currentThread().getId(), getLogString() + logStringVal);
                logStringList.set(getLogString() + logStringVal);
            }
        }
    }
}
