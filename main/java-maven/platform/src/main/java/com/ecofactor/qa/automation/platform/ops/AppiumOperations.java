/*
 * AppiumOperations.java
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
 * The Interface AppiumOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface AppiumOperations {

    /**
     * Start appium server.
     * @throws DeviceException 
     */
    void startAppiumServer() throws DeviceException;
    
    /**
     * Stop appium server.
     */
    void stopAppiumServer();    
}
