/*
 * ThermostatRuntimeSavings_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.testng.*;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;

import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing Thermostat Runtime Savings API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class ThermostatRuntimeSavings_Test extends AbstractTest {

    @Inject
    private ConsumerApiURL consumerApiURL;
    private static final String RUNTIME_HRS_ACTUAL = "runtime_hours_actual";
    private static final Object HEAT = "heat";
    private static final Object COOL = "cool";
    private static final Object MONTHS = "months";
    private static final String HVAC_SYSTEMS = "hvacSystems";
    private static final String JSON_RESPONSE = "Json Response:";

    /**
     * APPS-197 Test_fetching_ thermostat_ runtime savings_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority = 1)
    public void thermostatRuntimeSavingsForvalidThermostatID(final String username,
            final String password, final String thermostatId) {

        setLogString("Verify runtime savings exists for valid thermostat.", true);

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        Assert.assertTrue(runtimes.length > 0,
                "Thermostat runtime savings not exists for given user account.");
        setLogString("Runtime savings exists for given thermostat.", true);
    }

    /**
     * APPS-199 Test_fetching_ thermostat_ runtime savings_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "learningmode", dataProviderClass = ApiDataProvider.class, priority = 2)
    public void thermostatRuntimeSavingsForLearningModeTrue(final String username,
            final String password, final String thermostatId) {

        setLogString("Verify learning mode is true for given user thermostat.", true);
        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        final String learningMode = object.get("learning_mode").toString();
        Assert.assertTrue(learningMode.equalsIgnoreCase("true"));
        setLogString("Runtime savings shows learning mode as true.", true);
    }

    /**
     * APPS-247 Thermostat runtime savings for learning mode false.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority = 3)
    public void thermostatRuntimeSavingsForLearningModeFalse(final String username,
            final String password, final String thermostatId) {

        setLogString(
                "Verify runtime savings for thermostat installed two months ago is not under learning mode.",
                true, CustomLogLevel.MEDIUM);

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        final String learningMode = object.get("learning_mode").toString();
        Assert.assertTrue(learningMode.equalsIgnoreCase("false"));
        setLogString("Runtime savings shows learning mode as false.", true);
    }

    /**
     * APPS-198 Runtime savings for invalid thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "invalidThermostatId", dataProviderClass = ApiDataProvider.class, priority = 4)
    public void runtimeSavingsForInvalidThermostatId(final String username, final String password,
            final String thermostatId) {

        setLogString("Verify runtime savings for invalid thermostat id.", true,
                CustomLogLevel.MEDIUM);

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);

        Assert.assertTrue(response.getStatus() == 404, "Error status was not 404. It was : "
                + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(content.contains("Requested operation not authorized for the user"));
    }

    /**
     * APPS-252 Runtime savings for inactive thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "inactivethermostat", dataProviderClass = ApiDataProvider.class, priority = 5)
    public void runtimeSavingsForInactiveThermostatId(final String username, final String password,
            final String thermostatId) {

        setLogString("Verify runtime savings for inactive thermostat id.", true,
                CustomLogLevel.MEDIUM);

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(response.getStatus() == 404, "Error status was not 404. It was : "
                + response.getStatus());

        Assert.assertTrue(content.contains("Thermostat_id is not found"),
                "Expected message 'Thermostat_id is not found' was missing in the response string.");
    }

    /**
     * APPS-250 Negative runtime savings thermostat.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "negativeruntimethermostat", dataProviderClass = ApiDataProvider.class, priority = 6)
    public void negativeRuntimeSavings(final String username, final String password,
            final String thermostatId) {

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        setLogString("Verify negative runtime hours for given thermostat.", true);
        for (final Object runtime : runtimes) {

            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            final float coolRuntimeHrsActual = Float.valueOf(jsonRuntimeCool.get(
                    "runtime_hours_saved").toString());
            final float heatRuntimeHrsActual = Float.valueOf(jsonRuntimeHeat.get(
                    "runtime_hours_saved").toString());

            setLogString("coolRuntimeHrsActual: " + coolRuntimeHrsActual, true);
            Assert.assertTrue(coolRuntimeHrsActual < 0,
                    "Cool run time hours actual is not negative.");
            setLogString("heatRuntimeHrsActual: " + heatRuntimeHrsActual, true);
            Assert.assertTrue(heatRuntimeHrsActual < 0,
                    "Heat run time hours actual is not negative.");
        }

        setLogString("Verified negative runtime hours for given thermostat.", true);
    }

    /**
     * APPS-203 Monthly runtime hours actual not exceeds max month hours.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority = 7)
    public void monthlyRuntimeHoursActualNotExceedsMaxMonthHours(final String username,
            final String password, final String thermostatId) {

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        for (final Object runtime : runtimes) {
            setLogString("Verify that runtime_hours_actual cannot exceed 31 (days) * 24 ( hours)",
                    true);
            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            final float coolRuntimeHrsActual = Float.valueOf(jsonRuntimeCool
                    .get(RUNTIME_HRS_ACTUAL).toString());
            final float heatRuntimeHrsActual = Float.valueOf(jsonRuntimeHeat
                    .get(RUNTIME_HRS_ACTUAL).toString());

            setLogString("coolRuntimeHrsActual: " + coolRuntimeHrsActual, true);
            Assert.assertTrue(coolRuntimeHrsActual < 744,
                    "Cool run time hours actual is greater than maximum month hours");
            setLogString("heatRuntimeHrsActual: " + heatRuntimeHrsActual, true);
            Assert.assertTrue(heatRuntimeHrsActual < 744,
                    "Heat run time hours actual is greater than maximum month hours");
        }
    }

    /**
     * APPS-208 Monthly runtime hours only with2 decimal precission.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "savingsprecision", dataProviderClass = ApiDataProvider.class, priority = 8)
    public void monthlyRuntimeHoursOnlyWith2DecimalPrecission(final String username,
            final String password, final String thermostatId) {

        setLogString("Verify runtime savings have two decimal point precision.", true);
        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        setLogString("Verify that runtime_hours_actual have 2 decimal point precision.", true,
                CustomLogLevel.MEDIUM);
        for (final Object runtime : runtimes) {

            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            final String coolRuntimeHrsActual = jsonRuntimeCool.get(RUNTIME_HRS_ACTUAL).toString();

            final String heatRuntimeHrsActual = jsonRuntimeHeat.get(RUNTIME_HRS_ACTUAL).toString();

            setLogString("coolRuntimeHrsActual: " + coolRuntimeHrsActual, true, CustomLogLevel.HIGH);
            Assert.assertTrue(
                    coolRuntimeHrsActual.split("\\.").length > 1 ? coolRuntimeHrsActual
                            .split("\\.")[1].length() <= 2 : true,
                    "Cool run time hours have more than 2 decimal point precision.");

            setLogString("heatRuntimeHrsActual: " + heatRuntimeHrsActual, true, CustomLogLevel.HIGH);
            Assert.assertTrue(
                    heatRuntimeHrsActual.split("\\.").length > 1 ? heatRuntimeHrsActual
                            .split("\\.")[1].length() <= 2 : true,
                    "Heat run time hours have more than 2 decimal point precision.");
        }
        setLogString("Verified that runtime savings have two decimal point precision.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * APPS-205 Verify thermostat disconnected.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "missingsavingsmonth", dataProviderClass = ApiDataProvider.class, priority = 9)
    public void thermostatDisconnected(final String username, final String password,
            final String thermostatId) throws ParseException {

        setLogString(
                "Verify that given thermostat has missing data to simulate disconnected thermostat.",
                true);
        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());
        setLogString("Response :'" + response + "'", true);

        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);

        final Object[] monthyear = mesgs.keySet().toArray();

        Assert.assertTrue(monthyear.length > 0, "No runtime savings data found.");

        Calendar calendar;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        final ArrayList<Calendar> yearMonths = new ArrayList<>();

        for (final Object runtime : monthyear) {
            final Date date = dateFormat.parse(runtime.toString());

            calendar = Calendar.getInstance();
            calendar.setTime(date);

            yearMonths.add(calendar);
        }

        Collections.sort(yearMonths);

        boolean offline = false;
        for (int i = 0; i < yearMonths.size() - 1; i++) {
            if (checkIfMonthGapsExistsBetweenCalendar(yearMonths.get(i), yearMonths.get(i + 1))) {
                offline = true;
                break;
            }
        }

        Assert.assertTrue(offline,
                "Unable to verify missing monthly runtimes in given thermostat (Disconnected).");
        setLogString(
                "Verified that given thermostat has missing data to simulate disconnected thermostat.",
                true);
    }

    /**
     * Check if month gaps exists between calendar.
     * @param calendar1 the calendar1
     * @param calendar2 the calendar2
     * @return true, if successful
     */
    private boolean checkIfMonthGapsExistsBetweenCalendar(final Calendar calendar1,
            final Calendar calendar2) {

        calendar1.add(Calendar.MONTH, 1);
        return calendar1.compareTo(calendar2) != 0;
    }

    /**
     * APPS-251 Monthly runtime hours for provisioned user.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "provisioned", dataProviderClass = ApiDataProvider.class, priority = 10)
    public void monthlyRuntimeHoursForProvisionedUser(final String username, final String password,
            final String thermostatId) {

        setLogString("Verify runtime savings for provisioned thermostat.", true);
        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());
        setLogString("Response :'" + response + "'", true);

        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        Assert.assertTrue(runtimes.length == 0,
                "Thermostat runtime savings exists for provisioned user.");
    }

    /**
     * APPS-248 No data for runtime savings.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "nosavings", dataProviderClass = ApiDataProvider.class, priority = 11)
    public void noDataForRuntimeSavings(final String username, final String password,
            final String thermostatId) {

        setLogString("Verify runtime savings API for user with no savings data.", true);

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);

        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());
        setLogString("Response :'" + response + "'", true);

        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        for (final Object runtime : runtimes) {

            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            final String coolRuntimeHrsActual = jsonRuntimeCool.get(RUNTIME_HRS_ACTUAL).toString();

            final String heatRuntimeHrsActual = jsonRuntimeHeat.get(RUNTIME_HRS_ACTUAL).toString();

            setLogString("coolRuntimeHrsActual: " + coolRuntimeHrsActual, true, CustomLogLevel.HIGH);
            Assert.assertTrue(coolRuntimeHrsActual.contains("0.0"),
                    "cool runtime savings data available for user with no savings.");

            setLogString("heatRuntimeHrsActual: " + heatRuntimeHrsActual, true, CustomLogLevel.HIGH);
            Assert.assertTrue(heatRuntimeHrsActual.contains("0.0"),
                    "Heat runtime savings data available for user with no savings.");
        }
        setLogString("Verified runtime savings API for user with no savings data.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * APPS-226 Verify tstat runtimes for heat only user.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "heatonlyuser", dataProviderClass = ApiDataProvider.class, priority = 12)
    public void verifyTstatRuntimesForHeatOnlyUser(final String username, final String password,
            final String thermostatId) {

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        setLogString("Verify only heat runtime_hours exists for given thermostat.", true);
        for (final Object runtime : runtimes) {

            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            Assert.assertTrue(jsonRuntimeCool != null, "Cool run time hours not exists.");
            Assert.assertTrue(jsonRuntimeHeat != null, "Heat run time hours not exists.");

        }
        setLogString("Verified heat runtime_hours alone exists for given thermostat.", true);
    }

    /**
     * APPS-226 Verify tstat runtimes for cool only user.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "coolonlyuser", dataProviderClass = ApiDataProvider.class, priority = 13)
    public void verifyTstatRuntimesForCoolOnlyUser(final String username, final String password,
            final String thermostatId) {

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        setLogString("Verify cool runtime_hours alone exists for given thermostat.", true);
        for (final Object runtime : runtimes) {

            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            Assert.assertTrue(jsonRuntimeCool != null, "Cool run time hours not exists.");
            Assert.assertTrue(jsonRuntimeHeat != null, "Heat run time hours not exists.");

        }
        setLogString("Verified cool runtime_hours alone exists for given thermostat.", true);
    }

    /**
     * APPS-226 Verify tstat runtimes for heat and cool user.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority = 14)
    public void verifyTstatRuntimesForHeatAndCoolUser(final String username, final String password,
            final String thermostatId) {

        final Response response = consumerApiURL.getThermostatRuntimeSavings(thermostatId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString(JSON_RESPONSE, true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get(MONTHS);
        final Object[] runtimes = mesgs.values().toArray();

        setLogString("Verify heat and cool runtime_hours exists for given thermostat.", true);
        for (final Object runtime : runtimes) {

            final JSONObject json = (JSONObject) runtime;
            final JSONObject jsonRuntimeCool = (JSONObject) json.get(COOL);
            final JSONObject jsonRuntimeHeat = (JSONObject) json.get(HEAT);

            Assert.assertTrue(jsonRuntimeCool != null, "Cool run time hours not exists.");
            Assert.assertTrue(jsonRuntimeHeat != null, "Heat run time hours not exists.");

        }
        setLogString("Verified heat and cool runtime_hours for given thermostat.", true);
    }
}
