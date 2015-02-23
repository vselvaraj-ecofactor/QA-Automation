/*
 * HvacModes.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.enums;

/**
 * The Enum HvacModes.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public enum HvacModes {

    HEAT, COOL, OFF, IDLE;
    private static final String COOL_GRADIENT = "Cool";
    private static final String HEAT_GRADIENT = "Heat";
    private static final String OFF_GRADIENT = "rgb(109, 111, 112)";
    private static final String IDLE_GRADIENT = "";

    /**
     * Gets the gradient value.
     * @return the gradient value
     */
    public String getGradientValue() {

        switch (this) {

        case HEAT:
            return HEAT_GRADIENT;
        case COOL:
            return COOL_GRADIENT;
        case OFF:
            return OFF_GRADIENT;
        case IDLE:
            return IDLE_GRADIENT;
        default:
            return IDLE_GRADIENT;
        }
    }
}
