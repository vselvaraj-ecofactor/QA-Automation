/*
 * SavingsPage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import java.text.ParseException;


import com.ecofactor.qa.automation.newapp.page.impl.SavingsPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface SavingsPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = SavingsPageImpl.class)
public interface SavingsPage extends BasePage {

    /**
     * Gets the total savings.
     * @return the total savings
     */
    String getTotalSavings();

    /**
     * Gets the current month.
     * @return the current month
     */
    String getCurrentMonthAndYear();

    /**
     * Click previous.
     */
    void clickPrevious();

    /**
     * Click next.
     */
    void clickNext();

    /**
     * Gets the savings value.
     * @return the savings value
     */
    String getSavingsValue();

    /**
     * Gets the savings hours.
     * @return the savings hours
     */
    String getSavingsHours();

    /**
     * Click menu.
     */
    void clickMenu();

    /**
     * Gets the savings percentage.
     * @return the savings percentage
     */
    String getSavingsPercentage();

    /**
     * Checks if is footer displayed.
     * @return true, if is footer displayed
     */
    boolean isFooterDisplayed();

    /**
     * Display savings data.
     * @param month the month
     * @param year the year
     * @param isDispalyData the is dispaly data
     * @throws ParseException the parse exception
     */
    void goToMonth(String month, String year, boolean isDispalyData) throws ParseException;

    /**
     * Checks if is arrow not displayed.
     * @param arrow the arrow
     * @return true, if is arrow not displayed
     */
    boolean isArrowNotDisplayed(String arrow);

    /**
     * Checks if is arrow displayed.
     * @param arrow the arrow
     * @return true, if is arrow displayed
     */
    boolean isArrowDisplayed(String arrow);

    /**
     * Checks if is learn more displayed.
     * @return true, if is learn more displayed
     */
    boolean isLearnMoreDisplayed();

    /**
     * Gets the location name.
     * @return the location name
     */
    String getLocationName();

    /**
     * Gets the savings header color.
     * @return the savings header color
     */
    boolean getSavingsHeaderColor();

    /**
     * Gets the total savings color.
     * @return the total savings color
     */
    boolean getTotalSavingsColor();

    /**
     * Gets the savings arrow color.
     * @return the savings arrow color
     */
    boolean getSavingsArrowColor();

    /**
     * Checks if is location not installed.
     * @return true, if is location not installed
     */
    boolean isLocationNotInstalled();

    /**
     * Checks if is savings dollar vertical.
     * @return true, if is savings dollar vertical
     */
    boolean isSavingsDollarVertical();

    /**
     * Checks if is savings month horizontal.
     * @return true, if is savings month horizontal
     */
    boolean isSavingsMonthHorizontal();
}
