/*
 * OpsScriptDaoTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao;
import com.ecofactor.qa.automation.dao.opsscript.OpsScriptDaoModule;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * The Class ThermostatEventDaoTest.
 */
public class OpsScriptDaoTest {

    @Inject
    private OpsScriptDao opsScriptDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(OpsScriptDaoTest.class);

    /**
     * Setup.
     */
    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new OpsScriptDaoModule());
        opsScriptDao = injector.getInstance(OpsScriptDao.class);
    }

    /**
     * List by thermostat and event sys time range.
     * @throws ParseException the parse exception
     */
    @Test
    public void findByAlgorithmSubscriptionCount() throws ParseException {

        String[] algoId = new String[] { "190", "191" };
        int count = opsScriptDao.algoSubscriptionCount(algoId);
        DriverConfig.setLogString("count = " + count, true);
    }

    /**
     * Find job count.
     * @throws ParseException the parse exception
     */
    @Test
    public void findJobCount() throws ParseException {

        int count = opsScriptDao.jobCountOfTheDay();
        DriverConfig.setLogString("count = " + count, true);
    }

    /**
     * Comcast algorithm count.
     * @throws ParseException the parse exception
     */
    @Test
    public void comcastAlgorithmCount() throws ParseException {

        String[] algoId = new String[] { "190", "191" };
        int count = opsScriptDao.comcastAlgorithmCount(algoId, 236);
        DriverConfig.setLogString("Comcast Algorithm Count = " + count, true);
    }

    /**
     * Comcast job count.
     * @throws ParseException the parse exception
     */
    @Test
    public void comcastJobCount() throws ParseException {

        int count = opsScriptDao.comcastThermostatJobCount(236);
        DriverConfig.setLogString("Comcast Job Count = " + count, true);
    }

    /**
     * Non comcast algorithm count.
     * @throws ParseException the parse exception
     */
    @Test
    public void nonComcastAlgorithmCount() throws ParseException {

        String[] algoId = new String[] { "190", "191" };
        int count = opsScriptDao.nonComcastAlgorithmCount(algoId, 236);
        DriverConfig.setLogString("Non-Comcast Algorithm Count = " + count, true);
    }

    /**
     * Non comcast thermostat job count.
     * @throws ParseException the parse exception
     */
    @Test
    public void nonComcastThermostatJobCount() throws ParseException {

        int count = opsScriptDao.nonComcastThermostatJobCount(236);
        DriverConfig.setLogString("Non-Comcast Thermostyat Job Count = " + count, true);
    }

}
