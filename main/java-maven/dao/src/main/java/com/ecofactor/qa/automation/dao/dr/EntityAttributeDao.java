/*
 * EntityAttributeDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.List;

import com.ecofactor.common.pojo.EntityAttribute;
import com.ecofactor.qa.automation.dao.BaseDao;

/**
 * The Interface EntityAttributeDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface EntityAttributeDao extends BaseDao<EntityAttribute> {

    /**
     * List all the values based on entity id.
     * @param entityId the entity id.
     * @return list.
     */
    public List<EntityAttribute> listByEntityId(Integer entityId);
}
