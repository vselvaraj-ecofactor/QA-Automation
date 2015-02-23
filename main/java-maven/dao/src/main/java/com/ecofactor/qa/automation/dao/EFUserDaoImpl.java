/*
 * EFUserDaoImpl.java
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

import com.ecofactor.common.pojo.User;
import com.ecofactor.common.pojo.User.Status;

/**
 * The Class EFUserDaoImpl.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class EFUserDaoImpl extends BaseDaoImpl<User> implements EFUserDao {

	/**
	 * Gets the user lists.
	 * 
	 * @param userName
	 *            the user name
	 * @return the user lists
	 * @see com.ecofactor.qa.automation.dao.EFUserDao#getUserLists(java.lang.String)
	 */
	@Override
	public List<User> getUserLists(String userName) {

		String sql = "SELECT efUser from User efUser where efUser.username in (:userName)";
		Map<String, Object> paramVals = new HashMap<String, Object>();
		paramVals.put("userName", userName);
		List<User> results = listByQuery(sql, paramVals);
		return results;
	}

	/**
	 * Gets the user name by thermostat id.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the user name by thermostat id
	 * @see com.ecofactor.qa.automation.dao.EFUserDao#getUserNameByThermostatId(java.lang.Integer)
	 */
	public User getUserNameByThermostatId(Integer thermostatId) {

		String sql = "select t from User t where t.user_id in "
				+ " (select a.user_id from Location a, Thermostat b where a.id=b.locationid and "
				+ "b.id =:thermostatids) ";
		Map<String, Object> paramVals = new HashMap<String, Object>();
		paramVals.put("thermostatids", thermostatId);
		User results = findByQuery(sql, paramVals);
		return results;

	}

	/**
	 * @param userName
	 * @return
	 * @see com.ecofactor.qa.automation.dao.EFUserDao#findByUserName(java.lang.String)
	 */
	public User findByUserName(String userName) {
		String sql = "select t from User t where t.username = :userName";
		Map<String, Object> paramVals = new HashMap<String, Object>();
		paramVals.put("userName", userName);
		User results = findByQuery(sql, paramVals);
		return results;
	}

	/**
	 * List to be deleted users.
	 * 
	 * @return the list
	 */
	public List<User> listTobeDeletedUsers() {
		String sql = "SELECT *  FROM ef11qa_apps.ef_user  WHERE user_name REGEXP '(^ecofactorwizardautomation|ecofactorqaautomationuser1|ecofactorqaautomation).*[0-9]{2}@'";
		Map<String, Object> paramVals = new HashMap<String, Object>();
		List<User> userList = listByNativeQuery(sql, paramVals, User.class);
		return userList;
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            the user
	 * @return the boolean
	 * @see com.ecofactor.qa.automation.dao.EFUserDao#updateUser(com.ecofactor.common.pojo.User)
	 */
	@Override
	public Boolean updateUserToProvision(User user) {

		user.setStatus(Status.PROVISION);
		Boolean entitySaved = updateUserEntity(user);
		return entitySaved;
	}

	/**
	 * Update password to ecofactor.
	 * 
	 * @param user
	 *            the user
	 * @return true, if successful
	 * @see com.ecofactor.qa.automation.dao.EFUserDao#updatePasswordToEcofactor(com.ecofactor.common.pojo.User)
	 */
	@Override
	public boolean updatePasswordToEcofactor(User user) {

		final String password = "2bf8d6e82454ff2f1a2855c8c111fbb0615d09fd";
		user.setPasswd(password);
		final boolean updated = updateUserEntity(user);
		return updated;
	}

}
