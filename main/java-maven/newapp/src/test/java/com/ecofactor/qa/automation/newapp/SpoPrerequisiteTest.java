/*
 * SpoPrerequisiteTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.newapp.service.AlgorithmService;
import com.ecofactor.qa.automation.newapp.service.DataService;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.qtc.QTCModule;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class SpoPrerequisiteTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class,
        QTCModule.class })
public class SpoPrerequisiteTest {

    @Inject
    private DataService dataService;
    @Inject
    private AlgorithmService algorithmService;

    private final static int SPO_COOL = 190;
    private final static String COOL = "Cool";

    /**
     * Spo default config.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultSavingsEnegry", dataProviderClass = CommonsDataProvider.class)
    public void spoDefaultConfig(final String userName, final String password,
            final Integer thermostatId) {

        dataService.init("spoDefaultConfig", COOL, thermostatId);
        DriverConfig
                .setLogString(
                        "Update Algo control table to status CANCEL, if any existing SPO COOL is running for thermostat id : "
                                + thermostatId, true);
        dataService.updateAlgoControlToInactive(thermostatId, SPO_COOL);
        DriverConfig
                .setLogString(
                        "Update Algo control table to status CANCEL, if any existing SPO HEAT is running for thermostat id : "
                                + thermostatId, true);
        dataService.updateAlgoControlToInactive(thermostatId, 191);
        mediumWait();
        algorithmService.createAlgoControl(thermostatId, SPO_COOL);
        dataService.init("spoDefaultConfig", COOL, thermostatId);
    }
}
