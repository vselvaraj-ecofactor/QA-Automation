/*
 * UserLocationStagingDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.UserLocationStaging;

/**
 * The Interface UserLocationStagingDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface UserLocationStagingDao {

    /**
     * Find by id.
     * @param id the id
     * @return the UserLocationStaging
     */
    UserLocationStaging findById(Integer id);

    List<UserLocationStaging> findByUploadSessionId(Integer uploadSessionId);
}
