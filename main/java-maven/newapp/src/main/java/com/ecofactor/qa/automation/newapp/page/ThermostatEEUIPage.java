/*
 * ThermostatEEUIPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.page.impl.ThermostatEEUIPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface ThermostatEEUIPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = ThermostatEEUIPageImpl.class)
public interface ThermostatEEUIPage {

    /**
     * Checks if is close button displayed.
     * @return true, if is close button displayed
     */
    boolean isCloseButtonDisplayed();

    /**
     * Checks if is image displayed.
     * @return true, if is image displayed
     */
    boolean isImageDisplayed();

    /**
     * Checks if is content displayed.
     * @return true, if is content displayed
     */
    boolean isContentDisplayed();

}
