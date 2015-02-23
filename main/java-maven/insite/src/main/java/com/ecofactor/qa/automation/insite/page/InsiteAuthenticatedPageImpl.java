/*
 * InsiteAuthenticatedPageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class InsiteAuthenticatedPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class InsiteAuthenticatedPageImpl extends InsitePageImpl implements
        InsiteAuthenticatedPage {

    @Inject
    protected InsiteConfig insiteConfig;
    @Inject
    protected InsiteLogin insiteLogin;
    @Inject
    protected InsiteLoginConfig loginConfig;

    protected boolean login = false;
    protected String loginUser = null;
    private WebElement subMenu = null;
    private static Logger logger = LoggerFactory.getLogger(InsiteAuthenticatedPageImpl.class);

    /**
     * Logout.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#logout()
     */
    @Override
    public void logout() {

        insiteLogin.logout();
        insiteLogin.verifyLoginPageIdentifier();
        insiteLogin.clearUser();
    }

    /**
     * Load page.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPageImpl#loadPage(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void loadPage(String userId, String password) {

        DriverConfig.setLogString(
                "Load the required insite page through valid user login credentials.", true);
        if (DriverConfig.getDriver() == null) {
            getDriver();
        }
        if (insiteLogin.isAuthenticatedUser() && !insiteLogin.isAuthenticatedUser(userId)) {
            logger.info("Authenticated User is different, so logout");
            logout();
        }
        if (!insiteLogin.isAuthenticatedUser()) {
            logger.info("No Authenticated User, so load login page.");
            insiteLogin.loadPage();
            insiteLogin.login(userId, password);
            insiteLogin.verifyLogin(userId);
        }
        loadPage();
    }

    /**
     * Select page.
     * @param pageName the page name
     */
    private void selectPage(String pageName) {

        logger.info("select page from the menu.");
        List<WebElement> menuItems = DriverConfig.getDriver().findElements(
                By.id(insiteConfig.get(MENU_ID)));
        if (menuItems != null && menuItems.size() > 0) {
            WebElement linkElements = menuItems.get(0);
            List<WebElement> linkElement = linkElements.findElements(By.tagName(TAG_ANCHOR));
            if (linkElement != null && linkElement.size() > 0) {
                for (WebElement element : linkElement) {
                    smallWait();
                    if (element.getText().equalsIgnoreCase(insiteConfig.get(pageName))) {
                        logger.info("click the menu item");
                        element.click();
                        break;
                    }
                }
            }
        }

    }

    /**
     * Checks if is logo displayed.
     * @return true, if is logo displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#isLogoDisplayed()
     */
    @Override
    public boolean isLogoDisplayed() {

        DriverConfig.setLogString("check if logo is displayed.", true);
        WebElement logoElement = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_ANCHOR, ATTR_TITLE, "EcoFactor");
        Assert.assertTrue(logoElement.isDisplayed(), "logo is not available");
        return false;
    }

    /**
     * Verify welcome text.
     * @param userId the user id
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#verifyWelcomeText(java.lang.String)
     */
    @Override
    public void verifyWelcomeText(String userId) {

        DriverConfig.setLogString("check if welcome text displayed.", true);
        WebElement userInfo = DriverConfig.getDriver().findElement(
                By.className(insiteConfig.get(EF_SMALL_LABEL)));
        Assert.assertTrue(userInfo.getText().equalsIgnoreCase(userId),
                "Welcome text doesn't contain the user");

    }

    /**
     * Click admin.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickAdmin()
     */
    @Override
    public void clickUserManagement() {

        DriverConfig.setLogString("select user management page.", true);
        selectPage(ADMIN);
        smallWait();
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(USER_MNGMNT), SHORT_TIMEOUT);
        rolesLink.click();
        smallWait();

        logger.info("check if user management page is displayed.");
        String url = insiteConfig.get(ADMIN_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if user management page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");

    }

    /**
     * Click role management.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickRoleManagement()
     */
    @Override
    public void clickRoleManagement() {

        DriverConfig.setLogString("select role management page.", true);
        if (!DriverConfig.getDriver().getCurrentUrl().contains(insiteConfig.get(ADMIN_PAGE)))
            selectPage(ADMIN);
        smallWait();

        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(ROLE_MNGMNT), SHORT_TIMEOUT);
        rolesLink.click();
        smallWait();

        logger.info("check if role management page is displayed.");
        String url = insiteConfig.get(ROLE_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if role management page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click support.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickSupport()
     */
    @Override
    public void clickSupport() {

        DriverConfig.setLogString("select support page.", true);
        selectPage(SUPPORT);
        smallWait();
        DriverConfig.getDriver().switchTo().defaultContent();

        String url = insiteConfig.get(ACCOUNT_URL).substring(0,
                insiteConfig.get(ACCOUNT_URL).lastIndexOf("?") + 1);
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if support page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click installation.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickInstallation()
     */
    @Override
    public void clickInstallation() {

        DriverConfig.setLogString("select installation page.", true);

        selectPage(INSTALLATION);
        smallWait();
        DriverConfig.getDriver().switchTo().defaultContent();
        logger.info("check if installation page is displayed.", true);
        String url = insiteConfig.get(INSTALLATION_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if installation page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click onsite installation.
     */
    public void clickOnsiteInstallation() {

        DriverConfig.setLogString("select onsite installation page.", true);
        if (!DriverConfig.getDriver().getCurrentUrl().contains(insiteConfig.get(INSTALLATION_PAGE)))
            selectPage(INSTALLATION);
        smallWait();
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(ONSITE_INSTALLATION), SHORT_TIMEOUT);
        rolesLink.click();

        smallWait();

        logger.info("check if onsite installation page is displayed.");
        String url = insiteConfig.get(INSTALLATION_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if onsite installation page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click scheduling.
     */
    public void clickScheduling() {

        DriverConfig.setLogString("select schedule page.", true);
        if (!DriverConfig.getDriver().getCurrentUrl().contains(insiteConfig.get(INSTALLATION_PAGE)))
            selectPage(INSTALLATION);
        smallWait();
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(SCHEDULING), SHORT_TIMEOUT);
        rolesLink.click();

        smallWait();

        logger.info("check if schedule page is displayed.", true);
        String url = insiteConfig.get(SCHEDULE_URL).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if scheduling page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click pre configuration.
     */
    public void clickPreConfiguration() {

        DriverConfig.setLogString(
                "select pre configuration page." + insiteConfig.get(PRECONFIG_URL), true);
        if (!DriverConfig.getDriver().getCurrentUrl().contains(insiteConfig.get(INSTALLATION_PAGE)))
            selectPage(INSTALLATION);

        smallWait();
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(PRE_CONFIGURATION), SHORT_TIMEOUT);
        rolesLink.click();

        smallWait();

        logger.info("check if preconfig page is displayed.");
        String url = insiteConfig.get(PRECONFIG_URL).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if preconfig page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click demand side management.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickDemandSideManagement()
     */
    @Override
    public void clickDemandSideManagement() {

        DriverConfig.setLogString("select demand side management page.", true);

        selectPage(DEMAND_SIDE_MGMNT);
        smallWait();
        DriverConfig.getDriver().switchTo().defaultContent();

        logger.info("check if demand side page is displayed.");
        String url = insiteConfig.get(DEMANDSIDE_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if demand side page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click on board.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickOnBoard()
     */
    @Override
    public void clickOnBoard() {

        DriverConfig.setLogString("select On boarding page." + ON_BOARD_PAGE, true);
        selectPage(ON_BOARDING);
        smallWait();

        logger.info("check if on baording page is displayed.");
        String url = insiteConfig.get(ON_BOARD_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if on baording page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    @Override
    public void clickUploadOneUser() {

        DriverConfig.setLogString("select upload one user page.", true);
        selectPage(ON_BOARDING);
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement errorToBeFixedLink = retrieveSubElementByTagText(DriverConfig.getDriver(),
                subMenu, "a", insiteConfig.get(UPLOAD_ONE_USER), SHORT_TIMEOUT);
        errorToBeFixedLink.click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        logger.info("check if on baording- upload one user page is displayed.");
        String url = insiteConfig.get(UPLOAD_ONE_USER).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if upload one user page is selected." + url);
        // Assert.assertTrue(driver.getCurrentUrl().contains(url), "Url is different");
    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickBulkUpload()
     */
    @Override
    public void clickBulkUpload() {

        DriverConfig.setLogString("select On boarding - BULK UPLOAD page.", true);
        selectPage(ON_BOARDING);
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        logger.info("On boarding selected.");
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));
        WebElement bulkUploadLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                "a", insiteConfig.get(BULK_UPLOADS), SHORT_TIMEOUT);
        bulkUploadLink.click();

        logger.info("check if on baording- BULK UPLOAD page is displayed.");
        String url = insiteConfig.get(ON_BOARD_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if on baording- BULK UPLOAD page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickHistory()
     */
    @Override
    public void clickHistory() {

        DriverConfig.setLogString("select On boarding - history page.", true);
        selectPage(ON_BOARDING);
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement historyLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                "a", insiteConfig.get(HISTORY), SHORT_TIMEOUT);
        historyLink.click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        logger.info("check if on baording- history is displayed.");
        String url = insiteConfig.get(HISTORY_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if baording- history page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    @Override
    public void clickErrorsToBeFixed() {

        DriverConfig.setLogString("select On boarding - errors to be fixed page.", true);
        selectPage(ON_BOARDING);
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement errorToBeFixedLink = retrieveSubElementByTagText(DriverConfig.getDriver(),
                subMenu, "a", insiteConfig.get(ERRORS_TO_BE_FIXED), SHORT_TIMEOUT);
        errorToBeFixedLink.click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        logger.info("check if on baording- Errors to be fixed page is displayed.");
        String url = insiteConfig.get(ERRORS_TO_BE_FIXED_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if on baording- Errors to be fixed page is selected." + url);
        Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
    }

    /**
     * Click partner management.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#clickAdmin()
     */
    @Override
    public void clickPartnerManagement() {

        DriverConfig.setLogString("select partner management page.", true);
        selectPage(ADMIN);
        smallWait();
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(PARTNER_MNGMNT), SHORT_TIMEOUT);
        rolesLink.click();
        smallWait();

        logger.info("check if partner management page is displayed.");
        String url = insiteConfig.get(ADMIN_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if partner management page is selected." + url);
       /* Assert.assertTrue(DriverConfig.getDriver().getCurrentUrl().contains(url),
                "Url is different");
*/
    }
    
    @Override
    public void clickECPCoreManagement(){
        
        DriverConfig.setLogString("select ECP Core Management page.", true);
        selectPage(ADMIN);
        smallWait();
        subMenu = DriverConfig.getDriver().findElement(By.id("submenu"));

        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu, "a",
                insiteConfig.get(ECP_CORE_MNGMNT), SHORT_TIMEOUT);
        rolesLink.click();
        smallWait();

        logger.info("check if ecp core management page is displayed.");
        String url = insiteConfig.get(ECP_CORE_PAGE).split("\\?")[0];
        logger.info(DriverConfig.getDriver().getCurrentUrl()
                + " check if ecpcore management page is selected." + url);
    }

}
