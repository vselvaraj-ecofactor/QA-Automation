/*
 * AndroidTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.platform.action.UIAction;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.MobileRetryAnalyzer;
import com.ecofactor.qa.automation.platform.listener.MobileScreenShotListener;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.util.DeviceUtil;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class AndroidTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class })
@Listeners(MobileScreenShotListener.class)
public class MobileTest {

    /** The mobile config. */
    @Inject
    private MobileConfig mobileConfig;

    /** The log util. */
    @Inject
    private LogUtil logUtil;

    /** The start. */
    private long start;

    /** The mobile ops. */
    @Inject
    private TestOperations mobileOps;

    /** The mobile ui action. */
    @Inject
    private UIAction mobileUIAction;

    /**
     * Sets the up.
     * @throws DeviceException the device exception
     */
    @BeforeSuite(alwaysRun = true)
    public void setUp() throws DeviceException {

        DriverConfig.setLogString("Entering Before Suite!", true);
        smallWait();
        mobileOps.initialize();
        DriverConfig.setLogString("Exiting Before Suite!", true);
    }

    /**
     * Before method.
     * @param method the method
     * @param param the param
     * @throws DeviceException the device exception
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, Object[] param) throws DeviceException {

        DriverConfig.setLogString("Entering Before method!", true);
        mobileOps.cleanup();
        String deviceId = DeviceUtil.getDeviceIdParam();
        String port = DeviceUtil.getMobilePortParam();
        DriverConfig.setLogString("Device Id: " + deviceId + ", Port: " + port, true);
        logUtil.logStart(method, param, deviceId);
        start = System.currentTimeMillis();
        DriverConfig.setLogString("Exiting Before method!", true);
    }

    /**
     * Tear down.
     * @param method the method
     * @throws Exception the exception
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method) throws Exception {

        long duration = (System.currentTimeMillis() - start) / 1000;
        logUtil.logEnd(method, duration);
    }

    /**
     * After suite.
     * @throws Exception the exception
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {

        mobileOps.close();
        smallWait();
    }

    /**
     * Api demo.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity1" }, retryAnalyzer = MobileRetryAnalyzer.class)
    public void apiDemo() throws Exception {

        checkPrerequisite();

        String userName = System.getProperty("userName");
        String password = System.getProperty("password");

        userName = userName != null && !userName.isEmpty() ? userName : mobileConfig.get(USERNAME);
        password = password != null && !password.isEmpty() ? password : mobileConfig.get(PASSWORD);

        doLogin(userName, password);

        Assert.assertTrue(verifyHomePage(), "Home page is not displayed");
        DriverConfig.setLogString("Click Thermostat in home page", true);
        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
        smallWait();

        // performTstatOperations();
        DriverConfig.setLogString("Click Home button", true);
        WebElement el6 = mobileOps.getDeviceDriver().findElement(By.id("thermoHomeButton0"));
        mobileUIAction.click(el6);
        smallWait();

        // performAwayOperations();
        performMoreOperations();
        WebElement back = mobileOps.getDeviceDriver().findElement(By.id("ppEnMoreButton"));
        mobileUIAction.click(back);
        mediumWait();

        doLogout();
    }

    /**
     * Invalid user name.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity1" }, retryAnalyzer = MobileRetryAnalyzer.class)
    public void invalidUserName() throws Exception {

        checkPrerequisite();

        String userName = mobileConfig.get(INVALID_USERNAME);
        String password = mobileConfig.get(INVALID_PASSWORD);

        doLogin(userName, password);

        DriverConfig.setLogString("Verify Home Page", true);
        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
    }

    /**
     * Regression test.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity" }, retryAnalyzer = MobileRetryAnalyzer.class, invocationCount = 10)
    public void regressionTest() throws Exception {

        checkPrerequisite();

        String userName = System.getProperty("userName");
        String password = System.getProperty("password");

        userName = userName != null && !userName.isEmpty() ? userName : mobileConfig.get(USERNAME);
        password = password != null && !password.isEmpty() ? password : mobileConfig.get(PASSWORD);

        doLogin(userName, password);

        Assert.assertTrue(verifyHomePage(), "Home page is not displayed");
        DriverConfig.setLogString("Click Thermostat in home page", true);

        waitUntil(FIVE_MINS);

        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
        smallWait();

        // performTstatOperations();
        DriverConfig.setLogString("Click Home button", true);
        WebElement el6 = mobileOps.getDeviceDriver().findElement(By.id("thermoHomeButton0"));
        mobileUIAction.click(el6);
        smallWait();

        // performAwayOperations();
        performMoreOperations();
        WebElement back = mobileOps.getDeviceDriver().findElement(By.id("ppEnMoreButton"));
        mobileUIAction.click(back);
        mediumWait();

        doLogout();
    }

    /**
     * Regression test1.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity" }, retryAnalyzer = MobileRetryAnalyzer.class)
    public void regressionTest1() throws Exception {

        checkPrerequisite();

        String userName = System.getProperty("userName");
        String password = System.getProperty("password");

        userName = userName != null && !userName.isEmpty() ? userName : mobileConfig.get(USERNAME);
        password = password != null && !password.isEmpty() ? password : mobileConfig.get(PASSWORD);

        doLogin(userName, password);

        Assert.assertTrue(verifyHomePage(), "Home page is not displayed");
        DriverConfig.setLogString("Click Thermostat in home page", true);

        waitUntil(TEN_MINS * 3);

        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
        smallWait();

        // performTstatOperations();
        DriverConfig.setLogString("Click Home button", true);
        WebElement el6 = mobileOps.getDeviceDriver().findElement(By.id("thermoHomeButton0"));
        mobileUIAction.click(el6);
        smallWait();

        // performAwayOperations();
        performMoreOperations();
        WebElement back = mobileOps.getDeviceDriver().findElement(By.id("ppEnMoreButton"));
        mobileUIAction.click(back);
        mediumWait();

        doLogout();
    }

    /**
     * Regression test2.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity2" }, retryAnalyzer = MobileRetryAnalyzer.class)
    public void regressionTest2() throws Exception {

        checkPrerequisite();

        String userName = System.getProperty("userName");
        String password = System.getProperty("password");

        userName = userName != null && !userName.isEmpty() ? userName : mobileConfig.get(USERNAME);
        password = password != null && !password.isEmpty() ? password : mobileConfig.get(PASSWORD);

        doLogin(userName, password);

        Assert.assertTrue(verifyHomePage(), "Home page is not displayed");
        DriverConfig.setLogString("Click Thermostat in home page", true);

        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
        smallWait();

        // performTstatOperations();
        DriverConfig.setLogString("Click Home button", true);
        WebElement el6 = mobileOps.getDeviceDriver().findElement(By.id("thermoHomeButton0"));
        mobileUIAction.click(el6);
        smallWait();

        // performAwayOperations();
        performMoreOperations();
        WebElement back = mobileOps.getDeviceDriver().findElement(By.id("ppEnMoreButton"));
        mobileUIAction.click(back);
        mediumWait();

        doLogout();
    }

    /**
     * Regression test3.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity3" }, retryAnalyzer = MobileRetryAnalyzer.class)
    public void regressionTest3() throws Exception {

        checkPrerequisite();

        String userName = mobileConfig.get(INVALID_USERNAME);
        String password = mobileConfig.get(INVALID_PASSWORD);

        doLogin(userName, password);

        DriverConfig.setLogString("Verify Home Page", true);
        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
    }

    /**
     * Regression test4.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity4" }, retryAnalyzer = MobileRetryAnalyzer.class)
    public void regressionTest4() throws Exception {

        checkPrerequisite();

        String userName = mobileConfig.get(INVALID_USERNAME);
        String password = mobileConfig.get(INVALID_PASSWORD);

        doLogin(userName, password);

        DriverConfig.setLogString("Verify Home Page", true);
        WebElement el4 = mobileOps.getDeviceDriver().findElement(By.id("thermostatLink0"));
        mobileUIAction.click(el4);
    }

    /**
     * Verify home page.
     * @return true, if successful
     */
    private boolean verifyHomePage() {

        DriverConfig.setLogString("Verify Home page is displayed", true);
        boolean isHomePageDispalyed = isDisplayedById(mobileOps.getDeviceDriver(),
                "thermostatLink0", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("Home page displayed : " + isHomePageDispalyed, true);
        return isHomePageDispalyed;
    }

    /**
     * Do login.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    private void doLogin(String userName, String password) throws Exception {

        DriverConfig.setLogString("Login to Consumer Portal with username: " + userName, true);
        mediumWait();

        mobileOps.switchToWebView();

        DriverConfig.setLogString("Check Username textField is displayed", true);
        WebElement el = mobileOps.getDeviceDriver().findElement(By.id("j_username"));
        el.clear();
        el.sendKeys(userName);
        smallWait();

        DriverConfig.setLogString("Check Password field is displayed", true);
        WebElement el2 = mobileOps.getDeviceDriver().findElement(By.id("j_password"));
        el2.clear();
        el2.sendKeys(password);
        mediumWait();

        DriverConfig.setLogString("Click Login Submit Button", true);
        WebElement el3 = mobileOps.getDeviceDriver().findElement(By.id("loginSubmit"));
        mobileUIAction.click(el3);

        mediumWait();
    }

    /**
     * Do logout.
     * @throws Exception the exception
     */
    private void doLogout() throws Exception {

        DriverConfig.setLogString("Do Logout", true);
        WebElement el5 = mobileOps.getDeviceDriver().findElement(By.id("aLogout"));
        mobileUIAction.click(el5);
        mediumWait();

        mobileUIAction.acceptAlert();
        mediumWait();
    }

    /**
     * Perform more operations.
     * @throws Exception the exception
     */
    private void performMoreOperations() throws Exception {

        DriverConfig.setLogString("Click More button", true);

        WebElement moreButton = mobileOps.getDeviceDriver().findElement(By.id("moreButton"));
        mobileUIAction.click(moreButton);
        smallWait();

        DriverConfig.setLogString("Click Privacy Link", true);
        WebElement privacy = mobileOps.getDeviceDriver().findElement(By.id("morePrivacy"));
        mobileUIAction.click(privacy);
        mediumWait();
        WebElement page = mobileOps.getDeviceDriver().findElement(By.id("privacy_en"));
        mobileUIAction.click(page);

        mobileUIAction.doSwipeDown(page, 133.0, 357.0, 100, 0.5);
        tinyWait();

        mobileUIAction.doSwipeDown(page, 133.0, 257.0, 100, 0.5);
        tinyWait();

        mobileUIAction.doSwipeUp(page, 133.0, 157.0, 100, 0.5);
        tinyWait();

        mobileUIAction.doSwipeUp(page, 133.0, 257.0, 100, 0.5);
        tinyWait();
    }

    /**
     * Perform away operations.
     * @throws Exception the exception
     */
    @SuppressWarnings("unused")
    private void performAwayOperations() throws Exception {

        DriverConfig.setLogString("Click Setaway", true);
        WebElement setAway = mobileOps.getDeviceDriver().findElement(By.id("setAway0"));
        mobileUIAction.click(setAway);
        smallWait();

        DriverConfig.setLogString("Click edit", true);
        WebElement editAway = mobileOps.getDeviceDriver().findElement(By.id("edit0"));
        mobileUIAction.click(editAway);
        smallWait();

        setEditAwayHeatPicker();

        setEditAwayCoolPicker();

        DriverConfig.setLogString("Click Save", true);
        WebElement saveButton = mobileOps.getDeviceDriver().findElement(By.id("save_button"));
        mobileUIAction.click(saveButton);

        largeWait();
        mobileUIAction.click(setAway);
    }

    /**
     * Sets the edit away cool picker.
     * @throws Exception the exception
     */
    private void setEditAwayCoolPicker() throws Exception {

        DriverConfig.setLogString("Change cool values", true);
        WebElement coolPicker = mobileOps.getDeviceDriver().findElement(By.id("cool_select_dummy"));

        mobileUIAction.click(coolPicker);
        smallWait();

        changeSetPointBy5(coolPicker, true);
    }

    /**
     * Change set point by5.
     * @param picker the picker
     * @param increment the increment
     * @throws Exception the exception
     */
    private void changeSetPointBy5(WebElement picker, boolean increment) throws Exception {

        int pickerOldValue = Integer.valueOf(picker.getAttribute("value"));
        for (int j = 1; j < 4; j++) {
            // int pickerValueToSet = increment ? pickerOldValue + j : pickerOldValue - j;
            WebElement pickerNewValue = mobileOps.getDeviceDriver().findElement(
                    By.xpath("//*[contains(@class,'dw-v')]/div[text()= '"
                            + (increment ? ++pickerOldValue : --pickerOldValue) + "']"));
            mobileUIAction.click(pickerNewValue);
        }
        WebElement pickerSetValue = mobileOps.getDeviceDriver().findElement(
                By.xpath("//*[contains(@class,'dwbw')]/span[text()= 'Set']"));
        mobileUIAction.click(pickerSetValue);
        smallWait();
    }

    /**
     * Sets the edit away heat picker.
     * @throws Exception the exception
     */
    private void setEditAwayHeatPicker() throws Exception {

        DriverConfig.setLogString("Change heat values", true);
        WebElement heatPicker = mobileOps.getDeviceDriver().findElement(By.id("heat_select_dummy"));

        mobileUIAction.click(heatPicker);
        smallWait();

        changeSetPointBy5(heatPicker, false);
    }

    /**
     * Perform tstat operations.
     * @throws Exception the exception
     */
    @SuppressWarnings("unused")
    private void performTstatOperations() throws Exception {

        DriverConfig.setLogString("Click mode", true);
        WebElement mode = mobileOps.getDeviceDriver().findElement(By.id("forLoc0mode0"));
        mobileUIAction.click(mode);
        smallWait();

        DriverConfig.setLogString("Change the mode", true);
        WebElement radioCool = mobileOps.getDeviceDriver().findElement(
                By.xpath("//*[@id='forLoc0hvacRadioBtn20']/div/input"));
        if (radioCool.isSelected()) {
            DriverConfig.setLogString("Click Heat", true);
            WebElement heat = mobileOps.getDeviceDriver().findElement(
                    By.xpath("//*[@id='forLoc0hvacRadioBtn10']/div/label"));
            mobileUIAction.click(heat);
        } else {
            DriverConfig.setLogString("Click Cool", true);
            WebElement cool = mobileOps.getDeviceDriver().findElement(
                    By.xpath("//*[@id='forLoc0hvacRadioBtn20']/div/label"));
            mobileUIAction.click(cool);
        }

        mediumWait();
        mobileUIAction.click(mode);
        smallWait();

        DriverConfig.setLogString("Change temperature", true);

        WebElement arrow_up = mobileOps.getDeviceDriver().findElement(By.id("forLoc0arrow0_up"));
        mobileUIAction.click(arrow_up);
        mobileUIAction.click(arrow_up);

        mediumWait();
    }

    /**
     * Check prerequisite.
     */
    private void checkPrerequisite() {

        DriverConfig.setLogString("Verify the prerequisite for test", true);

        if (hasErrors) {
            DriverConfig.setLogString("Error Message :" + getErrorMsg(), true);
            Assert.fail(getErrorMsg());
        }
    }
}
