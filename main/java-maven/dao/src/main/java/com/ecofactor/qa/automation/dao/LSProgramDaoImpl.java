/*
 * LSProgramDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
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

import com.ecofactor.common.pojo.EcpCoreLSProgram;

/**
 * The Class LSProgramDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LSProgramDaoImpl extends BaseDaoImpl<EcpCoreLSProgram> implements LSProgramDao {

    /**
     * Gets the ecp core ls program.
     * @param lsProgramid the ls programid
     * @return the ecp core ls program
     * @see com.ecofactor.qa.automation.dao.LSProgramDao#getEcpCoreLSProgram(int)
     */
    @Override
    public List<EcpCoreLSProgram> getEcpCoreLSProgram(int lsProgramid) {

        String sql = "select t from EcpCoreLSProgram  t where t.id = :lsPgm_id ";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsPgm_id", lsProgramid);
        List<EcpCoreLSProgram> results = listByQuery(sql, paramVals);
        return results;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.dao.LSProgramDao#getAllEcpCoreLSProgram()
     */
    @Override
    public List<EcpCoreLSProgram> getAllEcpCoreLSProgram() {

        String sql = "select t from EcpCoreLSProgram  t";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        List<EcpCoreLSProgram> results = listByQuery(sql,paramVals);
        return results;
    }



}
