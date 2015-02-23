/*
 * HelpOverlayPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.enums.HelpPage;
import com.ecofactor.qa.automation.newapp.page.HelpOverlayPage;
import com.ecofactor.qa.automation.util.WaitUtil;

/**
 * The Class SavingsPageImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 32256 $ $Date: 2014-10-14 15:46:14 +0530 (Tue, 14 Oct 2014) $
 */
public class HelpOverlayPageImpl extends AbstractAuthenticationPageImpl implements HelpOverlayPage {

    private static final String CLOSE_BRACKET = "']";
    private static final String IMG_SRC = "img[src='";
    private static final String HELP_DIV = "help_slide";
    private static final String HELP_CLOSE = "closeButton";
    private static final String SWIPE = "FTUXBody";
    private static final String MENU_IMG = "img/help/HelpMenu.png";
    private static final String AWAY_IMG = "img/help/HelpSavesEnergy.png";
    private static final String SWIPE_IMG = "/img/help/HelpForeground-03.png";
    private static final String SAVINGS_IMG = "img/help/HelpSavings.png";
    private static final String RIGHT = "right";
    private static final String FIRSTIMAGE_HELP = ".ftuxHand";

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        // TODO Auto-generated method stub

    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        setLogString("Verify if help overlay page is loaded.", true);
        return isDisplayed(getDriver(), By.className(HELP_DIV), TINY_TIMEOUT);
    }

    /**
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#closeHelpOverlay()
     */
    @Override
    public void closeHelpOverlay() {

        setLogString("Close Help", true);
        final WebElement closeElement = getElement(getDriver(), By.className(HELP_CLOSE),
                TINY_TIMEOUT);
        uiAction.click(closeElement);
    }

    /**
     * Gets the swipe container.
     * @return the swipe container
     */
    private WebElement getSwipeContainer() {

        return getElement(getDriver(), By.className(SWIPE), TINY_TIMEOUT);
    }

    /**
     * Gets the next button.
     * @return the next button
     */
    private WebElement getNextButton() {

        return getElement(getDriver(), By.cssSelector("div.nextButton"), TINY_TIMEOUT);
    }

    /**
     * Gets the started button.
     * @return the started button
     */
    private WebElement getStartedButton() {

        return getElement(getDriver(), By.cssSelector("div.doneButton"), TINY_TIMEOUT);
    }

    /**
     * Gets the current page element.
     * @return the current page element
     */
    public HelpPage getCurrentPage() {

        WebElement currentPageElement = null;
        HelpPage currentPage = null;
        currentPageElement = getElement(getDriver(), By.cssSelector("div.help_slide"), TINY_TIMEOUT);
        for (final HelpPage page : HelpPage.values()) {

            currentPageElement = getElement(getDriver(), By.cssSelector("div.help_slide"),
                    TINY_TIMEOUT);
            if (currentPageElement != null) {
                currentPage = page;
                getAction().click(getNextButton());
                WaitUtil.tinyWait();
                break;
            }
        }
        getAction().click(getNextButton());
        WaitUtil.tinyWait();
        return currentPage;
    }

    /**
     * Go to page.
     * @param page the page
     * @param swipe the swipe
     */
    public void goToPage(final HelpPage page, final boolean swipe) {

        getAction().click(getNextButton());
        WaitUtil.tinyWait();
        Assert.assertTrue(getCurrentPage().equals(page));
    }

    /**
     * Check swipe away menu help.
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#checkSwipeAwayMenuHelp()
     */
    @Override
    public void checkSwipeAwayMenuHelp() {

        setLogString("1. Check Away Help is displayed.", true);
        Assert.assertTrue(isDisplayed(getDriver(),
                By.cssSelector(IMG_SRC + AWAY_IMG + CLOSE_BRACKET), TINY_TIMEOUT));
        setLogString("2. Check Menu Help is displayed.", true);
        Assert.assertTrue(isDisplayed(getDriver(),
                By.cssSelector(IMG_SRC + MENU_IMG + CLOSE_BRACKET), TINY_TIMEOUT));
        setLogString("3. Check Swipe Help is displayed.", true);
        final Object value = executeScriptByClassName("slide_2", "background-image", getDriver());
        Assert.assertTrue(value.toString().contains(SWIPE_IMG));
        setLogString("4. Check Savings Help is displayed.", true);
        Assert.assertTrue(isDisplayed(getDriver(),
                By.cssSelector(IMG_SRC + SAVINGS_IMG + CLOSE_BRACKET), TINY_TIMEOUT));
    }

    /**
     * Swipe direction.
     * @param direction the direction
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#swipeDirection(java.lang.String)
     */
    @Override
    public void swipeDirection(final String direction) {

        final WebElement element = getSwipeContainer();
        if (direction.equalsIgnoreCase(RIGHT)) {
            getAction().doSwipeRight(element, element.getLocation().getX(),
                    element.getLocation().getY(), element.getSize().getWidth() / 2, 0.1);
        } else {
            getAction().doSwipeLeft(element, element.getLocation().getX(),
                    element.getLocation().getY(), element.getSize().getWidth() / 2, 0.1);
        }
    }

    /**
     * Close help overlay.
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#closeHelpOverlay()
     */
    @Override
    public void goToThermostatPage() {

        WebElement next = null;
        WebElement getStarted = null;
        while ((next = getNextButton()) != null || (getStarted = getStartedButton()) != null) {

            if (next != null) {
                setLogString("Click Next.", true);
                getAction().click(next);
                next = null;

            } else {
                setLogString("Click get started.", true);
                getAction().click(getStarted);
            }
        }
    }

    /**
     * @param page
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#isSlideLoaded(com.ecofactor.qa.automation.newapp.enums.HelpPage)
     */
    @Override
    public boolean isSlideLoaded(final HelpPage page, final String image) {

        Object bimage = null;
        final WebElement targetElement = getElement(getDriver(),
                By.cssSelector("div.help_slide_img"), TINY_TIMEOUT);
        bimage = targetElement.getAttribute("style");
        return bimage.toString().contains(image);
    }

    /**
     * Loads the previous page on the help overlay.
     * @param page the page to load
     * @param swipeStatus the swipe
     */
    @Override
    public void loadPreviousPage(final int pageIndicator, final boolean swipeStatus) {

        switch (pageIndicator) {
        case 1:
            goToPage(HelpPage.DRAG_TARGET_HELP, swipeStatus);
            break;
        case 2:
            goToPage(HelpPage.ECOPILOT_HELP, swipeStatus);
            break;
        case 3:
            goToPage(HelpPage.SWIPE_THERMOSTAT_HELP, swipeStatus);
            break;
        default:
            break;
        }

    }

    /**
     * Reach the first page of helpOverLay page.
     * @return true, if drag and drop image displayed.
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#helpOverlayPageOne()
     */
    @Override
    public boolean helpOverlayPageOne() {

        setLogString("Verify the drag and drop image display.", true);
        Assert.assertTrue(isDisplayed(getDriver(), By.cssSelector(FIRSTIMAGE_HELP), TINY_TIMEOUT));
        return true;
    }

    /**
     * Verify it reaches the second page of help overlay.
     * @return true, if savings energy page display.
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#helpOverlayPageTwo()
     */
    @Override
    public boolean helpOverlayPageTwo() {

        boolean helpPageOne = helpOverlayPageOne();

        setLogString("Verify the Energy savings page display.", true);
        if (helpPageOne == true) {

            WebElement next = getNextButton();
            getAction().click(next);
            WaitUtil.oneSec();
            getAction().rejectAlert();

            // Assert.assertTrue(isDisplayed(getDriver(), By.cssSelector(HELP_SLIDE),
            // TINY_TIMEOUT));
        }
        return true;
    }

    /**
     * verify it reaches the third page of help overlay page.
     * @return true, if switch thermostat page loaded.
     * @see com.ecofactor.qa.automation.newapp.page.HelpOverlayPage#helpOverlayPageThree()
     */
    @Override
    public boolean helpOverlayPageThree() {

        setLogString("Verify switch thermostat page display.", true);
        WebElement next = getNextButton();
        getAction().click(next);
        WaitUtil.oneSec();
        getAction().rejectAlert();

        return true;
    }
}
