/*
 * SettingsPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.page.impl.SettingsPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface SettingsPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = SettingsPageImpl.class)
public interface SettingsPage extends BasePage {

    /**
     * Click menu settings.
     */
    public void clickMenuSettings();

    /**
     * Click privacy policy tab.
     */
    public void clickPrivacyPolicyTab();

    /**
     * Click user agreement tab.
     */
    public void clickUserAgreementTab();

    /**
     * Gets the current url.
     * @return the current url
     */
    public String getCurrentURL();

    /**
     * Change thermostat name.
     */
    public void changeThermostatName();

    /**
     * Click menu highlighted.
     */
    public void clickMenuHighlighted();

    /**
     * Change sixty char tst name.
     */
    public void changeSixtyCharTstName();

    /**
     * Verify language tab displayed.
     * @return true, if successful
     */
    public boolean verifyLanguageTabDisplayed();

    /**
     * Verify support tab displayed.
     * @return true, if successful
     */
    public boolean verifySupportTabDisplayed();

    /**
     * Verify password tab displayed.
     * @return true, if successful
     */
    public boolean verifyPasswordTabDisplayed();

    /**
     * Check change password tab.
     */
    public void checkChangePasswordTab();

    /**
     * Verify fields in password tab.
     * @return true, if successful
     */
    public boolean VerifyFieldsInPasswordTab();

    /**
     * Click password tab.
     */
    public void clickPasswordTab();

    /**
     * Click faq tab.
     */
    public void clickFAQTab();

    /**
     * Gets the title.
     * @return the title
     */
    public String getTitle();

    /**
     * Gets the thermostat name.
     * @return the thermostat name
     */
    public String getThermostatName();

    /**
     * Gets the title and url.
     * @return the title and url
     */
    public void getTitleAndUrl();

    /**
     * Gets the mulitiple tsts names.
     * @param locationName the location name
     * @param noOfTsts the no of tsts
     * @return the multiple tsts names
     */
    public void getMultipleTstsNames(final String locationName, final int noOfTsts);

    /**
     * Gets the password requirement error msg.
     * @return the password requirement error msg
     */
    public void getPasswordRequirementErrorMsg();

    /**
     * Checks if is password updated.
     * @param username the username
     * @param password the password
     * @param newPasswd the new passwd
     * @return true, if is password updated
     */
    public boolean isPasswordUpdated(final String username, final String password,
            final String newPasswd);

    /**
     * Fill change password fields.
     * @param currentPwd the current pwd
     * @param newPwd the new pwd
     * @param repeatPwd the repeat pwd
     */
    public void fillChangePasswordFields(final String currentPwd, final String newPwd,
            final String repeatPwd);

    /**
     * Click change password.
     */
    public void clickChangePasswordBtn();

    /**
     * Clear tokens.
     * @param userName the user name
     * @return the last updated time
     */
    public void getLastUpdatedTime(final String userName);

    /**
     * Checks if is password updated.
     * @param userName the user name
     * @return true, if is password updated
     */
    public boolean isPasswordUpdated(final String userName);

    /**
     * Checks if is privacy link clickable.
     * @return true, if is privacy link clickable
     */
    public boolean isPrivacyLinkClickable();

    /**
     * Checks if is user agreement clickable.
     * @return true, if is user agreement clickable
     */
    public boolean isUserAggrementClickable();

    /**
     * Checks if is FAQ clickable.
     * @return true, if is FAQ clickable
     */
    public boolean isFAQClickable();

    /**
     * Tst sorting.
     */
    public void verifyTstNames();

    /**
     * Click back.
     */
    public void clickBack();
    
    /**
     * Gets the current pwd error msg.
     * @return the current pwd error msg
     */
    public String getCurrentPwdErrorMsg();
    
    /**
     * Gets the invalid pwd error msg.
     * @return the invalid pwd error msg
     */
    public String getInvalidPwdErrorMsg();
    
    /**
     * Gets the confirm pwd error msg.
     * @return the confirm pwd error msg
     */
    public String getConfirmPwdErrorMsg();
    
    /**
     * Checks if is back btn displayed.
     * @return true, if is back btn displayed
     */
    public boolean isBackBtnDisplayed();
}
