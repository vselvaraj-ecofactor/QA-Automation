/*
 * AggregateHourlyReportDaoImpl.java
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

import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.pojo.AggHourlyStatsThermostatWeather;

/**
 * AggregateHourlyReportDaoImpl implements AggregateHourlyReportDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AggregateHourlyReportDaoImpl extends BaseDaoImpl<AggHourlyStatsThermostatWeather> implements AggregateHourlyReportDao {

	/**
     * @return
     * @see com.ecofactor.qa.automation.dao.HourlyReportDao#listHvacStatusOnMinutesGT60()
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<AggHourlyStatsThermostatWeather> listHvacStatusOnMinutesGT60() {

        String sql = "SELECT s.year,s.month,s.day,count(s.hvacStateOnMinutes) FROM AggHourlyStatsThermostatWeather s where hvacStateOnMinutes > 60 group by 1,2,3";
        List<AggHourlyStatsThermostatWeather> stats =getComcastUIReportEntityManager().createQuery(sql).setMaxResults(2).getResultList();
        return stats;
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     * @see com.ecofactor.qa.automation.dao.comcast.AggregateHourlyReportDao#listHourlyTstatStatusWeather(java.util.Calendar, java.util.Calendar)
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<AggHourlyStatsThermostatWeather> listHourlyTstatStatusWeather(Calendar startDate,Calendar endDate) {

        String sql = "SELECT s FROM AggHourlyStatsThermostatWeather s where statsDatetime >=:start_Date and  statsDatetime< :end_Date group by 1,2,3 having count(hvac_state)>1 ";
        List<AggHourlyStatsThermostatWeather> stats = (List<AggHourlyStatsThermostatWeather>)getComcastUIReportEntityManager().createQuery(sql).setParameter("start_Date", startDate.getTime()).setParameter("end_Date", endDate.getTime()).setMaxResults(1).getResultList();
        return stats;
    }

}
