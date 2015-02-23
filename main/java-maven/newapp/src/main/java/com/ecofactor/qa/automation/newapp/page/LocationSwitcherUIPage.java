/*
 * LocationSwitcherUIPage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import java.util.List;

import com.ecofactor.qa.automation.newapp.enums.TemperatureType;
import com.ecofactor.qa.automation.newapp.page.impl.LocationSwitcherUIPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface LocationSwitcherUIPage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = LocationSwitcherUIPageImpl.class)
public interface LocationSwitcherUIPage extends BasePage {

    /**
     * Gets the no of locations.
     * @return the no of locations
     */
    int getNoOfLocations();

    /**
     * Gets the location names.
     * @return the location names
     */
    List<String> getLocationNames();

    /**
     * Gets the tstat name by id.
     * @param tstatId the tstat id
     * @return the tstat name by id
     */
    String getTstatNameById(Integer tstatId);

    /**
     * Gets the temp by tstat id.
     * @param tstatId the tstat id
     * @param tempType the temp type
     * @return the temp by tstat id
     */
    String getTempByTstatId(Integer tstatId, TemperatureType tempType);

    /**
     * Gets the tstat namesin location.
     * @param locId the loc id
     * @return the tstat namesin location
     */
    List<String> getTstatNamesinLocation(Integer locId);

    /**
     * Gets the tstat id for location.
     * @param locId the loc id
     * @return the tstat id for location
     */
    List<Integer> getTstatIdForLocation(Integer locId);

    /**
     * Gets the mode by tstat id.
     * @param tstatId the tstat id
     * @return the mode by tstat id
     */
    String getModeByTstatId(Integer tstatId);

    /**
     * Gets the text color.
     * @return the text color
     */
    String getTextColor();

    /**
     * Gets the location opacity value.
     * @return the location opacity value
     */
    Double getLocationOpacityValue();

    /**
     * Gets the rangeof temperature.
     * @param tstatId the tstat id
     * @return the rangeof temperature
     */
    String getRangeofTemperature(Integer tstatId);

}
