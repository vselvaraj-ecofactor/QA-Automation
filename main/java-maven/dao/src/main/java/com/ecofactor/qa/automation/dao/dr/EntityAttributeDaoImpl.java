/*
 * EntityAttributeDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.EntityAttribute;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class EntityAttributeDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */

public class EntityAttributeDaoImpl extends BaseDaoImpl<EntityAttribute> implements
        EntityAttributeDao {

    /**
     * List all the values based on entity id.
     * @param entityId the entity id.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.EntityAttributeDao#listByEntityId(java.lang.Integer)
     */
    @Override
    public List<EntityAttribute> listByEntityId(Integer entityId) {

        String sql = "select t from EntityAttribute  t where t.entity_id = :entity_id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("entity_id", entityId);
        List<EntityAttribute> attributes = listByQuery(sql, paramVals);
        return attributes;
    }

}
