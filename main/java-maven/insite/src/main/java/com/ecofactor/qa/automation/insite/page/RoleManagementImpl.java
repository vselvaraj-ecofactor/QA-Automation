/*
 * RoleManagementImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.RoleConfig.*;
import static com.ecofactor.qa.automation.insite.config.UserConfig.USER_TABLE;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.RoleConfig;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * <b>Role Management<b>
 * <p>
 * Contains basic features of role management tab such as create a new role, search, edit and
 * update.
 * </p>
 * @author Aximsoft
 */
public class RoleManagementImpl extends InsiteAuthenticatedPageImpl implements RoleManagement {

    /** The role config. */
    @Inject
    private RoleConfig roleConfig;

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The dynamic role name. */
    public String dynamicRoleNames = "TestRole";

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(RoleManagementImpl.class);

    /** The current date. */
    Date currentDate = new Date();

    /** The formatter. */
    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    /** The date time stamp. */
    public String dateTimeStamp = formatter.format(currentDate);

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    @SuppressWarnings("static-access")
    public void loadPage() {

        final String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.ROLE_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            clickRoleManagement();
        }

    }

    /**
     * Creates the new role.
     * @param roleName the role name
     * @param roleDescription the role description
     * @param unAssignedPermission the un assigned permission
     * @param partnerType the partner type
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#createNewRole(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void createNewRole(final String roleName, final String roleDescription,
            final String unAssignedPermission, final int partnerType) {

        DriverConfig.setLogString("Processing a new role creation with required fields.", true);

        WaitUtil.waitUntil(200);

        final WebElement roleManagement = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_ANCHOR, ATTR_TITLE, roleConfig.get(ROLE_MANAGEMENT_VAL));
        roleManagement.click();
        logger.info("check if create new role is displayed.");
        isDisplayedByLinkText(DriverConfig.getDriver(), roleConfig.get(CREATE_NEW_ROLE_VAL),
                MEDIUM_TIMEOUT);
        DriverConfig.setLogString("select create new role link.", true);
        final WebElement createNewRole = retrieveElementByLinkText(DriverConfig.getDriver(),
                roleConfig.get(CREATE_NEW_ROLE_VAL), MEDIUM_TIMEOUT);
        createNewRole.click();
        DriverConfig.setLogString("enter details in create role form.", true);
        final String dynamicRoleName = enterRoleName(roleName);
        selectPartnerType(partnerType);
        enterRoleDescription(roleDescription);
        DriverConfig.setLogString("select permissions for the role.", true);
        sendPermissions(DriverConfig.getDriver(), unAssignedPermission);
        DriverConfig.setLogString("save & verify the role access.", true);
        saveAndVerifyRole(DriverConfig.getDriver(), dynamicRoleName);
    }

    /**
     * Enter role name.
     * @param roleName the role name
     * @return the string
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#enterRoleName()
     */
    @Override
    public String enterRoleName(final String roleName) {

        DriverConfig.setLogString(
                "Send the Role values such as 'Role Name' and 'Role Description'", true);
        isDisplayedById(DriverConfig.getDriver(), roleConfig.get(ROLE_DISPLAY_NAME), MEDIUM_TIMEOUT);
        Date currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        final String dynamicRoleName = roleName + dateTimeStamp;
        DriverConfig.setLogString("Enter role name as " + dynamicRoleName, true);
        DriverConfig.getDriver().findElement(By.id(roleConfig.get(ROLE_DISPLAY_NAME)))
                .sendKeys(dynamicRoleName);
        return dynamicRoleName;
    }

    /**
     * Enter role description.
     * @param roleDescription the role description
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#enterRoleDescription(java.lang.String)
     */
    @Override
    public void enterRoleDescription(final String roleDescription) {

        DriverConfig.setLogString("Enter role description as " + roleDescription, true);
        DriverConfig.getDriver().findElement(By.id(roleConfig.get(ROLE_DESCRIPTION)))
                .sendKeys(roleDescription);
    }

    /**
     * Select partner type.
     * @param partnerType the partner type
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#selectPartnerType(java.lang.String)
     */
    @Override
    public void selectPartnerType(final Integer partnerType) {

        DriverConfig.setLogString("find partner type element and select an item.", true);
        final Select select2 = new Select(DriverConfig.getDriver().findElement(
                By.id(roleConfig.get(PARTNER_TYPE_ID))));
        switch (partnerType) {
        case 1:
            select2.selectByVisibleText(roleConfig.get(PARTNER_SYSTEM_ADMIN));
            DriverConfig.setLogString(
                    "Selected Partner Type :" + roleConfig.get(PARTNER_SYSTEM_ADMIN), true);
            break;
        case 2:
            select2.selectByVisibleText(roleConfig.get(PARTNER_CONSERVATION));
            DriverConfig.setLogString(
                    "Selected Partner Type :" + roleConfig.get(PARTNER_CONSERVATION), true);
            break;
        case 3:
            select2.selectByVisibleText(roleConfig.get(PARTNER_INSTALLATION));
            DriverConfig.setLogString(
                    "Selected Partner Type :" + roleConfig.get(PARTNER_INSTALLATION), true);
            break;
        case 4:
            select2.selectByVisibleText(roleConfig.get(PARTNER_SERVICE));
            DriverConfig.setLogString("Selected Partner Type :" + roleConfig.get(PARTNER_SERVICE),
                    true);
            break;
        case 5:
            select2.selectByVisibleText(roleConfig.get(PARTNER_UTILITY));
            DriverConfig.setLogString("Selected Partner Type :" + roleConfig.get(PARTNER_UTILITY),
                    true);
            break;
        case 6:
            select2.selectByVisibleText(roleConfig.get(PARTNER_CUSTOMER));
            DriverConfig.setLogString("Selected Partner Type :" + roleConfig.get(PARTNER_CUSTOMER),
                    true);
            break;
        default:
            DriverConfig.setLogString("Try to select valid partner type! please.", true);
            break;
        }
    }

    /**
     * Click top first role.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickTopFirstRole()
     */
    @Override
    public void clickTopFirstRole() {

        DriverConfig.setLogString("Click top first Role.", true);
        mediumWait();
        final WebElement userRow = DriverConfig.getDriver()
                .findElements(By.id(roleConfig.get(USER_TABLE))).get(0);
        retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), userRow,
                TAG_ANCHOR, ATTR_HREF, "roledetail.html?", MEDIUM_TIMEOUT).click();
        DriverConfig.setLogString("Record Displayed.", true);
    }

    /**
     * Click permissions tab.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickPermissionsTab()
     */
    @Override
    public void clickPermissionsTab() {

        DriverConfig
                .setLogString(
                        "Click on Permission Tab, and change the unAssignedPermissions to any one of Read, Read/Write or Delete access.",
                        true);
        DriverConfig.getDriver().findElement(By.id("roles")).click();
    }

    /**
     * Verify permissions mode.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#verifyPermissionsMode()
     */
    @Override
    public void verifyPermissionsMode() {

        DriverConfig.setLogString("Check for Permissions Assigned properly.", true);
        mediumWait();

        final List<WebElement> userRow = DriverConfig.getDriver().findElements(
                By.xpath(".//*[@id='user']"));

        for (int i = 1; i <= userRow.size(); i++) {

            userRow.get(0).getText();
            final String permissionValue = userRow.get(i - 1).getText();
            DriverConfig.setLogString("Permission : " + permissionValue, true);

            final WebElement element = DriverConfig.getDriver().findElement(
                    By.xpath(".//*[@id='user'][" + i + "]/td[2]/img"));

            if (element.getAttribute("src").contains(
                    "https://insite-apps-qa.ecofactor.com/images/ok_icon.png")) {

                DriverConfig.setLogString("Enabled for Read Only", true);
            } else {
                DriverConfig.setLogString("Not assigned for Read", true);
            }

            final WebElement element1 = DriverConfig.getDriver().findElement(
                    By.xpath(".//*[@id='user'][" + i + "]/td[3]/img"));

            if (element1.getAttribute("src").contains(
                    "https://insite-apps-qa.ecofactor.com/images/ok_icon.png")) {

                DriverConfig.setLogString("Enabled for Write Only", true);
            } else {
                DriverConfig.setLogString("Not assigned for Write", true);
            }
            final WebElement element2 = DriverConfig.getDriver().findElement(
                    By.xpath(".//*[@id='user'][" + i + "]/td[4]/img"));

            if (element2.getAttribute("src").contains(
                    "https://insite-apps-qa.ecofactor.com/images/ok_icon.png")) {

                DriverConfig.setLogString("Enabled for Delete Only", true);
            } else {
                DriverConfig.setLogString("Not assigned for Delete", true);
            }

        }

    }

    /**
     * Checks if is sub menu displayed.
     * @param subMenu the sub menu
     * @return true, if is sub menu displayed
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#isSubMenuDisplayed(java.lang.String)
     */
    @Override
    public boolean isSubMenuDisplayed(final String subMenu) {

        DriverConfig.setLogString("SubMenu " + subMenu, true);
        boolean isdisplayed = false;
        final List<WebElement> subMenus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='submenu']/a"));
        isdisplayed = checkMenu(subMenu, isdisplayed, subMenus);

        return isdisplayed;
    }

    /**
     * Checks if is menu displayed.
     * @param menu the menu
     * @return true, if is menu displayed
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#isMenuDisplayed(java.lang.String)
     */
    @Override
    public boolean isMenuDisplayed(final String menu) {

        DriverConfig.setLogString("Menu " + menu, true);
        boolean isdisplayed = false;
        final List<WebElement> menus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='menu']/li/a"));
        isdisplayed = checkMenu(menu, isdisplayed, menus);
        return isdisplayed;

    }

    /**
     * Gets the menu.
     * @return the menu
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#getMenu()
     */
    @Override
    public List<WebElement> getMenu() {

        final List<WebElement> menus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='menu']/li/a"));
        if (menus != null) {
            for (final WebElement webElement : menus) {
                DriverConfig.setLogString("Menu :" + webElement.getText(), true);
            }
        }

        return menus;
    }

    /**
     * Gets the sub menu.
     * @return the sub menu
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#getSubMenu()
     */
    @Override
    public List<WebElement> getSubMenu() {

        final List<WebElement> subMenus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='submenu']/a"));
        if (subMenus != null) {
            for (final WebElement webElement : subMenus) {
                DriverConfig.setLogString("SubMenu :" + webElement.getText(), true);
            }
        }
        return subMenus;
    }

    /**
     * Click top first user.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickTopFirstUser()
     */
    @Override
    public void clickTopFirstUser() {

        DriverConfig.setLogString("Click top first user.", true);
        smallWait();
        final List<WebElement> userRow = DriverConfig.getDriver().findElements(By.id("user"));
        for (int i = 0; i < userRow.size(); i++) {

            WebElement subElement = retrieveElementByAttributeValueContainsForSubElement(
                    DriverConfig.getDriver(), userRow.get(i), TAG_ANCHOR, ATTR_HREF, "javascript",
                    MEDIUM_TIMEOUT);
            if (subElement != null && !subElement.getText().isEmpty()) {
                subElement.click();
                break;
            }
        }

        tinyWait();
    }

    /**
     * Change away date value.
     * @param linkTextValue the link text value
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#changeAwayDateValue(java.lang.String)
     */
    @Override
    public void changeAwayDateValue(final String linkTextValue) {

        DriverConfig.setLogString("Change current date to : " + linkTextValue, true);
        (new WebDriverWait(DriverConfig.getDriver(), SHORT_TIMEOUT))
                .until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {

                        final List<WebElement> elementList = DriverConfig.getDriver().findElements(
                                By.linkText(linkTextValue));
                        for (final WebElement webElement : elementList) {
                            if (webElement.isDisplayed()) {
                                webElement.click();
                            }
                        }
                        return true;
                    }
                });
        tinyWait();
        clickDone();
    }

    /**
     * Click date picker.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickDatePicker()
     */
    @Override
    public void clickDatePicker() {

        final WebElement pickerField = DriverConfig.getDriver().findElement(By.id("datetime"));
        pickerField.click();

    }

    /**
     * Click date next day.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickDateNextDay()
     */
    @Override
    public void clickDateNextDay() {

        if (DateUtil.isLastDate()) {
            final WebElement divElement = DriverConfig.getDriver().findElement(
                    By.id("ui-datepicker-div"));
            final WebElement nextElement = retrieveElementByAttributeValueContainsForSubElement(
                    DriverConfig.getDriver(), divElement, TAG_SPAN, ATTR_CLASS,
                    "ui-icon ui-icon-circle-triangle-e", SHORT_TIMEOUT);
            nextElement.click();
            changeAwayDateValue("1");
        } else {
            int current = DateUtil.getCurrentDate();
            changeAwayDateValue(String.valueOf(current + 1));
        }
    }

    /**
     * Populate and create new role.
     * @param roleName the role name
     * @param roleDescription the role description
     * @param unAssignedPermission the un assigned permission
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#populateAndCreateNewRole(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void populateAndCreateNewRole(final String roleName, final String roleDescription,
            final String unAssignedPermission) {

        DriverConfig.setLogString("Processing a new role creation with required fields.", true);

        WaitUtil.waitUntil(200);

        final WebElement roleManagement = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_ANCHOR, ATTR_TITLE, roleConfig.get(ROLE_MANAGEMENT_VAL));
        roleManagement.click();
        logger.info("check if create new role is displayed.");
        isDisplayedByLinkText(DriverConfig.getDriver(), roleConfig.get(CREATE_NEW_ROLE_VAL),
                MEDIUM_TIMEOUT);
        DriverConfig.setLogString("select create new role link.", true);
        final WebElement createNewRole = retrieveElementByLinkText(DriverConfig.getDriver(),
                roleConfig.get(CREATE_NEW_ROLE_VAL), MEDIUM_TIMEOUT);
        createNewRole.click();
        DriverConfig.setLogString("enter details in create role form.", true);
        sendRoleDetails(DriverConfig.getDriver(), roleDescription);
        DriverConfig.setLogString("select permissions for the role.", true);
        sendPermissions(DriverConfig.getDriver(), unAssignedPermission);
        DriverConfig.setLogString("save & verify the role access.", true);
        saveAndVerifyRole(DriverConfig.getDriver(), roleName);
    }

    /**
     * Check menu.
     * @param menu the menu
     * @param isdisplayed the isdisplayed
     * @param menus the menus
     * @return true, if successful
     */
    private boolean checkMenu(final String menu, boolean isdisplayed, final List<WebElement> menus) {

        if (menus != null) {
            for (final WebElement webElement : menus) {
                if (webElement.getText().equalsIgnoreCase(menu) && webElement.isDisplayed()) {

                    isdisplayed = true;
                    break;
                }
            }
        }
        return isdisplayed;
    }

    /**
     * <p>
     * Click on Save button and wait for alert to be popped.The verify the page is redirected
     * properly.
     * </p>
     * @param driver the driver
     * @param roleName the role name
     */
    private void saveAndVerifyRole(final WebDriver driver, final String roleName) {

        DriverConfig
                .setLogString(
                        "Click on save link and verify the alert is popped, finally check the page is re-directed properly.",
                        true);
        final WebElement saveElement = retrieveElementByLinkText(driver, "Save", SHORT_TIMEOUT);
        saveElement.click();

        (new WebDriverWait(driver, LONG_TIMEOUT + LONG_TIMEOUT)).until(ExpectedConditions
                .alertIsPresent());
        DriverConfig.setLogString("click ok on alert message.", true);
        logger.info("click ok on alert message.");
        DriverConfig.setLogString("Alert Message :" + driver.switchTo().alert().getText(), true);
        waitUntil(FIVE_SECS);
        driver.switchTo().alert().accept();

        /*
         * retrieveElementByLinkText(driver, "First", SHORT_TIMEOUT); Assert.assertEquals(true,
         * driver.getCurrentUrl().contains("roles.html"), "Url doesn't contains roles.html");
         */
        smallWait();
        logger.info("find role name element is displayed.");
        DriverConfig.setLogString("find role name element is displayed.", true);
        isDisplayedById(driver, roleConfig.get(ROLE_NAME), SHORT_TIMEOUT);
        DriverConfig.setLogString("Enter role name as " + roleName, true);
        driver.findElement(By.id(roleConfig.get(ROLE_NAME))).sendKeys(roleName);
        DriverConfig.setLogString("check if find button is displayed & click it.", true);
        final WebElement findButtonElement = retrieveElementByAttributeValue(driver, TAG_INPUT,
                ATTR_VALUE, roleConfig.get(FIND_BUTTON));
        findButtonElement.click();
        DriverConfig.setLogString("check if search result is displayed.", true);
        confirmSearchResultValue(driver, roleName);
    }

    /**
     * Permission Enum type for Read, Read/Write and Read/Write/Delete.
     * @author $Author:$
     * @version $Rev:$ $Date:$
     */
    private enum Permission {

        R, RW, RWD;
    }

    /**
     * <p>
     * Verify the Search is populated with the value included in the textField.
     * </p>
     * @param driver the driver
     * @param searchResultValue the search result value
     * @return boolean
     */
    private boolean confirmSearchResultValue(final WebDriver driver, final String searchResultValue) {

        DriverConfig.setLogString("Confirm Serach result as " + searchResultValue, true);
        boolean outcome = false;
        DriverConfig.setLogString("check if multiple results are displayed.", true);
        isMultipleClassNameDisplayed(driver, roleConfig.get(SEARCH_RESULT_CLASS), MEDIUM_TIMEOUT);

        final List<WebElement> resultElements = driver.findElements(By.className(roleConfig
                .get(SEARCH_RESULT_CLASS)));
        DriverConfig.setLogString(
                "check if results displayed are relevent to provided search value.", true);
        for (final WebElement webElement : resultElements) {
            outcome = webElement.getText().contains(searchResultValue) ? true : false;
            DriverConfig.setLogString("Displayed Result." + searchResultValue, true);
            if (outcome) {
                return outcome;
            }
        }
        DriverConfig.setLogString("check if results are displayed.", true);
        Assert.assertEquals(true, resultElements.size() > 0, "Result size is zero");
        return outcome;
    }

    /**
     * <p>
     * Send Role field values
     * </p>
     * .
     * @param driver the driver
     * @param roleDescription the role description
     */
    private void sendRoleDetails(final WebDriver driver, final String roleDescription) {

        DriverConfig.setLogString(
                "Send the Role values such as 'Role Name' and 'Role Description'", true);
        isDisplayedById(driver, roleConfig.get(ROLE_DISPLAY_NAME), MEDIUM_TIMEOUT);
        Date currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        dynamicRoleNames += dateTimeStamp;
        DriverConfig.setLogString("find partner type element and select an item.", true);
        final Select select2 = new Select(
                driver.findElement(By.id(roleConfig.get(PARTNER_TYPE_ID))));
        select2.selectByVisibleText(roleConfig.get(PARTNER_TYPE));
        smallWait();
        DriverConfig.setLogString("Enter role name as " + dynamicRoleNames, true);
        driver.findElement(By.id(roleConfig.get(ROLE_DISPLAY_NAME))).sendKeys(dynamicRoleNames);
        DriverConfig.setLogString("Enter role description as " + roleDescription, true);
        driver.findElement(By.id(roleConfig.get(ROLE_DESCRIPTION))).sendKeys(roleDescription);
    }

    /**
     * <p>
     * Click on Permissions Tab and assign the values according to the parameter.
     * </p>
     * <ol>
     * <li>The below mentioned values is concated to the permission value, to point out in which
     * pane is to be mentioned.</li>
     * <li>R - Read</li>
     * <li>RW- Read and Write</li>
     * <li>RWD - Read, Write and Delete</li>
     * <ol>
     * @param driver the driver
     * @param unAssignedPermission the un assigned permission
     */
    private void sendPermissions(final WebDriver driver, final String unAssignedPermission) {

        clickPermissionsTab();
        isDisplayedById(driver, roleConfig.get(UNASSIGNED_PERMISSIONS), MEDIUM_TIMEOUT);

        final String[] unAssignedPermissionList = unAssignedPermission.split(",");
        for (final String permissionText : unAssignedPermissionList) {

            int lastIndexOfSplit = permissionText.lastIndexOf("-") + 1;

            final String property = permissionText.substring(0, lastIndexOfSplit - 1);
            final String permissionButton = permissionText.substring(lastIndexOfSplit,
                    permissionText.length());

            final Permission permission = Permission.valueOf(permissionButton);
            int selectedPermission = 0;
            switch (permission) {
            case R:
                selectedPermission = 1;
                DriverConfig.setLogString("Read Only Permissions.", true);
                break;
            case RW:
                selectedPermission = 2;
                DriverConfig.setLogString("Read/Write Permissions.", true);
                break;
            case RWD:
                selectedPermission = 3;
                DriverConfig.setLogString("Read/Write/Delete Permissions.", true);
                break;
            default:
                break;
            }
            final Select permissionSelect = new Select(driver.findElement(By.id(roleConfig
                    .get(UNASSIGNED_PERMISSIONS))));
            permissionSelect.selectByVisibleText(property);
            DriverConfig.setLogString("Permissions Granted for :" + property, true);
            logger.info("select required permission.");
            driver.findElement(By.id("add" + selectedPermission)).click();
        }
    }

    /**
     * Check ecp.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#checkEcp()
     */
    @Override
    public boolean checkEcp() {

        DriverConfig.setLogString("Check Ecp.", true);
        boolean isEcp = true;
        List<WebElement> userRow = DriverConfig.getDriver().findElements(By.id("user"));
        for (int i = 0; i < userRow.size(); i++) {
            userRow = DriverConfig.getDriver().findElements(By.id("user"));
            WebElement subElement = retrieveElementByAttributeValueContainsForSubElement(
                    DriverConfig.getDriver(), userRow.get(i), TAG_ANCHOR, ATTR_HREF, "javascript",
                    SHORT_TIMEOUT);
            if (subElement != null && !subElement.getText().isEmpty()) {
                subElement.click();
                tinyWait();
                WebElement ecpText = DriverConfig.getDriver().findElement(By.id("ecpCore"));
                DriverConfig.setLogString("ECP TEXT : " + ecpText.getText(), true);
                if (!ecpText.getText().contains("199")) {
                    isEcp = false;
                    break;
                }
                DriverConfig.getDriver().findElement(By.className("subNavOn")).click();
                tinyWait();
                clickFind();
                tinyWait();
            }
        }

        return isEcp;
    }

    /**
     * Click done.
     */
    @Override
    public void clickDone() {

        DriverConfig.setLogString("Click Done", true);
        final WebElement doneButton = DriverConfig.getDriver().findElement(
                By.xpath("//button[contains(text(),'Done')]"));
        doneButton.click();
    }

    /**
     * Click find.
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickFind()
     */
    @Override
    public void clickFind() {

        DriverConfig.setLogString("Click Find", true);
        WebElement findbuttonElement = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_INPUT, ATTR_VALUE, "find");
        findbuttonElement.click();
    }

    /**
     * Click menu.
     * @param menu the menu
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickMenu(java.lang.String)
     */
    @Override
    public void clickMenu(String menu) {

        DriverConfig.setLogString("Click Menu : " + menu, true);
        final List<WebElement> menus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='menu']/li/a"));
        for (WebElement webElement : menus) {

            if (webElement.getText().equalsIgnoreCase(menu)) {
                webElement.click();
                break;
            }

        }
        tinyWait();
    }

    /**
     * Click sub menu.
     * @param subMenu the sub menu
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickSubMenu(java.lang.String)
     */
    @Override
    public void clickSubMenu(String subMenu) {

        DriverConfig.setLogString("Click SubMenu : " + subMenu, true);
        final List<WebElement> menus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='submenu']/a"));
        for (WebElement webElement : menus) {

            if (webElement.getText().equalsIgnoreCase(subMenu)) {
                webElement.click();
                break;
            }

        }
        tinyWait();

    }

    /**
     * @param fileName
     * @return
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#checkUploadedFileInHistory(java.lang.String)
     */
    @Override
    public boolean checkUploadedFileInHistory(String fileName) {

        DriverConfig.setLogString("Check File : " + fileName, true);
        boolean isdisplayed = false;
        List<WebElement> pgmElements = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='user']/td[2]"));

        for (WebElement webElement : pgmElements) {
            if (webElement.getText().equalsIgnoreCase(fileName)) {
                if (webElement.isDisplayed()) {

                    isdisplayed = true;
                    break;
                }
            }
        }
        return isdisplayed;
    }

    /**
     * @param tstatId
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#setTstatId(java.lang.String)
     */
    @Override
    public void setTstatId(String tstatId) {

        DriverConfig.setLogString("Set Thermostat Id : " + tstatId, true);
        clearAndInput(DriverConfig.getDriver(), By.id("algo-tstat-ids"), tstatId);
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#getResult()
     */
    @Override
    public String getResult() {

        WebElement resultText = retrieveElementById(DriverConfig.getDriver(), "algo-job-output");
        DriverConfig.setLogString("Result : " + resultText.getAttribute(ATTR_VALUE), true);
        return resultText.getAttribute(ATTR_VALUE);
    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickSubscribe()
     */
    @Override
    public void clickSubscribe() {

        DriverConfig.setLogString("Click Subscribe", true);
        WebElement subscribeBtn = DriverConfig.getDriver().findElement(
                By.xpath(".//*[@id='algo-actions-layout-bottom']/input[1]"));
        subscribeBtn.click();
    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#clickUnSubscribe()
     */
    @Override
    public void clickUnSubscribe() {

        DriverConfig.setLogString("Click UnSubscribe", true);
        WebElement unSubscribeBtn = DriverConfig.getDriver().findElement(
                By.xpath(".//*[@id='algo-actions-layout-bottom']/input[2]"));
        unSubscribeBtn.click();
    }

}
