/*
 * LSProgramRulesDaoImpl.java
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

import com.ecofactor.common.pojo.EcpCoreLSProgramRules;

/**
 * The Class LSProgramRulesDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LSProgramRulesDaoImpl extends BaseDaoImpl<EcpCoreLSProgramRules> implements LSProgramRulesDao {

    /**
     * @param lsProgram_id
     * @param rule_Name
     * @return
     * @see com.ecofactor.qa.automation.dao.LSProgramRulesDao#getEcpCoreLSProgramRules(int,
     *      java.lang.String)
     */
    @Override
    public List<EcpCoreLSProgramRules> getEcpCoreLSProgramRules(int lsProgram_id, String rule_Name) {

        String sql = "select d from EcpCoreLSprogramRules d where lsProgramId=: lsProgramId and name =: ruleName ";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsProgramId", lsProgram_id);
        paramVals.put("ruleName", rule_Name);
        List<EcpCoreLSProgramRules> events = listByQuery(sql, paramVals);
        return events;
    }

}
