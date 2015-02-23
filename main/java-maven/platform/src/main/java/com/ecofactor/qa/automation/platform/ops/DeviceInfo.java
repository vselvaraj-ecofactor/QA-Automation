/*
 * DeviceInfo.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops;

import java.util.Properties;

/**
 * The Interface DeviceInfo.
 */
public interface DeviceInfo {

	/**
	 * Populate device details.
	 * @return the properties
	 */
	public Properties populateDeviceDetails();
}
