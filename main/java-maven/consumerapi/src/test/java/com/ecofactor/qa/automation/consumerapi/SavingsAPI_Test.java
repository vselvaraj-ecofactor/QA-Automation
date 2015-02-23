/*
 * SavingsAPI_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;
import java.io.IOException;
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
public class SavingsAPI_Test extends AbstractTest {

    @Inject
    private ConsumerApiURL consumerApiURL;

    private static final String THERMOSTAT = "thermostat";
    private static final String LOCATION = "location";
    private static final String PUT = "put";
    private static final String POST = "post";
    private static final String DELETE = "delete";
    private static final String PATCH = "patch";

    /**
     * APPS-209 Verify database e w20 health.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void verifyDatabaseEW20Health(final String username, final String password,
            final String thermostatId) {

        setLogString("Verify DB health status for ew20.", true);

        final Response response = consumerApiURL.getEW20DBHealth(securityCookie);

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);

        Assert.assertTrue(jsonObject.size() > 0, "DB health status for ew20 does not exists.");
        setLogString("Verified DB health status for ew20.", true);
    }

    /**
     * APP-236 Verify delete call on savings ap is.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "apimethodsallowed", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void verifyDeleteCallOnSavingsAPIs(final String username, final String password,
            final String thermostatId, final String locationId) {

        setLogString("Verify delete call on thermostat runtime savings API.", true);
        setLogString("----------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, thermostatId, DELETE, THERMOSTAT);
        setLogString("Verified delete call on thermostat runtime savings API.", true);

        setLogString("Verify delete call on location runtime savings API.", true);
        setLogString("--------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, locationId, DELETE, LOCATION);
        setLogString("Verified delete call on location runtime savings API.", true);
    }

    /**
     * APPS-233 Verify put call on savings ap is.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @param locationId the location id
     * @throws JsonGenerationException the json generation exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "apimethodsallowed", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void verifyPutCallOnSavingsAPIs(final String username, final String password,
            final String thermostatId, final String locationId) {

        setLogString("Verify put call on thermostat runtime savings API.", true);
        setLogString("----------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, thermostatId, PUT, THERMOSTAT);
        setLogString("Verified put call on thermostat runtime savings API.", true);

        setLogString("Verify put call on location runtime savings API.", true);
        setLogString("--------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, locationId, PUT, LOCATION);
        setLogString("Verified put call on location runtime savings API.", true);
    }

    /**
     * APPS-232 Verify post call on savings ap is.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @param locationId the location id
     * @throws JsonGenerationException the json generation exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "apimethodsallowed", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void verifyPostCallOnSavingsAPIs(final String username, final String password,
            final String thermostatId, final String locationId) {

        setLogString("Verify post call on thermostat runtime savings API.", true);
        setLogString("----------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, thermostatId, POST, THERMOSTAT);
        setLogString("Verified post call on thermostat runtime savings API.", true);

        setLogString("Verify post call on location runtime savings API.", true);
        setLogString("--------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, locationId, POST, LOCATION);
        setLogString("Verified post call on location runtime savings API.", true);
    }

    /**
     * APPS-237 Verify patch call on savings ap is.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @param locationId the location id
     * @throws JsonGenerationException the json generation exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "apimethodsallowed", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void verifyPatchCallOnSavingsAPIs(final String username, final String password,
            final String thermostatId, final String locationId) {

        setLogString("Verify patch call on thermostat runtime savings API.", true);
        setLogString("----------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, thermostatId, PATCH, THERMOSTAT);
        setLogString("Verified patch call on thermostat runtime savings API.", true);

        setLogString("Verify patch call on location runtime savings API.", true);
        setLogString("--------------------------------------------------", true);
        verifyRestMethodSupportOnSavingsApis(username, password, locationId, PATCH, LOCATION);
        setLogString("Verified patch call on location runtime savings API.", true);
    }

    /**
     * Verify rest method support on savings apis.
     * @param username the username
     * @param password the password
     * @param id the id
     * @param method the method
     * @param apiRequired the api required
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    private void verifyRestMethodSupportOnSavingsApis(final String username, final String password,
            final String locationOrTstatId, final String method, final String apiRequired) {

        Response response = null;
        if (apiRequired.contains(THERMOSTAT)) {
            response = consumerApiURL.thermostatRuntimeSavings(locationOrTstatId, securityCookie,
                    method);
        } else if (apiRequired.contains(LOCATION)) {
            response = consumerApiURL.locationRuntimeSavings(locationOrTstatId, securityCookie,
                    method);
        }
        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        if (method.contains(DELETE) || method.contains(PUT) || method.contains(POST)
                || method.contains(PATCH)) {
            Assert.assertTrue(content.contains("Method not allowed."), "User is able to make "
                    + method + " call on this API.");

            Assert.assertTrue(response.getStatus() == 400,
                    "Expected status 400. Actual status is :" + response.getStatus());
        }
    }

}
