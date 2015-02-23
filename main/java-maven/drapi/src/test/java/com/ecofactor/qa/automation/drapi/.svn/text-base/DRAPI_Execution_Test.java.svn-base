/*
 * DRAPI_Execution_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.EcpCoreLSEventLocation;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.dr.EventControlDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventReportDao;
import com.ecofactor.qa.automation.dao.dr.ThermostatDao;
import com.ecofactor.qa.automation.dao.dr.ThermostatEventDao;
import com.ecofactor.qa.automation.drapi.data.DRAPIDataProvider;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class DRAPI_Execution_Test.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class DRAPI_Execution_Test extends AbstractTest {

    /** The astatus. */
    private static String ASTATUS = "ACTIVE";

    /** The pstatus. */
    private static String PSTATUS = "PENDING";

    /** The istatus. */
    private static String ISTATUS = "INACTIVE";

    /** The sstatus. */
    private static String SSTATUS = "SKIPPED";

    /** The drapitest. */
    @Inject
    private DRAPI_Test drapitest;

    /** The api config. */
    @Inject
    private static DRApiConfig apiConfig;

    /** The ls pro event. */
    @Inject
    private LSProgramEventDao lsProEvent;

    /** The e control. */
    @Inject
    private EventControlDao eControl;

    /** The lsp event report. */
    @Inject
    private LSProgramEventReportDao lspEventReport;

    /** The tstat event. */
    @Inject
    private ThermostatEventDao tstatEvent;

    /** The lsp event location. */
    @Inject
    private LSProgramEventLocationDao lspEventLocation;

    /** The tsat. */
    @Inject
    private ThermostatDao tsat;

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
     * @param drUrl the dr url
     * @param programID the program id
     * @param eventID the event id
     * @param targetType the target type
     * @param targetALLJson the target all json
     * @throws ParseException the parse exception
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRALLGatewaysECO", dataProviderClass = DRAPIDataProvider.class, priority = 2)
    public void drEventForEco(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson) throws ParseException {

        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetALLJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 5))).replaceFirst(
                "<end_time>", Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 25)));
        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));

        final String result = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + result + "'", true);

        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error status: "
                + response.getStatusLine());

        final String eventName = getDrEventName(result);
        setLogString("DR EventName: " + eventName, true);

        final int proramEventId = lsProEvent.programEventId(eventName);
        setLogString("DR Event Id: " + proramEventId, true);

        WaitUtil.tinyWait();

        final String eventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + eventStatus, true);

        final Map<String, Object> Values = lsProEvent.updateEventByStartDateAndEndDate(eventName);
        setLogString("Details Based on Event Name : " + Values, true);

        final List<Integer> eventLocation = lspEventLocation.fetchByLocationId(proramEventId);
        setLogString("Available Location : " + eventLocation, true);

        WaitUtil.mediumWait();
        setLogString("After 20 Seconds ", true);

        final String UpdatedEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + UpdatedEventStatus, true);

        WaitUtil.hugeWait();
        setLogString("After 2 minutes ", true);

        final String UpdatedEventStatus1 = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + UpdatedEventStatus1, true);

        final List<EcpCoreLSEventLocation> locIdStatus = lspEventLocation
                .listByLocationIdStatus(proramEventId);
        setLogString("Available Locations with Status : " + locIdStatus, true);

        final int locId = getLocationIdForStatus(locIdStatus, ASTATUS);
        setLogString("Location Id in Active Mode : " + locId, true);

        final int thermostatId = tsat.listByLoationId(locId);
        setLogString("Thermostat Id : " + thermostatId, true);

        WaitUtil.mediumWait();
        setLogString("After 20 Seconds ", true);

        List<PartitionedThermostatEvent> allDetails = tstatEvent.setPointEDR(thermostatId);

        WaitUtil.hugeWait();
        setLogString("After 2 minutes ", true);

        final List<PartitionedThermostatEvent> allDetail = tstatEvent.setPointEDR(thermostatId);

        final String nextPhaseTime = eControl.fetchNextPhaseTime(proramEventId);
        setLogString("Next Phase Time : " + nextPhaseTime, true);

        WaitUtil.tinyWait();
        setLogString("After 10 seconds", true);

        eControl.updateNextPhaseTime(proramEventId);
        setLogString("Updated Next Phase Time", true);

        final String currentStatus = eControl.fetchStatus(proramEventId);
        setLogString("Current Status : " + currentStatus, true);

        if (currentStatus.equalsIgnoreCase(PSTATUS)) {

            eControl.updateStatus(proramEventId, PSTATUS);
            setLogString("Updated status ", true);
        }

        WaitUtil.hugeWait();
        setLogString("After 2 minutes ", true);

        final List<PartitionedThermostatEvent> updatedAllDetails1 = tstatEvent
                .setPointEDR(thermostatId);

        final String programEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + programEventStatus, true);

        final String finalStatus = eControl.fetchStatus(proramEventId);
        setLogString("Current Status : " + finalStatus, true);

        if (programEventStatus.equalsIgnoreCase(ASTATUS)) {

            WaitUtil.tinyWait();
            setLogString("After 10 seconds", true);

            eControl.updateNextPhaseTime(proramEventId);
            setLogString("Updated Next Phase Time", true);

            final String currentStatus1 = eControl.fetchStatus(proramEventId);
            setLogString("Current Status : " + currentStatus1, true);

            if (currentStatus.equalsIgnoreCase(PSTATUS)) {

                eControl.updateStatus(proramEventId, PSTATUS);
                setLogString("Updated status ", true);
            }
        }

        WaitUtil.hugeWait();
        setLogString("After 2 minutes ", true);

        final List<PartitionedThermostatEvent> updatedAllDetail = tstatEvent
                .setPointEDR(thermostatId);

        final String programEventStatus1 = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + programEventStatus1, true);

        final String finalStatus1 = eControl.fetchStatus(proramEventId);
        setLogString("Final Status : " + finalStatus1, true);

        final int programEventId = lsProEvent.updateEventStatus(eventName);
        setLogString("Program Event Id: " + programEventId, true);

        eControl.updateStatus(programEventId, ISTATUS);
        setLogString("Updated ", true);
    }

    /**
     * Test_create_dr_event_nve.
     * @param drUrl the dr url
     * @param programID the program id
     * @param eventID the event id
     * @param targetType the target type
     * @param targetAllJson the target all json
     * @throws ParseException the parse exception
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRAllGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drEventForNve(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetAllJson) throws ParseException {

        long timeStamp = System.currentTimeMillis();
        String directURL = drUrl;

        directURL = directURL.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetAllJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 1))).replaceFirst(
                "<end_time>", Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 7)));
        setLogString("URL Values of the API \n" + directURL + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(directURL, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String resultValueString = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);

        final String eventName = getDrEventName(resultValueString);
        setLogString("DR EventName: " + eventName, true);

        final int proramEventId = lsProEvent.programEventId(eventName);
        setLogString("DR Event Id: " + proramEventId, true);

        final String eventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + eventStatus, true);

        WaitUtil.hugeWait();
        setLogString("After 2 Minutes ", true);

        final String UpdatedEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + UpdatedEventStatus, true);

        final List<EcpCoreLSEventLocation> locIdStatus = lspEventLocation
                .listByLocationIdStatus(proramEventId);
        setLogString("Available Locations with Status : " + locIdStatus, true);

        final int locId = getLocationIdForStatus(locIdStatus, ASTATUS);
        setLogString("Location Id in Active Mode : " + locId, true);

        final int thermostatId = tsat.listByLoationId(locId);
        setLogString("Thermostat Id : " + thermostatId, true);

        final List<PartitionedThermostatEvent> allDetails = tstatEvent.setPointEDR(thermostatId);

        final String nextPhaseTime = eControl.fetchNextPhaseTime(proramEventId);
        setLogString("Next Phase Time : " + nextPhaseTime, true);

        WaitUtil.tinyWait();
        setLogString("After 10 seconds", true);

        eControl.updateNextPhaseTime(proramEventId);
        setLogString("Updated Next Phase Time", true);

        final String currentStatus = eControl.fetchStatus(proramEventId);
        setLogString("Current Status : " + currentStatus, true);

        if (currentStatus.equalsIgnoreCase(PSTATUS)) {

            eControl.updateStatus(proramEventId, PSTATUS);
            setLogString("Updated status ", true);
        }

        WaitUtil.hugeWait();
        setLogString("After 2 minutes ", true);

        final List<PartitionedThermostatEvent> updatedAllDetails = tstatEvent
                .setPointEDR(thermostatId);

        final String programEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + programEventStatus, true);

        final String finalStatus = eControl.fetchStatus(proramEventId);
        setLogString("Current Status : " + finalStatus, true);

        if (programEventStatus.equalsIgnoreCase(ASTATUS)) {

            WaitUtil.tinyWait();
            setLogString("After 10 seconds", true);

            eControl.updateNextPhaseTime(proramEventId);
            setLogString("Updated Next Phase Time", true);

            final String currentStatus1 = eControl.fetchStatus(proramEventId);
            setLogString("Current Status : " + currentStatus1, true);

            if (currentStatus.equalsIgnoreCase(PSTATUS)) {

                eControl.updateStatus(proramEventId, PSTATUS);
                setLogString("Updated status ", true);
            }
        }

        WaitUtil.hugeWait();
        setLogString("After 2 minutes ", true);

        final List<PartitionedThermostatEvent> updatedAllDetails1 = tstatEvent
                .setPointEDR(thermostatId);

        final String programEventStatus1 = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + programEventStatus1, true);

        final String finalStatus1 = eControl.fetchStatus(proramEventId);
        setLogString("Final Status : " + finalStatus1, true);

        final Map<String, Object> thermostatDetails = lspEventReport.updatedDetails(proramEventId);
        setLogString("Thermostat Details: " + thermostatDetails, true);

        final int programEventId = lsProEvent.updateEventStatus(eventName);
        setLogString("Program Event Id: " + programEventId, true);

        eControl.updateStatus(programEventId, ISTATUS);
        setLogString("Updated ", true);
    }

    /**
     * Fetch the Event Name after creation of DR Event.
     * @param response the response
     * @return String.
     */
    private String getDrEventName(final String response) {

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
     * Fetch the location Id based on status.
     * @param allDetails the List details.
     * @param status the status
     * @return Integer.
     */
    public int getLocationIdForStatus(final List<EcpCoreLSEventLocation> locIdStatus,
            final String status) {

        int valueLocationId = 0;
        for (EcpCoreLSEventLocation ecpCoreLSEventLocation : locIdStatus) {
            String locStatus = ecpCoreLSEventLocation.getStatus().toString();
            if (locStatus.equalsIgnoreCase(ASTATUS)) {
                valueLocationId = (ecpCoreLSEventLocation.getLocationid());
            }
        }
        return valueLocationId;
    }

    /**
     * Gets the count by location status.
     * @param locIdStatus the loc id status
     * @return the count by location status
     */
    public Map<String, Integer> getCountByLocationStatus(
            final List<EcpCoreLSEventLocation> locIdStatus) {

        List<Integer> acLocationId = new ArrayList<Integer>();
        List<Integer> scLocationId = new ArrayList<Integer>();
        List<Integer> acTstId = new ArrayList<Integer>();
        Map<String, Integer> locationCount = new HashMap<String, Integer>();
        locationCount.put("Total No of Locations", locIdStatus.size());
        for (EcpCoreLSEventLocation ecpCoreLSEventLocation : locIdStatus) {
            String locStatus = ecpCoreLSEventLocation.getStatus().toString();
            if (locStatus.equalsIgnoreCase(ASTATUS)) {
                acLocationId.add(ecpCoreLSEventLocation.getLocationid());
            } else if (locStatus.equalsIgnoreCase(SSTATUS)) {
                scLocationId.add(ecpCoreLSEventLocation.getLocationid());
            }
        }
        locationCount.put("Total No of Locations based on Active Status", acLocationId.size());
        locationCount.put("Total No of Locations based on Skipped Status", scLocationId.size());

        for (int i = 0; i < acLocationId.size(); i++) {

            int locId = acLocationId.get(i);
            final int thermostatId = tsat.listByLoationId(locId);
            acTstId.add(thermostatId);
        }

        return locationCount;
    }

}
