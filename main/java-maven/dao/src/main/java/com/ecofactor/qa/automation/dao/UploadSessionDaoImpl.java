/*
 * UploadSessionDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.UploadSession;

/**
 * The Class UploadSessionDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UploadSessionDaoImpl extends BaseDaoImpl<UploadSession> implements UploadSessionDao {

    /**
     * Find by id.
     * @param id the id
     * @return the UploadSession
     * @see com.ecofactor.qa.automation.dao.UploadSessionDao#findById(java.lang.Integer)
     */
    public UploadSession findById(Integer id) {

        String ql = "SELECT a FROM UploadSession a WHERE a.id = :UploadSessionId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("UploadSessionId", id);

        UploadSession uploadSession = findByQuery(ql, paramVals);
        return uploadSession;
    }

    @Override
    public UploadSession findByFileName(String fileName) {
                
            String ql = "SELECT a FROM UploadSession a WHERE a.filename = :fileName";
            Map<String, Object> paramVals = new HashMap<String, Object>();
            paramVals.put("fileName", fileName);

            UploadSession uploadSession = findByQuery(ql, paramVals);
            return uploadSession;
    }
}
