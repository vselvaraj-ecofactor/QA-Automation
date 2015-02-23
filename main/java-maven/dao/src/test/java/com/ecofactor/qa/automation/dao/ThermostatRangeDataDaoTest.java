package com.ecofactor.qa.automation.dao;

import static org.testng.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;
import com.ecofactor.qa.automation.util.DateUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ThermostatRangeDataDaoTest {

    private ThermostatRangeDataDao thermostatRangeDataDao;

    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        thermostatRangeDataDao = injector.getInstance(ThermostatRangeDataDao.class);
    }

    /**
     * List by thermostat and start time range.
     * @throws ParseException the parse exception
     */
    @Test
    public void listByThermostatAndStartTimeRange() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        Calendar calStartTime = Calendar.getInstance();
        Calendar calEndTime = Calendar.getInstance();
        Date startDate = (Date) formatter.parse("2013-09-5 6:00:00");
        Date endDate = (Date) formatter.parse("2013-09-5 10:40:00");
        calStartTime.setTime(startDate);
        calEndTime.setTime(endDate);
        List<PartitionedThermostatRangeData> thermostatRangeDatas = thermostatRangeDataDao.listByThermostatAndStartTimeRange(32686, calStartTime, calEndTime);
        assertNotNull(thermostatRangeDatas);
        assertTrue(thermostatRangeDatas.size() > 0);
        //DataUtil.printRangeDataTableGrid(thermostatRangeDatas);
    }

    /**
     * Find latest by thermostat.
     */
    @Test
    public void findLatestByThermostat() {

        PartitionedThermostatRangeData thermostatRangeData = thermostatRangeDataDao.findLatestByThermostat(32686);
        assertNotNull(thermostatRangeData);
    }

    /**
     * Find max cool temp by thermostat.
     */
    @Test
    public void findMaxCoolTempByThermostat() {

        String startTimestamp = "2013-06-11 10:49:00";
        Calendar startTime = DateUtil.parseToCalendar(startTimestamp, DateUtil.DATE_FMT);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.MINUTE, 1);

        // ThermostatRangeData thermostatRangeData =
        // thermostatRangeDataDao.findMaxCoolTempByThermostat(2023, startTime, endTime);
        // assertNotNull(thermostatRangeData);
    }

}
