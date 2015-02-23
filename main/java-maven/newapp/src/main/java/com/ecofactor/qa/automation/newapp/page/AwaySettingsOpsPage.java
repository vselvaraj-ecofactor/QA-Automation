/*
 * AwaySettingsOpsPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.impl.AwaySettingsOpsPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface AwaySettingsOpsPage.
 * @author $Author: vprasannaa $
 * @version $Rev: 32804 $ $Date: 2014-11-20 15:45:25 +0530 (Thu, 20 Nov 2014) $
 */
@ImplementedBy(value = AwaySettingsOpsPageImpl.class)
public interface AwaySettingsOpsPage {

    /**
     * Sets the away param picker.
     * @param setAwayParams the set away params
     * @param value the value
     * @return true, if successful
     */
    boolean setAwayParamPicker(final SetAwayParams setAwayParams, final int expectedValue);

    /**
     * Selects Away Settings from Menu.
     */
    void clickAwaySettings();

    /**
     * Selects Away Settings from Menu.
     */
    void clickThermostat();

    /**
     * Click menu icon.
     */
    void clickMenuIcon();

    /**
     * Click away param picker.
     * @param setAwayParam the set away param
     * @return true, if successful
     */
    boolean clickAwayParamPicker(final SetAwayParams setAwayParam);

    /**
     * Swipe.
     */
    void swipePage(String direction);

    /**
     * click Back.
     */
    void clickBack();

}
