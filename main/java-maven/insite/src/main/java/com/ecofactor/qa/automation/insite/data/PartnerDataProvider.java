/*
 * PartnerDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import static com.ecofactor.qa.automation.insite.config.PartnerConfig.*;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.PartnerConfig;
import com.ecofactor.qa.automation.insite.config.PartnerTestConfig;

import com.google.inject.Inject;

/**
 * The Class PartnerDataProvider.
 * @author Aximsoft
 */
public class PartnerDataProvider {

    /** The partner config. */
    @Inject
    private static PartnerConfig partnerConfig;

    /** The partner config. */
    @Inject
    private static PartnerTestConfig partnerTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "defaultUser")
    public static Object[][] defaultUser(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD) } };
        return data;
    }

    /**
     * Create active partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createPartner")
    public static Object[][] createPartner(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Create Inactive partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createInactivePartner")
    public static Object[][] createInactivePartner(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_INACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4)

        } };
        return data;
    }

    /**
     * User test config.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "withoutPartnerName")
    public static Object[][] withoutPartnerName(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * User test config.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "withoutPartnerType")
    public static Object[][] withoutPartnerType(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER)

        } };
        return data;
    }

    /**
     * User test config.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "withoutPartnerNameType")
    public static Object[][] withoutPartnerNameType(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER)

        } };
        return data;
    }

    /**
     * Valid partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findValidPartner")
    public static Object[][] findValidPartner(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(VALID_PARTNER) } };
        return data;
    }

    /**
     * InValid partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findInValidPartner")
    public static Object[][] findInValidPartner(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(INVALID_PARTNER) } };
        return data;
    }

    /**
     * valid Email Partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findPartnerByValidEmail")
    public static Object[][] findPartnerByValidEmail(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(PARTNER_VALID_EMAIL) } };
        return data;
    }

    /**
     * invalid Email Partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findPartnerByInValidEmail")
    public static Object[][] findPartnerByInValidEmail(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(PARTNER_INVALID_EMAIL) } };
        return data;
    }

    /**
     * Skip fields and validate.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "skipFieldsAndValidate")
    public static Object[][] skipFieldsAndValidate(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(PARTNER_NAME_ID),
                partnerConfig.get(ACTIVE_PARTNER), partnerConfig.get(PARTNER_STREET_ID_2),
                partnerConfig.get(PARTNER_STATE_ID), partnerConfig.get(PARTNER_ZIP_ID),
                partnerConfig.get(PARTNER_PHONE_ID), partnerConfig.get(PARTNER_TYPE_VALUE_1)

        } };
        return data;
    }

    /**
     * Fill partner type.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "fillPartnerType")
    public static Object[][] fillPartnerType(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(PARTNER_TYPE_VALUE_2) } };
        return data;
    }

    /**
     * Save without email id.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutEmailId")
    public static Object[][] saveWithoutEmailId(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without street address1.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutStreetAddress")
    public static Object[][] saveWithoutStreetAddress1(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without city.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutCity")
    public static Object[][] saveWithoutCity(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without state.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutState")
    public static Object[][] saveWithoutState(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without zip.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutZip")
    public static Object[][] saveWithoutZip(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without country.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutCountry")
    public static Object[][] saveWithoutCountry(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without primary contact.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutPrimaryContact")
    public static Object[][] saveWithoutPrimaryContact(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Save without phone number.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "saveWithoutPhoneNumber")
    public static Object[][] saveWithoutPhoneNumber(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4) } };
        return data;
    }

    /**
     * Find valid partner invalid email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findValidPartnerInvalidEmail")
    public static Object[][] findValidPartnerInvalidEmail(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(VALID_PARTNER_NAME),
                partnerConfig.get(VALID_EMAIL_ID) } };
        return data;
    }

    /**
     * Find in valid partner valid email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "findInValidPartnerValidEmail")
    public static Object[][] findInValidPartnerValidEmail(Method m) {

        Object[][] data = { { partnerConfig.get(DEFAULT_USERNAME),
                partnerConfig.get(DEFAULT_PASSWORD), partnerConfig.get(INVALID_PARTNER_NAME),
                partnerConfig.get(INVALID_EMAIL_ID) } };
        return data;
    }
}
