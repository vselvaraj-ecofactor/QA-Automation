/*
 * LocationDaoTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
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

import com.ecofactor.common.pojo.Location;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class LocationDaoTest {

    @Inject
    private LocationDao locationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDaoTest.class);

    /**
     * Setup.
     */
    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        locationDao = injector.getInstance(LocationDao.class);
    }

    /**
     * List by thermostat and event sys time range.
     * @throws ParseException the parse exception
     */
    @Test
    public void listLocation() throws ParseException {

        List<Location> locationList = locationDao.getLocationByUserId(42204);
        for (Location location : locationList) {
            System.out.println("locationList==" + location.getName());
        }
    }

    /**
     * Gets the location for a thermostat.
     * @return the location for a thermostat
     * @throws ParseException the parse exception
     */
    @Test
    public void getLocationForAThermostat() throws ParseException {

        Location location = locationDao.getLocationForAThermostat(23326);
        System.out.println("location Name : " + location.getName());

    }

    @Test
    public void locationForAnUser() throws ParseException {

        List<Location> locationList = locationDao.getLocationByUserName("qa+twolocations@ecofactor.com");
        for (Location location : locationList) {
            System.out.println("locationList==" + location.getName());
        }
        System.out.println("locationList Size: " + locationList.size());
    }
}
