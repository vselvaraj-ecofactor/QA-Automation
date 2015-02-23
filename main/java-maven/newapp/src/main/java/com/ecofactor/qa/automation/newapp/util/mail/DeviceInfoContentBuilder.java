/*
 * DeviceInfoContentBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util.mail;

import java.util.List;
import com.ecofactor.qa.automation.newapp.model.DeviceData;
import com.google.inject.ImplementedBy;

/**
 * The Interface DeviceInfoContentBuilder.
 */
@ImplementedBy(value = DeviceInfoHtmlContentBuilder.class)
public interface DeviceInfoContentBuilder {

	/**
	 * Device info.
	 * @param deviceData the device data
	 * @return the string
	 */
	String deviceInfo(List<DeviceData> deviceData);
}
