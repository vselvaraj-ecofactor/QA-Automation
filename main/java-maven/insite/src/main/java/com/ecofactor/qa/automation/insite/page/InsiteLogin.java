/*
 * InsiteLogin.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface InsiteLogin.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface InsiteLogin extends InsitePage {

    /**
     * Load page without assertion.
     */
    public void loadPageWithoutAssertion();

    /**
     * Load page.
     */
    public void loadPage();

    /**
     * Login.
     * @param userName the user name
     * @param password the password
     */
    public void login(final String userName, final String password);

    /**
     * Logout.
     */
    public void logout();

    /**
     * Verify invalid login.
     */
    public void verifyInvalidLogin();

    /**
     * Verify login.
     * @param userName the user name
     */
    public void verifyLogin(final String userName);

    /**
     * Verify login without assertion.
     * @param userName the user name
     */
    public void verifyLoginWithoutAssertion(final String userName);

    /**
     * Do rest password.
     * @param password the password
     */
    public void doRestPassword(final String password);

    /**
     * Change new password.
     * @param newPassword the new password
     */
    public void changeNewPassword(final String newPassword);


    /**
     * Login with new password.
     * @param userName the user name
     * @param tempPassword the temp password
     * @param newPassword the new password
     */
    public void loginWithNewPassword(final String userName, final String tempPassword, final String newPassword);

    /**
     * Verify home page identifier.
     */
    public void verifyHomePageIdentifier();

    /**
     * Verify login page identifier.
     */
    public void verifyLoginPageIdentifier();

    /**
     * Gets the login user.
     * @return the login user
     */
    public String getLoginUser();

    /**
     * Clear user.
     */
    public void clearUser();

    /**
     * Checks if is authenticated user.
     * @return true, if is authenticated user
     */
    public boolean isAuthenticatedUser();

    /**
     * Checks if is authenticated user.
     * @param userName the user name
     * @return true, if is authenticated user
     */
    public boolean isAuthenticatedUser(String userName);

    /**
     * Test reset pswd cancel link.
     */
    public void testResetPswdCancelLink();

    /**
     * Click reset pswd with blank fields.
     */
    public void clickResetPswdWithBlankFields();

    /**
     * Click reset pswd with non matching username.
     */
    public void clickResetPswdWithNonMatchingUsername();

    /**
     * Test about ecofactor link.
     */
    public void testAboutEcofactorLink();

    /**
     * End.
     */
    public void end();

}
