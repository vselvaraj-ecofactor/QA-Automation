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

import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.drapi.data.DRAPIDataProvider;
import com.ecofactor.qa.automation.drapi.DRApiConfig;
import com.ecofactor.qa.automation.drapi.DRApiModule;
import com.ecofactor.qa.automation.drapi.HTTPSClient;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.dr.EventControlDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventDao;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * The Class DRAPI_Test.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class DRAPI_Test extends AbstractTest {

    private static String ISTATUS = "INACTIVE";

    @Inject
    private DRApiConfig apiconfig;

    @Inject
    private LSProgramEventDao lsProEvent;

    @Inject
    private EventControlDao eControl;

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
     * Test_create_dr_event_ecofactor Corporation.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRALLGatewaysECO", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drEventForEco(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetALLJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, -5))).replaceFirst(
                "<end_time>", Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 120)));
        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));

        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);

        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error status: "
                + response.getStatusLine());

        final String eventName = getDrEventName(result);
        setLogString("DR EventName: " + eventName, true);

        final int programEventId = lsProEvent.updateEventStatus(eventName);
        setLogString("Program Event Id: " + programEventId, true);

        eControl.updateStatus(programEventId, ISTATUS);
        setLogString("Updated ", true);

    }

    /**
     * Test_create_dr_event_Comcast.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRLocationComCAST", dataProviderClass = DRAPIDataProvider.class, priority = 2)
    public void drEventForComcast(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());
        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        final String eventName = getDrEventName(resultValue);
        setLogString("DR EventName: " + eventName, true);

        final int programEventId = lsProEvent.updateEventStatus(eventName);
        setLogString("Program Event Id: " + programEventId, true);

        eControl.updateStatus(programEventId, ISTATUS);
        setLogString("Updated ", true);

    }

    /**
     * Test_create_dr_event_nve.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRAllGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 3)
    public void drEventForNve(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetAllJson) {

        long timeStamp = System.currentTimeMillis();
        String directURL = drUrl;

        directURL = directURL.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(directURL, targetAllJson);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);

        final String eventName = getDrEventName(resultValueString);
        setLogString("DR EventName: " + eventName, true);

        final int programEventId = lsProEvent.updateEventStatus(eventName);
        setLogString("Program Event Id: " + programEventId, true);

        eControl.updateStatus(programEventId, ISTATUS);
        setLogString("Updated ", true);
    }

    /**
     * Test_create_Dr_event_Default.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRALLGatewaysDefault", dataProviderClass = DRAPIDataProvider.class, priority = 4)
    public void drEventForDefault(final String drUrl, final String programID,
            final String targetType, final String targetALLJson) {

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);

        final int lsProgramEventID = getDrEventId(result);
        setLogString("DR Event ID: " + lsProgramEventID, true);

        lsProEvent.updateById(lsProgramEventID);

        eControl.updateStatus(lsProgramEventID, ISTATUS);
        setLogString("Updated ", true);
    }

    /**
     * Test_create_Dr_event for blank program ID.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForBlankProgramID", dataProviderClass = DRAPIDataProvider.class, priority = 5)
    public void blankProgramID(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String drUrl = Url;
        drUrl = drUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(drUrl, jSon);

        final HttpResponse response = HTTPSClient.postResponse(drUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 404, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
    }

    /**
     * Test Dr event for non existing program Id.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForNonExistingProgramID", dataProviderClass = DRAPIDataProvider.class, priority = 6)
    public void nonExistingProgramId(final String Url, final String programId,
            final String eventId, final String targetType, final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String drUrl = Url;
        drUrl = drUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(drUrl, jSon);

        final HttpResponse response = HTTPSClient.postResponse(drUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
    }

    /**
     * Test Dr event for providing junk program Id.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForJunkProgramID", dataProviderClass = DRAPIDataProvider.class, priority = 7)
    public void junkProgramId(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String drUrl = Url;

        drUrl = drUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(drUrl, jSon);

        final HttpResponse response = HTTPSClient.postResponse(drUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
    }

    /**
     * Test create Dr event for without providing event id.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventforNoEventID", dataProviderClass = DRAPIDataProvider.class, priority = 8)
    public void withoutEventId(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String drUrl = Url;
        drUrl = drUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(drUrl, jSon);

        final HttpResponse response = HTTPSClient.postResponse(drUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
    }

    /**
     * Test create Dr event for without providing partner code.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForNoPartnerCode", dataProviderClass = DRAPIDataProvider.class, priority = 9)
    public void withoutPartnerCode(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String drUrl = Url;
        drUrl = drUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(drUrl, jSon);

        final HttpResponse response = HTTPSClient.postResponse(drUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
    }

    /**
     * Test create Dr event for existing event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForExistingEvent", dataProviderClass = DRAPIDataProvider.class, priority = 10)
    public void existingEvent(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon, final String startTime, final String endTime) {

        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId).replaceFirst("<target_type>", targetType)
                .replaceFirst("<target_all>", "true");

        String json = jSon;
        json = json.replaceFirst("<start_time>", startTime).replaceFirst("<end_time>", endTime);

        setLogString("URL Values of the API \n" + directURL + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString, true);
    }

    /**
     * Test create Dr event for adding event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForAddingAnEvent", dataProviderClass = DRAPIDataProvider.class, priority = 11)
    public void addingEvent(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon, final String event, final String noEvents) {

        long timeStamp = System.currentTimeMillis();

        final int eventValue = Integer.parseInt(event);
        final int noOfEvents = Integer.parseInt(noEvents);
        int count = 0;
        String createUrl = Url;
        HttpResponse response = null;

        if (noOfEvents < eventValue) {
            while (noOfEvents <= eventValue) {
                createUrl = createUrl.replaceFirst("<program_id>", programId)
                        .replaceFirst("<event_id>", eventId + timeStamp)
                        .replaceFirst("<target_type>", targetType)
                        .replaceFirst("<target_all>", "true");

                String json = commonJson(createUrl, jSon);

                response = HTTPSClient.postResponse(createUrl, json,
                        HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));

                final String result = HTTPSClient.getResultString(response.getEntity());
                System.out.println("result" + result);
                setLogString("response :'" + result + "'", true);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error status: "
                        + response.getStatusLine());

                final String eventName = getDrEventName(result);
                setLogString("DR EventName: " + eventName, true);

                final int programEventId = lsProEvent.updateEventStatus(eventName);
                setLogString("Program Event Id: " + programEventId, true);

                eControl.updateStatus(programEventId, ISTATUS);
                setLogString("Updated ", true);

                if (response.getStatusLine().getStatusCode() == 200) {
                    count++;
                }
                if (count > 0) {
                    Assert.assertTrue(response.getStatusLine().getStatusCode() == 400,
                            "Error Status: " + response.getStatusLine());
                }
            }
        } else {
            createUrl = createUrl.replaceFirst("<program_id>", programId)
                    .replaceFirst("<event_id>", eventId + timeStamp)
                    .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

            String json = commonJson(createUrl, jSon);

            response = HTTPSClient.postResponse(createUrl, json,
                    HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
            Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error status: "
                    + response.getStatusLine());

            final String result = HTTPSClient.getResultString(response.getEntity());
            System.out.println("result" + result);
            setLogString("response :'" + result + "'", true);

            final String eventName = getDrEventName(result);
            setLogString("DR EventName: " + eventName, true);

            final int programEventId = lsProEvent.updateEventStatus(eventName);
            setLogString("Program Event Id: " + programEventId, true);

            eControl.updateStatus(programEventId, ISTATUS);
            setLogString("Updated ", true);

        }
    }

    /**
     * Test for window out of program boundaries.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "WindowOutofProgramBoundary", dataProviderClass = DRAPIDataProvider.class, priority = 12)
    public void outofProgramBoundary(final String Url, final String programId,
            final String eventId, final String targetType, final String jSon,
            final String startTime, final String endTime) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = jSon;
        json = json.replaceFirst("<start_time>", startTime).replaceFirst("<end_time>", endTime);

        setLogString("URL Values of the API \n" + directURL + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString, true);

    }

    /**
     * Test create dr event for Inactive event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "eventInactive", dataProviderClass = DRAPIDataProvider.class, priority = 13)
    public void eventInactive(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String createUrl = Url;

        createUrl = createUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, jSon);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));

        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error status: "
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result, true);
    }

    /**
     * Test create Dr event for an expired event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "eventExpired", dataProviderClass = DRAPIDataProvider.class, priority = 14)
    public void eventExpired(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon, final String startTime, final String endTime) {

        long timeStamp = System.currentTimeMillis();
        String createUrl = Url;

        createUrl = createUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = jSon;
        json = json.replaceFirst("<start_time>", startTime).replaceFirst("<end_time>", endTime);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error status: "
                + response.getStatusLine());

        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result, true);
    }

    /**
     * Test create Dr event for gateway id is blank.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "gatewayIDBlank", dataProviderClass = DRAPIDataProvider.class, priority = 15)
    public void gatewayIDBlank(final String Url, final String programId, final String eventId,
            final String jSon, final String gatewayID) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId).replaceFirst("<event_id>",
                eventId + timeStamp);

        String json = gatewayJson(directURL, jSon, gatewayID);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
    }

    /**
     * Test create Dr event for non existing gateway id.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "notExistingGatewayID", dataProviderClass = DRAPIDataProvider.class, priority = 16)
    public void notExistingGatewayID(final String Url, final String programId,
            final String eventId, final String jSon, final String gatewayID) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId).replaceFirst("<event_id>",
                eventId + timeStamp);

        String json = gatewayJson(directURL, jSon, gatewayID);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
    }

    /**
     * Test create Dr event for junk gateway id.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "junkGatewayID", dataProviderClass = DRAPIDataProvider.class, priority = 17)
    public void junkGatewayID(final String Url, final String programId, final String eventId,
            final String jSon, final String gatewayID) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId).replaceFirst("<event_id>",
                eventId + timeStamp);

        String json = gatewayJson(directURL, jSon, gatewayID);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
    }

    /**
     * Test create DR event for providing correct gateway id.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "correctGatewayID", dataProviderClass = DRAPIDataProvider.class, priority = 18)
    public void correctGatewayID(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon, final String gatewayID) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = gatewayJson(directURL, jSon, gatewayID);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);

        final String eventName = getDrEventName(resultValueString);
        setLogString("DR EventName: " + eventName, true);

        final int programEventId = lsProEvent.updateEventStatus(eventName);
        setLogString("Program Event Id: " + programEventId, true);

        eControl.updateStatus(programEventId, ISTATUS);
        setLogString("Updated ", true);

    }

    /**
     * Test create DR event for providing omit gateway block.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "omitGatewayBlock", dataProviderClass = DRAPIDataProvider.class, priority = 19)
    public void omitGatewayBlock(final String Url, final String programId, final String eventId,
            final String jSon) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId).replaceFirst("<event_id>",
                eventId + timeStamp);

        String json = commonJson(directURL, jSon);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
    }

    /**
     * String value is common for all test cases.
     * @param url is url
     * @param targetJson is targetJson
     * @return String
     */
    public String commonJson(final String url, final String targetJson) {

        String json = targetJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 10))).replaceFirst(
                "<end_time>", Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 20)));
        setLogString("URL Values of the API \n" + url + "\n" + json, true);
        return json;
    }

    /**
     * String value is common for few test cases were depends on gateway id.
     * @param targetJson is targetJson
     * @param gatewayID is gatewayID
     * @return String
     */
    public String gatewayJson(final String url, final String targetJson, final String gatewayID) {

        String json = targetJson;
        json = json
                .replaceFirst("<gateway_id>", gatewayID)
                .replaceFirst("<start_time>",
                        Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.HOUR, 1)));
        setLogString("URL Values of the API \n" + url + "\n" + json, true);
        return json;
    }

    /**
     * Fetch the Event Name after creation of DR Event.
     * @param response the response
     * @return String.
     */
    public String getDrEventName(final String response) {

        StringTokenizer st = new StringTokenizer(response, ",");
        String eventID = "";
        while (st.hasMoreElements()) {

            @SuppressWarnings("unused")
            String status = st.nextToken();
            eventID = st.nextToken();
        }
        String[] eventValues = eventID.split(":");
        final String eventName = eventValues[2];

        setLogString("DR EventName Fetched : " + eventName.substring(0, eventName.length() - 3),
                true);
        return eventName.substring(0, eventName.length() - 3);
    }

    /**
     * Fetch the Event Id after creation of DR Event.
     * @param response the response.
     * @return Integer.
     */
    public int getDrEventId(final String response) {

        StringTokenizer st = new StringTokenizer(response, ",");
        String eventID = "";
        while (st.hasMoreElements()) {

            String[] values = new String[(st.countTokens())];
            for (int i = 0; i < 5; i++) {
                values[i] = st.nextToken();
            }

            eventID = values[1];
        }
        String[] eventValues = eventID.split(":");
        String value = eventValues[1];
        String str = value.substring(1, value.length() - 1);
        final int eventId = Integer.parseInt(str);
        return eventId;
    }
}
