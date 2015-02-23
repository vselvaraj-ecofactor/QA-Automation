/*
 * ECPTestConfig.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
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

public class ECPTestConfig extends BaseConfig {

    public static final String VALID_LOGIN_USER="validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD="validLogin_password";
    
    public static final String CONSERV_PARTNER="conservationPartner";
    public static final String INSTALLERS="installers";
    public static final String SERVICE_PROVIDERS="serviceProviders";
    public static final String UTILITIES="utilities";
    public static final String CUSTOMER_CARES="customerCares";
    public static final String GATEWAY_MODEL="gatewayModel";
    public static final String TSTAT_MODEL="tstatModel";
    
    public static final String ECOFACTOR="ecofactor";
    public static final String ECP_CORE_NAME = "ecpcorename";
    public static final String EMPTY_STRING = "empty";		
    
    /**
     * Instantiates a new ECP test config.
     * @param driverConfig the driver config
     */
    @Inject
    public ECPTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("EcpTest.properties");
    }
}
