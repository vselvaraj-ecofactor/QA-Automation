/*
 * MenuPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.page.impl.MenuPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface MenuPage.
 * @author $Author: vprasannaa $
 * @version $Rev: 32983 $ $Date: 2014-12-08 16:55:00 +0530 (Mon, 08 Dec 2014) $
 */
@ImplementedBy(value = MenuPageImpl.class)
public interface MenuPage extends BasePage {

    /**
     * Click clickMenuIcon.
     */
    void clickMenuIcon();

    /**
     * Click clickMenuIconOnMenuPage.
     */
    void clickMenuIconOnMenuPage();

    /**
     * Click clickThermostatMenuItem.
     */
    void clickThermostatMenuItem();

    /**
     * Click clickLogoutMenuItem.
     */
    void clickLogoutMenuItem();

    /**
     * Checks if is footer slice displayed.
     * @return true, if is footer slice displayed
     */
    boolean isFooterSliceDisplayed();

    /**
     * Click footer slice.
     */
    void clickSlice();

    /**
     * Click clickThermostatMenuItem.
     */
    void clickAwayTemperature();

    /**
     * Swipe page.
     * @param leftOrRight the left or right
     */
    void swipePage(String leftOrRight);

    /**
     * Checks if is savings label displayed.
     * @return true, if is savings label displayed
     */
    boolean isSavingsLabelDisplayed();

    /**
     * Click savings.
     */
    void clickSavings();

    /**
     * click when thermostat highlighted.
     */
    void clickThermostatHighlighted();

    /**
     * click menu container for focus
     */
    void clickMenuContainer();
    
    /**
     * Click away higlighted.
     */
    void clickAwayHiglighted();
}
