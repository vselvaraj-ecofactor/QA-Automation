/*
 * PartnerManagementImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.PartnerConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.PartnerConfig;
import com.ecofactor.qa.automation.insite.config.UserConfig;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class PartnerManagementImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PartnerManagementImpl extends InsiteAuthenticatedPageImpl implements PartnerManagement {

    /** The partner config. */
    @Inject
    private PartnerConfig partnerConfig;

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The user config. */
    @Inject
    private UserConfig userConfig;

    /** The menu tab id. */
    private static String MENU_ACCOUNT_TAB = "acct";

    /** The menu contact tab. */
    private static String MENU_CONTACT_TAB = "contact";

    /** The menu role tab. */
    private static String MENU_ROLE_TAB = "roles";

    /** The add available role. */
    private static String ADD_AVAILABLE_ROLE = "addPartnerType";

    /** The remove assigned role. */
    private static String REMOVE_ASSIGNED_ROLE = "remove";

    /** The active. */
    private static String ACTIVE = "active";

    /** The result message. */
    private static String RESULT_MESSAGE = "No results found.";

    /** The tag strong. */
    private static String TAG_STRONG = "strong";

    /** The partner class. */
    @SuppressWarnings("unused")
    private static String PARTNER_CLASS = "userinfo bgnone";

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(PartnerManagementImpl.class);

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsitePage#loadPage()
     */
    @SuppressWarnings("static-access")
    @Override
    public void loadPage() {

        String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.ADMIN_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            logger.info("select partner management. ");
            clickPartnerManagement();
        }
    }

    /**
     * Click new partner.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickNewPartner()
     */
    @Override
    public void clickNewPartner() {

        LogUtil.setLogString("Click Create New Partner ", true);
        WaitUtil.smallWait();
        final WebElement createNewUserLink = retrieveElementByLinkText(DriverConfig.getDriver(),
                partnerConfig.get(CREATE_NEW_PARTNER), MEDIUM_TIMEOUT);
        createNewUserLink.click();
    }

    /**
     * Click cancel.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickCancel()
     */
    @Override
    public void clickCancel() {

        LogUtil.setLogString("Click Cancel in partner page ", true);
        WaitUtil.smallWait();
        final WebElement cancelLink = retrieveElementByTagText(DriverConfig.getDriver(),
                TAG_ANCHOR, partnerConfig.get(CANCEL));
        cancelLink.click();
    }

    /**
     * Click Reset.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickReset()
     */
    @Override
    public void clickReset() {

        LogUtil.setLogString("Click Reset in partner page ", true);
        WaitUtil.smallWait();
        final WebElement resetLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                partnerConfig.get(RESET));
        resetLink.click();
    }

    /**
     * Fill partner details.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#fillPartnerDetails(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void fillPartnerDetails(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        populatePartnerDetails(driver, partnerName, emailId, activeUser, streetAddress1,
                streetAddress2, city, state, zip, country, primaryContactName, phoneNumber,
                availablePartnerType);

    }

    /**
     * Populate partner details.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#populatePartnerDetails(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void populatePartnerDetails(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        fillContactInformation(driver, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Click save link.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickSave()
     */
    @Override
    public void clickSave() {

        LogUtil.setLogString("Click save link.", true);
        WebElement saveLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "Save");
        saveLink.click();
    }

    /**
     * Check alert for partner name.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#checkForPartnerNameAlert()
     */
    @Override
    public void checkForPartnerNameAlert() {

        WaitUtil.tinyWait();
        LogUtil.setLogString("Check for partner Name Alert.", true);
        final String partnerAlert = DriverConfig.getDriver().switchTo().alert().getText();
        Assert.assertTrue("ALert Message is not displayed", (DriverConfig.getDriver().switchTo()
                .alert().getText().equalsIgnoreCase(partnerAlert)));
        LogUtil.setLogString("Alert Message for partner Name :" + partnerAlert, true);
        DriverConfig.getDriver().switchTo().alert().accept();
    }

    /**
     * Check alert for partner type.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#checkForPartnerTypeAlert()
     */
    @Override
    public void checkForPartnerTypeAlert() {

        WaitUtil.tinyWait();
        LogUtil.setLogString("Check for partner type Alert.", true);
        final String partnerAlert = DriverConfig.getDriver().switchTo().alert().getText();
        Assert.assertTrue("ALert Message is not displayed", (DriverConfig.getDriver().switchTo()
                .alert().getText().equalsIgnoreCase(partnerAlert)));
        LogUtil.setLogString("Alert Message for Partner Type : " + partnerAlert, true);
        DriverConfig.getDriver().switchTo().alert().accept();

    }

    /**
     * check alert for name and type.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#checkForPartnerNameAndTypeAlert()
     */
    @Override
    public void checkForPartnerNameAndTypeAlert() {

        WaitUtil.tinyWait();
        LogUtil.setLogString("Check for partner Name and Type Alert.", true);
        final String partnerAlert = DriverConfig.getDriver().switchTo().alert().getText();
        Assert.assertTrue("ALert Message is not displayed", (DriverConfig.getDriver().switchTo()
                .alert().getText().equalsIgnoreCase(partnerAlert)));
        /*
         * DriverConfig .getDriver() .switchTo() .alert() .getText() .equalsIgnoreCase(
         * "Partner Name cannot be null %s%n%s Atleast one Partner type has to be assigned to the Partner"
         * );
         */
        LogUtil.setLogString("Alert Message for Partner Name and Type : " + partnerAlert, true);
        DriverConfig.getDriver().switchTo().alert().accept();
    }

    /**
     * Check for partner account creation saved.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#checkForPartnerCreationAlert()
     */
    @Override
    public void checkForPartnerCreationAlert() {

        WaitUtil.tinyWait();
        LogUtil.setLogString("Check for partner creation saved.", true);
        final String creationAlert = DriverConfig.getDriver().switchTo().alert().getText();
        LogUtil.setLogString("Alert Text : "+creationAlert, true);
        DriverConfig.getDriver().switchTo().alert().accept();
        Assert.assertTrue("Alert Message is not displayed",
                creationAlert.equalsIgnoreCase("Partner Created Successfully"));
    }

    /**
     * Fill details without partner name.
     * @param driver the driver
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#fillDetailsWithoutPartnerName(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void fillDetailsWithoutPartnerName(final WebDriver driver, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        clickAccountTab();
        enterPartnerEmailId(emailId);
        enterPartnerStatus(activeUser);
        fillContactInformation(driver, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);

    }

    /**
     * Fill details without partner type.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#fillDetailsWithoutPartnerType(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void fillDetailsWithoutPartnerType(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber) {

        fillAccountDetails(partnerName, emailId, activeUser);
        fillContactInformation(driver, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber);
    }

    /**
     * Fill details without partner name and type.
     * @param driver the driver
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#fillDetailsWithoutPartnerNameType(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void fillDetailsWithoutPartnerNameType(final WebDriver driver, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber) {

        clickAccountTab();
        enterPartnerEmailId(emailId);
        enterPartnerStatus(activeUser);
        fillContactInformation(driver, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber);
    }

    /**
     * Click Primary Account tab.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickAccountTab()
     */
    @Override
    public void clickAccountTab() {

        LogUtil.setLogString("Click Primary Account Tab ", true);
        LogUtil.setLogString(
                "Click on Account Panel and enter account details to the required fields.", true);
        LogUtil.setLogString("check accout details tab is displayed.", true);
        isDisplayedById(DriverConfig.getDriver(), MENU_ACCOUNT_TAB, SHORT_TIMEOUT);
        WaitUtil.smallWait();
        final WebElement contactTab = DriverConfig.getDriver().findElement(By.id(MENU_ACCOUNT_TAB));
        contactTab.click();

    }

    /**
     * Click Contact Information Tab.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickContactInformation()
     */
    @Override
    public void clickContactInformation() {

        LogUtil.setLogString("Click Contact Information Tab ", true);
        LogUtil.setLogString(
                "Click on Account Panel and enter Contact information details to the Contact Information Tab.",
                true);
        WaitUtil.smallWait();
        final WebElement contactTab = DriverConfig.getDriver().findElement(By.id(MENU_CONTACT_TAB));
        contactTab.click();
    }

    /**
     * Click partner Type Tab.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickPartnerTypes()
     */
    @Override
    public void clickPartnerTypes() {

        LogUtil.setLogString("Click Partner types Tab ", true);
        LogUtil.setLogString(
                "Click on Role tab and Send the Role details to the selection, and add it to the pane.",
                true);
        isDisplayedById(DriverConfig.getDriver(), MENU_ROLE_TAB, SHORT_TIMEOUT);
        WaitUtil.smallWait();
        final WebElement roleTab = DriverConfig.getDriver().findElement(By.id(MENU_ROLE_TAB));
        roleTab.click();
    }

    /**
     * Check for contact information tab displayed.
     * @return true, if contact tab displayed,
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isContactTabDisplayed()
     */
    @Override
    public boolean isContactTabDisplayed() {

        LogUtil.setLogString("Check Contact Information tab Displayed ", true);
        return isDisplayedById(DriverConfig.getDriver(), MENU_CONTACT_TAB, SHORT_TIMEOUT);
    }

    /**
     * Check for partner type tab displayed.
     * @return true, if partner type tab displayed.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isPartnerTypesTabDisplayed()
     */
    @Override
    public boolean isPartnerTypesTabDisplayed() {

        LogUtil.setLogString("Check Partner Types tab Displayed ", true);
        return isDisplayedById(DriverConfig.getDriver(), MENU_ROLE_TAB, SHORT_TIMEOUT);
    }

    /**
     * Check for Primary account Details tab Displayed.
     * @return true, if account details tab displayed.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isAccountTabDisplayed()
     */
    @Override
    public boolean isAccountTabDisplayed() {

        LogUtil.setLogString("Check Primary Account Details tab Displayed ", true);
        return isDisplayedById(DriverConfig.getDriver(), MENU_ACCOUNT_TAB, SHORT_TIMEOUT);
    }

    /**
     * Checks if is account tab highlighted.
     * @return true, if is account tab highlighted
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isAccountTabHighlighted()
     */
    @Override
    public boolean isAccountTabHighlighted() {

        LogUtil.setLogString("Check Primary Account Details tab Highlighted ", true);
        final WebElement accountTab = DriverConfig.getDriver().findElement(By.id(MENU_ACCOUNT_TAB));
        return accountTab.getAttribute("class").contains(ACTIVE);
    }

    /**
     * Check if is contact tab highlighted.
     * @return true, if is contact tab highlighted.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isContactTabHighlighted()
     */
    @Override
    public boolean isContactTabHighlighted() {

        LogUtil.setLogString("Check Contact Information tab Highlighted ", true);
        final WebElement contactTab = DriverConfig.getDriver().findElement(By.id(MENU_CONTACT_TAB));
        return contactTab.getAttribute("class").contains(ACTIVE);
    }

    /**
     * check if partner types tab highlighted.
     * @return true, if partnerTypes tab highlighted.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isPartnerTypesTabHighlighted()
     */
    @Override
    public boolean isPartnerTypesTabHighlighted() {

        LogUtil.setLogString("Check Partner Types tab Highlighted ", true);
        final WebElement roleTab = DriverConfig.getDriver().findElement(By.id(MENU_ROLE_TAB));
        return roleTab.getAttribute("class").contains(ACTIVE);
    }

    /**
     * Click Find.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickFind()
     */
    @Override
    public void clickFind() {

        LogUtil.setLogString("Click Find.", true);
        final WebElement buttonElement = retrieveElementByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Find", MEDIUM_TIMEOUT);
        buttonElement.click();
    }

    /**
     * Search By Name.
     * @param partnerName the partner name
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#searchByName(java.lang.String)
     */
    @Override
    public void searchByName(String partnerName) {

        LogUtil.setLogString("Search Partner by Name :" + partnerName, true);
        final WebElement textBoxElement = DriverConfig.getDriver().findElement(
                By.id(partnerConfig.get(PARTNER_FEILD_ID)));
        textBoxElement.sendKeys(partnerName);
    }

    /**
     * Search By Email.
     * @param partnerEmail the partner email
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#searchByEmail(java.lang.String)
     */
    @Override
    public void searchByEmail(String partnerEmail) {

        LogUtil.setLogString("Search Partner by Email :" + partnerEmail, true);
        final WebElement textBoxElement = DriverConfig.getDriver().findElement(
                By.id(partnerConfig.get(PARTNER_EMAIL_FEILD_ID)));
        textBoxElement.sendKeys(partnerEmail);

    }

    /**
     * Fetch result message.
     * @return String
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#getResultMessage()
     */
    @Override
    public String getResultMessage() {

        LogUtil.setLogString("Fetch Reuslt Message", true);
        final WebElement getMessage = retrieveElementByTagText(DriverConfig.getDriver(),
                TAG_STRONG, RESULT_MESSAGE);
        final String resultMessage = getMessage.getText();
        LogUtil.setLogString("Reuslt Message :" + resultMessage, true);
        return resultMessage;
    }

    /**
     * Checks if is displayed top first partner.
     * @param partnerName the partner name
     * @return true, if is displayed top first partner
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isDisplayedTopFirstPartner()
     */
    @Override
    public void isDisplayedTopFirstPartner(final String partnerName) {

        LogUtil.setLogString("Check for details given is Displayed.", true);
        mediumWait();
        /*
         * final WebElement partnerRow = DriverConfig.getDriver()
         * .findElements(By.id(partnerConfig.get(PARTNER_TABLE))).get(0); final WebElement
         * partnerValue = retrieveElementByAttributeValueContainsForSubElement(
         * DriverConfig.getDriver(), partnerRow, TAG_TD, ATTR_CLASS, PARTNER_CLASS, MEDIUM_TIMEOUT);
         */
        final WebElement partnerValue = retrieveElementByTagText(DriverConfig.getDriver(), TAG_TD,
                partnerName);
        LogUtil.setLogString("Displayed Details." + partnerValue.getText(), true);
        /*
         * final boolean partnerDisplay = isDisplayedByElementText(DriverConfig.getDriver(),
         * partnerValue, partnerValue.getText(), MEDIUM_TIMEOUT); return partnerDisplay;
         */
    }

    /**
     * Click Next.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickNext()
     */
    @Override
    public void clickNext() {

        LogUtil.setLogString("click Next", true);
        WaitUtil.smallWait();
        final WebElement nextLink = DriverConfig.getDriver().findElement(By.cssSelector("a.next.ef_smallLabel"));
        nextLink.click();
    }

    /**
     * Click previous.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickPrevious()
     */
    @Override
    public void clickPrevious() {

        LogUtil.setLogString("click Previous", true);
        WaitUtil.smallWait();
        final WebElement previousLink = DriverConfig.getDriver().findElement(By.cssSelector("a.prev.ef_smallLabel"));
        previousLink.click();
    }

    /**
     * Click First.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickFirst()
     */
    @Override
    public void clickFirst() {

        LogUtil.setLogString("click First", true);
        WaitUtil.smallWait();
        final WebElement firstLink = DriverConfig.getDriver().findElement(By.cssSelector("a.first.ef_smallLabel"));
        firstLink.click();
    }

    /**
     * Click Last.
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#clickLast()
     */
    @Override
    public void clickLast() {

        LogUtil.setLogString("click Last", true);
        WaitUtil.smallWait();
        final WebElement lastLink = DriverConfig.getDriver().findElement(By.cssSelector("a.last.ef_smallLabel"));
        lastLink.click();
    }

    /**
     * Checks if is first page displayed.
     * @return true, if is first page displayed
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isFirstPageDisplayed()
     */
    @Override
    public boolean isFirstPageDisplayed() {

        LogUtil.setLogString("Check either page is in 1st page", true);
        WaitUtil.smallWait();
        boolean pageDisplayed = false;
        final WebElement firstPage = retrieveElementByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_INPUT, ATTR_CLASS,
                partnerConfig.get(PAGINATION_CLASS), SHORT_TIMEOUT);
        if (firstPage.getText().contains("1"))
            pageDisplayed = true;
        return pageDisplayed;
    }

    /**
     * Checks if is second page displayed.
     * @return true, if is second page displayed
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isSecondPageDisplayed()
     */
    @Override
    public boolean isSecondPageDisplayed() {

        LogUtil.setLogString("Check either page is in 2nd page", true);
        WaitUtil.smallWait();
        final WebElement firstPage = retrieveElementByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_TD, ATTR_CLASS, "ef_smallLabel", SHORT_TIMEOUT);
        System.out.println(firstPage.getText() + "fsdf" + firstPage.getAttribute("class"));
        return true;
    }

    /**
     * Checks if is last pag displayed.
     * @return true, if is last pag displayed
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#isLastPagDisplayed()
     */
    @Override
    public boolean isLastPageDisplayed() {

        LogUtil.setLogString("Check either page is in last page", true);
        WaitUtil.smallWait();
        boolean pageDisplayed = false;
        /*
         * final WebElement firstPage = retrieveElementByContainsOfAttributeValue(
         * DriverConfig.getDriver(), TAG_INPUT, ATTR_CLASS, partnerConfig.get(PAGINATION_CLASS),
         * SHORT_TIMEOUT);
         */
        pageDisplayed = isDisplayedByClassName(DriverConfig.getDriver(),
                partnerConfig.get(PAGINATION_CLASS), SHORT_TIMEOUT);
        return pageDisplayed;
    }

    /**
     * Fill partner types.
     * @param driver the driver
     * @param partnerType the partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#fillPartnerTypes(org.openqa.selenium.WebDriver,
     *      java.lang.String)
     */
    @Override
    public void fillPartnerTypes(final WebDriver driver, final String partnerType) {

        fillPartnerTypeDetails(driver, partnerType);
    }

    /**
     * Enter partner name.
     * @param partnerName the partner name
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerName(java.lang.String)
     */
    @Override
    public void enterPartnerName(final String partnerName) {

        LogUtil.setLogString("Enter Partner name as " + partnerName, true);
        isDisplayedById(DriverConfig.getDriver(), partnerConfig.get(PARTNER_NAME_ID), SHORT_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_NAME_ID)))
                .sendKeys(partnerName);
    }

    /**
     * Enter partner email id.
     * @param partnerEmailId the partner email id
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerEmailId(java.lang.String)
     */
    @Override
    public void enterPartnerEmailId(final String partnerEmailId) {

        LogUtil.setLogString("Enter email address " + partnerEmailId, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_EMAIL_ID)))
                .sendKeys(partnerEmailId);
    }

    /**
     * Enter partner status.
     * @param partnerStatus the partner status
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerStatus(java.lang.String)
     */
    @Override
    public void enterPartnerStatus(final String partnerStatus) {

        LogUtil.setLogString("select user status ", true);
        Select select1 = new Select(DriverConfig.getDriver().findElement(
                By.id(partnerConfig.get(PARTNER_STATUS_ID))));
        select1.selectByValue(partnerStatus);
    }

    /**
     * Enter partner address1.
     * @param partnerAddress1 the partner address1
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerAddress1(java.lang.String)
     */
    @Override
    public void enterPartnerAddress1(final String partnerAddress1) {

        LogUtil.setLogString("Enter street address1 as" + partnerAddress1, true);
        isDisplayedById(DriverConfig.getDriver(), partnerConfig.get(PARTNER_STREET_ID_1),
                MEDIUM_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_STREET_ID_1)))
                .sendKeys(partnerAddress1);
    }

    /**
     * Enter partner address2.
     * @param partnerAddress2 the partner address2
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerAddress2(java.lang.String)
     */
    @Override
    public void enterPartnerAddress2(final String partnerAddress2) {

        LogUtil.setLogString("Enter street address2 as " + partnerAddress2, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_STREET_ID_2)))
                .sendKeys(partnerAddress2);

    }

    /**
     * Enter partner city.
     * @param partnerCity the partner city
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerCity(java.lang.String)
     */
    @Override
    public void enterPartnerCity(final String partnerCity) {

        LogUtil.setLogString("Enter city " + partnerCity, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_CITY_ID)))
                .sendKeys(partnerCity);
    }

    /**
     * Enter partner country.
     * @param partnerCountry the partner country
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerCountry(java.lang.String)
     */
    @Override
    public void enterPartnerCountry(final String partnerCountry) {

        LogUtil.setLogString("Enter country " + partnerCountry, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_COUNTRY_ID)))
                .sendKeys(partnerCountry);
    }

    /**
     * Enter primary contact number.
     * @param primaryContactNumber the primary contact number
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPrimaryContactNumber(java.lang.String)
     */
    @Override
    public void enterPrimaryContactNumber(final String primaryContactNumber) {

        LogUtil.setLogString("Enter primary contact name " + primaryContactNumber, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_CONTACT_NAME)))
                .sendKeys(primaryContactNumber);
    }

    /**
     * Enter partner phone number.
     * @param phoneNumber the phone number
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerPhoneNumber(java.lang.String)
     */
    @Override
    public void enterPartnerPhoneNumber(final String phoneNumber) {

        LogUtil.setLogString("Enter phone no " + phoneNumber, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_PHONE_ID)))
                .sendKeys(phoneNumber);
    }

    /**
     * Enter partner state.
     * @param partnerState the partner state
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerState(java.lang.String)
     */
    @Override
    public void enterPartnerState(final String partnerState) {

        LogUtil.setLogString("Enter state " + partnerState, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_STATE_ID)))
                .sendKeys(partnerState);
    }

    /**
     * Enter partner zip.
     * @param partnerZip the partner zip
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#enterPartnerZip(java.lang.String)
     */
    @Override
    public void enterPartnerZip(final String partnerZip) {

        LogUtil.setLogString("Enter zip " + partnerZip, true);
        DriverConfig.getDriver().findElement(By.id(partnerConfig.get(PARTNER_ZIP_ID)))
                .sendKeys(partnerZip);

    }

    /**
     * Save without email id.
     * @param driver the driver
     * @param partnerName the partner name
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param partnerType the partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutEmailId(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutEmailId(final WebDriver driver, final String partnerName,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber, final String partnerType) {

        clickAccountTab();
        enterPartnerName(partnerName);
        enterPartnerStatus(activeUser);
        fillContactInformation(driver, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber);
        fillPartnerTypes(driver, partnerType);
    }

    /**
     * Save without street address one.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutStreetAddress(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutStreetAddress(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String city, final String state,
            final String zip, final String country, final String primaryContactName,
            final String phoneNumber, final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerCity(city);
        enterPartnerState(state);
        enterPartnerZip(zip);
        enterPartnerCountry(country);
        enterPrimaryContactNumber(primaryContactName);
        enterPartnerPhoneNumber(phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Save without city.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutCity(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutCity(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerState(state);
        enterPartnerZip(zip);
        enterPartnerCountry(country);
        enterPrimaryContactNumber(primaryContactName);
        enterPartnerPhoneNumber(phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Save without state.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutState(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutState(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerCity(city);
        enterPartnerZip(zip);
        enterPartnerCountry(country);
        enterPrimaryContactNumber(primaryContactName);
        enterPartnerPhoneNumber(phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Save without zip.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutZip(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutZip(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerCity(city);
        enterPartnerState(state);
        enterPartnerCountry(country);
        enterPrimaryContactNumber(primaryContactName);
        enterPartnerPhoneNumber(phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Save without country.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutCountry(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutCountry(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerCity(city);
        enterPartnerState(state);
        enterPartnerZip(zip);
        enterPrimaryContactNumber(primaryContactName);
        enterPartnerPhoneNumber(phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Save without primary contact name.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutPrimaryContactName(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutPrimaryContactName(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String phoneNumber, final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerCity(city);
        enterPartnerState(state);
        enterPartnerZip(zip);
        enterPartnerCountry(country);
        enterPartnerPhoneNumber(phoneNumber);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Save without phone number.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param availablePartnerType the available partner type
     * @see com.ecofactor.qa.automation.insite.page.PartnerManagement#saveWithoutPhoneNumber(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void saveWithoutPhoneNumber(final WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, String zip,
            final String country, final String primaryContactName, final String availablePartnerType) {

        fillAccountDetails(partnerName, emailId, activeUser);
        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerCity(city);
        enterPartnerState(state);
        enterPartnerZip(zip);
        enterPartnerCountry(country);
        enterPrimaryContactNumber(primaryContactName);
        fillPartnerTypeDetails(driver, availablePartnerType);
    }

    /**
     * Fill account details.
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     */
    private void fillAccountDetails(final String partnerName, final String emailId,
            final String activeUser) {

        clickAccountTab();
        enterPartnerName(partnerName);
        enterPartnerEmailId(emailId);
        enterPartnerStatus(activeUser);
    }

    /**
     * Fill contact information.
     * @param driver the driver
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     */
    private void fillContactInformation(final WebDriver driver, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber) {

        clickContactInformation();
        enterPartnerAddress1(streetAddress1);
        enterPartnerAddress2(streetAddress2);
        enterPartnerCity(city);
        enterPartnerState(state);
        enterPartnerZip(zip);
        enterPartnerCountry(country);
        enterPrimaryContactNumber(primaryContactName);
        enterPartnerPhoneNumber(phoneNumber);
    }

    /**
     * Fill partner type details.
     * @param driver the driver
     * @param availableRole the available role
     */
    private void fillPartnerTypeDetails(final WebDriver driver, final String availableRole) {

        clickPartnerTypes();
        LogUtil.setLogString("check if available roles element is displayed.", true);
        isDisplayedById(driver, partnerConfig.get(AVAILABLE_PARTNER_TYPE), MEDIUM_TIMEOUT);
        LogUtil.setLogString("unselect existing selection.", true);
        removeAllToAvailablePane(driver);
        final Select select = new Select(driver.findElement(By.id(partnerConfig
                .get(AVAILABLE_PARTNER_TYPE))));
        select.deselectAll();
        LogUtil.setLogString("select required role " + availableRole, true);
        select.selectByValue(availableRole);
        LogUtil.setLogString("click on add button.", true);
        driver.findElement(By.id(ADD_AVAILABLE_ROLE)).click();
    }

    /**
     * <p>
     * Remove all values from the assigned panel to the available panel and verify there is no value
     * in the assigned pane.
     * </p>
     * @param driver the driver
     */
    private void removeAllToAvailablePane(final WebDriver driver) {

        LogUtil.setLogString(
                "Remome all values from the assigned Pane and moving to available pane", true);
        final Select select = new Select(driver.findElement(By.id(partnerConfig
                .get(ASSIGNED_PARTNER_TYPE))));

        final List<WebElement> optionsList = select.getOptions();

        for (final WebElement webElement : optionsList) {
            select.selectByVisibleText(webElement.getText());
            driver.findElement(By.id(REMOVE_ASSIGNED_ROLE)).click();
        }

        final List<WebElement> optionsList2 = select.getOptions();
        (new WebDriverWait(driver, SHORT_TIMEOUT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                boolean outcome = false;
                outcome = optionsList2.size() == 0 ? true : false;
                return outcome;
            }
        });
    }
}
