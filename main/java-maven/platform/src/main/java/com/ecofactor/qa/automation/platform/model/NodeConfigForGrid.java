package com.ecofactor.qa.automation.platform.model;

import java.util.List;

/**
 * The Class NodeConfigForGrid.
 * This is the Node configuration model object used by appium for the Grid.
 * When Appium starts, this model can to be supplied as a JSON file which will then
 * be used to register to the Selenium Grid Hub.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class NodeConfigForGrid {

    private List<GridCapability> capabilities;
    private GridConfiguration configuration;

    /**
     * Gets the capabilities.
     * @return the capabilities
     */
    public List<GridCapability> getCapabilities() {

        return capabilities;
    }

    /**
     * Sets the capabilities.
     * @param capabilities the new capabilities
     */
    public void setCapabilities(final List<GridCapability> capabilities) {

        this.capabilities = capabilities;
    }

    /**
     * Gets the configuration.
     * @return the configuration
     */
    public GridConfiguration getConfiguration() {

        return configuration;
    }

    /**
     * Sets the configuration.
     * @param configuration the new configuration
     */
    public void setConfiguration(final GridConfiguration configuration) {

        this.configuration = configuration;
    }
}
