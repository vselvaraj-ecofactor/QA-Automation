/*
 * ThermostatEETest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.util.Calendar;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.ThermostatEEOpsPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatEEUIPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.qtc.QTCModule;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatEETest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class, QTCModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class ThermostatEETest extends AbstractTest {

    /** The tstat page ui. */
    @Inject
    private ThermostatPageUI tstatPageUI;

    /** The tstat page ops. */
    @Inject
    private ThermostatPageOps tstatPageOps;

    /** The tstat eeui page. */
    @Inject
    private ThermostatEEUIPage tstatEEUIPage;

    /** The tsta ee ops page. */
    @Inject
    private ThermostatEEOpsPage tstaEEOpsPage;

    /** The tstat db validation. */
    @Inject
    private TstatDBValidation tstatDBValidation;

    /**
     * Savings energy link displayed.
     * @param userName the user name
     * @param password the password, Row No: 205
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 1, dependsOnMethods = { "loginAndCloseHelp" })
    public void savingsEnergyLinkDisplayed(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        tstatPageUI.isUnderSavingsEnergy();
        Assert.assertTrue(tstatPageUI.isSavingsEnergyLinkDisplayed(),
                "Savings Link is not Displayed");
        doLogout();
    }

    /**
     * Login and close help.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class)
    private void loginAndCloseHelp(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true);
        doLogout();
    }

    /**
     * Savings energy link clickable.
     * @param userName the user name
     * @param password the password, Row No: 206
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 2, dependsOnMethods = { "loginAndCloseHelp" })
    public void savingsEnergyLinkClickable(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        tstatPageUI.isUnderSavingsEnergy();
        Assert.assertTrue(tstatPageUI.isSavingsEnergyLinkClickable(),
                "Savings Link is not Clickable");
        tstatPageOps.clickSavings();
        tstaEEOpsPage.clickClose();
        doLogout();
    }

    /**
     * Ee description message displayed.
     * @param userName the user name
     * @param password the password, Row No: 207
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 3, dependsOnMethods = { "loginAndCloseHelp" })
    public void eeDescriptionMessageDisplayed(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        tstaEEOpsPage.clickSavingsEELink();
        Assert.assertTrue(tstatEEUIPage.isContentDisplayed(),
                "EE Message description is not displayed");
    }

    /**
     * Tstat page loaded after close.
     * @param userName the user name
     * @param password the password, Row No: 217
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 4, dependsOnMethods = { "loginAndCloseHelp" })
    public void eeMessageDisplayedForWhile(final String userName, final String password,
            final Integer thermostatId) {

        Assert.assertTrue(tstatEEUIPage.isContentDisplayed(),
                "EE Message description is not displayed");
        WaitUtil.mediumWait();
        Assert.assertTrue(tstatEEUIPage.isContentDisplayed(),
                "EE Message description is not displayed");
    }

    /**
     * Close button clickable.
     * @param userName the user name
     * @param password the password, Row No: 208
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 5, dependsOnMethods = { "loginAndCloseHelp" })
    public void closeButtonClickable(final String userName, final String password,
            final Integer thermostatId) {

        Assert.assertTrue(tstatEEUIPage.isContentDisplayed(),
                "EE Message description is not displayed");
        tstaEEOpsPage.clickClose();
        doLogout();
    }

    /**
     * Tstat page loaded after close.
     * @param userName the user name
     * @param password the password, Row No: 214
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 6, dependsOnMethods = { "loginAndCloseHelp" })
    public void tstatPageLoadedAfterClose(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page is not loaded");
        doLogout();
    }

    /**
     * Energy saving fade out.
     * @param userName the user name
     * @param password the password, Row No: 155
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 7, dependsOnMethods = { "loginAndCloseHelp" })
    public void energySavingFadeOut(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        thPageUI.isSavingsEnergyLinkDisplayed();
        thPageUI.isScheduleTempDisplayed();
        LogUtil.setLogString("Wait for 20 seconds", true);
        WaitUtil.mediumWait();
        Assert.assertTrue(thPageUI.isScheduleTempDisplayed(), "Schedule Temp is not displayed");
        doLogout();
    }

    /**
     * Energy message4 sec.
     * @param userName the user name
     * @param password the password, Row No: 159
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 8, dependsOnMethods = { "loginAndCloseHelp" })
    public void energyMessage4Sec(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        thPageUI.isSavingsEnergyLinkDisplayed();
        Calendar utcCalendar = DateUtil.getUTCCalendar();
        utcCalendar.add(Calendar.SECOND, 6);
        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        LogUtil.setLogString("UTC TIME : " + currentUTCTime, true);
        LogUtil.setLogString("Wait for 4 seconds", true);
        WaitUtil.fourSec();
        Assert.assertTrue(thPageUI.isScheduleTempDisplayed(), "Schedule Temp not displayed");        
        String latestCurrentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        Calendar utcCalendar2 = DateUtil.getUTCCalendar();
        LogUtil.setLogString("UTC TIME : " + latestCurrentUTCTime, true);
        LogUtil.setLogString("Check the gap in between a  max of 6 seconds", true);
        Assert.assertTrue(utcCalendar2.before(utcCalendar),
                "Taken long time to display the schedule temp");
        doLogout();
    }

    /**
     * Login logout ee.
     * @param userName the user name
     * @param password the password, Row No: 161
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 9, dependsOnMethods = { "loginAndCloseHelp" })
    public void loginLogoutEE(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        thPageUI.isSavingsEnergyLinkDisplayed();
        doLogout();
        loadPage(userName, password, true);
        thPageUI.isUnderSavingsEnergy();
        thPageUI.isSavingsEnergyLinkDisplayed();
        thPageUI.isScheduleTempDisplayed();
        Assert.assertTrue(thPageUI.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Link is not displayed");
        doLogout();
    }

    /**
     * Swirl displayed.
     * @param userName the user name
     * @param password the password, Row No: 171
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 10, dependsOnMethods = { "loginAndCloseHelp" })
    public void swirlDisplayed(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        loadPage(userName, password, true, true);
        LogUtil.setLogString("Wait for 20 secs and check the page is loaded with swirl Icon", true);
        WaitUtil.mediumWait();
        Assert.assertTrue(thPageUI.isUnderSavingsEnergy(), "Savings Energy Link is not displayed");
        doLogout();
    }

    /**
     * Energy saving not displayed.
     * @param userName the user name
     * @param password the password, Row No: 156
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 11, dependsOnMethods = { "loginAndCloseHelp" })
    public void energySavingDisplayedForStSPO(final String userName, final String password,
            final Integer thermostatId) {

        exitToLoginScreen();
        String[] subscribedAlgo = tstatDBValidation.getSubscribedAlgo(thermostatId);
        boolean hasSTSPO = false;
        for (String algoName : subscribedAlgo) {
            if (algoName.contains("SPO") || algoName.contains("ST")) {
                hasSTSPO = true;
                LogUtil.setLogString("Algorithm Name : " + algoName, true);
                break;
            }
        }
        loadPage(userName, password, true, true);
        Assert.assertTrue(hasSTSPO, "ST or SPO is not subscribed");
        Assert.assertTrue(thPageUI.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Link is not subscribed");
        Assert.assertTrue(thPageUI.isUnderSavingsEnergy(), "Savings Energy is not displayed");
        doLogout();
    }

    /**
     * Exit to login screen.
     */
    protected void exitToLoginScreen() {

        if (loginPage.isLoggedIn()) {
            doLogout();
        }
    }
}
