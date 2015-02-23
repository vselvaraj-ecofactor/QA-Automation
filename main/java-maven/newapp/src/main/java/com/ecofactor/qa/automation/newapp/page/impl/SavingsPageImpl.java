/*
 * SavingsPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.newapp.MobileConfig.SAVINGS_COLOR;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.MEDIUM_TIMEOUT;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.SHORT_TIMEOUT;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.TINY_TIMEOUT;
import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;
import static com.ecofactor.qa.automation.platform.util.Pageutil.executeScriptByClassName;
import static com.ecofactor.qa.automation.platform.util.Pageutil.getElement;
import static com.ecofactor.qa.automation.platform.util.Pageutil.getElementBySubElement;
import static com.ecofactor.qa.automation.platform.util.Pageutil.getElementsBySubElement;
import static com.ecofactor.qa.automation.platform.util.Pageutil.isDisplayed;
import static com.ecofactor.qa.automation.platform.util.Pageutil.isNotDisplayed;
import static com.ecofactor.qa.automation.util.WaitUtil.tinyWait;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.DateUtil;
import com.google.inject.Inject;

/**
 * The Class SavingsPageImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 32836 $ $Date: 2014-11-21 13:34:05 +0530 (Fri, 21 Nov 2014) $
 */
public class SavingsPageImpl extends AbstractAuthenticationPageImpl implements SavingsPage {

    private static final String SAVINGS_CONTAINER = ".ViewSavingsManager.savings_manager";
    private static final String SAVINGS_DOLLARS = "total_savings_amount";
    private static final String SAVINGS_MONTH = "savings_carousel_caption";
    private static final String MENU_SAVINGS = "div.menuClick.light";
    private static final String SAVINGS_DOLLAR_AMOUNT = "total_savings_amount";
    private static final String BACKWARD_ICON = "savings_carousel_backward";
    private static final String FORWARD_ICON = "savings_carousel_forward";
    private static final String SAVINGS_HOURS = "savings_carousel_runtime_hours";
    private static final String SAVINGS_PERCENTAGE = "savings_carousel_runtime_percent";
    private static final String FOOTER_CONTAINER = "footer_container";
    private static final String LEFT_ARROW = "div.savings_carousel_backward.disabled";
    private static final String RIGHT_ARROW = "div.savings_carousel_forward.disabled";
    private static final String SAVINGS_LOCATION = "savings_location";
    private static final String SAVINGS_HEADER = "savings_header";
    private static final String LOCATION_NOT_INSTALLED = "Location is not installed";
    private static final String ERROR_MODEBOX = "modeMessage";
    private static final String MODEL_LABEL = "modeLabel";
    private static final String MODEL_DIALOG = "div.modeDialog.notInstalledModeBox";

    @Inject
    protected MobileConfig mobileConfig;

    /**
     * Clean up.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        setLogString("Cleanup in Savings Page.", true, CustomLogLevel.HIGH);
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        setLogString("Verify the savings Page is loaded", true, CustomLogLevel.LOW);
        return isDisplayed(getDriver(), By.cssSelector(SAVINGS_CONTAINER), TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.className(SAVINGS_DOLLARS), TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.cssSelector(MENU_SAVINGS), TINY_TIMEOUT);

    }

    /**
     * Gets the Total Savings.
     * @return savings
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getTotalSavings()
     */
    @Override
    public String getTotalSavings() {

        setLogString("Get total Savings Amount", true, CustomLogLevel.HIGH);
        final String savingsValue = getElement(getDriver(), By.className(SAVINGS_DOLLARS),
                TINY_TIMEOUT).getText();
        setLogString("Total Savings :" + savingsValue, true, CustomLogLevel.LOW);
        return savingsValue;
    }

    /**
     * Gets the current month.
     * @return the current month
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getCurrentMonth()
     */
    @Override
    public String getCurrentMonthAndYear() {

        setLogString("Get Month and year", true, CustomLogLevel.HIGH);
        final String currentMonth = getElement(getDriver(), By.className(SAVINGS_MONTH),
                TINY_TIMEOUT).getText();
        setLogString("Current Month and Year: " + currentMonth, true, CustomLogLevel.HIGH);
        return currentMonth.substring(currentMonth.indexOf("-") + 1, currentMonth.length()).trim();
    }

    /**
     * Click previous.
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#clickPrevious()
     */
    @Override
    public void clickPrevious() {

        setLogString("Click previous", true, CustomLogLevel.HIGH);
        final WebElement backwardIcon = getElement(getDriver(), By.className(BACKWARD_ICON),
                TINY_TIMEOUT);
        getAction().click(backwardIcon);
        getAction().rejectAlert();
    }

    /**
     * Click next.
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#clickNext()
     */
    @Override
    public void clickNext() {

        setLogString("Click Next", true, CustomLogLevel.HIGH);
        final WebElement forwardIcon = getElement(getDriver(), By.className(FORWARD_ICON),
                TINY_TIMEOUT);
        getAction().click(forwardIcon);
        getAction().rejectAlert();
    }

    /**
     * Gets the savings value.
     * @return the savings value
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getSavingsValue()
     */
    @Override
    public String getSavingsValue() {

        final String savingsValue = getElement(getDriver(), By.className(SAVINGS_DOLLAR_AMOUNT),
                TINY_TIMEOUT).getText();
        setLogString("Savings Amount :" + savingsValue, true, CustomLogLevel.HIGH);
        return savingsValue;
    }

    /**
     * Gets the savings hours.
     * @return the savings hours
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getSavingsHours()
     */
    @Override
    public String getSavingsHours() {

        final String savingsHrs = getElement(getDriver(), By.className(SAVINGS_HOURS), TINY_TIMEOUT)
                .getText();
        setLogString("Savings Hours:" + savingsHrs, true, CustomLogLevel.HIGH);
        return savingsHrs.substring(savingsHrs.indexOf("(") + 1, savingsHrs.indexOf("h"));
    }

    /**
     * Click menu.
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#clickMenu()
     */
    @Override
    public void clickMenu() {

        setLogString("Click Menu Icon in Savings", true);
        final boolean isMenuDisplayed = isDisplayed(getDriver(), By.cssSelector(MENU_SAVINGS),
                MEDIUM_TIMEOUT);
        if (isMenuDisplayed) {
            final WebElement menuElement = getElement(getDriver(), By.cssSelector(MENU_SAVINGS),
                    SHORT_TIMEOUT);
            getAction().click(menuElement);
            tinyWait();
            getAction().rejectAlert();
        }
    }

    /**
     * Gets the savings percentage.
     * @return the savings percentage
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getSavingsPercentage()
     */
    @Override
    public String getSavingsPercentage() {

        final String savingsPercent = getElement(getDriver(), By.className(SAVINGS_PERCENTAGE),
                TINY_TIMEOUT).getText();
        setLogString("Savings Percentage:" + savingsPercent + "%", true, CustomLogLevel.HIGH);
        return savingsPercent + "%";
    }

    /**
     * Checks if is footer displayed.
     * @return true, if is footer displayed
     */
    @Override
    public boolean isFooterDisplayed() {

        setLogString("Check footer is not displayed", true, CustomLogLevel.HIGH);
        return isNotDisplayed(getDriver(), By.className(FOOTER_CONTAINER), TINY_TIMEOUT);
    }

    /**
     * Display savings data.
     * @param month the month
     * @param year the year
     * @param isDisplayData the is display data
     * @throws ParseException the parse exception
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#displaySavingsData(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void goToMonth(String month, String year, boolean isDisplayData) throws ParseException {

        String savingaData = getCurrentMonthAndYear();
        final Date dateExp = DateUtil.parseDateWithTimezone("1 " + month + " " + year);
        Date dateAct = DateUtil.parseDateWithTimezone("1 " + savingaData.split(" ")[0] + " "
                + savingaData.split(" ")[1]);

        final Calendar expCal = Calendar.getInstance();
        expCal.setTime(dateExp);
        final Calendar actCal = Calendar.getInstance();
        actCal.setTime(dateAct);
        getTotalSavings();
        do {
            displaySavingsData(isDisplayData);
            if (expCal.compareTo(actCal) == 0) {
                return;
            }
            if (expCal.compareTo(actCal) < 0) {
                clickPrevious();
            } else {
                clickNext();
            }
            savingaData = getCurrentMonthAndYear();
            dateAct = DateUtil.parseDateWithTimezone("1 " + savingaData.split(" ")[0] + " "
                    + savingaData.split(" ")[1]);
            actCal.setTime(dateAct);
        } while (expCal.compareTo(actCal) != 0);
    }

    /**
     * Checks if is arrow not displayed.
     * @param arrow the arrow
     * @return true, if is arrow not displayed
     *         com.ecofactor.qa.automation.newapp.page.SavingsPage#isArrowNotDisplayed
     *         (java.lang.String)
     */
    @Override
    public boolean isArrowNotDisplayed(String arrow) {

        setLogString("Check " + arrow + " Arrow is not displayed", true, CustomLogLevel.HIGH);
        if (arrow.equalsIgnoreCase("Left")) {
            return isDisplayed(getDriver(), By.cssSelector(LEFT_ARROW), TINY_TIMEOUT);
        } else if (arrow.equalsIgnoreCase("Right")) {
            return isDisplayed(getDriver(), By.cssSelector(RIGHT_ARROW), TINY_TIMEOUT);
        }
        return false;
    }

    /**
     * Checks if is arrow displayed.
     * @param arrow the arrow
     * @return true, if is arrow displayed
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#isArrowDisplayed(java.lang.String)
     */
    @Override
    public boolean isArrowDisplayed(String arrow) {

        setLogString("Check " + arrow + " Arrow is displayed", true, CustomLogLevel.HIGH);
        if (arrow.equalsIgnoreCase("Left")) {
            return isDisplayed(getDriver(), By.className(BACKWARD_ICON), TINY_TIMEOUT);
        } else if (arrow.equalsIgnoreCase("Right")) {
            return isDisplayed(getDriver(), By.className(FORWARD_ICON), TINY_TIMEOUT);
        }
        return false;
    }

    /**
     * Checks if is learn more displayed.
     * @return true, if is learn more displayed
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#isLearnMoreDisplayed()
     */
    @Override
    public boolean isLearnMoreDisplayed() {

        setLogString("Check if Learn More is displayed", true, CustomLogLevel.HIGH);
        return isDisplayed(getDriver(), By.cssSelector(".help_icon.clickable"), TINY_TIMEOUT);
    }

    /**
     * Display savings data.
     * @param isDisplayData the is display data
     */
    private void displaySavingsData(boolean isDisplayData) {

        if (isDisplayData) {
            getSavingsValue();
            getSavingsHours();
        }
        getSavingsPercentage();
    }

    /**
     * Gets the location name.
     * @return the location name
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getLocationName()
     */
    @Override
    public String getLocationName() {

        final String locationName = getElement(getDriver(), By.className(SAVINGS_LOCATION),
                TINY_TIMEOUT).getText();
        setLogString("Savings Location Name :" + locationName, true);
        return locationName;
    }

    /**
     * Gets the savings header color.
     * @return the savings header color
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getSavingsHeaderColor()
     */
    @Override
    public boolean getSavingsHeaderColor() {

        setLogString("Verify if savings header has css value : " + mobileConfig.get(SAVINGS_COLOR),
                true, CustomLogLevel.HIGH);
        final Object val = executeScriptByClassName(SAVINGS_HEADER, "background-color", getDriver());

        setLogString("Savings header has css value : " + val, true, CustomLogLevel.MEDIUM);
        return val.toString().contains(mobileConfig.get(SAVINGS_COLOR));
    }

    /**
     * Gets the total savings color.
     * @return the total savings color
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getTotalSavingsColor()
     */
    @Override
    public boolean getTotalSavingsColor() {

        setLogString("Verify if total savings has css value : " + mobileConfig.get(SAVINGS_COLOR),
                true, CustomLogLevel.HIGH);
        final Object val = executeScriptByClassName(SAVINGS_DOLLARS, "color", getDriver());
        setLogString("Total Savings has css value : " + val, true, CustomLogLevel.MEDIUM);
        return val.toString().contains(mobileConfig.get(SAVINGS_COLOR));
    }

    /**
     * Gets the savings arrow color.
     * @return the savings arrow color
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#getSavingsArrowColor()
     */
    @Override
    public boolean getSavingsArrowColor() {

        setLogString("Verify if savings arrow has css value : " + mobileConfig.get(SAVINGS_COLOR),
                true, CustomLogLevel.HIGH);
        final Object val = executeScriptByClassName(BACKWARD_ICON, "color", getDriver());
        setLogString("Savings arrow has css value : " + val, true, CustomLogLevel.MEDIUM);
        return val.toString().contains(mobileConfig.get(SAVINGS_COLOR));
    }

    /**
     * Checks if is location not installed.
     * @return true, if is location not installed
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#isLocationNotInstalled()
     */
    @Override
    public boolean isLocationNotInstalled() {

        setLogString("Check if location is not installed.", true, CustomLogLevel.HIGH);
        final String thStatusMsg = getTstatStatusMessage();
        return thStatusMsg != null && thStatusMsg.contains(LOCATION_NOT_INSTALLED);

    }

    /**
     * Gets the tstat status message.
     * @return the tstat status message
     */
    private String getTstatStatusMessage() {

        String thStatusMsg = null;
        WebElement modeDialog = getElement(getDriver(), By.cssSelector(MODEL_DIALOG), TINY_TIMEOUT);
        if (modeDialog.isDisplayed()) {
            WebElement modeMessage = getElementBySubElement(getDriver(), modeDialog,
                    By.className(ERROR_MODEBOX), TINY_TIMEOUT);
            thStatusMsg = getElementBySubElement(getDriver(), modeMessage,
                    By.className(MODEL_LABEL), TINY_TIMEOUT).getText();
            setLogString("Location status message:" + thStatusMsg, true, CustomLogLevel.HIGH);
        }
        return thStatusMsg;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#isSavingsDollarVertical()
     */
    @Override
    public boolean isSavingsDollarVertical() {

        final WebElement savingsDollarElement = getElement(getDriver(),
                By.className("fusioncharts-yaxis-0-gridlabels"), TINY_TIMEOUT);
        final List<WebElement> savingsDollar = getElementsBySubElement(getDriver(),
                savingsDollarElement, By.tagName("text"), TINY_TIMEOUT);
        boolean isVertical = false;
        for (WebElement webElement : savingsDollar) {
            setLogString(
                    "SavingsDollar :"
                            + getElementBySubElement(getDriver(), webElement, By.tagName("tspan"),
                                    TINY_TIMEOUT).getText(), true, CustomLogLevel.HIGH);
            isVertical = Integer.valueOf(webElement.getLocation().getX()).equals(
                    Integer.valueOf(savingsDollar.get(1).getLocation().getX()));
            if (!isVertical) {
                break;
            }
        }
        setLogString("SavingsDollar isVertical :" + isVertical, true, CustomLogLevel.LOW);
        return isVertical;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.SavingsPage#isSavingsMonthHorizontal()
     */
    @Override
    public boolean isSavingsMonthHorizontal() {

        final WebElement savingsMonthElement = getElement(getDriver(),
                By.className("fusioncharts-xaxis-0-gridlabels"), TINY_TIMEOUT);
        final List<WebElement> savingsMonth = getElementsBySubElement(getDriver(),
                savingsMonthElement, By.tagName("text"), TINY_TIMEOUT);
        boolean isHorizontal = false;
        for (WebElement webElement : savingsMonth) {
            setLogString(
                    "SavingsMonth :"
                            + getElementBySubElement(getDriver(), webElement, By.tagName("tspan"),
                                    TINY_TIMEOUT).getText(), true, CustomLogLevel.HIGH);
            isHorizontal = Integer.valueOf(webElement.getLocation().getY()).equals(
                    Integer.valueOf(savingsMonth.get(1).getLocation().getY()));
            if (!isHorizontal) {
                break;
            }
        }
        setLogString("SavingsMonth isHorizontal :" + isHorizontal, true, CustomLogLevel.LOW);
        return isHorizontal;
    }
}
