/*
 * InsiteAuthenticatedPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface InsiteAuthenticatedPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface InsiteAuthenticatedPage extends InsitePage {

    /**
     * Load page.
     * @param userName the user name
     * @param password the password
     */
    public void loadPage(final String userName, final String password);

    /**
     * Load page.
     */
    public void loadPage();

    /**
     * Checks if is logo displayed.
     * @return true, if is logo displayed
     */
    public boolean isLogoDisplayed();

    /**
     * Verify welcome text.
     * @param userId the user id
     */
    public abstract void verifyWelcomeText(final String userId);

    /**
     * Click User Management.
     */

    public void clickUserManagement();

    /**
     * Click role management.
     */
    public void clickRoleManagement();

    /**
     * Click support.
     */
    public void clickSupport();

    /**
     * Click installation.
     */
    public void clickInstallation();

    /**
     * Click scheduling.
     */
    public void clickScheduling();

    /**
     * Click pre configuration.
     */
    public void clickPreConfiguration();

    /**
     * Click demand side management.
     */
    public void clickDemandSideManagement();

    /**
     * Click on board.
     */
    public void clickOnBoard();

    /**
     * Click bulk upload.
     */
    public void clickBulkUpload();

    /**
     * Click history.
     */
    public void clickHistory();

    /**
     * Click errors to be fixed.
     */
    public void clickErrorsToBeFixed();

    /**
     * Logout.
     */
    public abstract void logout();

    /**
     * Click upload one user.
     */
    public void clickUploadOneUser();

    /**
     * End.
     */
    public void end();

    /**
     * Click partner management.
     */
    public void clickPartnerManagement();

    /**
     * Click ecp core management.
     */
    public void clickECPCoreManagement();

}
