/*
 * UserDaoTest.java
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

import com.ecofactor.common.pojo.User;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * The Class ThermostatEventDaoTest.
 */
public class UserDaoTest {

    @Inject
    private EFUserDao efUserDao;
    @Inject
    private UserTestDao userTestDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

    /**
     * Setup.
     */
    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        efUserDao = injector.getInstance(EFUserDao.class);
        userTestDao = injector.getInstance(UserTestDao.class);
    }

    /**
     * List by thermostat and event sys time range.
     * @throws ParseException the parse exception
     */
    @Test
    public void findByUserName() throws ParseException {

        User user = efUserDao.findByUserName("ecofactorqaautomation@gmail.com");
        DriverConfig.setLogString("user " + user, true);
    }

    /**
     * List to be deleted users.
     * @throws ParseException the parse exception
     */
    @Test
    public void listToBeDeletedUsers() throws ParseException {

        List<User> userList = efUserDao.listTobeDeletedUsers();
        for (User user : userList) {
        	DriverConfig.setLogString("username " + user.getUsername(), true);
        }

    }

    /**
     * Update a user.
     * @throws ParseException the parse exception
     */
    @Test
    public void updateAUser() throws ParseException {

        WaitUtil.smallWait();

        User user = efUserDao.findByUserName("qa+vi92.lo@ecofactor.com");
        efUserDao.updateUserToProvision(user);

    }

    @Test
    public void getActiveThermostats() {

        userTestDao.getActiveThermostats();
    }

}
