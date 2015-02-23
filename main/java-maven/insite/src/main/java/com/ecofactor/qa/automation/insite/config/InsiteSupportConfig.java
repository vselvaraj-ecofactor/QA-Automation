/*
 * SupportConfig.java
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
 * The Class SupportConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteSupportConfig extends BaseConfig {

    public static final String ACCOUNT_EMAIL = "findAccountByEmail";
    public static final String ACCOUNT_PHONE = "findAccountByPhone";
    public static final String ACCOUNT_ID = "findAccountById";
    public static final String ACCOUNT_STREET_ADDR = "findAccountByStreet";

    /**
     * The Constant FIND_BUTTON.
     */
    public static final String FIND_BUTTON = "findButtonValue";
    public static final String SEARCH_RESULT_CLASS = "searchResultClass";
    public static final String INSTALLED_HARDWARE_MENU = "installedHardwareMenuField";
    public static final String THERMOSTAT_ACTIVE = "thermostatActive";
    public static final String THERMOSTAT_INACTIVE = "thermostatInActive";
    public static final String BUTTON_SET = "buttonSetField";

    // Support Edit Page features
    public static final String HOME_OWNER_LINK = "homeOwnerLink";
    public static final String PHONE_NUMBER_LINK = "phoneNumberLink";
    public static final String ENERGY_EFF_REFRESH = "energyEffeciencyRef";
    public static final String ECP_CORE_LINK = "ecpCoreLink";
    public static final String USER_NAME_LINK = "userNameLink";
    public static final String EMAIL_ADDR_LINK = "emailAddrLink";
    public static final String ELECTRICITY_RATE_LINK = "ectricityRateLink";
    public static final String GAS_RATE_LINK = "gasRateLink";
    public static final String PROXY_LINK = "proxyLink";
    public static final String ENERGY_EFF_CHECK = "energyEfficiencyCheckbox";

    public static final String ELECTRICITY_RATE = "newElectricityRate";
    public static final String GAS_RATE = "newGasRate";
    public static final String ERROR = "ef_error";
    public static final String OK_BTN = "okBtn";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String Email_ID = "userEmailDlg";
    public static final String PHONE_ERROR = "phone_error_msg";

    /**
     * Instantiates a new support config.
     * @param driverConfig the driver config
     */
    @Inject
    public InsiteSupportConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("insiteSupport.properties");
    }
}
