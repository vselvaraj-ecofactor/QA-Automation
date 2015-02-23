/*
 * AndroidDeviceInfo.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.DeviceUtil.arrayToList;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.getPropertyFromArray;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.runCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ecofactor.qa.automation.platform.ops.DeviceInfo;

/**
 * The Class AndroidDeviceInfo.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AndroidDeviceInfo extends AndroidOperations implements DeviceInfo {

    private Properties deviceInfoProps = new Properties();

	/**
	 * Populate device details.
	 * @return the properties
	 * @see com.ecofactor.qa.automation.platform.ops.DeviceInfo#populateDeviceDetails()
	 */
	@Override
	public Properties populateDeviceDetails() {
		final String[] result = runCommands(arrayToList("adb", "devices"));
		if (result != null) {
			final List<String> resultList = arrayToList(result);
			final List<String> deviceIdList = new ArrayList<String>();
			for (final String lineValue : resultList) {
				if (!lineValue.contains("daemon") && !lineValue.contains("attached")) {
					final String[] temp = lineValue.split("\t");
					deviceIdList.add(temp[0].trim());
					final String deviceState = temp[1].contains("device") ? "Online" : temp[1].trim();
					deviceInfoProps.put(temp[0].trim() + ".state", deviceState);
					getdeviceInfoPropsMap(temp[0].trim());
				}
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
	private void getdeviceInfoPropsMap(final String deviceId) {

		if (deviceInfoProps.get(deviceId + ".state").equals("Online")) {
			deviceInfoProps.put(deviceId + ".id", deviceId);
			final Map<String, String> propertyMap = getPropertyFromArray(getDeviceInfo(deviceId), "=");
			deviceInfoProps.put(deviceId + ".model", propertyMap.get("ro.product.model"));
			deviceInfoProps.put(deviceId + ".version", propertyMap.get("ro.build.version.sdk"));
			deviceInfoProps.put(deviceId + ".slave", System.getenv("NODE_NAME"));
		}
	}
}
