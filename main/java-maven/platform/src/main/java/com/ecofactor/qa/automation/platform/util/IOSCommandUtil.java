/*
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.plist.NSDictionary;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListException;
import net.sf.plist.io.PropertyListParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ecofactor.qa.automation.platform.enums.LogSection;

/**
 * The Class IOSCommandUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class IOSCommandUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(IOSCommandUtil.class);
	private static String XCODE_APP = "/Applications/Xcode.app";
	private static String USER_HOME_FOLDER = "/Desktop/libimobiledevice-macosx-master/";
	private static String APP_PATH = "/EcoFactor_iOS/EcoFactorNew.zip";
	private static String PPID = "PPID";

	private static String PACKAGE_NAME = "com.ecofactor.mobileapp.qa";
	private static String IDEVICE_INSTALLER = "./ideviceinstaller";

	/**
	 * Open Xcode.
	 */
	public static void openXcode() {

		LogUtil.setLogString("Open Xcode", true);
		final ProcessBuilder process = new ProcessBuilder(arrayToList("open", "-a", XCODE_APP));
		startProcessBuilder(process);
		smallWait();
	}

	/**
	 * Gets the parent id.
	 * @param childPid the child pid
	 * @return the parent id
	 */
	public static String getParentId(final String childPid) {

		final String[] cmd = { "ps", "-o", "ppid", childPid };
		String parentId = null;
		try {
			final Process process = Runtime.getRuntime().exec(cmd);
			final BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			do {
				line = buffer.readLine();
				if (!line.contains(PPID)) {
					parentId = line;
					break;
				}
			} while (line != null);
		} catch (final IOException e) {
			LOGGER.error(new StringBuilder("ERROR in getting Parent ID for child Process = ").append(childPid).append(". Cause: ").toString(), e);
		}
		return parentId;
	}

	/**
	 * Gets the IPA version.
	 * @return the IPA version
	 */
	public static void getIPAVersion() {

		final String deviceId = getDeviceIdParam();
		try {
			final ProcessBuilder process = new ProcessBuilder(arrayToList(IDEVICE_INSTALLER, "-l", "-U", deviceId));
			final String userHomepath = System.getProperty("user.home");
			process.directory(new File(new StringBuilder(userHomepath).append(USER_HOME_FOLDER).toString()));
			final Process processOutput = process.start();
			final InputStreamReader reader = new InputStreamReader(processOutput.getInputStream());
			final BufferedReader bufferedReader = new BufferedReader(reader);
			final StringBuilder builder = new StringBuilder();
			String line = null;
			String appVersion = "";
			final StringBuilder appVersionString = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line).append(",\n");
				final String bundleInfo = line;
				if (bundleInfo.contains(PACKAGE_NAME)) {
					final String[] appInfo = bundleInfo.split(" - ");
					final String[] versionInfo = appInfo[1].split(" ");
					LogUtil.setLogString(appVersionString.append("APP Version = ").append(versionInfo[1]).toString(), true);
					appVersion = versionInfo[1];
					break;
				}
				appVersionString.setLength(0);
			}
			System.setProperty("buildVersion", appVersion);
		} catch (final IOException e) {
			LOGGER.error("ERROR in Getting Version: ", e);
		}
	}

	/**
	 * Gets the version from config and parse it using XML DOM
	 * @param ipaName the ipa name
	 * @param configFile the config file
	 * @param attribute the attribute
	 * @return the version from config
	 */
	public static String getVersionFromConfig(final File ipaName, final String configFile, final String attribute) {

		JarFile ipaFile = null;
		try {
			ipaFile = new JarFile(ipaName);
			final InputStream inputStream = ipaFile.getInputStream(ipaFile.getEntry(configFile));
			final byte[] xml = new byte[inputStream.available()];
			inputStream.read(xml);
			final String xmlData = new String(xml);
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
			doc.getDocumentElement().normalize();
			return doc.getDocumentElement().getAttribute(attribute);
		} catch (IOException e) {
			LOGGER.error("ERROR in Getting Version: ", e);
		} catch (ParserConfigurationException e) {
			LOGGER.error("ERROR in Getting Version: ", e);
		} catch (SAXException e) {
			LOGGER.error("ERROR in Getting Version: ", e);
		} finally {
			if (ipaFile != null) {
				try {
					ipaFile.close();
				} catch (IOException e) {
					LOGGER.error("ERROR in File.Close for ipaFile. ", e);
				}
			}
		}
		return null;
	}

	/**
	 * Gets the version from p list.
	 * @param ipaName the ipa name
	 * @param pListFile the list file
	 * @param attribute the attribute
	 * @return the version from p list
	 */
	public static String getVersionFromPList(final File ipaName, final String pListFile, final String attribute) {

		JarFile ipaFile = null;
		ZipEntry zipEntry = null;
		try {
			ipaFile = new JarFile(ipaName);
			Enumeration<JarEntry> entries = ipaFile.entries();
			while(entries.hasMoreElements()) {
			    zipEntry = entries.nextElement();
			    if(pListFile.equalsIgnoreCase(zipEntry.getName())) {
			        break;
			    }
			}
			final InputStream inputStream = ipaFile.getInputStream(zipEntry);
			final NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(inputStream);
			final NSObject version = rootDict.get(attribute);
			return version.toString();
		} catch (IOException e) {
			LOGGER.error("ERROR in Getting Version: ", e);
		} catch (PropertyListException e) {
			LOGGER.error("ERROR in Getting Version: ", e);
		} finally {
			if (ipaFile != null) {
				try {
					ipaFile.close();
				} catch (IOException e) {
					LOGGER.error("ERROR in File.Close for ipaFile. ", e);
				}
			}
		}
		return null;
	}

	/**
	 * Gets the installer command out put.
	 * @param commands the commands
	 * @return the installer command out put
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static StringBuilder getInstallerCmdOutPut(final List<String> commands) throws IOException {

		StringBuilder builder = null;
		final ProcessBuilder process = new ProcessBuilder(commands);
		final String userHomepath = System.getProperty("user.home");
		process.directory(new File(new StringBuilder(userHomepath).append(USER_HOME_FOLDER).toString()));
		final Process processOutput = process.start();
		final InputStreamReader reader = new InputStreamReader(processOutput.getInputStream());
		final BufferedReader bufferedReader = new BufferedReader(reader);
		builder = new StringBuilder();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line).append(",\n");
			LogUtil.setLogString(line, true);
		}
		return builder;
	}

	/**
	 * Start proxy server.
	 * @param deviceId the device id
	 * @see com.ecofactor.qa.automation.mobile.ops.impl.IOSOperations#startProxyServer()
	 */
	public static void startProxyServer(final String deviceId) {

		setLogString(LogSection.START, "Start proxy server", true);
		final String port = getValueFromPropertyFile(new StringBuilder(deviceId)
				.append(".proxyPort").toString());
		try {
			setLogString(new StringBuilder("Command to start Proxy Server : ")
							.append("osascript  -e tell application \"Terminal\" to do script \"ios_webkit_debug_proxy -c ")
							.append(deviceId).append(":").append(port).append(" -d\"").toString(), true);
			final String[] cmd = {"osascript", "-e",new StringBuilder("tell application \"Terminal\" to do script \"ios_webkit_debug_proxy -c ")
							.append(deviceId).append(":").append(port).append(" -d\"").toString() };
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			LOGGER.error("ERROR in starting Proxy Server. Cause: ", e);
		}
		setLogString(LogSection.END, "Proxy server started", true);
	}

	/**
	 * Install app ios.
	 * @param deviceId the device id
	 */
	public static void installAppiOS(final String deviceId) {

		setLogString(LogSection.START, "Install APP", true);
		final File filepath = new File("");
		final String appPath = new StringBuilder(filepath.getAbsolutePath()).append(APP_PATH).toString();
		setLogString(new StringBuilder("App Location to install :").append(appPath).toString(), true);
		executeInstallerCmd(arrayToList(IDEVICE_INSTALLER, "-i", appPath, "-U", deviceId), "Install");
		setLogString(LogSection.END, "Installation done", true);

	}

	/**
	 * Un install app ios.
	 * @param deviceId the device id
	 */
	public static void unInstallAppiOS(final String deviceId) {

		setLogString(LogSection.START, "UnInstall APP", true);
		smallWait();
		executeInstallerCmd(arrayToList(IDEVICE_INSTALLER, "-u", PACKAGE_NAME, "-U", deviceId), "Uninstall");
		setLogString(LogSection.END, "Uninstall done", true);
	}

	/**
	 * Execute installer cmd.
	 * @param commands the commands
	 * @param operation the operation
	 */
	public static void executeInstallerCmd(final List<String> commands, final String operation) {

		setLogString(new StringBuilder(operation).append(" app initiated.").toString(), true);
		try {
			verifyAppSetUp(operation, getInstallerCmdOutPut(commands));
			setLogString(new StringBuilder(operation).append(" app completed.").toString(), true);
		} catch (IOException e) {
			LOGGER.error(new StringBuilder("ERROR in ").append(operation).append(" app. Cause: ").toString(), e);
		}
	}

	/**
	 * Kill child parent process.
	 * @param childId the child id
	 */
	public static void killChildParentProcess(final String childId) {

		killProcess(childId);
		smallWait();
	}

	/**
	 * Kiil process.
	 * @param pid the pid
	 */
	public static void killProcess(final String pid) {

		final ProcessBuilder process = new ProcessBuilder(arrayToList("kill", "-9", pid));
		startProcessBuilder(process);
		smallWait();

	}

	/**
	 * Verify app set up.
	 * @param operation the operation
	 * @param builder the builder
	 */
	public static void verifyAppSetUp(final String operation, final StringBuilder builder) {

		if ("Install".equalsIgnoreCase(operation)) {
			final boolean isInstalled = verifyInstallationCompleted(builder);
			if (!isInstalled) {
				setLogString("\033[41;1m App Installation Failed", true);
				Assert.fail("\033[41;1mApp Installation Failed");
			}
		} else if ("UnInstall".equalsIgnoreCase(operation)) {
			final boolean isInstalled = verifyUnInstallationCompleted(builder);
			if (!isInstalled) {
				setLogString("\033[41;1m App Un-Installation Failed", true);
				Assert.fail("\033[41;1mApp Un-Installation Failed");
			}
		}
	}

	/**
	 * Verify installation completed.
	 * @param builder the builder
	 * @return true, if successful
	 */
	public static boolean verifyInstallationCompleted(final StringBuilder builder) {

		return builder.toString().contains("Install - Complete");
	}

	/**
	 * Verify un installation completed.
	 * @param builder the builder
	 * @return true, if successful
	 */
	public static boolean verifyUnInstallationCompleted(final StringBuilder builder) {

		return builder.toString().contains("Uninstall - Complete");
	}
}
