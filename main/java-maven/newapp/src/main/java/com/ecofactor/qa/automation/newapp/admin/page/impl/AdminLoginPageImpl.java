/*
 * AdminLoginPageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page.impl;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.ecofactor.qa.automation.newapp.admin.page.AdminLoginPage;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.google.inject.Inject;

/**
 * The Class AdminLoginPageImpl.
 */
public class AdminLoginPageImpl extends AbstractAdminPageImpl implements AdminLoginPage {

    private static final String USER_NAME_FIELD = "j_username";
    private static final String PASSWORD_FIELD = "j_password";
    private static final String CONTINUE_BTN = "btn_continue";

    private boolean loggedIn;

    @Inject
    protected MobileConfig mobileConfig;

    /**
     * login
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminLoginPage#login()
     */
    @Override
    public void login() {

        loadPage();

        LogUtil.setLogString("Login to Admin Portal Page", true);
        LogUtil.setLogString("Check Username textField is displayed", true);
        clearAndInput(driverManager.getAdminDriver(), By.name(USER_NAME_FIELD), mobileConfig.get(ADMIN_USERNAME));
        LogUtil.setLogString("Enter Username :" + mobileConfig.get(ADMIN_USERNAME), true);

        LogUtil.setLogString("Check Password field is displayed", true);
        clearAndInput(driverManager.getAdminDriver(), By.name(PASSWORD_FIELD), mobileConfig.get(ADMIN_PASSWORD));
        LogUtil.setLogString("Enter Password :" + mobileConfig.get(ADMIN_PASSWORD), true);

        LogUtil.setLogString("Click Continue Button", true);
        isDisplayed(driverManager.getAdminDriver(), By.id(CONTINUE_BTN), TINY_TIMEOUT);
        final WebElement continueBtn = getElement(driverManager.getAdminDriver(), By.id(CONTINUE_BTN), TINY_TIMEOUT);
        continueBtn.click();
    }

    /**
     * clean up
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        LogUtil.setLogString("Cleanup in Admin Login Page.", true);
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        LogUtil.setLogString("Verify the Admin Login Page is loaded", true);
        return isDisplayed(driverManager.getAdminDriver(), By.id(CONTINUE_BTN), MEDIUM_TIMEOUT);
    }

    /**
     * Checks if is logged in.
     * @return true, if is logged in
     */
    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the logged in.
     * @param loggedIn the new logged in
     */
    @Override
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * load page
     */
    private void loadPage() {

        final String url = mobileConfig.get(ADMIN_URL);
        LogUtil.setLogString("Load admin page url: " + url, true);
        driverManager.getAdminDriver().navigate().to(url);

    }

}
