/*
 * OnBoardTestConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class OnBoardTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UploadOneUserTestConfig extends BaseConfig {

	public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";


    /**
     * Instantiates a new on board test config.
     * @param driverConfig the driver config
     */
    @Inject
    public UploadOneUserTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("uploadOneUserTest.properties");
    }
}
