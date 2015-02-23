/*
 * ConsumerApiURL.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.consumerapi.ApiConfig.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.annotations.Guice;

import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Constants class containing the URLs for different Consumer API calls
 * @author npaila
 */
@Guice(modules = { UtilModule.class})
public class ConsumerApiURL {

    private static final String LOCATIONID = "LOCATIONID";
    private static final String THERMOSTATID = "THERMOSTATID";
    private Client client;

    @Inject
    protected ApiConfig apiConfig;

    /**
     * Gets the accumulated dollar savings.
     * @param locationId the location id
     * @param securityCookie the security cookie
     * @return the accumulated dollar savings
     */
    public Response getAccumulatedDollarSavings(final String locationId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(ACCUMULATED_DOLLAR_SAVINGS_URL).replaceFirst(LOCATIONID,
                        locationId)).request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the away savings.
     * @param locationId the location id
     * @param securityCookie the security cookie
     * @return the away savings
     */
    public Response getAwaySavings(final String locationId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(AWAY_URL).replaceFirst(LOCATIONID, locationId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the hVAC systems savings.
     * @param thermostatId the thermostat id
     * @param securityCookie the security cookie
     * @return the hVAC systems savings
     */
    public Response getHVACSystemsSavings(final String thermostatId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(HVACSYSTEMS_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the locations savings.
     * @param locationId the location id
     * @param securityCookie the security cookie
     * @return the locations savings
     */
    public Response getLocationsSavings(final String locationId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(LOCATION_URL).replaceFirst(LOCATIONID, locationId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the location preference savings.
     * @param locationId the location id
     * @param securityCookie the security cookie
     * @return the location preference savings
     */
    public Response getLocationPreferenceSavings(final String locationId,
            final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(LOCATION_PREFERENCES_URL)
                        .replaceFirst(LOCATIONID, locationId)).request(MediaType.APPLICATION_JSON)
                .cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the location runtime savings.
     * @param locationId the location id
     * @param securityCookie the security cookie
     * @return the location runtime savings
     */
    public Response getLocationRuntimeSavings(final String locationId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(LOCATION_RUNTIME_SAVINGS_URL).replaceFirst(LOCATIONID,
                        locationId)).request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the schedule savings.
     * @param scheduleId the schedule id
     * @param securityCookie the security cookie
     * @return the schedule savings
     */
    public Response getScheduleSavings(final String scheduleId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(SCHEDULE_URL).replaceFirst("SCHEDULEID", scheduleId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the thermostat savings.
     * @param thermostatId the thermostat id
     * @param securityCookie the security cookie
     * @return the thermostat savings
     */
    public Response getThermostatSavings(final String thermostatId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(THERMOSTAT_URL).replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Get thermostat runtime savings api.
     * @param thermostatId the thermostat id
     * @param securityCookie the security cookie
     * @return the invocation. builder
     */
    public Response getThermostatRuntimeSavings(final String thermostatId,
            final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(THERMOSTAT_RUNTIME_SAVINGS_URL).replaceFirst(THERMOSTATID,
                        thermostatId)).request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the thermostat state savings.
     * @param thermostatId the thermostat id
     * @param securityCookie the security cookie
     * @return the thermostat state savings
     */
    public Response getThermostatStateSavings(final String thermostatId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(THERMOSTAT_STATE_URL)
                        .replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }
    
    /**
     * Gets the thermostat away API.
     * @param thermostatId the thermostat id
     * @param securityCookie the security cookie
     * @return the thermostat state savings
     * @throws ParseException 
     */
    public Response setAway(final String thermostatId, final Cookie securityCookie) throws ParseException {

        client = ClientBuilder.newClient();
        String json1 = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": ";
        String date = "\""+DateUtil.addDaysToToday()+"\"";
        String json2 = "}";
        String jsonString = json1+date+json2;     
        System.out.println(jsonString);
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(AWAY_URL)
                        .replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.put(Entity.json(jsonString));
    }
    
    public Response deleteAway(final String thermostatId, final Cookie securityCookie) throws ParseException {

        client = ClientBuilder.newClient();   
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(AWAY_URL)
                        .replaceFirst(THERMOSTATID, thermostatId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.delete();
    }

    /**
     * Gets the timezone savings.
     * @param zipCode the zip code
     * @param securityCookie the security cookie
     * @return the timezone savings
     */
    public Response getTimezoneSavings(final String zipCode, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(SCHEDULE_URL).replaceFirst("ZIPCODE", zipCode))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);

        return invocationBuilder.get();
    }

    /**
     * Gets the user savings.
     * @param userId the user id
     * @param securityCookie the security cookie
     * @return the user savings
     */
    public Response getUserSavings(final String userId, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(USER_URL).replaceFirst("USERID", userId))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the weather data savings.
     * @param zipCode the zip code
     * @param securityCookie the security cookie
     * @return the weather data savings
     */
    public Response getWeatherDataSavings(final String zipCode, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(WEATHER_DATA_URL).replaceFirst("ZIPCODE_CITYNAME", zipCode))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the eCP core energy savings.
     * @param ecpcoreid the ecpcoreid
     * @param securityCookie the security cookie
     * @return the eCP core energy savings
     */
    public Response getECPCoreEnergySavings(final String ecpcoreid, final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(ECP_ENERGY_SAVINGS_URL)
                        .replaceFirst("ECP_CORE_ID", ecpcoreid))
                .request(MediaType.APPLICATION_JSON).cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Gets the e w20 db health.
     * @param securityCookie the security cookie
     * @return the e w20 db health
     */
    public Response getEW20DBHealth(final Cookie securityCookie) {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(DB_EW20_HEALTH_URL)).request(MediaType.APPLICATION_JSON)
                .cookie(securityCookie);
        return invocationBuilder.get();
    }

    /**
     * Thermostat runtime savings.
     * @param thermostatId the thermostat id
     * @param securityCookie the security cookie
     * @param method the method
     * @return the response
     * @throws JsonGenerationException the json generation exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Response thermostatRuntimeSavings(final String thermostatId,
            final Cookie securityCookie, final String method) {

        final StringWriter thermostatJson = new StringWriter();

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(THERMOSTAT_RUNTIME_SAVINGS_URL).replaceFirst(THERMOSTATID,
                        thermostatId)).request(MediaType.APPLICATION_JSON).cookie(securityCookie);
//thermostatId
        return getResponse(method, invocationBuilder, thermostatJson.toString());

    }

    /**
     * Location runtime savings.
     * @param locationId the location id
     * @param securityCookie the security cookie
     * @param method the method
     * @return the response
     * @throws JsonGenerationException the json generation exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Response locationRuntimeSavings(final String locationId, final Cookie securityCookie,
            final String method) {

        final StringWriter locationJson = new StringWriter();

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(apiConfig.get(THERMOSTAT_RUNTIME_SAVINGS_URL).replaceFirst(LOCATION_ID,
                        locationId)).request(MediaType.APPLICATION_JSON).cookie(securityCookie);

        return getResponse(method, invocationBuilder, locationJson.toString());

    }

    /**
     * Gets the response.
     * @param method the method
     * @param invocationBuilder the invocation builder
     * @param jsonString the json string
     * @return the response
     */
    private Response getResponse(final String method, final Invocation.Builder invocationBuilder,
            final String jsonString) {

        invocationBuilder.accept(MediaType.APPLICATION_JSON);
        Response response = null;
        if (method.contains("post") || method.contains("patch")) {
            response = (Response) invocationBuilder.post(Entity.json(jsonString));
        } else if (method.contains("delete")) {
            response = invocationBuilder.delete();
        } else if (method.contains("put")) {
            response = (Response) invocationBuilder.put(Entity.json(jsonString));
        }
        return response;
    }

}
