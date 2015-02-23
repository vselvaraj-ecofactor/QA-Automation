/*
 * ApiConfig.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class ApiConfig.
 */
public class ApiConfig extends BaseConfig {

	//URL'S
	public static final String AUTH_URL = "authenticate.url";
	public static final String USER_URL = "user.url";
	public static final String LOCATION_URL = "location.url";
	public static final String LOCATION_PREFERENCES_URL = "locationpreference.url";
	public static final String LOCATION_RUNTIME_SAVINGS_URL = "locationsavings.url";
	public static final String THERMOSTAT_URL = "thermostat.url";
	public static final String THERMOSTAT_STATE_URL = "thermostatstate.url";
	public static final String HVACSYSTEMS_URL = "hvacsystems.url";
	public static final String THERMOSTAT_RUNTIME_SAVINGS_URL = "thermostatsavings.url";
	public static final String SCHEDULE_URL = "schedule.url";
	public static final String WEATHER_DATA_URL = "weatherdata.url";
	public static final String AWAY_URL = "away.url";
	public static final String ACCUMULATED_DOLLAR_SAVINGS_URL = "dollarsavings.url";
	public static final String TIMEZONE_URL = "timezone.url";
	public static final String ECP_ENERGY_SAVINGS_URL = "ecpenergypricing.url";

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String LOCATION_ID = "location.id";
	public static final String ECP_CORE_ID = "ecpcore.id";

	public static final String AUTHENTICATOR_USERNAME = "authenticator.username";
	public static final String AUTHENTICATOR_PASSWORD = "authenticator.password";

	public static final String THERMOSTAT_ID = "thermostat.id";
	public static final String SCHEDULE_ID = "schedule.id";
	public static final String TIMEZONE_ZIPCODE = "timezone.zipcode";
	public static final String USER_ID = "user.id";
	public static final String ZIOCODE_CITYNAME = "weather.zipcodecityname";

	public static final String INVALID_USERNAME = "invalid.username";
    public static final String INVALID_PASSWORD = "invalid.password";
    public static final String INVALID_THERMOSTAT_ID = "invalid.thermostat.id";
    public static final String INVALID_LOCATION_ID = "invalid.location.id";

    public static final String LEARNING_MODE_USERNAME = "learningmode.username";
    public static final String LEARNING_MODE_PASSWORD = "learningmode.password";
    public static final String LEARNING_MODE_THERMOSTAT_ID = "learningmode.thermostat.id";

    public static final String MISSING_SAVINGS_MONTH_USERNAME = "missingsavingsmonths.username";
    public static final String MISSING_SAVINGS_MONTH_PASSWORD = "missingsavingsmonths.password";
    public static final String MISSING_SAVINGS_MONTH_THERMOSTAT_ID = "missingsavingsmonths.thermostat.id";

    public static final String OFFLINE_USERNAME = "offline.username";
    public static final String OFFLINE_PASSWORD = "offline.password";
    public static final String OFFLINE_THERMOSTAT_ID = "offline.thermostat.id";
    public static final String SAVINGS_PRECISION_USERNAME = "savingsprecision.username";
    public static final String SAVINGS_PRECISION_PASSWORD = "savingsprecision.password";
    public static final String SAVINGS_PRECISION_THERMOSTAT_ID = "savingsprecision.thermostat.id";
    public static final String SAVINGS_PRECISION_LOCATION_ID = "savingsprecision.location.id";

    public static final String PROVISIONED_USERNAME = "provisioned.username";
    public static final String PROVISIONED_PASSWORD = "provisioned.password";
    public static final String PROVISIONED_THERMOSTAT_ID = "provisioned.thermostat.id";

    public static final String NO_SAVINGS_USERNAME = "nosavings.username";
    public static final String NO_SAVINGS_PASSWORD = "nosavings.password";
    public static final String NO_SAVINGS_THERMOSTAT_ID = "nosavings.thermostat.id";

    public static final String SIX_THERMOSTATS_USERNAME = "sixthermostats.username";
    public static final String SIX_THERMOSTATS_PASSWORD = "sixthermostats.password";
    public static final String SIX_THERMOSTATS_LOCATION_ID = "sixthermostats.location.id";

    public static final String NEGATIVE_RUNTIMESAVINGS_USERNAME = "negativeruntimesavings.username";
    public static final String NEGATIVE_RUNTIMESAVINGS_PASSWORD = "negativeruntimesavings.password";
    public static final String NEGATIVE_RUNTIMESAVINGS_THERMOSTAT_ID = "negativeruntimesavings.thermostat.id";

    public static final String INACTIVE_USERNAME = "inactive.username";
    public static final String INACTIVE_PASSWORD = "inactive.password";
    public static final String INACTIVE_THERMOSTAT_ID = "inactive.thermostat.id";

    public static final String COOLONLY_USERNAME = "coolonly.username";
    public static final String COOLONLY_PASSWORD = "coolonly.password";
    public static final String COOLONLY_THERMOSTAT_ID = "coolonly.thermostat.id";

    public static final String HEATONLY_USERNAME = "heatonly.username";
    public static final String HEATONLY_PASSWORD = "heatonly.password";
    public static final String HEATONLY_THERMOSTAT_ID = "heatonly.thermostat.id";

    public static final String TWO_TSAT_ONE_LEARNING_USERNAME = "twotstatoneInlearning.username";
    public static final String TWO_TSAT_ONE_LEARNING_PASSWORD = "twotstatoneInlearning.password";
    public static final String TWO_TSAT_ONE_LEARNING_THERMOSTAT_IDS = "twotstatoneInlearning.thermostat.ids";
    public static final String TWO_TSAT_ONE_LEARNING_LOCATION_ID = "twotstatoneInlearning.location.id";

    public static final String DB_EW20_HEALTH_URL = "dbew20Health.url";
    
    public static final String json_away_invalid_thermostat = "json.away.invalid.thermostat";
    public static final String json_away_invalid_thermostat_null = "json.away.invalid.thermostat.null";
    public static final String json_away_max_coolsetpoint = "json.away.max.coolsetpoint";
    public static final String json_away_max_heatsetpointset = "json.away.max.heatsetpointset";
    public static final String json_away_over_max_coolsetpoint = "json.away.over.max.coolsetpoint";
    public static final String json_away_over_max_heatsetpoint = "json.away.over.max.heatsetpoint";
    public static final String json_away_invalid_null_coolsetpoint = "json.away.invalid.null.coolsetpoint";
    public static final String json_away_invalid_null_heatsetpoint = "json.away.invalid.null.heatsetpoint";
    public static final String json_away_invalid_junk_coolsetpoint = "json.away.invalid.junk.coolsetpoint";
    public static final String json_away_invalid_junk_heatsetpoint = "json.away.invalid.junk.heatsetpoint";
    public static final String json_away_invalid_format_json = "json.away.invalid.format.json";
    public static final String json_away_invalid_month_timestamp = "json.away.invalid.month.timestamp";
    public static final String json_away_invalid_day_timestamp = "json.away.invalid.day.timestamp";
    public static final String json_away_invalid_year_timestamp = "json.away.invalid.year.timestamp";
    public static final String json_away_invalid_format_timestamp_noT = "json.away.invalid.format.timestamp_noT";
    public static final String json_away_invalid_format_timestamp_K = "json.away.invalid.format.timestamp_K";
    public static final String json_away_missing_quotes_timestamp = "json.away.missing.quotes.timestamp";
    
    public static final String JSON_STATE_INVALID_HVAC_MODE_EMPTY= "json.state.invalid.hvac.mode.empty";
    public static final String JSON_STATE_INVALID_HVAC_MODE_EMPTYSPACE = "json.state.invalid.hvac.mode.emptyspace";
    public static final String JSON_STATE_INVALID_HVAC_MODE_NULL = "json.state.invalid.hvac.mode.null";
    public static final String JSON_STATE_INVALID_HVAC_MODE_NUMBERS = "json.state.invalid.hvac.mode.numbers";
    public static final String JSON_STATE_INVALID_HVAC_MODE_MULTICOMMASEP = "json.state.invalid.hvac.mode.multicommasep";
    public static final String JSON_STATE_INVALID_HVAC_MODE_MULTITABSEP = "json.state.invalid.hvac.mode.multitabsep";
    public static final String JSON_STATE_INVALID_HVAC_MODE_NOQUOTES = "json.state.invalid.hvac.mode.noquotes";
    public static final String JSON_STATE_INVALID_HVAC_MODE_NOQUOTESEMPTY = "json.state.invalid.hvac.mode.noquotesempty";
    public static final String JSON_STATE_INVALID_HVAC_MODE_NOQUOTESNULL = "json.state.invalid.hvac.mode.noquotesnull";
    public static final String JSON_STATE_INVALID_HVAC_MODE_SINGLEQUOTES = "json.state.invalid.hvac.mode.singlequotes";
    public static final String JSON_STATE_INVALID_HVAC_MODE_NONEXISTING = "json.state.invalid.hvac.mode.nonexisting";
    public static final String JSON_STATE_INVALID_HVAC_MODE_UPPERCASE = "json.state.invalid.hvac.mode.uppercase";
    public static final String JSON_STATE_INVALID_FAN_MODE_EMPTY = "json.state.invalid.fan.mode.empty";
    public static final String JSON_STATE_INVALID_FAN_MODE_EMPTYSPACE = "json.state.invalid.fan.mode.emptyspace";
    public static final String JSON_STATE_INVALID_FAN_MODE_NULL = "json.state.invalid.fan.mode.null";
    public static final String JSON_STATE_INVALID_FAN_MODE_NUMBERS = "json.state.invalid.fan.mode.numbers";
    public static final String JSON_STATE_INVALID_FAN_MODE_MULTICOMMASEP = "json.state.invalid.fan.mode.multicommasep";
    public static final String JSON_STATE_INVALID_FAN_MODE_MULTITABSEP = "json.state.invalid.fan.mode.multitabsep";
    public static final String JSON_STATE_INVALID_FAN_MODE_NOQUOTES = "json.state.invalid.fan.mode.noquotes";
    public static final String JSON_STATE_INVALID_FAN_MODE_NOQUOTESEMPTY = "json.state.invalid.fan.mode.noquotesempty";
    public static final String JSON_STATE_INVALID_FAN_MODE_NOQUOTESNULL = "json.state.invalid.fan.mode.noquotesnull";
    public static final String JSON_STATE_INVALID_FAN_MODE_SINGLEQUOTES = "json.state.invalid.fan.mode.singlequotes";
    public static final String JSON_STATE_INVALID_FAN_MODE_NONEXISTING = "json.state.invalid.fan.mode.nonexisting";
    public static final String JSON_STATE_INVALID_FAN_MODE_UPPERCASE = "json.state.invalid.fan.mode.uppercase";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_EMPTY = "json.state.invalid.coolsetpoint.empty";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_ZERO = "json.state.invalid.coolsetpoint.zero";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_NULL = "json.state.invalid.coolsetpoint.null";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_BOUNDARYLOW = "json.state.invalid.coolsetpoint.boundarylow";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_BOUNDARYHIGH = "json.state.invalid.coolsetpoint.boundaryhigh";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_DECIMALCOMMA = "json.state.invalid.coolsetpoint.decimalcomma";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_DECIMALDOT = "json.state.invalid.coolsetpoint.decimaldot";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_DOUBLEQUOTES = "json.state.invalid.coolsetpoint.doublequotes";
    public static final String JSON_STATE_INVALID_COOLSETPOINT_SINGLEQUOTES = "json.state.invalid.coolsetpoint.singlequotes";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_EMPTY = "json.state.invalid.heatsetpoint.empty";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_ZERO = "json.state.invalid.heatsetpoint.zero";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_NULL = "json.state.invalid.heatsetpoint.null";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_BOUNDARYLOW = "json.state.invalid.heatsetpoint.boundarylow";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_BOUNDARYHIGH = "json.state.invalid.heatsetpoint.boundaryhigh";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_DECIMALCOMMA = "json.state.invalid.heatsetpoint.decimalcomma";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_DECIMALDOT = "json.state.invalid.heatsetpoint.decimaldot";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_DOUBLEQUOTES = "json.state.invalid.heatsetpoint.doublequotes";
    public static final String JSON_STATE_INVALID_HEATSETPOINT_SINGLEQUOTES = "json.state.invalid.heatsetpoint.singlequotes";
    public static final String JSON_STATE_VALID_HVAC_MODE_COOL = "json.state.valid.hvac.mode.cool";
    public static final String JSON_STATE_VALID_HVAC_MODE_HEAT = "json.state.valid.hvac.mode.heat";
    public static final String JSON_STATE_VALID_COOLSETPOINT = "json.state.valid.coolsetpoint";
    public static final String JSON_STATE_VALID_HEATSETPOINT = "json.state.valid.heatsetpoint";
    
    
    public static final String HEATPUMP_USER = "heatpump.user";
    public static final String HEATPUMP_PASSWORD = "heatpump.password";

	/**
	 * Instantiates a new api config.
	 * @param driverConfig the driver config
	 */
	@Inject
	public ApiConfig(final DriverConfig driverConfig) {

		super(driverConfig.get(DriverConfig.ENV));
		load("api.properties");
	}
}
