/*
 * UserRoleTestConfig.java
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
 * The Class UserRoleTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserRoleTestConfig extends BaseConfig {
    public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";
    public static final String ECP_USER = "ecpUser";
    public static final String ECP_PASSWORD = "ecpUserPwd";
    public static final String INSTALL_USER = "instalUser";
    public static final String INSTALL_PASSWORD = "installPswd";
    public static final String SERVICE_USER = "serviceUser";
    public static final String SERVICE_PASSWORD = "servicePswd";
    public static final String UTIL_USER = "utilUser";
    public static final String UTIL_PASSWORD = "utilPswd";
    public static final String CUSTOM_USER = "customUser";
    public static final String CUSTOM_PASSWORD = "customPswd";
    public static final String EF_ADMIN_USERID = "efAdminUserId";
    public static final String EF_ADMIN_PSWD = "efAdminPswd";

    public static final String CUSTOM_USR_ADD_USERID = "customUserAdd_UserId";
    public static final String CUSTOM_USR_ADD_PASSWORD = "customUserAdd_password";
    public static final String CUSTOM_USR_REMVE_USERID = "customUserRemve_UserId";
    public static final String CUSTOM_USR_REMVE_PASSWORD = "customUserRemve_password";
    /**
     * Instantiates a new user role test config.
     * @param driverConfig the driver config
     */
    @Inject
    public UserRoleTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("userRoleTest.properties");
    }

}
