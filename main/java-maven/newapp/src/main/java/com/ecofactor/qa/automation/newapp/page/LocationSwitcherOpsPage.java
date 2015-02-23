/*
 * LocationSwitcherOpsPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.page.impl.LocationSwitcherOpsPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface LocationSwitcherOpsPage.
 * @author $Author: dramkumar $
 * @version $Rev: 31304 $ $Date: 2014-06-25 09:40:55 +0530 (Wed, 25 Jun 2014) $
 */
@ImplementedBy(value = LocationSwitcherOpsPageImpl.class)
public interface LocationSwitcherOpsPage extends BasePage {

    /**
     * Select tstat by id.
     * @param tstatId the tstat id
     */
    void selectTstatById(Integer tstatId);

    /**
     * Click close.
     */
    void clickClose();

    /**
     * Click location.
     * @param locaionName the locaion name
     */
    void clickLocation(String locaionName);

    /**
     * Click background.
     */
    void clickBackground();
}
