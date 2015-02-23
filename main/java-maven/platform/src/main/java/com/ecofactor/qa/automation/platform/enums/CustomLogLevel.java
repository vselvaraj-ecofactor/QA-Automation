/*
 * CustomLogLevel.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.enums;

/**
 * The Enum CustomLogLevel.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public enum CustomLogLevel {

    LOW(0), MEDIUM(1), HIGH(2);

    /**
     * The value.
     */
    private final int value;

    /**
     * Instantiates a new custom log level.
     * @param value the value
     */
    private CustomLogLevel(final int value) {

        this.value = value;
    }

    /**
     * Gets the value.
     * @return the value
     */
    public int getValue() {

        return value;
    }
}
