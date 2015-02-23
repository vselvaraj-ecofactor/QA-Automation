/*
 * TestlinkUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.constants.ResponseDetails;
import br.eti.kinoshita.testlinkjavaapi.model.CustomField;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;

/**
 * TestlinkUtil is the helper class to get data from testlink.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestlinkUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(TestlinkUtil.class);
    private final TestlinkConfig testlinkConfig = new TestlinkConfig();
    private final Properties consumerTestCaseIds = new Properties();
    private final Properties insiteTestCaseIds = new Properties();
    private final Properties algorithmTestCaseIds = new Properties();
    private final Properties apiTestCaseIds = new Properties();
    private final Properties opsTestCaseIds = new Properties();
    private final Properties comcastTestCaseIds = new Properties();
    private final Properties mobileTestCaseIds = new Properties();
    private final Properties newAppTestCaseIds = new Properties();
    private final Properties applicationsTestCaseIds = new Properties();

    public String getTestCaseId(final String project, final String method) {

        String testCaseId = null;
        try {
            if (project.equals("Consumer")) {
                if (consumerTestCaseIds.isEmpty()) {
                    consumerTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "consumerTestlink.properties"));
                }
                testCaseId = consumerTestCaseIds.getProperty(method);
            } else if (project.equals("Insite")) {
                if (insiteTestCaseIds.isEmpty()) {
                    insiteTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "insiteTestlink.properties"));
                }
                testCaseId = insiteTestCaseIds.getProperty(method);
            } else if (project.equals("Algorithm")) {
                if (algorithmTestCaseIds.isEmpty()) {
                    algorithmTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "algorithmTestlink.properties"));
                }
                testCaseId = algorithmTestCaseIds.getProperty(method);
            } else if (project.equals("API")) {
                if (apiTestCaseIds.isEmpty()) {
                    apiTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "apiTestlink.properties"));
                }
                testCaseId = apiTestCaseIds.getProperty(method);
            } else if (project.equals("OPS")) {
                if (opsTestCaseIds.isEmpty()) {
                    opsTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "opsTestlink.properties"));
                }
                testCaseId = opsTestCaseIds.getProperty(method);
            } else if (project.equals("Comcast")) {
                if (comcastTestCaseIds.isEmpty()) {
                    comcastTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "comcastTestlink.properties"));
                }
                testCaseId = comcastTestCaseIds.getProperty(method);
            } else if (project.equals("Mobile")) {
                if (mobileTestCaseIds.isEmpty()) {
                    mobileTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "mobileTestlink.properties"));
                }
                testCaseId = mobileTestCaseIds.getProperty(method);
            } else if (project.equals("Mobile Regression New App")) {
                if (newAppTestCaseIds.isEmpty()) {
                    newAppTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "newAppsTestlink.properties"));
                }
                testCaseId = newAppTestCaseIds.getProperty(method);
            } else if (project.equals("ConsumerAPI") || project.equals("Applications")
                    || project.equals("Platform") || project.equals("Mobile Regression New App")) {
                if (applicationsTestCaseIds.isEmpty()) {
                    applicationsTestCaseIds.load(getClass().getClassLoader().getResourceAsStream(
                            "applicationsTestlink.properties"));
                }
                testCaseId = applicationsTestCaseIds.getProperty(method);
            }

        } catch (Exception e) {
            LOGGER.error("Error loading properties " + e.getMessage());
            e.printStackTrace();
        }
        return testCaseId;
    }

    /**
     * Gets the test case ids.
     * @param project the project
     * @param plan the plan
     * @return the test case ids
     */
    public void updateTestCaseId(final String module) {

        TestLinkAPI api = null;
        try {

            api = new TestLinkAPI(new URL(testlinkConfig.get(TestlinkConfig.URL)),
                    testlinkConfig.get(TestlinkConfig.DEV_KEY));
        } catch (MalformedURLException e) {
            LOGGER.error("Error creating Testlink api " + e.getMessage());
        }

        LOGGER.info("Please wait for few minutes, as it will get time to read and update.");
        final TestProject[] testProjects = api.getProjects();
        for (final TestProject testProject : testProjects) {
            LOGGER.info("Getting project Plans for " + testProject.getName());
            LOGGER.info("Project name: " + module);

            if (module == null || testProject.getName().contains(module)) {
                LOGGER.info("Getting Test Plans for " + testProject.getName());
                final TestPlan[] testPlans = api.getProjectTestPlans(testProject.getId());

                for (final TestPlan testPlan : testPlans) {

                    LOGGER.info("Test Plans Name : " + testPlan.getName());

                    final boolean use = (testPlan.getName().contains("QA-APPS") && (testPlan
                            .getName().contains("Smoke") || testPlan.getName().contains("UI") || testPlan
                            .getName().contains("Regression")))
                            || testPlan.getName().contains("API")
                            || testPlan.getName().contains("DR Creation")
                            || testPlan.getName().contains("Query")
                            || testPlan.getName().contains("Comcast")
                            || testPlan.getName().contains("Android")
                            || testPlan.getName().contains("Desktop")
                            || testPlan.getName().contains("Samsung")
                            || testPlan.getName().contains("HTC")
                            || testPlan.getName().contains("DEV")
                            || testPlan.getName().contains("Mobile")
                            || testPlan.getName().contains("APPS-Sprint Skor Test plan")
                            || testPlan.getName().contains("chrome")
                            || testPlan.getName().contains("firefox")
                            || (testPlan.getName().contains("QA-PLAT") && (testPlan.getName()
                                    .contains("SPO")
                                    || testPlan.getName().contains("ST")
                                    || testPlan.getName().contains("LS1") || testPlan.getName()
                                    .contains("LS2")));

                    if (use) {

                        LOGGER.info("Getting Test Cases for " + testPlan.getName());

                        final TestCase[] testCases = api.getTestCasesForTestPlan(testPlan.getId(),
                                null, null, null, null, null, null, null, ExecutionType.AUTOMATED,
                                Boolean.TRUE, null);

                        for (final TestCase testCase : testCases) {
                            LOGGER.info("Found Test Case " + testCase.getFullExternalId());
                            final CustomField customField = api.getTestCaseCustomFieldDesignValue(
                                    testCase.getId(), null, /* testCaseExternalId */
                                    testCase.getVersion(), testProject.getId(), "test_method",
                                    ResponseDetails.FULL);
                            final String method = customField.getValue().substring(
                                    customField.getValue().lastIndexOf(".") + 1);
                            final String testCaseId = testCase.getFullExternalId();

                            if (testProject.getName().equals("Consumer")) {
                                consumerTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("Insite")) {
                                insiteTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("Algorithm")) {
                                algorithmTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("API")) {
                                apiTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("OPS")) {
                                opsTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("Comcast")) {

                                comcastTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("Mobile")) {
                                mobileTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("Mobile Regression New App")) {
                                newAppTestCaseIds.setProperty(method, testCaseId);
                            } else if (testProject.getName().equals("Applications")
                                    || testProject.getName().equals("ConsumerAPI")
                                    || testProject.getName().equals("Platform")) {
                                applicationsTestCaseIds.setProperty(method, testCaseId);
                            }
                        }

                    }
                }
            }
        }

        try {
            consumerTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/consumerTestlink.properties"), null);
            insiteTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/insiteTestlink.properties"), null);
            algorithmTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/algorithmTestlink.properties"), null);
            apiTestCaseIds.store(new FileOutputStream("src/main/resources/apiTestlink.properties"),
                    null);
            opsTestCaseIds.store(new FileOutputStream("src/main/resources/opsTestlink.properties"),
                    null);
            comcastTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/comcastTestlink.properties"), null);
            mobileTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/mobileTestlink.properties"), null);
            newAppTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/newAppsTestlink.properties"), null);
            applicationsTestCaseIds.store(new FileOutputStream(
                    "src/main/resources/applicationsTestlink.properties"), null);
        } catch (Exception e) {
            LOGGER.error("Error writing to properties " + e.getMessage());
        }
    }

    /**
     * The main method.
     * @param args the arguments
     */
    public static void main(String[] args) {

        final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
        logger.setLevel(Level.INFO);

        final TestlinkUtil testlinkUtil = new TestlinkUtil();

        // If you need to run a particular module change the null value to module name
        // For Example : testlinkUtil.updateTestCaseId("OPS");
        testlinkUtil.updateTestCaseId("Applications");
        testlinkUtil.updateTestCaseId("Platform");
        testlinkUtil.updateTestCaseId("Mobile Regression New App");
        testlinkUtil.updateTestCaseId("Insite");
        LOGGER.info("Testlink util Completed");
        LOGGER.info("=======================");
    }
}
