/*
 * ProgramReportDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

/**
 * DailyReportDao provides the interface for Daily Reports of location and user.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
import java.util.Calendar;
import java.util.List;

import com.ecofactor.common.pojo.analytics.QuantReport;
import com.ecofactor.qa.automation.dao.BaseDao;

public interface ProgramReportDao extends BaseDao<QuantReport> {

    /**
     * List report for particular program event based on thermostat Id.
     * @param thermostatId the thermostat Id.
     * @return list.
     */
    public List<QuantReport> listReportByThermostatId(Integer thermostatId);

    /**
     * List report for particular program event based on thermostat id with start and end time.
     * @param thermostatId the thermostat Id.
     * @param startDate the start date.
     * @param endDate the end date.
     * @return list.
     */
    public List<QuantReport> listReportbyThermostatStartAndEndTime(int thermostatId,
            Calendar startDate, Calendar endDate);
}
