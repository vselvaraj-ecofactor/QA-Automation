/*
 * InsiteSupportDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import static com.ecofactor.qa.automation.insite.config.InsiteSupportTestConfig.*;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.InsiteSupportTestConfig;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SupportDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteSupportDataProvider {

    /** The support test config. */
    @Inject
    private static InsiteSupportTestConfig supportTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validLogin")
    public static Object[][] validLogin(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Search by email id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByEmailId")
    public static Object[][] searchByEmailId(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(ACCOUNT_EMAIL_ID) } };
        return data;
    }

    /**
     * Search by phone no.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByPhoneNo")
    public static Object[][] searchByPhoneNo(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(ACCOUNT_PHONE_NO) } };
        return data;
    }

    /**
     * Test thermostat connection.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testThermostatConnection")
    public static Object[][] testThermostatConnection(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(TSTAT_CONN_EMAIL_ID) } };
        return data;
    }

    /**
     * Verify account and location info.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "verifyAccountAndLocationInfo")
    public static Object[][] verifyAccountAndLocationInfo(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(LOCATION_INFO_EMAIL) } };
        return data;
    }

    /**
     * Deny proxy login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "denyProxyLogin")
    public static Object[][] denyProxyLogin(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(DENY_PROXY_LOGIN) } };
        return data;
    }

    /**
     * Support tab for nv esuer.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "supportTabForNVEsuer")
    public static Object[][] supportTabForNVEsuer(Method m) {

        Object[][] data = { { supportTestConfig.get(NVE_USER), supportTestConfig.get(NVE_PASSWORD) } };
        return data;
    }

    /**
     * Reset pswd.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "resetPswd")
    public static Object[][] resetPswd(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(RESET_PSWD_SEARCH_EMAIL_ID),
                supportTestConfig.get(RESET_PSWD_SEARCH_USER_NAME),
                supportTestConfig.get(RESET_PSWD_EMAIL_URL),
                supportTestConfig.get(RESET_PSWD_EMAIL_ID),
                supportTestConfig.get(RESET_PSWD_EMAIL_PSWD),
                supportTestConfig.get(RESET_PSWD_SUBJECT),
                supportTestConfig.get(RESET_PSWD_NEW_APP_URL),
                supportTestConfig.get(RESET_PSWD_NEW_PSWD) } };
        return data;
    }

    /**
     * Comcast credentials.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "comcastCrednetials")
    public static Object[][] comcastCredentials(Method m) {

        Object[][] data = { { supportTestConfig.get(COMCAST_LOGIN_USER),
                supportTestConfig.get(COMCAST_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Verify electricity rate.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "verifyElectricityRate")
    public static Object[][] verifyElectricityRate(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(LOCATION_INFO_EMAIL),
                supportTestConfig.get(ELECTRICITY_ERROR_MSG) } };
        return data;
    }

    /**
     * Verify gas rate.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "verifyGasRate")
    public static Object[][] verifyGasRate(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(LOCATION_INFO_EMAIL), supportTestConfig.get(GAS_ERROR_MSG) } };
        return data;
    }

    /**
     * Verify phone number.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "verifyPhoneNumber")
    public static Object[][] verifyPhoneNumber(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(LOCATION_INFO_EMAIL),
                supportTestConfig.get(INVALID_PHONE_NUMBER) } };
        return data;
    }

    /**
     * Verify email id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "verifyEmailID")
    public static Object[][] verifyEmailID(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(LOCATION_INFO_EMAIL), supportTestConfig.get(EMAIL_ERROR_MSG) } };
        return data;
    }

    /**
     * Past installation date.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "pastInstallationDate")
    public static Object[][] pastInstallationDate(Method m) {

        Object[][] data = { { supportTestConfig.get(LOCATION_INFO_USERID),
                supportTestConfig.get(LOCATION_INFO_PSWD),
                supportTestConfig.get(LOCATION_INFO_ADDRESS),
                supportTestConfig.get(LOCATION_INFO_DATE), supportTestConfig.get(EMAIL_ERROR_MSG) } };
        return data;
    }

    /**
     * Search non exsisting email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchNonExsistingEmail")
    public static Object[][] searchNonExsistingEmail(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(NON_EXSISTING_EMAIL) } };
        return data;
    }

    /**
     * Search non exsisting phone no.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchNonExsistingPhoneNo")
    public static Object[][] searchNonExsistingPhoneNo(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(NON_EXSISTING_PHONE_NUMBER) } };
        return data;
    }

    /**
     * Search non exsisting id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchNonExsistingId")
    public static Object[][] searchNonExsistingId(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(NON_EXSISTING_ID) } };
        return data;
    }

    /**
     * Search non exsisting street addr.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchNonExsistingStreet")
    public static Object[][] searchNonExsistingStreetAddr(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(NON_EXSISTING_STREET_ADDR) } };
        return data;
    }

    /**
     * Search by valid email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByValidEmail")
    public static Object[][] searchByValidEmail(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD), supportTestConfig.get(VALID_EMAIL) } };
        return data;
    }

    /**
     * Search by valid phone no.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByValidPhoneNo")
    public static Object[][] searchByValidPhoneNo(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD), supportTestConfig.get(VALID_PHONE_NO) } };
        return data;
    }

    /**
     * Search by valid street address.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByValidStreetAddress")
    public static Object[][] searchByValidStreetAddress(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(VALID_STREET_ADDRESS) } };
        return data;
    }

    /**
     * Search by valid id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByValidId")
    public static Object[][] searchByValidId(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD), supportTestConfig.get(VALID_ID) } };
        return data;
    }
    
    /**
     * Search by partial email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByPartialEmail")
    public static Object[][] searchByPartialEmail(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(TSTAT_CONN_EMAIL_ID), supportTestConfig.get(PARTIAL_EMAIL) } };
        return data;
    }

    /**
     * Search by partial phone no.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByPartialPhoneNo")
    public static Object[][] searchByPartialPhoneNo(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(ACCOUNT_PHONE_NO), supportTestConfig.get(PARTIAL_PHONE_NO) } };
        return data;
    }

    /**
     * Search by partial street address.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "searchByPartialStreetAddress")
    public static Object[][] searchByPartialStreetAddress(Method m) {

        Object[][] data = { { supportTestConfig.get(VALID_LOGIN_USER),
                supportTestConfig.get(VALID_LOGIN_PASSWORD),
                supportTestConfig.get(ACCOUNT_STREET_ADDR), supportTestConfig.get(PARTIAL_STREET_ADDRESS) } };
        return data;
    }

}
