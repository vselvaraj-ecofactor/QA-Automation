/*
 * LocationRuntimeSavings_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing Location Runtime Savings API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class LocationRuntimeSavings_Test extends AbstractTest {

    @Inject
    private ConsumerApiURL consumerApiURL;

    /**
     * APPS-201 Test_fetching_ location_ runtime savings_ data_using_valid_location id.
     * @param username the username
     * @param password the password
     * @param locId the loc id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "locationSavings", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void locationRuntimeSavingsForValidLocationID(final String username,
            final String password, final String locationId) {

        setLogString("Verify runtime savings exists for valid location id.", true);
        final Response response = consumerApiURL.getLocationRuntimeSavings(locationId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);

        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get("months");
        final Object[] runtimes = mesgs.values().toArray();

        Assert.assertTrue(runtimes.length > 0,
                "Thermostat runtime savings not exists for given user account.");

        setLogString("Runtime savings exists for given thermostat.", true);
    }

    /**
     * APPS-202 Location runtime savings for invalid location id.
     * @param username the username
     * @param password the password
     * @param locationId the location id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "invalidlocationid", dataProviderClass = ApiDataProvider.class, priority = 2)
    public void locationRuntimeSavingsForInvalidLocationID(final String username,
            final String password, final String locationId) {

        setLogString("Verify runtime savings exists for invalid location id.", true);
        final Response response = consumerApiURL.getLocationRuntimeSavings(locationId,
                securityCookie);
        Assert.assertTrue(response.getStatus() == 404, "Error status was not 404. It was : "
                + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(content.contains("Location_id is not found"),
                "The expected response message was: {msg: 'Location_id is not found' code: 404}");
        setLogString("Verified runtime savings for invalid location id.", true);
    }

    /**
     * APPS-255 Location runtime savings for provision state.
     * @param username the username
     * @param password the password
     * @param locationId the location id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "provisioned", dataProviderClass = ApiDataProvider.class, priority = 3)
    public void locationRuntimeSavingsForProvisionState(final String username,
            final String password, final String locationId) {

        setLogString("Verify runtime savings for provisioned state.", true);
        final Response response = consumerApiURL.getLocationRuntimeSavings(locationId,
                securityCookie);

        setLogString("Response :'" + response + "'", true);

        Assert.assertTrue(response.getStatus() == 404, "Error status was not 404. It was : "
                + response.getStatus());

        setLogString("Verified runtime savings exists for provisioned state.", true);

    }

    /**
     * APPS-258 Runtime savings for six thermostat location.
     * @param username the username
     * @param password the password
     * @param locationId the location id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "sixthermostats", dataProviderClass = ApiDataProvider.class, priority = 4)
    public void runtimeSavingsForSixThermostatLocation(final String username,
            final String password, final String locationId) {

        setLogString("Verify runtime savings exists for location with six thermostats.", true);
        final Response response = consumerApiURL.getLocationRuntimeSavings(locationId,
                securityCookie);
        setLogString("Response :'" + response + "'", true);

        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject mesgs = (JSONObject) jsonObject.get("months");
        final Object[] runtimes = mesgs.values().toArray();

        Assert.assertTrue(runtimes.length > 0,
                "Thermostat runtime savings not exists for given user account.");
        setLogString("Verified Runtime savings exists for location with six thermostats.", true);

    }

    /**
     * APPS-260 - Runtime savings for account with invalid location id.
     * @param username the username
     * @param password the password
     * @param locationId the location id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "invalidlocationid", dataProviderClass = ApiDataProvider.class, priority = 5)
    public void runtimeSavingsForAccountWithInvalidLocationID(final String username,
            final String password, final String locationId) {

        setLogString("Verify runtime savings for account with invalid location id.", true);
        final Response response = consumerApiURL.getLocationRuntimeSavings(locationId,
                securityCookie);

        setLogString("Response :'" + response + "'", true);

        Assert.assertTrue(response.getStatus() == 403, "Error status was not 403. It was : "
                + response.getStatus());

        setLogString("Veried runtime savings exists for account with invalid location id.", true);

    }

    /**
     * APPS-259 Two tstats one in learning mode.
     * @param username the username
     * @param password the password
     * @param locationId the location id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "twotstatoneInlearning", dataProviderClass = ApiDataProvider.class, priority = 6)
    public void twoTstatsOneInLearningMode(final String username, final String password,
            final String thermostats, final String locationId) {

        setLogString(
                "Verify learning mode for location is false, with one thermostat in learning mode and another not in learning mode.",
                true);

        final String thermostat[] = thermostats.split("\\_");

        setLogString("Get learning mode from thermostat runtime savings api for thermostat :"
                + thermostat[0], true);
        Response response = consumerApiURL.getThermostatRuntimeSavings(thermostat[0],
                securityCookie);
        setLogString("Response :'" + response + "'", true);
        String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        JSONObject jsonObject = JsonUtil.parseObject(content);
        Boolean learningMode = (Boolean) jsonObject.get("learning_mode");
        setLogString("Thermostat Id :" + thermostat[0] + "; Learning Mode :" + learningMode, true,
                CustomLogLevel.MEDIUM);

        setLogString("---------------------------------------------------", true);
        setLogString("Get learning mode from thermostat runtime savings api for thermostat :"
                + thermostat[1], true);
        response = consumerApiURL.getThermostatRuntimeSavings(thermostat[1], securityCookie);
        setLogString("Response :'" + response + "'", true);
        content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        jsonObject = JsonUtil.parseObject(content);
        learningMode = (Boolean) jsonObject.get("learning_mode");
        setLogString("Thermostat Id :" + thermostat[0] + "; Learning Mode :" + learningMode, true,
                CustomLogLevel.MEDIUM);

        setLogString("---------------------------------------------------", true);
        setLogString("Get learning mode from location runtime savings api for current location :"
                + locationId, true);
        response = consumerApiURL.getLocationRuntimeSavings(locationId, securityCookie);
        setLogString("Response :'" + response + "'", true);

        content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        jsonObject = JsonUtil.parseObject(content);
        learningMode = (Boolean) jsonObject.get("learning_mode");

        Assert.assertFalse(learningMode, "Learning mode is true for location runtime savings.");

        setLogString(
                "Verified learning mode for location is false, with one thermostat in learning mode and another not in learning mode.",
                true);
    }
}
