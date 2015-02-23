/*
 * CheckJenkinsParamUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static com.ecofactor.qa.automation.platform.util.JenkinsParamUtil.*;

/**
 * The Class CheckJenkinsParamUtil. It has methods to check if params exist in Jenkins. The
 * convention is all methods start with is/has/can etc, for example hasSomeParam. All methods should
 * return a boolean.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class CheckJenkinsParamUtil {

    /**
     * Instantiates a new check jenkins param util.
     */
    private CheckJenkinsParamUtil() {

        super();
    }

    /**
     * Can db validate.
     * @return true, if successful
     */
    public static boolean canDBValidate() {

        final String dbValidate = System.getProperty("dbValidate");
        return dbValidate != null && Boolean.TRUE == Boolean.valueOf(dbValidate);
    }

    /**
     * Can admin validate.
     * @return true, if successful
     */
    public static boolean canAdminValidate() {

        final String adminValidate = System.getProperty("adminValidate");
        return adminValidate != null && Boolean.TRUE == Boolean.valueOf(adminValidate);
    }

    /**
     * Checks for jenkins mobile params.
     * @return true, if successful
     */
    public static boolean hasJenkinsMobileParams() {

        return hasJenkinsDeviceIdParam();
    }

    /**
     * Checks for jenkins device id param.
     * @return true, if successful
     */
    public static boolean hasJenkinsDeviceIdParam() {

        final String deviceId = System.getProperty("deviceId");
        return deviceId != null && !deviceId.isEmpty() && !deviceId.equalsIgnoreCase("Select");
    }

    /**
     * Checks for jenkins device name param.
     * @return true, if successful
     */
    public static boolean hasJenkinsDeviceNameParam() {

        final String deviceId = System.getProperty("deviceName");
        return deviceId != null && !deviceId.isEmpty();
    }

    /**
     * Checks for jenkins browser param.
     * @return true, if successful
     */
    public static boolean hasJenkinsBrowserParam() {

        final String browser = System.getProperty("browser");
        return browser != null && !browser.isEmpty();
    }

    /**
     * Checks for jenkins admin browser param.
     * @return true, if successful
     */
    public static boolean hasJenkinsAdminBrowserParam() {

        final String adminBrowser = System.getProperty("adminBrowser");
        return adminBrowser != null && !adminBrowser.isEmpty();
    }

    /**
     * Checks for jenkins save all screens param.
     * @return true, if successful
     */
    public static boolean hasJenkinsSaveAllScreensParam() {

        final String saveAllScreens = System.getProperty("saveAllScreens");
        return saveAllScreens != null && Boolean.TRUE == Boolean.valueOf(saveAllScreens);
    }

    /**
     * Checks for jenkins mandatory params.
     * @return true, if successful
     */
    public static boolean hasJenkinsMandatoryParams() {

        return hasJenkinsMobileParams() || hasJenkinsBrowserParam();
    }

    /**
     * Gets the jenkins device number.
     * @return the jenkins device number
     */
    public static boolean hasJenkinsDeviceNumber() {

        final String deviceNumber = System.getProperty("deviceNumber");
        return deviceNumber != null && !deviceNumber.isEmpty();
    }

    /**
     * Gets the jenkins build number.
     * @return the jenkins build number
     */
    public static boolean hasJenkinsBuildNumber() {

        final String buildNumber = System.getProperty("buildNumber");
        return buildNumber != null && !buildNumber.isEmpty();
    }

    /**
     * Gets the jenkins build id.
     * @return the jenkins build id
     */
    public static boolean hasJenkinsBuildId() {

        final String buildId = System.getProperty("buildId");
        return buildId != null && !buildId.isEmpty();
    }

    /**
     * Gets the jenkins job name.
     * @return the jenkins job name
     */
    public static boolean hasJenkinsJobName() {

        final String jobName = System.getProperty("jobName");
        return jobName != null && !jobName.isEmpty();
    }

    /**
     * Checks for jenkins selenium hub param.
     * @return true, if successful
     */
    public static boolean hasSeleniumHubParam() {

        return getSeleniumHubHost() != null && getSeleniumHubPort() != null;
    }

    /**
     * Checks for node name param.
     * @return true, if successful
     */
    public static boolean hasNodeNameParam() {

        final String node = System.getenv("NODE_NAME");
        return node != null && !node.isEmpty();
    }

    /**
     * Checks for download param.
     * @return true, if successful
     */
    public static boolean hasDownloadParam() {

        final String downloadApp = System.getProperty("downloadApp");
        return downloadApp != null && Boolean.TRUE == Boolean.valueOf(downloadApp);
    }

    /**
     * Checks for platform param.
     * @return true, if successful
     */
    public static boolean hasPlatformParam() {

        final String platformParam = System.getProperty("platform");
        return platformParam != null && !platformParam.isEmpty();
    }

    /**
     * Checks for version param.
     * @return true, if successful
     */
    public static boolean hasVersionParam() {

        final String versionParam = getVersion();
        return versionParam != null && !versionParam.isEmpty();
    }

    /**
     * Checks for device name param.
     * @return true, if successful
     */
    public static boolean hasDeviceNameParam() {

        final String deviceNameParam = getDeviceName();
        return deviceNameParam != null && !deviceNameParam.isEmpty();
    }

    /**
     * Checks for project param.
     * @return true, if successful
     */
    public static boolean hasProjectParam() {

        final String project = getProject();
        return project != null && !project.isEmpty();
    }
}
