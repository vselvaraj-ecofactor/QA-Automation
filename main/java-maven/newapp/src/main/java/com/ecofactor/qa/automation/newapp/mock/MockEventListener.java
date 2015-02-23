/*
 * MockEventListener.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.mock;

import java.util.EventListener;

import com.ecofactor.qa.automation.util.mock.MockEvent;

/**
 * The listener interface for receiving mockEvent events. The class that is interested in processing
 * a mockEvent event implements this interface, and the object created with that class is registered
 * with a component using the component's <code>addMockEventListener<code> method. When
 * the mockEvent event occurs, that object's appropriate
 * method is invoked.
 * @see MockEventEvent
 */
public interface MockEventListener extends EventListener {

    /**
     * Handle mock event.
     * @param me the me
     */
    public void handleMockEvent(MockEvent me);
}
