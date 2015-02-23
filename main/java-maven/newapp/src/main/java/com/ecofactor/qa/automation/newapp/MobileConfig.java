/*
 * MobileConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.io.File;
import java.io.IOException;

import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * MobileConfig configures the mobile.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MobileConfig extends BaseConfig {

	// admin page
	public static final String ADMIN_URL = "admin.url";
	public static final String ADMIN_USERNAME = "admin.userName";
	public static final String ADMIN_PASSWORD = "admin.password";

	public static final String GMAIL_URL = "http://www.gmail.com";
	public static final String ECOFACTOR_URL = "ecofactorUrl";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String LEARN_MORE_LINK = "ecofactor.learnmorepage.url";

	// Thermostat Page
	public static final String MULTI_THERMOSTAT_USERNAME = "multiplethermostat.username";
	public static final String MULTI_THERMOSTAT_PASSWORD = "multiplethermostat.password";

	public static final String HEATONLY_THERMOSTAT_USERNAME = "heatonlythermostat.username";
	public static final String HEATONLY_THERMOSTAT_PASSWORD = "heatonlythermostat.password";

	public static final String COOLONLY_THERMOSTAT_USERNAME = "coolonlythermostat.username";
	public static final String COOLONLY_THERMOSTAT_PASSWORD = "coolonlythermostat.password";

	public static final String TSTAT_OFFLINE_USERNAME = "tstatOffline.username";
	public static final String TSTAT_OFFLINE_PASSWORD = "tstatOffline.password";

	public static final String ASCII_TSTAT_USERNAME = "asciithermostat.username";
	public static final String ASCII_TSTAT_PASSWORD = "asciithermostat.password";

	public static final String BLANK_TSTAT_USERNAME = "blankThermostat.username";
	public static final String BLANK_TSTAT_PASSWORD = "blankThermostat.password";

	public static final String MULTIPLE_LOC_USERNAME = "multipleLoc.username";
	public static final String MULTIPLE_LOC_PASSWORD = "multipleLoc.password";

	public static final String DUAL_LOC_USERNAME = "dualLocation.username";
	public static final String DUAL_LOC_PASSWORD = "dualLocation.password";

	public static final String COMPARE_POSITION_USERNAME = "comparePosition.username";
	public static final String COMPARE_POSITION_PASSWORD = "comparePosition.password";

	public static final String MULTIPLE_TSTAT_ALPHA_USERNAME = "multipleTstatAlpha.username";
	public static final String MULTIPLE_TSTAT_ALPHA_PASSWORD = "multipleTstatAlpha.password";

	public static final String MULTI_THERMOSTAT_USERNAME_ALT1 = "multiplethermostatAlt1.username";
	public static final String MULTI_THERMOSTAT_PASSWORD_ALT1 = "multiplethermostatAlt1.password";

	// Login Page
	public static final String VALIDLOGIN_USERNAME = "validlogin.username";
	public static final String VALIDLOGIN_PASSWORD = "validlogin.password";

	public static final String WEBUSERLOGIN_USERNAME = "webUserLogin.username";
	public static final String WEBUSERLOGIN_PASSWORD = "webUserLogin.password";

	public static final String SIXTYCHAR_USERNAME = "sixtyChar.username";
	public static final String SIXTYCHAR_PASSWORD = "sixtyChar.password";

	public static final String FORGOTPASSWORD_USERNAME = "forgotpassword.username";
	public static final String FORGOTPASSWORD_PASSWORD = "forgotpassword.password";

	public static final String LEADINGTRAILING_USERNAME = "leadingtrailing.username";
	public static final String LEADINGTRAILING_PASSWORD = "leadingtrailing.password";

	public static final String INVALID_USERNAME = "invalid.username";
	public static final String INVALID_PASSWORD = "invalid.password";

	public static final String FIRSTSPACELAST_USERNAME = "firstspacelast.username";
	public static final String FIRSTSPACELAST_PASSWORD = "firstspacelast.password";

	public static final String UNREGISTERED_USERNAME = "unregistered.username";
	public static final String UNREGISTERED_PASSWORD = "unregistered.password";

	public static final String INCORRECT_PASSWORD = "incorrect.password";

	public static final String SPECIAL_CHAR_USERNAME = "specialchar.username";

	public static final String PROVISIONAL_TSTAT_USERNAME = "provisionalStatus.username";
	public static final String PROVISIONAL_TSTAT_PASSWORD = "provisionalStatus.password";

	public static final String MULTIPLE_2_GOOD_TSTATS_USERNAME = "multipleThermostatTwoGoodTstats.username";
	public static final String MULTIPLE_2_GOOD_TSTATS_PASSWORD = "multipleThermostatTwoGoodTstats.password";

	public static final String MULTIPLE_1_ON_1_OFF_USERNAME = "multipleOneOnOneOff.username";
	public static final String MULTIPLE_1_ON_1_OFF_PASSWORD = "multipleOneOnOneOff.password";

	public static final String MULTIPLE_LOCATIONS_USERNAME = "multipleLocations.username";
	public static final String MULTIPLE_LOCATIONS_PASSWORD = "multipleLocations.password";

	public static final String INACTIVE_USERNAME = "inactive.username";
	public static final String INACTIVE_PASSWORD = "inactive.password";

	public static final String PROVISIONED_USERNAME = "provisioned.username";
	public static final String PROVISIONED_PASSWORD = "provisioned.password";

	public static final String HEATONLY_USERNAME = "heatonly.username";
	public static final String HEATONLY_PASSWORD = "heatonly.password";

	public static final String COOLONLY_USERNAME = "coolonly.username";
	public static final String COOLONLY_PASSWORD = "coolonly.password";

	public static final String NO_LOCATION_USERNAME = "locationNotInstalled.username";
	public static final String NO_LOCATION_PASSWORD = "locationNotInstalled.password";

	// Color codes
	public static final String HEAT_GRADIENT_CODE = "gradientheat.colorcode";
	public static final String COOL_GRADIENT_CODE = "gradientcool.colorcode";
	public static final String OFF_GRADIENT_CODE = "gradientoff.colorcode";
	public static final String IDLE_GRADIENT_CODE = "gradientidle.colorcode";
	// login
	public static final String FONT_COLOR = "error.fontcolor";
	public static final String FONT_FAMILY = "error.fontfamily";
	public static final String ERROR_BGCOLOR = "error.backgroundcolor";

	public static final String INFRASTRUCTUREDB_USERNAME = "infrastructureDB.username";
	public static final String INFRASTRUCTUREDB_PASSWORD = "infrastructureDB.password";
	public static final String INFRASTRUCTUREDB_THERMOSTATID = "infrastructureDB.tstId";

	public static final String DEFAULT_SAVINGS_USERNAME = "defaultSavingsEnegry.username";
	public static final String DEFAULT_SAVINGS_PASSWORD = "defaultSavingsEnegry.password";
	public static final String DEFAULT_SAVINGS_TSTAT_ID = "defaultSavingsEnegry.thermostatId";

	public static final String MULTIPLE_LOCATION_DYNAMIC_USERNAME = "multipleLocationDynamic.username";
	public static final String MULTIPLE_LOCATION_DYNAMIC_PASSWORD = "multipleLocationDynamic.password";

	public static final String ECPCORE_WITHOUTSAVINGS_USERNAME = "ecpcoreWithoutsavings.username";
	public static final String ECPCORE_WITHOUTSAVINGS_PASSWORD = "ecpcoreWithoutSavings.password";

	public static final String ECPCORE_WITHSAVINGS_USERNAME = "ecpcoreWithSavings.username";
	public static final String ECPCORE_WITHSAVINGS_PASSWORD = "ecpcorewithSavings.password";

	public static final String SAVINGS_WITHSIXMONTHDATA_USERNAME = "savingsWithSixMonthData.username";
	public static final String SAVINGS_WITHSIXMONTHDATA_PASSWORD = "savingsWithSixMonthData.password";

	public static final String ZERO_RUNTIME_SAVINGS_USERNAME = "zeroRuntimeSavings.username";
	public static final String ZERO_RUNTIME_SAVINGS_PASSWORD = "zeroRuntimeSavings.password";

	public static final String LEARN_MORE_USERNAME = "learnMoreUser.username";
	public static final String LEARN_MORE_PASSWORD = "learnMoreUser.password";

	public static final String LOCATION_SWITCHER_MULTIlOC_USERNAME = "locationSwitcherMultiLoc.username";
	public static final String LOCATION_SWITCHER_MULTIlOC_PASSWORD = "locationSwitcherMultiLoc.password";

	public static final String LOCATION_SWITCHER_MULTITSTAT_USERNAME = "locationSwitcherMultiTstat.username";
	public static final String LOCATION_SWITCHER_MULTITSTAT_PASSWORD = "locationSwitcherMultiTstat.password";

	public static final String SAVINGS_PRECISION_LOCATION_ID = "savingsprecision.locationId";

	public static final String SETTINGS_PAGE_USERNAME = "loginSettingsPage.username";
	public static final String SETTINGS_PAGE_PASSWORD = "loginSettingsPage.password";

	public static final String PASSWORD_USERNAME = "password.username";
	public static final String PASSWORD_PASSWORD = "password.password";

	public static final String DIFF_PASSWORDS = "diff.passwords";

	public static final String SAVINGS_COLOR = "savings.color";

	/**
	 * Instantiates a new mobile config.
	 * 
	 * @throws IOException
	 * @throws DeviceException
	 */
	@Inject
	public MobileConfig(final DriverConfig driverConfig) throws IOException {

		super(driverConfig.get(DriverConfig.ENV));
		String node = System.getenv("NODE_NAME");
		String useDefault = System.getProperty("useDefaultUserNames");
		if (useDefault != null && useDefault.equalsIgnoreCase("true")) {
			node = "default";
		}
		node = node != null && !node.isEmpty() ? node + "_" : "default_";
		load("mobile.properties");
		String url = get(ECOFACTOR_URL);
		System.setProperty("url", url);
		LogUtil.setLogString("Use Default user names :" + useDefault, true,
				CustomLogLevel.LOW);
		File file = null;
		if (useDefault != null && useDefault.equalsIgnoreCase("true")) {
			LogUtil.setLogString("Load default user properties", true,
					CustomLogLevel.LOW);
			file = new File("src/main/resources/"
					+ driverConfig.get(DriverConfig.ENV)
					+ "_default_mobileTest.properties");
		} else {
			file = new File("src/main/resources/"
					+ driverConfig.get(DriverConfig.ENV) + "_" + node
					+ "mobileTest.properties");
		}
		if (file.exists()) {
			load(node + "mobileTest.properties");
		} else {
			LogUtil.setLogString(
					"Users are not allocated to the slave "
							+ System.getenv("NODE_NAME")
							+ ".Add slave name in slaves.properties and run test data to allocate users.",
					true);
			throw new IOException(
					"Users are not allocated to the slave "
							+ System.getenv("NODE_NAME")
							+ ".Add slave name in slaves.properties and run test data to allocate users.");
		}
	}
}
