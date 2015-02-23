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
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.EcpCoreLSEventLocation;
import com.ecofactor.common.pojo.Thermostat;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.dr.EFThermostatEventDao;
import com.ecofactor.qa.automation.dao.dr.EventControlDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao;
import com.ecofactor.qa.automation.dao.dr.LSProgramEventReportDao;
import com.ecofactor.qa.automation.dao.dr.ThermostatDao;
import com.ecofactor.qa.automation.dao.dr.ThermostatEventDao;
import com.ecofactor.qa.automation.drapi.data.DRAPIDataProvider;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.pojo.ThermostatEvent;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class DRAPI_Execution_Test.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class DR_Execution_410_Test extends AbstractTest {

    /** The astatus. */
    private static String ASTATUS = "ACTIVE";

    /** The sstatus. */
    private static String SSTATUS = "SKIPPED";

    /** The startevent. */
    private static String STARTEVENT = "startEvent";

    /** The endevent. */
    private static String ENDEVENT = "endEvent";

    /** The eventstatus. */
    private static String EVENTSTATUS = "PROCESSED";

    /** The eventype. */
    private static String EVENTYPE_ALGO = "ALGO";

    /** The algorithmid. */
    private static Integer ALGORITHMID_410 = 410;

    /** The starteventphase. */
    private static Integer EVENTPHASE_0 = 0;

    /** The endeventphase. */
    private static Integer EVENTPHASE_2 = 2;

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

    /** The efTsatEvent. */
    @Inject
    private EFThermostatEventDao efTsatEvent;

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
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createExecutionDRALLGatewaysECO", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drEventForEco(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson, final String tstId)
            throws ParseException {

        final long timeStamp = System.currentTimeMillis();
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

        // Finding the eventId

        final int proramEventId = lsProEvent.programEventId(eventName);
        setLogString("DR Event Id: " + proramEventId, true);

        final Double grpId = (double) proramEventId;

        WaitUtil.tinyWait();

        final String eventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + eventStatus, true);

        // Updating start and end time in ef_ls_program_event table

        final Map<String, Object> values = lsProEvent.updateEventByStartDateAndEndDate(eventName);
        setLogString("Details Based on Event Name : " + values, true);

        final int algorithmId = getAlgorithmId(values);
        setLogString("Algorithm Id : " + algorithmId, true);

        // Gets the list of location.

        final List<Integer> eventLocation = lspEventLocation.fetchByLocationId(proramEventId);
        setLogString("Available Location : " + eventLocation, true);

        WaitUtil.largeWait();
        setLogString("After 30 Seconds ", true);

        // At this time location status should be SCHEDULED

        final String updatedEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + updatedEventStatus, true);
        Assert.assertTrue(updatedEventStatus.equalsIgnoreCase(ASTATUS),
                "Still status is not in ACTIVE mode");

        final List<String> locIdStatus1 = lspEventLocation
                .fullListByLocationIdStatus(proramEventId);
        setLogString("Available Locations : " + locIdStatus1, true);

        // This wait makes sure since start time was updated as now+2 mins we have enough wait to
        // ensure the DR is kicked in

        final Map<String, Object> startTime = lsProEvent.updateEventByStartDate(eventName);
        setLogString("Details after updated the start time : " + startTime, true);

        // This should have location Ids with status as SKIPPED and ACTIVE
        /*
         * Action Item: get count of locations and thermostat linked to these locations which have
         * status as ACTIVE and SKIPPED This is needed for report assertion
         */

        final List<String> locIdStatus = lspEventLocation.fullListByLocationIdStatus(proramEventId);
        setLogString("Location and Status after few seconds : " + locIdStatus, true);

        final Map<String, Integer> countDetails = getCountOfTstLocations(proramEventId);
        setLogString("Location and Thermostat Count based on status : " + countDetails, true);

        // Test thermostatId
        final int thermostatId = Integer.parseInt(tstId); /* tstatEvent.listByGroupId(grpId); */
        setLogString("Thermostat Id : " + thermostatId, true);

        /*
         * WaitUtil.hugeWait(); setLogString("After 5 minutes ", true);
         */

        // This should give the record from ef_thermostat_event table for the given thermostat and
        // event
        /*
         * Action Item: {start event enty:Assertions on algorithm 410 should have event_type ALGO,
         * event_status PROCESSED, event_phase 0 and action startEvent}, {end event entry:
         * Assertions on algorithm 210 should have event_type ALGO, event_status PROCESSED,
         * event_phase 2, action endEvent, groupevent_id = event_id.2(2713.2)}, eg: on qa-plat run
         * this query : select * from ef_thermostat_event where thermostat_id = 2641 and
         * group_event_id in (2715,2715.2)
         */

        final List<ThermostatEvent> tstatDetails = efTsatEvent.fetchDetailsByThermostatId(
                thermostatId, grpId);
        Assert.assertTrue(isStartEntryValidated(tstatDetails),
                "Values were not matched in Start Entry");
        setLogString("Verified with start event ", true);

        WaitUtil.veryHugeWait();
        WaitUtil.veryHugeWait();
        WaitUtil.veryHugeWait();
        setLogString("After 20+ minutes ", true);
        final List<ThermostatEvent> tstatDetails1 = efTsatEvent.fetchDetailsByThermostatId(
                thermostatId, grpId);
        Assert.assertTrue(isEndEntryValidated(tstatDetails1, grpId),
                "Values were not matched in End Entry");
        setLogString("Verified with end event", true);

        /*
         * Remaining code 1. Cancel DR 2. Reports Assertion
         */

        /*
         * setLogString("Though event completed, but updating status as Cancel ", true); final int
         * programEventId = lsProEvent.updateEventStatus(eventName);
         * setLogString("Program Event Id: " + programEventId, true);
         * eControl.updateStatus(programEventId, ISTATUS); setLogString("Updated ", true);
         */

        final Map<String, Object> detail = lspEventReport.updatedDetails(proramEventId);
        setLogString("Event Location Report : " + detail, true);
        Assert.assertTrue(isLocationCountValidated(countDetails, detail),
                "Location count is not as same as Report");
        setLogString("Verified with Locations Count", true);
        Assert.assertTrue(isTstCountValidated(countDetails, detail),
                "Thermostat count is not as same as Report");
        setLogString("Verified with Thermostats Count", true);
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
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRAllGatewaysNVE", dataProviderClass = DRAPIDataProvider.class, priority = 2)
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

        // Finding the eventId

        final int proramEventId = lsProEvent.programEventId(eventName);
        setLogString("DR Event Id: " + proramEventId, true);

        final Double grpId = (double) proramEventId;

        WaitUtil.tinyWait();

        final String eventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + eventStatus, true);

        // Updating start and end time in ef_ls_program_event table

        final Map<String, Object> values = lsProEvent.updateEventByStartDateAndEndDate(eventName);
        setLogString("Details Based on Event Name : " + values, true);

        final int algorithmId = getAlgorithmId(values);
        setLogString("Algorithm Id : " + algorithmId, true);

        // Gets the list of location.

        final List<Integer> eventLocation = lspEventLocation.fetchByLocationId(proramEventId);
        setLogString("Available Location : " + eventLocation, true);

        WaitUtil.mediumWait();
        setLogString("After 20 Seconds ", true);

        // At this time location status should be SCHEDULED

        final String updatedEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + updatedEventStatus, true);
        Assert.assertTrue(updatedEventStatus.equalsIgnoreCase(ASTATUS),
                "Still status is not in ACTIVE mode");

        final List<String> locIdStatus1 = lspEventLocation
                .fullListByLocationIdStatus(proramEventId);
        setLogString("Available Locations : " + locIdStatus1, true);

        // This wait makes sure since start time was updated as now+2 mins we have enough wait to
        // ensure the DR is kicked in
        final Map<String, Object> startTime = lsProEvent.updateEventByStartDate(eventName);
        setLogString("Details after updated the start time : " + startTime, true);

        // This should have location Ids with status as SKIPPED and ACTIVE
        /*
         * Action Item: get count of locations and thermostat linked to these locations which have
         * status as ACTIVE and SKIPPED This is needed for report assertion
         */

        final List<String> locIdStatus = lspEventLocation.fullListByLocationIdStatus(proramEventId);
        setLogString("Location and Status after 5 mins : " + locIdStatus, true);

        final Map<String, Integer> countDetails = getCountOfTstLocations(proramEventId);
        setLogString("Location and Thermostat Count based on status : " + countDetails, true);

        // Test thermostatId
        final int thermostatId = 0; /* Integer.parseInt(tstId); tstatEvent.listByGroupId(grpId); */
        setLogString("Thermostat Id : " + thermostatId, true);

        // This should give the record from ef_thermostat_event table for the given thermostat and
        // event
        /*
         * Action Item: {start event enty:Assertions on algorithm 410 should have event_type ALGO,
         * event_status PROCESSED, event_phase 0 and action startEvent}, {end event entry:
         * Assertions on algorithm 210 should have event_type ALGO, event_status PROCESSED,
         * event_phase 2, action endEvent, groupevent_id = event_id.2(2713.2)}, eg: on qa-plat run
         * this query : select * from ef_thermostat_event where thermostat_id = 2641 and
         * group_event_id in (2715,2715.2)
         */

        final List<ThermostatEvent> tstatDetails = efTsatEvent.fetchDetailsByThermostatId(
                thermostatId, grpId);
        Assert.assertTrue(isStartEntryValidated(tstatDetails),
                "Values were not matched in Start Entry");

        WaitUtil.veryHugeWait();
        WaitUtil.veryHugeWait();
        WaitUtil.veryHugeWait();
        setLogString("After 20+ minutes ", true);
        final List<ThermostatEvent> tstatDetails1 = efTsatEvent.fetchDetailsByThermostatId(
                thermostatId, grpId);
        Assert.assertTrue(isEndEntryValidated(tstatDetails1, grpId),
                "Values were not matched in End Entry");
        setLogString("Verified with end event", true);

        /*
         * Remaining code 1. Cancel DR 2. Reports Assertion
         */

        /*
         * setLogString("Though event completed, but updating status as Cancel ", true); final int
         * programEventId = lsProEvent.updateEventStatus(eventName);
         * setLogString("Program Event Id: " + programEventId, true);
         * eControl.updateStatus(programEventId, ISTATUS); setLogString("Updated ", true);
         */

        final Map<String, Object> detail = lspEventReport.updatedDetails(proramEventId);
        setLogString("Event Location Report : " + detail, true);
        Assert.assertTrue(isLocationCountValidated(countDetails, detail),
                "Location count is not as same as Report");
        Assert.assertTrue(isTstCountValidated(countDetails, detail),
                "Thermostat count is not as same as Report");
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
     * @param allDetails the Map details.
     * @param status the status
     * @return Integer.
     */
    public int getLocationIdOfStatus(Map<Object, Object> allDetails, String status) {

        String keyStatus = "";
        int valueLocationId = 0;
        for (Map.Entry<Object, Object> allEntry : allDetails.entrySet()) {

            keyStatus = allEntry.getKey().toString();
            valueLocationId = (int) allEntry.getValue();
            if (keyStatus.equalsIgnoreCase(status)) {

                valueLocationId = (int) allEntry.getValue();
            }
        }
        return valueLocationId;
    }

    /**
     * Gets the algorithm id.
     * @param allDetails the all details
     * @return the algorithm id
     */
    public int getAlgorithmId(Map<String, Object> allDetails) {

        int algoId = 0;
        for (Entry<String, Object> values : allDetails.entrySet()) {
            if (values.getKey().contains("Algorithm Id")) {
                algoId = (int) values.getValue();
            }
        }
        return algoId;
    }

    /**
     * Gets the count of thermostat based on status.
     * @param programEventId the program event id
     * @param locationByStatus the location by status
     * @return the count of thermostat based on status
     */
    public List<Thermostat> getCountOfThermostatBasedOnStatus(final Integer programEventId,
            final List<EcpCoreLSEventLocation> locationByStatus) {

        final List<Thermostat> list = new ArrayList<>();
        for (final EcpCoreLSEventLocation locationEvent : locationByStatus) {

            list.addAll(tsat.listThermostatsByLocation(locationEvent.getLocationid()));
        }
        return list;
    }

    /**
     * Gets the count of tst locations.
     * @param proramEventId the proram event id
     * @return the count of tst locations
     */
    public Map<String, Integer> getCountOfTstLocations(final Integer proramEventId) {

        Map<String, Integer> locationCount = new HashMap<String, Integer>();
        List<Thermostat> tstByActiveStatus = null;
        List<Thermostat> tstBySkippedStatus = null;

        // count of locations based on active
        final List<EcpCoreLSEventLocation> locationByStatus = lspEventLocation
                .listByProgramEventAndStatus(proramEventId, ASTATUS);
        locationCount.put("Total No of Locations based on Active Status",
                locationByStatus == null ? 0 : locationByStatus.size());

        // count of tst based on active
        tstByActiveStatus = getCountOfThermostatBasedOnStatus(proramEventId, locationByStatus);
        locationCount.put("Total No of Thermostats based on Active Status",
                tstByActiveStatus.size());

        // count of locations based on skipped
        final List<EcpCoreLSEventLocation> locationByStatus1 = lspEventLocation
                .listByProgramEventAndStatus(proramEventId, SSTATUS);
        locationCount.put("Total No of Locations based on Skipped Status",
                locationByStatus1 == null ? 0 : locationByStatus1.size());

        // count of thst by skipped
        tstBySkippedStatus = getCountOfThermostatBasedOnStatus(proramEventId, locationByStatus1);
        locationCount.put("Total No of Thermostats based on Skipped Status",
                tstBySkippedStatus.size());

        // total no of locations and tst
        locationCount.put("Total No of Locations",
                (locationByStatus.size() + locationByStatus1.size()));
        locationCount.put("Total No of Thermostats",
                (tstByActiveStatus.size() + tstBySkippedStatus.size()));

        return locationCount;
    }

    /**
     * Checks if is location count validated.
     * @param locationCount the location count
     * @param detail the detail
     * @return true, if is location count validated
     */
    public boolean isLocationCountValidated(final Map<String, Integer> locationCount,
            final Map<String, Object> detail) {

        boolean validate = false;

        for (Map.Entry<String, Integer> values : locationCount.entrySet()) {
            for (Map.Entry<String, Object> value : detail.entrySet()) {

                if ((values.getKey().contains("Total No of Locations based on Active Status"))
                        && value.getKey().contains("Number Actual Locations")) {

                    validate = values.getValue().equals(value.getValue());
                }
            }
        }
        return validate;
    }

    /**
     * Checks if is tst count validated.
     * @param tstCount the tst count
     * @param detail the detail
     * @return true, if is tst count validated
     */
    public boolean isTstCountValidated(final Map<String, Integer> tstCount,
            final Map<String, Object> detail) {

        boolean tstValidate = false;

        for (Map.Entry<String, Integer> values : tstCount.entrySet()) {
            for (Map.Entry<String, Object> value : detail.entrySet()) {

                if ((values.getKey().contains("Total No of Thermostats based on Active Status"))
                        && value.getKey().contains("Number Actual Thermostats")) {

                    tstValidate = values.getValue().equals(value.getValue());
                }
            }
        }
        return tstValidate;
    }

    /**
     * Gets the details of tst event.
     * @param details the details
     * @return the details of tst event
     */
    public boolean isStartEntryValidated(final List<ThermostatEvent> details) {

        boolean startEntryValidate = false;
        for (ThermostatEvent thermostatEvent : details) {
            if ((thermostatEvent.getAction().equalsIgnoreCase(STARTEVENT))
                    && (thermostatEvent.getAlgorithmId().equals(ALGORITHMID_410))
                    && (thermostatEvent.getEventPhase().equals(EVENTPHASE_0))
                    && (thermostatEvent.getEventStatus().equalsIgnoreCase(EVENTSTATUS))
                    && (thermostatEvent.getEventType().equalsIgnoreCase(EVENTYPE_ALGO))) {
                startEntryValidate = true;
                break;
            }
        }
        return startEntryValidate;
    }

    /**
     * Checks if is end entry validated.
     * @param details the details
     * @param groupEventId the group event id
     * @return true, if is end entry validated
     */
    public boolean isEndEntryValidated(final List<ThermostatEvent> details,
            final Double groupEventId) {

        boolean endEntryValidate = false;
        final Double grpEventIdCompleted = groupEventId + 0.2;
        setLogString("Group Event ID Compleated : " + grpEventIdCompleted, true);
        for (ThermostatEvent thermostatEvent : details) {
            if ((thermostatEvent.getAction().equalsIgnoreCase(ENDEVENT))
                    && (thermostatEvent.getAlgorithmId().equals(ALGORITHMID_410))
                    && (thermostatEvent.getEventPhase().equals(EVENTPHASE_2))
                    && (thermostatEvent.getEventStatus().equalsIgnoreCase(EVENTSTATUS))
                    && (thermostatEvent.getEventType().equalsIgnoreCase(EVENTYPE_ALGO))
                    && (grpEventIdCompleted.equals(thermostatEvent.getGroupEventId()))) {
                endEntryValidate = true;
                break;
            }
        }
        return endEntryValidate;
    }
}
