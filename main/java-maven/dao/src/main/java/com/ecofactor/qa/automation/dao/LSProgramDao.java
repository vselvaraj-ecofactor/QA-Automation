/*
 * LSProgramDao.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.EcpCoreLSProgram;

/**
 * The Interface LSProgramDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LSProgramDao extends BaseDao<EcpCoreLSProgram> {

	/**
     * Gets the ecp core ls program.
     * @param lsProgramid the ls programid
     * @return the ecp core ls program
     */
	public List<EcpCoreLSProgram> getEcpCoreLSProgram(int lsProgramid);

	/**
     * Gets the all ecp core ls program.
     * @return the all ecp core ls program
     */
	public List<EcpCoreLSProgram> getAllEcpCoreLSProgram();

}
