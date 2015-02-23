/*
 * SeleniumDriverUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.JenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.SystemUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.http.conn.HttpHostConnectException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.SwipeableWebDriver;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;

/**
 * The Class SeleniumDriverUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class SeleniumDriverUtil {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumDriverUtil.class);
	private static final String ECOFACTOR_APK = "/EcoFactor-Android/EcoFactorNew.apk";
	private static final String ECOFACTOR_IPA = "/EcoFactor_iOS/EcoFactorNew.zip";
	private static final String HUB_HOST = "qaselhub.ecofactor.com";
	private static final String HUB_PORT = "4444";
	private static final String FIREFOX = "firefox";
	private static final String IE_BROWSER = "ie";
	private static final String IE_BROWSER8 = "ie8";
	private static final String IE_BROWSER9 = "ie9";
	private static final String I_EXPLORER = "iexplorer";
	private static final String VISTAIE8 = "vistaie8";
	private static final String IE_BROWSER10 = "ie10";
	private static final String CHROME = "chrome";
	private static final String SAFARI = "safari";

	/**
	 * Instantiates a new system util.
	 */
	private SeleniumDriverUtil() {

		super();
	}

	/**
	 * Creates the android driver.
	 * @param deviceType the device type
	 * @return the web driver
	 * @throws DeviceException the device exception
	 */
	public static WebDriver createAndroidDriver(final boolean deviceType) throws DeviceException {

		if (!hasErrors) {
			try {
				setLogString(LogSection.START, deviceType ? "Uninstalling App" : "Installing App", true);
				final File app = getAppPath();
				setLogString("App Location : " + app.getPath(), true);
				final DesiredCapabilities capabilities = setDriverCapabilities(deviceType, app);
                capabilities.setCapability(CapabilityType.BROWSER_NAME, getDeviceName() );
				setLogString("Launch webdriver", true);
				final WebDriver driver = new SwipeableWebDriver(new URL(createHostUrl()), capabilities);
				setLogString(LogSection.END, "App Process Completed", true);
				return driver;
			} catch (final Exception ex) {
				checkIfAppiumError(ex);
			}
		}
		return null;
	}

	/**
	 * Check if appium error. Display if any appium error and throws DeviceException.
	 * @param exception the ex
	 * @throws DeviceException the device exception
	 */
	private static void checkIfAppiumError(final Exception exception) throws DeviceException {

		setErrorMsg("\033[41;1mError in creating driver. " + exception.getMessage());
		if (exception.getCause() instanceof HttpHostConnectException) {
			setErrorMsg("\033[41;1mAppium not started, Please start.");
		}
		setLogString(LogSection.END, errorMsg, true);
		hasErrors = true;
		throw new DeviceException(getErrorMsg());
	}

	/**
	 * Creates the ios driver.
	 * @return the web driver
	 * @throws DeviceException the device exception
	 */
	public static WebDriver createIOSDriver() throws DeviceException {

		WebDriver driver = null;
		if (!hasErrors) {
			try {
				final File classpathRoot = new File(System.getProperty("user.dir"));
				final File app = new File(classpathRoot, ECOFACTOR_IPA);
				setLogString("Application Path: " + app.getPath(), true);
				final DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("device", "iphone");
				setHubCapabilities(capabilities);
				capabilities.setCapability(CapabilityType.BROWSER_NAME, getDeviceName());
				capabilities.setCapability("cleanSession", true);
				capabilities.setCapability("app", app.getAbsolutePath());
				capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				driver = new SwipeableWebDriver(new URL(createHostUrl()), capabilities);
			} catch (final Exception ex) {
				checkIfAppiumError(ex);
			}
		}
		return driver;
	}

	/**
	 * Creates the browser driver.
	 * @param browserName the browser name
	 * @return the web driver
	 */
	public static WebDriver createBrowserDriver(final String browserName) {

		WebDriver driver = null;
		final String browser = browserName.toLowerCase(Locale.ENGLISH);
		driver = createIfFirefoxDriver(browser, driver);
		driver = createIfIEDriver(browser, driver);
		driver = createIfChromeDriver(browser, driver);
		driver = createIfSafariDriver(browser, driver);
		return driver;
	}

	/**
	 * Creates the ie driver.
	 * @param browserName the browser name
	 * @param driver the driver
	 * @return the web driver
	 */
	private static WebDriver createIfIEDriver(final String browserName, final WebDriver driver) {

		if (browserName.equals(IE_BROWSER) || browserName.equals(IE_BROWSER8)
				|| browserName.equals(IE_BROWSER9)	|| browserName.equals(IE_BROWSER10)
				|| browserName.equals(VISTAIE8) || browserName.contains(IE_BROWSER)) {

			final DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(CapabilityType.BROWSER_NAME, I_EXPLORER);
			return createRemoteDriverForHub(capability);
		}
		return driver;
	}

	/**
	 * Creates the firefox driver.
	 * @param browserName the browser name
	 * @param driver the driver
	 * @return the web driver
	 */
	private static WebDriver createIfFirefoxDriver(final String browserName, final WebDriver driver) {

		if (browserName.equals(FIREFOX) || browserName.contains(FIREFOX)) {

			final DesiredCapabilities capability = DesiredCapabilities.firefox();
			return createRemoteDriverForHub(capability);
		}
		return driver;
	}

	/**
	 * Creates the remote driver for Selenium hub.
	 * @param capability the capability
	 * @return the remote web driver
	 */
	private static RemoteWebDriver createRemoteDriverForHub(final DesiredCapabilities capability) {

		try {
			setHubCapabilities(capability);
			return new SwipeableWebDriver(new URL(createHostUrl()), capability);
		} catch (final MalformedURLException e) {
			setLogString("Cannot reach Selenium Hub. Reason = " + e.getMessage(), true);
		}
		return null;
	}

	/**
	 * Creates the chrome driver.
	 * @param browserName the browser name
	 * @param driver the driver
	 * @return the web driver
	 */
	private static WebDriver createIfChromeDriver(final String browserName, final WebDriver driver) {

		if (browserName.equals(CHROME) || browserName.contains(CHROME)) {
			final DesiredCapabilities capability = DesiredCapabilities.chrome();
			return createRemoteDriverForHub(capability);
		}
		return driver;
	}

	/**
	 * Creates the if safari driver.
	 * @param browserName the browser name
	 * @param driver the driver
	 * @return the web driver
	 */
	private static WebDriver createIfSafariDriver(final String browserName, final WebDriver driver) {

		if (browserName.equals(SAFARI) || browserName.contains(SAFARI)) {

			final DesiredCapabilities capability = DesiredCapabilities.safari();
			return createRemoteDriverForHub(capability);
		}
		return driver;
	}

	/**
	 * Sets the driver capabilities.
	 * @param deviceType the device type
	 * @param app the app
	 * @return the desired capabilities
	 */
	private static DesiredCapabilities setDriverCapabilities(final boolean deviceType, final File app) {

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
	 * Sets the hub capabilities.
	 * @param capabilities the new hub capabilities
	 */
	private static void setHubCapabilities(final DesiredCapabilities capabilities) {


		if (hasPlatformParam()) {
			capabilities.setCapability(CapabilityType.PLATFORM, getPlatformName());
		}
		if (hasVersionParam()) {
			capabilities.setCapability(CapabilityType.VERSION, getVersion());
		}
	}

	/**
	 * Creates the host url.
	 * @return the string
	 */
	private static String createHostUrl() {

		// TODO: Hard coded SeleniumHubHost and SeleniumHubPort.Remove the hard coded values in future.
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://")
				.append(hasNodeNameParam() ? HUB_HOST : getHostName())
				.append(':')
				.append(HUB_PORT)
				.append("/wd/hub/");
		final String url = urlBuilder.toString();
		setLogString("Host Url : " + url, true);
		return url;
	}

	/**
	 * Gets the app path.
	 * @return the app path
	 */
	private static File getAppPath() {

		final File appDir = new File(System.getProperty("user.dir"));
		return new File(appDir, ECOFACTOR_APK);
	}
}
