/*
 * ErrorMessage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi.pojo;

/**
 * The Class ErrorMessage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ErrorMessage {

    private String msg;
    private int code;

    /**
     * Gets the msg.
     * @return the msg
     */
    public String getMsg() {

        return msg;
    }

    /**
     * Sets the msg.
     * @param msg the new msg
     */
    public void setMsg(String msg) {

        this.msg = msg;
    }

    /**
     * Gets the code.
     * @return the code
     */
    public int getCode() {

        return code;
    }

    /**
     * Sets the code.
     * @param code the new code
     */
    public void setCode(int code) {

        this.code = code;
    }

}
