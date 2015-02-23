/*
 * PartnerConfig.java
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

public class PartnerConfig extends BaseConfig {

    public final static String DEFAULT_USERNAME = "validUser";
    public final static String DEFAULT_PASSWORD = "validPassword";

    public final static String CREATE_NEW_PARTNER = "createNewUserValue";
    public final static String SAVE = "save";
    public final static String RESET = "reset";
    public final static String CANCEL = "cancel";
    public final static String PARTNER_NAME_ID = "partnerId";
    public final static String PARTNER_EMAIL_ID = "emailId";
    public final static String PARTNER_STATUS_ID = "statusId";
    public final static String ACTIVE_PARTNER = "active";
    public final static String INACTIVE_PARTNER = "inactive";
    public final static String PARTNER_STREET_ID_1 = "addressId1";
    public final static String PARTNER_STREET_ID_2 = "addressId2";
    public final static String PARTNER_CITY_ID = "cityId";
    public final static String PARTNER_STATE_ID = "stateId";
    public final static String PARTNER_ZIP_ID = "zipId";
    public final static String PARTNER_COUNTRY_ID = "countryId";
    public final static String PARTNER_CONTACT_NAME = "primaryContactNameId";
    public final static String PARTNER_PHONE_ID = "phoneId";
    public final static String AVAILABLE_PARTNER_TYPE = "availablePartnerType";
    public final static String ASSIGNED_PARTNER_TYPE = "assignedPartnerType";
    public final static String PARTNER_TYPE_VALUE_1 = "valueOne";
    public final static String PARTNER_TYPE_VALUE_2 = "valueTwo";
    public final static String PARTNER_TYPE_VALUE_3 = "valueThree";
    public final static String PARTNER_TYPE_VALUE_4 = "valueFour";
    public final static String PARTNER_TYPE_VALUE_5 = "valueFive";
    public final static String PARTNER_TYPE_VALUE_6 = "valueSix";
    public final static String PARTNER_CONTACT_INFO = "contactInfoId";
    public final static String PARTNER_TYPES = "partnerRoleId";
    public final static String VALID_PARTNER = "validPartnerName";
    public final static String PARTNER_FEILD_ID = "partnerNameId";
    public final static String PARTNER_EMAIL_FEILD_ID = "partnerEmailId";
    public final static String INVALID_PARTNER = "inValidPartnerName";
    public final static String PARTNER_VALID_EMAIL = "validEmailId";
    public final static String PARTNER_INVALID_EMAIL = "invalidEmailId";
    public final static String PARTNER_TABLE = "user";
    public final static String NEXT = "Next";
    public final static String PREVIOUS = "Previous";
    public final static String FIRST = "First";
    public final static String LAST = "Last";
    public final static String PAGINATION_CLASS = "paginationClassName";
    public final static String VALID_PARTNER_NAME = "valid.partner";
    public final static String VALID_EMAIL_ID = "invalid.Email";
    public final static String INVALID_PARTNER_NAME = "invalid.parnter";
    public final static String INVALID_EMAIL_ID = "valid.Email";

    /**
     * Instantiates a new partner test config.
     * @param driverConfig the driver config
     */
    @Inject
    public PartnerConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("partner.properties");
    }
}
