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
public enum SetAwayParams {

    Heating(0), Cooling(1), Days(2), Hours(3);

    /**
     * The value.
     */
    private final int params;

    /**
     * Instantiates a new custom timeout.
     * @param value the value
     */
    private SetAwayParams(final int value) {

        this.params = value;
    }

    /**
     * Gets the value.
     * @return the value
     */
    public int getValue() {

        return params;
    }

}