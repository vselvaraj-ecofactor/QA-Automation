/*
 * CommonsDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.data;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.google.inject.Inject;

/**
 * The Class CommonsDataProvider.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class CommonsDataProvider {

	/** The mobile config. */
	@Inject
	private static MobileConfig mobileConfig;

	/** The Constant DUMMY. */
	private final static String DUMMY = "dummy";

	/**
	 * Default user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "defaultUser")
	public static Object[][] defaultUser() {

		return checkDummy(VALIDLOGIN_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(VALIDLOGIN_USERNAME),
				mobileConfig.get(VALIDLOGIN_PASSWORD) } };
	}

	/**
	 * Default user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "webUser")
	public static Object[][] webUser() {

		return checkDummy(WEBUSERLOGIN_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(WEBUSERLOGIN_USERNAME),
				mobileConfig.get(WEBUSERLOGIN_PASSWORD) } };
	}

	/**
	 * Valid login.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "validLogin")
	public static Object[][] validLogin() {

		return checkDummy(VALIDLOGIN_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(VALIDLOGIN_USERNAME),
				mobileConfig.get(VALIDLOGIN_PASSWORD) } };
	}

	/**
	 * sixty Character.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "sixtyCharLogin")
	public static Object[][] sixtyCharLogin() {

		return checkDummy(SIXTYCHAR_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(SIXTYCHAR_USERNAME),
				mobileConfig.get(SIXTYCHAR_PASSWORD) } };
	}

	/**
	 * No location installed.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "noLocationInstalled")
	public static Object[][] noLocationInstalled() {

		return checkDummy(NO_LOCATION_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(NO_LOCATION_USERNAME),
				mobileConfig.get(NO_LOCATION_PASSWORD) } };
	}

	/**
	 * space between username and password.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "firstSpaceLast")
	public static Object[][] firstNameSpaceLastName() {

		return checkDummy(FIRSTSPACELAST_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(FIRSTSPACELAST_USERNAME),
				mobileConfig.get(FIRSTSPACELAST_PASSWORD) } };
	}

	/**
	 * space between username and password.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "forgotPassword")
	public static Object[][] forgotPassword() {

		return checkDummy(FORGOTPASSWORD_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(FORGOTPASSWORD_USERNAME),
				mobileConfig.get(FORGOTPASSWORD_PASSWORD) } };
	}

	/**
	 * Space in leading and trailing of username.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "leadingtrailingusername")
	public static Object[][] leadingtrailingusername() {

		return checkDummy(LEADINGTRAILING_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(LEADINGTRAILING_USERNAME),
				mobileConfig.get(LEADINGTRAILING_PASSWORD) } };
	}

	/**
	 * Invalid user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "invalidUser")
	public static Object[][] invalidUser() {

		return checkDummy(INVALID_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(INVALID_USERNAME),
				mobileConfig.get(INVALID_PASSWORD) } };
	}

	/**
	 * Unregistered user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "unregisteredUser")
	public static Object[][] unregisteredUser() {

		return checkDummy(UNREGISTERED_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(UNREGISTERED_USERNAME),
				mobileConfig.get(UNREGISTERED_PASSWORD) } };
	}

	/**
	 * Invalid password.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "invalidPassword")
	public static Object[][] invalidPassword() {

		return checkDummy(VALIDLOGIN_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(VALIDLOGIN_USERNAME),
				mobileConfig.get(INCORRECT_PASSWORD) } };
	}

	/**
	 * Special char user name.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "specialCharUserName")
	public static Object[][] specialCharUserName() {

		return checkDummy(SPECIAL_CHAR_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(SPECIAL_CHAR_USERNAME),
				mobileConfig.get(VALIDLOGIN_PASSWORD) } };
	}

	/**
	 * Multi thermostat user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multiThermostatUser")
	public static Object[][] multiThermostatUser() {

		return checkDummy(MULTI_THERMOSTAT_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(MULTI_THERMOSTAT_USERNAME),
						mobileConfig.get(MULTI_THERMOSTAT_PASSWORD) } };
	}

	/**
	 * Multi thermostat user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "comparePosition")
	public static Object[][] comparePosition() {

		return checkDummy(COMPARE_POSITION_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(COMPARE_POSITION_USERNAME),
						mobileConfig.get(COMPARE_POSITION_PASSWORD) } };
	}

	/**
	 * Heatonly thermostat user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "heatonlyThermostatUser")
	public static Object[][] heatonlyThermostatUser() {

		return checkDummy(HEATONLY_THERMOSTAT_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(HEATONLY_THERMOSTAT_USERNAME),
						mobileConfig.get(HEATONLY_THERMOSTAT_PASSWORD) } };
	}

	/**
	 * Coolonly thermostat user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "coolonlyThermostatUser")
	public static Object[][] coolonlyThermostatUser() {

		return checkDummy(COOLONLY_THERMOSTAT_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(COOLONLY_THERMOSTAT_USERNAME),
						mobileConfig.get(COOLONLY_THERMOSTAT_PASSWORD) } };
	}

	/**
	 * Tstat offline user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "tstatOfflineUser")
	public static Object[][] tstatOfflineUser() {

		return checkDummy(TSTAT_OFFLINE_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(TSTAT_OFFLINE_USERNAME),
				mobileConfig.get(TSTAT_OFFLINE_PASSWORD) } };
	}

	/**
	 * Ascii tstat user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "asciiTstatUser")
	public static Object[][] asciiTstatUser() {

		return checkDummy(ASCII_TSTAT_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(ASCII_TSTAT_USERNAME),
				mobileConfig.get(ASCII_TSTAT_PASSWORD) } };
	}

	/**
	 * Blank tstat name.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "blankTstatName")
	public static Object[][] blankTstatName() {

		return checkDummy(BLANK_TSTAT_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(BLANK_TSTAT_USERNAME),
				mobileConfig.get(BLANK_TSTAT_PASSWORD) } };
	}

	/**
	 * Settings page.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "settingsPage")
	public static Object[][] settingsPage() {

		return checkDummy(SETTINGS_PAGE_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(SETTINGS_PAGE_USERNAME),
				mobileConfig.get(SETTINGS_PAGE_PASSWORD) } };
	}

	/**
	 * Multiple location.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multipleLocation")
	public static Object[][] multipleLocation() {

		return checkDummy(MULTIPLE_LOC_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(MULTIPLE_LOC_USERNAME),
				mobileConfig.get(MULTIPLE_LOC_PASSWORD) } };
	}

	/**
	 * Dual location.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "dualLocation")
	public static Object[][] dualLocation() {

		return checkDummy(DUAL_LOC_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(DUAL_LOC_USERNAME),
				mobileConfig.get(DUAL_LOC_PASSWORD) } };
	}

	/**
	 * Multiple tstat alphabetical.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multipleTstatAlphabetical")
	public static Object[][] multipleTstatAlphabetical() {

		return checkDummy(MULTIPLE_TSTAT_ALPHA_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(MULTIPLE_TSTAT_ALPHA_USERNAME),
						mobileConfig.get(MULTIPLE_TSTAT_ALPHA_PASSWORD) } };
	}

	/**
	 * Provisional status.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "provisionalStatus")
	public static Object[][] provisionalStatus() {

		return checkDummy(PROVISIONAL_TSTAT_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(PROVISIONAL_TSTAT_USERNAME),
						mobileConfig.get(PROVISIONAL_TSTAT_PASSWORD) } };
	}

	/**
	 * Two good thermostats.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multipleTwoGoodTstats")
	public static Object[][] multipleTwoGoodTstats() {

		return checkDummy(MULTIPLE_2_GOOD_TSTATS_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(MULTIPLE_2_GOOD_TSTATS_USERNAME),
						mobileConfig.get(MULTIPLE_2_GOOD_TSTATS_PASSWORD) } };
	}

	/**
	 * Two thermostats - one connected, one disconnected.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multipleOneOnOneOff")
	public static Object[][] multipleOneOnOneOff() {

		return checkDummy(MULTIPLE_1_ON_1_OFF_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(MULTIPLE_1_ON_1_OFF_USERNAME),
						mobileConfig.get(MULTIPLE_1_ON_1_OFF_PASSWORD) } };
	}

	/**
	 * Three thermostats - Location 1: tstat1, tstat2; Location 2: tstat1.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multipleLocations")
	public static Object[][] multipleLocations() {

		return checkDummy(MULTIPLE_LOCATIONS_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(MULTIPLE_LOCATIONS_USERNAME),
						mobileConfig.get(MULTIPLE_LOCATIONS_PASSWORD) } };
	}

	/**
	 * Multiple locations dynamic.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "multipleLocationDynamic")
	public static Object[][] multipleLocationsDynamic() {

		return checkDummy(MULTIPLE_LOCATION_DYNAMIC_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(MULTIPLE_LOCATION_DYNAMIC_USERNAME),
						mobileConfig.get(MULTIPLE_LOCATION_DYNAMIC_PASSWORD) } };
	}

	/**
	 * Two thermostats - 1 active, 1 inactive.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "inactive")
	public static Object[][] inactive() {

		return checkDummy(INACTIVE_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(INACTIVE_USERNAME),
				mobileConfig.get(INACTIVE_PASSWORD) } };
	}

	/**
	 * Two thermostats - 1 active, 1 provisioned.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "provisioned")
	public static Object[][] provisioned() {

		return checkDummy(PROVISIONED_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(PROVISIONED_USERNAME),
				mobileConfig.get(PROVISIONED_PASSWORD) } };
	}

	/**
	 * Heat only thermostat.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "heatonly")
	public static Object[][] heatonly() {

		return checkDummy(HEATONLY_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(HEATONLY_USERNAME),
				mobileConfig.get(HEATONLY_PASSWORD) } };
	}

	/**
	 * Cool only thermostat.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "coolonly")
	public static Object[][] coolonly() {

		return checkDummy(COOLONLY_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(COOLONLY_USERNAME),
				mobileConfig.get(COOLONLY_PASSWORD) } };
	}

	/**
	 * Default savings enegry.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "defaultSavingsEnegry")
	public static Object[][] defaultSavingsEnegry() {

		return checkDummy(DEFAULT_SAVINGS_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(DEFAULT_SAVINGS_USERNAME),
						mobileConfig.get(DEFAULT_SAVINGS_PASSWORD),
						Integer.parseInt(mobileConfig
								.get(DEFAULT_SAVINGS_TSTAT_ID)) } };
	}

	/**
	 * Infrastructure db.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "infrastructureDB")
	public static Object[][] infrastructureDB() {

		return checkDummy(INFRASTRUCTUREDB_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(INFRASTRUCTUREDB_USERNAME),
						mobileConfig.get(INFRASTRUCTUREDB_PASSWORD),
						Integer.parseInt(mobileConfig
								.get(INFRASTRUCTUREDB_THERMOSTATID)) } };
	}

	/**
	 * Ecp core user wo savings.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "ecpCoreUserWOSavings")
	public static Object[][] ecpCoreUserWOSavings() {

		return checkDummy(ECPCORE_WITHOUTSAVINGS_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(ECPCORE_WITHOUTSAVINGS_USERNAME),
						mobileConfig.get(ECPCORE_WITHOUTSAVINGS_PASSWORD) } };
	}

	/**
	 * Ecp core user with savings.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "ecpCoreUserWithSavings")
	public static Object[][] ecpCoreUserWithSavings() {

		return checkDummy(ECPCORE_WITHSAVINGS_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(ECPCORE_WITHSAVINGS_USERNAME),
						mobileConfig.get(ECPCORE_WITHSAVINGS_PASSWORD) } };
	}

	/**
	 * Savings six month data.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "savingsSixMonthData")
	public static Object[][] savingsSixMonthData() {

		return checkDummy(SAVINGS_WITHSIXMONTHDATA_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(SAVINGS_WITHSIXMONTHDATA_USERNAME),
						mobileConfig.get(SAVINGS_WITHSIXMONTHDATA_PASSWORD) } };
	}

	/**
	 * Zero runtime savings.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "zeroRuntimeSavings")
	public static Object[][] zeroRuntimeSavings() {

		return checkDummy(ZERO_RUNTIME_SAVINGS_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(ZERO_RUNTIME_SAVINGS_USERNAME),
						mobileConfig.get(ZERO_RUNTIME_SAVINGS_PASSWORD) } };
	}

	/**
	 * Mutilple and one month data.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "mutilpleAndOneMonthData")
	public static Object[][] mutilpleAndOneMonthData() {

		return checkDummy(SAVINGS_WITHSIXMONTHDATA_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(SAVINGS_WITHSIXMONTHDATA_USERNAME),
						mobileConfig.get(DEFAULT_SAVINGS_USERNAME),
						mobileConfig.get(SAVINGS_WITHSIXMONTHDATA_PASSWORD) } };
	}

	/**
	 * Learn more user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "learnMoreUser")
	public static Object[][] learnMoreUser() {

		return checkDummy(LEARN_MORE_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(LEARN_MORE_USERNAME),
				mobileConfig.get(LEARN_MORE_PASSWORD) } };
	}

	/**
	 * Location savings.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "locationSavings")
	public static Object[][] locationSavings() {

		return checkDummy(SAVINGS_WITHSIXMONTHDATA_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(SAVINGS_WITHSIXMONTHDATA_USERNAME),
						mobileConfig.get(SAVINGS_WITHSIXMONTHDATA_PASSWORD),
						mobileConfig.get(SAVINGS_PRECISION_LOCATION_ID) } };
	}

	/**
	 * Location not installed.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "locationNotInstalled")
	public static Object[][] locationNotInstalled() {

		return checkDummy(PROVISIONAL_TSTAT_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(PROVISIONAL_TSTAT_USERNAME),
						mobileConfig.get(PROVISIONAL_TSTAT_PASSWORD) } };
	}

	/**
	 * Location switcher multi loc.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "locationSwitcherMultiLoc")
	public static Object[][] locationSwitcherMultiLoc() {

		return checkDummy(LOCATION_SWITCHER_MULTIlOC_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(LOCATION_SWITCHER_MULTIlOC_USERNAME),
						mobileConfig.get(LOCATION_SWITCHER_MULTIlOC_PASSWORD) } };
	}

	/**
	 * Location switcher multi tstat.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "locationSwitcherMultiTstat")
	public static Object[][] locationSwitcherMultiTstat() {

		return checkDummy(LOCATION_SWITCHER_MULTITSTAT_USERNAME) ? null
				: new Object[][] { {
						mobileConfig.get(LOCATION_SWITCHER_MULTITSTAT_USERNAME),
						mobileConfig.get(LOCATION_SWITCHER_MULTITSTAT_PASSWORD) } };
	}

	/**
	 * Password user.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "passwordUser")
	public static Object[][] passwordUser() {

		return checkDummy(PASSWORD_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(PASSWORD_USERNAME),
				mobileConfig.get(PASSWORD_PASSWORD) } };
	}

	/**
	 * Diff password check.
	 * 
	 * @return the object[][]
	 */
	@DataProvider(name = "diffPasswordCheck")
	public static Object[][] diffPasswordCheck() {

		return checkDummy(PASSWORD_USERNAME) ? null : new Object[][] { {
				mobileConfig.get(PASSWORD_USERNAME),
				mobileConfig.get(PASSWORD_PASSWORD),
				mobileConfig.get(DIFF_PASSWORDS) } };
	}

	/**
	 * Check dummy.
	 * 
	 * @param userKey
	 *            the user key
	 * @return true, if successful
	 */
	private static boolean checkDummy(final String userKey) {

		if (mobileConfig.get(userKey).contains(DUMMY)) {
			setLogString("Dummy user allocated", true);
			return true;
		}
		return false;
	}
}
