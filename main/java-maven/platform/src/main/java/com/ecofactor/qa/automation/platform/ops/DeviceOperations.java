/*
 * DeviceOperations.java
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
 * The Interface DeviceOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DeviceOperations extends DriverOperations {

    /**
     * Gets the device list.
     * @return the device list
     */
    String[] getDeviceList();

    /**
     * Gets the device info.
     * @param deviceId the device id
     * @return the device info
     */
    String[] getDeviceInfo(final String deviceId);

    /**
     * Creates the device mapping.
     * @param deviceId the device id
     * @throws DeviceException 
     */
    void createDeviceMapping(String deviceId) throws DeviceException;

}
