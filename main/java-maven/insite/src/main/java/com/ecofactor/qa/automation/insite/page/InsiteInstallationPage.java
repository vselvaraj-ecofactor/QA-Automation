/*
 * InsiteInstallationPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface InsiteInstallationPage.
 */
public interface InsiteInstallationPage extends InsiteAuthenticatedPage {

    /**
     * Search by street address.
     * @param streetAddress the street address
     */
    public void searchByStreetAddress(final String streetAddress);

    /**
     * Click find.
     */
    public void clickFind();

    /**
     * Click installation link.
     */
    public void clickInstallationTab();

    /**
     * Click pre config tab.
     */
    public void clickPreConfigTab();

    /**
     * Click top first user.
     */
    public void clickTopFirstUser();

    /**
     * Checks if is tst name page displayed.
     * @return true, if is tst name page displayed
     */
    public boolean isTstNamePageDisplayed();

    /**
     * Checks if is installation tab displayed.
     * @return true, if is installation tab displayed
     */
    public boolean isInstallationTabDisplayed();

    /**
     * Checks if is pre config tab displayed.
     * @return true, if is pre config tab displayed
     */
    public boolean isPreConfigTabDisplayed();

    /**
     * Checks if is scheduling tab displayed.
     * @return true, if is scheduling tab displayed
     */
    public boolean isSchedulingPageDisplayed();

    /**
     * Click scheduling link.
     */
    public void clickSchedulingPage();

    /**
     * Checks if is on boarding tab displayed.
     * @return true, if is on boarding tab displayed
     */
    public boolean isOnBoardingTabDisplayed();

    /**
     * Checks if is searched user displayed.
     * @param streetAddress the street address
     * @return true, if is searched user displayed
     */
    public boolean isSearchedUserDisplayed(final String streetAddress);

    /**
     * Click user management page.
     */
    public void selectUserManagementPage();

    /**
     * Click role management page.
     */
    public void selectRoleManagementPage();

    /**
     * Checks if is user management page displayed.
     * @return true, if is user management page displayed
     */
    public boolean isUserManagementPageDisplayed();

    /**
     * Checks if is role management page displayed.
     * @return true, if is role management page displayed
     */
    public boolean isRoleManagementPageDisplayed();

}
