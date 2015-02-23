package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

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

/**
 * The Class DRAPI_Execution_Test.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class DRAPI_Execution_Comcast_Test extends AbstractTest {

    private static String ASTATUS = "ACTIVE";
    private static String PSTATUS = "PENDING";
    private static String ISTATUS = "INACTIVE";

    @Inject
    private DRAPI_Test drapitest;

    @Inject
    private static DRApiConfig apiConfig;

    @Inject
    private LSProgramEventDao lsProEvent;

    @Inject
    private EventControlDao eControl;

    @Inject
    private LSProgramEventReportDao lspEventReport;

    @Inject
    private ThermostatEventDao tstatEvent;

    @Inject
    private LSProgramEventLocationDao lspEventLocation;

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
     * Execution of DR EVENT first - step of LS-Program-Event id.
     * @throws ParseException
     */

    @Test(groups = { Groups.SANITY1 }, dataProvider = "createDRExecutionComCAST", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drExecutionEventForComCast(final String drUrl, final String programID,
            final String eventID, final String targetType, final String targetALLJson,
            final String locationId) throws ParseException {

        long timeStamp = System.currentTimeMillis();

        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "false");

        String json = targetALLJson;
        json = json
                .replaceFirst("<location_id>", locationId)
                .replaceFirst("<start_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 10)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 25)));

        setLogString("URL Values of the API \n" + createUrl + "\n" + json, true);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));

        final String resultValue = HTTPSClient.getResultString(response.getEntity());
        setLogString("response :'" + resultValue + "'", true);

        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + response.getStatusLine());

        final String eventName = drEventName(resultValue);
        setLogString("DR EventName: " + eventName, true);

        final int proramEventId = lsProEvent.programEventId(eventName);
        setLogString("DR Event Id: " + proramEventId, true);

        final String eventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + eventStatus, true);

        WaitUtil.hugeWait();
        setLogString("After 2 Minutes ", true);

        final String UpdatedEventStatus = lsProEvent.listByEventName(eventName);
        setLogString("DR Event Status : " + UpdatedEventStatus, true);

        /*final Map<Object, Object> locIdStatus = lspEventLocation
                .listByLocationIdStatus(proramEventId);
        setLogString("Available Locations with Status : " + locIdStatus, true);
*/
        final int locId = 0;//locationId(locIdStatus, ASTATUS);
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
    private String drEventName(final String response) {

        StringTokenizer st = new StringTokenizer(response, ",");
        String eventID = "";
        while (st.hasMoreElements()) {

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
    public int locationId(Map<Object, Object> allDetails, String status) {

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
}
