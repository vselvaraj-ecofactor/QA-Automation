/*
 * HelpOverlayTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.HelpOverlayPage;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.google.inject.Inject;

/**
 * The Class HelpOverlayTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class HelpOverlayTest extends AbstractTest {

    /** The help overlay page. */
    @Inject
    private HelpOverlayPage helpOverlayPage;

    /** The thermostat page ui. */
    @Inject
    private ThermostatPageUI thermostatPageUI;

    /** The savings page. */
    @Inject
    private SavingsPage savingsPage;

    /** The Constant ERR_HELPPAGE_LOAD. */
    private static final String ERR_HELPPAGE_LOAD = "Help overlay page is not loaded";

    /** The Constant DEFAULT_USER. */
    private static final String DEFAULT_USER = "defaultUser";

    /**
     * APP-169 Help overlay in first time login.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException
     */

    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void helpOverlayInFirstTimeLogin(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true, true);
        Assert.assertTrue(helpOverlayPage.isPageLoaded(), ERR_HELPPAGE_LOAD);
    }

    /**
     * APP-170 Help overlay drag target.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void helpOverlayDragTarget(final String userName, final String password) {

        loadPage(userName, password, true, true);
        Assert.assertTrue(helpOverlayPage.isPageLoaded(), ERR_HELPPAGE_LOAD);
        setLogString("Verify drag target slide is loaded.", true);
        Assert.assertTrue(helpOverlayPage.helpOverlayPageOne());
    }

    /**
     * APP-181 Help overlay ecopilot.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void helpOverlayEcopilot(final String userName, final String password) {

        loadPage(userName, password, true, true);
        Assert.assertTrue(helpOverlayPage.isPageLoaded(), ERR_HELPPAGE_LOAD);
        setLogString("Verify ecopilot slide is loaded.", true);
        Assert.assertTrue(helpOverlayPage.helpOverlayPageTwo());
    }

    /**
     * APP-171 Help overlay switch thermostat.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void helpOverlayThermostatSwitch(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true, true);
        setLogString("Verify thermostat switch slide is loaded.", true);
        Assert.assertTrue(helpOverlayPage.helpOverlayPageThree());
    }

    /**
     * APP-184 Check valid header for help overlay.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void checkHeaderForHelpOverlay(final String userName, final String password) {

        loadPage(userName, password, true, true);
        Assert.assertTrue(thermostatPageUI.isPageLoaded(), "Thermostat details not loaded");
        Assert.assertNotNull(thermostatPageUI.getCurrentLocationName(), "Location name invalid");
        Assert.assertNotNull(thermostatPageUI.getCurrentThermostatName(), "Thermostat name invalid");
    }

    /**
     * APP-185 Check valid footer for help overlay.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void checkFooterForHelpOverlay(final String userName, final String password) {

        loadPage(userName, password, true, true);
        Assert.assertTrue(thermostatPageUI.isPageLoaded(), "Thermostat details not loaded");
        Assert.assertTrue(thermostatPageUI.isSavingsDisplayed(), "Savings details not loaded");
        Assert.assertNotNull(thermostatPageUI.getSavingsAmount(), "Invalid savings amount value");
    }

    /**
     * APP-192 Close and verify help on next time login.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void closeAndVerifyHelpOnNextTimeLogin(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true, true);
        helpOverlayPage.goToThermostatPage();
        thPageUI.isPageLoaded();
        doLogout();
        loadPage(userName, password, true, true);
        Assert.assertFalse(helpOverlayPage.isPageLoaded(),
                "Help overlay page is loaded while login to app the second time.");
        if (helpOverlayPage.isPageLoaded()) {
            helpOverlayPage.goToThermostatPage();
        }
        doLogout();
    }

    /**
     * APP-195 Clear cache and verify help page displayed.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void clearCacheAndVerifyHelpPageDisplayed(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true, true);
        helpOverlayPage.goToThermostatPage();
        thPageUI.isPageLoaded();

        closeAndLoadApp();
        loadPage(userName, password, true, true);
        Assert.assertTrue(helpOverlayPage.isPageLoaded(),
                "Help overlay page is loaded while login to app the second time.");
    }

    /**
     * APP-193 OfflineUser help page.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void offlineUserHelpPage(final String userName, final String password)
            throws DeviceException {

        closeAndLoadApp();
        Assert.assertTrue(isHelpPageLoadedForUser(userName, password), ERR_HELPPAGE_LOAD);
    }

    /**
     * APP-194 Multiple locations help page.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleLocation", dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void multipleLocationsHelpPage(final String userName, final String password)
            throws DeviceException {

        Assert.assertTrue(isHelpPageLoadedForUser(userName, password), ERR_HELPPAGE_LOAD);
    }

    /**
     * Is help page for user loaded.
     * @param userName the user name
     * @param password the password
     * @return true, if successful
     * @throws DeviceException the device exception
     */
    private boolean isHelpPageLoadedForUser(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true, true);
        return helpOverlayPage.isPageLoaded();
    }

    /**
     * Close and load app.
     * @throws DeviceException the device exception
     */
    private void closeAndLoadApp() throws DeviceException {

        setLogString("Close help page if displayed.", true);
        if (helpOverlayPage.isPageLoaded()) {
            helpOverlayPage.goToThermostatPage();
        }
        doLogout();
        testOps.cleanup();
        mediumWait();
        testOps.switchToWebView();
        uiAction.rejectAlert();
    }
}
