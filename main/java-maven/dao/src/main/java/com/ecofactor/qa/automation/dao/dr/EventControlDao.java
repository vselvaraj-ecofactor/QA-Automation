/*
 * EventControlDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

import com.ecofactor.qa.automation.dao.BaseDao;
import com.ecofactor.qa.automation.pojo.EventControl;

/**
 * The Interface EventControlDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface EventControlDao extends BaseDao<EventControl> {

    /**
     * Update status as INACTIVE.
     * @param eventProgramId the program event id.
     */
    public void updateStatus(Integer eventProgramId,String status);

    /**
     * Update nexpPhaseTime based on event program Id.
     * @param eventProgramId the program event id.
     */
    public void updateNextPhaseTime(Integer eventProgramId);

    /**
     * Fetch the next Phase time.
     * @param eventProgramId the program event id.
     * @return String.
     * @throws ParseException
     */
    public String fetchNextPhaseTime(Integer eventProgramId) throws ParseException;

    /**
     * Fetch the status for specified Event Id
     * @param eventProgramId the program Event id.
     * @return String.
     */
    public String fetchStatus(Integer eventProgramId);    
}
