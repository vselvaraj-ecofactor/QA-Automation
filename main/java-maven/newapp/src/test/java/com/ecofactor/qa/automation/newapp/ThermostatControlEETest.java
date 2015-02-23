/*
 * ThermostatControlEETest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.util.WaitUtil.mediumWait;
import static com.ecofactor.qa.automation.util.WaitUtil.waitUntil;

import java.util.Calendar;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.ThermostatEEOpsPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatEEUIPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.newapp.service.AlgorithmService;
import com.ecofactor.qa.automation.newapp.service.DataService;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.util.SeleniumDriverUtil;
import com.ecofactor.qa.automation.qtc.QTCModule;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatControlEETest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class, QTCModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class ThermostatControlEETest extends AbstractTest {

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The tstat eeui page. */
    @Inject
    private ThermostatEEUIPage tstatEEUIPage;

    /** The tstat ee ops page. */
    @Inject
    private ThermostatEEOpsPage tstatEEOpsPage;

    /** The data service. */
    @Inject
    private DataService dataService;

    /** The algorithm service. */
    @Inject
    private AlgorithmService algorithmService;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The Constant SPO_COOL. */
    private final static int SPO_COOL = 190;

    /** The Constant SPO_HEAT. */
    private final static int SPO_HEAT = 191;

    /** The Constant COOL. */
    private final static String COOL = "Cool";

    // 94
    /**
     * Savings energy link displayed.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void savingsEnergyLinkDisplayed(final String userName, final String password,
            Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.closeThermostatControl();
    }

    // 95
    /**
     * Savings energy link clickable.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void savingsEnergyLinkClickable(final String userName, final String password,
            Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.clickSavingsIcon();
        Assert.assertTrue(tstatEEUIPage.isImageDisplayed(),
                "Thermostat Savings image is not loaded");
        tstatEEOpsPage.clickClose();
        thCtrlOpsPage.closeThermostatControl();
    }

    // 96
    /**
     * Ee description msg displayed.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void eeDescriptionMsgDisplayed(final String userName, final String password,
            Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.clickSavingsIcon();
        Assert.assertTrue(tstatEEUIPage.isContentDisplayed(),
                "Thermostat Savings EE page is not loaded with content");
        tstatEEOpsPage.clickClose();
        thCtrlOpsPage.closeThermostatControl();
    }

    // 97
    /**
     * Close btn displayed.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void closeBtnDisplayed(final String userName, final String password, Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.clickSavingsIcon();
        Assert.assertTrue(tstatEEUIPage.isCloseButtonDisplayed(),
                "Thermostat Savings EE close button is not displayed");
        tstatEEOpsPage.clickClose();
        thCtrlOpsPage.closeThermostatControl();
    }

    // 102
    /**
     * Tstat control page after ee close.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void tstatControlPageAfterEEClose(final String userName, final String password,
            Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.clickSavingsIcon();
        Assert.assertTrue(tstatEEUIPage.isCloseButtonDisplayed(),
                "Thermostat Savings EE close button is not displayed");
        tstatEEOpsPage.clickClose();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        thCtrlOpsPage.closeThermostatControl();
    }

    // 106
    /**
     * Ee description longs for while.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void eeDescriptionLongsForWhile(final String userName, final String password,
            Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.clickSavingsIcon();
        Assert.assertTrue(tstatEEUIPage.isCloseButtonDisplayed(),
                "Thermostat Savings EE close button is not displayed");
        LogUtil.setLogString("Wait for 30 secs and check the EE page is still displayed", true);
        WaitUtil.mediumWait();
        Assert.assertTrue(tstatEEUIPage.isCloseButtonDisplayed(),
                "Thermostat Savings EE close button is not displayed");
        tstatEEOpsPage.clickClose();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        thCtrlOpsPage.closeThermostatControl();
    }

    // 55
    /**
     * Ee icon disappaers on mo.
     * @param userName the user name
     * @param password the password
     * @param thermostatIds the thermostat ids
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void eeIconDisappaersOnMO(final String userName, final String password,
            Integer thermostatIds) {

        loadPage(userName, password, true);
        final String thermostatName = thPageUI.getCurrentThermostatName();
        final Integer thermostatIdNew = thDbValidation.getTstatIdForUser(userName, thermostatName);
        subscribeSPO(thermostatIdNew, COOL, SPO_COOL, SPO_HEAT);

        String currentTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
        Calendar currentCalendar = DateUtil.parseToCalendar(currentTime, DateUtil.DATE_FMT_FULL);

        long millisecondsForSpoStarts = dataService.millisecondsForSPOBlock(thermostatIdNew,
                SPO_COOL);
        LogUtil.setLogString(
                "Wait until Spo Block Starts : " + (millisecondsForSpoStarts / 1000 / 60)
                        + ", Current Time : "
                        + DateUtil.format(currentCalendar, DateUtil.DATE_FMT_FULL), true);
        waitUntil(millisecondsForSpoStarts);
        LogUtil.setLogString("2 mins wait ", true);
        WaitUtil.hugeWait();
        long millisecondsForSpoStarts2 = dataService.millisecondsForSPOStart(thermostatIdNew,
                SPO_COOL);
        if (millisecondsForSpoStarts2 > 0) {
            LogUtil.setLogString(
                    "Check and Wait until Spo Block Starts : "
                            + (millisecondsForSpoStarts2 / 1000 / 60) + ", Current Time : "
                            + DateUtil.format(currentCalendar, DateUtil.DATE_FMT_FULL), true);
            waitUntil(millisecondsForSpoStarts2);
        }

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Image/Link is not loaded");
        thCtrlOpsPage.setPointChange(2);
        thCtrlOpsPage.closeThermostatControl();
        Assert.assertFalse(thPageUI.isUnderSavingsEnergy(), "Savings Energy is displayed");
    }

    /**
     * Sets the point reason api.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void setPointReasonAPI(final String userName, final String password, Integer thermostatId) {

        loadPage(userName, password, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
        Assert.assertTrue(thCtrlUIPage.isSavingsEnergyLinkDisplayed(),
                "Savings Energy Link is not displayed");

        WebDriver driver = null;
        try {
            loginPage.setLoggedIn(false);

            LogUtil.setLogString(LogSection.START, "New Browser verfication starts", true);
            driver = createWebDriver();
            loginPage.setDriver(driver);
            thPageOps.setDriver(driver);
            thPageUI.setDriver(driver);
            thCtrlUIPage.setDriver(driver);
            loadPage(userName, password, true);
            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Tstat Control Page is not Opened");
            loginPage.setDriver(null);
            thCtrlOpsPage.setDriver(driver);
            String content = thCtrlOpsPage.getEEapi(
                    "https://my-apps-qa.ecofactor.com/ws/v1.0/thermostat/" + thermostatId
                            + "/state", null, 200);
            LogUtil.setLogString("Json result :" + content, true);
            JSONObject jsonObj = new JSONObject(content);
            String setPointReason = jsonObj.get("setpoint_reason").toString();
            LogUtil.setLogString("SetPoint reason :" + setPointReason, true);
            driver.navigate().back();
            Assert.assertTrue(setPointReason.equalsIgnoreCase("ee"), "Setpoint Reason is not EE");
            thPageOps.setDriver(null);
            thCtrlOpsPage.setDriver(null);
            thCtrlUIPage.setDriver(null);
            thPageUI.setDriver(null);
            LogUtil.setLogString(LogSection.END, "New Browser verification ends", true);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            loginPage.setDriver(null);
            thPageOps.setDriver(null);
            thCtrlOpsPage.setDriver(null);
            thCtrlUIPage.setDriver(null);
            thPageUI.setDriver(null);
            if (driver != null) {
                LogUtil.setLogString("Quit driver for new browser", true);
                driver.quit();
            }
        }
        WaitUtil.mediumWait();
        thCtrlOpsPage.closeThermostatControl();
        Assert.assertTrue(thPageUI.isUnderSavingsEnergy(), "Savings Energy is not displayed");
    }

    /**
     * Subscribe spo.
     * @param thermostatId the thermostat id
     * @param mode the mode
     * @param spoModeId the spo mode id
     * @param spoInverseModeId the spo inverse mode id
     */
    private void subscribeSPO(Integer thermostatId, String mode, Integer spoModeId,
            Integer spoInverseModeId) {

        dataService.init("spoDefaultConfig", mode, thermostatId);
        LogUtil.setLogString(
                "Update Algo control table to status CANCEL, if any existing SPO COOL is running for thermostat id : "
                        + thermostatId, true);
        dataService.updateAlgoControlToInactive(thermostatId, spoModeId);
        LogUtil.setLogString(
                "Update Algo control table to status CANCEL, if any existing SPO HEAT is running for thermostat id : "
                        + thermostatId, true);
        dataService.updateAlgoControlToInactive(thermostatId, spoInverseModeId);
        mediumWait();
        algorithmService.createAlgoControl(thermostatId, spoModeId);
        dataService.init("spoDefaultConfig", mode, thermostatId);
    }

    /**
     * Creates the driver.
     * @return the web driver
     */
    private WebDriver createWebDriver() {

        LogUtil.setLogString("Create New webdriver", true);
        LogUtil.setLogString("Browser : " + System.getProperty("browser"), true);
        final WebDriver driver = SeleniumDriverUtil.createBrowserDriver(System
                .getProperty("browser"));
        driver.manage().window().maximize();
        final String url = mobileConfig.get(MobileConfig.ECOFACTOR_URL);
        LogUtil.setLogString("Load Url: " + url, true);
        driver.navigate().to(url);
        WaitUtil.smallWait();
        return driver;
    }

}
