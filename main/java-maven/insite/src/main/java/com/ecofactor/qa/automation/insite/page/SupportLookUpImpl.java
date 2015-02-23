/*
 * SupportLookUpImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.InsiteLoginConfig.*;
import static com.ecofactor.qa.automation.insite.config.InsiteSupportConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Assert.*;

import java.util.*;

import org.openqa.selenium.*;
import org.slf4j.*;
import org.testng.*;

import com.ecofactor.qa.automation.insite.config.*;
import com.ecofactor.qa.automation.util.*;
import com.google.inject.*;

/**
 * SupportLookUp class provides methods to perform account search related operations. It also
 * provides methods to edit account related options. Breadcrumb : Insite Login>Support
 * @author $Author: vraj $
 * @version $Rev: 33450 $ $Date: 2015-01-09 13:09:49 +0530 (Fri, 09 Jan 2015) $
 */
public class SupportLookUpImpl extends InsiteAuthenticatedPageImpl implements SupportLookUp {

    /** The login config. */
    @Inject
    private InsiteLoginConfig loginConfig;

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The support config. */
    @Inject
    private InsiteSupportConfig supportConfig;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(SupportLookUpImpl.class);

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    @SuppressWarnings("static-access")
    @Override
    public void loadPage() {

        String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.SUPPORT_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            clickSupport();
        }

    }

    /**
     * Search by email.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByEmail(final String textBoxFieldValue) {

        searchAccountLookUp(supportConfig.get(ACCOUNT_EMAIL), textBoxFieldValue);

    }

    /**
     * Search by phone.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByPhone(final String textBoxFieldValue) {

        searchAccountLookUp(supportConfig.get(ACCOUNT_PHONE), textBoxFieldValue);
    }

    /**
     * Search account look up.
     * @param textBoxFieldName the text box field name
     * @param textBoxFieldValue the text box field value
     */
    public void searchAccountLookUp(final String textBoxFieldName, final String textBoxFieldValue) {

        DriverConfig
                .setLogString(
                        "Send value to required text box and Click on find Button, then verify the search results are populated successfully.",
                        true);
        logger.info("check if search text box is displayed.");
        boolean emailTextDisplayed = isDisplayedById(DriverConfig.getDriver(), textBoxFieldName,
                MEDIUM_TIMEOUT);
        logger.info("provide value in search text.");
        DriverConfig.setLogString("Search " + textBoxFieldValue, true);
        DriverConfig.getDriver().findElement(By.id(textBoxFieldName)).sendKeys(textBoxFieldValue);
        DriverConfig.setLogString("select find button.", true);
        if (emailTextDisplayed) {
            WebElement findbuttonElement = retrieveElementByAttributeValue(
                    DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, supportConfig.get(FIND_BUTTON));
            findbuttonElement.click();
            DriverConfig.setLogString("Verify Search result", true);
            confirmSearchResultValue(textBoxFieldValue);
        }
    }

    /**
     * Confirm search result value.
     * @param searchResultValue the search result value
     * @return true, if successful
     */
    public boolean confirmSearchResultValue(final String searchResultValue) {

        DriverConfig
                .setLogString(
                        "Verify the search result is displayed and check for required result is populated in the grid.",
                        true);
        boolean outcome = false;
        Assert.assertTrue(
                isMultipleClassNameDisplayed(DriverConfig.getDriver(),
                        supportConfig.get(SEARCH_RESULT_CLASS), MEDIUM_TIMEOUT), "No Result Found");
        List<WebElement> resultElements = DriverConfig.getDriver().findElements(
                By.className(supportConfig.get(SEARCH_RESULT_CLASS)));

        for (WebElement webElement : resultElements) {
            outcome = webElement.getText().contains(searchResultValue) ? true : false;
            DriverConfig.setLogString("Record Displayed." + searchResultValue, true);
            if (outcome)
                return outcome;
        }
        DriverConfig.setLogString("verify if result are displayed.", true);
        Assert.assertEquals(true, resultElements.size() > 0, "Result size is 0");
        return outcome;
    }

    /**
     * Verify installation hardware.
     * @param emailId the email id
     */
    public void verifyInstallationHardware(final String emailId) {

        DriverConfig.setLogString("Click on the emailId as recieved - " + emailId, true);
        WebElement searchElement = retrieveElementByLinkText(DriverConfig.getDriver(), emailId,
                MEDIUM_TIMEOUT);
        searchElement.click();
        WaitUtil.waitUntil(200);
        logger.info("Verify installed hardware menu is displayed.");
        isDisplayedByLinkText(DriverConfig.getDriver(), supportConfig.get(INSTALLED_HARDWARE_MENU),
                MEDIUM_TIMEOUT);

        DriverConfig.setLogString("Click on Installed Hardware.", true);
        WebElement installedHardwareElement = retrieveElementByLinkText(DriverConfig.getDriver(),
                supportConfig.get(INSTALLED_HARDWARE_MENU), SHORT_TIMEOUT);
        installedHardwareElement.click();
        WaitUtil.waitUntil(MEDIUM_TIMEOUT);

        verifyAndLogThermostatDetails();
        DriverConfig.setLogString("Verify there is no exception.", true);
        Assert.assertTrue(!DriverConfig.getDriver().getPageSource().contains("exception"),
                "Found exception");
        closeAlert(DriverConfig.getDriver());
    }

    /**
     * Verify and log thermostat details.
     */
    private void verifyAndLogThermostatDetails() {

        DriverConfig.setLogString("Verify the thermostat device status and Log the details.", true);

        logger.info("Installed Hardware details.");
        List<WebElement> noOfInstalledharwareList = DriverConfig.getDriver().findElements(
                By.id("user"));
        StringBuffer tableContent = new StringBuffer();
        tableContent.append("<table>");
        tableContent.append("<tr style='font-weight:bold'>");
        tableContent.append("<td>Device");
        tableContent.append("</td>");
        tableContent.append("<td>Name/ID");
        tableContent.append("</td>");
        tableContent.append("<td>Model");
        tableContent.append("</td>");
        tableContent.append("<td>Connectivity");
        tableContent.append("</td>");
        tableContent.append("</tr>");

        for (WebElement webElement : noOfInstalledharwareList) {
            tableContent.append("<tr>");
            List<WebElement> columnList = webElement.findElements(By.className("userinfo"));
            for (WebElement webElement2 : columnList) {
                tableContent.append("<td>");
                tableContent.append(webElement2.getText());
                if (webElement2.getText().isEmpty()) {
                    DriverConfig.setLogString("check if thermostat status image is displayed.",
                            true);
                    WebElement imageElement = retrieveElementByAttributeValueForSubElement(
                            DriverConfig.getDriver(), webElement2, "img", "src", "images",
                            MEDIUM_TIMEOUT);
                    if (imageElement.getAttribute("src").contains("INACTIVE")) {
                        tableContent.append(supportConfig.get(THERMOSTAT_INACTIVE));
                    } else {
                        tableContent.append(supportConfig.get(THERMOSTAT_ACTIVE));
                    }
                }
                tableContent.append("</td>");
            }
            tableContent.append("</tr>");

        }
        tableContent.append("</table>");
        logger.info(tableContent.toString());
    }

    /**
     * Click searched result element.
     * @param searchResultValue the search result value
     */
    public void clickSearchedResultElement(final String searchResultValue) {

        logger.info("find search result container.");
        List<WebElement> resultElements = DriverConfig.getDriver().findElements(
                By.className(supportConfig.get(SEARCH_RESULT_CLASS)));
        DriverConfig.setLogString("find search result shows up proper result and click it.", true);
        for (WebElement webElement : resultElements) {
            boolean outcome = webElement.getText().contains(searchResultValue) ? true : false;

            if (outcome) {
                webElement.click();
                tinyWait();
                break;
            }

        }
    }

    /**
     * Click reset password.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickResetPassword()
     */
    public void clickResetPassword() {

        smallWait();
        DriverConfig.setLogString("click reset password.", true);
        WebElement resetPswdDiv = DriverConfig.getDriver().findElement(
                By.id("supportPage-leftNavigation-resetPasswordLink"));
        logger.info("resetPswdDiv class: " + resetPswdDiv.getAttribute("class"));
        resetPswdDiv.click();
        smallWait();
        List<WebElement> okButton = DriverConfig.getDriver().findElements(By.tagName("button"));
        okButton.get(0).click();
        closeAlert(DriverConfig.getDriver());
    }

    /**
     * Disable load shapping.
     * @param email the email
     */
    public void disableLoadShapping(String email) {

        searchByEmail(email);
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        WebElement emailElement = retrieveElementByLinkText(DriverConfig.getDriver(), email,
                SHORT_TIMEOUT);
        emailElement.click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        WebElement disableLSButton = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_INPUT, ATTR_VALUE, "Exclude Location");
        disableLSButton.click();
        Alert alert = DriverConfig.getDriver().switchTo().alert();
        alert.accept();
        DriverConfig.getDriver().switchTo().defaultContent();
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE,
                "Include Location");
    }

    /**
     * Click specified email.
     * @param email the email
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickSpecifiedEmail(java.lang.String)
     */
    public void clickSpecifiedEmail(String email) {

        DriverConfig.setLogString("Click on the emailId as recieved - " + email, true);
        WebElement element = retrieveElementByLinkText(DriverConfig.getDriver(), email,
                MEDIUM_TIMEOUT);
        element.click();
        tinyWait();
        DriverConfig.setLogString("Verify istalled harware menu is displayed.", true);
        isDisplayedByLinkText(DriverConfig.getDriver(), supportConfig.get(INSTALLED_HARDWARE_MENU),
                MEDIUM_TIMEOUT);
    }

    /**
     * Click home owner edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickHomeOwnerEdit()
     */
    public void clickHomeOwnerEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the Home Owner Link.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(HOME_OWNER_LINK));
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click phone number edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickPhoneNumberEdit()
     */
    public void clickPhoneNumberEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the Phone Number Link.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(PHONE_NUMBER_LINK));
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click energy efficiency refresh.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickEnergyEfficiencyRefresh()
     */
    public void clickEnergyEfficiencyRefresh() {

        tinyWait();
        DriverConfig.setLogString("Click on the Energy Efficiency Refresh.", true);
        WebElement element = containByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR, ATTR_ID,
                supportConfig.get(ENERGY_EFF_REFRESH), MEDIUM_TIMEOUT);
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click ecp core edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickEcpCoreEdit()
     */
    public void clickEcpCoreEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the ECp Core edit.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(ECP_CORE_LINK), MEDIUM_TIMEOUT);
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click user name edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickUserNameEdit()
     */
    public void clickUserNameEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the User Name edit.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(USER_NAME_LINK), MEDIUM_TIMEOUT);
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click email id edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickEmailIdEdit()
     */
    public void clickEmailIdEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the Email Id edit.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(EMAIL_ADDR_LINK), MEDIUM_TIMEOUT);
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click electricity rate edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickElectricityRateEdit()
     */
    public void clickElectricityRateEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the Electricity Rate edit.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(ELECTRICITY_RATE_LINK), MEDIUM_TIMEOUT);
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click gas rate edit.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickGasRateEdit()
     */
    public void clickGasRateEdit() {

        tinyWait();
        DriverConfig.setLogString("Click on the Gas Rate edit.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(GAS_RATE_LINK), MEDIUM_TIMEOUT);
        element.findElement(By.tagName(TAG_IMG)).click();
    }

    /**
     * Click pop up cancel.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickPopUpCancel()
     */
    public void clickPopUpCancel() {

        DriverConfig.setLogString("Click on the Pop up Cancel.", true);
        List<WebElement> listElement = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS, supportConfig.get(OK_BTN));
        for (WebElement element : listElement) {
            if (element.getText().equalsIgnoreCase("Cancel")) {
                element.click();
            }
        }
        smallWait();
    }

    /**
     * Click about ecofactor.
     * @see com.ecofactor.qa.automation.insite.page.InsitePageImpl#clickAboutEcofactor()
     */
    @Override
    public void clickAboutEcofactor() {

        // Auto-generated method stub
    }

    /**
     * Click proxy login.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickProxyLogin()
     */
    @Override
    public void clickProxyLogin() {

        DriverConfig.setLogString("Click Proxy Login.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_ID, supportConfig.get(PROXY_LINK), LONG_TIMEOUT);
        element.click();

    }

    /**
     * Iterate results and verify empty fields.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#iterateResultsAndVerifyEmptyFields()
     */
    @Override
    public void iterateResultsAndVerifyEmptyFields() {

        DriverConfig.setLogString("Iterate Search Results and Verify empty results", true);
        List<WebElement> resultTr = retrieveElementsByAttributeValueList(DriverConfig.getDriver(),
                "tr", "id", "user");
        boolean gateWayModelFound = false;
        String text = "";
        for (WebElement trElement : resultTr) {

            if (gateWayModelFound)
                break;
            List<WebElement> resultTd = trElement.findElements(By.tagName("td"));
            int count = 0;
            for (WebElement tdElement : resultTd) {
                if (count == 9)
                    text = tdElement.getText();

                logger.info("Account status: " + text);
                if (text.contains("ACTIVE")) {
                    WebElement idElement = resultTd.get(0);
                    WebElement searchElement = retrieveElementByLinkText(DriverConfig.getDriver(),
                            idElement.getText(), MEDIUM_TIMEOUT);
                    logger.info("click active account.", true);
                    searchElement.click();
                    WaitUtil.waitUntil(200);

                    DriverConfig.setLogString("click Installed Hardware.", true);
                    retrieveElementByLinkText(DriverConfig.getDriver(), "Installed Hardware",
                            MEDIUM_TIMEOUT).click();
                    WaitUtil.waitUntil(200);

                    WebElement gateWayDiv = retrieveElementByAttributeValue(
                            DriverConfig.getDriver(), TAG_DIV, ATTR_ID,
                            "supportPage-gatewayDetails", MEDIUM_TIMEOUT);
                    DriverConfig.setLogString("click Expand Gateway." + gateWayDiv.getTagName(),
                            true);

                    retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(),
                            gateWayDiv, TAG_IMG, ATTR_CLASS, "show", MEDIUM_TIMEOUT).click();
                    WaitUtil.waitUntil(200);

                    DriverConfig.setLogString("Verify Gateway model.", true);
                    List<WebElement> elementTD = retrieveElementsByTagText(
                            DriverConfig.getDriver(), TAG_TD, "iControl Android Touchscreen");

                    for (WebElement element : elementTD) {
                        DriverConfig.setLogString("element.getText() " + element.getText(), true);
                        gateWayModelFound = true;
                        break;
                    }
                    break;
                }
                count++;
            }
        }
        DriverConfig.setLogString("Check if gateway model 'iControl Android Touchscreen' found.",
                true);
        Assert.assertTrue(gateWayModelFound,
                "Gateway model 'iControl Android Touchscreen' not found.");
    }

    /**
     * Iterate results and verify comcast id.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#iterateResultsAndVerifyComcastId()
     */
    @Override
    public void iterateResultsAndVerifyComcastId() {

        DriverConfig.setLogString("Iterate Search Results and verify ComcastId", true);
        List<WebElement> resultTr = retrieveElementsByAttributeValueList(DriverConfig.getDriver(),
                "tr", "id", "user");
        for (WebElement trElement : resultTr) {
            logger.info("tr id: " + trElement.getAttribute("id"));
            List<WebElement> resultTd = trElement.findElements(By.tagName("td"));
            int i = 0;
            for (WebElement td : resultTd) {
                if (i == 0) {
                    Assert.assertFalse(td.getText().isEmpty(), "Search result is empty");
                }
                i++;
            }
        }
    }

    /**
     * Click top first user.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickTopFirstUser()
     */
    @Override
    public void clickTopFirstUser() {

        DriverConfig.setLogString("Click Top First User", true);
        WebElement tagTable = retrieveElementByAttributeValue(DriverConfig.getDriver(), "table",
                "id", "user");
        WebElement tagTbody = tagTable.findElement(By.tagName("tbody"));
        List<WebElement> resultTr = tagTbody.findElements(By.tagName("tr"));
        boolean lookUpFound = false;
        for (WebElement trElement : resultTr) {
            List<WebElement> resultTd = trElement.findElements(By.tagName("td"));

            for (WebElement td : resultTd) {

                Assert.assertFalse(td.getText().isEmpty(), "Search result is empty");

                logger.info("name link: " + td.getText());
                td.findElement(By.tagName(TAG_ANCHOR)).click();
                smallWait();
                DriverConfig.setLogString("Verify istalled harware menu is displyed.", true);
                WebElement accountDetails = DriverConfig.getDriver().findElement(
                        By.id("supportPage-accountDetails"));
                Assert.assertTrue(accountDetails.isDisplayed(), "Account details is not displayed");
                lookUpFound = true;
                break;

            }
            if (lookUpFound)
                break;
        }

    }

    /**
     * Click energy efficiency check box.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#clickEnergyEfficiencyCheckBox()
     */
    public void clickEnergyEfficiencyCheckBox() {

        DriverConfig.setLogString("click 'Energy efficiency' check box.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT,
                ATTR_ID, supportConfig.get(ENERGY_EFF_CHECK), MEDIUM_TIMEOUT);
        element.click();
        tinyWait();

        WebElement spanElement = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_SPAN, ATTR_ID, "EEText", MEDIUM_TIMEOUT);
        logger.info("Current EE is -" + spanElement.getText());
        if (element.getAttribute("checked") != null) {
            Assert.assertTrue(spanElement.getText().equalsIgnoreCase("ON"));
        } else {
            Assert.assertTrue(spanElement.getText().equalsIgnoreCase("OFF"));
        }
    }

    /**
     * Verify no proxy and reset link.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#verifyNoProxyAndResetLink()
     */
    public void verifyNoProxyAndResetLink() {

        DriverConfig.setLogString("Verify No Proxy Login Link and Reset Pwd Link.", true);
        Assert.assertTrue(!DriverConfig.getDriver().getPageSource().contains("Proxy Login"),
                "Proxy login link is available");
        Assert.assertTrue(!DriverConfig.getDriver().getPageSource().contains("Reset Password"),
                "Reset password link is available");
    }

    /**
     * Verify no export offline thermostat link.
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#verifyNoExportOfflineThermostatLink()
     */
    public void verifyNoExportOfflineThermostatLink() {

        tinyWait();
        DriverConfig.setLogString(
                "Verify that 'Export Offline Thermostats' Link is not displayed.", true);
        WebElement element = retrieveElementByTagText(DriverConfig.getDriver(), TAG_INPUT,
                "Export Offline Thermostats");
        Assert.assertTrue(element == null, "Export offline thermostat link is available");
    }

    /**
     * Verify proxy denied.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#verifyProxyDenied()
     */
    public boolean verifyProxyDenied() {

        DriverConfig.setLogString("Verify that 'Proxy Login not allowed' message is displayed.",
                true);
        if (DriverConfig.getDriver().switchTo().alert().getText()
                .contains("Proxy Login not allowed for")) {
            DriverConfig.getDriver().switchTo().alert().accept();
            return true;
        }

        return false;
    }

    /**
     * Do verify pswd reset by new app login.
     * @param userName the user name
     * @param tempPassword the temp password
     * @param newPassword the new password
     * @param newAppUrl the new app url
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#doVerifyPswdResetByNewAppLogin(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void doVerifyPswdResetByNewAppLogin(String userName, String tempPassword,
            String newPassword, String newAppUrl) {

        /*
         * WebDriver popup = null; Set<String> windowids =
         * DriverConfig.getDriver().getWindowHandles(); Iterator<String> iter =
         * windowids.iterator(); iter.next();
         * DriverConfig.setLogString("Switch to other window and verify the title", true); popup =
         * DriverConfig.getDriver().switchTo().window((String) iter.next()); smallWait(); iter =
         * windowids.iterator(); if (iter.hasNext()) { checkConsumerNewPasswordTrigger(userName,
         * tempPassword); doConsumerChangePassword(tempPassword, newPassword); doConsumerlogout();
         * loginConsumerPortal(userName, newPassword); doConsumerlogout(); popup.close(); // switch
         * to main window DriverConfig.setLogString("Switch to main window", true);
         * DriverConfig.getDriver().switchTo().window((String) iter.next()); }
         */
        DriverConfig.setLogString("Load URL : " + newAppUrl, true);
        DriverConfig.getDriver().get(newAppUrl);
        mediumWait();
        DriverConfig.setLogString("Enter UserName and Password", true);
        clearAndInput(DriverConfig.getDriver(), By.name("username"), userName);
        clearAndInput(DriverConfig.getDriver(), By.name("password"), tempPassword);
        tinyWait();
        DriverConfig.setLogString("Click Login", true);
        DriverConfig.getDriver().findElement(By.className("login_submit")).click();
        largeWait();
        DriverConfig.setLogString("Enter temporary Password : " + tempPassword, true);
        clearAndInput(DriverConfig.getDriver(), By.className("input_old_password"), tempPassword);
        tinyWait();
        DriverConfig.setLogString("Enter New Password : " + newPassword, true);
        clearAndInput(DriverConfig.getDriver(), By.className("input_new_password"), newPassword);
        tinyWait();
        DriverConfig.setLogString("Re-Enter New Password : " + newPassword, true);
        clearAndInput(DriverConfig.getDriver(), By.className("input_confirm_new_password"),
                newPassword);
        tinyWait();
        DriverConfig.setLogString("Click Continue", true);
        DriverConfig.getDriver().findElement(By.className("continueButton")).click();
        smallWait();
        DriverConfig.setLogString("Check HelpOverlay Page displayed", true);
        isDisplayedByClassName(DriverConfig.getDriver(), "help_slide", SHORT_TIMEOUT);
        while (!isDisplayedByClassName(DriverConfig.getDriver(), "close_ftux_button", SHORT_TIMEOUT)) {
            DriverConfig.setLogString("Click Next.", true);
            DriverConfig.getDriver().findElement(By.className("slick-next")).click();
        }
        DriverConfig.setLogString("Click GetStarted", true);
        DriverConfig.getDriver().findElement(By.className("close_ftux_button")).click();
        tinyWait();
        DriverConfig.setLogString("Click Logout", true);
        DriverConfig.getDriver().findElement(By.cssSelector("div.menu_row.logout")).click();
        assertTrue(isDisplayedByClassName(DriverConfig.getDriver(), "login_submit", SHORT_TIMEOUT),
                "Login Page Not Loaded Successfully");

    }

    /**
     * Do consumer change password.
     * @param password the password
     * @param newPassword the new password
     */
    public void doConsumerChangePassword(String password, String newPassword) {

        DriverConfig
                .setLogString("Enter old password, new password and confirm new password", true);
        DriverConfig.setLogString("Enter Old Passwod - " + password, true);
        DriverConfig.setLogString("Enter New Passwod - " + newPassword, true);
        DriverConfig.setLogString("Enter Confirm Passwod - " + newPassword, true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(CONSUMER_OLD_PASSWORD)))
                .sendKeys(password);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(CONSUMER_NEW_PASSWORD1)))
                .sendKeys(newPassword);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(CONSUMER_NEW_PASSWORD2)))
                .sendKeys(newPassword);

        DriverConfig.setLogString("Click Change Password", true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(CONSUMER_CHANGE_PSWD_BTN)))
                .click();

        smallWait();
    }

    /**
     * Check consumer new password trigger.
     * @param userId the user id
     * @param password the password
     */
    public void checkConsumerNewPasswordTrigger(String userId, String password) {

        loginConsumerPortal(userId, password);
        largeWait();
        DriverConfig.setLogString("Check Change Password page", true);
        boolean changePasswordPage = isDisplayedById(DriverConfig.getDriver(), "old_password",
                LONG_TIMEOUT);
        assertTrue(changePasswordPage, "Old password field is not available");
    }

    /**
     * Login consumer portal.
     * @param userId the user id
     * @param password the password
     */
    public void loginConsumerPortal(String userId, String password) {

        // enter the login credentials
        DriverConfig.setLogString("Check username and password fields", true);
        WebElement userField = DriverConfig.getDriver().findElement(
                By.id(loginConfig.get(CONSUMER_USER_ID)));
        WebElement passwordField = DriverConfig.getDriver().findElement(
                By.id(loginConfig.get(CONSUMER_PASSWORD)));
        assertNotNull(userField, "User name field is not available");
        assertNotNull(passwordField, "password field is not available");

        DriverConfig.setLogString("Enter username and password (" + userId + "/" + password + ")",
                true);
        userField.clear();
        passwordField.clear();

        userField.sendKeys(userId);
        passwordField.sendKeys(password);

        DriverConfig.setLogString("Check Login button, ", true);
        WebElement submitBtn = DriverConfig.getDriver().findElement(
                By.id(loginConfig.get(CONSUMER_SUBMIT_LOGIN)));
        assertNotNull(submitBtn, "Submit button is not available");

        DriverConfig.setLogString("Click the Login button", true);
        submitBtn.click();
    }

    /**
     * Verify consumer login.
     * @param userId the user id
     */
    public void verifyConsumerLogin(String userId) {

        DriverConfig.setLogString("Check logout link is present", true);
        WebElement logoutLink = DriverConfig.getDriver().findElement(
                By.cssSelector(loginConfig.get(CONSUMER_LOGOUT_LINK)));
        assertTrue(logoutLink.isDisplayed() && logoutLink.isEnabled(),
                "logout link is not available");

        DriverConfig.setLogString("Check welcome text in header contains " + userId, true);
        WebElement welcomeText = DriverConfig.getDriver().findElement(
                By.cssSelector(loginConfig.get(CONSUMER_WELCOME_TEXT)));
        assertTrue(welcomeText.getText().contains(userId), "welcome text doesn't contain the user");

        user = userId;
        DriverConfig.setLogString("Login success for " + user, true);

    }

    /**
     * Do consumerlogout.
     */
    public void doConsumerlogout() {

        DriverConfig.setLogString("Click Logout link.", true);
        WebElement logoutLink = DriverConfig.getDriver().findElement(
                By.cssSelector(loginConfig.get(CONSUMER_LOGOUT_LINK)));
        logoutLink.click();

        DriverConfig.setLogString("Check Login Page", true);
        boolean logoDisplayed = isDisplayedByCSS(DriverConfig.getDriver(),
                loginConfig.get(CONSUMER_LOGIN_LOGO), MEDIUM_TIMEOUT);
        assertTrue(logoDisplayed, "logo is not displayed");

    }

    /**
     * Enter character to electricity rate.
     * @param rate the rate
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#enterCharacterToElectricityRate(java.lang.String)
     */
    public void enterCharacterToElectricityRate(String rate) {

        DriverConfig.setLogString("Enter Electricity rate as ." + rate, true);
        WebElement electricityRate = DriverConfig.getDriver().findElement(
                By.id(supportConfig.get(ELECTRICITY_RATE)));
        electricityRate.sendKeys(rate);
        DriverConfig.setLogString("Click ok button", true);
        WebElement btnElement = DriverConfig.getDriver().findElement(
                By.className(supportConfig.get(OK_BTN)));
        btnElement.click();
    }

    /**
     * Gets the error message.
     * @return the error message
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#getErrorMessage()
     */
    public String getErrorMessage() {

        DriverConfig.setLogString("Check for error message", true);
        String validationMsg = "";
        List<WebElement> errorLabels = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_DIV, ATTR_CLASS, supportConfig.get(ERROR));
        for (WebElement webElement : errorLabels) {
            validationMsg += webElement.getText();
        }
        DriverConfig.setLogString("Error message : " + validationMsg, true);
        return validationMsg;
    }

    /**
     * Enter character to gas rate.
     * @param rate the rate
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#enterCharacterToGasRate(java.lang.String)
     */
    @Override
    public void enterCharacterToGasRate(String rate) {

        DriverConfig.setLogString("Enter Gas rate as ." + rate, true);
        WebElement electricityRate = DriverConfig.getDriver().findElement(
                By.id(supportConfig.get(GAS_RATE)));
        electricityRate.sendKeys(rate);
        DriverConfig.setLogString("Click ok button", true);
        WebElement btnElement = DriverConfig.getDriver().findElement(
                By.className(supportConfig.get(OK_BTN)));
        btnElement.click();
    }

    /*
     * (non-Javadoc)
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#enterPhoneNumber(java.lang.String)
     */
    /**
     * Enter phone number.
     * @param phoneNumber the phone number
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#enterPhoneNumber(java.lang.String)
     */
    @Override
    public void enterPhoneNumber(String phoneNumber) {

        DriverConfig.setLogString("Enter Phone Number as ." + phoneNumber, true);
        WebElement phoneNumberElement = DriverConfig.getDriver().findElement(
                By.id(supportConfig.get(PHONE_NUMBER)));
        phoneNumberElement.clear();
        phoneNumberElement.sendKeys(phoneNumber);
        clickOkBtn();
        closePopup();
    }

    /*
     * (non-Javadoc)
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#verifyErrorMessage()
     */
    /**
     * Verify error message.
     * @return the string
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#verifyErrorMessage()
     */
    @Override
    public String verifyErrorMessage() {

        DriverConfig.setLogString("Check for error message", true);
        String validationMsg = "";
        List<WebElement> errorLabels = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_DIV, ATTR_CLASS, supportConfig.get(PHONE_ERROR));
        for (WebElement webElement : errorLabels) {
            validationMsg += webElement.getText();
        }
        DriverConfig.setLogString("Error message : " + validationMsg, true);
        return validationMsg;
    }

    /*
     * (non-Javadoc)
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#enterEmailId(java.lang.String)
     */
    /**
     * Enter email id.
     * @param email the email
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#enterEmailId(java.lang.String)
     */
    @Override
    public void enterEmailId(String email) {

        DriverConfig.setLogString("Enter Email Id as ." + email, true);
        WebElement emailElement = DriverConfig.getDriver().findElement(
                By.id(supportConfig.get(Email_ID)));
        emailElement.clear();
        emailElement.sendKeys(email);
        clickOkBtn();
        closePopup();
    }

    /**
     * Click OK button.
     */
    public void clickOkBtn() {

        DriverConfig.setLogString("Click ok button", true);
        List<WebElement> listElement = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS, supportConfig.get(OK_BTN));
        for (WebElement element : listElement) {
            if (element.getText().equalsIgnoreCase("OK")) {
                element.click();
            }
        }
        smallWait();
    }

    /**
     * Close popup.
     */
    public void closePopup() {

        DriverConfig.setLogString("Close popup", true);
        List<WebElement> listElement = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS, supportConfig.get(OK_BTN));
        for (WebElement element : listElement) {
            if (element.getText().equalsIgnoreCase("Close")) {
                element.click();
            }
        }
        smallWait();
    }

    /**
     * search by emial address.
     * @param address the address
     */
    public void searchByAddress(String address) {

        DriverConfig.setLogString("Enter address as " + address, true);
        WebElement addressElement = DriverConfig.getDriver().findElement(By.id("address"));
        addressElement.sendKeys(address);
        tinyWait();

        WebElement buttonElement = retrieveElementByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Find", MEDIUM_TIMEOUT);
        buttonElement.click();
    }

    /**
     * click first address.
     * @param address the address
     */
    public void clickFirstAddress(String address) {

        DriverConfig.setLogString("Click Address", true);
        List<WebElement> addressElement = DriverConfig.getDriver().findElements(
                By.linkText(address));
        addressElement.get(0).click();
    }

    /**
     * Click installation date picker.
     */
    public void clickInstallationDate() {

        DriverConfig.setLogString("Click Installation Date/Time", true);
        WebElement dateElement = DriverConfig.getDriver().findElement(By.id("datetime"));
        dateElement.click();
    }

    /**
     * Checks if is no result displayed.
     * @return true, if is no result displayed
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#isNoResultDisplayed()
     */
    @Override
    public boolean isNoResultDisplayed() {

        DriverConfig.setLogString("Check No Result Found Text displayed", true);
        return isDisplayedByCSS(DriverConfig.getDriver(), ".ef_smallLabel>strong", SHORT_TIMEOUT);
    }

    /**
     * Search by email id.
     * @param textBoxFieldValue the text box field value
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#searchByEmailId(java.lang.String)
     */
    @Override
    public void searchByEmailId(String textBoxFieldValue) {

        searchInSupport(supportConfig.get(ACCOUNT_EMAIL), textBoxFieldValue);

    }

    /**
     * Search by phone number.
     * @param textBoxFieldValue the text box field value
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#searchByPhoneNumber(java.lang.String)
     */
    @Override
    public void searchByPhoneNumber(String textBoxFieldValue) {

        searchInSupport(supportConfig.get(ACCOUNT_PHONE), textBoxFieldValue);
    }

    /**
     * Search by id.
     * @param textBoxFieldValue the text box field value
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#searchById(java.lang.String)
     */
    @Override
    public void searchById(String textBoxFieldValue) {

        searchInSupport(supportConfig.get(ACCOUNT_ID), textBoxFieldValue);
    }

    /**
     * Search by street addr.
     * @param textBoxFieldValue the text box field value
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#searchByStreetAddr(java.lang.String)
     */
    @Override
    public void searchByStreetAddr(String textBoxFieldValue) {

        searchInSupport(supportConfig.get(ACCOUNT_STREET_ADDR), textBoxFieldValue);
    }

    /**
     * Check for top first user.
     * @param searchName the search name
     * @return true, if successful
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#checkForTopFirstUser()
     */
    @Override
    public boolean checkForTopFirstUser(final String searchName) {

        DriverConfig.setLogString("Check Top First User", true);
        WebElement tagTable = retrieveElementByAttributeValue(DriverConfig.getDriver(), "table",
                "id", "user");
        WebElement tagTbody = tagTable.findElement(By.tagName("tbody"));
        List<WebElement> resultTr = tagTbody.findElements(By.tagName("tr"));

        for (WebElement trElement : resultTr) {
            List<WebElement> resultTd = trElement.findElements(By.tagName("td"));

            for (@SuppressWarnings("unused")
            WebElement td : resultTd) {

                isDisplayedByTagName(DriverConfig.getDriver(), TAG_ANCHOR, SHORT_TIMEOUT);
                smallWait();
                // DriverConfig.setLogString("Verify istalled harware menu is displyed.", true);
                break;
            }
        }
        logger.info("Top First User : " + searchName);
        return true;
    }

    /**
     * Search in support.
     * @param textBoxFieldName the text box field name
     * @param textBoxFieldValue the text box field value
     */
    private void searchInSupport(final String textBoxFieldName, final String textBoxFieldValue) {

        DriverConfig.setLogString("Send value to required text box and Click on find Button", true);
        logger.info("check if search text box is displayed.");
        boolean emailTextDisplayed = isDisplayedById(DriverConfig.getDriver(), textBoxFieldName,
                MEDIUM_TIMEOUT);
        logger.info("provide value in search text.");
        DriverConfig.setLogString("Search " + textBoxFieldValue, true);
        DriverConfig.getDriver().findElement(By.id(textBoxFieldName)).sendKeys(textBoxFieldValue);
        DriverConfig.setLogString("select find button.", true);
        if (emailTextDisplayed) {
            WebElement findbuttonElement = retrieveElementByAttributeValue(
                    DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, supportConfig.get(FIND_BUTTON));
            findbuttonElement.click();
        }
    }

    /**
     * Checks if is search text displayed.
     * @param searchText the search text
     * @return true, if is search text displayed
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#isSearchTextDisplayed(java.lang.String)
     */
    @Override
    public boolean isSearchTextDisplayed(String searchText) {

        DriverConfig.setLogString("Given text " + searchText + " is displayed in grid", true);
        mediumWait();

        return isDisplayedByXpath(DriverConfig.getDriver(), "//td[contains(text(),'" + searchText
                + "')]", SHORT_TIMEOUT);
    }

    /**
     * Checks if is email search text displayed.
     * @param searchText the search text
     * @return true, if is email search text displayed
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#isEmailSearchTextDisplayed(java.lang.String)
     */
    @Override
    public boolean isEmailSearchTextDisplayed(String searchText) {

        DriverConfig.setLogString("Given text " + searchText + " is displayed in grid", true);
        mediumWait();

        return isDisplayedByXpath(DriverConfig.getDriver(), "//td/a[contains(text(),'" + searchText
                + "')]", SHORT_TIMEOUT);
    }

    /**
     * Gets the ecp.
     * @return the ecp
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#getEcp()
     */
    @Override
    public String getEcp() {

        WebElement ecpText = DriverConfig.getDriver().findElement(By.id("ecpCore"));
        DriverConfig.setLogString("Ecp : " + ecpText.getText(), true);
        return ecpText.getText();
    }

    /**
     * Gets the location name.
     * @return the location name
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#getLocationName()
     */
    @Override
    public String getLocationName() {

        WebElement locName = DriverConfig.getDriver().findElement(By.id("locationNam"));
        DriverConfig.setLogString("Location Name : " + locName.getText(), true);
        return null;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.insite.page.SupportLookUp#isLocationNameDisplayed()
     */
    @Override
    public boolean isLocationNameDisplayed() {
        
        DriverConfig.setLogString("Check Location Name is displayed ", true);
        getLocationName();
        return isDisplayedById(DriverConfig.getDriver(), "locationNam", SHORT_TIMEOUT);
    }

}
