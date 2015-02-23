/*
 * SubscriptionTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.regression;

import static org.testng.Reporter.*;

import java.lang.reflect.Method;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.qtc.QTCModule;
import com.ecofactor.qa.automation.qtc.data.SubscriptionDataProvider;
import com.ecofactor.qa.automation.qtc.page.QTCLogin;
import com.ecofactor.qa.automation.qtc.page.TaskPage;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * The Class SubscriptionTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, QTCModule.class, DaoModule.class })
public class SubscriptionTest {

    @Inject
    private QTCLogin qtcLogin;

    @Inject
    private TaskPage taskPage;

    /**
     * Inits the method.
     * @param method the method
     * @param param the param
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(Method method, Object[] param) {

        log("Started test " + method.getDeclaringClass().getSimpleName() + "." + method.getName(), true);
        try {
            qtcLogin.loadPage();
        } catch (Throwable e) {
            log("Error in before method " + e.getMessage(), true);
        }
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            qtcLogin.end();
        } catch (Throwable e) {
            log("Error in after suite " + e.getMessage(), true);
        }
    }

    /**
     * Subscribe a thermostat.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     */
    @Test(dataProvider = "subscribeThermostat", dataProviderClass = SubscriptionDataProvider.class, groups = {
            "smoke", "sanity" })
    public void subscribeThermostat(String userName, String password, String thermostatId, String algoName) {

        qtcLogin.login(userName, password);

        taskPage.clickTask();

        taskPage.selectAlgoSubscribe();

        taskPage.selectCsvInput();

        taskPage.clickNext();

        taskPage.selectAction("SUB");

        taskPage.selectAlgo(algoName);

        taskPage.queryCsvInput(thermostatId);

        taskPage.clickVerifyCsv();

        taskPage.clickNext2();

        taskPage.startTask();

        taskPage.verifyInTable(Integer.parseInt(thermostatId), algoName);

    }

    /**
     * Unsubscribe a thermostat.
     * @param userName the user name
     * @param password the password
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     */
    @Test(dataProvider = "unsubcsribeThermostat", dataProviderClass = SubscriptionDataProvider.class, groups = {
            "smoke", "sanity" })
    public void unsubscribeThermostat(String userName, String password, String thermostatId, String algoName) {

        qtcLogin.login(userName, password);

        taskPage.clickTask();

        taskPage.selectAlgoSubscribe();

        taskPage.selectCsvInput();

        taskPage.clickNext();

        taskPage.selectAction("UNSUB");

        taskPage.selectAlgo(algoName);

        taskPage.queryCsvInput(thermostatId);

        taskPage.clickVerifyCsv();

        taskPage.clickNext2();

        taskPage.startTask();

        taskPage.verifyNotInTable(Integer.parseInt(thermostatId), algoName);

    }

}
