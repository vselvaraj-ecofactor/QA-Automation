/*
 * DisconnectedDeviceDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */

package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.DisconnectedDevice;

/**
 * The Class DisconnectedDeviceDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DisconnectedDeviceDaoImpl extends BaseDaoImpl<DisconnectedDevice> implements DisconnectedDeviceDao {


    /**
     * @param thermostatId
     * @return
     * @see com.ecofactor.qa.automation.dao.DisconnectedDeviceDao#getDeviceStatus(java.lang.Integer)
     */
    @Override
    public DisconnectedDevice getDeviceStatus(Integer thermostatId){

        String sql = "SELECT loc from DisconnectedDevice loc where loc.deviceId =:deviceId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("deviceId", thermostatId);
        DisconnectedDevice results = findByQuery(sql, paramVals);
        return results;
    }



}
