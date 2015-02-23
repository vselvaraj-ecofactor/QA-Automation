/*
 * ThermostatRangeDataDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.Calendar;
import java.util.List;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;

/**
 * The Interface ThermostatRangeDataDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ThermostatRangeDataDao extends BaseDao<PartitionedThermostatRangeData> {

    /**
     * List by thermostat and start time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    List<PartitionedThermostatRangeData> listByThermostatAndStartTimeRange(Integer thermostatId,
            Calendar startTime, Calendar endTime);

    /**
     * List by thermostat and start time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    List<PartitionedThermostatRangeData> listByThermostatAndStartTimeRange(Integer thermostatId,
            Calendar startTime);

    /**
     * Find latest by thermostat.
     * @param thermostatId the thermostat id
     * @return the thermostat range data
     */
    PartitionedThermostatRangeData findLatestByThermostat(Integer thermostatId);
}
