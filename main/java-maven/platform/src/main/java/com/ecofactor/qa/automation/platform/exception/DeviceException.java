/*
 * DeviceException.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.exception;

/**
 * The Class DeviceException.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@SuppressWarnings("serial")
public class DeviceException extends Exception{
	
	/**
     * The message.
     */
	private String message;

	/**
     * Instantiates a new device exception.
     * @param message the message
     */
	public DeviceException(final String message) {
		 super(message);
	     this.message = message;
	}

    /**
     * Instantiates a new device exception.
     * @param message the message
     */
    public DeviceException(final String message, final Throwable cause) {
         super(message, cause);
         this.message = message;
    }
    
	/**
     * Gets the message.
     * @return the message
     * @see java.lang.Throwable#getMessage()
     */
	public String getMessage() {
		return message;
	}

	/**
     * Sets the message.
     * @param message the new message
     */
	public void setMessage(final String message) {
		this.message = message;
	}
	
	
}
