/*
 * DRApiConfig.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi;

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
    public static final String CANCEL_URL = "cancel.dr.event.url";
    public static final String DISCONNECT_GATEWAY_URL = "disconnect.gateway.url";
    public static final String URL_EVENT = "create.dr.event.nopartner.url";

    public static final String GET_PROGRAM_ID = "ls.program.id";
    public static final String GET_CMS_PROGRAM_ID = "ls.cms.program.id";
    public static final String EVENT_ID = "nve.partner.event";
    public static final String EVENT_NAME = "dr.event.name";
    public static final String GET_NVE_PROGRAM_ID = "ls.nve.program.id";
    public static final String TARGET_TYPE = "target.type.gateways";
    public static final String TARGET_TYPE_GATEWAY = "target.type.gateways";
    public static final String TARGET_TYPE_LOCATIONS = "target.type.locations";
    public static final String EVENT_ID_ECO = "efcorp.partner.event";
    public static final String EVENT_NAME_ECO = "dr.event.name.id";
    public static final String GET_ECO_PROGRAM_ID = "ls.eco.program.id";
    public static final String EVENT_ID_CMS = "cmcs.partner.event";
    public static final String EVENT_NAME_CMS = "dr.event.name.cmcs.id";
    public static final String GET_EFC_PROGRAM_ID = "ls.efc.program.id";
    public static final String GET_GATEWAY_ID_EFC = "ls.efcorp.gateway.id";
    public static final String TARGET_ALL_JSON = "create.dr.target.all.json";
    public static final String CANCEL_DR_JSON = "cancel.dr.json";
    public static final String CANCEL_BAD_JSON = "cancel.bad.json";

    public static final String BLANK_PROGRAMID = "ls.blank.program.id";
    public static final String NON_EXISTING_PROGRAMID = "ls.nonexisting.program.id";
    public static final String JUNK_PROGRAM_ID = "ls.junk.program.id";
    public static final String WITHOUT_EVENT_ID = "ls.no.event.id";
    public static final String WITHOUT_PARTNER_CODE = "ls.no.partner.code";
    public static final String EXISTING_START_TIME = "existing.start.time";
    public static final String EXISTING_END_TIME = "existing.end.time";
    public static final String EXISTING_EVENT_ID = "existing.event.id";
    public static final String MAX_EVENTS = "ls.program.max.event";
    public static final String GET_MAX_PROGRAM_ID = "max.program.id";
    public static final String MIN_START_HOUR = "minimum.start.hr";
    public static final String MAX_END_HOUR = "maximum.end.hr";
    public static final String TIME_OUT_ID = "time.program.id";
    public static final String GET_EVENT_PROGRAM_ID = "event.ls.program.id";
    public static final String NUMBER_OF_EVENTS = "ls.no.of.events";
    public static final String GET_PROGRAM_STATUS = "program.status";
    public static final String GET_STATUS_PROGRAM_ID = "status.program.id";

    public static final String ADDITIONAL_BRACES = "create.dr.target.additional.json";
    public static final String STATUS_COMLETED = "create.dr.completed.all.json";
    public static final String TARGET_JSON_EXPIRED = "create.dr.target.all.json.expired";
    public static final String STATUS_COMPLETED_PROGRAM_ID = "ls.completed.program.id";
    public static final String STATUS_DRAFT = "create.dr.draft.all.json";
    public static final String STATUS_DRAFT_PROGRAM_ID = "ls.draft.program.id";
    public static final String STATUS_CANCEL = "create.dr.cancel.all.json";
    public static final String STATUS_CANCEL_PROGRAM_ID = "ls.cancel.program.id";
    public static final String STATUS_DELETE = "create.dr.delete.all.json";
    public static final String JSON_URL = "create.dr.gateway.url";
    public static final String TARGET_GATWAY_JSON = "create.dr.target.gateway.json";
    public static final String GATEWAY_ID = "json.gateway.id";
    public static final String NO_EXISTING_GATEWAY_ID = "notExisting.gatway.id";
    public static final String JUNK_GATEWAY_ID = "junk.gateway.id";
    public static final String CORRECT_GATEWAY_ID = "correct.gateway.id";
    public static final String CORRECT_GATEWAY_ID_NVE = "correct.nve.gateway.id";
    public static final String CORRECT_PROGRAM_ID = "correct.gateway.program.id";
    public static final String OMIT_GATEWAY_BLOCK = "create.dr.omit.gateway.json";
    public static final String CANCELLED_EVENT_ID = "cancelled.event.id";
    public static final String NONEXISTTING_EVENT_ID = "nonexisting.event.id";
    public static final String EXPIRED_EVENT_START_TIME = "expired.start.time";
    public static final String EXPIRED_EVENT_END_TIME = "expired.end.time";

    public static final String URL_ADD_LOCATION = "addlocation.dr.event.url";
    public static final String URL_ADD_GATEWAY = "addgateway.dr.event.url";
    public static final String URL_CANCEL_DR_EVENT = "cancel.dr.event.url";
    public static final String LOCATION_ID = "comcast.location.id";
    public static final String LOCATION_ID_NON_EXISTING = "non.existing.comcast.location.id";
    public static final String LOCATION_ID_ADD = "comcast.addlocation.id";
    public static final String LOCATION_ID_ADD_NVE = "nve.addlocation.id";
    public static final String ADD_LOCATION_MULTI = "create.dr.target.addmultiloc.json";
    public static final String ADD_GATEWAY_MULTI = "create.dr.target.addmultigat.json";
    public static final String ADD_LOCATION = "create.dr.target.addloc.json";
    public static final String TARGET_LOCATONS_JSON = "create.dr.target.loc.json";

    public static final String ADD_GATEWAY = "create.dr.target.addgat.json";
    public static final String COMCAST_GATEWAY_ID = "comcast.gateway.id";
    public static final String TARGET_GATEWAY_JSON = "create.dr.target.gateway.json";
    public static final String GATEWAY_ID_ADD = "correct.addgateway.id";
    public static final String GATEWAY_ID_ADD_NVE = "correct.nve.addgateway.id";
    public static final String TARGET_JSON_GAT = "create.dr.target.gat.json";

    public static final String TARGET_EVENT_URL = "create.dr.location.url";
    public static final String CANCEL_DR_EVENT = "cancel.dr.target.json";
    public static final String CANCEL_DR_EVENT_NVE = "cancel.dr.json";
    public static final String THERMOSTAT_ID = "thermostat.id.eco";
    public static final String THERMOSTAT_ID_COMCAST = "thermostat.id.com";
    public static final String LOCATION_ID_AWAY = "location.away.id.eco";
    public static final String LOCATION_ID_HEAT = "location.heat.id.eco";
    public static final String LOCATION_ID_DISCONNECTED = "location.disconnected.id.eco";
    public static final String TSTID_NVE_USER = "tstid.nve.user";

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