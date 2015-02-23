/*
 * LocationDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */

package com.ecofactor.qa.automation.dao;

import com.ecofactor.common.pojo.DisconnectedDevice;

/**
 * The Interface LocationDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DisconnectedDeviceDao extends BaseDao<DisconnectedDevice> {


	/**
     * Gets the location by user id.
     * @param userdId the userd id
     * @return the location by user id
     */
	public DisconnectedDevice getDeviceStatus(Integer thermostatId);

}
