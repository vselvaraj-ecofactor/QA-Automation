/*
 * SetAway_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */

package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.consumerapi.ApiConfig.AWAY_URL;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.qa.automation.dao.ThermostatEventDao;
import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing Away API
 * @author sgulhar
 */
@Guice(modules = { UtilModule.class, DaoModule.class, ApiModule.class })
public class SetAway_Test extends AbstractTest {

    @Inject
    ConsumerApiURL consumerApiURL;
    @Inject
    private ThermostatEventDao thermostateventdao;
    @Inject
    protected ApiConfig apiConfig;

    private Client client;
    private static final String THERMOSTATID = "THERMOSTATID";
    public static String jsonString;

    /**
     * Test_setting_ away_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */
    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_valid_thermostatID(final String username,
            final String password, final String thermostatId) throws ParseException {

        final Response response = consumerApiURL.setAway(thermostatId, securityCookie);
        displayAPIResponse(response, "Set Away");
        int t_id = Integer.parseInt(thermostatId);
        PartitionedThermostatEvent stat = thermostateventdao.findLatestByPhase(t_id, 0);
        System.out.println(stat.getAlgorithmId());
        long actualalgo = stat.getAlgorithmId();
        long expectedalgo = -20;
        Assert.assertEquals(actualalgo, expectedalgo);

    }

    /**
     * Test_setting_ away_ data_using_invalid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "away", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_invalid_thermostatID(final String username,
            final String password) throws ParseException {

        client = ClientBuilder.newClient();
        String thermostatId = apiConfig.get(ApiConfig.INVALID_THERMOSTAT_ID);
        System.out.println("result" + thermostatId);
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        String json1 = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": ";
        String date = "\"" + DateUtil.addDaysToToday() + "\"";
        String json2 = "}";
        String finaljson = json1 + date + json2;
        final Response response = ib.put(Entity.json(finaljson));
        // displayAPIResponse(response, "Invalid ThermostatId");
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("99015"));
        Assert.assertTrue(content.contains("Requested operation not authorized for the user"));

    }

    /**
     * Test_setting_ away_ data_using_null_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "away", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_null_thermostatID(final String username,
            final String password) throws ParseException {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID,
                        apiConfig.get(ApiConfig.json_away_invalid_thermostat_null)))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        String json1 = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": ";
        String date = "\"" + DateUtil.addDaysToToday() + "\"";
        String json2 = "}";
        String finalJson = json1 + date + json2;
        final Response response = ib.put(Entity.json(finalJson));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("99015"));
        Assert.assertTrue(content.contains("Requested operation not authorized for the user"));

    }

    /**
     * Test_setting_ away_ data_using_max_coolsetpoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_max_coolsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_max_coolsetpoint);
        final Response response = ib.put(Entity.json(jsonString));
        displayAPIResponse(response, "Max cool setpoint");
        int t_id = Integer.parseInt(thermostatId);
        PartitionedThermostatEvent stat = thermostateventdao.findLatestByPhase(t_id, 0);
        System.out.println(stat.getAlgorithmId());
        long actualalgo = stat.getAlgorithmId();
        long expectedalgo = -20;
        Assert.assertEquals(actualalgo, expectedalgo);

    }

    /**
     * Test_setting_ away_ data_using_max_heatspoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_max_heatsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_max_heatsetpointset);
        final Response response = ib.put(Entity.json(jsonString));
        displayAPIResponse(response, "Max Heat Setpoint");
        int t_id = Integer.parseInt(thermostatId);
        PartitionedThermostatEvent stat = thermostateventdao.findLatestByPhase(t_id, 0);
        System.out.println(stat.getAlgorithmId());
        long actualalgo = stat.getAlgorithmId();
        long expectedalgo = -20;
        Assert.assertEquals(actualalgo, expectedalgo);

    }

    /**
     * Test_setting_ away_ data_using_over_max_coolsetpoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_over_max_coolsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_over_max_coolsetpoint);
        System.out.println("result2" + jsonString);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("15005"));
        Assert.assertTrue(content.contains("Cool setpoint out of range."));

    }

    /**
     * Test_setting_ away_ data_using_over_max_heatspoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_over_max_heatsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_over_max_heatsetpoint);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("15004"));
        Assert.assertTrue(content.contains("Heat setpoint out of range."));

    }

    /**
     * Test_setting_ away_ data_using_nullcoolsetpoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_null_coolsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_null_coolsetpoint);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40006"));
        Assert.assertTrue(content.contains("Cool setpoint cannot be null."));

    }

    /**
     * Test_setting_ away_ data_using_null_heatspoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_null_heatsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_null_heatsetpoint);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40007"));
        Assert.assertTrue(content.contains("Heat setpoint cannot be null."));

    }

    /**
     * Test_setting_ away_ data_using_junk coolsetpoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_junk_coolsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_junk_coolsetpoint);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("Cool setpoint cannot be null.")); 
        Assert.assertTrue(content.contains("40006"));
        Assert.assertTrue(content.contains("End timestamp cannot be null.")); 
        Assert.assertTrue(content.contains("40009")); 
        Assert.assertTrue(content.contains("Heat setpoint cannot be null."));
        Assert.assertTrue(content.contains("40007"));

    }

    /**
     * Test_setting_ away_ data_using_junk_heatspoint .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_using_junk_heatsetpoint(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_junk_heatsetpoint);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("Cool setpoint cannot be null.")); 
        Assert.assertTrue(content.contains("40006"));
        Assert.assertTrue(content.contains("End timestamp cannot be null.")); 
        Assert.assertTrue(content.contains("40009")); 
        Assert.assertTrue(content.contains("Heat setpoint cannot be null."));
        Assert.assertTrue(content.contains("40007"));

    }

    /**
     * Test_setting_ away_ Data_invalid_json_format .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_invalid_json_format(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_format_json);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("Cool setpoint cannot be null.")); 
        Assert.assertTrue(content.contains("40006"));
        Assert.assertTrue(content.contains("End timestamp cannot be null.")); 
        Assert.assertTrue(content.contains("40009")); 
        Assert.assertTrue(content.contains("Heat setpoint cannot be null."));
        Assert.assertTrue(content.contains("40007"));

    }

    /**
     * Test_setting_ away_ Data_invalid_month_timestamp .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_invalid_month_timestamp(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_month_timestamp);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40004"));
        Assert.assertTrue(content.contains("Invalid end time date format."));

    }

    /**
     * Test_setting_ away_ Data_invalid_year_timestamp .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_invalid_year_timestamp(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_year_timestamp);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40004"));
        Assert.assertTrue(content.contains("Invalid end time date format."));

    }

    /**
     * Test_setting_ away_ Data_invalid_day_timestamp .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws IOException
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_invalid_day_timestamp(final String username,
            final String password, final String thermostatId) throws IOException {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_day_timestamp);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40004"));
        Assert.assertTrue(content.contains("Invalid end time date format."));

    }

    /**
     * Test_setting_ away_ Data_invalid_timestamp_noT .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_invalid_format_timestamp_noT(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_format_timestamp_noT);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40004"));
        Assert.assertTrue(content.contains("Invalid end time date format."));

    }

    /**
     * Test_setting_ away_ Data_invalid_format_timestamp_K .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_invalid_format_timestamp_K(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_invalid_format_timestamp_K);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("40004"));
        Assert.assertTrue(content.contains("Invalid end time date format."));

    }

    /**
     * Test_setting_ away_ Data_missing_quotes_timestamp .
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_setting_Away_Data_missing_quotes_timestamp(final String username,
            final String password, final String thermostatId) {

        client = ClientBuilder.newClient();
        final Invocation.Builder ib = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        jsonString = apiConfig.get(ApiConfig.json_away_missing_quotes_timestamp);
        final Response response = ib.put(Entity.json(jsonString));
        String content = response.readEntity(String.class);
        setLogString("response :'" + response + "'", true);
        Assert.assertTrue(content.contains("Cool setpoint cannot be null.")); 
        Assert.assertTrue(content.contains("40006"));
        Assert.assertTrue(content.contains("End timestamp cannot be null.")); 
        Assert.assertTrue(content.contains("40009")); 
        Assert.assertTrue(content.contains("Heat setpoint cannot be null."));
        Assert.assertTrue(content.contains("40007"));

    }
    
    /**
     * deleting_Away_Data_using_valid_thermostatID.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     * @throws ParseException
     */
    
    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_deleting_Away_Data_using_valid_thermostatID(final String username,
            final String password, final String thermostatId) throws ParseException {

        final Response response = consumerApiURL.deleteAway(thermostatId, securityCookie);
        displayAPIResponse(response, "Delete Away");
        Assert.assertEquals(response.getStatus(), 202);
       /* int t_id = Integer.parseInt(thermostatId);
        PartitionedThermostatEvent stat = thermostateventdao.findLatestByPhase(t_id, 0);
        System.out.println(stat.getAlgorithmId());
        long actualalgo = stat.getAlgorithmId();
        long expectedalgo = -20;
        Assert.assertEquals(actualalgo, expectedalgo);*/

    }

}
