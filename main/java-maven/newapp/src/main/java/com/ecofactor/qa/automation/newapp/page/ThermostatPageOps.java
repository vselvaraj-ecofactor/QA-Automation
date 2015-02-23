/*
 * ThermostatPageOps.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.page.impl.ThermostatPageOpsImpl;
import com.google.inject.ImplementedBy;

// TODO: Auto-generated Javadoc
/**
 * The Interface ThermostatPageOps.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = ThermostatPageOpsImpl.class)
public interface ThermostatPageOps extends BasePage {

    /**
     * Close help.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#closeHelp()
     */
    void closeHelp();

    /**
     * Click current temp.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#clickCurrentTemp()
     */
    void clickCurrentTemp();

    /**
     * Sets the away popup.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#setAway()
     */
    void setAway();

    /**
     * Swipe.
     * @param leftOrRight the left or right
     */
    void swipe(String leftOrRight);

    /**
     * Drag target to.
     * @param currentTarget the current target
     * @param target the target
     */
    void dragTargetTo(Integer currentTarget, Integer target);

    /**
     * Drop and drop target.
     * @param currentTarget the current target
     * @param target the target
     */
    void dragAndDropTargetTo(Integer currentTarget, Integer target);

    /**
     * Drag target horizontal.
     */
    void dragTargetHorizontal();

    /**
     * Drop target to.
     */
    void dropTarget();

    /**
     * Turn system on.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#turnSystemOn()
     */
    void turnSystemOn();

    /**
     * Click target.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#clickTarget()
     */
    void openTstatController();

    /**
     * Click logout.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#clickLogout()
     */
    void logout();

    /**
     * Click menu.
     */
    void clickMenu();

    /**
     * Click savings.
     */
    void clickSavings();

    /**
     * Target temp change value by drag.
     * @param change the change
     */
    void targetTempChangeValueByDrag(final int change);

    /**
     * Load away settings popup.
     * @return true, if successful
     */
    boolean loadAwaySettingsPopup();

    /**
     * Click location switcher.
     */
    void clickLocationSwitcher();

    /**
     * Click controls icon.
     */
    void clickControlsIcon();

    /**
     * Swipe loc.
     * @param leftOrRight the left or right
     */
    void swipeLoc(String leftOrRight);

    /**
     * click set Away icon.
     */
    void clickSetAway();

    /**
     * Click heatIcon.
     */
    void clickHeatIcon();

    /**
     * Click Cool Icon.
     */
    void clickCoolIcon();

    /**
     * click settings icon in menu page.
     */
    void clickSettingsIcon();

    /**
     * Settings fields operation.
     */
    void settingsOperations();

    /**
     * click thermostat name in menu page.
     */
    void clickThermostatIcon();

    /**
     * click menu button in settings.
     */
    void clickSettingsMenu();

    /**
     * simple click.
     */
    void clickotherplace();

    /**
     * close the savings icon.
     */
    void isSavingsClickable();

    /**
     * verify either set away is display or not.
     */
    void closeSetAwayIcon();   
}
