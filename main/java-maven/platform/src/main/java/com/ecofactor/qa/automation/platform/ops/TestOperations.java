/*
 * TestOperations.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops;

import com.ecofactor.qa.automation.platform.exception.DeviceException;

/**
 * The Interface TestOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface TestOperations extends DriverOperations {

    /**
     * Initialize.
     * @throws DeviceException
     */
    void initialize() throws DeviceException;

    /**
     * Cleanup.
     * @throws DeviceException
     */
    void cleanup() throws DeviceException;

    /**
     * Close.
     */
    void close();

    /**
     * Sets the up listeners for device.
     */
    void setUpListenersForDevice();

	/**
	 * Gets the device brand model.
	 *
	 * @param property the property
	 * @return the device brand model
	 */
	String getDeviceBrandModel(String property);

    /**
     * Display app info.
     */
    void displayAppInfo();
}
