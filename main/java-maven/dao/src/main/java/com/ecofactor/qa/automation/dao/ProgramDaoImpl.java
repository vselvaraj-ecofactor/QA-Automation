/*
 * ProgramDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.Program;

/**
 * The Class ProgramDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ProgramDaoImpl extends BaseDaoImpl<Program> implements ProgramDao {

    /**
     * Gets the program lists.
     * @param programName the program name
     * @param userId the user id
     * @return the program lists
     * @see com.ecofactor.qa.automation.dao.ProgramDao#getProgramLists(java.lang.String, int)
     */
    @Override
    public List<Program> getProgramLists(String programName, int userId) {

        String sql = "SELECT efProgram from Program efProgram where efProgram.name = :efProgramName and efProgram.user = :efProgramUserId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("efProgramName", programName);
        paramVals.put("efProgramUserId", userId);
        List<Program> Programs = listByQuery(sql, paramVals);
        return Programs;
    }

    /**
     * @param thermostatId
     * @return
     * @see com.ecofactor.qa.automation.dao.ProgramDao#getActiveProgram(int)
     */
    @Override
    public Program getActiveProgram(int thermostatId, String programType) {

        String sql = "SELECT a from Program a where a.id in (select program.id from ThermostatProgram  where thermostatProgramStatus = 'ACTIVE' and thermostatId = :tstatId ) and a.type = :programType  order by a.id desc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("tstatId", thermostatId);
        paramVals.put("programType", programType);
        Program program  = findByQuery(sql, paramVals);
        return program;
    }
}
