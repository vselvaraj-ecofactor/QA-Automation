package com.ecofactor.qa.automation.dao.quant;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.pojo.EWQuantReport;

public class SavingsReportDaoImpl extends BaseDaoImpl<EWQuantReport> implements SavingsReportDao {

    @Override
    public List<EWQuantReport> listThermostatSavings(int thermostatId, Calendar startDate, Calendar endDate) {

        String sql = "select q from EWQuantReport q where q.thermostatId = :thermostatId and q.validFrom between :startDate and :endDate";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startDate", startDate);
        paramVals.put("endDate", endDate);
        List<EWQuantReport> reports = listByReportQuery(sql, paramVals);
        return reports;
    }

    @Override
    public List<EWQuantReport> listThermostatSavings(int thermostatId) {

        String sql = "select q from EWQuantReport q where q.thermostatId = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        List<EWQuantReport> reports = listByReportQuery(sql, paramVals);
        return reports;
    }
}
