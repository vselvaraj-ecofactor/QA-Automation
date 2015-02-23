/*
 * TstatControlUIPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import java.util.List;

import com.ecofactor.qa.automation.newapp.page.impl.TstatControlUIPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface TstatControlUIPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = TstatControlUIPageImpl.class)
public interface TstatControlUIPage extends BasePage {

    /**
     * Checks if is cool background.
     * @return true, if is cool background
     */
    boolean isCoolBackground();

    /**
     * Checks if is heat background.
     * @return true, if is heat background
     */
    boolean isHeatBackground();

    /**
     * Checks if is off background.
     * @return true, if is off background
     */
    boolean isOffBackground();

    /**
     * Checks if is up arrow btn red.
     * @return true, if is up arrow btn red
     */
    boolean isUpArrowBtnRed();

    /**
     * Checks if is down arrow btn blue.
     * @return true, if is down arrow btn blue
     */
    boolean isdownArrowBtnBlue();

    /**
     * Checks if is fan on.
     * @return true, if is fan on
     */
    boolean isFanOn();

    /**
     * Gets the current temperature.
     * @return the current temperature
     */
    String getCurrentTemperature();

    /**
     * Gets the target temperature.
     * @return the target temperature
     */
    String getTargetTemperature();

    /**
     * Gets the current temperature.
     * @return the current temperature
     */
    String getCurrentMode();

    /**
     * Gets the available modes.
     * @return the available modes
     */
    List<String> getAvailableModes();

    /**
     * Gets the opacity value for controls.
     * @return the opacity value for controls
     */
    Double getOpacityValueForControls();

    /**
     * Gets the opacityvalues for buttons.
     * @return the opacityvalues for buttons
     */
    Double getOpacityvalueForButtons();

    /**
     * Gets the rgb for hvac selected button.
     * @return the rgb for hvac selected button
     */
    String getRgbForHvacSelectedButton();

    /**
     * Gets the rgb for fan selected button.
     * @return the rgb for fan selected button
     */
    String getRgbForFanSelectedButton();

    /**
     * Gets the rgb for seperator.
     * @return the rgb for seperator
     */
    String getRgbForSeperator();

    /**
     * Checks if is savings energy link displayed.
     * @return true, if is savings energy link displayed
     */
    boolean isSavingsEnergyLinkDisplayed();
}
