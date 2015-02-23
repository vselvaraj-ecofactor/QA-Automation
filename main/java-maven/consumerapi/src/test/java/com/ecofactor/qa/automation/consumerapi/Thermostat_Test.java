/*
 * Thermostat_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;
import javax.ws.rs.core.Response;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing Thermostat API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class Thermostat_Test extends AbstractTest {

    @Inject
    ConsumerApiURL consumerApiURL;

    /**
     * Test_fetching_ thermostat_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_Thermostat_Data_using_valid_thermostatID(final String username,
            final String password, final String thermostatId) {

        final Response response = consumerApiURL.getThermostatSavings(thermostatId, securityCookie);
        displayAPIResponse(response, "Thermostat");
    }
}
