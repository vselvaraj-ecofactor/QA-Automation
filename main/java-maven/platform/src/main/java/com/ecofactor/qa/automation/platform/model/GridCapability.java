/*
 * GridCapability.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.model;

import com.ecofactor.qa.automation.platform.enums.DesktopOSType;

/**
 * The Class GridCapability.
 * This is a model for creating Grid capabilities. The capabilities will be used
 * by the Appium node module to register with Selenium Grid Hub.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class GridCapability {

    private String browserName;    
    private String version;    
    private int maxInstances;    
    private DesktopOSType platform;

    /**
     * Gets the browser name.
     * @return the browser name
     */
    public String getBrowserName() {
    
        return browserName;
    }

    /**
     * Sets the browser name.
     * @param browserName the new browser name
     */
    public void setBrowserName(final String browserName) {
    
        this.browserName = browserName;
    }

    /**
     * Gets the version.
     * @return the version
     */
    public String getVersion() {
    
        return version;
    }

    /**
     * Sets the version.
     * @param version the new version
     */
    public void setVersion(final String version) {
    
        this.version = version;
    }

    /**
     * Gets the max instances.
     * @return the max instances
     */
    public int getMaxInstances() {
    
        return maxInstances;
    }

    /**
     * Sets the max instances.
     * @param maxInstances the new max instances
     */
    public void setMaxInstances(final int maxInstances) {
    
        this.maxInstances = maxInstances;
    }

    /**
     * Gets the platform.
     * @return the platform
     */
    public DesktopOSType getPlatform() {
    
        return platform;
    }

    /**
     * Sets the platform.
     * @param platform the new platform
     */
    public void setPlatform(final DesktopOSType platform) {
    
        this.platform = platform;
    }
}
