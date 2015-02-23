/*
 * MockConsumerImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.mock;

import java.util.ArrayList;
import java.util.List;

import com.ecofactor.qa.automation.util.mock.MockEvent;

/**
 * The Class MockConsumerImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MockConsumerImpl implements MockConsumer {

    private List<MockEventListener> listeners = new ArrayList<MockEventListener>();

    /**
     * Adds the event listener.
     * @param listener the listener
     * @see com.ecofactor.qa.automation.consumer.page.MockConsumer#addEventListener(com.ecofactor.qa.automation.consumer.page.MockEventListener)
     */
    @Override
    public void addEventListener(MockEventListener listener) {

        listeners.add(listener);
    }

    /**
     * Removes the event listener.
     * @param listener the listener
     * @see com.ecofactor.qa.automation.consumer.page.MockConsumer#removeEventListener(com.ecofactor.qa.automation.consumer.page.MockEventListener)
     */
    @Override
    public void removeEventListener(MockEventListener listener) {

        listeners.remove(listener);
    }

    /**
     * Fire event.
     * @param event the event
     * @see com.ecofactor.qa.automation.consumer.page.MockConsumer#fireEvent(com.ecofactor.qa.automation.util.mock.MockEvent)
     */
    @Override
    public void fireEvent(MockEvent event) {

        for(MockEventListener listener : listeners) {
            listener.handleMockEvent(event);
        }
    }
}
