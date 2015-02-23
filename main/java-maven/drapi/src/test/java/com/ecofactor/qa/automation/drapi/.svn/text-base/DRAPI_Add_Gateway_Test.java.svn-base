/*
 * DRAPI_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.drapi.data.DRAPIDataProvider;
import com.ecofactor.qa.automation.drapi.DRApiConfig;
import com.ecofactor.qa.automation.drapi.DRApiModule;
import com.ecofactor.qa.automation.drapi.HTTPSClient;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * The Class DRAPI_Test.
 * @author $Author:vlobanov$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class DRAPI_Add_Gateway_Test extends AbstractTest {

    /**
     * String value is common for all test cases.
     * @param url is url
     * @param targetJson is targetJson
     * @return String
     */
    public String commonJson(final String url, final String targetJson) {

        String json = targetJson;
        json = apiconfig
                .get(DRApiConfig.TARGET_GATEWAY_JSON)
                .replaceFirst("<gateway_id>", apiconfig.get(DRApiConfig.CORRECT_GATEWAY_ID_NVE))
                .replaceFirst("<start_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 10)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 25)));
        setLogString("URL Values of the API \n" + url + "\n" + json, true);
        return json;
    }

    @Inject
    private DRApiConfig apiconfig;

    /**
     * Before method.
     * @param method the method
     * @param param the param
     * @see com.ecofactor.qa.automation.consumerapi.AbstractTest#beforeMethod(java.lang.reflect.Method,
     *      java.lang.Object[])
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final Method method, final Object[] param) {

        logUtil.logStart(method, param, null);
        startTime = System.currentTimeMillis();
    }

    /**
     * Test_create_dr_event_for_one_gateway_and_add_gateway_to_it.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drEventForGateway(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add gateway
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add Gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);
    }

    /**
     * Test_create_dr_event_for_one_gateway_and_cancel_it_and_try_to_add_gateway_to_it.
     */

    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 2)
    public void drEventForGateway_add_gateway_for_canceled_event(final String drUrl,
            final String programID, final String eventID, final String targetType,
            final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);

        /**
         * add Gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        // JSONObject jsonObject1 = JsonUtil.parseObject(resultValue1);
        // String apirespons = jsonObject1.get("errors").toString();
        // JSONObject jsonObject2 = JsonUtil.parseObject(apirespons);
        // String eventName1 = jsonObject2.get("code").toString();
        // String eventName2 = jsonObject2.get("msg").toString();
        //
        // System.out.println(eventName1);
        // System.out.println(eventName2);

    }

    /**
     * verify user can add same gateway only once
     */

    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 3)
    public void drEventForGateway_add_same_gateway_twice(final String drUrl,
            final String programID, final String eventID, final String targetType,
            final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add Gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);


        /**
         * add same Gateway
         */
        final HttpResponse response3 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response3.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response3.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue3 = HTTPSClient.getResultString(response3.getEntity());
        setLogString("add gateway response :'" + resultValue3 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);

    }

    /**
     * Test_create_dr_event_add_non_existing_gateway.
     */

    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 4)
    public void drEventForComcast_add_non_existing_gateway(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add nonexisting Gateway
         */

        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.NO_EXISTING_GATEWAY_ID)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);

    }

    /**
     * Test_create_comcast_dr_event_add_non_nve_gateway.
     */

    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 5)
    public void drEventForComcast_add_non_nve_gat(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add non_nve Gateway
         */

        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.CORRECT_GATEWAY_ID)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);

    }

    /**
     * Test_create_comcast_dr_event_add_multi_gateway_one_good_one_bad.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 6)
    public void drEventForComcast_add_good_and_bad_loc(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add multi_gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig
                        .get(DRApiConfig.ADD_GATEWAY_MULTI)
                        .replaceFirst("<addgatawey_id1>",
                                apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE))
                        .replaceFirst("<addgatawey_id2>",
                                apiconfig.get(DRApiConfig.CORRECT_GATEWAY_ID)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add location response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);

    }

    /**
     * Test_create_dr_event_for_one_gateway_add_gateway_to_nonxisting_dr_event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 7)
    public void add_gateway_to_nonexisting_dr_event(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add Gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", "dda"),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);
    }

    /**
     * Test_create_dr_event_for_one_gateway_add_gateway_to_expiring_dr_event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 8)
    public void add_gateway_to_expired(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        // String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(
                createUrl,
                apiconfig
                        .get(DRApiConfig.TARGET_GATEWAY_JSON)
                        .replaceFirst("<gateway_id>", apiconfig.get(DRApiConfig.CORRECT_GATEWAY_ID_NVE))
                        .replaceFirst("<start_time>", Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE,2)))
                        .replaceFirst("<end_time>",Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 3))),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        try {
            Thread.sleep(360000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * add Gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);
    }

    /**
     * Test_create_dr_event_for_one_gateway_and_try_to_add_gateway_to_it_using_cmcsa_cert.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 9)
    public void drEventForgateway_add_gateway_using_cmcsa_cert(final String drUrl,
            final String programID, final String eventID, final String targetType,
            final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add Gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig.get(DRApiConfig.ADD_GATEWAY).replaceFirst("<addgateway_id>",
                        apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 401, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add gateway response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);
    }

    /**
     * Test_create_dr_event_add_same_multi_gateways.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 10)
    public void drEventForgateway_add_same_multi_gateway_one_payload(final String drUrl,
            final String programID, final String eventID, final String targetType,
            final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        /**
         * storing event name for using to add location
         */

        JSONObject jsonObject = JsonUtil.parseObject(resultValue);
        String eventName = jsonObject.get("event_id").toString();

        /**
         * add multi_gateway
         */
        final HttpResponse response1 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_ADD_GATEWAY)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<event_id>", eventName),
                apiconfig
                        .get(DRApiConfig.ADD_GATEWAY_MULTI)
                        .replaceFirst("<addgatawey_id1>",
                                apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE))
                        .replaceFirst("<addgatawey_id2>",
                                apiconfig.get(DRApiConfig.GATEWAY_ID_ADD_NVE)),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response1.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response1.getStatusLine());
        // setLogString("response :'" + response1.getHeaders(createUrl)+ "'", true);

        final String resultValue1 = HTTPSClient.getResultString(response1.getEntity());
        setLogString("add location response :'" + resultValue1 + "'", true);

        /**
         * cancel dr event
         */
        final HttpResponse response2 = HTTPSClient.putResponse(
                apiconfig.get(DRApiConfig.BASE_URL)
                        + apiconfig.get(DRApiConfig.URL_CANCEL_DR_EVENT)
                                .replaceFirst("<program_id>", programID)
                                .replaceFirst("<PARTNER_EVENT_ID>", eventName),
                apiconfig.get(DRApiConfig.CANCEL_DR_EVENT_NVE),
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response2.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response2.getStatusLine());
        // setLogString("response :'" + response2 + "'", true);

        final String resultValue2 = HTTPSClient.getResultString(response2.getEntity());
        setLogString("cancel dr event response :'" + resultValue2 + "'", true);

    }

}
