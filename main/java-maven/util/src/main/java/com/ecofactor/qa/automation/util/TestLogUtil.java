/*
 * TestLogUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.lang.reflect.Method;

import com.ecofactor.qa.automation.util.test.FeatureUtil;
import com.google.inject.Inject;

/**
 * TestLogUtil provides helper methods to log the start and completion of test methods.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestLogUtil {

    @Inject
    private TestlinkUtil testLinkUtil;
    @Inject
    private FeatureUtil featureUtil;

    /**
     * Log start.
     * @param method the method
     * @param params the params
     */
    public void logStart(Method method) {

        String project = null;
        String absoluteName = method.getDeclaringClass().getName();
        if (absoluteName.contains("consumer")) {
            project = "Consumer";
        } else if (absoluteName.contains("insite")) {
            project = "Insite";
        } else if (absoluteName.contains("algorithm")) {
            project = "Algorithm";
        } else if (absoluteName.contains("api")) {
            project = "API";
        } else if (absoluteName.contains("opsscript")) {
            project = "OPS";
        } else if (absoluteName.contains("comcast")) {
            project = "Comcast";
        } else if (absoluteName.contains("mobile")) {
            project = "New App Automation";
        }

        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        String name = method.getDeclaringClass().getSimpleName();
        String test = method.getName();
        String testCaseId = testLinkUtil.getTestCaseId(project, name + "#" + test);
        DriverConfig.setLogString("Started test \033[43;1m" + name + "." + test + " [" + testCaseId + "]\033[0m", true);
        DriverConfig.setLogString(
                "UTC Time : " + currentUTCTime + ", Env: " + System.getProperty("env") + ", Browser: " + System.getProperty("browser") + ", Node: " + System.getenv("NODE_NAME"),
                true);

        featureUtil.init(name + "#" + test);
    }

    /**
     * Log start.
     * @param method the method
     * @param params the params
     */
    public void logStart(Method method, Object[] params) {

        String project = null;
        String absoluteName = method.getDeclaringClass().getName();
        if (absoluteName.contains("consumer")) {
            project = "Consumer";
        } else if (absoluteName.contains("insite")) {
            project = "Insite";
        } else if (absoluteName.contains("algorithm")) {
            project = "Algorithm";
        } else if (absoluteName.contains("opsscript")) {
            project = "OPS";
        } else if (absoluteName.contains("comcast")) {
            project = "Comcast";
        } else if (absoluteName.contains("mobile")) {
            project = "New App Automation";
        } else if (absoluteName.contains("newapp")) {
            project = "New App Automation   ";
        }

        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        String name = method.getDeclaringClass().getSimpleName();
        String test = method.getName();
        String testCaseId = testLinkUtil.getTestCaseId(project, name + "#" + test);
        DriverConfig.setLogString("Started test \033[43;1m" + name + "." + test + " [" + testCaseId + "]\033[0m", true);
        DriverConfig.setLogString(
                "UTC Time : " + currentUTCTime + ", Env: " + System.getProperty("env") + ", Browser: " + System.getProperty("browser") + ", Node: " + System.getenv("NODE_NAME"),
                true);
        if (project == "Algorithm") {
            DriverConfig.setLogString("User Name: " + params[0] + ", Thermostat Id: " + params[2], true);
        } else if (project == "Consumer") {
            DriverConfig.setLogString("User Name: " + params[0] + ", Password: " + params[1], true);
        }

        featureUtil.init(name + "#" + test);
    }

    /**
     * Log end.
     * @param method the method
     * @param duration the duration
     */
    public void logEnd(Method method, long duration) {

        String project = null;
        String absoluteName = method.getDeclaringClass().getName();
        if (absoluteName.contains("consumer")) {
            project = "Consumer";
        } else if (absoluteName.contains("insite")) {
            project = "Insite";
        } else if (absoluteName.contains("algorithm")) {
            project = "Algorithm";
        } else if (absoluteName.contains("opsscript")) {
            project = "OPS";
        } else if (absoluteName.contains("api")) {
            project = "api";
        } else if (absoluteName.contains("comcast")) {
            project = "Comcast";
        } else if (absoluteName.contains("mobile")) {
            project = "New App Automation";
        }
        String name = method.getDeclaringClass().getSimpleName();
        String test = method.getName();
        String testCaseId = testLinkUtil.getTestCaseId(project, name + "#" + test);

        DriverConfig.setLogString("Completed test " + name + "." + test + " [" + testCaseId + "] in \033[46;1m[" + duration + " secs]\033[0m", true);
        DriverConfig.setLogString("-------------------------------------------------------", true);

        featureUtil.done();
    }
}
