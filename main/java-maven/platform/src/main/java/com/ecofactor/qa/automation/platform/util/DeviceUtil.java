/*
 * DeviceUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static org.testng.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;

/**
 * The Class DeviceUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DeviceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceUtil.class);
    private static final String DEFAULT_PORT = "4723";
    private static final String ZIP_ARCHIVE_PATH = new StringBuilder(System.getProperty("user.home")).append( "/zipExtract/").toString();

    private static final String DEVICE_DETAILS_PROPERTIES="deviceDetails.properties";
    private static final String RESOURCES="src/main/resources/";
    private static final String OS_NAME_PROPERTIES=new StringBuilder(RESOURCES).append("version.properties").toString();
    private static final String IOS_APP_DETAILS="appDetails.properties";

    private static final String DEVICES="Devices";
    private static final String BACKUP_DEVICES="Backup Devices";


    private static final String DOWNLOAD_URL="http://jenkinsosx.ecofactor.com:8080/job/Ecofactor-Mobile-NVE/default/";
    private static final String DOWNLOAD_PATH="/zipExtract/archive.zip";
    private static final String ARCHIVE_ZIP="archive.zip";


    private static final String FOLDER_PATH="/zipExtract/archive/mobileapp/build/artifacts";
    private static final String IOS_ZIP_PATH="../newapp/EcoFactor_iOS/EcoFactorNew.zip";
    private static final String ANDROID_APK_PATH="../newapp/EcoFactor-Android/EcoFactorNew.apk";
	private static final int ONE = 1;


    /**
     * Runs the list commands through process builder and retrieves the result using input stream
     * @param commands the commands
     * @return the string[]
     */
    public static String[] runCommands(final List<String> commands) {

        String[] cmdOutput = null;
        String line = null;
        try {
            final ProcessBuilder process = new ProcessBuilder(commands);
            final Process processOutput = process.start();
            final InputStreamReader reader = new InputStreamReader(processOutput.getInputStream());
            final BufferedReader bufferedReader = new BufferedReader(reader);
            final StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            cmdOutput = builder.toString().split("\n");
        } catch (Exception e) {
            LOGGER.info(new StringBuilder("Exception in running ProcessBuilder Commands, Error : ").append( e.getMessage()).toString());
        }
        return cmdOutput;
    }

    /**
     * Gets the value from property file.
     * @param keyvalue the keyvalue
     * @return the value from property file
     */
    public static String getValueFromPropertyFile(final String keyvalue) {

        return readPropertyFile(DEVICE_DETAILS_PROPERTIES, keyvalue);
    }

    /**
     * Gets the os name.
     * @param keyvalue the keyvalue
     * @return the os name
     */
    public static String getOsName(final String keyvalue) {

        return readPropertyFile(OS_NAME_PROPERTIES, keyvalue);
    }

    /**
     * Read property file.
     * @param propertyFile the property file
     * @param keyValue the key value
     * @return the string
     */
    private static String readPropertyFile(final String propertyFile, final String keyValue) {

        String value = null;
        try {
            File file = new File(propertyFile);
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            Enumeration<Object> enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                if (keyValue.equalsIgnoreCase(key)) {
                    value = properties.getProperty(key);
                }
            }
        } catch (Exception ex) {
            LOGGER.info(new StringBuilder("Exception in reading propert file, Error : ").append( ex.getMessage()).toString());
        }
        return value;
    }

    /**
     * Gets the device id using adb commands
     * @return the device id
     */
    public static String[] getDeviceId() {

        List<String> commands = new ArrayList<String>();
        String[] deviceId;
        commands.add("adb");
        commands.add("devices");
        String[] devices = runCommands(commands);

        deviceId = new String[devices.length - 1];
        for (int i = 1; i < devices.length; i++) {
            deviceId[i - 1] = devices[i].split("\t")[0].trim();
        }
        return deviceId;
    }

    /**
     * Delete device details from the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void deleteDeviceDetails() throws IOException {

        File file = new File(DEVICE_DETAILS_PROPERTIES);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Gets the key for value from the propertis file
     * @param keyValue the key value
     * @return the key for value
     */
    public static String getkeyForValue(String keyValue) {

        String value = null;
        try {
            File file = new File(DEVICE_DETAILS_PROPERTIES);
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
            Enumeration<Object> enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String portValue = properties.getProperty(key);
                if (portValue.equalsIgnoreCase(keyValue)) {
                    value = key;
                    break;
                }

            }
        } catch (Exception e) {
            LOGGER.info(new StringBuilder("Exception in reading key for mobile device, Error : ").append( e.getMessage()).toString());
        }
        return value;
    }

    /**
     * Write properties to file.
     * @param properties the properties
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertiesToFile(final Properties properties) throws IOException {

        writePropertiesToFile(properties, DEVICE_DETAILS_PROPERTIES, DEVICES);
    }

    /**
     * Write properties to file.
     * @param properties the properties
     * @param fileName the file name
     * @param comment the comment
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertiesToFile(final Properties properties, final String fileName,
            final String comment) throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            DriverConfig.setLogString("Create New File", true);
            file.createNewFile();
        }
        assertTrue(file.exists());
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, comment);
        fileOut.close();

    }

    /**
     * Convert Array to list.
     * @param array the array
     * @return the list
     */
    public static List<String> arrayToList(final String... array) {

        return array != null ? Arrays.asList(array) : null;
    }

    /**
     * Array to string delimited. This method will convert a String array into a String delimited by
     * <strong>delimiter</strong>. </br>If the String array is NULL or EMPTY, the method returns an
     * empty string.
     * @param array the array
     * @param delimiter the delimiter
     * @return the string
     */
    public static String arrayToStringDelimited(final String[] array, final String delimiter) {

        final StringBuilder buffer = new StringBuilder();
        final int arrayLength = array != null ? array.length : 0;
        for (int i = 0; i < arrayLength - 1; i++) {
            buffer.append(array[i]).append(delimiter);
        }
        buffer.append(arrayLength > 0 ? array[arrayLength - 1] : "");
        return buffer.toString();
    }

    /**
     * Gets the property from array and parse it based on the given delimiter
     * @param array the array
     * @param delimiter the delimiter
     * @return the properties from array
     */
    public static Map<String, String> getPropertyFromArray(final String[] array,
            final String delimiter) {

        final Map<String, String> result = new HashMap<>();
        if (array != null && array.length > 0 && delimiter != null) {

            for (final String item : array) {
                String deviceprop = item.split("\t")[0].trim();
                if (deviceprop.contains(delimiter)) {
                    final String[] values = deviceprop.split(delimiter);
                    if (values.length > ONE) {
                        result.put(values[0], values[1].trim());
                    }
                }
            }
        }
        return result;
    }

    /**
     * Gets the key for value from the properties file
     * @param keyValue the key value
     * @param properties the properties
     * @return the key for value
     */
    public static String getkeyForValue(String keyValue, Properties properties) {

        String value = null;

        Enumeration<Object> enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String portValue = properties.getProperty(key);
            if (portValue.equalsIgnoreCase(keyValue)) {
                value = key;
                break;
            }
        }

        return value;
    }

    /**
     * Write properties to resource folder.
     * @param properties the properties
     * @param fileName the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertiesToResourceFolder(final Properties properties, String fileName)
            throws IOException {

        writePropertiesToFile(properties, new StringBuilder(RESOURCES).append(fileName).toString(), BACKUP_DEVICES);
    }

    /**
     * Gets the system os.
     * @return the system os
     */
    public static String getSystemOs() {

        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * Checks if the OS is windows.
     * @return true, if is windows
     */
    public static boolean isWindows() {

        String OS = getSystemOs();
        return (OS.indexOf("win") >= 0);
    }

    /**
     * Checks if the OS is mac.
     * @return true, if is mac
     */
    public static boolean isMac() {

        String OS = getSystemOs();
        return (OS.indexOf("mac") >= 0);
    }

    /**
     * Checks if the OS is unix.
     * @return true, if is unix
     */
    public static boolean isUnix() {

        String OS = getSystemOs();
        return (OS.indexOf("nux") >= 0);
    }

    /**
     * Gets the device id param.
     * @return the device id param
     */
    public static String getDeviceIdParam() {

        String deviceId = System.getProperty("deviceId");
        if (deviceId == null || deviceId.isEmpty()) {
            deviceId = getkeyForValue(getMobilePortParam());
        }
        return deviceId;
    }

    /**
     * Gets the mobile port param.
     * @return the mobile port param
     */
    public static String getMobilePortParam() {

        String port = System.getProperty("mobilePort");
        if (port == null || port.isEmpty()) {
            port = DEFAULT_PORT;
        }
        return port;
    }

    /**
     * Start process builder.
     * @param processBuilder the process builder
     */
    public static void startProcessBuilder(ProcessBuilder processBuilder) {

        try {
            processBuilder.start();
        } catch (Exception ex) {
            LOGGER.info(new StringBuilder("Error in starting ProcessBuilder. Error Msg ").append(ex.getMessage()).toString());
        }
    }

    /**
     * Start process builder.
     * @param commands the commands
     */
    public static void startProcessBuilder(final List<String> commands) {

        try {
            final ProcessBuilder process = new ProcessBuilder(commands);
            process.start();
        } catch (Exception ex) {
            LOGGER.info(new StringBuilder("Error in starting ProcessBuilder. Error Msg ").append(ex.getMessage()).toString());
        }
    }

    /**
     * Write properties to file.
     * @param deviceProps the device props
     * @param fileLocation the file location
     * @param fileName the file name
     * @param comment the comment
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertiesToFile(Properties deviceProps, String fileLocation,
            String fileName, String comment) throws IOException {

        final String dir = System.getProperty("user.dir");
        StringBuilder filePath=new StringBuilder(dir).append(fileLocation).append(fileName);

        File file = new File(filePath.toString());
        if (!file.exists()) {
            file.getParentFile().mkdir();
        }
        file.createNewFile();
        assertTrue(file.exists());
        FileOutputStream fileOut = new FileOutputStream(file);
        deviceProps.store(fileOut, comment);
        fileOut.close();
    }

    /**
     * Download the latest app from Jenkins and add to Project.
     * @return the lastest app into folder
     */
    public static void getLastestAppIntoFolder() {



        FileUtil.createDir(ZIP_ARCHIVE_PATH);
        FileUtil.deleteAllFilesInFolder(ZIP_ARCHIVE_PATH);
		FileUtil.downloadFileFromURL(
				DOWNLOAD_URL + JenkinsParamUtil.getAppReleaseId() + "/artifact/*zip*/archive.zip", new StringBuilder(System.getProperty("user.home")).append(DOWNLOAD_PATH).toString());
        WaitUtil.mediumWait();
        FileUtil.unZipFile(new StringBuilder(ZIP_ARCHIVE_PATH).append(ARCHIVE_ZIP).toString(), ZIP_ARCHIVE_PATH);
        WaitUtil.mediumWait();
        copyFilesToProjectDestination();
        WaitUtil.mediumWait();
    }

    /**
     * Copy and paste the IOS Zip and APK to project Folder.
     */
    private static void copyFilesToProjectDestination() {


        final String folderPath = new StringBuilder(System.getProperty("user.home")).append(FOLDER_PATH).toString();
        final File extractedPath = new File(folderPath);
        final String[] fileList = extractedPath.list();

        final StringBuilder folderPathString=new StringBuilder();
        final StringBuilder iosPath=new StringBuilder();
        final StringBuilder apkPath=new StringBuilder();

        for (final String fileName : fileList) {
            if (fileName.contains(".ipa")) {

                final File srcFile = new File(folderPathString.append(folderPath).append("/").append(fileName).toString());
                final File destFile = new File(IOS_ZIP_PATH);
                LogUtil.setLogString(iosPath.append("IOS Zip Path:").append( destFile.getAbsolutePath()).toString(), true);
                try {
                    FileUtil.copyFileUsingFileStreams(srcFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (fileName.contains(".apk")) {
                final File srcFile = new File(folderPathString.append(folderPath).append("/").append(fileName).toString());
                final File destFile = new File(ANDROID_APK_PATH);
                LogUtil.setLogString(apkPath.append("APK Path:").append( destFile.getAbsolutePath()).toString(), true);
                try {
                    FileUtil.copyFileUsingFileStreams(srcFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            folderPathString.setLength(0);
            iosPath.setLength(0);
            apkPath.setLength(0);
        }
    }

    /**
     * Gets the app version.
     * @param keyvalue the keyvalue
     * @return the app version
     */
    public static String getAppVersion(final String keyvalue) {

        return readPropertyFile(IOS_APP_DETAILS, keyvalue);
    }
}
