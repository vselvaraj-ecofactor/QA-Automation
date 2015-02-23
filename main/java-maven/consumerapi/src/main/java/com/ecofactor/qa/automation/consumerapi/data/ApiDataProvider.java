/*
 * ApiDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi.data;

import static com.ecofactor.qa.automation.consumerapi.ApiConfig.*;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.consumerapi.ApiConfig;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.google.inject.Inject;

/**
 * The Class ApiDataProvider.
 */
public class ApiDataProvider {

    @Inject
    private static ApiConfig apiConfig;

    /**
     * Dollar savings.
     * @return the object[][]
     */
    @DataProvider(name = "dollarSavings")
    public static Object[][] dollarSavings() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD) } };
    }

    /**
     * Authenticator.
     * @return the object[][]
     */
    @DataProvider(name = "authenticator")
    public static Object[][] authenticator() {

        return new Object[][] { { apiConfig.get(AUTHENTICATOR_USERNAME),
                apiConfig.get(AUTHENTICATOR_PASSWORD) } };
    }

    /**
     * Hvac systems.
     * @return the object[][]
     */
    @DataProvider(name = "hvacSystems")
    public static Object[][] hvacSystems() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(THERMOSTAT_ID) } };
    }
    
    @DataProvider(name = "away")
    public static Object[][] away() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD)} };
    }

    /**
     * Location savings.
     * @return the object[][]
     */
    @DataProvider(name = "locationSavings")
    public static Object[][] locationSavings() {

        return new Object[][] { { apiConfig.get(SAVINGS_PRECISION_USERNAME),
                apiConfig.get(SAVINGS_PRECISION_PASSWORD),
                apiConfig.get(SAVINGS_PRECISION_LOCATION_ID) } };
    }

    /**
     * Schedule.
     * @return the object[][]
     */
    @DataProvider(name = "schedule")
    public static Object[][] schedule() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(SCHEDULE_ID) } };
    }

    /**
     * Time zone.
     * @return the object[][]
     */
    @DataProvider(name = "timeZone")
    public static Object[][] timeZone() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(TIMEZONE_ZIPCODE) } };
    }

    /**
     * User data.
     * @return the object[][]
     */
    @DataProvider(name = "userData")
    public static Object[][] userData() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(USER_ID) } };
    }

    /**
     * Weather data.
     * @return the object[][]
     */
    @DataProvider(name = "weatherData")
    public static Object[][] weatherData() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(ZIOCODE_CITYNAME) } };
    }

    /**
     * Invalid thermostat id.
     * @return the object[][]
     */
    @DataProvider(name = "invalidThermostatId")
    public static Object[][] invalidThermostatId() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(INVALID_THERMOSTAT_ID) } };
    }

    /**
     * Invalid thermostat id.
     * @return the object[][]
     */
    @DataProvider(name = "learningmode")
    public static Object[][] learningmode() {

        if (apiConfig.get(LEARNING_MODE_USERNAME).contains("dummy")) {
            logDummy();
            return null;
        }
        return new Object[][] { { apiConfig.get(LEARNING_MODE_USERNAME),
                apiConfig.get(LEARNING_MODE_PASSWORD), apiConfig.get(LEARNING_MODE_THERMOSTAT_ID) } };
    }

    /**
     * Offline.
     * @return the object[][]
     */
    @DataProvider(name = "missingsavingsmonth")
    public static Object[][] missingsavingsmonth() {

        if (apiConfig.get(MISSING_SAVINGS_MONTH_USERNAME).contains("dummy")) {
            logDummy();
            return null;
        }
        return new Object[][] { { apiConfig.get(MISSING_SAVINGS_MONTH_USERNAME),
                apiConfig.get(MISSING_SAVINGS_MONTH_PASSWORD),
                apiConfig.get(MISSING_SAVINGS_MONTH_THERMOSTAT_ID) } };
    }

    /**
     * Savingsprecision.
     * @return the object[][]
     */
    @DataProvider(name = "savingsprecision")
    public static Object[][] savingsprecision() {

        if (apiConfig.get(SAVINGS_PRECISION_USERNAME).contains("dummy")) {
            logDummy();
            return null;
        }
        return new Object[][] { { apiConfig.get(SAVINGS_PRECISION_USERNAME),
                apiConfig.get(SAVINGS_PRECISION_PASSWORD),
                apiConfig.get(SAVINGS_PRECISION_THERMOSTAT_ID) } };
    }

    /**
     * Provisioned.
     * @return the object[][]
     */
    @DataProvider(name = "provisioned")
    public static Object[][] provisioned() {

        if (apiConfig.get(PROVISIONED_USERNAME).contains("dummy")) {
            logDummy();
            return null;
        }
        return new Object[][] { { apiConfig.get(PROVISIONED_USERNAME),
                apiConfig.get(PROVISIONED_PASSWORD), apiConfig.get(PROVISIONED_THERMOSTAT_ID) } };
    }

    /**
     * invalid location id.
     * @return the object[][]
     */
    @DataProvider(name = "invalidlocationid")
    public static Object[][] invalidlocationid() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(INVALID_LOCATION_ID) } };
    }

    /**
     * Nosavings.
     * @return the object[][]
     */
    @DataProvider(name = "nosavings")
    public static Object[][] nosavings() {

        return new Object[][] { { apiConfig.get(NO_SAVINGS_USERNAME),
                apiConfig.get(NO_SAVINGS_PASSWORD), apiConfig.get(NO_SAVINGS_THERMOSTAT_ID) } };
    }

    /**
     * Sixthermostats.
     * @return the object[][]
     */
    @DataProvider(name = "sixthermostats")
    public static Object[][] sixthermostats() {

        return new Object[][] { { apiConfig.get(SIX_THERMOSTATS_USERNAME),
                apiConfig.get(SIX_THERMOSTATS_PASSWORD), apiConfig.get(SIX_THERMOSTATS_LOCATION_ID) } };
    }

    /**
     * Inactivethermostat.
     * @return the object[][]
     */
    @DataProvider(name = "inactivethermostat")
    public static Object[][] inactivethermostat() {

        return new Object[][] { { apiConfig.get(INACTIVE_USERNAME),
                apiConfig.get(INACTIVE_PASSWORD), apiConfig.get(INACTIVE_THERMOSTAT_ID) } };
    }

    /**
     * Negativeruntimethermostat.
     * @return the object[][]
     */
    @DataProvider(name = "negativeruntimethermostat")
    public static Object[][] negativeruntimethermostat() {

        return new Object[][] { { apiConfig.get(NEGATIVE_RUNTIMESAVINGS_USERNAME),
                apiConfig.get(NEGATIVE_RUNTIMESAVINGS_PASSWORD),
                apiConfig.get(NEGATIVE_RUNTIMESAVINGS_THERMOSTAT_ID) } };
    }

    /**
     * Validecpcore.
     * @return the object[][]
     */
    @DataProvider(name = "validecpcore")
    public static Object[][] validecpcore() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(ECP_CORE_ID) } };
    }

    /**
     * Apimethodsallowed.
     * @return the object[][]
     */
    @DataProvider(name = "apimethodsallowed")
    public static Object[][] apimethodsallowed() {

        return new Object[][] { { apiConfig.get(USERNAME), apiConfig.get(PASSWORD),
                apiConfig.get(THERMOSTAT_ID), apiConfig.get(LOCATION_ID) } };
    }

    /**
     * Coolonlyuser.
     * @return the object[][]
     */
    @DataProvider(name = "coolonlyuser")
    public static Object[][] coolonlyuser() {

        if (apiConfig.get(COOLONLY_USERNAME).contains("dummy")) {
            logDummy();
            return null;
        }

        return new Object[][] { { apiConfig.get(COOLONLY_USERNAME),
                apiConfig.get(COOLONLY_PASSWORD), apiConfig.get(THERMOSTAT_ID),
                apiConfig.get(COOLONLY_THERMOSTAT_ID) } };
    }

    /**
     * Heatonlyuser.
     * @return the object[][]
     */
    @DataProvider(name = "heatonlyuser")
    public static Object[][] heatonlyuser() {

        if (apiConfig.get(HEATONLY_USERNAME).contains("dummy")) {
            logDummy();
            return null;
        }

        return new Object[][] { { apiConfig.get(HEATONLY_USERNAME),
                apiConfig.get(HEATONLY_PASSWORD), apiConfig.get(THERMOSTAT_ID),
                apiConfig.get(HEATONLY_THERMOSTAT_ID) } };
    }

    /**
     * Twotstatone inlearning.
     * @return the object[][]
     */
    @DataProvider(name = "twotstatoneInlearning")
    public static Object[][] twotstatoneInlearning() {

        return new Object[][] { { apiConfig.get(TWO_TSAT_ONE_LEARNING_USERNAME),
                apiConfig.get(TWO_TSAT_ONE_LEARNING_PASSWORD),
                apiConfig.get(TWO_TSAT_ONE_LEARNING_THERMOSTAT_IDS),
                apiConfig.get(TWO_TSAT_ONE_LEARNING_LOCATION_ID) } };
    }
    
    /**
     * Log dummy.
     */
    private static void logDummy() {

        LogUtil.setLogString("Dummy user allocated.", true);
    }
}
