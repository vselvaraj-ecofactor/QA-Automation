/*
 * MenuPageImpl.java
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
import static com.ecofactor.qa.automation.platform.util.Pageutil.getElement;
import static com.ecofactor.qa.automation.platform.util.Pageutil.isDisplayed;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuPageImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 33071 $ $Date: 2014-12-12 17:11:36 +0530 (Fri, 12 Dec 2014) $
 */
public class MenuPageImpl extends AbstractBasePageImpl implements MenuPage {

    /** The Constant MENU_ICON. */
    private static final String MENU_ICON = "div.menuClick";

    /** The Constant MENU_PAGE_ICON. */
    private static final String MENU_PAGE_ICON = "off_menu_blocker";

    /** The Constant THERMOSTAT_MENU_ITEM. */
    private static final String THERMOSTAT_MENU_ITEM = "div.menu_row.thermostat";

    /** The Constant THERMOSTAT_HIGHLIGHT. */
    private static final String THERMOSTAT_HIGHLIGHT = "div.menu_row.header";

    /** The Constant AWAY_TEMPERATURE_MENU_ITEM. */
    private static final String AWAY_TEMPERATURE_MENU_ITEM = ".menu_row.settings";

    /** The Constant AWAY_TEMPERATURE_HIGHLIGHTED. */
    private static final String AWAY_TEMPERATURE_HIGHLIGHTED = "div.menu_row.settings.highlighted";

    /** The Constant LOGOUT_MENU_ITEM. */
    private static final String LOGOUT_MENU_ITEM = "div.menu_row.logout";

    /** The Constant MENU_HEADER. */
    private static final String MENU_HEADER = ".menu_row.header";

    /** The Constant MENU_CONTAINER. */
    private static final String MENU_CONTAINER = ".menu_container";

    /** The Constant MENU_CLOSED. */
    @SuppressWarnings("unused")
    private static final String MENU_CLOSED = "div.menu_container.closed";

    /** The Constant FOOTER. */
    private static final String FOOTER = "away_container";

    /** The Constant SAVINGS_LABEL. */
    private static final String SAVINGS_LABEL = "div.menu_row.savings";

    /** The Constant SAVINGS_NAME. */
    private static final String SAVINGS_NAME = "div.label";

    /** The Constant LOGGER. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuPageImpl.class);

    /** The Constant RIGHT. */
    private static final String RIGHT = "right";

    /** The mobile config. */
    @Inject
    protected MobileConfig mobileConfig;

    /**
     * Click the menu icon from the thermostat page.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickMenuIcon()
     */
    @Override
    public void clickMenuIcon() {

        getAction().rejectAlert();
        setLogString("Check Menu Icon is Displayed", true);
        isDisplayed(getDriver(), By.cssSelector(MENU_ICON), SHORT_TIMEOUT);
        setLogString("Click Menu Icon in Slice", true);
        getAction().rejectAlert();
        final WebElement menuIcon = getElement(getDriver(), By.cssSelector(MENU_ICON),
                SHORT_TIMEOUT);
        menuIcon.click();
        // getAction().click(menuIcon);
        WaitUtil.oneSec();
        getAction().rejectAlert();

    }

    /**
     * Click the thermostat menu item from the menu page.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickThermostatMenuItem()
     */
    @Override
    public void clickThermostatMenuItem() {

        WaitUtil.tinyWait();
        setLogString("Check Thermostat Menu Item is Displayed", true);
        final boolean state = isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_MENU_ITEM),
                TINY_TIMEOUT);
        if (state) {           
            
            final WebElement menuIcon = getElement(getDriver(),
                    By.cssSelector(THERMOSTAT_MENU_ITEM), TINY_TIMEOUT);           
            menuIcon.click();
            setLogString("Thermostat Menu Item is Clicked", true);
            // getAction().click(menuIcon);
            getAction().rejectAlert();
        } else {

            setLogString("Thermostat Menu Item is Not Displayed", true);
        }
    }

    /**
     * Click the logout menu item from the menu page.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickLogoutMenuItem()
     */
    @Override
    public void clickLogoutMenuItem() {

        WaitUtil.tinyWait();
        getAction().rejectAlert();
        setLogString("Check Logout Menu Item is Displayed", true);
        final boolean state = isDisplayed(getDriver(), By.cssSelector(LOGOUT_MENU_ITEM),
                TINY_TIMEOUT);
        WaitUtil.tinyWait();
        if (state) {
            setLogString("Logout Menu Item is Clicked", true);
            final WebElement menuIcon = getElement(getDriver(), By.cssSelector(LOGOUT_MENU_ITEM),
                    SHORT_TIMEOUT);
            menuIcon.click();
            WaitUtil.tinyWait();
        } else {
            setLogString("Logout Menu Item is Not Displayed", true);
        }

    }

    /**
     * Click the menu icon from the menu page.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickMenuIconOnMenuPage()
     */
    @Override
    public void clickMenuIconOnMenuPage() {

        setLogString("Check Menu Icon is Displayed", true);
        isDisplayed(getDriver(), By.className(MENU_PAGE_ICON), TINY_TIMEOUT);
        setLogString("Click Menu Icon", true);
        final WebElement menuIcon = getElement(getDriver(), By.className(MENU_PAGE_ICON),
                TINY_TIMEOUT);
        menuIcon.click();
        // getAction().click(menuIcon);
        getAction().rejectAlert();

    }

    /**
     * Click the away temperature menu item from the menu page.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickAwayTemperature()
     */
    @Override
    public void clickAwayTemperature() {

        WaitUtil.tinyWait();
        setLogString("Check Away Temperature Menu Item is Displayed", true);
        final boolean state = isDisplayed(getDriver(), By.cssSelector(AWAY_TEMPERATURE_MENU_ITEM),
                TINY_TIMEOUT);
        if (state) {
            setLogString("Away Temperature Menu Item is Clicked", true);
            getAction().rejectAlert();
            final WebElement menuIcon = getElement(getDriver(),
                    By.cssSelector(AWAY_TEMPERATURE_MENU_ITEM), TINY_TIMEOUT);           
           // setLogString("log" + menuIcon.getAttribute("class"), true);
            WaitUtil.oneSec();
            menuIcon.click();
            // getAction().click(menuIcon);
            getAction().rejectAlert();
        } else {
            setLogString("Away Temperature Menu Item is Not Displayed", true);
        }
    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        setLogString("Cleanup in Menu Page.", true, CustomLogLevel.HIGH);
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        setLogString("Verify the Menu Page is loaded", true, CustomLogLevel.MEDIUM);
        return isDisplayed(getDriver(), By.cssSelector(MENU_CONTAINER), TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_MENU_ITEM), TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.cssSelector(AWAY_TEMPERATURE_MENU_ITEM),
                        TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.cssSelector(LOGOUT_MENU_ITEM), TINY_TIMEOUT);
    }

    /**
     * Swipe page.
     * @param leftOrRight the left or right
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#swipePage(java.lang.String)
     */
    @Override
    public void swipePage(String leftOrRight) {

        WebElement thermostatContent = null;

        if (leftOrRight.equalsIgnoreCase(RIGHT)) {
            isDisplayed(getDriver(), By.cssSelector(MENU_HEADER), TINY_TIMEOUT);
            thermostatContent = getElement(getDriver(), By.cssSelector(MENU_HEADER), TINY_TIMEOUT);
            WaitUtil.oneSec();
            thermostatContent.click();
            // getAction().doSwipeRight(thermostatContent);

        } else {
            isDisplayed(getDriver(), By.className(MENU_PAGE_ICON), TINY_TIMEOUT);
            thermostatContent = getElement(getDriver(), By.className(MENU_PAGE_ICON), TINY_TIMEOUT);
            getAction().click(thermostatContent);
            getAction().rejectAlert();
        }
    }

    /**
     * Checks if is footer slice displayed.
     * @return true, if is footer slice displayed
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#isFooterSliceDisplayed()
     */
    public boolean isFooterSliceDisplayed() {

        return isDisplayed(getDriver(), By.className(FOOTER), TINY_TIMEOUT);
    }

    /**
     * Click slice.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickFooterSlice()
     */
    public void clickSlice() {

        setLogString("Click Slice Area", true, CustomLogLevel.LOW);
        final WebElement menu_container = getElement(getDriver(), By.className("menu_container"),
                TINY_TIMEOUT);
        final WebElement footerElement = menu_container.findElement(By
                .className("off_menu_blocker"));
        getAction().click(footerElement);
        getAction().rejectAlert();
    }

    /**
     * Checks if is savings label displayed.
     * @return true, if is savings label displayed
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#isSavingsLabelDisplayed()
     */
    @Override
    public boolean isSavingsLabelDisplayed() {

        setLogString("Check Savings label is displayed", true, CustomLogLevel.HIGH);
        return isDisplayed(getDriver(), By.cssSelector(SAVINGS_NAME), TINY_TIMEOUT);
    }

    /**
     * Click savings.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickSavings()
     */
    @Override
    public void clickSavings() {

        setLogString("Click Savings in Menu page", true, CustomLogLevel.HIGH);
        final WebElement savings = getElement(getDriver(), By.cssSelector(SAVINGS_LABEL),
                TINY_TIMEOUT);
        getAction().click(savings);
    }

    /**
     * click thermostat when it highlighted.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickThermostatHighlighted()
     */
    @Override
    public void clickThermostatHighlighted() {

        WaitUtil.smallWait();
        setLogString("Check Thermostat Menu Item is Displayed", true);
        isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_HIGHLIGHT), TINY_TIMEOUT);
        // System.out.println(stateEnable + "stateEnable");
        setLogString("Thermostat Menu Item is Clicked", true);
        final WebElement menuIcon = getElement(getDriver(), By.cssSelector(THERMOSTAT_HIGHLIGHT),
                TINY_TIMEOUT);
        WaitUtil.oneSec();
        menuIcon.click();
        // getAction().click(menuIcon);
        WaitUtil.oneSec();
        getAction().rejectAlert();
    }

    /**
     * Click menu container.
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickMenuContainer()
     */
    @Override
    public void clickMenuContainer() {

        final WebElement containerEle = getElement(getDriver(), By.cssSelector(MENU_CONTAINER),
                TINY_TIMEOUT);
        WaitUtil.oneSec();
        containerEle.click();
        getAction().rejectAlert();

    }

    /**
     * @see com.ecofactor.qa.automation.newapp.page.MenuPage#clickAwayHiglighted()
     */
    @Override
    public void clickAwayHiglighted() {

        WaitUtil.tinyWait();
        setLogString("Check Away Temperature Menu item Highlighted is Displayed", true);
        final boolean state = isDisplayed(getDriver(),
                By.cssSelector(AWAY_TEMPERATURE_HIGHLIGHTED), TINY_TIMEOUT);
        if (state) {
            setLogString("Away Temperature Menu Highlighted Item is Clicked", true);
            final WebElement menuIcon = getElement(getDriver(),
                    By.cssSelector(AWAY_TEMPERATURE_HIGHLIGHTED), TINY_TIMEOUT);
            WaitUtil.oneSec();
            menuIcon.click();
            // getAction().click(menuIcon);
            getAction().rejectAlert();
        } else {
            setLogString("Away Temperature Menu Item is Not Displayed", true);
        }

    }
}
