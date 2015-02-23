/*
 * ThermostatAlgoControlDaoTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import static org.testng.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.qa.automation.util.DateUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The Class ThermostatAlgoControlDaoTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatAlgoControlDaoTest {

    private ThermostatAlgoControlDao thAlgoControlDao;
    private final static Logger LOGGER = LoggerFactory.getLogger(ThermostatAlgoControlDaoTest.class);

    /**
     * Setup.
     */
    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        thAlgoControlDao = injector.getInstance(ThermostatAlgoControlDao.class);
    }

    /**
     * Active events.
     * @throws ParseException the parse exception
     */
    @Test
    public void activeEvents() throws ParseException {

        ThermostatAlgorithmController thAlgo = thAlgoControlDao.findActiveEvent(2203, 114);
        assertNotNull(thAlgo);
    }

    /**
     * Algo next phase time.
     * @throws ParseException the parse exception
     */
    @Test
    public void algoNextPhaseTime() throws ParseException {

        Calendar nextPhaseTime = null;
        Calendar currentTime = DateUtil.getUTCCalendar();
        ThermostatAlgorithmController thAlgo = thAlgoControlDao.findActiveEvent(2023, 113);
        String nextPhase = null;
        if (thAlgo != null) {
            nextPhase = DateUtil.getTimeStamp(thAlgo.getNextPhaseTime());
        }

        nextPhaseTime = DateUtil.parseToUTCCalendar(nextPhase, DateUtil.DATE_FMT_FULL);

        LOGGER.error("Current Time " + DateUtil.formatToUTC(currentTime, DateUtil.DATE_FMT_FULL_TZ) + ", Next Phase Time "
                + DateUtil.formatToUTC(nextPhaseTime, DateUtil.DATE_FMT_FULL_TZ));
    }

    /**
     * Zero phase active event.
     * @throws ParseException the parse exception
     */
    @Test
    public void zeroPhaseActiveEvent() throws ParseException {

    	ThermostatAlgorithmController thAlgo = thAlgoControlDao.findZeroPhaseActiveEvent(31429, 113, 75.00);
        assertNotNull(thAlgo);
    }

    /**
     * First phase active event.
     * @throws ParseException the parse exception
     */
    @Test
    public void firstPhaseActiveEvent() throws ParseException {

    	ThermostatAlgorithmController thAlgo = thAlgoControlDao.findFirstPhaseActiveEvent(31429, 113, 76.00);
        assertNotNull(thAlgo);
    }

    /**
     * Active algorithm.
     * @throws ParseException the parse exception
     */
    @Test
    public void activeAlgorithm() throws ParseException {

    	List<ThermostatAlgorithmController> thAlgo = thAlgoControlDao.listActiveAlgoControl(31429, 113);
    	assertNotNull(thAlgo);
    	assertTrue(thAlgo.size() > 0);
    }

    /**
     * Active spo.
     * @throws ParseException the parse exception
     */
    @Test
    public void activeSPO() throws ParseException {

    	ThermostatAlgorithmController thAlgo = thAlgoControlDao.findSPOStart(2159, 191);
    	assertNotNull(thAlgo);
    }
}
