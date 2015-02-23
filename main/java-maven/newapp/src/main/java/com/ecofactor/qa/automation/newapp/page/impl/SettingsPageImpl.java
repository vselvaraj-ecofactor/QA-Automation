/*
 * SettingsPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.TINY_TIMEOUT;
import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.tinyWait;
import static com.ecofactor.qa.automation.util.PageUtil.retrieveElementByAttributeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ecofactor.common.pojo.User;
import com.ecofactor.qa.automation.dao.EFUserDao;
import com.ecofactor.qa.automation.dao.LoginTokensDao;
import com.ecofactor.qa.automation.newapp.page.SettingsPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.pojo.LoginTokens;

import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class SettingsPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SettingsPageImpl extends AbstractAuthenticationPageImpl implements SettingsPage {

    /** The th page ui. */
    @Inject
    protected ThermostatPageUI thPageUI;

    /** The th page ops. */
    @Inject
    protected ThermostatPageOps thPageOps;

    /** The ef user dao. */
    @Inject
    private EFUserDao efUserDao;

    /** The user dao. */
    @Inject
    private EFUserDao userDao;

    /** The login tokens dao. */
    @Inject
    private LoginTokensDao loginTokensDao;

    /** The Constant AWAY_TEMPERATURE_MENU_ITEM. */
    private static final String SETTINGS_ICON = ".menu_row.settings";

    /** The Constant PRIVACY_POLICY. */
    private static final String PRIVACY_POLICY = ".item.grey_arrow.privacy_policy";

    /** The Constant USER_AGREEMENT. */
    private static final String USER_AGREEMENT = ".item.grey_arrow.user_agreement";

    /** The Constant INSTALLED_THERMOSTAT. */
    private static final String INSTALLED_THERMOSTAT = ".section_label.installed_thermostat";

    /** The Constant PREFERENCES. */
    private static final String PREFERENCES = ".section_label.preferences";

    /** The Constant LANGUAGE. */
    private static final String LANGUAGE = ".section_label.language_locale";

    /** The Constant EMAIL. */
    private static final String EMAIL = ".section_label.email";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = ".section_label.password";

    /** The Constant SUPPORT. */
    private static final String SUPPORT = ".section_label.support";

    /** The Constant LEGAL. */
    private static final String LEGAL = ".section_label.legal";

    /** The Constant THERMOSTAT_FIELD. */
    private static final String THERMOSTAT_FIELD = ".item.thermostat.first_thermostat";

    /** The Constant THERMOSTAT_FIELD_NAME. */
    private static final String THERMOSTAT_FIELD_NAME = "HALL_TEST";

    /** The Constant THERMOSTAT_FIELD_VALUE. */
    private static final String THERMOSTAT_FIELD_VALUE = ".name_input.item.first_thermostat";

    /** The Constant MENU_BTN. */
    private static final String MENU_BTN = ".menuClick.light";

    /** The Constant TST_NAME_SIXTY_CHAR. */
    private static final String TST_NAME_SIXTY_CHAR = "InstalledThermostatNameassixtycharacterswithinthenupdateNames";

    /** The Constant ELEMENTS. */
    private static final String ELEMENTS = ".elements";

    /** The Constant BUTTON. */
    private static final String BUTTON = ".changeButton";

    /** The Constant FAQ_TAB. */
    private static final String FAQ_TAB = ".item.faq.clickable.grey_arrow";

    /** The Constant THERMOSTAT_NAME. */
    private static final String THERMOSTAT_NAME = ".item.thermostat.first_thermostat";

    /** The Constant CHANGE_PASSWORD. */
    private static final String CHANGE_PASSWORD = ".change_password.clickable.item.grey_arrow";

    /** The Constant PASSWORD_ERROR_MESSAGE. */
    private static final String PASSWORD_ERROR_MESSAGE = ".error.new_password_error.password_error";

    /** The Constant PASSWORD_REQUIREMENT_MESSAGE. */
    private static final String PASSWORD_REQUIREMENT_MESSAGE = "Password must be at least 8 characters long and contain both letters and numbers";

    /** The Constant CURRENT_PASSWD_FILED. */
    private static final String CURRENT_PASSWD_FILED = "input_old_password";

    /** The Constant NEW_PASSWD_FILED. */
    private static final String NEW_PASSWD_FILED = "input_new_password";

    /** The Constant REPEAT_PASSWD_FILED. */
    private static final String REPEAT_PASSWD_FILED = "input_confirm_new_password";

    /** The Constant PRIVACY_LINK. */
    private static final String PRIVACY_LINK = ".item.grey_arrow.privacy_policy";

    /** The Constant USERAGGREMENT_LINK. */
    private static final String USERAGGREMENT_LINK = ".item.grey_arrow.user_agreement";

    /** The Constant BACK_BUTTON. */
    private static final String BACK_BUTTON = "div.back";

    /** The Constant ATTRIBUTE_VALUE. */
    private static final String ATTRIBUTE_TST = "tstat";

    /** The Constant ATTRIBUTE_VALUE. */
    private static final String ATTRIBUTE_VALUE = "value";

    /** The Constant TAG_NAME. */
    private static final String TAG_NAME = "input";

    /** The Constant TST_1. */
    private static final String TST_1 = "23415";

    /** The Constant TST_2. */
    private static final String TST_2 = "23416";
    
    private static final String CURRENT_PASSWD_ERROR = ".error.old_password_error password_error";
    
    private static final String NEW_PASSWD_ERROR = ".error.new_password_error password_error";
    
    private static final String CONFIRM_PASSWD_ERROR = ".error.confirm_password_error";

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        setLogString("Cleanup in Savings Page.", true, CustomLogLevel.HIGH);

    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        setLogString("Verify settings page is loaded", true);
        return isDisplayed(getDriver(), By.cssSelector(INSTALLED_THERMOSTAT), TINY_TIMEOUT)
                || isDisplayed(getDriver(), By.cssSelector(PREFERENCES), TINY_TIMEOUT)
                || isDisplayed(getDriver(), By.cssSelector(LANGUAGE), TINY_TIMEOUT)
                || isDisplayed(getDriver(), By.cssSelector(EMAIL), TINY_TIMEOUT)
                || isDisplayed(getDriver(), By.cssSelector(PASSWORD), TINY_TIMEOUT)
                || isDisplayed(getDriver(), By.cssSelector(SUPPORT), TINY_TIMEOUT)
                || isDisplayed(getDriver(), By.cssSelector(LEGAL), TINY_TIMEOUT);
    }

    /**
     * Click menu settings.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickMenuSettings()
     */
    @Override
    public void clickMenuSettings() {

        setLogString("Click Accounts settings in Menu Page", true);
        final WebElement menuIcon = getElement(getDriver(), By.cssSelector(SETTINGS_ICON),
                TINY_TIMEOUT);
        menuIcon.click();
        getAction().rejectAlert();
    }

    /**
     * Click privacy policy tab.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickPrivacyPolicyTab()
     */
    @Override
    public void clickPrivacyPolicyTab() {

        setLogString("Click Legal Privacy Policy Tab", true);
        final WebElement privacyElement = getElement(getDriver(), By.cssSelector(PRIVACY_POLICY),
                TINY_TIMEOUT);
        privacyElement.click();
        getAction().rejectAlert();
    }

    /**
     * Click user agreement tab.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickUserAgreementTab()
     */
    @Override
    public void clickUserAgreementTab() {

        setLogString("Click Legal User Agreement Tab", true);
        final WebElement userElement = getElement(getDriver(), By.cssSelector(USER_AGREEMENT),
                TINY_TIMEOUT);
        userElement.click();
        getAction().rejectAlert();
    }

    /**
     * Gets the current url.
     * @return the current url
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getCurrentURL()
     */
    @Override
    public String getCurrentURL() {

        setLogString("Get Current URL from browser", true);
        setLogString("Current URL: " + getDriver().getCurrentUrl(), true);
        return getDriver().getCurrentUrl();
    }

    /**
     * Click menu highlighted.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickMenuHighlighted()
     */
    @Override
    public void clickMenuHighlighted() {

        setLogString("Click Menu in Thermostat", true, CustomLogLevel.LOW);
        final WebElement menuBtn = getElement(getDriver(), By.cssSelector(MENU_BTN), TINY_TIMEOUT);
        WaitUtil.oneSec();
        getAction().click(menuBtn);
        getAction().rejectAlert();
    }

    /**
     * Change thermostat name.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#changeThermostatName()
     */
    @Override
    public void changeThermostatName() {

        setLogString("Click Thermostat name Field", true, CustomLogLevel.LOW);
        final WebElement thermostatField = getElement(getDriver(),
                By.cssSelector(THERMOSTAT_FIELD), TINY_TIMEOUT);
        getAction().click(thermostatField);
        WaitUtil.oneSec();
        getAction().rejectAlert();

        setLogString("Thermostat Field Name Changed.", true, CustomLogLevel.LOW);
        WaitUtil.oneSec();
        final WebElement fieldValue = getElement(getDriver(),
                By.cssSelector(THERMOSTAT_FIELD_VALUE), TINY_TIMEOUT);
        fieldValue.clear();
        WaitUtil.oneSec();
        fieldValue.sendKeys(THERMOSTAT_FIELD_NAME);
        WaitUtil.fourSec();

        setLogString("Updated Thermostat Field Name.", true, CustomLogLevel.LOW);
        final WebElement thermostatFieldName = getElement(getDriver(),
                By.cssSelector(INSTALLED_THERMOSTAT), TINY_TIMEOUT);
        getAction().click(thermostatFieldName);
        getAction().rejectAlert();

        tinyWait();
        clickMenuHighlighted();
    }

    /**
     * Change sixty char tst name.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#changeSixtyCharTstName()
     */
    @Override
    public void changeSixtyCharTstName() {

        setLogString("Click Thermostat name Field", true, CustomLogLevel.LOW);
        final WebElement thermostatField = getElement(getDriver(),
                By.cssSelector(THERMOSTAT_FIELD), TINY_TIMEOUT);
        getAction().click(thermostatField);
        WaitUtil.oneSec();
        getAction().rejectAlert();

        setLogString("Thermostat Field Name Changed.", true, CustomLogLevel.LOW);
        WaitUtil.oneSec();
        final WebElement fieldValue = getElement(getDriver(),
                By.cssSelector(THERMOSTAT_FIELD_VALUE), TINY_TIMEOUT);
        fieldValue.clear();
        WaitUtil.oneSec();
        fieldValue.sendKeys(TST_NAME_SIXTY_CHAR);
        WaitUtil.fourSec();

        setLogString("Updated Thermostat Field Name.", true, CustomLogLevel.LOW);
        getToastErrorMessage();
        final WebElement thermostatFieldName = getElement(getDriver(),
                By.cssSelector(INSTALLED_THERMOSTAT), TINY_TIMEOUT);
        getAction().click(thermostatFieldName);
        getAction().rejectAlert();

        tinyWait();
        clickMenuHighlighted();
    }

    /**
     * Verify language tab displayed.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#verifyLanguageTabDisplayed()
     */
    @Override
    public boolean verifyLanguageTabDisplayed() {

        setLogString("Verify Language Tab displayed", true, CustomLogLevel.LOW);
        return isDisplayed(getDriver(), By.cssSelector(LANGUAGE), TINY_TIMEOUT);
    }

    /**
     * Verify support tab displayed.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#verifySupportTabDisplayed()
     */
    @Override
    public boolean verifySupportTabDisplayed() {

        setLogString("Verify Support Tab displayed", true, CustomLogLevel.LOW);
        return isDisplayed(getDriver(), By.cssSelector(SUPPORT), TINY_TIMEOUT);
    }

    /**
     * Verify password tab displayed.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#verifyPasswordTabDisplayed()
     */
    @Override
    public boolean verifyPasswordTabDisplayed() {

        setLogString("Verify Password Tab displayed", true, CustomLogLevel.LOW);
        return isDisplayed(getDriver(), By.cssSelector(PASSWORD), TINY_TIMEOUT);
    }

    /**
     * Check change password tab.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#checkChangePasswordTab()
     */
    @Override
    public void checkChangePasswordTab() {

        final boolean languageTab = verifyLanguageTabDisplayed();
        if (languageTab == true) {
            Assert.assertTrue(verifyLanguageTabDisplayed(), "Settings Page is not loaded");
            final boolean passwordTab = verifyPasswordTabDisplayed();
            if (passwordTab == true) {

                final boolean supportTab = verifySupportTabDisplayed();
                if (supportTab == true) {

                    setLogString("Password Tab below language tab and above support tab", true,
                            CustomLogLevel.LOW);
                } else {
                    setLogString("Password Tab not in a order", true, CustomLogLevel.LOW);
                }
            }
        }
    }

    /**
     * Verify fields in password tab.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#VerifyFieldsInPasswordTab()
     */
    @Override
    public boolean VerifyFieldsInPasswordTab() {

        setLogString("Verify fields are in password tab", true, CustomLogLevel.LOW);
        boolean fieldsDisplayed = false;
        final WebElement fields = getElement(getDriver(), By.cssSelector(ELEMENTS), TINY_TIMEOUT);
        final String className = ".prompt.old_password,.prompt.new_password,.prompt.confirm_new_password";
        String values = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(className, ",");
        while (stringTokenizer.hasMoreElements()) {
            values = stringTokenizer.nextToken();
            final WebElement textFiled = getElementBySubElement(getDriver(), fields,
                    By.cssSelector(values), TINY_TIMEOUT);
            setLogString("Text Fields in Password Tab" + textFiled.getText(), true,
                    CustomLogLevel.LOW);
            fieldsDisplayed = isDisplayed(getDriver(), By.cssSelector(values), TINY_TIMEOUT);
        }
        return fieldsDisplayed && isDisplayed(getDriver(), By.cssSelector(BUTTON), TINY_TIMEOUT);
    }

    /**
     * Click password tab.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickPasswordTab()
     */
    @Override
    public void clickPasswordTab() {

        setLogString("Click password tab", true, CustomLogLevel.LOW);
        final WebElement passwordTab = getElement(getDriver(), By.cssSelector(CHANGE_PASSWORD),
                TINY_TIMEOUT);
        WaitUtil.oneSec();
        passwordTab.click();
        getAction().rejectAlert();
    }

    /**
     * Click faq tab.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickFAQTab()
     */
    @Override
    public void clickFAQTab() {

        setLogString("Click FAQ tab", true, CustomLogLevel.LOW);
        final WebElement faqElement = getElement(getDriver(), By.cssSelector(FAQ_TAB), TINY_TIMEOUT);
        faqElement.click();
        getAction().rejectAlert();
    }

    /**
     * Gets the title.
     * @return the title
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getTitle(org.openqa.selenium.WebDriver)
     */
    @Override
    public String getTitle() {

        setLogString("Get title of FAQ page", true, CustomLogLevel.LOW);
        getDriver().getWindowHandle();
        String name = "";
        for (String winH : getDriver().getWindowHandles()) {
            name = getDriver().switchTo().window(winH).getTitle();
        }
        setLogString("Title of Page: " + name, true, CustomLogLevel.LOW);
        return name;
    }

    /**
     * Gets the thermostat name.
     * @return the thermostat name
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getThermostatName()
     */
    @Override
    public String getThermostatName() {

        setLogString("Get Thermostat Name from Settings page", true, CustomLogLevel.LOW);
        final WebElement thermostatName = getElement(getDriver(), By.cssSelector(THERMOSTAT_NAME),
                TINY_TIMEOUT);
        setLogString("Thermostat Name from settings page: " + thermostatName.getText(), true,
                CustomLogLevel.LOW);
        return thermostatName.getText();
    }

    /**
     * Gets the title and url.
     * @return the title and url
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getTitleAndUrl()
     */
    @Override
    public void getTitleAndUrl() {

        final String currentUrl = getURLFromPhoneBrowser(FAQ_TAB);
        setLogString("Get URL :" + currentUrl, true, CustomLogLevel.LOW);
    }

    /**
     * Gets the multiple tsts names.
     * @param locationName the location name
     * @param noOfTsts the no of tsts
     * @return the multiple tsts names
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getMultipleTstsNames(java.lang.String,
     *      int)
     */
    @Override
    public void getMultipleTstsNames(final String locationName, final int noOfTsts) {

        setLogString("Fetch multiple thermostats name from settings page", true, CustomLogLevel.LOW);
        final HashMap<String, String> thMap = new HashMap<String, String>();

        final List<String> locationList = new ArrayList<String>();
        for (int i = 0; i < noOfTsts; i++) {

            final String thermostatName = thPageUI.getCurrentThermostatName().trim();
            if (thMap.get(locationName) != null && !thMap.get(locationName).isEmpty()) {
                final String value = thMap.get(locationName);
                thMap.put(locationName, value + "##" + thermostatName);
            } else {
                thMap.put(locationName, thermostatName);
            }
            if (!locationList.contains(locationName)) {
                locationList.add(locationName);
            }
            thPageOps.swipe("left");
        }
        /*
         * List<WebElement> list = retrieveElementsByAttributeValueList(getDriver(), TAG_DIV,
         * ATTR_CLASS, ".item.thermostat"); System.out.println(list.size()+"Sfcs"); if
         * (isDisplayed(getDriver(), By.cssSelector(".item.thermostat.first_thermostat"),
         * TINY_TIMEOUT)) { final WebElement tstTwo = getElement(getDriver(),
         * By.cssSelector(".item.thermostat.first_thermostat"), TINY_TIMEOUT);
         * setLogString("ThermostatName :" + tstTwo.getText(), true, CustomLogLevel.LOW);
         * System.out.println(tstTwo.getAttribute("class") + "DSf"); thMap.put(locationName,
         * tstTwo.getText()); } if (isDisplayed(getDriver(), By.cssSelector(".item.thermostat"),
         * TINY_TIMEOUT)) { final WebElement tstOne = getElement(getDriver(),
         * By.cssSelector(".item.thermostat"), TINY_TIMEOUT); setLogString("ThermostatName :" +
         * tstOne.getText(), true, CustomLogLevel.LOW);
         * System.out.println(tstOne.getAttribute("class") + "DSf"); thMap.put(locationName,
         * tstOne.getText()); }
         */
        verifyTstAlphabeticlaOrder(thMap);
    }

    /**
     * Gets the password requirement error msg.
     * @return the password requirement error msg
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getPasswordRequirementErrorMsg()
     */
    @Override
    public void getPasswordRequirementErrorMsg() {

        setLogString("Get Password Requirement error message", true, CustomLogLevel.LOW);
        final WebElement msgElement = getElement(getDriver(),
                By.cssSelector(PASSWORD_ERROR_MESSAGE), TINY_TIMEOUT);
        Assert.assertTrue(PASSWORD_REQUIREMENT_MESSAGE.equalsIgnoreCase(msgElement.getText()));
        setLogString("Password Requirement error message : " + msgElement.getText(), true,
                CustomLogLevel.LOW);
    }

    /**
     * Checks if is password updated.
     * @param userName the user name
     * @param password the password
     * @param newPasswd the new passwd
     * @return true, if is password updated
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#isPasswordUpdated(java.lang.String)
     */
    @Override
    public boolean isPasswordUpdated(final String userName, final String password,
            final String newPasswd) {

        setLogString("Old Password :" + password, true, CustomLogLevel.LOW);
        setLogString("Verify password in DB", true, CustomLogLevel.LOW);
        final User user = userDao.findByUserName(userName);
        setLogString("Current Password :" + user.getPasswd(), true, CustomLogLevel.LOW);
        return user.getPasswd().equalsIgnoreCase(newPasswd);
    }

    /**
     * Fill change password fields.
     * @param currentPwd the current pwd
     * @param newPwd the new pwd
     * @param repeatPwd the repeat pwd
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#fillChangePasswordFields(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void fillChangePasswordFields(String currentPwd, String newPwd, String repeatPwd) {

        fillCurrentPassword(currentPwd);
        fillNewPassword(newPwd);
        fillRepeatPassword(repeatPwd);
    }

    /**
     * Click change password.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickChangePassword()
     */
    @Override
    public void clickChangePasswordBtn() {

        setLogString("Click change apssword button", true, CustomLogLevel.LOW);
        final WebElement changeButton = getElement(getDriver(), By.cssSelector(BUTTON),
                TINY_TIMEOUT);
        changeButton.click();
    }

    /**
     * Gets the last updated time.
     * @param userName the user name
     * @return the last updated time
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getLastUpdatedTime(java.lang.String)
     */
    @Override
    public void getLastUpdatedTime(final String userName) {

        setLogString("Get last updated time", true, CustomLogLevel.LOW);
        final LoginTokens record = loginTokensDao.findByUserName(userName);
        setLogString("Last updated time :" + record.getLastUsed(), true, CustomLogLevel.LOW);
    }

    /**
     * Checks if is password updated.
     * @param userName the user name
     * @return true, if is password updated
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#isPasswordUpdated(java.lang.String)
     */
    @Override
    public boolean isPasswordUpdated(String userName) {

        setLogString("verify Password updated", true, CustomLogLevel.LOW);
        final User user = efUserDao.findByUserName(userName);
        return efUserDao.updatePasswordToEcofactor(user);
    }

    /**
     * Checks if is privacy link clickable.
     * @return true, if is privacy link clickable
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#isPrivacyLinkClickable()
     */
    @Override
    public boolean isPrivacyLinkClickable() {

        setLogString("verify Privacy Link is Clickable", true, CustomLogLevel.LOW);
        final WebElement privacyLink = getElement(getDriver(), By.cssSelector(PRIVACY_LINK),
                TINY_TIMEOUT);
        return isClickable(getDriver(), privacyLink, TINY_TIMEOUT);
    }

    /**
     * Checks if is user agreement clickable.
     * @return true, if is user agreement clickable
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#isUserAggrementClickable()
     */
    @Override
    public boolean isUserAggrementClickable() {

        setLogString("verify User Aggrement Link is Clickable", true, CustomLogLevel.LOW);
        final WebElement userAgreementLink = getElement(getDriver(),
                By.cssSelector(USERAGGREMENT_LINK), TINY_TIMEOUT);
        return isClickable(getDriver(), userAgreementLink, TINY_TIMEOUT);
    }

    /**
     * Checks if is FAQ clickable.
     * @return true, if is FAQ clickable
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#isFAQClickable()
     */
    @Override
    public boolean isFAQClickable() {

        setLogString("verify User Aggrement Link is Clickable", true, CustomLogLevel.LOW);
        final WebElement faqTab = getElement(getDriver(), By.cssSelector(FAQ_TAB), TINY_TIMEOUT);
        return isClickable(getDriver(), faqTab, TINY_TIMEOUT);
    }

    /**
     * Tst sorting.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#verifyTstNames()
     */
    @Override
    public void verifyTstNames() {

        setLogString("verify Thermostat names", true, CustomLogLevel.LOW);
        final WebElement tstNameOne = retrieveElementByAttributeValue(getDriver(), TAG_NAME,
                ATTRIBUTE_TST, TST_1);
        final WebElement tstNameTwo = retrieveElementByAttributeValue(getDriver(), TAG_NAME,
                ATTRIBUTE_TST, TST_2);
        setLogString("Thermostat names:" + tstNameOne.getAttribute(ATTRIBUTE_VALUE) + " "
                + tstNameTwo.getAttribute(ATTRIBUTE_VALUE), true, CustomLogLevel.LOW);
    }

    /**
     * Click back.
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#clickBack()
     */
    @Override
    public void clickBack() {

        setLogString("Click Back", true, CustomLogLevel.LOW);
        final WebElement backElement = getElement(getDriver(), By.cssSelector(BACK_BUTTON),
                TINY_TIMEOUT);
        getAction().click(backElement);
    }
    
    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getCurrentPwdErrorMsg()
     */
    @Override
    public String getCurrentPwdErrorMsg() {

        final WebElement currentPwdError = getElement(getDriver(), By.cssSelector(CURRENT_PASSWD_ERROR),
                TINY_TIMEOUT);
        setLogString("Current Password Error : "+ currentPwdError.getText(), true, CustomLogLevel.LOW);
        return currentPwdError.getText();
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getInvalidPwdErrorMsg()
     */
    @Override
    public String getInvalidPwdErrorMsg() {

        final WebElement invalidPwdError = getElement(getDriver(), By.cssSelector(NEW_PASSWD_ERROR),
                TINY_TIMEOUT);
        setLogString("Invalid Password Error : "+ invalidPwdError.getText(), true, CustomLogLevel.LOW);
        return invalidPwdError.getText();
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#getConfirmPwdErrorMsg()
     */
    @Override
    public String getConfirmPwdErrorMsg() {

        final WebElement repeatPwdError = getElement(getDriver(), By.cssSelector(CONFIRM_PASSWD_ERROR),
                TINY_TIMEOUT);
        setLogString("Repeat Password Error : "+ repeatPwdError.getText(), true, CustomLogLevel.LOW);
        return repeatPwdError.getText();
    }


    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.SettingsPage#isBackBtnDisplayed()
     */
    @Override
    public boolean isBackBtnDisplayed() {

        
        return isDisplayed(getDriver(), By.className("back"), TINY_TIMEOUT);
    }

    /**
     * Fill current password.
     * @param currentPasswd the current passwd
     */
    private void fillCurrentPassword(String currentPasswd) {

        setLogString("Current Password  : "+ currentPasswd, true, CustomLogLevel.LOW);
        if (currentPasswd != null && !currentPasswd.isEmpty()) {
            clearAndInput(getDriver(), By.className(CURRENT_PASSWD_FILED), currentPasswd);
        }

    }
    
    /**
     * Fill new password.
     * @param newPasswd the new passwd
     */
    private void fillNewPassword(String newPasswd) {
        
        setLogString("New Password  : "+ newPasswd, true, CustomLogLevel.LOW);
        if (newPasswd != null && !newPasswd.isEmpty()) {
            clearAndInput(getDriver(), By.className(NEW_PASSWD_FILED), newPasswd);
        }

    }
    
    /**
     * Fill repeat password.
     * @param repeatPasswd the repeat passwd
     */
    private void fillRepeatPassword(String repeatPasswd) {
        
        setLogString("Repeat Password  : "+ repeatPasswd, true, CustomLogLevel.LOW);
        if (repeatPasswd != null && !repeatPasswd.isEmpty()) {
            clearAndInput(getDriver(), By.className(REPEAT_PASSWD_FILED), repeatPasswd);
        }

    }

    /**
     * Gets the URL from phone browser.
     * @param element the element
     * @return the URL from phone browser
     */
    private String getURLFromPhoneBrowser(final String element) {

        setLogString("Get URL from phone browser", true, CustomLogLevel.LOW);
        final boolean elementDisplayed = isDisplayed(getDriver(), By.cssSelector(element),
                TINY_TIMEOUT);
        String currentURL = "";
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        final WebElement faqElement = getElement(getDriver(), By.cssSelector(element), TINY_TIMEOUT);
        if (elementDisplayed == true) {
            final String previousUrl = getDriver().getCurrentUrl();
            faqElement.click();
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {

                    return (d.getCurrentUrl() != previousUrl);
                }
            };
            wait.until(e);
            currentURL = getDriver().getCurrentUrl();

        }
        return currentURL;
    }

    /**
     * Verify tst alphabeticla order.
     * @param thMap the th map
     */
    private void verifyTstAlphabeticlaOrder(HashMap<String, String> thMap) {

        for (final String key : thMap.keySet()) {
            final String value = thMap.get(key);
            final List<String> tstatUIList = Arrays.asList(value);
            final List<String> tstatUISortList = new ArrayList<>();
            tstatUISortList.addAll(tstatUIList);
            Collections.sort(tstatUISortList);
            for (int i = 0; i < tstatUISortList.size(); i++) {
                final String thName = tstatUISortList.get(i);
                final String thUIName = tstatUIList.get(i);
                Assert.assertTrue(thName.equalsIgnoreCase(thUIName),
                        "Sorting Fails for thermostat name");
            }
        }
    }

}
