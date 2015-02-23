/*
 * IOSOperations.java Copyright (c) 2012, EcoFactor, All Rights Reserved. This software is the
 * confidential and proprietary information of EcoFactor ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.IOSCommandUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.conn.HttpHostConnectException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.SwipeableWebDriver;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.util.JenkinsParamUtil;
import com.ecofactor.qa.automation.platform.util.SystemUtil;

/**
 * The Class IOSOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class IOSOperations extends AbstractMobileOperations {

    private static final String DEFAULT_PORT = "4723";
    private static final String CONFIG_FILE = "Payload/QA_Ecofactor.app/Info.plist";
    private static final String ATTRIBUTE = "CFBundleVersion";
    private static final String BUNDLE_IDENTIFIER = "CFBundleIdentifier";
    private static final Logger LOGGER = LoggerFactory.getLogger(IOSOperations.class);
    private Properties iOSProps = new Properties();
    /**
     * The proxy port.
     */
    private int proxyPort = 27753;

    /**
     * The is taken.
     */
    private boolean isTaken = true;

    /**
     * Creates the cmd for device info.
     * @param deviceId the device id
     * @return the list
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#createCmdForDeviceInfo(java.lang.String)
     */
    @Override
    protected List<String> createCmdForDeviceInfo(final String deviceId) {

        return arrayToList("ideviceinfo", "-u", deviceId);
    }

    /**
     * Gets the device props map.
     * @param deviceId the device id
     * @param deviceVersionMap the device version map
     * @return the device props map
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#getDevicePropsMap(java.lang.String,
     *      java.util.Map)
     */
    @Override
    protected void getDevicePropsMap(final String deviceId,
            final Map<String, String> deviceVersionMap) {

        deviceProps.put(deviceId + ".Id", deviceId);
        final Map<String, String> propertyMap = getPropertyFromArray(getDeviceInfo(deviceId), ":");
        deviceProps.put(deviceId + ".ProductType", propertyMap.get("ProductType"));
        deviceProps.put(deviceId + ".DeviceName", propertyMap.get("DeviceName"));
        deviceProps.put(deviceId + ".Version", propertyMap.get("ProductVersion"));
        deviceProps.put(deviceId + ".proxyPort", String.valueOf(proxyPort));
        deviceProps.put(deviceId + ".ModelNumber", propertyMap.get("ModelNumber"));
        proxyPort++;
        deviceVersionMap.put(deviceProps.getProperty(deviceId + ".Version"), deviceId);
    }

    /**
     * Creates the device driver.
     * @return the web driver
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#createDeviceDriver()
     */
    @Override
    protected WebDriver createDeviceDriver() throws DeviceException {

        WebDriver driver = null;
        if (!hasErrors) {
            try {
                final File classpathRoot = new File(System.getProperty("user.dir"));
                final File app = new File(classpathRoot, "/EcoFactor_iOS/EcoFactorNew.zip");
                setLogString("Application Path: " + app.getPath(), true);
                final DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("device", "iphone");
                capabilities.setCapability(CapabilityType.BROWSER_NAME, JenkinsParamUtil.getDeviceName());
                capabilities.setCapability(CapabilityType.VERSION, "7.0");
                capabilities.setCapability(CapabilityType.PLATFORM, SystemUtil.getOSType());
                capabilities.setCapability("cleanSession", true);
                capabilities.setCapability("app", app.getAbsolutePath());
                capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
                driver = new SwipeableWebDriver(new URL(getAppiumHostUrl()),
                        capabilities);
            } catch (Exception ex) {
                if (ex.getCause() instanceof HttpHostConnectException) {
                    setErrorMsg("Appium not started");
                    setLogString(errorMsg, true);
                    hasErrors = true;
                    throw new DeviceException("\033[41;1m" + getErrorMsg());
                }
                setLogString("\033[41;1mERROR in initialze (" + ex.getMessage() + ")", true);
                throw new DeviceException("ERROR in initialze (" + ex.getMessage() + ")");
            }
        }
        return driver;
    }

    /**
     * Perform device cleanup.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#performCleanup()
     */
    @Override
    public void performCleanup() {

        final String deviceId = getDeviceIdParam();
        unInstallAppiOS(deviceId);
        smallWait();
        installAppiOS(deviceId);
        mediumWait();
    }

    /**
     * Start Appium server.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#startAppiumServer()
     */
    @Override
    public void startAppiumServer() {

        final String deviceId = getDeviceIdParam();
        startProxyServer(deviceId);
        setLogString(LogSection.START, "Start appium server", true);
        try {
            final ProcessBuilder process = new ProcessBuilder(arrayToList("node", ".", "-U",
                    deviceId, "-p", DEFAULT_PORT));
            process.directory(new File(
                    "/Applications/Appium.app/Contents/Resources/node_modules/appium/"));
            process.redirectOutput(new File("outPut.txt"));
            process.redirectError(new File("error.txt"));
            startProcessBuilder(process);
        } catch (Exception e) {
            LOGGER.error("ERROR in starting Appium Server. Cause: ", e);
        }
        setLogString(LogSection.END, "Appium server started", true);
    }

    /**
     * Get deviceIds.
     * @return the device ids
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#getDeviceIds()
     */
    @Override
    protected String[] getDeviceIds() {

        String[] deviceId = null;
        final String[] devices = runCommands(arrayToList("idevice_id", "-l"));
        if (devices != null) {
            deviceId = new String[devices.length];
            for (int i = 0; i < devices.length; i++) {
                deviceId[i] = devices[i].trim();
            }
        }
        return deviceId;
    }

    /**
     * Stop appium server.
     * @see com.ecofactor.qa.automation.mobile.ops.AppiumOperations#stopAppiumServer()
     */
    @Override
    public void stopAppiumServer() {

        setLogString(LogSection.START, "Stop Existing Appium Server", true);
        closeAllProxy();
        executeAndVerifyProcess("node", true);
        quitTerminal();
        setLogString(LogSection.END, "Verification Completed", true);
    }

    /**
     * Close all proxy.
     */
    private void closeAllProxy() {

        setLogString(LogSection.START, "Close Proxy", true);
        executeAndVerifyProcess("ios_webkit_debug_proxy", true);
        setLogString(LogSection.END, "Closing Proxy Completed", true);
    }

    /**
     * Quit terminal.
     */
    private void quitTerminal() {

        tinyWait();
        setLogString("Quit Terminal", true);
        try {
            final String[] cmd = { "osascript", "-e", "tell application \"Terminal\" to quit" };
            Runtime.getRuntime().exec(cmd);
            setLogString("Quit Terminal completed successfully.", true);
        } catch (Exception e) {
            LOGGER.error("ERROR in quitting Terminal. Cause: ", e);
        }
    }

    /**
     * The main method.
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws DeviceException the device exception
     */
    public static void main(final String[] args) throws IOException, DeviceException {

        final IOSOperations iosOps = new IOSOperations();
        iosOps.cleanAndStartAppium();
    }

    /**
     * Cleanup driver for close.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#cleanupDriverForClose()
     */
    @Override
    protected void cleanupDriverForClose() {

        for (final String winHandle : getDeviceDriver().getWindowHandles()) {
            getDeviceDriver().switchTo().window(winHandle);
        }
    }

    /**
     * Gets the device brand model.
     * @param deviceId the device id
     * @return the device brand model
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#getDeviceBrandModel(java.lang.String)
     */
    @Override
    public String getDeviceBrandModel(final String deviceId) {

        String brandModel = (String) deviceProps.get(deviceId + ".ProductType") + "_"
                + (String) deviceProps.get(deviceId + ".ModelNumber");
        brandModel = brandModel.replaceAll(",", "");
        return brandModel;
    }

    /**
     * Take screen shot.
     * @param fileNames the file names
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractDriverOperations#takeScreenShot(java.lang.String[])
     */
    @Override
    public void takeScreenShot(final String... fileNames) throws DeviceException {

        if (isTaken) {
            try {
                final JavascriptExecutor jsExecutor = (JavascriptExecutor) getDeviceDriver();
                final HashMap<String, String> execObject = new HashMap<String, String>();
                final Path screenShotFile = getTargetScreenshotPath(fileNames);
                execObject.put("file", screenShotFile.toString());
                jsExecutor.executeScript("mobile: localScreenshot", execObject);
            } catch (final Exception e) {
                isTaken = false;
                LOGGER.error("ERROR taking screenshot for " + fileNames, e);
                stopAppiumServer();
                smallWait();
                startAppiumServer();
                loadDeviceDriver();
                smallWait();
                switchToWebView();
            }
        }
    }

    /**
     * Verify Appium server is running.
     */
    @Override
    protected void verifyAppiumServerIsRunning() {

        boolean isRunning = executeAndVerifyProcess("ios_webkit_debug_proxy", false);
        if (isRunning) {
            setLogString("Proxy is Running", true);
            isRunning = executeAndVerifyProcess("node", false);
            if (isRunning) {
                setLogString("Appium is Running", true);
            }
        }
    }

    /**
     * Execute and verify process.
     * @param processName the process name
     * @param killProcess the kill process
     * @return true, if successful
     */
    private boolean executeAndVerifyProcess(final String processName, final boolean killProcess) {

        final String server = processName.contains("proxy") ? "Proxy Server" : "Appium Server";
        final String logValue = killProcess ? "and down if it is running" : "is running";
        setLogString("Verify " + server + " " + logValue, true);
        final String[] cmd = { "/bin/sh", "-c", "ps -A | grep " + processName + "" };
        try {
            final Process process = Runtime.getRuntime().exec(cmd);
            final BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            final boolean processRunning = isProcessRunning(killProcess, buffer);
            if (!processRunning) {
                setLogString("Did not find an existing " + server + " instance.", true);
            }
            return true;
        } catch (final IOException e) {
            LOGGER.error("ERROR in process " + server + ". Cause: ", e);
        }
        return false;
    }

    /**
     * Checks if is process running.
     * @param killProcess the kill process
     * @param buffer the buffer
     * @return true, if is process running
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private boolean isProcessRunning(final boolean killProcess, final BufferedReader buffer)
            throws IOException {

        boolean processRunning = false;
        String line = null;
        while ((line = buffer.readLine()) != null) {
            if (!line.contains("grep")) {
                final String[] temp = line.trim().split(" ");
                if (killProcess) {
                    killChildParentProcess(temp[0]);
                }
                processRunning = !temp[0].isEmpty();
            }
        }
        return processRunning;
    }

    /**
     * Display app info.
     * @see com.ecofactor.qa.automation.mobile.ops.impl.AbstractMobileOperations#displayAppInfo()
     */
    @Override
    public void displayAppInfo() {

        final File appDir = new File(System.getProperty("user.dir"));
        final File app = new File(appDir, "/EcoFactor_iOS/EcoFactorNew.zip");
        System.setProperty("buildVersion", getVersionFromPList(app, CONFIG_FILE, ATTRIBUTE));
        System.setProperty("buildIdentifier", getVersionFromPList(app, CONFIG_FILE, BUNDLE_IDENTIFIER));
    }

    /**
     * Switch to web view.
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#switchToWebView()
     */
    @Override
    public void switchToWebView() {

        mediumWait();
        try {
            for (final String winHandle : getDeviceDriver().getWindowHandles()) {
                getDeviceDriver().switchTo().window(winHandle);
            }
        } catch (Exception e) {
            setDeviceDriver(null);
            throw e;
        }
    }

    /**
     * Switch to native.
     * @see @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#switchToNative()
     */
    @Override
    public void switchToNative() {

        ((JavascriptExecutor) getDeviceDriver()).executeScript("mobile: leaveWebView");
    }

	/**
	 * Install un install app on device.
	 */
	public void installUnInstallAppOnDevice() {

		final String[] deviceIds = getDeviceIds();
		setLogString("No of device connected : " + deviceIds.length, true);
		for (final String deviceId : deviceIds) {
			if (deviceId != null && !deviceId.isEmpty()) {
				setLogString("Uninstall and Install App for the device : " + deviceId, true);
				unInstallAppiOS(deviceId);
				smallWait();
				installAppiOS(deviceId);
				mediumWait();
			}
		}
	}

	/**
	 * Gets the app details.
	 * @return the app details
	 */
	public Properties getAppDetails() {

		iOSProps.put("version", System.getProperty("buildVersion"));
		iOSProps.put("packageName", System.getProperty("buildIdentifier"));
		return iOSProps;
	}
}