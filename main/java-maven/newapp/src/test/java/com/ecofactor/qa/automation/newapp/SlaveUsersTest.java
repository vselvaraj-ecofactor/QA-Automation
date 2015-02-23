/*
 * SlaveUsersTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.newapp.util.mail.MobileMailUtil;
import com.ecofactor.qa.automation.util.mail.TestType;
import com.google.inject.Inject;

/**
 * The Class SlaveUsersTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class })
public class SlaveUsersTest {

    private String environment = "";
    Map<String, List<String>> myMap;
    @Inject
    private MobileUserTestDataConfig mobileUserTestDataConfig;
    @Inject
    MobileMailUtil mailUtil;

    /**
     * After suite.
     * @throws Exception the exception
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {

        mailUtil.slaveUsers(TestType.CONSUMER, mobileUserTestDataConfig.getEnvironment(), myMap);
    }

    /**
     * Slave users.
     * @throws Exception the exception
     */
    @Test(groups = { "sanity1" })
    public void slaveUsers() throws Exception {

        myMap = new HashMap<String, List<String>>();
        environment = System.getProperty("env");
        final Properties properties = new Properties();
        final Properties properties1 = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("slaves.properties"));
        String slaves = properties.getProperty("slaves");
        String[] temp = slaves.split(",");
        for (String slave : temp) {
            properties1.load(getClass().getClassLoader().getResourceAsStream(environment + "_" + slave + "_mobileTest.properties"));
            List<String> arrayList = new ArrayList<String>();
            for (Object key : properties1.keySet()) {
                if (key.toString().contains("username")) {
                    String keyValue = key.toString();
                    int lastIndexOfDot = keyValue.lastIndexOf(".");
                    keyValue = keyValue.substring(0,lastIndexOfDot+1);
                    arrayList.add(properties1.getProperty(key.toString()) + "/"+keyValue);
                }
            }
            myMap.put(slave, arrayList);
        }
    }
}