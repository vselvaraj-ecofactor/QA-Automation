/*
 * SavingsTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.text.ParseException;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.Authenticator;
import com.ecofactor.qa.automation.consumerapi.ConsumerApiURL;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class SavingsTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class SavingsTest extends AbstractTest {

    /** The consumer api url. */
    @Inject
    private ConsumerApiURL consumerApiURL;

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The savings page. */
    @Inject
    private SavingsPage savingsPage;

    /**
     * APPS-266 Ecp core user without savings icon.
     * @param userName the user name
     * @param password the password
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "ecpCoreUserWOSavings", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void ecpCoreUserWithoutSavingsIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        Assert.assertFalse(thPageUI.isSavingsDisplayed(), "Savings icon is displayed");
        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not loaded");
        Assert.assertTrue(menuPage.isSavingsLabelDisplayed(), "Savings label is displayed");
    }

    /**
     * APPS-267 Ecp core user with savings icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "ecpCoreUserWithSavings", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void ecpCoreUserWithSavingsIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not loaded");
        Assert.assertTrue(menuPage.isSavingsLabelDisplayed(), "Savings label is not displayed");
        menuPage.clickSavings();
        Assert.assertTrue(savingsPage.isPageLoaded(), "Savings Page is not loaded");
    }

    /**
     * APPS-275 Click menu option.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void clickMenuOption(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not loaded");
        // menuPage.clickMenuIconOnMenuPage();
        savingsPage.clickMenu();
        Assert.assertTrue(savingsPage.isPageLoaded(), "Savings Page is not loaded");
    }

    /**
     * APPS-272 Navigate to savings data using arrows.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "ecpCoreUserWithSavings", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void navigateToSavingsDataUsingArrows(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.getCurrentMonthAndYear();
        savingsPage.clickPrevious();
        savingsPage.getCurrentMonthAndYear();
        savingsPage.clickNext();
        savingsPage.getCurrentMonthAndYear();
    }

    /**
     * APPS-277 Verify footer is not displayed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void verifyFooterIsNotDisplayed(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.isFooterDisplayed(), "Savings Page is not loaded");
    }

    /**
     * APPS-278 Verify six months savings data.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "savingsSixMonthData", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void verifySixMonthsSavingsData(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.goToMonth("January", "2014", true);
    }

    /**
     * APPS-280 Verify zero runtime savings.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "zeroRuntimeSavings", dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void verifyZeroRuntimeSavings(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        checkZeroSavings();
    }

    /**
     * APPS-283 Verify arrows displayed.
     * @param userName the user name
     * @param oneMonthDataUser the one month data user
     * @param password the password
     * @throws ParseException the parse exception
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "mutilpleAndOneMonthData", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void verifyArrowsDisplayed(final String userName, final String oneMonthDataUser,
            final String password) throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.isArrowNotDisplayed("Right"), "Right Arrow is displayed");
        savingsPage.goToMonth("November", "2013", false);
        Assert.assertTrue(savingsPage.isArrowNotDisplayed("Left"), "Left Arrow is displayed");
        loadPage(oneMonthDataUser, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.isArrowNotDisplayed("Right"), "Right Arrow is displayed");
        Assert.assertTrue(savingsPage.isArrowNotDisplayed("Left"), "Left Arrow is displayed");
    }

    /**
     * APPS-274 Verify savings since install.
     * @param userName the user name
     * @param password the password
     * @param locationId the location id
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "locationSavings", dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void verifySavingsSinceInstall(final String userName, final String password,
            String locationId) {

        // Savings UI
        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        final String totalSavings = savingsPage.getTotalSavings();

        // Savings API
        Cookie securityCookie = Authenticator.getSecurityCookieForUser(userName, password);
        final Response response = consumerApiURL.getAccumulatedDollarSavings(locationId,
                securityCookie);
        String apiSavings = getDollarSavings(response);
        setLogString("Savings Since Install value from API :" + apiSavings, true);
        Assert.assertEquals(apiSavings, totalSavings,
                "Savings Since Install value from API is differ from Savings UI");
    }

    /**
     * APPS-289 Verify location name.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void verifyLocationName(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        final String locationName = savingsPage.getLocationName();
        Assert.assertNotNull(locationName, "Location Name is not displayed");
    }

    /**
     * APPS-284 Verify learn more.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "learnMoreUser", dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void verifyLearnMore(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.isLearnMoreDisplayed(), "Learn More link is not displayed");
        // checkZeroSavings();
    }

    /**
     * APPS-291 Verify savings color.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void verifySavingsColor(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.getSavingsHeaderColor(), "Savings header color is changed");
        Assert.assertTrue(savingsPage.getTotalSavingsColor(), "Total Savings color is changed");
        // Assert.assertTrue(savingsPage.getSavingsArrowColor(), "Savings arrow color is changed");
    }

    /**
     * APPS-285 Verify location not installed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "locationNotInstalled", dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void verifyLocationNotInstalled(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.isLocationNotInstalled(),
                "Location is installed. Error message differs");
    }

    /**
     * APPS-292 Verify hours percentage.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "ecpCoreUserWithSavings", dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void verifyHoursPercentage(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.goToMonth("January", "2014", false);
        savingsPage.getSavingsPercentage();
    }

    /**
     * APPS-269 Verify savings for multi location.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleLocation", dataProviderClass = CommonsDataProvider.class, priority = 15)
    public void verifySavingsForMultiLocation(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.getLocationName();
        savingsPage.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu page is not loaded");
        menuPage.clickThermostatMenuItem();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page is not loaded");
        thPageOps.swipeLoc("left");
        goToSavingsPage();
        savingsPage.getLocationName();
    }

    /**
     * APPS-279 Verify gaps in savings.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "ecpCoreUserWithSavings", dataProviderClass = CommonsDataProvider.class, priority = 16)
    public void verifyGapsInSavings(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.goToMonth("January", "2014", true);
    }

    /**
     * APPS-273 Re launch mobile app.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 17)
    public void reLaunchMobileApp(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        WaitUtil.largeWait();
        WaitUtil.mediumWait();
        testOps.closeDeviceDriver();
        testOps.loadDeviceDriver();
        WaitUtil.mediumWait();
        testOps.switchToWebView();
        WaitUtil.smallWait();
        uiAction.rejectAlert();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat Page is not loaded");
    }

    /**
     * APPS-276 Verify monthly savings with api.
     * @param userName the user name
     * @param password the password
     * @param locationId the location id
     * @throws ParseException the parse exception
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "locationSavings", dataProviderClass = CommonsDataProvider.class, priority = 18)
    public void verifyMonthlySavingsWithAPI(final String userName, final String password,
            String locationId) throws ParseException {

        // Savings UI
        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        savingsPage.goToMonth("November", "2013", true);
        final String savingsHours = savingsPage.getSavingsHours();

        // Savings API
        Cookie securityCookie = Authenticator.getSecurityCookieForUser(userName, password);
        final Response response = consumerApiURL.getLocationRuntimeSavings(locationId,
                securityCookie);
        String apiSavingsHours = getMonthHoursValue(response);
        setLogString("Monthly savings hours value from API :" + apiSavingsHours, true);
        Assert.assertEquals(savingsHours, apiSavingsHours,
                "Monthly section values from UI diffes from runtime_savings API value");
    }

    /**
     * Savings in tstat page.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 19)
    public void savingsInTstatPage(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
    }

    /**
     * Savings compare value.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "learnMoreUser", dataProviderClass = CommonsDataProvider.class, priority = 20)
    public void savingsCompareValue(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        String tstatSavingsAmount = thPageUI.getSavingsAmount();
        goToSavingsPage();
        String savingsAmount = savingsPage.getSavingsValue();
        Assert.assertEquals(Double.valueOf(tstatSavingsAmount), Double.valueOf(savingsAmount));
    }

    /**
     * Savings dollar and month.
     * @param userName the user name
     * @param password the password
     * @throws ParseException the parse exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 21)
    public void savingsDollarAndMonth(final String userName, final String password)
            throws ParseException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        goToSavingsPage();
        Assert.assertTrue(savingsPage.isSavingsDollarVertical(), "SavingsDollar is not Vertical");
        Assert.assertTrue(savingsPage.isSavingsMonthHorizontal(), "SavingsMonth is not Horizontal");

    }

    /**
     * Check idle conditions for thermostat.
     */
    @TestPrerequisite
    public void checkIdleConditionsForThermostat() {

        if (thPageUI.isPageLoaded()) {
            Assert.assertFalse(thPageUI.isThermostatOffline(),
                    "Unable to do thermostat related UI changes since thermostat is offline.");
        }
        if (awayPage.isPageLoaded()) {
            setLogString("Wait 30 seconds and click Imback", true);
            WaitUtil.largeWait();
            awayPage.clickAmBack();
        }
        if (thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }
        if (menuPage.isPageLoaded()) {
            menuPage.clickThermostatMenuItem();
            Assert.assertTrue(thPageUI.isPageLoaded(),
                    "Unable to load Thermostat page from Menu page.");
        }
    }

    /**
     * Gets the dollar savings.
     * @param response the response
     * @return the dollar savings
     */
    private String getDollarSavings(Response response) {

        String rep = response.readEntity(String.class);
        setLogString("Savings API response : " + rep, true);
        JSONObject jsonObj = JsonUtil.parseObject(rep);
        return String.valueOf(Math.round((Double) jsonObj.get("dollar_savings")));
    }

    /**
     * Gets the month hours value.
     * @param response the response
     * @return the month hours value
     */
    private String getMonthHoursValue(Response response) {

        String rep = response.readEntity(String.class);
        setLogString("Runtime Savings API response : " + rep, true);
        JSONObject jsonObj = JsonUtil.parseObject(rep);
        JSONObject months = JsonUtil.parseObject(jsonObj.get("months").toString());
        JSONObject date = JsonUtil.parseObject(months.get("2013-11").toString());
        JSONObject heat = JsonUtil.parseObject(date.get("heat").toString());
        JSONObject cool = JsonUtil.parseObject(date.get("cool").toString());
        return String.valueOf(Math.round(Double.valueOf(heat.get("runtime_hours_saved").toString())
                + Double.valueOf(cool.get("runtime_hours_saved").toString())));
    }

    /**
     * Go to savings page.
     */
    private void goToSavingsPage() {

        if (thPageUI.isPageLoaded()) {
            Assert.assertTrue(thPageUI.isSavingsDisplayed(), "Savings icon is not displayed");
            thPageOps.clickSavings();
        }
        Assert.assertTrue(savingsPage.isPageLoaded(), "Savings Page is not loaded");
    }

    /**
     * Check zero savings.
     */
    private void checkZeroSavings() {

        final Double savings = Double.valueOf(savingsPage.getSavingsValue());
        // final Double totalSavings = Double.valueOf(savingsPage.getTotalSavings());
        Assert.assertEquals(savings, Double.valueOf(0.0), "Runtime Savings is not Zero!");
        // Assert.assertEquals(totalSavings, Double.valueOf(0.0), "Total Savings is not Zero!");
    }
}
