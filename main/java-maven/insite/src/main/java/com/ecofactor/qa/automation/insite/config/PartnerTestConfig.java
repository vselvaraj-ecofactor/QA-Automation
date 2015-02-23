package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

public class PartnerTestConfig extends BaseConfig {

    public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";
    public static final String PARTNER_NAME = "partnerName";
    public static final String EMAIL = "emailAddress";
    public static final String DATA_ACTIVE_PARTNER = "activeUser";
    public static final String DATA_INACTIVE_PARTNER = "inactiveUser";
    public static final String STREET_ADDRESS_1 = "streetAddress1";
    public static final String STREET_ADDRESS_2 = "streetAddress2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String COUNTRY = "country";
    public static final String PRIMARY_CONTACT_NAME = "primaryContactName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String PARTNER_TYPE = "availableRole";
    public final static String DATA_TYPE_VALUE_1 = "valueOne";
    public final static String DATA_TYPE_VALUE_2 = "valueTwo";
    public final static String DATA_TYPE_VALUE_3 = "valueThree";
    public final static String DATA_TYPE_VALUE_4 = "valueFour";
    public final static String DATA_TYPE_VALUE_5 = "valueFive";
    public final static String DATA_TYPE_VALUE_6 = "valueSix";

    /**
     * Instantiates a new user test config.
     * @param driverConfig the driver config
     */
    @Inject
    public PartnerTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("partnerTest.properties");
    }

}
