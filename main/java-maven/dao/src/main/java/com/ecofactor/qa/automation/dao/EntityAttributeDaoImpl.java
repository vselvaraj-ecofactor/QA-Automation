/*
 * EntityAttributeDaoImpl.java
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

import com.ecofactor.common.pojo.EntityAttribute;

/**
 * The Class EntityAttributeDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class EntityAttributeDaoImpl extends BaseDaoImpl<EntityAttribute> implements
        EntityAttributeDao {

    // LS_MAX_SHED_OFFSET
    /**
     * Gets the attributes.
     * @param programId the program id
     * @param atributeKey the atribute key
     * @return the attributes
     * @see com.ecofactor.qa.automation.dao.EntityAttributeDao#getAttributes(int, java.lang.String)
     */
    @Override
    public List<EntityAttribute> getAttributes(int programId, String atributeKey) {

        String sql = "select t from EntityAttribute  t where t.id = :lsPgm_id and t.attributeKey =:lsAttributeKey";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsPgm_id", programId);
        paramVals.put("lsAttributeKey", atributeKey);
        List<EntityAttribute> attributes = listByQuery(sql, paramVals);
        return attributes;
    }
}
