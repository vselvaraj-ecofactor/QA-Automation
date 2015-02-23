/*
 * DRApiConfig.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi.dr;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class DRApiConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DRApiConfig extends BaseConfig {

    public static final String BASE_URL = "api.base.url";
    public static final String URL = "create.dr.event.url";
    public static final String GET_PROGRAM_ID = "ls.program.id";
    public static final String GET_CMS_PROGRAM_ID="ls.cms.program.id";
    public static final String GET_LOCATION_ID = "comsast.location.id";
    public static final String EVENT_ID = "nve.partner.event";
    public static final String EVENT_NAME = "dr.event.name";
    public static final String GET_NVE_PROGRAM_ID="ls.nve.program.id";
    public static final String TARGET_TYPE = "target.type.gateways";
    public static final String TARGET_TYPE_LOC = "target.type.locations";
    public static final String EVENT_ID_ECO = "efcorp.partner.event";
    public static final String EVENT_NAME_ECO = "dr.event.name.id";
    public static final String GET_ECO_PROGRAM_ID="ls.eco.program.id";
    public static final String EVENT_ID_CMS = "cmcs.partner.event";
    public static final String EVENT_NAME_CMS = "dr.event.name.cmcs.id";
    public static final String EVENT_NAME_DEFAULT = "efc.partnet.event";
    public static final String EVENT_NAME_DEFAULT_ID = "dr.event.name.ec.id";
    public static final String GET_EFC_PROGRAM_ID="ls.efc.program.id";
    public static final String TARGET_ALL_JSON = "create.dr.target.all.json";
    public static final String TARGET_LOC_JSON = "create.dr.target.loc.json";   

    public static final String BLANK_PROGRAMID = "ls.blank.program.id";
    public static final String NON_EXISTING_PROGRAMID = "ls.nonexisting.program.id";
    public static final String JUNK_PROGRAM_ID = "ls.junk.program.id";
    public static final String WITHOUT_EVENT_ID = "ls.no.event.id";
    public static final String WITHOUT_PARTNER_CODE = "ls.no.partner.code";
    public static final String EXISTING_START_TIME="existing.start.time";
    public static final String EXISTING_END_TIME="existing.end.time";
    public static final String MAX_EVENTS="ls.program.max.event";
    public static final String GET_MAX_PROGRAM_ID="max.program.id";
    public static final String MIN_START_HOUR="minimum.start.hr";
    public static final String MAX_END_HOUR="maximum.end.hr";
    public static final String EVENT_END_DATE="event.end.date";
    public static final String GET_EVENT_PROGRAM_ID="event.ls.program.id";

    /**
     * Instantiates a new api config.
     * @param driverConfig the driver config
     */
    @Inject
    public DRApiConfig(final DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("drApi.properties");
    }

}
