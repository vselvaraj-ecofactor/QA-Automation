/*
 * TstatAdminValidation.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.validate.admin;

import java.util.Locale;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.common.pojo.ThermostatDataDef.FanMode;
import com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage;
import com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage;
import com.ecofactor.qa.automation.newapp.admin.page.AdminLoginPage;
import com.ecofactor.qa.automation.newapp.admin.page.impl.AdminDialoguePageImpl.HvacState;
import com.ecofactor.qa.automation.newapp.admin.page.impl.AdminDialoguePageImpl.SelectOption;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.annotation.AdminValidationMethod;
import com.ecofactor.qa.automation.platform.annotation.BindAdminValidation;
import com.google.inject.Inject;

/**
 * The Class TstatAdminValidation.
 * Validation operations for Thermostat Admin. 
 * This class contains verification methods for temperature,hvac mode,fan mode,etc
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@BindAdminValidation
public class TstatAdminValidation {

    @Inject
    private AdminLoginPage adminLogin;
    @Inject
    private AdminHomePage homePage;
    @Inject
    private AdminDialoguePage adminDialougePage;

	private static String HEAT_SETPOINT="Heat Setpoint";
	private static String COOL_SETPOINT="Cool Setpoint";
	
	private static String HEAT="Heat";
    
    /**
     * The Constant LOGGER.
     */
    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(TstatAdminValidation.class);

    /**
     * Verify current temperature.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param currentTemperature the current temperature
     */
    @AdminValidationMethod
    public void verifyCurrentTemperature(final String userName, final String thermostatName, final String currentTemperature) {

        loadPage(userName, thermostatName, currentTemperature);
        homePage.filterCriteria(userName);
        homePage.clickFilteredRecord(0);
        final String currentTemp = homePage.getCurrentTemperature(thermostatName);
        Assert.assertTrue(currentTemp.equalsIgnoreCase(currentTemperature));
        cleanUp();
    }

    /**
     * Verify target temp.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     * @param targetTemp the target temp
     */
    @AdminValidationMethod
    public void verifyTargetTemp(final String userName, final String thermostatName, final String mode, final String targetTemp) {

        loadPage(userName, thermostatName, mode);
        homePage.filterCriteria(userName);
        homePage.clickFilteredRecord(0);
        homePage.clickDiagLink(thermostatName);
        adminDialougePage.isPageLoaded();
        final String value = mode.equalsIgnoreCase(HEAT) ? HEAT_SETPOINT : COOL_SETPOINT;
        adminDialougePage.selectDropdownValue(value);
        adminDialougePage.clickRead();
        final String hexaValue = adminDialougePage.readTemperature();
        LogUtil.setLogString(new StringBuilder("Target temperature from UI: ").append( targetTemp).toString(), true);
        Assert.assertTrue("Target temperature differs", adminDialougePage.hexaToFahrenheit(hexaValue).equalsIgnoreCase(targetTemp));
        cleanUp();
    }

    /**
     * Verify mode.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     */
    @AdminValidationMethod
    public void verifyMode(final String userName, final String thermostatName, final String mode) {

        loadPage(userName, thermostatName, mode);
        homePage.filterCriteria(userName);
        homePage.clickFilteredRecord(0);
        homePage.clickDiagLink(thermostatName);
        adminDialougePage.isPageLoaded();
        adminDialougePage.selectDropdownValue(SelectOption.HVAC_MODE);
        adminDialougePage.clickRead();
        final String hexaValue = adminDialougePage.readTemperature();
        final String modeValue = adminDialougePage.hexaToDecimal(hexaValue);
        LogUtil.setLogString(new StringBuilder("Hvac mode from UI: ").append(mode).toString(), true);
        LogUtil.setLogString(new StringBuilder("Hvac mode from Admin: ").append( HvacState.values()[Integer.parseInt(modeValue)]).toString(), true);
        Assert.assertTrue("Hvac mode differs", Integer.valueOf(modeValue).equals(HvacState.valueOf(mode.toUpperCase(Locale.ENGLISH)).getState()));
        LogUtil.setLogString("Verified the mode in Admin", true);
        cleanUp();
    }

    /**
     * Verify fan mode.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     */
    @AdminValidationMethod
    public void verifyFanMode(final String userName, final String thermostatName, final String mode) {

        loadPage(userName, thermostatName, mode);
        homePage.filterCriteria(userName);
        homePage.clickFilteredRecord(0);
        homePage.clickDiagLink(thermostatName);
        adminDialougePage.isPageLoaded();
        adminDialougePage.selectDropdownValue(SelectOption.FAN_MODE);
        adminDialougePage.clickRead();
        final String hexaValue = adminDialougePage.readTemperature();
        final String modeValue = adminDialougePage.hexaToDecimal(hexaValue);
        LogUtil.setLogString(new StringBuilder("Fan mode from UI: ").append( mode).toString(), true);
        LogUtil.setLogString(new StringBuilder("Fan mode from Admin: ").append( FanMode.values()[Integer.parseInt(modeValue)]).toString(), true);
        Assert.assertTrue("Fan mode differs", Integer.valueOf(modeValue).equals(FanMode.valueOf(mode).getMode()));
        cleanUp();
    }

    /**
     * Verify thermostat offline.
     * @param userName the user name
     * @param thermostatName the thermostat name
     */
    @AdminValidationMethod
    public void verifyThermostatOffline(final String userName, final String thermostatName) {

        loadPage(userName, thermostatName, null);
        homePage.filterCriteria(userName);
        homePage.clickFilteredRecord(0);
        final String currentTemp = homePage.getCurrentTemperature(thermostatName);
        LogUtil.setLogString(new StringBuilder("currr temp: ").append(currentTemp).toString(), true);
        Assert.assertTrue(currentTemp.contains("Error:Device Control") || currentTemp.contains("Running..."));
        cleanUp();
    }

    /**
     * Load page.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     */
    public void loadPage(final String userName, final String thermostatName, final String mode) {

        LogUtil.setLogString(new StringBuilder("Username:").append(userName).append("; Thermostat Name: ").append( thermostatName).append( "; Mode: ").append( mode).toString(), true);
        adminLogin.login();
    }

    /**
     * Clean up.
     */
    public void cleanUp() {

        if (adminDialougePage.isPageLoaded()) {
            adminDialougePage.closeDialogueWindow();
        }
        homePage.logout();
    }
}
