/*
 * UserLocationStagingDaoImpl.java
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

import com.ecofactor.common.pojo.UserLocationStaging;

/**
 * The Class UserLocationStagingDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserLocationStagingDaoImpl extends BaseDaoImpl<UserLocationStaging> implements UserLocationStagingDao {

    /**
     * Find by id.
     * @param id the id
     * @return the UserLocationStaging
     * @see com.ecofactor.qa.automation.dao.UserLocationStagingDao#findById(java.lang.Integer)
     */
    public UserLocationStaging findById(Integer id) {

        String ql = "SELECT a FROM UserLocationStaging a WHERE a.id = :UserLocationStagingId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("UserLocationStagingId", id);

        UserLocationStaging UserLocationStaging = findByQuery(ql, paramVals);
        return UserLocationStaging;
    }

    @Override
    public List<UserLocationStaging> findByUploadSessionId(Integer uploadSessionId) {

        String ql = "SELECT a FROM UserLocationStaging a,UploadSession b WHERE a.uploadSession.id=b.id and  b.id = :UploadSessionId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("UploadSessionId", uploadSessionId);

        List<UserLocationStaging> UserLocationStaging = listByQuery(ql, paramVals);
        return UserLocationStaging;
    }
}
