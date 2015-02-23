/*
 * DRAPIDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi.data;

import static com.ecofactor.qa.automation.drapi.DRApiConfig.*;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.drapi.DRApiConfig;
import com.google.inject.Inject;

/**
 * The Class DRAPIDataProvider.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DRAPIDataProvider {

    /** The api config. */
    @Inject
    private static DRApiConfig apiConfig;

    /**
     * Creates the dr all gateways.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRAllGatewaysNVE")
    public static Object[][] createDRAllGateways() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Creates the dr gateways.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRGatewaysNVE")
    public static Object[][] createDRGateways() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_GATEWAY_JSON) } };
    }

    /**
     * Cancel dr event nve.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelDREventNVE")
    public static Object[][] cancelDREventNVE() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_DR_JSON) } };
    }

    /**
     * Creates the drall gateways eco.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRALLGatewaysECO")
    public static Object[][] createDRALLGatewaysECO() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_ECO_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Creates the execution drall gateways eco.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createExecutionDRALLGatewaysECO")
    public static Object[][] createExecutionDRALLGatewaysECO() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_ECO_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(THERMOSTAT_ID) } };
    }

    /**
     * Creates the execution drall gateways comcast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createExecutionDRALLGatewaysComcast")
    public static Object[][] createExecutionDRALLGatewaysComcast() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":"
                        + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_LOCATIONS),
                apiConfig.get(TARGET_LOCATONS_JSON),
                apiConfig.get(LOCATION_ID),
                apiConfig.get(THERMOSTAT_ID_COMCAST) } };
    }

    /**
     * Creates the dr gateways eco.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRGatewaysECO")
    public static Object[][] createDRGatewaysECO() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_ECO_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Cancel dr event eco.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelDREventECO")
    public static Object[][] cancelDREventECO() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_ECO_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_DR_JSON) } };
    }

    /**
     * Creates the drall gateways com cast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRALLGatewaysComCAST")
    public static Object[][] createDRALLGatewaysComCAST() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":"
                        + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_GATEWAY),
                apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Creates the dr gateways com cast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRGatewaysComCAST")
    public static Object[][] createDRGatewaysComCAST() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":"
                        + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_GATEWAY),
                apiConfig.get(TARGET_GATEWAY_JSON) } };
    }

    /**
     * Creates the drall locations com cast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRLocationComCAST")
    public static Object[][] createDRALLLocationsComCAST() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":"
                        + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_LOCATIONS),
                apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Creates the dr execution com cast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRExecutionComCAST")
    public static Object[][] createDRExecutionComCAST() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":"
                        + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_LOCATIONS),
                apiConfig.get(TARGET_LOCATONS_JSON), apiConfig.get(LOCATION_ID) } };
    }

    /**
     * Cancel dr event com cast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelDREventComCAST")
    public static Object[][] cancelDREventComCAST() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":"
                        + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_LOCATIONS),
                apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_DR_JSON) } };
    }

    /**
     * Creates the drall gateways default.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRALLGatewaysDefault")
    public static Object[][] createDRALLGatewaysDefault() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL_EVENT),
                apiConfig.get(GET_EFC_PROGRAM_ID), apiConfig.get(TARGET_TYPE),
                apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Creates the dr gateways default.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "createDRGatewaysDefault")
    public static Object[][] createDRGatewaysDefault() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL_EVENT),
                apiConfig.get(GET_EFC_PROGRAM_ID), apiConfig.get(TARGET_TYPE),
                apiConfig.get(TARGET_GATEWAY_JSON) } };
    }

    /**
     * Cancel dr event default.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelDREventDefault")
    public static Object[][] cancelDREventDefault() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL_EVENT),
                apiConfig.get(GET_EFC_PROGRAM_ID), apiConfig.get(TARGET_TYPE),
                apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_DR_JSON) } };
    }

    /**
     * Dr event for blank program id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventForBlankProgramID")
    public static Object[][] drEventForBlankProgramID() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(BLANK_PROGRAMID),
                apiConfig.get(WITHOUT_PARTNER_CODE) + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Dr event for non existing program id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventForNonExistingProgramID")
    public static Object[][] drEventForNonExistingProgramID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(NON_EXISTING_PROGRAMID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Dr event for junk program id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventForJunkProgramID")
    public static Object[][] drEventForJunkProgramID() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(JUNK_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Creates the drall gateways no event id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventforNoEventID")
    public static Object[][] createDRALLGatewaysNoEventID() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(WITHOUT_EVENT_ID),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * For no partner code.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventForNoPartnerCode")
    public static Object[][] forNoPartnerCode() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_PROGRAM_ID),
                apiConfig.get(WITHOUT_PARTNER_CODE) + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Dr event for existing event.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventForExistingEvent")
    public static Object[][] drEventForExistingEvent() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EXISTING_EVENT_ID), apiConfig.get(TARGET_TYPE),
                apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(EXISTING_START_TIME),
                apiConfig.get(EXISTING_END_TIME) } };
    }

    /**
     * Dr event for adding an event.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "drEventForAddingAnEvent")
    public static Object[][] drEventForAddingAnEvent() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_MAX_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(MAX_EVENTS), apiConfig.get(NUMBER_OF_EVENTS) } };
    }

    /**
     * Window outof program boundary.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "WindowOutofProgramBoundary")
    public static Object[][] WindowOutofProgramBoundary() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(TIME_OUT_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(MIN_START_HOUR), apiConfig.get(MAX_END_HOUR) } };
    }

    /**
     * Event expired.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "eventExpired")
    public static Object[][] eventExpired() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_EVENT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_JSON_EXPIRED),
                apiConfig.get(EXPIRED_EVENT_START_TIME),
                apiConfig.get(EXISTING_END_TIME) } };
    }

    /**
     * Event inactive.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "eventInactive")
    public static Object[][] eventInactive() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_STATUS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Additional braces commas.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "additionalBracesCommas")
    public static Object[][] additionalBracesCommas() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(ADDITIONAL_BRACES) } };
    }

    /**
     * Status as scheduled.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "statusAsScheduled")
    public static Object[][] statusAsScheduled() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    /**
     * Status as completed.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "statusAsCompleted")
    public static Object[][] statusAsCompleted() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_COMPLETED_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_COMLETED) } };
    }

    /**
     * Status as draft.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "statusAsDraft")
    public static Object[][] statusAsDraft() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_DRAFT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_DRAFT) } };
    }

    /**
     * Status as cancel.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "statusAsCancel")
    public static Object[][] statusAsCancel() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_CANCEL_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_CANCEL) } };
    }

    /**
     * Status as deleted.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "statusAsDeleted")
    public static Object[][] statusAsDeleted() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_CANCEL_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_CANCEL) } };
    }

    /**
     * Gateway id blank.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "gatewayIDBlank")
    public static Object[][] gatewayIDBlank() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_GATWAY_JSON), apiConfig.get(GATEWAY_ID) } };
    }

    /**
     * Not existing gateway id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "notExistingGatewayID")
    public static Object[][] notExistingGatewayID() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_GATWAY_JSON),
                apiConfig.get(NO_EXISTING_GATEWAY_ID) } };
    }

    /**
     * Junk gateway id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "junkGatewayID")
    public static Object[][] junkGatewayID() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_GATWAY_JSON),
                apiConfig.get(JUNK_GATEWAY_ID) } };
    }

    /**
     * Correct gateway id.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "correctGatewayID")
    public static Object[][] correctGatewayID() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(CORRECT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_GATWAY_JSON),
                apiConfig.get(CORRECT_GATEWAY_ID) } };
    }

    /**
     * Omit gateway block.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "omitGatewayBlock")
    public static Object[][] omitGatewayBlock() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(CORRECT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":"
                        + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(OMIT_GATEWAY_BLOCK) } };
    }

    /**
     * Disconnect gateway list nve.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "disconnectGatewayListNVE")
    public static Object[][] disconnectGatewayListNVE() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID) } };
    }

    /**
     * Disconnect gateway list eco.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "disconnectGatewayListEco")
    public static Object[][] disconnectGatewayListEco() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_ECO_PROGRAM_ID) } };
    }

    /**
     * Disconnect gateway list comcast.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "disconnectGatewayListComcast")
    public static Object[][] disconnectGatewayListComcast() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_CMS_PROGRAM_ID) } };
    }

    /**
     * Disconnect gateway list default.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "disconnectGatewayListDefault")
    public static Object[][] disconnectGatewayListDefault() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_EFC_PROGRAM_ID) } };
    }

    /**
     * Disconnect gateway junk program.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "disconnectGatewayJunkProgram")
    public static Object[][] disconnectGatewayJunkProgram() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(JUNK_PROGRAM_ID) } };
    }

    /**
     * Disconnect gateway blank program.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "disconnectGatewayBlankProgram")
    public static Object[][] disconnectGatewayBlankProgram() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(BLANK_PROGRAMID) } };
    }

    /**
     * Cancel already cancelled event.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelAlreadyCancelledEvent")
    public static Object[][] cancelAlreadyCancelledEvent() {

        return new Object[][] { { apiConfig.get(GET_EFC_PROGRAM_ID),
                apiConfig.get(CANCELLED_EVENT_ID),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_DR_JSON) } };
    }

    /**
     * Cancel non existing event.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelNonExistingEvent")
    public static Object[][] cancelNonExistingEvent() {

        return new Object[][] { { apiConfig.get(GET_EFC_PROGRAM_ID),
                apiConfig.get(NONEXISTTING_EVENT_ID),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_DR_JSON) } };
    }

    /**
     * Cancel bad json.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "cancelBadJson")
    public static Object[][] cancelBadJson() {

        return new Object[][] { {
                apiConfig.get(BASE_URL) + apiConfig.get(URL_EVENT),
                apiConfig.get(GET_EFC_PROGRAM_ID), apiConfig.get(TARGET_TYPE),
                apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(BASE_URL) + apiConfig.get(CANCEL_URL),
                apiConfig.get(CANCEL_BAD_JSON), apiConfig.get(CANCEL_DR_JSON) } };
    }

}
