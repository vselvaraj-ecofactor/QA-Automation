/*
 * EFUserDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.User;

/**
 * The Interface EFUserDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface EFUserDao extends BaseDao<User> {

	/**
     * Gets the user lists.
     * @param userName the user name
     * @return the user lists
     */
	public List<User> getUserLists(String userName);

	/**
     * Gets the user name by thermostat id.
     * @param thermostatId the thermostat id
     * @return the user name by thermostat id
     */
	public User getUserNameByThermostatId(Integer thermostatId);


	/**
     * Find by user name.
     * @param userName the user name
     * @return the user
     */
	public User findByUserName(String userName);

	/**
	 * List to be deleted users.
	 * @return the list
	 */
	public List<User> listTobeDeletedUsers();

	/**
     * Update user.
     * @param user the user
     * @return the boolean
     */
	public Boolean updateUserToProvision(User user);
	
	/**
     * Update password to ecofactor.
     * @param user the user
     * @return true, if successful
     */
    public boolean updatePasswordToEcofactor(User user);
}
