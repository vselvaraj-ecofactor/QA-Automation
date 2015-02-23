/*
 * SavingsReportDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.quant;

import java.util.Calendar;
import java.util.List;

import com.ecofactor.qa.automation.pojo.EWQuantReport;

/**
 * DailyReportDao provides the interface for Daily Reports of location and user.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface SavingsReportDao {

    /**
     * List thermostat savings.
     * @param thermostatId the thermostat id
     * @param startDate the start date
     * @param endDate the end date
     * @return the list
     */
    public List<EWQuantReport> listThermostatSavings(int thermostatId, Calendar startDate, Calendar endDate);

    /**
     * List thermostat savings.
     * @param thermostatId the thermostat id
     * @return the list
     */
    public List<EWQuantReport> listThermostatSavings(int thermostatId);
}
