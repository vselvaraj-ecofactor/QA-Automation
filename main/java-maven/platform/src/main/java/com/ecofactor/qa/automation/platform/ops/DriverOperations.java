/*
 * DriverOperations.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops;

import org.openqa.selenium.WebDriver;

import com.ecofactor.qa.automation.platform.exception.DeviceException;

/**
 * The Interface DriverOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DriverOperations {

    /**
     * Load device driver.
     * @throws DeviceException the device exception
     */
    void loadDeviceDriver() throws DeviceException;

    /**
     * Perform device cleanup.
     * @throws DeviceException the device exception
     */
    void performCleanup() throws DeviceException;

    /**
     * Close device driver.
     */
    void closeDeviceDriver();

    /**
     * Gets the device driver.
     * @return the device driver
     */
    WebDriver getDeviceDriver();

    /**
     * Take screen shot.
     * @param fileNames the file names
     * @throws DeviceException the device exception
     */
    void takeScreenShot(final String ...fileNames) throws DeviceException;

    /**
	 * Take custom screen shot.
	 * @param fileNames the file names
	 */
    void takeCustomScreenShot(final String ...fileNames);

    /**
     * Switch to web view.
     */
    void switchToWebView();

    /**
     * Switch to native.
     */
    void switchToNative();

}
