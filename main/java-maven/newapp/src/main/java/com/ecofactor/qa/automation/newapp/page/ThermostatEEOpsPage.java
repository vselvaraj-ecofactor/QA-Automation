/*
 * ThermostatEEOpsPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.page.impl.ThermostatEEOpsPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface ThermostatEEOpsPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = ThermostatEEOpsPageImpl.class)
public interface ThermostatEEOpsPage {

    /**
     * Click close.
     */
    void clickClose();
    
    /**
     * Click savings ee link.
     */
    void clickSavingsEELink();

}
