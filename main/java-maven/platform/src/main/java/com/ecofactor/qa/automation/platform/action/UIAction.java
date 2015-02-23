/*
 * UIAction.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.action;

import org.openqa.selenium.WebElement;

/**
 * The Interface UIAction.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface UIAction {

    /**
     * Do swipe left.
     * @param element the element
     */
    void doSwipeLeft(final WebElement element);

    /**
     * Do swipe right.
     * @param element the element
     */
    void doSwipeRight(final WebElement element);

    int getWindowWidth(final WebElement element);
    
    int getWindowHeight(final WebElement element);
    /**
     * Do swipe left.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     */
    void doSwipeLeft(final WebElement element, final double xCoordinate, final double yCoordinate,
            final double distance, final double speed);

    /**
     * Do swipe right.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     */
    void doSwipeRight(final WebElement element, final double xCoordinate, final double yCoordinate,
            final double distance, final double speed);

    /**
     * Do swipe up.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     */
    void doSwipeUp(final WebElement element, final double xCoordinate, final double yCoordinate,
            final double distance, final double speed);

    /**
     * Do swipe down.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     */
    void doSwipeDown(final WebElement element, final double xCoordinate, final double yCoordinate,
            final double distance, final double speed);

    /**
     * Drag element.
     * @param srcElement the src element
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    void dragElement(final WebElement srcElement, Integer xOffset, Integer yOffset);

    /**
     * Drop element.
     * @param srcElement the src element
     */
    void dropElement(final WebElement srcElement);

    /**
     * Click.
     * @param element the element
     */
    void click(final WebElement element);

    /**
     * Accept alert.
     */
    void acceptAlert();

    /**
     * Gets the custom screenshot folder.
     * @return the custom screenshot folder
     */
    String getCustomScreenshotFolder();

    /**
     * Sets the custom screenshot folder.
     * @param folderName the folder name
     * @return the string
     */
    void setCustomScreenshotFolder(String folderName);

    /**
     * Clear custom screenshot folder.
     */
    void clearCustomScreenshotFolder();

    /**
     * Do tap.
     * @param element the element
     */
    void doTap(final WebElement element);

    /**
     * Reject alert.
     */
    void rejectAlert();

    /**
     * APPS-8247
     * Click try again button that occurs after login success.
     */
    void clickTryAgain();
}
