

/*
 * UserTestConfig.java
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
 * The Class UserTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserNegativeTestConfig extends BaseConfig {
    public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "emailAddress";
    public static final String ACCOUNT_USER_NAME = "accountUserName";
    public static final String ACTIVE_USER = "activeUser";
    public static final String INACTIVE_USER = "inactiveUser";
    public static final String PARTNER_TYPE = "partnerType";
    public static final String PARTNER = "partner";
    public static final String STREET_ADD_1 = "streetAddress1";
    public static final String STREET_ADD_2 = "streetAddress2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String COUNTRY = "country";
    public static final String MOBILE_PHONE = "mobilePhoneNumber";
    public static final String HOME_PHONE = "homePhoneNumber";
    public static final String FAX = "fax";
    public static final String AVAILABLE_ROLE = "availableRole";
    public static final String EMAIL_URL = "createNewUser_emailURL";
    public static final String EMAIL_ID = "createNewUser_emailId";
    public static final String EMAIL_PSWD = "createNewUser_emailPwd";
    public static final String EMAIL_SUB = "createNewUser_subject";
    public static final String NEW_PSWD = "createNewUser_newPassword";

    public static final String RESET_PSWD_EMAIL_URL = "createNewUser_emailURL";
    public static final String RESET_PSWD_EMAIL_ID = "resetPswdEmailId";
    public static final String RESET_PSWD_EMAIL_PSWD = "resetPswd_emailPwd";
    public static final String RESET_PSWD_EMAIL_SUB = "resetPwd_subject";
    public static final String RESET_PSWD_FOR_USER = "resetPswdForUser";
    public static final String RESET_PSWD_USERNAME = "resetPswd_Username";
    public static final String RESET_PSWD_NEW_PSWD = "resetNewUser_newPassword";

    public static final String ASSIGN_ROLE_USER_NAME = "assignRoleUser_Name";
    public static final String ASSIGN_ROLE_USERNAME = "assignRoleUsername";

    // Edit Account Details
    public static final String EDIT_FIRST_NAME = "editFirstName";
    public static final String EDIT_LAST_NAME = "editLastName";
    public static final String EDIT_EMAIL = "editEmailAddress";
    public static final String EDIT_ACTIVE_USER = "editActiveUser";

    public static final String ERROR_MSG = "error_msg";
    public static final String EMAIL_ADDRESS = "email_id";
    public static final String INVALID_EMAIL_ADDRESS = "inavalid_email";
    
    public static final String EMPTY_STRING = "emptyString";
   
    public static final String NON_EXSISTING_EMAIL = "nonExsistingEmail";
    public static final String  NON_EXSISTING_NAME = "nonExsistingName";

    /**
     * Instantiates a new user test config.
     * @param driverConfig the driver config
     */
    @Inject
    public UserNegativeTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("userNegativeTest.properties");
    }
}
