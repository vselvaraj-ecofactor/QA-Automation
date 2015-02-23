package com.ecofactor.qa.automation.platform.action.impl;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import org.openqa.selenium.WebElement;

/**
 * The Class AbstractMobileUIAction.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class AbstractMobileUIAction extends AbstractUIAction {

    /**
     * Swipe.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     * @param direction the direction
     */
    protected abstract void swipe(WebElement element, double xCoordinate, double yCoordinate,
            double distance, double speed, Direction direction);

    protected abstract void swipe(WebElement element, double xCoordinate, double yCoordinate,
            double speed, Direction direction);

    /**
     * Gets the end xCoordinate.
     * @param xCoordinate the xCoordinate
     * @param distance the distance
     * @param direction the direction
     * @return the end xCoordinate
     */
    protected double getEndX(final double xCoordinate, final double distance,
            final Direction direction) {

        switch (direction) {

        case LEFT:
            return xCoordinate + distance;

        case RIGHT:
            return xCoordinate - distance;

        default:
            return xCoordinate;
        }
    }

    /**
     * Gets the end yCoordinate.
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param direction the direction
     * @return the end yCoordinate
     */
    protected double getEndY(final double yCoordinate, final double distance,
            final Direction direction) {

        switch (direction) {

        case UP:
            return yCoordinate - distance;

        case DOWN:
            return yCoordinate + distance;

        default:
            return yCoordinate;
        }
    }

    /**
     * Do swipe left.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.MobileUIAction#doSwipeLeft(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeLeft(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        setLogString("Swipe Left", true);
        swipe(element, xCoordinate, yCoordinate, distance, speed, Direction.LEFT);
    }

    /**
     * Do swipe right.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.MobileUIAction#doSwipeRight(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeRight(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        setLogString("Swipe Right", true);
        swipe(element, xCoordinate, yCoordinate, distance, speed, Direction.RIGHT);
    }

}
