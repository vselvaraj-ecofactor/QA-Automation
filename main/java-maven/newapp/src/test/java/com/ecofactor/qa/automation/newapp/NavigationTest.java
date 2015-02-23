/*
 * NavigationTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.google.inject.Inject;

/**
 * The Class NavigationTest.
 * @author $Author: vprasannaa $
 * @version $Rev: 32913 $ $Date: 2014-12-02 17:42:42 +0530 (Tue, 02 Dec 2014) $
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class NavigationTest extends AbstractTest {

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /**
     * APP-72 Tstat name in db.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void tstatNameInDB(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        final String thermostatName = thPageUI.getCurrentThermostatName();
        Assert.assertFalse(thermostatName.isEmpty(), "ThermostatName is Empty");
        thDbValidation.verifyTstatName(userName, thermostatName);
    }

    /**
     * APP-77 Rename thermostat and verify order.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void renameThermostatAndVerifyOrder(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageOps.clickMenu();
        thPageOps.clickSettingsIcon();
        renameThermostatName();
        thPageOps.clickThermostatIcon();
        thPageUI.getCurrentThermostatName();
    }

    /**
     * Thermostat offline.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void thermostatOffline(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        boolean isOffline = thPageUI.isThermostatOffline();
        LogUtil.setLogString(isOffline ? "Thermostat is Offline" : "Thermostat is not offline",
                true);
        Assert.assertTrue(isOffline, "Thermostat Offline is not displayed");
    }

    /**
     * APP-70 Ascii tstat user.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "asciiTstatUser", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void asciiTstatUser(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        String thermostatName = thPageUI.getCurrentThermostatName();
        LogUtil.setLogString("Verify ASCII Character $ and ! in  thermostat name : "
                + thermostatName, true);
        Assert.assertTrue(thermostatName.contains("$"), "Thermostat name not contains $ ");
        Assert.assertTrue(thermostatName.contains("!"), "Thermostat name not contains ! ");
    }

    /**
     * APP-71 Blank tstat name.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "blankTstatName", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void blankTstatName(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        String thermostatName = thPageUI.getCurrentThermostatName();
        LogUtil.setLogString("Verify thermostatName is empty ", true);
        Assert.assertTrue(thermostatName.isEmpty(), "Thermostat name is not empty ");
    }

    /**
     * APP-73 Multiple location.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleLocation", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void multipleLocation(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        thPageOps.clickLocationSwitcher();
        Integer noOfThermostat = thPageUI.getNoOfThermostats();
        LogUtil.setLogString("No Of Thermostats : " + noOfThermostat.toString(), true);
        LogUtil.setLogString("No Of Locations : " + thPageUI.getNoOfLocations().toString(), true);
        locPageOps.clickClose();
        Assert.assertTrue(noOfThermostat > 1, "There is no multiple thermostats");

        List<String> locationList = new ArrayList<>();
        List<String> thermostatList = new ArrayList<>();
        for (int i = 0; i < noOfThermostat; i++) {

            String locationName = thPageUI.getCurrentLocationName().trim();
            String thermostatName = thPageUI.getCurrentThermostatName();
            if (!locationList.contains(locationName)) {
                locationList.add(locationName);
            }
            if (!thermostatList.contains(thermostatName)) {
                thermostatList.add(thermostatName);
            }
            thPageOps.swipe("left");
        }
    }

    /**
     * APP-74 Multiple location alphabetical.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleLocation", dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void multiLocationAlphabetical(final String userName, final String password)
            throws Exception {

        loadPage(userName, password, true);
        Integer noOfThermostat = thPageUI.getNoOfThermostats();
        List<String> locationList = new ArrayList<>();
        for (int i = 0; i < noOfThermostat; i++) {
            String locationName = thPageUI.getCurrentLocationName().trim();
            if (!locationList.contains(locationName)) {
                locationList.add(locationName);
            }
            thPageOps.swipe("left");
        }
        thDbValidation.verifyLocationAlphabeticalOrder(userName, locationList);
    }

    /**
     * APP-75 Thermostat alphabetical.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleTstatAlphabetical", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void thermostatAlphabetical(final String userName, final String password)
            throws Exception {

        loadPage(userName, password, true);
        thPageOps.clickLocationSwitcher();
        Integer noOfThermostat = thPageUI.getNoOfThermostats();
        LogUtil.setLogString("No of Thermostat : " + noOfThermostat, true);
        locPageOps.clickClose();
        HashMap<String, String> thMap = new HashMap<String, String>();
        List<String> locationList = new ArrayList<String>();
        for (int i = 0; i < noOfThermostat; i++) {
            String locationName = thPageUI.getCurrentLocationName().trim();
            String thermostatName = thPageUI.getCurrentThermostatName().trim();
            if (thMap.get(locationName) != null && !thMap.get(locationName).isEmpty()) {
                String value = thMap.get(locationName);
                thMap.put(locationName, value + "##" + thermostatName);
            } else {
                thMap.put(locationName, thermostatName);
            }
            if (!locationList.contains(locationName)) {
                locationList.add(locationName);
            }
            thPageOps.swipe("left");
        }
        verifyTstatAlphabetical(thMap);
    }

    /**
     * Verify tstat alphabetical.
     * @param thMap the th map
     */
    private void verifyTstatAlphabetical(HashMap<String, String> thMap) {

        for (String key : thMap.keySet()) {
            String value = thMap.get(key);
            String[] thArray = value.split("##");
            List<String> tstatUIList = Arrays.asList(thArray);
            List<String> tstatUISortList = new ArrayList<>();
            tstatUISortList.addAll(tstatUIList);
            Collections.sort(tstatUISortList);
            for (int i = 0; i < tstatUISortList.size(); i++) {
                String thName = tstatUISortList.get(i);
                String thUIName = tstatUIList.get(i);
                Assert.assertTrue(thName.equalsIgnoreCase(thUIName),
                        "Sorting Fails for thermostat name");
            }
        }
    }

}