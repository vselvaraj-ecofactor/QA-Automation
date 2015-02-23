/*
 * CustomTimeout.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.enums;

/**
 * The Enum CustomTimeout.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public enum CustomTimeout {

    ATOMIC_TIMEOUT(1), THREE_SEC(3), TINY_TIMEOUT(10), SHORT_TIMEOUT(30), MEDIUM_TIMEOUT(60), LONG_TIMEOUT(
            120), VERY_LONG_TIMEOUT(300);

    /**
     * The value.
     */
    private final int value;

    /**
     * Instantiates a new custom timeout.
     * @param value the value
     */
    private CustomTimeout(final int value) {

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
