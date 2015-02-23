/*
 * ThermostatDaoTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The Class ThermostatDaoTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatDaoTest {

    private  ThermostatDao  thermostatDao;
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ThermostatDaoTest.class);

    /**
     * Setup.
     */
    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        thermostatDao = injector.getInstance(ThermostatDao.class);
    }

    /**
     * List by thermostat id.
     * @throws ParseException the parse exception
     */
    @Test
    public void listByThermostatId() throws ParseException {

        List<Integer> thList = thermostatDao.getThermostatIdForUser("qa+apps-hc20@ecofactor.com");
        DriverConfig.setLogString("List of thermostats :" + thList, true);
    }

    @Test
    public void tstatId() throws ParseException {

        Thermostat tstat = thermostatDao.findByThermostatId(String.valueOf("6332"));
        DriverConfig.setLogString("List of thermostats :" + tstat.getName(), true);
    }
}
