/*
 * Error.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi.pojo;

import java.util.List;

/**
 * The Class Error.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class Error {

    private List<ErrorMessage> errors;

    /**
     * Gets the errors.
     * @return the errors
     */
    public List<ErrorMessage> getErrors() {

        return errors;
    }

    /**
     * Sets the errors.
     * @param errors the new errors
     */
    public void setErrors(List<ErrorMessage> errors) {

        this.errors = errors;
    }
}
