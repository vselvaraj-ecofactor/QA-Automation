/*
 * User_Test.java
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
 * Test class for testing User API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class User_Test extends AbstractTest {

    @Inject
    ConsumerApiURL consumerApiURL;

    // Authenticate -> User Info -> Location(s) -> Thermostat(s) -> TStat schedule(s)
    /**
     * Test_fetching_ user_ data_using_valid_user id.
     * @param username the username
     * @param password the password
     * @param userId the user id
     */
    @Test(dataProvider = "userData", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_User_Data_using_valid_userID(final String username,
            final String password, final String userId) {

        final Response responseWithSecCookie = consumerApiURL
                .getUserSavings(userId, securityCookie);
        displayAPIResponse(responseWithSecCookie, "User");
    }
}