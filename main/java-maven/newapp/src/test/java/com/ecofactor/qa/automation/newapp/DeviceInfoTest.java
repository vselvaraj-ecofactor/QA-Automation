/*
 * DeviceInfoTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.model.DeviceData;
import com.ecofactor.qa.automation.newapp.util.mail.MobileMailUtil;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.ops.DeviceInfo;
import com.ecofactor.qa.automation.platform.util.DeviceUtil;
import com.google.inject.Inject;

/**
 * The Class DeviceInfoTest.
 * @author $Author: rvinoopraj $
 * @version $Rev: 29212 $ $Date: 2014-03-11 13:02:34 +0530 (Tue, 11 Mar 2014) $
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
public class DeviceInfoTest {

    @Inject
    private DeviceInfo deviceInfo;
    @Inject
    private MobileUserTestDataConfig mobileUserTestDataConfig;
    @Inject
    private MobileMailUtil mailUtil;

    private static List<DeviceData> deviceDatas = new LinkedList<DeviceData>();
    private boolean sendMail;

    /**
     * After suite.
     * @throws Exception the exception
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {

        if (sendMail) {
            mailUtil.deviceDetails(deviceDatas, mobileUserTestDataConfig.getEnvironment());
        }

    }

    /**
     * After method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(final Method method) {

        if (method.getName().equalsIgnoreCase("readDeviceInfo")) {
            sendMail = true;
        }
    }

    /**
     * Creates the property file.
     * @throws DeviceException the device exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void createPropertyFile() throws DeviceException, IOException {

        final Properties deviceProps = deviceInfo.populateDeviceDetails();
        DeviceUtil.writePropertiesToFile(deviceProps, "/target/deviceinfo/",
                System.getenv("NODE_NAME") + "_" + "deviceInfo.properties", "DeviceInfo");
    }

    /**
     * Read device info.
     * @throws Exception the exception
     */
    @Test
    public void readDeviceInfo() throws Exception {

        DeviceData deviceInfo;
        final Properties properties = new Properties();
        final Properties properties1 = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("slaves.properties"));
        final String slaves = properties.getProperty("slaves");
        final String[] temp = slaves.split(",");
        for (final String slave : temp) {
            if (!slave.contains("default")) {
                Reader reader = null;
                try {
                    URL url = new URL("http://jenkins.ecofactor.com/newapp/target/deviceinfo/"
                            + slave + "_deviceInfo.properties");
                    final InputStream in = url.openStream();
                    reader = new InputStreamReader(in, "UTF-8");
                    if (in != null && url != null) {
                        properties1.load(reader);
                        boolean hasDevice = false;
                        for (final Object key : properties1.keySet()) {
                            deviceInfo = new DeviceData();
                            if (key.toString().contains("id")) {
                                hasDevice = true;
                                final String keyValue = properties1.getProperty(key.toString());
                                deviceInfo.setDeviceId(keyValue);
                                deviceInfo.setVersion(properties1
                                        .getProperty(keyValue + ".version"));
                                deviceInfo.setSlave(properties1.getProperty(keyValue + ".slave"));
                                deviceInfo.setState(properties1.getProperty(keyValue + ".state"));
                                deviceInfo.setName(properties1.getProperty(keyValue + ".model"));
                                deviceDatas.add(deviceInfo);
                            }
                        }

                        if (!hasDevice) {
                            deviceInfo = new DeviceData();
                            deviceInfo.setDeviceId("-");
                            deviceInfo.setVersion("-");
                            deviceInfo.setSlave(slave);
                            deviceInfo.setState("-");
                            deviceInfo.setName("-");
                            deviceDatas.add(deviceInfo);
                        }
                        properties1.clear();
                    }
                } catch (Exception ex) {
                    System.out.println("Error :" + ex.getMessage());
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
        }
    }
}
