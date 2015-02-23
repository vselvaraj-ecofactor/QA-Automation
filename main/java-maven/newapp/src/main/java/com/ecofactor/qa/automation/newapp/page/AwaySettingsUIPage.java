/*
 * AwaySettingsUIPage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.impl.AwaySettingsUiPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface AwaySettings.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = AwaySettingsUiPageImpl.class)
public interface AwaySettingsUIPage extends BasePage {

    /**
     * Gets the picker element.
     * @param setAwayParam the set away param
     * @return the picker element
     */
    WebElement getPickerElement(final SetAwayParams setAwayParam);

    /**
     * Gets the picker value.
     * @param setAwayParam the set away param
     * @return the picker value
     */
    int getPickerValue(final SetAwayParams setAwayParam);

    /**
     * Checks if is sets the away param label displayed.
     * @param setAwayParam the set away param
     * @return true, if is sets the away param label displayed
     */
    boolean isSetAwayParamLabelDisplayed(final SetAwayParams setAwayParam);

    /**
     * Checks whether the away settings displayed
     * @return true, if away settings displayed
     */
    boolean isAwaySettingsAlertDisplayed();

    /**
     * Checks if is duration label displayed.
     * @return true, if is duration label displayed
     */
    boolean isDurationLabelDisplayed();

}
