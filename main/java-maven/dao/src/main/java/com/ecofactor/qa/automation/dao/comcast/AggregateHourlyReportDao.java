/*
 * HourlyReportDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.comcast;

import java.util.Calendar;
import java.util.List;

import com.ecofactor.qa.automation.pojo.AggHourlyStatsThermostatWeather;


/**
 * AggregateHourlyReportDao provides the interface for hourly report dao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface AggregateHourlyReportDao {

    /**
	 * List hvac status on minutes g t60.
	 *
	 * @return the int
	 */
    public List<AggHourlyStatsThermostatWeather> listHvacStatusOnMinutesGT60();


    /**
	 * List hourly tstat status weather.
	 *
	 * @param startDate the start date
	 * @param enddate the enddate
	 * @return the int
	 */
    public List<AggHourlyStatsThermostatWeather> listHourlyTstatStatusWeather(Calendar startDate,Calendar enddate);
}
