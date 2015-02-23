/*
 * MenuTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import org.joda.time.LocalTime;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage;
import com.ecofactor.qa.automation.newapp.page.LoginPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.page.validate.admin.TstatAdminValidation;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;

import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.util.SeleniumDriverUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class MenuTest.
 * @author $Author: vprasannaa $
 * @version $Rev: 33495 $ $Date: 2015-01-14 12:40:27 +0530 (Wed, 14 Jan 2015) $
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class MenuTest extends AbstractTest {

    /** The Constant COOL. */
    private static final String COOL = "cool";

    /** The Constant HEAT. */
    private static final String HEAT = "heat";

    /** The Constant OFF. */
    private static final String OFF = "off";

    // private static final String RIGHT = "right";

    /** The Constant DEFAULT_USER. */
    private static final String DEFAULT_USER = "defaultUser";

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The login page. */
    @Inject
    private LoginPage loginPage;

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The away settings ui page. */
    @Inject
    private AwaySettingsUIPage awaySettingsUIPage;

    /** The away settings ops page. */
    @Inject
    private AwaySettingsOpsPage awaySettingsOpsPage;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The thermostat admin. */
    @Inject
    private TstatAdminValidation thermostatAdmin;

    /** The counter start time. */
    @SuppressWarnings("unused")
    private LocalTime counterStartTime;

    /** The counter end time. */
    @SuppressWarnings("unused")
    private LocalTime counterEndTime;

    /** The duration. */
    @SuppressWarnings("unused")
    private long duration;

    /**
     * APP-159. Verify AwaySetting page is displayed when clicking the away temperature menu item on
     * menu page .
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void showAwaySettingsByAwayTemperatureClick(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        menuPage.clickAwayTemperature();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(),
                "Error Loading Away Settings after clicking the menu item");
        closeMenuPage();
    }

    /**
     * APP-148 Verify thermostat page is displayed when clicking the thermostat menu item on menu
     * page .
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void testThermostatMenuItem(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        menuPage.clickThermostatMenuItem();
        Assert.assertTrue(thPageUI.isPageLoaded(),
                "Error Loading Thermostat Page after clicking the menu item");
    }

    /**
     * APP-149 Verify login page is displayed when clicking the logout menu item on menu page .
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void testLogoutMenuItem(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        doLogout();
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Error Loading Login Page after clicking the logout");
    }

    /**
     * APP-150. Slice tstat screen visible.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void sliceTstatScreenVisible(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.closeThermostatControl();
        openMenuPage();
        LogUtil.setLogString("Verify the current state is HEAT", true);
        Assert.assertTrue(HEAT.equalsIgnoreCase(thPageUI.getCurrentMode()),
                "Heat mode is not reflected");

        menuPage.clickThermostatHighlighted();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.closeThermostatControl();
        openMenuPage();
        LogUtil.setLogString("Verify the current state is COOL", true);
        Assert.assertTrue(COOL.equalsIgnoreCase(thPageUI.getCurrentMode()),
                "Cool mode is not reflected");

        menuPage.clickThermostatHighlighted();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToOff();
        openMenuPage();
        LogUtil.setLogString("Verify the current state is OFF", true);
        Assert.assertTrue(OFF.equalsIgnoreCase(thPageUI.getCurrentMode()),
                "Off mode is not reflected");

        closeMenuPage();
    }

    /**
     * APP-153. Verify menu is closed when clicking the menu icon while keeping the menu open.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void menuCloseByMenuIconClick(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        Assert.assertTrue(thPageUI.isPageLoaded(),
                "Error Loading Thermostat Page after clicking the menu icon");
    }

    /**
     * APP-158. Close and launch app by keeping menu open.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.SANITY2, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void closeAndLaunchAppWithMenuOpen(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        testOps.closeDeviceDriver();
        testOps.loadDeviceDriver();
        testOps.switchToWebView();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page loaded on return to the app");
        Assert.assertFalse(menuPage.isPageLoaded(), "Menu page loaded on return to the app");
    }

    /**
     * APP-152. No actions on tstat slice.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void noActionsOnTstatSlice(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        closeMenuPage();
    }

    /**
     * APP-157. Close menuby click on slice.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.SANITY2, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void closeMenubyClickOnSlice(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page not loaded.");
        menuPage.clickMenuIcon();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page not loaded");
        // closeMenuPage();
    }

    /**
     * APP-155. Swipe left to right.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void swipeLeftToRight(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        Assert.assertTrue(menuPage.isPageLoaded(),
                "Error Loading Menu Page after clicking the menu icon");
    }

    /**
     * APP-156. Swipe right to left.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void swipeRightToLeft(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        Assert.assertTrue(menuPage.isPageLoaded(),
                "Error Loading Menu Page after clicking the menu icon");
        menuPage.clickMenuIcon();
        Assert.assertTrue(thPageUI.isPageLoaded(),
                "Menu Page not closed on clicking thermostat slice.");
        closeMenuPage();
    }

    /**
     * APP-163. Swipe disabled in menu page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void swipeDisabledInMenuPage(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        LogUtil.setLogString(
                "Open menu page, do swipe right and verify swipe action is denied by asserting menu page remains loaded.",
                true, CustomLogLevel.MEDIUM);
        menuPage.clickMenuIcon();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu page is not loaded!");
    }

    /**
     * APP-151. Slice offline tstat screen visible.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void sliceOfflineTstatScreenVisible(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        openMenuPage();
        LogUtil.setLogString("Verify the current state is Offline", true);
        thPageUI.isThermostatOffline();
        closeMenuPage();
    }

    /**
     * APP-162. Menu slice mode reflection from web.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.SANITY2, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "webUser", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void menuSliceModeReflectionFromWeb(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        closeMenuPage();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.closeThermostatControl();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thDbValidation.verifyTargetTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                thPageUI.getTargetTemperature());
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                thPageUI.getTargetTemperature());
    }

    /**
     * Reset away check mode off.
     */
    @TestPrerequisite
    public void resetAwayCheckModeOff() {

        if (awayPage.isPageLoaded()) {
            awayPage.clickAmBack();
        }

        if (!menuPage.isPageLoaded() && thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }
    }

    /**
     * Open menu page.
     */
    private void openMenuPage() {

        if (!menuPage.isPageLoaded()) {
            /* thPageOps.clickMenu(); */
            menuPage.clickMenuIcon();
        }
        WaitUtil.smallWait();
        Assert.assertTrue(menuPage.isPageLoaded(),
                "Error Loading Menu Page after clicking the menu icon");
    }

    /**
     * Close menu page.
     */
    private void closeMenuPage() {

        if (menuPage.isPageLoaded()) {
            menuPage.clickThermostatHighlighted();
            // menuPage.clickThermostatMenuItem();
        } else if (awaySettingsUIPage.isPageLoaded()) {
            awaySettingsOpsPage.clickMenuIcon();
            menuPage.clickThermostatMenuItem();
        }

        Assert.assertTrue(thPageUI.isPageLoaded(),
                "Error Loading Thermostat Page after clicking the menu item");
    }

    /**
     * Creates the driver.
     * @return the web driver
     */
    @SuppressWarnings("unused")
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
