/*
 * DesktopOperations.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;
import static com.ecofactor.qa.automation.util.WaitUtil.largeWait;
import static com.ecofactor.qa.automation.util.WaitUtil.smallWait;
import static com.ecofactor.qa.automation.util.WaitUtil.waitUntil;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.util.SeleniumDriverUtil;

/**
 * The Class DesktopOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DesktopOperations extends AbstractDriverOperations {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DesktopOperations.class);

    /**
     * Perform cleanup.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#performCleanup()
     */
    @Override
    public void performCleanup() throws DeviceException {

        close();
        loadDeviceDriver();
        loadPage();
    }

    /**
     * Creates the device driver.
     * @return the web driver
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractDriverOperations#createDeviceDriver()
     */
    @Override
    protected WebDriver createDeviceDriver() throws DeviceException {

        final String browser = System.getProperty("browser");
        setLogString("Browser: " + browser, true);
        setLogString("Create instance for WebDriver", true);
        final WebDriver driver = SeleniumDriverUtil.createBrowserDriver(browser);
        setLogString("Maximize the Browser window", true);
        driver.manage().window().maximize();
        setLogString("Wait for few seconds after driver initialize", true);
        final String driverWait = System.getProperty("defaultBrowserWaitInMilliSecs");
        LOGGER.info("Wait for driver startup " + driverWait, true);
        if (driverWait == null || driverWait.isEmpty()) {
            largeWait();
        } else {
            waitUntil(Long.valueOf(driverWait));
        }
        return driver;
    }

    /**
     * Cleanup driver for close.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractDriverOperations#cleanupDriverForClose()
     */
    @Override
    protected void cleanupDriverForClose() {

        // TODO Auto-generated method stub
    }

    /**
     * Load page.
     */
    protected void loadPage() {

        final String url = System.getProperty("url");
        setLogString("Load Url: " + url, true);
        getDeviceDriver().navigate().to(url);
        smallWait();
    }

    /**
     * Gets the device brand model.
     * @param property the property
     * @return the device brand model
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#getDeviceBrandModel(java.lang.String)
     */
    @Override
    public String getDeviceBrandModel(final String property) {

        return "NA";
    }

    /**
     * Display app info.
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#displayAppInfo()
     */
    @Override
    public void displayAppInfo() {

        // TODO Auto-generated method stub
    }

    /**
     * switch to web view.
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#switchToWebView()
     */
    @Override
    public void switchToWebView() {

        // TODO Auto-generated method stub
    }

    /**
     * switch to native.
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#switchToNative()
     */
    @Override
    public void switchToNative() {

        // TODO Auto-generated method stub
    }

}
