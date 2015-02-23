/*
 * HelpPage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.enums;

/**
 * The Enum HelpPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public enum HelpPage {

    DRAG_TARGET_HELP(0), ECOPILOT_HELP(1), SWIPE_THERMOSTAT_HELP(2);

    private static final String PAGE_CLASS = "slide_";

    /**
     * The value.
     */
    private final int value;

    /**
     * Instantiates a new custom timeout.
     * @param value the value
     */
    private HelpPage(final int value) {

        this.value = value;
    }

    /**
     * Gets the value.
     * @return the value
     */
    public int getValue() {

        return value;
    }

    /**
     * Gets the indicator.
     * @return the indicator
     */
    public String getIndicator() {

        return PAGE_CLASS + getValue();
    }
}