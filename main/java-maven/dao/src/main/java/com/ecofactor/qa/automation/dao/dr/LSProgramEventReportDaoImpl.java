/*
 * LSProgramEventReportDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.pojo.LSProgramEventReport;

/**
 * The Class LSProgramEventReportDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LSProgramEventReportDaoImpl extends BaseDaoImpl<LSProgramEventReport> implements
        LSProgramEventReportDao {

    /**
     * Fetch the details based on program event id.
     * @param programEventId the program event id.
     * @return Map.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventReportDao#updatedDetails(java.lang.Integer)
     */
    @Override
    public Map<String, Object> updatedDetails(int programEventId) {

        String sql = "SELECT lspER FROM LSProgramEventReport lspER WHERE lspER.programID=:proId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("proId", programEventId);
        LSProgramEventReport lspEventReport = findByQuery(sql, paramVals);
        Map<String, Object> values = new HashMap<String, Object>();
        final int expectedThermostats = lspEventReport.getNumExpectedThermostats();
        final int expectedLocations = lspEventReport.getNumExpectedLocations();
        final int actualThermostats = lspEventReport.getNumActualThermostats();
        final int actualLocations = lspEventReport.getNumActualLocations();
        values.put("Number Expected Thermostats :", expectedThermostats);
        values.put("Number Expected Locations :", expectedLocations);
        values.put("Number Actual Thermostats :", actualThermostats);
        values.put("Number Actual Locations :", actualLocations);
        return values;
    }

}
