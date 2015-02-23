/*
 * MockConsumer.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.mock;

import com.ecofactor.qa.automation.util.mock.MockEvent;


/**
 * The Interface MockConsumer.
 * @author $Author: asaravana $
 * @version $Rev: 20078 $ $Date: 2013-06-24 17:34:46 +0530 (Mon, 24 Jun 2013) $
 */
public interface MockConsumer {

    /**
     * Adds the event listener.
     * @param listener the listener
     */
    public void addEventListener(MockEventListener listener);

    /**
     * Removes the event listener.
     * @param listener the listener
     */
    public void removeEventListener(MockEventListener listener);

    /**
     * Fire event.
     * @param event the event
     */
    public void fireEvent(MockEvent event);
}
