/*
 * HTTPClientException.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi.dr;

/**
 * The Class HTTPClientException.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@SuppressWarnings("serial")
public class HTTPClientException extends Exception {

    /**
     * Instantiates a new HTTP client exception.
     */
    public HTTPClientException() {

        super();
    }

    /**
     * Instantiates a new HTTP client exception.
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public HTTPClientException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new HTTP client exception.
     * @param message the message
     * @param cause the cause
     */
    public HTTPClientException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Instantiates a new HTTP client exception.
     * @param message the message
     */
    public HTTPClientException(String message) {

        super(message);
    }

    /**
     * Instantiates a new HTTP client exception.
     * @param cause the cause
     */
    public HTTPClientException(Throwable cause) {

        super(cause);
    }

}
