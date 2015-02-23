/*
 * LoginConfig.java
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
 * The Class LoginConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteLoginConfig extends BaseConfig {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String SUBMIT = "submitLogin";
    public static final String SUBMIT_RESET = "submitReset";
    public static final String LOGIN_PAGE_WARNING_MSG = "loginPageWarningMsg";
    public static final String LOGIN_PAGE_WARNING_MSG_TEXT = "loginPageWarningMsgText";

    public static final String RESET_PASSWORD_LINK = "resetPasswordLink";
    public static final String CANCEL_RESET_PASSWORD = "cancelResetPassword";
    public static final String LOGIN_SUBMIT = "loginSubmit";
    public static final String RESET_PASSWORD_EMAILADDRESS = "resetPasswordEmailAddress";
    public static final String RESET_PASSWORD_EMAILADDRESS_CHECK = "resetPasswordEmailAddressCheck";
    public static final String RESET_PASSWORD = "resetPasswordPassword";
    public static final String RESET_PASSWORD_PASSWORD_CHECK = "resetPasswordPasswordCheck";
    public static final String RESET_PASSWORD_BLANK_ERROR_MSG = "resetPasswordBlankErrorMsg";
    public static final String RESET_PASSWORD_USERNAME_MISMATCH_ERROR_MSG = "resetPasswordUsernameMismatchErrorMsg";

    public static final String MICROSOFT_MAIL_SIGNIN_WITH_DIFFERENT_USERID = "signInWithDifferentUserId";
    public static final String MICROSOFT_MAIL_SIGNIN_BTN = "signInBtn";
    public static final String MICROSOFT_MAIL_USERID_FIELD = "userIdField";
    public static final String MICROSOFT_MAIL_PSWD_FIELD = "pswdField";
    public static final String IN_BOX = "inBox";

    public static final String CHANGE_PASSWORD_PAGE_PASSWORD = "changePasswordPagePassword";
    public static final String CHANGE_PASSWORD_RETYPE_PASSWORD = "changePasswordRetypePassword";
    public static final String CHANGE_PASSWORD_SUBMIT = "changePasswordSubmit";
    public static final String PASSWORD_CHANGED_MESSAGE = "passwordChangedMessage";

    public static final String HOME_PAGE_IDENTIFIER = "homePageIdentifier";

    //For proxy logging in consumer portal.
    public static final String CONSUMER_USER_ID = "consumerUserId";
    public static final String CONSUMER_PASSWORD = "consumerPassword";
    public static final String CONSUMER_SUBMIT_LOGIN = "consumerSubmit";

    public static final String CONSUMER_LOGOUT_LINK = "consumerLogoutLink";
    public static final String CONSUMER_WELCOME_TEXT = "consumerWelcomeText";

    public static final String CONSUMER_OLD_PASSWORD = "consumerOldPassword";
    public static final String CONSUMER_NEW_PASSWORD1 = "consumerNew_password1";
    public static final String CONSUMER_NEW_PASSWORD2 = "consumerNew_password2";
    public static final String CONSUMER_CHANGE_PSWD_BTN = "consumerChangePwdButton";
    public static final String CONSUMER_LOGIN_LOGO = "consumerLoginLogo";
    /**
     * Instantiates a new login config.
     * @param driverConfig the driver config
     */
    @Inject
    public InsiteLoginConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("insiteLogin.properties");
    }
}
