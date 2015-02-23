/*
 * AuthenticatorTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.util.UtilModule;

/**
 * The Class AuthenticatorTest.
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class AuthenticatorTest extends AbstractTest {

    /**
     * Test_ authentication.
     * @param username the username
     * @param password the password
     */
    @Test(dataProvider = "authenticator", dataProviderClass = ApiDataProvider.class)
    public void test_Authentication(final String username, final String password) {

        Assert.assertNotNull(securityCookie, "Authentication Failed - cookie not created");
    }

}
