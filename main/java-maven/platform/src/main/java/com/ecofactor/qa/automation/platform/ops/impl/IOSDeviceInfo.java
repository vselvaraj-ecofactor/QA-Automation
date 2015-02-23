/*
 * IOSDeviceInfo.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */

package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;

import java.util.Map;
import java.util.Properties;

import com.ecofactor.qa.automation.platform.ops.DeviceInfo;

/**
 * The Class IOSDeviceInfo.
 */
public class IOSDeviceInfo extends IOSOperations implements DeviceInfo {

    private Properties deviceInfoProps = new Properties();

    /**
     * Populate device details.
     * @return the properties
     * @see com.ecofactor.qa.automation.platform.ops.DeviceInfo#populateDeviceDetails()
     */
    @Override
    public Properties populateDeviceDetails() {

        String[] deviceId = null;
        final String[] devices = runCommands(arrayToList("idevice_id", "-l"));
        if (devices != null) {
            deviceId = new String[devices.length];
            for (int i = 0; i < devices.length; i++) {
                deviceId[i] = devices[i].trim();
                getdeviceInfoPropsMap(deviceId[i]);
            }
            return deviceInfoProps;
        }
        return null;
    }

    /**
     * Gets the device props map.
     * @param deviceId the device id
     * @return the device props map
     */
    protected void getdeviceInfoPropsMap(final String deviceId) {


        String[] deviceArray = getDeviceInfo(deviceId);
        if (deviceArray != null && deviceArray.length > 0) {
            final Map<String, String> propertyMap = getPropertyFromArray(deviceArray, ":");
            if (propertyMap != null && !propertyMap.isEmpty() && propertyMap.get("DeviceName")!=null) {
                deviceInfoProps.put(deviceId + "id", deviceId);
                deviceInfoProps.put(deviceId + ".model", propertyMap.get("DeviceName"));
                deviceInfoProps.put(deviceId + ".version", propertyMap.get("ProductVersion"));
                deviceInfoProps.put(deviceId + ".state", "connected");
                deviceInfoProps.put(deviceId + ".slave", System.getenv("NODE_NAME"));
            }
        }
    }
}
