/*
 * LSProgramRulesDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.EcpCoreLSProgramRules;

/**
 * The Interface LSProgramRulesDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LSProgramRulesDao extends BaseDao<EcpCoreLSProgramRules> {
	
	/**
     * Gets the ecp core ls program rules.
     * @param lsProgramid the ls programid
     * @param ruleName the rule name
     * @return the ecp core ls program rules
     */
	public List<EcpCoreLSProgramRules> getEcpCoreLSProgramRules(int lsProgramid,String ruleName);
	

}
