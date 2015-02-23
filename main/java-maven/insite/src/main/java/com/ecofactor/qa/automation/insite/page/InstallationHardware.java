/*
 * InstallationHardware.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface InstallationHardware.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface InstallationHardware extends InsiteAuthenticatedPage {

    /**
     * Test installation stuff.
     * @param streetAddressValue the street address value
     */
    public void testInstallationStuff(final String streetAddressValue);

    /**
     * Check look up address.
     * @param streetAddressValue the street address value
     */
    public void checkLookUpAddress(final String streetAddressValue);

    /**
     * Save thermostat name.
     * @param thermostatName the thermostat name
     */
    public void saveThermostatName(String thermostatName);

    /**
     * Verify thermostat name.
     * @param thermostatName the thermostat name
     */
    public void verifyThermostatName(String thermostatName);

    /**
     * Adds the thermostat.
     * @param thermostatName the thermostat name
     */
    public void addThermostat(String thermostatName);

    /**
     * Removes the thermostat.
     */
    public String removeThermostat();

    /**
     * Verify no thermostat with name.
     * @param thermostatName the thermostat name
     */
    public void verifyNoThermostatWithName(String thermostatName);

}
