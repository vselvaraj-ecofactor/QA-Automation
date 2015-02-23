/*
 * HVACSystems_Test.java
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
 * Test class for testing Thermostat API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class HVACSystems_Test extends AbstractTest {

    @Inject
    ConsumerApiURL consumerApiURL;

    /**
     * APPS-234 Test_fetching_ hvac systems_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER },dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class, priority=1)
    public void hvacSystemsInfoValidThermostatID(final String username, final String password, final String thermostatId) {

        setLogString("Verify HVAC system data for valid thermostat.", true);

        final Response response = consumerApiURL.getHVACSystemsSavings(thermostatId, securityCookie);

        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject heatObject = (JSONObject) jsonObject.get("heat");
        final JSONObject coolObject = (JSONObject) jsonObject.get("cool");

        Assert.assertTrue(!heatObject.isEmpty() || !coolObject.isEmpty(),
                "HVAC systems info is for given user account.");
        setLogString("Verified HVAC system data for valid thermostat.", true);
    }

    /**
     * APPS-235 Hvac systems info inactive thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "inactivethermostat", dataProviderClass = ApiDataProvider.class, priority=2)
    public void hvacSystemsInfoInactiveThermostatID(final String username, final String password, final String thermostatId) {
        setLogString("Verify HVAC system data for invalid thermostat.", true);

        final Response response = consumerApiURL.getHVACSystemsSavings(thermostatId, securityCookie);

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(response.getStatus() == 400, "Expected error status 400. Actual error status: " + response.getStatus());

        setLogString("Verified HVAC system data for invalid thermostat.", true);
    }

    /**
     * APPS-238 Hvac systems info for different modes.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class, priority=3)
    public void hvacSystemsInfoForHeatAndCoolModes(final String username, final String password, final String thermostatId) {

        setLogString("Verify HVAC system data for heat only user.", true);
        setLogString("There is no user account for heat only user.", true);

        setLogString("Verify HVAC system data for cool only user.", true);
        setLogString("There is no user account for cool only user.", true);

        setLogString("Verify HVAC system data for heat and cool mode.", true);
        final Response response = consumerApiURL.getHVACSystemsSavings(thermostatId, securityCookie);

        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject heatObject = (JSONObject) jsonObject.get("heat");
        final JSONObject coolObject = (JSONObject) jsonObject.get("cool");

        Assert.assertTrue(!heatObject.isEmpty() || !coolObject.isEmpty(),
                "HVAC systems info is for given user account.");
        setLogString("Verified HVAC system data for heat and cool mode.", true);
    }


    /**
     * APPS-239 Test_fetching_ hvac systems_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER },dataProvider = "invalidThermostatId", dataProviderClass = ApiDataProvider.class, priority=4)
    public void hvacSystemsInfoInvalidThermostatID(final String username, final String password, final String thermostatId) {

        setLogString("Verify HVAC system data for invalid thermostat.", true);

        final Response response = consumerApiURL.getHVACSystemsSavings(thermostatId, securityCookie);

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(response.getStatus() == 400, "Expected error status 400. Actual error status: " + response.getStatus());

        setLogString("Verified HVAC system data for invalid thermostat.", true);
    }

    /**
     * APPS-241 Verify hvac stages.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER },dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class, priority=5)
    public void verifyHVACStages(final String username, final String password, final String thermostatId) {

        setLogString("Verify HVAC Stages.", true);

        final Response response = consumerApiURL.getHVACSystemsSavings(thermostatId, securityCookie);

        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject heatObject = (JSONObject) jsonObject.get("heat");
        final JSONObject coolObject = (JSONObject) jsonObject.get("cool");

        Assert.assertTrue(heatObject!=null , "Missing hvac systems data for heat.");
        Assert.assertTrue(coolObject!=null , "Missing hvac systems data for cool.");

        JSONObject heatObjArray = (JSONObject) heatObject.get("1");
        JSONObject coolObjArray = (JSONObject) coolObject.get("1");

        setLogString("Hvac Systems data for Heater/HP:", true);
        setLogString("-----------------------------", true);
        setLogString("Heat HVAC Type : "+ heatObjArray.get("hvac_type"), true);
        setLogString("Heat Tonnage : "+ heatObjArray.get("input_btuh"), true);
        setLogString("Heat Tonnage : "+ heatObjArray.get("hspf"), true);


        setLogString("Hvac Systems data for AC:", true);
        setLogString("-----------------------------", true);
        setLogString("Heat HVAC Type : "+ coolObjArray.get("hvac_type"), true);
        setLogString("Heat Tonnage : "+ coolObjArray.get("tonnage"), true);
        setLogString("Heat Seer : "+ coolObjArray.get("seer"), true);

        setLogString("Verified HVAC stages.", true);
    }

    /**
     * APPS-243 Hvac systems info for heater.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER },dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class, priority=6)
    public void validHVACSystemsHPHeaterAC(final String username, final String password, final String thermostatId) {

        setLogString("Verify HVAC system info for AC / HP / Heater.", true);

        final Response response = consumerApiURL.getHVACSystemsSavings(thermostatId, securityCookie);

        Assert.assertTrue(response.getStatus() == 200, "Error status: " + response.getStatus());

        setLogString("Response :'" + response + "'", true);
        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject heatObject = (JSONObject) jsonObject.get("heat");
        final JSONObject coolObject = (JSONObject) jsonObject.get("cool");

        Assert.assertTrue(heatObject!=null , "Missing hvac systems data for heat.");
        Assert.assertTrue(coolObject!=null , "Missing hvac systems data for cool.");

        JSONObject heatObjArray = (JSONObject) heatObject.get("1");
        JSONObject coolObjArray = (JSONObject) coolObject.get("1");

        setLogString("Hvac Systems data for Heater/HP:", true);
        setLogString("-----------------------------", true);
        setLogString("Heat HVAC Type : "+ heatObjArray.get("hvac_type"), true);
        setLogString("Heat Tonnage : "+ heatObjArray.get("input_btuh"), true);
        setLogString("Heat Tonnage : "+ heatObjArray.get("hspf"), true);


        setLogString("Hvac Systems data for AC:", true);
        setLogString("-----------------------------", true);
        setLogString("Heat HVAC Type : "+ coolObjArray.get("hvac_type"), true);
        setLogString("Heat Tonnage : "+ coolObjArray.get("tonnage"), true);
        setLogString("Heat Seer : "+ coolObjArray.get("seer"), true);

        setLogString("Verified HVAC system info for AC / HP / Heater.", true);
    }
}
