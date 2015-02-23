/*
 * AndroidOperations.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.AndroidUtil.*;
import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.HttpHostConnectException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.SwipeableWebDriver;
import com.ecofactor.qa.automation.platform.enums.DesktopOSType;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.model.GridCapability;
import com.ecofactor.qa.automation.platform.model.GridConfiguration;
import com.ecofactor.qa.automation.platform.model.NodeConfigForGrid;
import com.ecofactor.qa.automation.platform.util.JenkinsParamUtil;
import com.ecofactor.qa.automation.platform.util.SystemUtil;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.StringUtil;

/**
 * The Class AndroidOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AndroidOperations extends AbstractMobileOperations {

    private static final String ECOFACTOR_APK = "/EcoFactor-Android/EcoFactorNew.apk";
    private static final String DEVICE_STATE = ".state";
    private static final String NATIVE_APP = "NATIVE_APP";
    private static final String WEBVIEW = "WEBVIEW";
    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidOperations.class);
    private static final String DEFAULT_PORT = "4723";

    /**
     * Creates the cmd for device info.
     * @param deviceId the device id
     * @return the list
     * @see com.ecofactor.qa.automation.platform.ops.impl.AbstractMobileOperations#createCmdForDeviceInfo(java.lang.String)
     */
    @Override
    protected List<String> createCmdForDeviceInfo(final String deviceId) {

        return arrayToList("adb", "-s", deviceId, "shell", "cat", "/system/build.prop");
    }

    /**
     * Gets the device props map.
     * @param deviceId the device id
     * @param deviceVersionMap the device version map
     * @return the device props map
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#getDevicePropsMap(java.lang.String,
     *      java.util.Map)
     */
    @Override
    protected void getDevicePropsMap(final String deviceId,
            final Map<String, String> deviceVersionMap) throws DeviceException {

        if (deviceProps.get(deviceId + DEVICE_STATE).equals("unauthorized")) {
            killExistingProcess();
            getDeviceIds();
            smallWait();
        }
        if (deviceProps.get(deviceId + DEVICE_STATE).equals("Online")) {
            deviceProps.put(deviceId + ".Id", deviceId);
            updateDeviceProperties(deviceId);
            deviceVersionMap.put(deviceProps.getProperty(deviceId + ".Version"), deviceId);
        } else {
            setErrorMsg("Mobile device with ID '" + deviceId + "' is "
                    + deviceProps.get(deviceId + DEVICE_STATE));
            hasErrors = true;
            setLogString("\033[41;1m" + getErrorMsg(), true);
            throw new DeviceException(getErrorMsg());
        }
    }

    /**
     * Update device properties.
     * @param deviceId the device id
     */
    private void updateDeviceProperties(final String deviceId) {

        final Map<String, String> propertyMap = getPropertyFromArray(getDeviceInfo(deviceId), "=");
        deviceProps.put(deviceId + ".Brand", propertyMap.get("ro.product.brand"));
        deviceProps.put(deviceId + ".Model", propertyMap.get("ro.product.model"));
        deviceProps.put(deviceId + ".Version", propertyMap.get("ro.build.version.sdk"));
        deviceProps.put(deviceId + ".OsName", getOsName(propertyMap.get("ro.build.version.sdk")));
        deviceProps.put(deviceId + ".WIFI", getWiFiConnectionState());
    }

    /**
     * Creates the device driver.
     * @return the web driver
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#createDeviceDriver()
     */
    @Override
    public WebDriver createDeviceDriver() throws DeviceException {

        return createMobileInstance(false);
    }

    /**
     * Creates the mobile instance.
     * @param deviceType the device type
     * @return the web driver
     * @throws DeviceException the device exception
     */
    private WebDriver createMobileInstance(final boolean deviceType) throws DeviceException {

        if (!hasErrors) {
            try {
                setLogString(LogSection.START, deviceType ? "Uninstalling App" : "Installing App",
                        true);
                final File app = getAppPath();
                setLogString("App Location : " + app.getPath(), true);
                final DesiredCapabilities capabilities = setDriverCapabilities(deviceType, app);
                setLogString("Launch webdriver", true);
                final WebDriver driver = new SwipeableWebDriver(new URL(getAppiumHostUrl()),
                        capabilities);
                setLogString(LogSection.END, "App Process Completed", true);
                return driver;
            } catch (Exception ex) {
                if (ex.getCause() instanceof HttpHostConnectException) {
                    setErrorMsg("\033[41;1mAppium not started, Please start.");
                    setLogString(LogSection.END, errorMsg, true);
                    hasErrors = true;
                    throw new DeviceException(getErrorMsg());
                }
                logInitializationError(ex);
            }
        }
        return null;
    }

    /**
     * Gets the app path.
     * @return the app path
     */
    private File getAppPath() {

        final File appDir = new File(System.getProperty("user.dir"));
        return new File(appDir, ECOFACTOR_APK);
    }

    /**
     * Sets the driver capabilities.
     * @param deviceType the device type
     * @param app the app
     * @return the desired capabilities
     */
    private DesiredCapabilities setDriverCapabilities(final boolean deviceType, final File app) {

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device", deviceType ? "Android" : "Selendroid");
        capabilities.setCapability("launch", deviceType ? "false" : "true");
        setHubCapabilities(capabilities);
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("app-package", "com.ecofactor.mobileapp.qa");
        capabilities.setCapability("app-activity", "com.ecofactor.mobileapp.qa.QA_Ecofactor");
        capabilities.setJavascriptEnabled(true);
        return capabilities;
    }

    /**
     * Perform device cleanup.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.DeviceOperations#performCleanup()
     */
    @Override
    public void performCleanup() throws DeviceException {

        final WebDriver driver = createMobileInstance(true);
        if (driver != null) {
            setLogString("Quit existing driver", true);
            driver.quit();
        }
    }

    /**
     * Start Appium server.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#startAppiumServer()
     */
    @Override
    public void startAppiumServer() {

        setLogString(LogSection.START, "Start appium server", true);
        final String deviceId = getDeviceIdParam();
        if (!deviceProps.get(deviceId + DEVICE_STATE).equals("Online")) {
            setLogString(
                    "Appium will not be started since the device state is = "
                            + deviceProps.get(deviceId + DEVICE_STATE) + ".", true);
            return;
        }
        final String appiumHome = System.getenv("APPIUM_HOME");
        setLogString("APPIUM_HOME : " + appiumHome, true);
        if (appiumHome == null || appiumHome.isEmpty()) {
            setErrorMsg("\033[41;1mPlease set APPIUM_HOME environment variable and try again.");
            setLogString(errorMsg, true);
            hasErrors = true;
            return;
        }
        generateNodeConfig();
        final List<String> commands = arrayToList("node", ".", "-U", deviceId, "-p", DEFAULT_PORT,
                "--full-reset");
        String listToString = StringUtil.listToString(commands, " ");
        final int indexOfK = listToString.lastIndexOf("/k");
        listToString = listToString.substring(indexOfK + 2, listToString.length());
        setLogString("Command to Start Appium Server:" + listToString, true);

        final ProcessBuilder process = new ProcessBuilder(commands);
        process.directory(new File(appiumHome));
        process.redirectOutput(new File("outPut.txt"));
        process.redirectError(new File("error.txt"));
        startProcessBuilder(process);
        mediumWait();
        setLogString(LogSection.END, "Started appium server", true);
    }

    /**
     * Get deviceIds.
     * @return the device ids
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#getDeviceIds()
     */
    @Override
    protected String[] getDeviceIds() {

        final String[] result = runCommands(arrayToList("adb", "devices"));
        if (result != null) {
            final List<String> resultList = arrayToList(result);
            final List<String> deviceIdList = new ArrayList<String>();
            for (final String lineValue : resultList) {
                if (!lineValue.contains("daemon") && !lineValue.contains("attached")) {
                    final String[] temp = lineValue.split("\t");
                    deviceIdList.add(temp[0].trim());
                    final String deviceState = temp[1].contains("device") ? "Online" : temp[1]
                            .trim();
                    deviceProps.put(temp[0].trim() + DEVICE_STATE, deviceState);
                }
            }
            final String[] deviceId = new String[deviceIdList.size()];
            deviceIdList.toArray(deviceId);
            return deviceId;
        }
        return null;
    }

    /**
     * Stop appium server.
     * @see com.ecofactor.qa.automation.mobile.ops.AppiumOperations#stopAppiumServer()
     */
    @Override
    public void stopAppiumServer() {

        setLogString(LogSection.START, "Stop APPIUM server if any appium session is running", true);
        final boolean isExists = isAppiumServerRunning("Stop");
        if (isExists) {
            setLogString("Stopped existing appium server", true);
        } else {
            setLogString("No Appium Server is running", true);
        }
        setLogString(LogSection.END, "Verification Completed", true);

    }

    /**
     * The main method.
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws DeviceException the device exception
     */
    public static void main(final String[] args) throws IOException, DeviceException {

        final AndroidOperations androidOps = new AndroidOperations();
        androidOps.cleanAndStartAppium();
    }

    /**
     * Cleanup driver for close.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#cleanupDriverForClose()
     */
    @Override
    protected void cleanupDriverForClose() {

        // No implementation.
    }

    /**
     * Gets the device brand model.
     * @param deviceId the device id
     * @return the device brand model
     */
    public String getDeviceBrandModel(final String deviceId) {

        final Map<String, String> propertyMap = getPropertyFromArray(getDeviceInfo(deviceId), "=");
        final String brandModel = propertyMap.get("ro.product.brand")
                + propertyMap.get("ro.product.model");
        return brandModel;
    }

    /**
     * Verify Appium server is running.
     */
    @Override
    protected void verifyAppiumServerIsRunning() {

        setLogString("Verify Appium server is running", true);
        final boolean isRunning = isAppiumServerRunning("Start");
        setLogString("Appium server is " + (isRunning ? "" : "not ") + "running", true);
    }

    /**
     * Display app info.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#displayAppInfo()
     */
    @Override
    public void displayAppInfo() {

        final String[] apkInfo = runCommands(arrayToList("aapt", "d", "badging", getAppPath()
                .getAbsolutePath()));
        final Map<String, String> propertyMap = getPropertyFromArray(apkInfo, ":");
        final String[] apk = propertyMap.get("package").split(" ");
        LOGGER.debug("Package" + apk[0] + "  " + "APK" + apk[1] + "  " + "APK" + apk[2]);
        final String apkVersionCode = apk[1].split("=")[1];
        final String apkVersion = apk[2].split("=")[1];
        System.setProperty("buildVersion", apkVersion);
        System.setProperty("buildCode", apkVersionCode);
    }

    /**
     * Switch to web view.
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#switchToWebView()
     */
    @Override
    public void switchToWebView() {

        getDeviceDriver().switchTo().window(WEBVIEW);
    }

    /**
     * Switch to native.
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#switchToNative()
     */
    @Override
    public void switchToNative() {

        getDeviceDriver().switchTo().window(NATIVE_APP);
    }

    /**
     * Generate node config json for registering Appium with the Selenium Hub.
     */
    private void generateNodeConfig() {

        final NodeConfigForGrid nodeConfig = new NodeConfigForGrid();
        nodeConfig.setCapabilities(createGridCapabilities());
        nodeConfig.setConfiguration(createGridConfig());
        JsonUtil.writeJsonToFile(nodeConfig, getNodeConfigPath());
    }

    /**
     * Gets the node config path.
     * @return the node config path
     */
    private String getNodeConfigPath() {

        return System.getProperty("user.dir") + "nodeConfig.json";
    }

    /**
     * Creates the Selenium grid capabilities.
     * @return the list
     */
    private List<GridCapability> createGridCapabilities() {

        final List<GridCapability> capabilities = new ArrayList<>();
        final GridCapability capability = new GridCapability();
        capability.setBrowserName("Android");
        capability.setMaxInstances(1);
        capability.setPlatform(DesktopOSType.WINDOWS);
        capability.setVersion(deviceProps.getProperty(getDeviceIdParam() + ".Version"));
        capabilities.add(capability);
        return capabilities;
    }

    /**
     * Creates the Selenium grid config.
     * @return the grid configuration
     */
    private GridConfiguration createGridConfig() {

        final GridConfiguration configuration = new GridConfiguration();
        configuration.setCleanUpCycle(2000);
        configuration.setTimeout(30000);
        configuration.setProxy("org.openqa.grid.selenium.proxy.DefaultRemoteProxy");
        configuration.setPort(Integer.parseInt(getMobilePortParam()));
        configuration.setMaxSession(1);
        configuration.setRegisterCycle(5000);
        configuration.setRegister(true);
        configuration.setUrl(getAppiumHostUrl());
        configuration.setHost(SystemUtil.getHostName());
        configuration.setHubHost(hasSeleniumHubParam() ? JenkinsParamUtil.getSeleniumHubHost()
                : "qwinjs11.ecofactor.com");
        configuration.setHubPort(hasSeleniumHubParam() ? Integer.parseInt(JenkinsParamUtil
                .getSeleniumHubPort()) : 4444);
        return configuration;
    }
}
