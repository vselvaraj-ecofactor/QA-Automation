/*
 * LoginConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class LoginConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LoginConfig extends BaseConfig {

    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String SUBMIT = "submit";
    public static final String LOGIN_URL = "loginUrl";

    /**
     * Instantiates a new login config.
     * @param driverConfig the driver config
     */
    @Inject
    public LoginConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("qtclogin.properties");
    }
}
