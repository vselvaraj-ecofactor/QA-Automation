/*
 * SetAwayPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.SetAwayPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class SetAwayPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SetAwayPageImpl extends AbstractSetAwayParameters implements SetAwayPage {

    /** The Constant TSTAT_POPUP. */
    private static final String TSTAT_POPUP = "thermostatPopupContainer";

    /** The Constant TSTAT_POPUP_BTN. */
    private static final String TSTAT_POPUP_BTN = "thermostatPopupButton";

    /** The Constant TSTAT_POPUP_LABEL. */
    private static final String TSTAT_POPUP_LABEL = "thermostatPopupLabel";

    /** The Constant CLOSE_TOAST_BTN. */
    private static final String CLOSE_TOAST_BTN = "div.closeButton";

    /** The Constant CURRENT_VALUE. */
    private static final String CURRENT_VALUE = "div.dw-li.dw-v.dw-sel";

    /** The Constant DATA_VALUE. */
    private static final String DATA_VALUE = "data-val";

    /** The Constant AWAY_SETTINGS. */
    private static final String AWAY_SETTINGS = ".away_settings.clickable.item.grey_arrow";

    /** The Constant ALERT_MESSAGE. */
    @SuppressWarnings("unused")
    private static final String ALERT_MESSAGE = "Please turn off away mode to see away settings for your home.";

    @Inject
    protected ThermostatPageUI thPageUI;

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        setLogString("Cleanup in Away Page.", true, CustomLogLevel.HIGH);
    }

    /**
     * Checks if is date picker displayed.
     * @return true, if is date picker displayed
     */
    @Override
    public boolean isDatePickerDisplayed() {

        final List<WebElement> dateTimePickers = getElements(getDriver(),
                By.cssSelector("div.setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);

        setLogString("Check if date picker is displayed.", true, CustomLogLevel.MEDIUM);
        return dateTimePickers.get(0).isDisplayed();
    }

    /**
     * Gets the end date.
     * @return the end date
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#getEndDate()
     */
    @Override
    public String getEndDate() {

        final List<WebElement> dateTimePickers = getElements(getDriver(),
                By.cssSelector("div.setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);

        setLogString("Check if date picker is displayed.", true, CustomLogLevel.MEDIUM);
        return dateTimePickers.get(0).getText();
    }

    /**
     * Checks if is time picker displayed.
     * @return true, if is time picker displayed
     */
    @Override
    public boolean isTimePickerDisplayed() {

        final List<WebElement> dateTimePickers = getElements(getDriver(),
                By.cssSelector("div.setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);

        setLogString("Check if time picker is displayed.", true, CustomLogLevel.MEDIUM);
        return dateTimePickers.get(1).isDisplayed();
    }

    /**
     * Gets the end time.
     * @return the end time
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#getEndTime()
     */
    @Override
    public String getEndTime() {

        final List<WebElement> dateTimePickers = getElements(getDriver(),
                By.cssSelector("div.setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);

        setLogString("Check if time picker is displayed.", true, CustomLogLevel.MEDIUM);
        return dateTimePickers.get(1).getText();
    }

    /**
     * Checks if is sets the point picker displayed.
     * @return true, if is sets the point picker displayed
     */
    public boolean isSetPointPickerDisplayed() {

        return isDisplayed(getDriver(), By.className("awaySetpointPicker"), ATOMIC_TIMEOUT);
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        setLogString("Verify if Away Page is loaded.", true, CustomLogLevel.MEDIUM);
        return isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(TSTAT_POPUP), ATOMIC_TIMEOUT);
    }

    /**
     * Click am back.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#clickAmBack()
     */
    @Override
    public void clickAmBack() {

        setLogString("Click I'm back button.", true, CustomLogLevel.LOW);
        final WebElement iAmBackBtnElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(TSTAT_POPUP_BTN), ATOMIC_TIMEOUT);
        getAction().click(iAmBackBtnElement);
        getAction().rejectAlert();
    }

    /**
     * Display away info.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#displayActualSetAwayInfo
     */
    @Override
    public void displayActualSetAwayInfo() {

        final String[] awayInfo = getActualAwaySettings();
        setLogString("Away triggered successfully. It will be active until DATE : " + awayInfo[1]
                + "  " + "TIME : " + awayInfo[3], true, CustomLogLevel.LOW);
    }

    /**
     * Gets the away settings.
     * @return the away settings
     */
    @Override
    public String[] getActualAwaySettings() {

        final WebElement awayInfoText = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(TSTAT_POPUP_LABEL), TINY_TIMEOUT);
        final String[] awayInfo = awayInfoText.getText().split(" ");

        return awayInfo;
    }

    /**
     * Click close toast error.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#clickCloseToastError()
     */
    @Override
    public void clickCloseToastError() {

        setLogString("Close Away Toast Error by Clicking on 'X'.", true, CustomLogLevel.MEDIUM);
        final WebElement closeToastErrBtn = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(CLOSE_TOAST_BTN), ATOMIC_TIMEOUT);
        getAction().click(closeToastErrBtn);
    }

    /**
     * Wait for error message.
     * @param expectedMessage the expected message
     * @param timout the timout
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#waitForErrorMessage(java.lang.String,
     *      int)
     */
    @Override
    public boolean waitForErrorMessage(final String expectedMessage, final int timout) {

        final Calendar endTime = DateUtil.getUTCCalendar();
        int secCounter = 0;
        endTime.add(Calendar.MINUTE, timout);
        do {
            WaitUtil.oneSec();
            secCounter++;
            final String errorMessage = getToastErrorMessage();
            if ((errorMessage != null) && errorMessage.contains(expectedMessage)) {
                setLogString("'" + expectedMessage + "' Error Message Appeared after " + secCounter
                        + " Seconds.", true);
                return true;
            }
        } while (DateUtil.getUTCCalendar().before(endTime));

        return false;
    }

    /**
     * Checks whether the Away Date is valid or not.
     * @return the validity status of Away Date
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#isDateValidForAway
     */
    @Override
    public boolean isDateValidForAway() {

        Date currentDate = null;
        Date newDate = null;

        final WebElement dateTimePickerButton = getElement(getDriver(),
                By.cssSelector(".setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);

        String currentDateString = dateTimePickerButton.getText();
        currentDateString = currentDateString.substring(currentDateString.indexOf("-") + 2);

        try {
            currentDate = new SimpleDateFormat("MMM dd, yyyy").parse(currentDateString);
        } catch (Exception ex) {
            setLogString("Exception:" + ex, true);
        }

        setLogString("Value:" + currentDate, true);
        dateTimePickerButton.click();

        setLogString(
                "Set Date Scroll Control Displayed:"
                        + isDisplayed(getDriver(), By.cssSelector("a.dwb-e.dwwb.dwwbm"),
                                TINY_TIMEOUT), true);

        setLogString("Trying to set a past date for Away", true);
        final WebElement dateScroll = getElement(getDriver(),
                By.cssSelector(".dwwl1 > a:nth-child(2)"), TINY_TIMEOUT);
        dateScroll.click();

        WaitUtil.tinyWait();

        setLogString("Set the New Date Value", true);
        final WebElement setElement = getElement(getDriver(), By.cssSelector("a.dwb.dwb0.dwb-e"),
                TINY_TIMEOUT);
        getAction().click(setElement);

        String newDateString = dateTimePickerButton.getText();
        newDateString = newDateString.substring(newDateString.indexOf("-") + 2);

        WaitUtil.tinyWait();

        try {
            newDate = new SimpleDateFormat("MMM dd, yyyy").parse(newDateString);
        } catch (Exception ex) {
            setLogString("Exception:" + ex, true);
        }

        setLogString("New Value:" + newDate, true);

        if (currentDate.compareTo(newDate) < 0 || currentDate.compareTo(newDate) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set Away After waiting for 5 minutes idle.
     * @return boolean status of Set Away
     */
    @Override
    public boolean waitAndSetAway() {

        setLogString("Waiting for 5 minutes", true);
        WaitUtil.waitUntil(300000);

        setLogString("Set Away after 5 minutes wait", true);
        final WebElement setButton = getElement(getDriver(),
                By.cssSelector("input.ctaButton:nth-child(2)"), SHORT_TIMEOUT);
        getAction().click(setButton);
        getAction().rejectAlert();
        setLogString(
                "Toast Display:"
                        + isDisplayed(getDriver(), By.cssSelector("div.view_toast_container"),
                                TINY_TIMEOUT), true);

        if (isDisplayed(getDriver(), By.cssSelector("div.view_toast_container"), TINY_TIMEOUT))
            return false;
        else
            return true;
    }

    /**
     * Close away settings popup.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#closeAwaySettingsPopup()
     */
    @Override
    public void closeAwaySettingsPopup() {

        setLogString("Click cancel button on away settings popup.", true);
        final WebElement cancelButton = getElement(getDriver(),
                By.cssSelector("input.ctaButton.setAwayCancelButton"), TINY_TIMEOUT);
        getAction().click(cancelButton);
    }

    /**
     * Click set away button.
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#clickSetAwayButton()
     */
    @Override
    public boolean clickSetAwayButton() {

        WaitUtil.tinyWait();
        setLogString("Click set button on away settings popup.", true);
        getAction().rejectAlert();
        final WebElement setButton = getElement(getDriver(),
                By.cssSelector("input.ctaButton.setAwaySubmitButton"), TINY_TIMEOUT);
        WaitUtil.oneSec();
        getAction().click(setButton);
        getAction().rejectAlert();

        return isDisplayed(getDriver(), By.cssSelector("div.view_toast_container"), TINY_TIMEOUT);
    }

    /**
     * Click away parameter.
     * @param param the param
     * @return true, if successful
     */
    @Override
    public boolean clickAwayParamPicker(final SetAwayParams param) {

        final List<WebElement> dateAndTimePickers = getElements(getDriver(),
                By.cssSelector("div.setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);
        WebElement awayParamElement = null;
        if (param.equals(SetAwayParams.Days)) {
            awayParamElement = dateAndTimePickers.get(0);
        } else if (param.equals(SetAwayParams.Hours)) {
            awayParamElement = dateAndTimePickers.get(1);
        } else {
            awayParamElement = getElement(getDriver(), By.cssSelector("input.awaySetpointPicker"),
                    TINY_TIMEOUT);
        }
        getAction().click(awayParamElement);

        return isDisplayed(getDriver(), By.cssSelector("div.dw-li.dw-v.dw-sel"), TINY_TIMEOUT);
    }

    /**
     * Sets the away param picker.
     * @param setAwayParams the set away params
     * @param expectedValue the expected value
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#setAwayParamPicker(com.ecofactor.qa.automation.newapp.enums.SetAwayParams,
     *      int)
     */
    @Override
    public boolean setAwayParamPicker(final SetAwayParams setAwayParams, final int expectedValue) {

        setLogString("Set " + setAwayParams + " as :" + expectedValue, true);

        Assert.assertTrue(clickAwayParamPicker(setAwayParams), setAwayParams
                + " picker not displayed.");

        final WebElement currentReadingElement = getElement(getDriver(),
                By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        final int currentReadingValue = Integer.parseInt(currentReadingElement
                .getAttribute(DATA_VALUE));

        final int setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;

        return changeAwayParamValue(setAwayParams, setPointValue).equals(expectedValue);

    }

    /**
     * Sets the away date.
     * @param reqDateTime the new away date
     * @return true, if successful
     */
    @Override
    public boolean setAwayDate(final Calendar reqDateTime) {

        SimpleDateFormat enddateFormat = new SimpleDateFormat("E - MMM dd, YYYY");
        enddateFormat.setTimeZone(enddateFormat.getTimeZone());
        String enddate = enddateFormat.format(reqDateTime.getTime());
        setLogString("Set away date as :" + enddate, true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(clickAwayParamPicker(SetAwayParams.Days), SetAwayParams.Days
                + " picker not displayed.");

        final WebElement dateContainerElement = getElement(getDriver(),
                By.cssSelector("div.dwwl.dwwl1"), TINY_TIMEOUT);
        final WebElement monthContainerElement = getElement(getDriver(),
                By.cssSelector("div.dwwl.dwwl0"), TINY_TIMEOUT);
        final WebElement yearContainerElement = getElement(getDriver(),
                By.cssSelector("div.dwwl.dwwl2"), TINY_TIMEOUT);

        boolean dateUpdated = true;

        final WebElement dateCurrentReadingElement = getElementBySubElement(getDriver(),
                dateContainerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        int currentReadingValue = Integer.parseInt(dateCurrentReadingElement
                .getAttribute(DATA_VALUE));
        int expectedValue = reqDateTime.get(Calendar.DATE);
        int setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;
        dateUpdated = dateUpdated
                && changeRequiredParameter(dateContainerElement, setPointValue,
                        reqDateTime.get(Calendar.DATE));

        final WebElement monthCurrentReadingElement = getElementBySubElement(getDriver(),
                monthContainerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        currentReadingValue = Integer.parseInt(monthCurrentReadingElement.getAttribute(DATA_VALUE));
        expectedValue = reqDateTime.get(Calendar.MONTH);
        setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;
        dateUpdated = dateUpdated
                && changeRequiredParameter(monthContainerElement, setPointValue,
                        reqDateTime.get(Calendar.MONTH));

        final WebElement yearCurrentReadingElement = getElementBySubElement(getDriver(),
                yearContainerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        currentReadingValue = Integer.parseInt(yearCurrentReadingElement.getAttribute(DATA_VALUE));
        expectedValue = reqDateTime.get(Calendar.YEAR);
        setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;
        dateUpdated = dateUpdated
                && changeRequiredParameter(yearContainerElement, setPointValue,
                        reqDateTime.get(Calendar.YEAR));

        saveAwaySettings();

        return dateUpdated;
    }

    /**
     * Sets the away time.
     * @param reqDateTime the new away time
     * @return true, if successful
     */
    @Override
    public boolean setAwayTime(final Calendar reqDateTime) {

        SimpleDateFormat endtimeFormat = new SimpleDateFormat("HH:MM a Z");
        endtimeFormat.setTimeZone(endtimeFormat.getTimeZone());
        String endtime = endtimeFormat.format(reqDateTime.getTime());
        setLogString("Set away time as :" + endtime, true, CustomLogLevel.MEDIUM);

        boolean timeUpdated = true;
        Assert.assertTrue(clickAwayParamPicker(SetAwayParams.Hours), SetAwayParams.Hours
                + " picker not displayed.");

        final WebElement hourContainerElement = getElement(getDriver(),
                By.cssSelector("div.dwwl.dwwl0"), TINY_TIMEOUT);
        final WebElement minuteContainerElement = getElement(getDriver(),
                By.cssSelector("div.dwwl.dwwl1"), TINY_TIMEOUT);
        final WebElement ampmContainerElement = getElement(getDriver(),
                By.cssSelector("div.dwwl.dwwl2"), TINY_TIMEOUT);

        final WebElement hourCurrentReadingElement = getElementBySubElement(getDriver(),
                hourContainerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        int currentReadingValue = Integer.parseInt(hourCurrentReadingElement
                .getAttribute(DATA_VALUE));
        int expectedValue = reqDateTime.get(Calendar.HOUR);
        int setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;
        timeUpdated = timeUpdated
                && changeRequiredParameter(hourContainerElement, setPointValue,
                        reqDateTime.get(Calendar.HOUR));

        final WebElement minuteCurrentReadingElement = getElementBySubElement(getDriver(),
                minuteContainerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        currentReadingValue = Integer
                .parseInt(minuteCurrentReadingElement.getAttribute(DATA_VALUE));
        expectedValue = reqDateTime.get(Calendar.MINUTE);
        setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;
        timeUpdated = timeUpdated
                && changeRequiredParameter(minuteContainerElement, setPointValue,
                        reqDateTime.get(Calendar.MINUTE));

        final WebElement ampmCurrentReadingElement = getElementBySubElement(getDriver(),
                ampmContainerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        currentReadingValue = Integer.parseInt(ampmCurrentReadingElement.getAttribute(DATA_VALUE));
        expectedValue = reqDateTime.get(Calendar.AM_PM);
        setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;
        timeUpdated = timeUpdated
                && changeRequiredParameter(ampmContainerElement, setPointValue,
                        reqDateTime.get(Calendar.AM_PM));

        saveAwaySettings();
        return timeUpdated;
    }

    /**
     * Change required parameter.
     * @param containerElement the container element
     * @param noOfSetPoints the no of set points
     * @param expectedValue the expected value
     * @return true, if successful
     */
    private boolean changeRequiredParameter(final WebElement containerElement,
            final int noOfSetPoints, final int expectedValue) {

        final int setPoints = Math.abs(noOfSetPoints);
        final WebElement upOrDownArrow = expectedValue > 0 ? getElementBySubElement(getDriver(),
                containerElement, By.cssSelector("a.dwb-e.dwwb.dwwbm"), SHORT_TIMEOUT)
                : getElementBySubElement(getDriver(), containerElement,
                        By.cssSelector("a.dwb-e.dwwb.dwwbp"), SHORT_TIMEOUT);

        for (int count = 0; count < setPoints; count++) {
            getAction().click(upOrDownArrow);
        }

        final WebElement currentReadingElement = getElementBySubElement(getDriver(),
                containerElement, By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        final Integer currentReadingValue = Integer.parseInt(currentReadingElement
                .getAttribute(DATA_VALUE));

        return currentReadingValue.equals(expectedValue);
    }

    /**
     * Gets the default time to set away.
     * @return String default away time
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#getDefaultAwayTime
     */
    @Override
    public String getDefaultAwayTime() {

        final List<WebElement> dateTimePickers = getElements(getDriver(),
                By.cssSelector(".setAwayPicker.awayEndDateTimePicker"), TINY_TIMEOUT);

        final String currentTimeString = dateTimePickers.get(1).getText();

        return currentTimeString;
    }

    /**
     * Click Set button in away popup.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#clickSetButton()
     */
    @Override
    public void clickSetButton() {

        setLogString("Click Set Button", true, CustomLogLevel.MEDIUM);
        final WebElement footerElement = getElement(getDriver(),
                By.cssSelector(".footerAwayPicker"), SHORT_TIMEOUT);
        final WebElement awaySettingsPopUp = getElementBySubElement(getDriver(), footerElement,
                By.cssSelector(".ctaButton.setAwaySubmitButton"), TINY_TIMEOUT);
        getAction().click(awaySettingsPopUp);
        getAction().rejectAlert();
    }

    /**
     * Click Set Away from Settigns page.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#clickAwaySettings()
     */
    @Override
    public void clickAwaySettings() {

        WaitUtil.tinyWait();
        setLogString("Click SetAway in Settings", true, CustomLogLevel.MEDIUM);
        getAction().rejectAlert();
        final WebElement awaySettings = getElement(getDriver(), By.cssSelector(AWAY_SETTINGS),
                TINY_TIMEOUT);
        WaitUtil.oneSec();
        awaySettings.click();
        getAction().rejectAlert();
    }

    /**
     * verify whether set away page loaded from Settings page.
     * @return true, if set away page loaded from settings page.
     */
    @Override
    public boolean isSetAwayPageLoaded() {

        final boolean element = isDisplayed(getDriver(), By.cssSelector(AWAY_SETTINGS),
                TINY_TIMEOUT);
        return element;
    }

    /**
     * Alert message.
     * @return true, if successful.
     * @see com.ecofactor.qa.automation.newapp.page.SetAwayPage#alertMessage()
     */
    @Override
    public boolean alertMessage() {

        setLogString("Verify with alert Message", true, CustomLogLevel.MEDIUM);
        final boolean alertValue = isDisplayed(getDriver(),
                By.cssSelector(".settings_alert_label"), TINY_TIMEOUT);
        if (alertValue) {

            final WebElement alertMessage = getElement(getDriver(),
                    By.cssSelector(".settings_alert_label"), TINY_TIMEOUT);
            setLogString("Alert Message" + alertMessage.getText(), true, CustomLogLevel.MEDIUM);
        }
        return true;
    }

}
