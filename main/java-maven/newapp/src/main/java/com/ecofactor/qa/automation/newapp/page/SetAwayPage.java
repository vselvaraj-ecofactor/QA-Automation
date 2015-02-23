/*
 * SetAwayPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import java.util.Calendar;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.impl.SetAwayPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface AwayPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = SetAwayPageImpl.class)
public interface SetAwayPage extends BasePage {

    /**
     * Click am back.
     */
    void clickAmBack();

    /**
     * Display actual set away info.
     */
    void displayActualSetAwayInfo();

    /**
     * Wait for error message.
     * @param expectedMessage the expected message
     * @param timout the timout
     * @return true, if successful
     * @throws Exception the exception
     */
    boolean waitForErrorMessage(final String expectedMessage, final int timout);

    /**
     * Manually close Away Toast Error message.
     */
    void clickCloseToastError();

    /**
     * Gets the actual away settings.
     * @return the actual away settings
     */
    String[] getActualAwaySettings();

    /**
     * Checks whether the Away Date is valid or not
     * @return the validity status of Away Date
     */
    boolean isDateValidForAway();

    /**
     * Set Away After waiting for 5 minutes idle
     * @return boolean status of Set Away
     */
    boolean waitAndSetAway();

    /**
     * Close away settings popup.
     */
    void closeAwaySettingsPopup();

    /**
     * Gets the default time to set away
     * @return String default away time
     */
    String getDefaultAwayTime();

    /**
     * Click away parameter.
     * @param param the param
     */
    boolean clickAwayParamPicker(SetAwayParams param);

    /**
     * Sets the away param picker.
     * @param setAwayParams the set away params
     * @param value the value
     * @return true, if successful
     */
    boolean setAwayParamPicker(final SetAwayParams setAwayParams, final int expectedValue);

    /**
     * Sets the away date.
     * @param reqDateTime the req date time
     * @return true, if successful
     */
    boolean setAwayDate(Calendar reqDateTime);

    /**
     * Sets the away time.
     * @param reqDateTime the req date time
     * @return true, if successful
     */
    boolean setAwayTime(Calendar reqDateTime);

    /**
     * Click set away button.
     * @return
     */
    boolean clickSetAwayButton();

    /**
     * Checks if is date picker displayed.
     * @return true, if is date picker displayed
     */
    boolean isDatePickerDisplayed();

    /**
     * Checks if is time picker displayed.
     * @return true, if is time picker displayed
     */
    boolean isTimePickerDisplayed();

    /**
     * Gets the end date.
     * @return the end date
     */
    String getEndDate();

    /**
     * Gets the end time.
     * @return the end time
     */
    String getEndTime();

    /**
     * Click Submit button.
     */
    void clickSetButton();

    /**
     * Click away settings in Menu Page.
     */
    void clickAwaySettings();

    /**
     * verify whether set away page loaded from Settings page.
     * @return true, if set away page loaded from settings page.
     */
    boolean isSetAwayPageLoaded();

    /**
     * Alert message.
     * @return true, if successful
     */
    boolean alertMessage();
}
