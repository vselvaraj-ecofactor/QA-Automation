/*
 * OpsScriptTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.opsscript.regression;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao;
import com.ecofactor.qa.automation.dao.opsscript.OpsScriptDaoModule;
import com.ecofactor.qa.automation.opsscript.OpsScriptModule;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * The Class OpsScriptTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, OpsScriptModule.class, OpsScriptDaoModule.class })
public class OpsScriptTest {

    @Inject
    private OpsScriptDao opsScriptDao;
    @Inject
    private TestLogUtil testLogUtil;
    private long start;

    /**
     * Inits the method.
     * @param method the method
     * @param param the param
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(Method method, Object[] param) {

        testLogUtil.logStart(method, param);
        start = System.currentTimeMillis();
    }

    /**
     * End method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(Method method) {

        long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * Algo subscription verification.
     * @param algoIds the algo ids
     * @param calculationNumber the calculation number
     */
    @Test(groups = { "sanity" })
    public void algoSubscriptionVerification() {

        if (System.getProperty("algoIds") == null || System.getProperty("calculationPercentage") == null) {
            Assert.fail("One of the Parameter value is null");
        }

        String[] algoIds = System.getProperty("algoIds").split(",");
        Integer calculationPercentage = Integer.valueOf(System.getProperty("calculationPercentage"));

        String algoString = "";
        for (String algoIdString : algoIds) {
            algoString += algoIdString + ", ";
        }

        DriverConfig.setLogString("Get algo Subscription count for " + algoString, true);
        Integer subscriptionCount = opsScriptDao.algoSubscriptionCount(algoIds);
        DriverConfig.setLogString("Subscription count : " + subscriptionCount, true);

        DriverConfig.setLogString("Get Job Count of the Interval", true);
        Integer jobCount = opsScriptDao.jobCountOfTheDay();
        DriverConfig.setLogString("Job count : " + jobCount, true);

        DriverConfig.setLogString("Calculate the steps " + subscriptionCount + "/" + "100 * " + calculationPercentage, true);
        double calcValue = subscriptionCount / 100 * calculationPercentage;
        DriverConfig.setLogString("Calculated value is " + calcValue, true);

        DriverConfig.setLogString("Verify the JobCount value is greater than or equal to Calculated value ", true);
        Assert.assertTrue("The algorithm subscription job count is lesser than calculationPercentage.",jobCount >= calcValue);

        DriverConfig.setLogString("Result Value is " + calcValue, true);
    }

    /**
     * Comcast ops verification.
     * @param algoIds the algo ids
     * @param ecpCoreId the ecp core id
     * @param calculationNumber the calculation number
     */
    @Test(groups = { "sanity" })
    public void comcastOPSVerification() {

        if (System.getProperty("algoIds") == null || System.getProperty("ecpCoreId") == null || System.getProperty("calculationPercentage") == null) {
            Assert.fail("One of the Parameter value is null");
        }

        String[] algoIds = System.getProperty("algoIds").split(",");
        Integer ecpCoreId = Integer.valueOf(System.getProperty("ecpCoreId"));
        Integer calculationPercentage = Integer.valueOf(System.getProperty("calculationPercentage"));

        String algoString = "";
        for (String algoIdString : algoIds) {
            algoString += algoIdString + ", ";
        }

        DriverConfig.setLogString("Get algo Subscription count for comcastOPS " + algoString, true);
        Integer comcastCount = opsScriptDao.comcastAlgorithmCount(algoIds, ecpCoreId);
        DriverConfig.setLogString("Comcast Subscription count : " + comcastCount, true);

        DriverConfig.setLogString("Get algo Subscription count for comcastOPS " + algoString, true);
        Integer jobCount = opsScriptDao.comcastThermostatJobCount(ecpCoreId);
        DriverConfig.setLogString("Comcast job count : " + jobCount, true);

        DriverConfig.setLogString("Calculate the steps " + comcastCount + "/" + "100 * " + calculationPercentage, true);
        double calcValue = comcastCount / 100 * calculationPercentage;
        DriverConfig.setLogString("Calculated value is " + calcValue, true);

        DriverConfig.setLogString("Verify the Comcast JobCount value is greater than or equal to Calculated value ", true);
        Assert.assertTrue("The comcast job count is lesser than calculationPercentage.",jobCount >= calcValue);

        DriverConfig.setLogString("Result Value is " + calcValue, true);
    }

    /**
     * Non comcast ops verification.
     * @param algoIds the algo ids
     * @param ecpCoreId the ecp core id
     * @param calculationNumber the calculation number
     */
    @Test(groups = { "sanity" })
    public void nonComcastOPSVerification() {

        if (System.getProperty("algoIds") == null || System.getProperty("ecpCoreId") == null || System.getProperty("calculationPercentage") == null) {
            Assert.fail("One of the Parameter value is null");
        }

        String[] algoIds = System.getProperty("algoIds").split(",");
        Integer ecpCoreId = Integer.valueOf(System.getProperty("ecpCoreId"));
        Integer calculationPercentage = Integer.valueOf(System.getProperty("calculationPercentage"));

        String algoString = "";
        for (String algoIdString : algoIds) {
            algoString += algoIdString + ", ";
        }
        DriverConfig.setLogString("Get Non Comcast Algorithm count for " + algoString, true);
        Integer nonComcastCount = opsScriptDao.nonComcastAlgorithmCount(algoIds, ecpCoreId);
        DriverConfig.setLogString("Non Comcast Algorithm count is " + nonComcastCount, true);

        DriverConfig.setLogString("Get Job Count of the Interval ECP core " + ecpCoreId, true);
        Integer nonComcastjobCount = opsScriptDao.nonComcastThermostatJobCount(ecpCoreId);
        DriverConfig.setLogString("Job count : " + nonComcastjobCount, true);

        DriverConfig.setLogString("Calculate the steps " + nonComcastCount + "/" + "100 * " + calculationPercentage, true);
        double calcValue = nonComcastCount / 100 * calculationPercentage;
        DriverConfig.setLogString("Calculated value is " + calcValue, true);

        DriverConfig.setLogString("Verify the Non Comcast JobCount value is greater than or equal to Calculated value ", true);
        Assert.assertTrue("The non comcast job count is lesser than calculationPercentage.",nonComcastjobCount >= calcValue);

        DriverConfig.setLogString("Result Value is " + calcValue, true);

    }

}
