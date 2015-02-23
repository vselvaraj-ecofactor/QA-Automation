package com.ecofactor.qa.automation.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.analytics.QuantReport;

public class SavingsReportDaoImpl extends BaseDaoImpl<QuantReport> implements SavingsReportDao {

    @Override
    public List<QuantReport> listThermostatSavings(int thermostatId, Calendar startDate, Calendar endDate) {

        String sql = "select q from QuantReport q where q.thermostatId = :thermostatId and q.validFrom between :startDate and :endDate";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startDate", startDate);
        paramVals.put("endDate", endDate);
        List<QuantReport> reports = listByReportQuery(sql, paramVals);
        return reports;
    }

    @Override
    public List<QuantReport> listThermostatSavings(int thermostatId) {

        String sql = "select q from QuantReport q where q.thermostatId = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        List<QuantReport> reports = listByReportQuery(sql, paramVals);
        return reports;
    }
}
