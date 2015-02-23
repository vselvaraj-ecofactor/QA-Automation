/*
 * AbstractDriverOperations.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.JenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.annotation.BindOSOperation;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.ops.DriverOperations;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.util.SystemUtil;

/**
 * The Class AbstractDriverOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class AbstractDriverOperations implements DriverOperations, TestOperations {

    private static final String AT_STRING = " at ";

    /**
     * The device driver.
     */
    protected static ThreadLocal<WebDriver> deviceDriver = new ThreadLocal<WebDriver>();

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDriverOperations.class);

    /**
     * Load device driver.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#loadDeviceDriver()
     */
    @Override
    public void loadDeviceDriver() throws DeviceException {

        mediumWait();
        setLogString(LogSection.START, "Load WebDriver", true);
        final WebDriver driver = createDeviceDriver();
        setDeviceDriver(driver);
        setLogString(LogSection.END, "WebDriver loaded", true);
    }

    /**
     * Creates the device driver.
     * @return the web driver
     * @throws DeviceException the device exception
     */
    protected abstract WebDriver createDeviceDriver() throws DeviceException;

    /**
     * Close device driver.
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#closeDeviceDriver()
     */
    @Override
    public void closeDeviceDriver() {

        if (getDeviceDriver() != null) {
            cleanupDriverForClose();
            setLogString("Quit webdriver", true);
            getDeviceDriver().manage().deleteAllCookies();
            getDeviceDriver().quit();
        }
    }

    /**
     * Gets the mobile driver.
     * @return the mobile driver
     */
    public WebDriver getDeviceDriver() {

        synchronized (deviceDriver) {
            return deviceDriver.get();
        }
    }

    /**
     * Sets the mobile driver.
     * @param driver the new mobile driver
     */
    protected void setDeviceDriver(final WebDriver driver) {

        synchronized (deviceDriver) {
            deviceDriver.set(driver);
        }
    }

    /**
     * Cleanup driver for close.
     */
    protected abstract void cleanupDriverForClose();

    /**
     * Close.
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#close()
     */
    @Override
    public void close() {

        closeDeviceDriver();
    }

    /**
     * Initialize.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#initialize()
     */
    @Override
    public void initialize() throws DeviceException {

        checkForMandatoryParams();
        cleanUpScreenShots();
        setUpListenersForDevice();
    }

    /**
     * Cleanup.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#cleanup()
     */
    @Override
    public void cleanup() throws DeviceException {

        performCleanup();
    }

    /**
     * Sets the up listeners for os.
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#setUpListenersForDevice()
     */
    @Override
    public void setUpListenersForDevice() {

        try {
            injectOpsForListener("com.ecofactor.qa.automation.platform.listener.AutomationListener");
            injectOpsForListener("com.ecofactor.qa.automation.platform.listener.AutomationTransformer");
        } catch (SecurityException | ClassNotFoundException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Could not inject operations into Listener." + e.getMessage());
        }
    }

    /**
     * Inject ops for listener.
     * @param listenerName the listener name
     * @throws ClassNotFoundException the class not found exception
     * @throws IllegalAccessException the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    private void injectOpsForListener(final String listenerName) throws ClassNotFoundException,
            IllegalAccessException, InvocationTargetException {

        for (final Method method : this.getClass().getClassLoader().loadClass(listenerName)
                .getMethods()) {
            if (method.isAnnotationPresent(BindOSOperation.class)) {
                method.invoke(null, this);
            }
        }
    }

    /**
     * Take screen shot.
     * @param fileNames the file names
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.DriverOperations#takeScreenShot(java.lang.String[])
     */
    @Override
    public void takeScreenShot(final String... fileNames) throws DeviceException {

        try {
            final Path screenshot = ((TakesScreenshot) getDeviceDriver()).getScreenshotAs(
                    OutputType.FILE).toPath();
            final Path screenShotFile = getTargetScreenshotPath(fileNames);
            Files.copy(screenshot, screenShotFile, StandardCopyOption.REPLACE_EXISTING);
            if (Files.exists(screenShotFile)) {
                LOGGER.info("Saved screenshot for " + fileNames + AT_STRING + screenShotFile);
            } else {
                LOGGER.info("Unable to save screenshot for " + fileNames + AT_STRING
                        + screenShotFile);
            }
        } catch (WebDriverException | IOException e) {
            LOGGER.error("Error taking screenshot for " + fileNames, e);
        }
    }

    /**
     * Take custom screen shot.
     * @param fileNames the file names
     */
    @Override
    public void takeCustomScreenShot(final String... fileNames) {

        try {
            smallWait();
            final String dir = System.getProperty("user.dir");
            final Path screenshot = ((TakesScreenshot) getDeviceDriver()).getScreenshotAs(
                    OutputType.FILE).toPath();
            smallWait();
            final String folders = arrayToStringDelimited(fileNames, "/");
            final Path screenShotDir = Paths.get(dir, "target", folders.indexOf('/') == -1 ? ""
                    : folders.substring(0, folders.lastIndexOf('/')));

            Files.createDirectories(screenShotDir);
            LOGGER.debug("screenShotDir: " + screenShotDir.toString() + "; folders: " + folders);
            final Path screenShotFile = Paths.get(dir, "target", folders + ".png");

            Files.copy(screenshot, screenShotFile, StandardCopyOption.REPLACE_EXISTING);
            if (Files.exists(screenShotFile)) {
                LOGGER.info("Saved custom screenshot for " + fileNames + AT_STRING + screenShotFile);
            } else {
                LOGGER.info("Unable to save custom screenshot for " + fileNames + AT_STRING
                        + screenShotFile);
            }
        } catch (WebDriverException | IOException e) {
            LOGGER.error("Error taking custom screenshot for " + fileNames, e);
        }
    }

    /**
     * Gets the target screenshot path.
     * @param fileNames the file names
     * @return the target screenshot path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected Path getTargetScreenshotPath(final String... fileNames) throws IOException {

        final String dir = System.getProperty("user.dir");
        final String folders = arrayToStringDelimited(fileNames, "/");
        final Path baseDir = Paths.get(dir, fileNames != null && fileNames.length > 1 ? ""
                : "target/screenshots/");
        LOGGER.debug("Screenshot Base directory is : " + baseDir, true);
        final Path screenShotDir = Paths.get(baseDir.toString(),
                folders.indexOf('/') == -1 ? "" : folders.substring(0, folders.lastIndexOf('/')));
        LOGGER.debug("Actual Screenshot directory is : " + screenShotDir, true);
        Files.createDirectories(screenShotDir);
        if (SystemUtil.isMac()) {
            final String command = "chmod -R 777 " + baseDir;
            Runtime.getRuntime().exec(command);
        }
        return Paths.get(baseDir.toString(), folders + ".png");
    }

    /**
     * Check for mandatory params.
     * @throws DeviceException the device exception
     */
    private void checkForMandatoryParams() throws DeviceException {

        setLogString("Check for mandatory parameters (env, deviceId or browser)", true);
        if (hasJenkinsMandatoryParams()) {
            setLogString("Mandatory parameters are avaialble", true);
        } else {
            setErrorMsg("Mandatory parameter is not passed.You should pass either Device Id or Browser Name");
            hasErrors = true;
            setLogString(getErrorMsg(), true);
            throw new DeviceException(getErrorMsg());
        }
    }

    /**
     * Clean up screen shots.
     */
    private void cleanUpScreenShots() {

        try {
            final File srcFile = new File("target/screenshots");
            if (srcFile.exists()) {
                FileUtils.cleanDirectory(srcFile);
            }
        } catch (final IOException e) {
            LOGGER.error("Error in deleting screenshots");
        }
    }

	/**
	 * Sets the hub capabilities.
	 * @param capabilities the new hub capabilities
	 */
	protected void setHubCapabilities(final DesiredCapabilities capabilities) {

		capabilities.setCapability(CapabilityType.BROWSER_NAME, getDeviceName());
		if (hasPlatformParam()) {
			capabilities.setCapability(CapabilityType.PLATFORM, getPlatformName());
		}
		if (hasVersionParam()) {
			capabilities.setCapability(CapabilityType.VERSION, getVersion());
		}
	}
}
