/*
 * HelpOverlayPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import com.ecofactor.qa.automation.newapp.enums.*;
import com.ecofactor.qa.automation.newapp.page.impl.HelpOverlayPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface SavingsPage.
 * @author $Author: vprasannaa $
 * @version $Rev: 32256 $ $Date: 2014-10-14 15:46:14 +0530 (Tue, 14 Oct 2014) $
 */
@ImplementedBy(value = HelpOverlayPageImpl.class)
public interface HelpOverlayPage extends BasePage {

    /**
     * Move to page.
     * @param pageIndex the page index
     * @param swipe the swipe
     */
    void goToPage(final HelpPage page, final boolean swipe);

    /**
     * Click close.
     */
    void closeHelpOverlay();

    /**
     * Check swipe away menu help.
     */
    void checkSwipeAwayMenuHelp();

    /**
     * Gets the current page.
     * @return the current page
     */
    HelpPage getCurrentPage();

    /**
     * Swipe direction.
     * @param direction the direction
     */
    void swipeDirection(final String direction);

    /**
     * Checks if is slide loaded.
     * @param page the page
     */
    boolean isSlideLoaded(final HelpPage page, final String image);

    /**
     * Loads the previous page on the help overlay.
     * @param page the page to load
     * @param swipeStatus the swipe
     */
    void loadPreviousPage(final int pageIndicator, final boolean swipeStatus);

    /**
     * Go to thermostat page.
     */
    void goToThermostatPage();

    /**
     * verify either reach the First page of Help Overlay page.
     * @return true, if drag and drop page loaded in help overlay page.
     */
    boolean helpOverlayPageOne();

    /**
     * verify either reach the second page of Help overlay page.
     * @return true, if savings energy page loaded in help overlay page.
     */
    boolean helpOverlayPageTwo();

    /**
     * verify either reach the third page of Help overlay page.
     * @return true, if switch thermostat page loaded in help overlay page.
     */
    boolean helpOverlayPageThree();
}
