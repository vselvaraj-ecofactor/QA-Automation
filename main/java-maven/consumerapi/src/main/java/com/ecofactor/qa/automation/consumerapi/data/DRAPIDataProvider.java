/*
 * DRAPIDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi.data;

import static com.ecofactor.qa.automation.consumerapi.DRApiConfig.*;
import org.testng.annotations.DataProvider;
import com.google.inject.Inject;

/**
 * The Class DRAPIDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DRAPIDataProvider {

    @Inject
    private static com.ecofactor.qa.automation.consumerapi.DRApiConfig apiConfig;

    @DataProvider(name = "createDRAllGatewaysNVE")
    public static Object[][] createDRAllGateways() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "createDRALLGatewaysECO")
    public static Object[][] createDRALLGatewaysECO() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_ECO_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "createDRALLGatewaysComCAST")
    public static Object[][] createDRALLGatewaysComCAST() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":" + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_LOCATIONS), apiConfig.get(TARGET_ALL_JSON) } };
    }
    
    @DataProvider(name = "createDRALLLocationsComCAST")
    public static Object[][] createDRALLLocationsComCAST() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_CMS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_CMS) + ":" + apiConfig.get(EVENT_NAME_CMS),
                apiConfig.get(TARGET_TYPE_LOCATIONS), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "createDRALLGatewaysDefault")
    public static Object[][] createDRALLGatewaysDefault() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL_EVENT),
                apiConfig.get(GET_EFC_PROGRAM_ID), apiConfig.get(TARGET_TYPE),
                apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "drEventForBlankProgramID")
    public static Object[][] drEventForBlankProgramID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(BLANK_PROGRAMID),
                apiConfig.get(WITHOUT_PARTNER_CODE) + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "drEventForNonExistingProgramID")
    public static Object[][] drEventForNonExistingProgramID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(NON_EXISTING_PROGRAMID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "drEventForJunkProgramID")
    public static Object[][] drEventForJunkProgramID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(JUNK_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "drEventforNoEventID")
    public static Object[][] createDRALLGatewaysNoEventID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(WITHOUT_EVENT_ID),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "drEventForNoPartnerCode")
    public static Object[][] forNoPartnerCode() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_PROGRAM_ID),
                apiConfig.get(WITHOUT_PARTNER_CODE) + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "drEventForExistingEvent")
    public static Object[][] drEventForExistingEvent() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID), apiConfig.get(EXISTING_EVENT_ID),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(EXISTING_START_TIME), apiConfig.get(EXISTING_END_TIME) } };
    }

    @DataProvider(name = "drEventForAddingAnEvent")
    public static Object[][] drEventForAddingAnEvent() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_MAX_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(MAX_EVENTS), apiConfig.get(NUMBER_OF_EVENTS) } };
    }

    @DataProvider(name = "WindowOutofProgramBoundary")
    public static Object[][] WindowOutofProgramBoundary() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(TIME_OUT_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON),
                apiConfig.get(MIN_START_HOUR), apiConfig.get(MAX_END_HOUR) } };
    }

    @DataProvider(name = "eventExpired")
    public static Object[][] eventExpired() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_EVENT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "eventInactive")
    public static Object[][] eventInactive() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_STATUS_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "additionalBracesCommas")
    public static Object[][] additionalBracesCommas() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(ADDITIONAL_BRACES) } };
    }

    @DataProvider(name = "statusAsScheduled")
    public static Object[][] statusAsScheduled() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_ALL_JSON) } };
    }

    @DataProvider(name = "statusAsCompleted")
    public static Object[][] statusAsCompleted() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_COMPLETED_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_COMLETED) } };
    }

    @DataProvider(name = "statusAsDraft")
    public static Object[][] statusAsDraft() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_DRAFT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_DRAFT) } };
    }

    @DataProvider(name = "statusAsCancel")
    public static Object[][] statusAsCancel() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_CANCEL_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_CANCEL) } };
    }

    @DataProvider(name = "statusAsDeleted")
    public static Object[][] statusAsDeleted() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(URL),
                apiConfig.get(STATUS_CANCEL_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(STATUS_CANCEL) } };
    }

    @DataProvider(name = "gatewayIDBlank")
    public static Object[][] gatewayIDBlank() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_GATWAY_JSON), apiConfig.get(GATEWAY_ID) } };
    }

    @DataProvider(name = "notExistingGatewayID")
    public static Object[][] notExistingGatewayID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_GATWAY_JSON), apiConfig.get(NO_EXISTING_GATEWAY_ID) } };
    }

    @DataProvider(name = "junkGatewayID")
    public static Object[][] junkGatewayID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID),
                apiConfig.get(EVENT_ID) + ":" + apiConfig.get(EVENT_NAME),
                apiConfig.get(TARGET_GATWAY_JSON), apiConfig.get(JUNK_GATEWAY_ID) } };
    }

    @DataProvider(name = "correctGatewayID")
    public static Object[][] correctGatewayID() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(CORRECT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(TARGET_TYPE), apiConfig.get(TARGET_GATWAY_JSON),
                apiConfig.get(CORRECT_GATEWAY_ID) } };
    }

    @DataProvider(name = "omitGatewayBlock")
    public static Object[][] omitGatewayBlock() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(JSON_URL),
                apiConfig.get(CORRECT_PROGRAM_ID),
                apiConfig.get(EVENT_ID_ECO) + ":" + apiConfig.get(EVENT_NAME_ECO),
                apiConfig.get(OMIT_GATEWAY_BLOCK) } };
    }

    @DataProvider(name = "disconnectGatewayListNVE")
    public static Object[][] disconnectGatewayListNVE() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_NVE_PROGRAM_ID) } };
    }

    @DataProvider(name = "disconnectGatewayListEco")
    public static Object[][] disconnectGatewayListEco() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_ECO_PROGRAM_ID) } };
    }

    @DataProvider(name = "disconnectGatewayListComcast")
    public static Object[][] disconnectGatewayListComcast() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_CMS_PROGRAM_ID) } };
    }

    @DataProvider(name = "disconnectGatewayListDefault")
    public static Object[][] disconnectGatewayListDefault() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(GET_EFC_PROGRAM_ID) } };
    }

    @DataProvider(name = "disconnectGatewayBlankProgram")
    public static Object[][] disconnectGatewayBlankProgram() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(BLANK_PROGRAMID) } };
    }

    @DataProvider(name = "disconnectGatewayJunkProgram")
    public static Object[][] disconnectGatewayJunkProgram() {

        return new Object[][] { { apiConfig.get(BASE_URL) + apiConfig.get(DISCONNECT_GATEWAY_URL),
                apiConfig.get(JUNK_PROGRAM_ID) } };
    }
}
