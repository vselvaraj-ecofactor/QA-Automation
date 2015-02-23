/*
 * InstallationTestConfig.java
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
 * The Class InstallationTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InstallationTestConfig extends BaseConfig {

    public static final String USER_SUPPORT_USRNAME = "validLogin_userId";
    public static final String USER_SUPPORT_PSWD = "validLogin_password";
    public static final String INSTALLATION_HW_USER_NAME = "installationHardwareUserName";
    public static final String INSTALLATION_HW_PSWD = "installationHardwarePassword";
    public static final String INSTALLATION_HW_Street_Addr = "installationHardwareStAddress";
    public static final String INSTALLATION_HW_LOOP = "installationHardwareLoop";

    /**
     * Instantiates a new installation test config.
     * @param driverConfig the driver config
     */
    @Inject
    public InstallationTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("installationTest.properties");
    }
}
