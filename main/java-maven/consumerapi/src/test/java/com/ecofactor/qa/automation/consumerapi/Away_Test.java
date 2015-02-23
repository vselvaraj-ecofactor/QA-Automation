/*
 * Away_Test.java
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
 * Test class for testing Away API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class Away_Test extends AbstractTest {

    @Inject
    ConsumerApiURL consumerApiURL;

    /**
     * Test_fetching_ away_ data_using_valid_location id.
     * @param username the username
     * @param password the password
     * @param locId the loc id
     */
    @Test(dataProvider = "dollarSavings", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_Away_Data_using_valid_locationID(final String username, final String password, final String locationId) {

        final Response response = consumerApiURL.getAwaySavings(locationId, securityCookie);
        displayAPIResponse(response, "Away");
    }
}
