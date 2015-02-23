package com.ecofactor.qa.automation.dao;

import static org.testng.Assert.*;
import static org.testng.Reporter.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.Status;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.ThermostatEventAttributeDef;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The Class ThermostatEventDaoTest.
 */
public class ThermostatEventDaoTest {

    private ThermostatEventDao thermostatEventDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ThermostatEventDaoTest.class);

    /**
     * Setup.
     */
    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        thermostatEventDao = injector.getInstance(ThermostatEventDao.class);
    }

    /**
     * List by thermostat and event sys time range.
     * @throws ParseException the parse exception
     */
    @Test
    public void listByThermostatAndEventSysTimeRange() throws ParseException {

        Calendar calStartTime = DateUtil.parseToCalendar("2013-10-08 5:00:00", DateUtil.DATE_FMT);
        Calendar calEndTime = DateUtil.parseToCalendar("2013-10-08 7:00:00", DateUtil.DATE_FMT);

        List<PartitionedThermostatEvent> thermostatEvents = thermostatEventDao.listByThermostatAndEventSysTimeRange(2685, calStartTime, calEndTime);
        assertNotNull(thermostatEvents);
        assertTrue(thermostatEvents.size() > 0);

        DataUtil.printEventTableGrid(thermostatEvents);
    }

    /**
     * List by thermostat and event sys time.
     * @throws ParseException the parse exception
     */
    @Test
    public void listByThermostatAndEventSysTime() throws ParseException {

        Calendar calStartTime = DateUtil.parseToCalendar("2013-10-8 5:00:00", DateUtil.DATE_FMT);

        List<PartitionedThermostatEvent> thermostatEvents = thermostatEventDao.listByThermostatAndEventSysTime(2735, calStartTime);
        assertNotNull(thermostatEvents);
        assertTrue(thermostatEvents.size() > 0);
    }

    /**
     * Find latest thermostat event.
     */
    @Test
    public void findLatestThermostatEvent() {

        PartitionedThermostatEvent thermostatEvent = thermostatEventDao.findLatestByThermostat(1087);
        assertNotNull(thermostatEvent);
        log("Thermostat thermostatEvent: ======== " + thermostatEvent.getId().getThermostatEventId(), true);
        log("Thermostat Algo: ======== " + ThermostatEventAttributeDef.EventType.USER.eventType(), true);
    }

    /**
     * Prints the grid.
     * @throws ParseException the parse exception
     */
    @Test
    public void printGrid() throws ParseException {

        Calendar calStartTime = DateUtil.parseToCalendar("2013-06-07 12:17:00", DateUtil.DATE_FMT);
        Calendar calEndTime = DateUtil.parseToCalendar("2013-06-07 12:27:00", DateUtil.DATE_FMT);
        DataUtil.printEventTableGrid(2950, calStartTime, calEndTime);
    }

    /**
     * List algo processed events by thermostat and time range.
     */
    @Test
    public void listAlgoProcessedEventsByThermostatAndTimeRange() {

        Calendar calStartTime = DateUtil.parseToCalendar("2013-10-08 7:00:00", DateUtil.DATE_FMT);
        Calendar calEndTime = DateUtil.parseToCalendar("2013-10-08 11:55:00", DateUtil.DATE_FMT);

        List<PartitionedThermostatEvent> events = thermostatEventDao.listAlgoProcessedEventsByThermostatAndTimeRange(31432, calStartTime, calEndTime);
        DataUtil.printEventTableGrid(events);

    }

    /**
     * List by start date and status.
     * @throws ParseException the parse exception
     */
    @Test
    public void listByStartDateAndStatus() throws ParseException {

        Calendar curDate = Calendar.getInstance();
        DriverConfig.setLogString("curDate" + ThermostatEventAttributeDef.Status.PROCESSED.status(), true);
        List<PartitionedThermostatEvent> thermostatEvents = thermostatEventDao.listByStartDateAndStatus(curDate, Status.DRAFT);
        assertNotNull(thermostatEvents);
        assertTrue(thermostatEvents.size() > 0);
    }

    /**
     * Find latest by phase.
     * @throws ParseException the parse exception
     */
    @Test
    public void findLatestByPhase() throws ParseException {

        PartitionedThermostatEvent thermostatEvent = thermostatEventDao.findLatestByPhase(31437, 0);
        assertNotNull(thermostatEvent);
    }

    /**
     * Find thermostat event by event id.
     * @throws ParseException the parse exception
     */
    @Test
    public void findThermostatEventByEventID() throws ParseException {

        List<PartitionedThermostatEvent> thermostatEvents = thermostatEventDao.findThermostatEventByEventID(1374226342760.00);
        assertNotNull(thermostatEvents);
        assertNotNull(thermostatEvents.size() > 0);
    }

    /**
     * Find thermostat event by group event id and thermostat id.
     * @throws ParseException the parse exception
     */
    @Test
    public void findThermostatEventByGroupEventIdAndThermostatId() throws ParseException {

        PartitionedThermostatEvent thermostatEvent = thermostatEventDao.findThermostatEventByGroupEventIdAndThermostatId(1381224192799.00, 31437);
        assertNotNull(thermostatEvent);
    }

    /**
     * List events by thermostat algorithm.
     * @throws ParseException the parse exception
     */
    @Test
    public void listEventsByThermostatAlgorithm() throws ParseException {

        Calendar startDate = DateUtil.parseToCalendar("2013-10-08 5:00:00", DateUtil.DATE_FMT);
        Calendar endDate = DateUtil.parseToCalendar("2013-10-08 11:00:00", DateUtil.DATE_FMT);
        List<PartitionedThermostatEvent> thermostatEvents = thermostatEventDao.listEventsByThermostatAlgorithm(31437, 190, startDate, endDate);
        assertNotNull(thermostatEvents.size() > 0);
    }

    /**
     * Find base temp.
     * @throws ParseException the parse exception
     */
    @Test
    public void findBaseTemp() throws ParseException {

        String startTimestamp = "2013-06-11 10:49:00";
        Calendar startTime = DateUtil.parseToCalendar(startTimestamp, DateUtil.DATE_FMT);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.MINUTE, 50);
        PartitionedThermostatEvent thermostatEvent = thermostatEventDao.findBaseTemp(2023, startTime, endTime);
        assertNotNull(thermostatEvent);
    }

    /**
     * Gets the base temp heat.
     * @return the base temp heat
     * @throws ParseException the parse exception
     */
    @Test
    public void getBaseTempHeat() throws ParseException {

        double baseTempHeat = thermostatEventDao.getBaseTempHeat(31429);
        assertNotNull(baseTempHeat);
    }

    /**
     * Gets the base temp cool.
     * @return the base temp cool
     * @throws ParseException the parse exception
     */
    @Test
    public void getBaseTempCool() throws ParseException {

        double baseTempCool = thermostatEventDao.getBaseTempCool(1085);
        assertNotNull(baseTempCool);
    }

    /**
     * Find latest by thermostat and algorithm.
     * @throws ParseException the parse exception
     */
    @Test
    public void findLatestByThermostatAndAlgorithm() throws ParseException {

        PartitionedThermostatEvent thermostatEvent = thermostatEventDao.findLatestByThermostatAndAlgorithm(2213, 111);
        assertNotNull(thermostatEvent);
    }
}
