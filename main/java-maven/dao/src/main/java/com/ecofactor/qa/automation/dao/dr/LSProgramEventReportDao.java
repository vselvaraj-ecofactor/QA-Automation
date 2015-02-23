/*
 * LSProgramEventReportDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.Map;

import com.ecofactor.qa.automation.dao.BaseDao;
import com.ecofactor.qa.automation.pojo.LSProgramEventReport;

/**
 * The Interface LSProgramEventReportDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LSProgramEventReportDao extends BaseDao<LSProgramEventReport> {

    /**
     * Fetch the details based on program event id.
     * @param programEventId the program event id.
     * @return Map.
     */
    public Map<String, Object> updatedDetails(int programEventId);
}
