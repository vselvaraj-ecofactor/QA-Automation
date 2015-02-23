/*
 * LoginConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class LoginConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SubscriptionConfig extends BaseConfig {

    public static final String SUBSCRIPTION_USER_NAME = "subUsername";
    public static final String SUBSCRIPTION_PASSWORD = "subPassword";
    public static final String SUBSCRIPTION_TH_ID = "subThId";
    public static final String SUBSCRIBE_ALGO = "subscribeAlgo";
    public static final String UNSUBSCRIBE_ALGO = "unsubscribeAlgo";

    /**
     * Instantiates a new login config.
     * @param driverConfig the driver config
     */
    @Inject
    public SubscriptionConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("subscription.properties");
    }
}
