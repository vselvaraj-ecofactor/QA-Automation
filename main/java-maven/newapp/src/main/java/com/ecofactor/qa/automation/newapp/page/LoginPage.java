/*
 * LoginPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import org.joda.time.LocalTime;

import com.ecofactor.qa.automation.newapp.page.impl.LoginPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface LoginPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = LoginPageImpl.class)
public interface LoginPage extends BasePage {

    /**
     * getting values of userName and Password.
     * @param userName the user name.
     * @param password the password.
     */
    void userNamePasswordFields(final String userName, final String password);

    /**
     * click the submit button.
     */
    void clickSubmit(final String userName);

    /**
     * Login.
     * @param userName the user name
     * @param password the password
     */
    void login(final String userName, final String password);

    /**
     * Click login.
     * @param userName the user name
     * @param password the password
     */
    void clickLogin(final String userName, final String password);

    /**
     * Checks if is logged in.
     * @return true, if is logged in
     */
    boolean isLoggedIn();

    /**
     * Sets the logged in.
     * @param loggedIn the new logged in
     */
    void setLoggedIn(boolean loggedIn);

    /**
     * Checks if is logged in.
     * @param userName the user name
     * @return true, if is logged in
     */
    boolean isLoggedIn(final String userName);

    /**
     * Sets the logged in user.
     * @param loggedIn the new logged in user
     */
    void setLoggedInUser(final String loggedIn);

    /**
     * Gets the logged in user.
     * @return the logged in user
     */
    String getLoggedInUser();

    /**
     * Gets the error message.
     * @return the error message
     */
    String getErrorMessage();

    /**
     * Gets the error text color.
     * @return the error text color
     */
    String getErrorTextColor();

    /**
     * Gets the error text font.
     * @return the error text font
     */
    String getErrorTextFont();

    /**
     * Gets the login start time.
     * @return the login start time
     */
    LocalTime getLoginStartTime();

    /**
     * Checks if is loading icon not displayed.
     * @return true, if is loading icon not displayed
     */
    boolean isLoadingIconNotDisplayed();

    /**
     * verifies the background color for the login screen.
     * @return true, if is login background color valid
     */
    boolean isLoginBackgroundColorValid();

    /**
     * verifies the default focus for the login screen is with user name.
     * @return true, if is default focus with user name
     */
    boolean isDefaultFocusWithUsername();

    /**
     * Checks if is error info position top of login box.
     * @return true, if is error info position top of login box
     */
    boolean isErrorInfoPositionTopOfLoginBox();

    /**
     * Gets the error text background color.
     * @return the error text background color
     */
    String getErrorTextBackgroundColor();

    /**
     * validates the dimension of the text boxes.
     * @return the status of the text box validation
     */
    boolean validateTextBoxDimension();

    /**
     * validates the check box is clicked or not of remember Mail.
     */
    void clickRememberEvent();

    /**
     * verify either hyper links were working or not.
     */
    void verifyLinks();

    /**
     * verify either Need Help link clicked or not.
     */
    void clickNeedHelp();

    /**
     * verify either back button clicked or not.
     */
    void clickBackButton();

    /**
     * Fetch the version name of the APP.
     * @return String.
     */
    String versionName();

    /**
     * Verify either send button is clicked or not.
     */
    void clickSendButton();

    /**
     * Fetch password from mail.
     * @param userName the user name.
     * @return String.
     */
    String fetchPassword(final String userName);    
}
