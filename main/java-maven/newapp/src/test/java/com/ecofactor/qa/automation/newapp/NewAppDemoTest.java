/*
 * NewAppDemoTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class NewAppDemoTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class NewAppDemoTest extends AbstractTest {

    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /**
     * Api demo.
     * @throws Exception
     */
    @Test(groups = { "sanity1" }, priority = 1)
    public void validlogin() throws Exception {

        doLogin();
        doLogout();
    }

    /**
     * Verify target temperature is displayed.
     * @throws Exception
     */
    @Test(groups = { "sanity1" }, priority = 2)
    public void verifyTargetTemperatureIsDisplayed() throws Exception {

        doLogin();
        thPageUI.getTargetTemperature();
        doLogout();
    }

    /**
     * Verify mode change and set point changes.
     * @throws Exception
     */
    @Test(groups = { "sanity1" }, priority = 5)
    public void verifyModeChangeAndSetPointChanges() throws Exception {

        doLogin();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.setPointChange(1);
        thCtrlOpsPage.setPointChange(-1);
        thCtrlOpsPage.closeThermostatControl();
        doLogout();
    }

    /**
     * Verify current temperature.
     * @throws Exception
     */
    @Test(groups = { "sanity1" }, priority = 4)
    public void verifyCurrentTemperature() throws Exception {

        doLogin();
        WaitUtil.tinyWait();
        thPageUI.getCurrentTemperature();
        doLogout();
    }

    /**
     * Verify current temperature.
     * @throws Exception
     */
    @Test(groups = { "sanity1" }, priority = 3)
    public void checkForMultipleThermostats() throws Exception {

        doLogin();
        thPageUI.getCurrentThermostatName();
        thPageOps.swipe("left");
        thPageUI.getCurrentThermostatName();
        thPageOps.swipe("right");
        doLogout();
    }

    /**
     * Invalid login.
     * @throws Exception
     */
    @Test(groups = { "sanity1" }, priority = 6)
    public void invalidLogin() throws Exception {

        if (loginPage.isLoggedIn()) {
            doLogout();
        }
        String userName = mobileConfig.get(INVALID_USERNAME);
        String password = mobileConfig.get(INVALID_PASSWORD);
        loginPage.login(userName, password);
        try {
            thPageUI.isPageLoaded();
            Assert.assertTrue(false);
        } catch (AssertionError e) {
            LogUtil.setLogString("Login failed since invalid credentials are provided.", true);
            Assert.assertTrue(true);
        }
    }

    /**
     * Do login.
     * @throws Exception
     */
    private void doLogin() throws Exception {

        if (loginPage.isLoggedIn()) {
            doLogout();
        }
        loginPage.isPageLoaded();

        String userName = System.getProperty("userName");
        String password = System.getProperty("password");

        userName = userName != null && !userName.isEmpty() ? userName : mobileConfig.get(USERNAME);
        password = password != null && !password.isEmpty() ? password : mobileConfig.get(PASSWORD);

        System.setProperty("userName", userName);
        System.setProperty("password", password);

        loginPage.login(userName, password);
        thPageUI.isPageLoaded();
    }
}
