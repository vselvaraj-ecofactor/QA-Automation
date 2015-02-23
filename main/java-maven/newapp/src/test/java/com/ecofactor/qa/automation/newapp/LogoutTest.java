/*
 * AwayTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;

/**
 * The Class LogoutTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class LogoutTest extends AbstractTest {

    /** The Constant SANITY1. */
    private final static String SANITY1 = "sanity1";

    /**
     * Logout actions.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { SANITY1 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void logout(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        doLogout();
        Assert.assertTrue(loginPage.isPageLoaded(), "Login Page Not Loaded");
    }

    /**
     * Logout and Login actions.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { SANITY1 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void logoutAndLogin(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        doLogout();
        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isPageLoaded(), "Login Failed after Logout");
    }

}
