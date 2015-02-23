/*
 * SubscriptionDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.data;

import static com.ecofactor.qa.automation.qtc.config.SubscriptionConfig.*;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.qtc.config.SubscriptionConfig;
import com.google.inject.Inject;

/**
 * The Class LoginDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SubscriptionDataProvider {

    @Inject
    private static SubscriptionConfig subscriptionConfig;

    /**
     * Subscribe a thermostat.
     * @return the object[][]
     */
    @DataProvider(name = "subscribeThermostat")
    public static Object[][] subscribeThermostat() {

        String[] arrayOfAccount = subscriptionConfig.get(SUBSCRIBE_ALGO).split(",");

        Object[][] data = new Object[arrayOfAccount.length][4];
        int accountDetails = 0;
        for (Object[] objects : data) {
            objects[0] = subscriptionConfig.get(SUBSCRIPTION_USER_NAME);
            objects[1] = subscriptionConfig.get(SUBSCRIPTION_PASSWORD);
            objects[2] = subscriptionConfig.get(SUBSCRIPTION_TH_ID);
            objects[3] = arrayOfAccount[accountDetails];
            accountDetails++;
        }
        return data;
    }

    /**
     * Unsubscribe a thermostat.
     * @return the object[][]
     */
    @DataProvider(name = "unsubcsribeThermostat")
    public static Object[][] unsubcsribeThermostat() {

        String[] arrayOfAccount = subscriptionConfig.get(UNSUBSCRIBE_ALGO).split(",");

        Object[][] data = new Object[arrayOfAccount.length][4];
        int accountDetails = 0;
        for (Object[] objects : data) {
            objects[0] = subscriptionConfig.get(SUBSCRIPTION_USER_NAME);
            objects[1] = subscriptionConfig.get(SUBSCRIPTION_PASSWORD);
            objects[2] = subscriptionConfig.get(SUBSCRIPTION_TH_ID);
            objects[3] = arrayOfAccount[accountDetails];
            accountDetails++;
        }

        return data;
    }
}
