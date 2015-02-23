/*
 * OpsTestConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.opsscript.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class OpsTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class OpsTestConfig extends BaseConfig {

    public static final String SUBSCRIPTION_ALGOS = "subscriptionAlgos";
    public static final String SUBSCRIPTION_CALCULATION_NUMBER = "subscriptionCalculationNumber";

    public static final String COMCAST_SUB_ALGOS = "comcastSubAlgos";
    public static final String COMCAST_CALCULATION_NUMBER = "comcastCalculationNumber";
    public static final String COMCAST_ECP_CORE = "comcastECPCore";

    public static final String NON_COMCAST_SUB_ALGOS = "nonComcastSubAlgos";
    public static final String NON_COMCAST_CALCULATION_NUMBER = "nonComcastCalculationNumber";
    public static final String NON_COMCAST_ECP_CORE = "nonComcastECPCore";

    /**
     * Instantiates a new ops test config.
     * @param driverConfig the driver config
     */
    @Inject
    public OpsTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("opsScriptTest.properties");
    }

}
