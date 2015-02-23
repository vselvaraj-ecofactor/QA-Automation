/*
 * TstatControlOpsPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import java.util.Map;

import com.ecofactor.qa.automation.newapp.page.impl.TstatControlOpsPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface TstatControlOpsPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = TstatControlOpsPageImpl.class)
public interface TstatControlOpsPage extends BasePage {

    /**
     * Checks if is pop up opened.
     * @return true, if is pop up opened
     */
    boolean isPopUpOpened();

    /**
     * Change to heat.
     */
    void changeToHeat();

    /**
     * Change to cool.
     */
    void changeToCool();

    /**
     * Change to off.
     */
    void changeToOff();

    /**
     * Click mode heat.
     */
    void clickModeHeat();

    /**
     * Click mode off.
     */
    void clickModeOff();

    /**
     * Click mode cool.
     */
    void clickModeCool();

    /**
     * Decrease set point.
     * @param setPoint the new point change
     */
    void setPointChange(Integer setPoint);

    /**
     * Sets the point change.
     * @param setPoint the set point
     * @param expectedValue the expected value
     */
    void setPointChange(Integer setPoint, Integer expectedValue);

    /**
     * Click fan on.
     */
    void clickFanOn();

    /**
     * Click fan auto.
     */
    void clickFanAuto();

    /**
     * Close thermostat control.
     */
    void closeThermostatControl();

    /**
     * Check and update boundary.
     * @param requiredChange the required change
     */
    void checkAndUpdateBoundary(Integer requiredChange);

    /**
     * Click savings icon.
     */
    void clickSavingsIcon();

    /**
     * Gets the e eapi.
     * @param url the url
     * @param params the params
     * @param expectedStatus the expected status
     * @return the e eapi
     */
    String getEEapi(String url, Map<String, String> params, int expectedStatus);

    /**
     * verify either schedule message is display in thermostat control page.
     * @return true, if schedule message display.
     */
    boolean displayScheduleMessage();

    /**
     * Verify the color of Cool or heat label.
     * @param mode the mode.
     * @return true, if property color value and declared value is matched.
     */
    boolean labelColor(String mode);

    /**
     * verify color of separator line in cool or heat value.
     * @param mode the mode.
     * @return true, if property color value and declared value is matched.
     */
    boolean separatorLine(String mode);

    /**
     * Verify modes were enable in thermostat control popup.
     * @param mode the mode.
     */
    void verifyModesEnable(String mode);
}
