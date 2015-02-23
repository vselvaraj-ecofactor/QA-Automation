/*
 * LoginTestConfig.java
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
 * LoginTestConfig provides the login test data.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteLoginTestConfig extends BaseConfig {

    public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";
    public static final String INVALID_LOGIN_USER = "invalidUserName";
    public static final String INVALID_LOGIN_PASSWORD = "invalidPassword";
    public static final String CHANGE_PASSWORD_USERNAME = "inisteChangePassword_UserName";
    public static final String CHANGE_PASSWORD_EMAILURL = "inisteChangePassword_emailUrl";
    public static final String CHANGE_PASSWORD_EMAILID = "inisteChangePassword_emailId";
    public static final String CHANGE_PASSWORD_EMAIL_PWD = "inisteChangePassword_emailPassword";
    public static final String CHANGE_PASSWORD_NEW_PWD = "inisteChangePassword_newPassword";
    public static final String CHANGE_PASSWORD_SUBJECT = "inisteChangePassword_mailSubject";
    public static final String COMCAST_LOGIN_USER = "comcastLogin_userId";
    public static final String COMCAST_LOGIN_PASSWORD = "comcastLogin_password";

    /**
     * Instantiates a new login test config.
     */
    @Inject
    public InsiteLoginTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("insiteLoginTest.properties");
    }
}
