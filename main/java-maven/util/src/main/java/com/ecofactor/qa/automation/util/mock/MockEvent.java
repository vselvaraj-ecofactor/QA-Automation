/*
 * MockEvent.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mock;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * MockEvent defines the type of event mocking and other required properties.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MockEvent extends EventObject {

    private static final long serialVersionUID = -77824176692436743L;
    private Map<String, Object> properties = new HashMap<String, Object>();
    private MockEventType type;

    /**
     * Instantiates a new mock event.
     * @param source the source
     */
    public MockEvent(Object source) {

        super(source);
    }

    /**
     * Instantiates a new mock event.
     * @param source the source
     * @param type the type
     */
    public MockEvent(Object source, MockEventType type) {

        super(source);
        this.type = type;
    }

    /**
     * Gets the property.
     * @param name the name
     * @return the property
     */
    public Object getProperty(String name) {

        return properties.get(name);
    }

    /**
     * Sets the property.
     * @param name the name
     * @param value the value
     */
    public void setProperty(String name, Object value) {

        properties.put(name, value);
    }

    /**
     * Gets the type.
     * @return the type
     */
    public MockEventType getType() {

        return type;
    }

    /**
     * Sets the type.
     * @param type the new type
     */
    public void setType(MockEventType type) {

        this.type = type;
    }
}
