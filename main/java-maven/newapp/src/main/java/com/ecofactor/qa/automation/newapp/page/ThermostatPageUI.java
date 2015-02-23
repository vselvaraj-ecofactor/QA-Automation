/*
 * ThermostatPageUI.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import org.joda.time.LocalTime;

import com.ecofactor.qa.automation.newapp.page.impl.ThermostatPageUIImpl;
import com.google.inject.ImplementedBy;

// TODO: Auto-generated Javadoc
/**
 * The Interface ThermostatPageUI.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = ThermostatPageUIImpl.class)
public interface ThermostatPageUI extends BasePage {

    /**
     * Checks if is heater on.
     * @return true, if is heater on
     */
    boolean isHeaterOn();

    /**
     * Checks if is ac on.
     * @return true, if is ac on
     */
    boolean isAcOn();

    /**
     * Checks if is mode off.
     * @return true, if is mode off
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isModeOff()
     */
    boolean isModeOff();

    /**
     * Gets the no of locations.
     * @return the no of locations
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getNoOfLocations()
     */
    Integer getNoOfLocations();

    /**
     * Checks if is savings displayed.
     * @return true, if is savings displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isSavingsDisplayed()
     */
    boolean isSavingsDisplayed();

    /**
     * Checks if is home icon displayed.
     * @return true, if is home icon displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isHomeIconDisplayed()
     */
    boolean isHomeIconDisplayed();

    /**
     * Checks if is menu icon displayed.
     * @return true, if is logout icon displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isLogoutIconDisplayed()
     */
    boolean isMenuIconDisplayed();

    /**
     * Checks if is target temp displayed.
     * @return true, if is target temp displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isTargetTempDisplayed()
     */
    boolean isTargetTempDisplayed();

    /**
     * Checks if is idle state.
     * @return true, if is idle state
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isIdleState()
     */
    boolean isIdleState();

    /**
     * Checks if is long dashes not displayed.
     * @return true, if is long dashes not displayed
     */
    boolean isLongDashesNotDisplayed();

    /**
     * Checks if is thermostat not installed.
     * @return true, if is thermostat not installed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isThermostatNotInstalled()
     */
    boolean isThermostatNotInstalled();

    /**
     * Checks if is thermostat not connected.
     * @return true, if is thermostat not connected
     */
    boolean isThermostatNotConnected();

    /**
     * Checks if is thermostat offline.
     * @return true, if is thermostat offline
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isThermostatOffline()
     */
    boolean isThermostatOffline();

    /**
     * Checks if is target dashed line displayed.
     * @return true, if is target dashed line displayed
     */
    boolean isTargetDashedLineDisplayed();

    /**
     * Checks if is current dashed line displayed.
     * @return true, if is current dashed line displayed
     */
    boolean isCurrentDashedLineDisplayed();

    /**
     * Checks if is under savings energy.
     * @return true, if is under savings energy
     */
    boolean isUnderSavingsEnergy();

    /**
     * Checks if is savings energy link displayed.
     * @return true, if is savings energy link displayed
     */
    boolean isSavingsEnergyLinkDisplayed();

    /**
     * Checks if is savings energy link clickable.
     * @return true, if is savings energy link clickable
     */
    boolean isSavingsEnergyLinkClickable();

    /**
     * Checks if is schedule temp displayed.
     * @return true, if is schedule temp displayed
     */
    boolean isScheduleTempDisplayed();

    /**
     * Gets the page load time on login.
     * @return the page load time on login
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getPageLoadTimeOnLogin()
     */
    LocalTime getPageLoadTimeOnLogin();

    /**
     * Sets the page load time on login.
     * @param endTime the new page load time on login
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#setPageLoadTimeOnLogin(org.joda.time.LocalTime)
     */
    void setPageLoadTimeOnLogin(LocalTime endTime);

    /**
     * Gets the no of thermostats.
     * @return the no of thermostats
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getNoOfThermostats()
     */
    Integer getNoOfThermostats();

    /**
     * Gets the current location name.
     * @return the current location name
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getCurrentLocationName()
     */
    String getCurrentLocationName();

    /**
     * Gets the current thermostat name.
     * @return the current thermostat name
     */
    String getCurrentThermostatName();

    /**
     * Gets the current mode.
     * @return the current mode
     * @return, current mode
     */
    String getCurrentMode();

    /**
     * Gets the current temperature.
     * @return the current temperature
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getCurrentTemperature()
     */
    String getCurrentTemperature();

    /**
     * Gets the target temperature.
     * @return the target temperature
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getTargetTemperature()
     */
    String getTargetTemperature();

    /**
     * Checks if is target temperature changed.
     * @param targetTemp the target temp
     * @return true, if is target temperature changed
     */
    boolean isTargetTemperatureChanged(String targetTemp);

    /**
     * Current temperature drag.
     * @param change the change
     */
    void currentTemperatureDrag(final int change);

    /**
     * Gets the sliding temperature.
     * @return the sliding temperature
     */
    String getSlidingTemperature();

    /**
     * Checks if is target clickable.
     * @return true, if is target clickable
     */
    boolean isTargetClickable();

    /**
     * Checks if is learn more clickable.
     * @return true, if is learn more clickable
     */
    boolean isLearnMoreClickable();

    /**
     * Gets the temperature content by position.
     * @param position the position
     * @return the temperature content by position
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getTemperatureContentByPosition(java.lang.String)
     */
    public String getTemperatureContentByPosition(String position);

    /**
     * Checks if is temp position relative.
     * @param currentTemp the current temp
     * @param targetTemp the target temp
     * @return true, if is temp position relative
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isTempPositionRelative(int, int)
     */
    public boolean isTempPositionRelative(int currentTemp, int targetTemp);

    /**
     * Checks if is background color gradient.
     * @param mode the mode
     * @return true, if is background color gradient
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isBackgroundColorGradient(java.lang.String)
     */

    public boolean isBackgroundColorGradient(String mode);

    /**
     * Checks if is mode color reflected in screen.
     * @param mode the mode
     * @return true, if is mode color reflected in screen
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isModeColorReflectedInScreen(java.lang.String)
     */
    public boolean isModeColorReflectedInScreen(String mode);

    /**
     * Checks if is click learn more link redirects new window.
     * @return true, if is click learn more link redirects new window
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isClickLearnMoreLinkRedirectsNewWindow()
     */
    public boolean isClickLearnMoreLinkRedirectsNewWindow();

    /**
     * Gets the tstat status message.
     * @return the tstat status message
     */
    public String getOffModeMessage();

    /**
     * Checks if is current temp displayed.
     * @return true, if is current temp displayed
     */
    boolean isCurrentTempDisplayed();

    /**
     * Checks if is inside temp displayed.
     * @return true, if is inside temp displayed
     */
    boolean isInsideTempDisplayed();

    /**
     * Checks if is location switcher displayed.
     * @return true, if is location switcher displayed
     */
    public boolean isLocationSwitcherDisplayed();

    /**
     * Checks if is away settings pop up loaded.
     * @return true, if is away settings pop up loaded
     */
    boolean isAwaySettingsPopUpLoaded();

    /**
     * Checks if is off mode dialog displayed.
     * @return true, if is off mode dialog displayed
     */
    boolean isOffModeDialogDisplayed();

    /**
     * Checks if is not installed dialog displayed.
     * @return true, if is not installed dialog displayed
     */
    boolean isNotInstalledDialogDisplayed();

    /**
     * check weather the background image is displayed.
     * @param mode the mode.
     * @return true, if background image is displayed.
     */
    public boolean isBackgroundImageDisplayed(String mode);

    /**
     * Gets the savings amount.
     * @return the savings amount
     */
    String getSavingsAmount();

    /**
     * Check either thermostat name displayed in header.
     * @return true, if thermostat name displayed in header.
     */
    boolean isThermostatHeaderDisplayed();

    /**
     * Fetch the current outside temperature from thermostat.
     * @return String.
     */
    String fetchOutsideTemperature();

    /**
     * Check either target circle displayed in thermostat page or not.
     * @return true, if target circle display in thermostat page.
     */
    boolean checkTargetCircle();
}
