/*
 * ThermostatProgramLogTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.ThermostatProgramLog;
import com.ecofactor.qa.automation.util.DateUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;

// TODO: Auto-generated Javadoc
/**
 * The Class ThermostatProgramLogTest.
 *
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatProgramLogTest {

    private ThermostatProgramLogDao tstatProgLogDao;
    private Logger LOGGER = LoggerFactory.getLogger(ThermostatProgramLogDao.class);

    /**
     * Setup.
     */
    @BeforeMethod
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        tstatProgLogDao = injector.getInstance(ThermostatProgramLogDao.class);
    }

    /**
     * Gets the Base Temperature.
     */
    @Test
    public void getBaseTemp()
    {
        double baseTemp = getCurrentBaseTemp(2769, "Cool");
        LOGGER.debug("Base Temp : " + baseTemp);
    }

    /**
     * Gets the current base temp.
     *
     * @param thermostatId the thermostat id
     * @param mode the mode
     * @return the current base temp
     */
    public double getCurrentBaseTemp(Integer thermostatId, String mode)
    {
        double baseTemp =0;
        List<ThermostatProgramLog> programList = tstatProgLogDao.listUTCCurrentDayLog(thermostatId, DateUtil.getUTCDayOfWeek());
        for (ThermostatProgramLog tstatProgramLog : programList)
        {

            Date startTimeUTC = tstatProgramLog.getStartTimeUTC();
            Date endTimeUTC = tstatProgramLog.getEndTimeUTC();

            DateTime startTimeJoda = new DateTime(startTimeUTC);
            DateTime endTimeJoda = new DateTime(endTimeUTC);

            Calendar startCalendar = DateUtil.convertTimeToUTCCalendar(startTimeJoda);
            Calendar endCalendar = DateUtil.convertTimeToUTCCalendar(endTimeJoda);
            Calendar utcCalendar = DateUtil.getUTCCalendar();

            if (!endCalendar.before(utcCalendar) && !startCalendar.after(utcCalendar)) {

                if(mode.equalsIgnoreCase("Cool"))
                {
                    baseTemp = tstatProgramLog.getCoolSetting();
                }
                else if(mode.equalsIgnoreCase("Heat"))
                {
                    baseTemp =  tstatProgramLog.getHeatSetting();
                }
                break;
            }
        }
        return baseTemp;
    }

    /**
     * List utc current day log.
     */
    @Test
    public void listUTCCurrentDayLog()
    {
        List<ThermostatProgramLog> programList = tstatProgLogDao.listUTCCurrentDayLog(2023, DateUtil.getUTCDayOfWeek());
        Assert.assertNotNull(programList);
    }
    
    /**
     * Find latest by thermostat.
     */
    @Test
    public void findLatestByThermostat()
    {
        ThermostatProgramLog thermostatEvent = tstatProgLogDao.findLatestByThermostat(2023);
        Assert.assertNotNull(thermostatEvent);
    }
}
