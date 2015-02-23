/*
 * SupportTestConfig.java
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
 * The Class SupportTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteSupportTestConfig extends BaseConfig {

    public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";
    public static final String ACCOUNT_EMAIL_ID = "accountLookUpEmailId";
    public static final String ACCOUNT_PHONE_NO = "accountLookUpPhoneNo";
    public static final String ACCOUNT_STREET_ADDR = "accountLookUpStreetAddr";
    public static final String TSTAT_CONN_EMAIL_ID = "thermostatConnectivityCheck_emailId";
    public static final String RESET_PSWD_SEARCH_EMAIL_ID = "resetPwd_searchEmailId";
    public static final String RESET_PSWD_SEARCH_USER_NAME = "resetPwd_searchUserName";
    public static final String RESET_PSWD_EMAIL_URL = "resetPwd_emailURL";
    public static final String RESET_PSWD_EMAIL_ID = "resetPwd_emailId";
    public static final String RESET_PSWD_EMAIL_PSWD = "resetPwd_emailPwd";
    public static final String RESET_PSWD_SUBJECT = "resetPwd_subject";
    public static final String RESET_PSWD_NEW_PSWD = "resetPwd_newPassword";
    public static final String RESET_PSWD_NEW_APP_URL = "resetPwd_newAppUrl";
    public static final String USER_ADMIN = "userNameAdmin";
    public static final String USER_PSWD = "passwordAdmin";
    public static final String LOCATION_INFO_USERID = "locationInfo_userId";
    public static final String LOCATION_INFO_PSWD = "locationInfo_password";
    public static final String LOCATION_INFO_EMAIL = "locationInfoEmail";
    public static final String DENY_PROXY_LOGIN = "denyProxyLogin";

    public static final String NVE_USER = "nveUser";
    public static final String NVE_PASSWORD = "nvePassword";

    public static final String COMCAST_LOGIN_USER = "comcastLogin_userId";
    public static final String COMCAST_LOGIN_PASSWORD = "comcastLogin_password";

    public static final String ELECTRICITY_ERROR_MSG = "electricity_error_msg";
    public static final String GAS_ERROR_MSG = "gas_error_msg";
    public static final String PHONE_ERROR_MSG = "phone_error_msg";
    public static final String EMAIL_ERROR_MSG = "email_error_msg";
    public static final String INVALID_PHONE_NUMBER = "invalidPhoneNumber";
    public static final String LOCATION_INFO_ADDRESS = "address";
    public static final String LOCATION_INFO_DATE = "date";

    public static final String NON_EXSISTING_EMAIL = "nonExsistingEmail";
    public static final String NON_EXSISTING_PHONE_NUMBER = "nonExsistingPhoneNumber";
    public static final String NON_EXSISTING_STREET_ADDR = "nonExsistingStreet";
    public static final String NON_EXSISTING_ID = "nonExsistingId";

    public static final String VALID_EMAIL = "validEmail";
    public static final String VALID_PHONE_NO = "validPhoneNumber";
    public static final String VALID_STREET_ADDRESS = "streetAddress";
    public static final String VALID_ID = "validId";
    
    public static final String PARTIAL_EMAIL = "partialEmail";
    public static final String PARTIAL_PHONE_NO = "partialPhoneNo";
    public static final String PARTIAL_STREET_ADDRESS = "partialStreetAddr";

    /**
     * Instantiates a new support test config.
     * @param driverConfig the driver config
     */
    @Inject
    public InsiteSupportTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("insiteSupportTest.properties");
    }

}
