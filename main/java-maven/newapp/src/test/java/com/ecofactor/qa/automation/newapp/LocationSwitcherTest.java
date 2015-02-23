/*
 * LocationSwitcherTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.enums.TemperatureType;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage;
import com.ecofactor.qa.automation.newapp.page.LocationSwitcherOpsPage;
import com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class LocationSwitcherTest.
 * @author $Author: vprasannaa $
 * @version $Rev: 32949 $ $Date: 2014-12-04 15:16:10 +0530 (Thu, 04 Dec 2014) $
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class LocationSwitcherTest extends AbstractTest {

    /** The Constant DEFAULT_USER. */
    private static final String DEFAULT_USER = "defaultUser";

    /** The Constant MULTI_TSTAT_USER. */
    private static final String MULTI_TSTAT_USER = "locationSwitcherMultiTstat";

    /** The Constant MULTIPLE_1_ON_1_OFF_USER. */
    public static final String MULTIPLE_1_ON_1_OFF_USER = "multipleOneOnOneOff";

    /** The Constant MULTIPLE_LOCATION. */
    private static final String MULTIPLE_LOCATION = "locationSwitcherMultiLoc";

    /** The loc switch page ui. */
    @Inject
    private LocationSwitcherUIPage locSwitchPageUI;

    /** The loc switch page ops. */
    @Inject
    private LocationSwitcherOpsPage locSwitchPageOps;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The away settings ops page. */
    @Inject
    private AwaySettingsOpsPage awaySettingsOpsPage;

    /**
     * APPS-60 Location icon not displayed for single tstat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void locationIconNotDisplayedForSingleTstat(final String userName, final String password) {

        loadPage(userName, password, true);
        Assert.assertFalse(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is displayed for Single thermostat User");
    }

    /**
     * APPS-243 Location icon displayed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void locationIconDisplayed(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
    }

    /**
     * APPS-60 Location icon displayed for multi loc.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_LOCATION, dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void locationIconDisplayedForMultiLoc(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
    }

    /**
     * APPS-61 Click location row.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void clickLocationRow(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        List<String> locationNameList = locSwitchPageUI.getLocationNames();
        locSwitchPageOps.clickLocation(locationNameList.get(0));
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        locSwitchPageOps.clickClose();
    }

    /**
     * APPS-61 Click thermostat row.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void clickThermostatRow(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        List<String> locationNameList = locSwitchPageUI.getLocationNames();
        String locationName = locationNameList.get(0);
        Integer locationId = thDbValidation.getLocationIdForUser(userName, locationName);
        List<String> tstatNameList = locSwitchPageUI.getTstatNamesinLocation(locationId);
        List<Integer> tstatIdList = locSwitchPageUI.getTstatIdForLocation(locationId);
        locSwitchPageOps.selectTstatById(tstatIdList.get(0));
        WaitUtil.mediumWait();
        LogUtil.setLogString("Thermostat name in Location Switcher" + tstatNameList.get(1).trim(),
                true);
        Assert.assertTrue(
                thPageUI.getCurrentThermostatName().trim()
                        .equalsIgnoreCase(tstatNameList.get(0).trim()), "Thermostat Name Differs");
    }

    /**
     * APPS-245 Switch to offline user.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_1_ON_1_OFF_USER, dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void switchToOfflineUser(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed());
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        Integer tsId = thDbValidation.getTstatIdForUser(userName,
                thPageUI.getCurrentThermostatName());
        locSwitchPageOps.selectTstatById(tsId);
        Assert.assertTrue(thPageUI.isThermostatOffline() || thPageUI.isThermostatNotInstalled(),
                "Thermostat is not Offline or Disconnected");
    }

    /**
     * APPS-246 Location alphabetical.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_LOCATION, dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void locationAlphabetical(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed());
        thPageOps.clickLocationSwitcher();
        List<String> locationNameList = locSwitchPageUI.getLocationNames();
        int locPosition = 1;
        for (String localtionName : locationNameList) {
            LogUtil.setLogString(locPosition + ". Location Name : " + localtionName, true);
            locPosition++;
        }
        LogUtil.setLogString("verify the alphabetical order", true);
        List<String> locationNameSortList = new ArrayList<String>(locationNameList);
        Collections.sort(locationNameSortList);
        int sortPosition = 0;
        for (String locationName : locationNameSortList) {
            Assert.assertTrue(locationName.trim().equalsIgnoreCase(
                    locationNameList.get(sortPosition).trim()));
            sortPosition++;
        }
    }

    /**
     * APPS-65 Tstat alphabetical in each location.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_LOCATION, dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void tstatAlphabeticalInEachLocation(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        List<String> locationNameList = locSwitchPageUI.getLocationNames();
        LogUtil.setLogString(LogSection.START, "Verify Order of Thermostats", true);
        for (String locationName : locationNameList) {
            LogUtil.setLogString("Verify Thermostats are in order for Location : " + locationName,
                    true);
            Integer locationId = thDbValidation.getLocationIdForUser(userName, locationName);
            List<String> tstatNameList = locSwitchPageUI.getTstatNamesinLocation(locationId);
            List<String> tstatNameSortList = new ArrayList<String>(tstatNameList);
            Collections.sort(tstatNameSortList);

            int sortPosition = 0;
            for (String tstatName : tstatNameSortList) {
                LogUtil.setLogString("Thermostat Name :" + tstatName, true);
                Assert.assertTrue(tstatName.trim().equalsIgnoreCase(
                        tstatNameList.get(sortPosition).trim()));
                sortPosition++;
            }
        }
        LogUtil.setLogString(LogSection.END, "Completed verifying the order of Thermostats", true);
    }

    /**
     * APPS-248 Away mode l sicon displayed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_LOCATION, dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void awayModeLSiconDisplayed(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        locSwitchPageOps.clickClose();
        thPageOps.clickSetAway();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
    }

    /**
     * APPS-77 Offline ls verification.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_1_ON_1_OFF_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void offlineLSVerification(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed());
        locSwitchPageOps.clickClose();

    }

    /**
     * APPS-78 Ls icon clickable tstat control.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void lsIconClickableTstatControl(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.openTstatController();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        Assert.assertFalse(thCtrlOpsPage.isPopUpOpened(), "Thermostat Page not closed");
    }

    /**
     * APP-250 Ls open and click set away.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void lsOpenAndClickSetAway(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        thPageOps.clickSetAway();
    }

    /**
     * Ls open and click savings.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void lsOpenAndClickSavings(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        thPageOps.clickSavings();
        savingsPage.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu page is not loaded");
        menuPage.clickThermostatMenuItem();
        Assert.assertTrue(thPageUI.isOffModeDialogDisplayed(),
                "Thermostat OFF mode dialog is not displayed");
    }

    /**
     * APP-253 Swipe disabled.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void swipeDisabled(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        String tstatName = thPageUI.getCurrentThermostatName();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        thPageOps.swipe("left");
        LogUtil.setLogString("Verify the thermostat is not switched to another", true);
        Assert.assertTrue(
                tstatName.trim().equalsIgnoreCase(thPageUI.getCurrentThermostatName().trim()),
                "Switched to another thermostat");
        LogUtil.setLogString("The page remains in same state", true);
        locSwitchPageOps.clickClose();

    }

    /**
     * APP-254 Hide and show off mode dialog when ls popup open.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 18)
    public void hideAndShowOffModeDialoglWhenLSPopupOpen(final String userName,
            final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        Assert.assertFalse(thPageUI.isOffModeDialogDisplayed(),
                "Thermostat OFF mode dialog is displayed");
        locSwitchPageOps.clickClose();
        Assert.assertTrue(thPageUI.isOffModeDialogDisplayed(),
                "Thermostat OFF mode dialog is not displayed");
    }

    /**
     * APPS-99 (1) Hide and show disconnected dialogl when ls popup open.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_1_ON_1_OFF_USER, dataProviderClass = CommonsDataProvider.class, priority = 19)
    public void hideAndShowDisconnectedDialoglWhenLSPopupOpen(final String userName,
            final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        locSwitchPageOps.clickClose();
        Assert.assertTrue(thPageUI.isThermostatNotConnected(), "Thermostat is Connected");
        thPageOps.clickLocationSwitcher();
        Assert.assertFalse(thPageUI.isNotInstalledDialogDisplayed(),
                "Thermostat OFF mode dialog is displayed");
        locSwitchPageOps.clickClose();
        Assert.assertTrue(thPageUI.isOffModeDialogDisplayed(),
                "Thermostat OFF mode dialog is not displayed");
    }

    /**
     * APP-256 Fan icon displayed for cool mode.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 20)
    public void fanIconDisplayedForCoolMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * Heat icon displayed for heat mode.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 21)
    public void heatIconDisplayedForHeatMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * APP-255 Hide and show disconnected dialogl when ls popup open.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 22)
    public void lsIconNotDisplayedInMenuAndAwaysettings(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        locSwitchPageOps.clickClose();
        Assert.assertTrue(thPageUI.isOffModeDialogDisplayed(),
                "Thermostat OFF mode dialog is not displayed");
        thPageOps.clickMenu();
        menuPage.clickAwayTemperature();
        awaySettingsOpsPage.clickMenuIcon();
        menuPage.clickThermostatMenuItem();
    }

    /**
     * Switch mode and verify ls icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 23)
    public void switchModeAndVerifyLsIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        locSwitchPageOps.clickClose();
        Assert.assertTrue(thPageUI.isNotInstalledDialogDisplayed(),
                "Thermostat OFF mode dialog is not displayed");
    }

    /**
     * APPS-244 Collapse ls by ls icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 24)
    public void collapseLsByLsIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed());
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        locSwitchPageOps.clickClose();
        Assert.assertFalse(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Closed");
    }

    /**
     * APPS-258 Temperature shows hiphen icon for offline tstats.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_1_ON_1_OFF_USER, dataProviderClass = CommonsDataProvider.class, priority = 25)
    public void temperatureShowsHiphenIconForOfflineTstats(final String userName,
            final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed(),
                "Location Icon is not displayed for multiple thermostat User");
        thPageOps.clickLocationSwitcher();
        List<String> locationNameList = locSwitchPageUI.getLocationNames();
        String locationName = locationNameList.get(0);
        Integer locationId = thDbValidation.getLocationIdForUser(userName, locationName);
        List<Integer> tstatIdList = locSwitchPageUI.getTstatIdForLocation(locationId);
        String temperature = locSwitchPageUI.getTempByTstatId(tstatIdList.get(0),
                TemperatureType.CURRENTTEMPERATURE);
        Assert.assertTrue(temperature.equalsIgnoreCase("--"), "Temperature not shows Hiphen text");
        String modeIcon = locSwitchPageUI.getModeByTstatId(tstatIdList.get(0));
        Assert.assertTrue(modeIcon != null && modeIcon.equalsIgnoreCase("OFF"),
                "Fan icon is not displayed");
        locSwitchPageOps.clickClose();
    }

    /**
     * APPS-257 Target temperature on tstat row.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = MULTIPLE_1_ON_1_OFF_USER, dataProviderClass = CommonsDataProvider.class, priority = 26)
    public void targetTemperatureOnTstatRow(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.clickLocationSwitcher();
        List<String> locationNameList = locSwitchPageUI.getLocationNames();
        String locationName = locationNameList.get(0);
        Integer locationId = thDbValidation.getLocationIdForUser(userName, locationName);
        List<Integer> tstatIdList = locSwitchPageUI.getTstatIdForLocation(locationId);
        locSwitchPageUI.getRangeofTemperature(tstatIdList.get(0));
        locSwitchPageOps.clickClose();
    }

    /**
     * APPS-61 Collapse ls by menu icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 27)
    public void collapseLsByMenuIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed());
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        locSwitchPageOps.clickClose();
        Assert.assertFalse(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Closed");
    }

    /**
     * APPS-61 Collapse ls by background.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = MULTI_TSTAT_USER, dataProviderClass = CommonsDataProvider.class, priority = 28)
    public void collapseLsByBackground(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        Assert.assertTrue(thPageUI.isLocationSwitcherDisplayed());
        thPageOps.clickLocationSwitcher();
        Assert.assertTrue(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Loaded");
        locSwitchPageOps.clickBackground();
        locSwitchPageOps.clickClose();
        Assert.assertFalse(locSwitchPageUI.isPageLoaded(), "Location Switcher Page Not Closed");
    }

    /**
     * Check common prequisites.
     */
    @TestPrerequisite
    public void checkCommonPrequisites() {

        Assert.assertFalse(thPageUI.isThermostatOffline(),
                "Unable to do thermostat related UI changes since thermostat is offline.");
        if (thCtrlOpsPage.isPopUpOpened()) {
            thCtrlOpsPage.closeThermostatControl();
        }

        if (awayPage.isPageLoaded()) {
            LogUtil.setLogString("Wait 30 seconds and click Imback", true);
            WaitUtil.largeWait();
            awayPage.clickAmBack();
        }

        if (thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }

        if (locSwitchPageUI.isPageLoaded()) {
            locSwitchPageOps.clickClose();
        }
    }
}
