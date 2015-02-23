/*
 * EntityAttributeDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.EntityAttribute;

/**
 * The Interface EntityAttributeDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface EntityAttributeDao extends BaseDao<EntityAttribute> {

    /**
     * Gets the attributes.
     * @param programId the program id
     * @param attributeKey the attribute key
     * @return the attributes
     */
    public List<EntityAttribute> getAttributes(int programId, String attributeKey);

}
