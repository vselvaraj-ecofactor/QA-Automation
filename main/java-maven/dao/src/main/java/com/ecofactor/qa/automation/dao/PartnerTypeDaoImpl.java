/*
 * PartnerTypeDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.Map;




import com.ecofactor.common.pojo.PartnerType;
import com.ecofactor.common.pojo.PartnerType.PartnerTypeName;


/**
 * The Class PartnerTypeDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PartnerTypeDaoImpl  extends BaseDaoImpl<PartnerType> implements PartnerTypeDao {

    /**
     * @param partnerTypeName
     * @return
     * @see com.ecofactor.qa.automation.dao.PartnerTypeDao#getPartnerTypeByName(java.lang.String)
     */
    @Override
    public PartnerType getPartnerTypeByName(PartnerTypeName partnerTypeName) {

        String sql = " select e from PartnerType e " + " where  e.name =:name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("name", partnerTypeName);
        PartnerType user = findByQuery(sql, paramVals);
        return user;
    }

}
