package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.List;
import java.util.Map;

import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.testng.Assert;

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
public class DR_Execution_210_Test {

    /** The astatus. */
    private static String ASTATUS = "ACTIVE";

    /** The pstatus. */
    @SuppressWarnings("unused")
    private static String PSTATUS = "PENDING";

    /** The istatus. */
    @SuppressWarnings("unused")
    private static String ISTATUS = "INACTIVE";

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
    private static Integer ALGORITHMID_210 = 210;

    /** The algorithmid. */
    private static Integer ALGORITHMID_310 = 310;

    /** The starteventphase. */
    private static Integer EVENTPHASE_0 = 0;

    /** The EVENTPHAS e_1. */
    private static Integer EVENTPHASE_1 = 1;

    /** The endeventphase. */
    private static Integer EVENTPHASE_2 = 2;

    /** The algoid. */
    private static Integer ALGOID_MINUS_FIFTY = -50;

    /** The eventype ls. */
    private static String EVENTYPE_LS = "LS";

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
     * Test_create_dr_event_ecofactor Corporation.
     * @param drUrl the dr url
     * @param programID the program id
     * @param eventID the event id
     * @param targetType the target type
     * @param targetALLJson the target all json
     * @param tstId the tst id
     * @throws ParseException the parse exception
     */
    @Test(groups = { Groups.SANITY1 }, dataProvider = "createExecutionDRALLGatewaysECO", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drEventForEco(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson, final String tstId)
            throws ParseException {

        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;
        // Creating DR
        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = targetALLJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 190))).replaceFirst(
                "<end_time>", Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 240)));
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

        // Updating nextphasetime in ef_Event_control table

        eControl.updateNextPhaseTime(proramEventId);
        setLogString("Updated Next Phase Time", true);

        // Gets the list of location.

        final List<Integer> eventLocation = lspEventLocation.fetchByLocationId(proramEventId);
        setLogString("Available Location : " + eventLocation, true);

        WaitUtil.mediumWait();
        setLogString("After 20 Seconds ", true);

        // At this time location status should be SCHEDULED

        final String updatedEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + updatedEventStatus, true);

        final List<String> locIdStatus1 = lspEventLocation
                .fullListByLocationIdStatus(proramEventId);
        setLogString("Available Locations : " + locIdStatus1, true);

        // This wait makes sure since start time was updated as now+2 mins we have enough wait to
        // ensure the DR is kicked in
        final Map<String, Object> startTime = lsProEvent.updateEventByStartDate(eventName);
        setLogString("Details after updated the start time : " + startTime, true);

        WaitUtil.hugeWait();
        setLogString("After 5 mins ", true);

        // Now event should have status as ACTIVE. we need an assert statement here
        final String status = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status after 5 mins : " + status, true);
        Assert.assertTrue(status.equalsIgnoreCase(ASTATUS), "Still status is not in ACTIVE mode");

        // This should have location Ids with status as SKIPPED and ACTIVE


        final List<String> locIdStatus = lspEventLocation.fullListByLocationIdStatus(proramEventId);
        setLogString("Location and Status after 5 mins : " + locIdStatus, true);


        WaitUtil.hugeWait();
        setLogString("After 5 minutes ", true);
        
        /*
         * Action Item: get count of locations and thermostat linked to these locations which have
         * status as ACTIVE and SKIPPED This is needed for report assertion
         */
        
        final Map<String, Integer> countDetails = getCountOfTstLocations(proramEventId);
        setLogString("Location and Thermostat Count based on status : " + countDetails, true);
        


        // Test thermostatId
        final int thermostatId = Integer.parseInt(tstId); /* tstatEvent.listByGroupId(grpId); */
        setLogString("Thermostat Id : " + thermostatId, true);

        // This should give the record from ef_thermostat_event table for the given thermostat and
        // event
        /*
         * Action Item: {start event enty:Assertions on algorithm 210 should have event_type ALGO,
         * event_status PROCESSED, event_phase 0 and action startEvent}, {end event entry:
         * Assertions on algorithm 210 should have event_type ALGO, event_status PROCESSED,
         * event_phase 2, action endEvent, groupevent_id = event_id.2(2713.2)}, {pre cool entry:
         * assert there are three -50 algo control entry 1.) event phase 0 and new_setting as 80.00
         * and event_type LS 2.) event phase 1 and new_setting as 84.00 and event_type LS 3.) event
         * phase 2 and new_setting as 80.00 and event_type LS} As an eg: on qa-plat run this query :
         * select * from ef_thermostat_event where thermostat_id = 2641 and group_event_id in
         * (2715,2715.2)
         */

        /*
         * List<PartitionedThermostatEvent> allDetails = tstatEvent.std210DR(thermostatId, grpId);
         * setLogString(allDetails.toString(), true);
         */

        final List<ThermostatEvent> tstatDetails = efTsatEvent.fetchDetailsByThermostatId(
                thermostatId, grpId);
        setLogString(tstatDetails.toString(), true);
        isStartEntryValidated(tstatDetails);
        Assert.assertTrue(isStartEntryValidated(tstatDetails),
                "Values were not matched in Start Entry");
        Assert.assertTrue(isEntryValues(tstatDetails, algorithmId), "Values were not matched ");
        setLogString("Verified with start event and its entries ", true);

        WaitUtil.veryHugeWait();
        WaitUtil.veryHugeWait();
        WaitUtil.veryHugeWait();
        setLogString("After 20+ minutes ", true);
        final Double grpEventIdCompleted = grpId + 0.2;
        final List<ThermostatEvent> tstatDetails1 = efTsatEvent.fetchDetailsByThermostatId(
                thermostatId, grpEventIdCompleted);
        System.out.println(tstatDetails1);
        isEndEntryValidated(tstatDetails1, grpEventIdCompleted);
       /* Assert.assertTrue(isEndEntryValidated(tstatDetails1, grpEventIdCompleted),
                "Values were not matched in End Entry");*/
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
       //Commenting the report section for now
      /*  final Map<String, Object> detail = lspEventReport.updatedDetails(proramEventId);
        setLogString("Event Location Report : " + detail, true);
        Assert.assertTrue(isLocationCountValidated(countDetails, detail),
                "Location count is not as same as Report");
        Assert.assertTrue(isTstCountValidated(countDetails, detail),
                "Thermostat count is not as same as Report");*/

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
                    //&& (thermostatEvent.getAlgorithmId().equals(ALGORITHMID_210))
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
            final Double grpEventIdCompleted) {

        boolean endEntryValidate = false;
       // final Double grpEventIdCompleted = groupEventId + 0.2;
        System.out.println("completed group eventId"+grpEventIdCompleted);
        for (ThermostatEvent thermostatEvent : details) {
            System.out.println("ENDEVENT"+thermostatEvent.getAction());
            System.out.println("ALGORITHMID_210"+thermostatEvent.getAlgorithmId());
            System.out.println("EVENTPHASE_2"+thermostatEvent.getEventPhase());
            System.out.println("EVENTSTATUS"+thermostatEvent.getEventStatus());
            System.out.println("EVENTYPE_ALGO"+thermostatEvent.getEventType());
            System.out.println("grpEventIdCompleted"+thermostatEvent.getGroupEventId());
            if ((thermostatEvent.getAction().equalsIgnoreCase(ENDEVENT))
                   // && (thermostatEvent.getAlgorithmId().equals(ALGORITHMID_210))
                    && (thermostatEvent.getEventPhase().equals(EVENTPHASE_2))
                    && (thermostatEvent.getEventStatus().equalsIgnoreCase(EVENTSTATUS))
                    && (thermostatEvent.getEventType().equalsIgnoreCase(EVENTYPE_ALGO))
                    && (grpEventIdCompleted.equals(thermostatEvent.getGroupEventId()))) {
                endEntryValidate = true;
                break;
            }
        }
        System.out.println("endEntryValidate"+endEntryValidate);
        return endEntryValidate;
    }

    /**
     * Checks if is entry values.
     * @param details the details
     * @return true, if is entry values
     */
    public boolean isEntryValues(final List<ThermostatEvent> details, final Integer algorithmId) {

        boolean validate = false;

        boolean eventPhase0 = false;
        boolean eventPhase1 = false;
        boolean eventPhase2 = false;

        for (ThermostatEvent thermostatEvent : details) {
            if (algorithmId.equals(ALGORITHMID_210)) {
                if (thermostatEvent.getAlgorithmId().equals(ALGOID_MINUS_FIFTY)) {

                    final int newSettingValue = thermostatEvent.getNewSetting().intValue();
                    eventPhase0 = checkEventPhase0(thermostatEvent, eventPhase0, newSettingValue);
                    eventPhase1 = checkEventPhase1(thermostatEvent, eventPhase1, newSettingValue);
                    eventPhase2 = checkEventPhase2(thermostatEvent, eventPhase2, newSettingValue);
                    validate = true;
                    break;
                }
            } else if (algorithmId.equals(ALGORITHMID_310)) {
                if (thermostatEvent.getAlgorithmId().equals(ALGOID_MINUS_FIFTY)) {

                    final int newSettingValue = thermostatEvent.getNewSetting().intValue();
                    eventPhase1 = checkEventPhase1(thermostatEvent, eventPhase1, newSettingValue);
                    eventPhase2 = checkEventPhase2(thermostatEvent, eventPhase2, newSettingValue);
                    validate = true;
                    break;
                }
            }
        }

        return validate;
    }

    /**
     * Check event phase0.
     * @param thermostatEvent the thermostat event
     * @param eventPhase0 the event phase0
     * @param newSettingValue the old setting value
     * @return true, if successful
     */

    private boolean checkEventPhase0(final ThermostatEvent thermostatEvent,
            final boolean eventPhase0, final int newSettingValue) {

        return eventPhase0
                || (thermostatEvent.getEventPhase().equals(EVENTPHASE_0)
                        && thermostatEvent.getEventType().equalsIgnoreCase(EVENTYPE_LS) && thermostatEvent
                        .getNewSetting().equals(newSettingValue));
    }

    /**
     * Check event phase1.
     * @param thermostatEvent the thermostat event
     * @param eventPhase1 the event phase1
     * @param newSettingValue the old setting value
     * @return true, if successful
     */
    private boolean checkEventPhase1(final ThermostatEvent thermostatEvent,
            final boolean eventPhase1, final int newSettingValue) {

        return eventPhase1
                || (thermostatEvent.getEventPhase().equals(EVENTPHASE_1)
                        && thermostatEvent.getEventType().equalsIgnoreCase(EVENTYPE_LS) && thermostatEvent
                        .getNewSetting().equals(newSettingValue + 4));
    }

    /**
     * Check event phase2.
     * @param thermostatEvent the thermostat event
     * @param eventPhase2 the event phase2
     * @param newSettingValue the old setting value
     * @return true, if successful
     */
    private boolean checkEventPhase2(final ThermostatEvent thermostatEvent,
            final boolean eventPhase2, final int newSettingValue) {

        return eventPhase2
                || (thermostatEvent.getEventPhase().equals(EVENTPHASE_2)
                        && thermostatEvent.getEventType().equalsIgnoreCase(EVENTYPE_LS) && thermostatEvent
                        .getNewSetting().equals(newSettingValue));
    }
}
