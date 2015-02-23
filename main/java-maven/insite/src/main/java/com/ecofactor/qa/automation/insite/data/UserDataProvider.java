/*
 * UserDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.UserNegativeTestConfig;
import com.ecofactor.qa.automation.insite.config.UserTestConfig;
import com.google.inject.Inject;

/**
 * The Class UserDataProvider.
 * @author $Author: vprasannaa $
 * @version $Rev: 33616 $ $Date: 2015-02-19 16:51:21 +0530 (Thu, 19 Feb 2015) $
 */
public class UserDataProvider {

    /** The user test config. */
    @Inject
    private static UserTestConfig userTestConfig;

    /** The user negative test config. */
    @Inject
    private static UserNegativeTestConfig userNegativeTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validLogin")
    public static Object[][] validLogin(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Valid login for ui.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validLoginForUI")
    public static Object[][] validLoginForUI(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.SEARCH_EMAILID) } };
        return data;
    }

    /**
     * Reset pswd.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "resetPswd")
    public static Object[][] resetPswd(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.RESET_PSWD_FOR_USER),
                userTestConfig.get(UserTestConfig.RESET_PSWD_USERNAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.RESET_PSWD_EMAIL_ID),
                userTestConfig.get(UserTestConfig.RESET_PSWD_EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.RESET_PSWD_EMAIL_SUB),
                userTestConfig.get(UserTestConfig.RESET_PSWD_NEW_PSWD) } };
        return data;
    }

    /**
     * User test config.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUser")
    public static Object[][] UserTestConfig(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIRST_NAME),
                userTestConfig.get(UserTestConfig.LAST_NAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.EMAIL_ID),
                userTestConfig.get(UserTestConfig.EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.EMAIL_SUB),
                userTestConfig.get(UserTestConfig.EMAIL),
                userTestConfig.get(UserTestConfig.ACCOUNT_USER_NAME),
                userTestConfig.get(UserTestConfig.ACTIVE_USER),
                userTestConfig.get(UserTestConfig.PARTNER_TYPE),
                userTestConfig.get(UserTestConfig.PARTNER),
                userTestConfig.get(UserTestConfig.STREET_ADD_1),
                userTestConfig.get(UserTestConfig.STREET_ADD_2),
                userTestConfig.get(UserTestConfig.CITY), userTestConfig.get(UserTestConfig.STATE),
                userTestConfig.get(UserTestConfig.ZIP), userTestConfig.get(UserTestConfig.COUNTRY),
                userTestConfig.get(UserTestConfig.MOBILE_PHONE),
                userTestConfig.get(UserTestConfig.HOME_PHONE),
                userTestConfig.get(UserTestConfig.FAX),
                userTestConfig.get(UserTestConfig.AVAILABLE_ROLE),
                userTestConfig.get(UserTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Reset new user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "resetNewUser")
    public static Object[][] resetNewUser(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIRST_NAME),
                userTestConfig.get(UserTestConfig.LAST_NAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.EMAIL_ID),
                userTestConfig.get(UserTestConfig.EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.EMAIL_SUB),
                userTestConfig.get(UserTestConfig.EMAIL),
                userTestConfig.get(UserTestConfig.ACCOUNT_USER_NAME),
                userTestConfig.get(UserTestConfig.ACTIVE_USER),
                userTestConfig.get(UserTestConfig.PARTNER_TYPE),
                userTestConfig.get(UserTestConfig.PARTNER),
                userTestConfig.get(UserTestConfig.STREET_ADD_1),
                userTestConfig.get(UserTestConfig.STREET_ADD_2),
                userTestConfig.get(UserTestConfig.CITY), userTestConfig.get(UserTestConfig.STATE),
                userTestConfig.get(UserTestConfig.ZIP), userTestConfig.get(UserTestConfig.COUNTRY),
                userTestConfig.get(UserTestConfig.MOBILE_PHONE),
                userTestConfig.get(UserTestConfig.HOME_PHONE),
                userTestConfig.get(UserTestConfig.FAX),
                userTestConfig.get(UserTestConfig.AVAILABLE_ROLE)

        } };
        return data;
    }

    /**
     * Edits the user account details.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "editUserAccountDetails")
    public static Object[][] editUserAccountDetails(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.SEARCH_EMAILID),
                userTestConfig.get(UserTestConfig.EDIT_FIRST_NAME),
                userTestConfig.get(UserTestConfig.EDIT_LAST_NAME),
                userTestConfig.get(UserTestConfig.EDIT_EMAIL),
                userTestConfig.get(UserTestConfig.EDIT_ACTIVE_USER) } };
        return data;
    }

    /**
     * Assign role to user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "assignRoleToUser")
    public static Object[][] assignRoleToUser(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.ASSIGN_ROLE_USER_NAME),
                userTestConfig.get(UserTestConfig.ASSIGN_ROLE_USERNAME) } };
        return data;
    }

    /**
     * Invalid email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "invalidEmail")
    public static Object[][] invalidEmail(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.EMAIL_ADDRESS),
                userTestConfig.get(UserTestConfig.INVALID_EMAIL_ADDRESS),
                userTestConfig.get(UserTestConfig.ERROR_MSG) } };
        return data;
    }

    /**
     * Find valid email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findValidEmail")
    public static Object[][] findValidEmail(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIND_VALID_EMAIL), } };
        return data;
    }

    /**
     * Find partial email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findPartialEmail")
    public static Object[][] findPartialEmail(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIND_PARTIAL_EMAIL), } };
        return data;
    }

    /**
     * Find valid user name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findValidUserName")
    public static Object[][] findValidUserName(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIND_VALID_USERNAME), } };
        return data;
    }

    /**
     * Find partial user name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findPartialUserName")
    public static Object[][] findPartialUserName(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIND_PARTIAL_USERNAME), } };
        return data;
    }

    /**
     * Creates the user neagtive.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserNegative")
    public static Object[][] createUserNeagtive(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.INACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user empty first name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserEmptyFirstName")
    public static Object[][] createUserEmptyFirstName(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user empty last name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserEmptyLastName")
    public static Object[][] createUserEmptyLastName(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user empty email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserEmptyEmail")
    public static Object[][] createUserEmptyEmail(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user empty user name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserEmptyUserName")
    public static Object[][] createUserEmptyUserName(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user empty partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserEmptyPartner")
    public static Object[][] createUserEmptyPartner(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user empty role.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserEmptyRole")
    public static Object[][] createUserEmptyRole(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMPTY_STRING),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user enter number in state.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserStateNumber")
    public static Object[][] createUserEnterNumberInState(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user enter number in country.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserCountryNumber")
    public static Object[][] createUserEnterNumberInCountry(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user enter char in zip.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserZipChar")
    public static Object[][] createUserEnterCharInZip(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user enter char in mobile number.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserMobileNumberChar")
    public static Object[][] createUserEnterCharInMobileNumber(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user enter char in phone number.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserPhoneNumberChar")
    public static Object[][] createUserEnterCharInPhoneNumber(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.FAX),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Creates the user enter char in fax.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUserFaxNumberChar")
    public static Object[][] createUserEnterCharInFax(Method m) {

        Object[][] data = { { userNegativeTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.FIRST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.LAST_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_URL),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_ID),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_PSWD),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL_SUB),
                userNegativeTestConfig.get(UserNegativeTestConfig.EMAIL),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACCOUNT_USER_NAME),
                userNegativeTestConfig.get(UserNegativeTestConfig.ACTIVE_USER),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER_TYPE),
                userNegativeTestConfig.get(UserNegativeTestConfig.PARTNER),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_1),
                userNegativeTestConfig.get(UserNegativeTestConfig.STREET_ADD_2),
                userNegativeTestConfig.get(UserNegativeTestConfig.CITY),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.ZIP),
                userNegativeTestConfig.get(UserNegativeTestConfig.COUNTRY),
                userNegativeTestConfig.get(UserNegativeTestConfig.MOBILE_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.HOME_PHONE),
                userNegativeTestConfig.get(UserNegativeTestConfig.STATE),
                userNegativeTestConfig.get(UserNegativeTestConfig.AVAILABLE_ROLE),
                userNegativeTestConfig.get(UserNegativeTestConfig.NEW_PSWD)

        } };
        return data;
    }

    /**
     * Search non exsisting email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchNonExsistingEmail")
    public static Object[][] searchNonExsistingEmail(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.NON_EXSISTING_EMAIL) } };
        return data;
    }

    /**
     * Search non exsisting name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchNonExsistingName")
    public static Object[][] searchNonExsistingName(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.VALID_LOGIN_USER),
                userTestConfig.get(UserTestConfig.VALID_LOGIN_PASSWORD),
                userNegativeTestConfig.get(UserNegativeTestConfig.NON_EXSISTING_NAME) } };
        return data;
    }

    /**
     * Login to emils.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "loginToEmils")
    public static Object[][] loginToEmils(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.SCHEDULELINK_USERNAME),
                userTestConfig.get(UserTestConfig.SCHEDULELINK_PASSWORD), } };
        return data;
    }

    /**
     * Login to schedule link.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "loginToScheduleLink")
    public static Object[][] loginToScheduleLink(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.SCHEDULELINK_USERNAME),
                userTestConfig.get(UserTestConfig.SCHEDULELINK_PASSWORD),
                userTestConfig.get(UserTestConfig.SCHEDULE_STREETADDRESS) } };
        return data;
    }

    /**
     * Login to preconfig link.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "loginToPreconfigLink")
    public static Object[][] loginToPreconfigLink(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.SCHEDULELINK_USERNAME),
                userTestConfig.get(UserTestConfig.SCHEDULELINK_PASSWORD),
                userTestConfig.get(UserTestConfig.PRECONFIG_STREETADDRESS) } };
        return data;
    }

    /**
     * Login to emil admin.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "loginToEmilAdmin")
    public static Object[][] loginToEmilAdmin(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.EMILADMIN_USERNAME),
                userTestConfig.get(UserTestConfig.EMILADMIN_PASSWORD) } };
        return data;
    }

    /**
     * Search emil email id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchEmilEmailId")
    public static Object[][] searchEmilEmailId(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.EMILADMIN_USERNAME),
                userTestConfig.get(UserTestConfig.EMILADMIN_PASSWORD),
                userTestConfig.get(UserTestConfig.SEARCH_EMIL_EMAILID) } };
        return data;
    }

    /**
     * Login to nve users.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "loginToNVEUsers")
    public static Object[][] loginToNVEUsers(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.NVE_USERNAME_1),
                userTestConfig.get(UserTestConfig.NVE_PASSWORD_1),
                userTestConfig.get(UserTestConfig.NVE_USERNAME_2),
                userTestConfig.get(UserTestConfig.NVE_PASSWORD_2) } };
        return data;
    }

    /**
     * Search emil admin email id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchEmilAdminEmailId")
    public static Object[][] searchEmilAdminEmailId(Method m) {

        Object[][] data = { { userTestConfig.get(UserTestConfig.EMILADMIN_USERNAME),
                userTestConfig.get(UserTestConfig.EMILADMIN_PASSWORD),
                userTestConfig.get(UserTestConfig.SEARCH_EMIL_ADMIN_EMAILID),
                userTestConfig.get(UserTestConfig.EDIT_FNAME),
                userTestConfig.get(UserTestConfig.EDIT_LNAME),
                userTestConfig.get(UserTestConfig.EDIT_EMAIL_ADDRESS),
                userTestConfig.get(UserTestConfig.EDIT_ACTIVE_STATUS),
                userTestConfig.get(UserTestConfig.EDIT_STREET_ADDRESS1),
                userTestConfig.get(UserTestConfig.EDIT_STREET_ADDRESS2),
                userTestConfig.get(UserTestConfig.EDIT_CITY),
                userTestConfig.get(UserTestConfig.EDIT_STATE),
                userTestConfig.get(UserTestConfig.EDIT_ZIP),
                userTestConfig.get(UserTestConfig.EDIT_COUNTRY),
                userTestConfig.get(UserTestConfig.EDIT_MOBILE_PHONE_NUM),
                userTestConfig.get(UserTestConfig.EDIT_PHONE_NUM),
                userTestConfig.get(UserTestConfig.EDIT_FAX) } };
        return data;
    }
}
