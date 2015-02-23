/*
 * ProgramDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.Program;

/**
 * The Interface ProgramDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ProgramDao extends BaseDao<Program> {
	
	/**
     * Gets the program lists.
     * @param programName the program name
     * @param userId the user id
     * @return the program lists
     */
	public List<Program> getProgramLists(String programName,int userId);

    /**
     * Gets the active program.
     * @param thermostatId the thermostat id
     * @return the active program
     */
    Program getActiveProgram(int thermostatId, String programType);

}
