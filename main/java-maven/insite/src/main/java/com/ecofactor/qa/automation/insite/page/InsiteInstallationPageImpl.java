/*
 * InsiteInstallationPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.UserConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.UserConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class InsiteInstallationPageImpl.
 */
public class InsiteInstallationPageImpl extends InsiteAuthenticatedPageImpl implements
        InsiteInstallationPage {

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The user config. */
    @Inject
    private UserConfig userConfig;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(InsiteAuthenticatedPageImpl.class);

    /** The tst name. */
    private static String TSTNAME = "ef_installer_title";

    /** The menu name. */
    private static String MENU_NAME = ".accounts";

    /** The user details. */
    private static String USER_DETAILS = ".//*[@id='user']/td[2]/a";

    /** The submenu. */
    private static String SUBMENU = "submenu";

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsitePage#loadPage()
     */
    @Override
    public void loadPage() {

        // Auto-generated method stub

    }

    /**
     * Search by street address.
     * @param streetAddress the street address
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#searchByStreetAddress(java.lang.String)
     */
    @Override
    public void searchByStreetAddress(final String streetAddress) {

        DriverConfig.setLogString("Find Street Address : " + streetAddress, true);
        final WebElement address = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(ADDRESS)));
        address.sendKeys(streetAddress);
    }

    /**
     * Click find.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickFind()
     */
    @Override
    public void clickFind() {

        DriverConfig.setLogString("Click find", true);
        final WebElement address = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(FIND_ADDRESS)));
        address.click();
    }

    /**
     * Click installation link.
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#clickInstallationLink()
     */
    @Override
    public void clickInstallationTab() {

        DriverConfig.setLogString("Click Installation tab", true);
        selectPage(INSTALLATION);
        smallWait();
    }

    /**
     * Click pre config tab.
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#clickPreConfigTab()
     */
    @Override
    public void clickPreConfigTab() {

        DriverConfig.setLogString("Click PreConfiguration tab", true);
        final WebElement subMenu = DriverConfig.getDriver().findElement(By.id(SUBMENU));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                TAG_ANCHOR, insiteConfig.get(PRE_CONFIGURATION), SHORT_TIMEOUT);
        smallWait();
        rolesLink.click();
    }

    /**
     * Click scheduling link.
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#clickScheduling Link()
     */
    @Override
    public void clickSchedulingPage() {

        DriverConfig.setLogString("Click Scheduling Page", true);
        final WebElement subMenu = DriverConfig.getDriver().findElement(By.id(SUBMENU));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                TAG_ANCHOR, insiteConfig.get(SCHEDULING), SHORT_TIMEOUT);
        smallWait();
        rolesLink.click();
    }

    /**
     * Click top first user.
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#clickTopFirstUser()
     */
    @Override
    public void clickTopFirstUser() {

        DriverConfig.setLogString("Click top first user.", true);
        mediumWait();
        final WebElement theRecord = DriverConfig.getDriver().findElement(
                By.cssSelector(".userinfo>a"));
        theRecord.click();
    }

    /**
     * Checks if is tst name page displayed.
     * @return true, if is tst name page displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#isTstNamePageDisplayed()
     */
    @Override
    public boolean isTstNamePageDisplayed() {

        DriverConfig.setLogString("Verify Thermostat Name Page Displayed.", true);
        mediumWait();
        return isDisplayedByClassName(DriverConfig.getDriver(), TSTNAME, SHORT_TIMEOUT);
    }

    /**
     * Checks if is installation tab displayed.
     * @return true, if is installation tab displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#isInstallationTabDisplayed()
     */
    @Override
    public boolean isInstallationTabDisplayed() {

        DriverConfig.setLogString("Verify installation Page Displayed.", true);
        mediumWait();
        final WebElement installLink = DriverConfig.getDriver().findElement(
                By.cssSelector(MENU_NAME));
        return installLink.getText().equalsIgnoreCase(INSTALLATION);
    }

    /**
     * Checks if is pre config tab displayed.
     * @return true, if is pre config tab displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#isPreConfigTabDisplayed()
     */
    @Override
    public boolean isPreConfigTabDisplayed() {

        DriverConfig.setLogString("Verify PreConfiguration Page Displayed.", true);
        mediumWait();
        final WebElement subMenu = DriverConfig.getDriver().findElement(By.id(SUBMENU));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                TAG_ANCHOR, insiteConfig.get(PRE_CONFIGURATION), SHORT_TIMEOUT);
        return rolesLink.isDisplayed();
    }

    /**
     * Checks if is scheduling page displayed.
     * @return true, if is scheduling page displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#isSchedulingPageDisplayed()
     */
    @Override
    public boolean isSchedulingPageDisplayed() {

        DriverConfig.setLogString("Verify Scheduling Page Displayed.", true);
        mediumWait();
        final WebElement subMenu = DriverConfig.getDriver().findElement(By.id(SUBMENU));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                TAG_ANCHOR, insiteConfig.get(SCHEDULING), SHORT_TIMEOUT);
        return rolesLink.isDisplayed();
    }

    /**
     * Checks if is on boarding tab displayed.
     * @return true, if is on boarding tab displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#isOnBoardingTabDisplayed()
     */
    @Override
    public boolean isOnBoardingTabDisplayed() {

        DriverConfig.setLogString("Verify OnBoarding Page Displayed.", true);
        mediumWait();
        final WebElement subMenu = DriverConfig.getDriver().findElement(By.id(SUBMENU));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                TAG_ANCHOR, insiteConfig.get(ON_BOARDING), SHORT_TIMEOUT);
        return rolesLink.isDisplayed();
    }

    /**
     * Checks if is searched user displayed.
     * @param streetAddress the street address
     * @return true, if is searched user displayed
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#isSearchedUserDisplayed(java.lang.String)
     */
    @Override
    public boolean isSearchedUserDisplayed(final String streetAddress) {

        DriverConfig.setLogString("Verify searched user Displayed.", true);
        mediumWait();
        DriverConfig.setLogString("Searched User :" + streetAddress, true);
        return isDisplayedByXpath(DriverConfig.getDriver(), USER_DETAILS, MEDIUM_TIMEOUT);
    }

    /**
     * Click user management page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#selectUserManagementPage()
     */
    @Override
    public void selectUserManagementPage() {

        DriverConfig.setLogString("select user management page.", true);
        selectPage(ADMIN);
        smallWait();
        final WebElement subMenu = DriverConfig.getDriver().findElement(
                By.id(insiteConfig.get(SUB_MENU)));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subMenu,
                TAG_ANCHOR, insiteConfig.get(USER_MNGMNT), SHORT_TIMEOUT);
        rolesLink.click();
    }

    /**
     * Checks if is user management page displayed.
     * @return true, if is user management page displayed
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isUserManagementPageDisplayed()
     */
    @Override
    public boolean isUserManagementPageDisplayed() {

        DriverConfig.setLogString("verify User Management Page displayed.", true);
        final WebElement subElement = DriverConfig.getDriver().findElement(
                By.id(insiteConfig.get(SUB_MENU)));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subElement,
                TAG_ANCHOR, insiteConfig.get(USER_MNGMNT), SHORT_TIMEOUT);
        return rolesLink.isDisplayed();
    }

    /**
     * Checks if is role management page displayed.
     * @return true, if is role management page displayed
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isRoleManagementPageDisplayed()
     */
    @Override
    public boolean isRoleManagementPageDisplayed() {

        DriverConfig.setLogString("verify RoleManagement Page displayed.", true);
        final WebElement subElement = DriverConfig.getDriver().findElement(
                By.id(insiteConfig.get(SUB_MENU)));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subElement,
                TAG_ANCHOR, insiteConfig.get(ROLE_MNGMNT), SHORT_TIMEOUT);
        return rolesLink.isDisplayed();
    }

    /**
     * Click role management page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteInstallationPage#selectRoleManagementPage()
     */
    @Override
    public void selectRoleManagementPage() {

        DriverConfig.setLogString("select role management page.", true);
        selectPage(ADMIN);
        smallWait();

        final WebElement subElement = DriverConfig.getDriver().findElement(
                By.id(insiteConfig.get(SUB_MENU)));
        WebElement rolesLink = retrieveSubElementByTagText(DriverConfig.getDriver(), subElement,
                TAG_ANCHOR, insiteConfig.get(ROLE_MNGMNT), SHORT_TIMEOUT);
        rolesLink.click();
        smallWait();
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

}
