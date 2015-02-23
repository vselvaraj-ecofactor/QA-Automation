/*
 * DRAPI_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.DRAPIDataProvider;
import com.ecofactor.qa.automation.consumerapi.dr.DRApiConfig;
import com.ecofactor.qa.automation.consumerapi.dr.DRApiModule;
import com.ecofactor.qa.automation.consumerapi.dr.HTTPSClient;
import com.ecofactor.qa.automation.dao.DaoModule;
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
     * Test_create_dr_event_ecofactor Corporation.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRALLGatewaysECO", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void test_create_dr_event_eco(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetALLJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.HOUR, 2)));

        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error status: "
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
    }

    /**
     * Test_create_dr_event_Comcast.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRALLLocationsComCAST", dataProviderClass = DRAPIDataProvider.class, priority = 2)
    public void test_create_dr_event_comcast(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson,
            final String locationID) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetALLJson;
        json = json
                .replaceFirst("<location_id>", locationID)
                .replaceFirst("<start_time>",
                        Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 20)));

        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());
        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);
    }

    /**
     * Test_create_dr_event_nve.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRAllGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 3)
    public void test_create_dr_event_nve(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetAllJson) {

        long timeStamp = System.currentTimeMillis();

        String directURL = drUrl;

        directURL = directURL.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetAllJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.HOUR, 1)));

        setLogString("URL Values of the API \n" + directURL + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
    }

    /**
     * Test_create_Dr_event_Default.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRALLGatewaysDefault", dataProviderClass = DRAPIDataProvider.class, priority = 4)
    public void test_create_dr_event_default(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson) {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetALLJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 20)));

        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);
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

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 15)));

        setLogString("URL Values of the API \n" + drUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(drUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
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

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 15)));

        setLogString("URL Values of the API \n" + drUrl + "\n" + json, true);

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

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 15)));

        setLogString("URL Values of the API \n" + drUrl + "\n" + json, true);

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

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 15)));

        setLogString("URL Values of the API \n" + drUrl + "\n" + json, true);

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

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 20)));

        setLogString("URL Values of the API \n" + drUrl + "\n" + json, true);

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
        setLogString("response :'" + resultValueString + " "
                + "Event with the given PartnerEvent Id Already Exists" + "'", true);
    }

    @Test(groups = { Groups.SANITY1 }, dataProvider = "drEventForAddingAnEvent", dataProviderClass = DRAPIDataProvider.class, priority = 11)
    public void addingEvent(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon, final String event) {

        long timeStamp = System.currentTimeMillis();

        final int eventValue = Integer.parseInt(event);
        int count = 0;
        String createUrl = Url;

        createUrl = createUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.HOUR, 2)));

        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error status: "
                + response.getStatusLine());

        if (count > 0) {

        }
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + " " + "Exceeds the maximum Events " + "'", true);

    }

    /**
     * Test for window out of program boundaries.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "WindowOutofProgramBoundary", dataProviderClass = DRAPIDataProvider.class, priority = 12)
    public void outofProgramBoundary(final String Url, final String programId,
            final String eventId, final String targetType, final String jSon,
            final String startDateTime, final String endDateTime) {

        long timeStamp = System.currentTimeMillis();
        String directURL = Url;

        directURL = directURL.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = jSon;
        json = json.replaceFirst("<start_time>", startDateTime).replaceFirst("<end_time>",
                endDateTime);

        setLogString("URL Values of the API \n" + directURL + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + " "
                + "Event Window out of Program Boundaries " + "'", true);
    }

    /**
     * Test create Dr event for an expired event.
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "eventExpired", dataProviderClass = DRAPIDataProvider.class, priority = 13)
    public void eventExpired(final String Url, final String programId, final String eventId,
            final String targetType, final String jSon, final String endtime) {

        long timeStamp = System.currentTimeMillis();
        String createUrl = Url;

        createUrl = createUrl.replaceFirst("<program_id>", programId)
                .replaceFirst("<event_id>", eventId + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = jSon;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.subtractFromUTCMilliSeconds(Calendar.MINUTE, 5)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.HOUR, 2)));

        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 400, "Error status: "
                + response.getStatusLine());
        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + " " + "Cannot create an event for the expired event "
                + "'", true);
    }
}
