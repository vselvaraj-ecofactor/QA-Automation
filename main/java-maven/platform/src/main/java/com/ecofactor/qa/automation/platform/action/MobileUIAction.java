/*
 * MobileUIAction.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.action;

/**
 * The Interface MobileUIAction.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface MobileUIAction extends UIAction {

    /**
     * Switch to web view.
     */
    void switchToWebView();

    /**
     * Switch to native.
     */
    void switchToNative();
}
