/*
 * DemandSideDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.DemandSideTestConfig;
import com.google.inject.Inject;

/**
 * The Class DemandSideDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DemandSideDataProvider {

    @Inject
    private static DemandSideTestConfig demandSideTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validLogin")
    public static Object[][] validLogin(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD) } };
        return data;
    }
    /**
     * programProperties.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "programProperties")
    public static Object[][] programProperties(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.NEW_PROGRAM),} };
        return data;
    }
    /**
     * Event name metadata input.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventNameMetadataInput")
    public static Object[][] eventNameMetadataInput(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.META_DATA_PGM),
                demandSideTestConfig.get(DemandSideTestConfig.META_DATA_EVENT) } };
        return data;
    }

    /**
     * Event desc metadata input.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventDescMetadataInput")
    public static Object[][] eventDescMetadataInput(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.META_DATA_EVENT_DESC_PGM),
                demandSideTestConfig.get(DemandSideTestConfig.META_DATA_EVENT_DESC_EVENT_NAME),
                demandSideTestConfig.get(DemandSideTestConfig.META_DATA_EVENT_DESC_EVENT_DESC) } };
        return data;
    }

    /**
     * Event start date.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventStartDate")
    public static Object[][] eventStartDate(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Event save.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventSave")
    public static Object[][] eventSave(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Event schedule.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventSchedule")
    public static Object[][] eventSchedule(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Cancel event.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "cancelEvent")
    public static Object[][] cancelEvent(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Edits the event draft.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "editEventDraft")
    public static Object[][] editEventDraft(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Edits the event scheduled.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "editEventScheduled")
    public static Object[][] editEventScheduled(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Edits the event before start.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "editEventBeforeStart")
    public static Object[][] editEventBeforeStart(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Event status filter.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventStatusFilter")
    public static Object[][] eventStatusFilter(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Event meta data.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventMetaData")
    public static Object[][] eventMetaData(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Draft event not scheduled.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "draftEventNotScheduled")
    public static Object[][] draftEventNotScheduled(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD) } };
        return data;
    }

    /**
     * Lead time ls precool.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "leadTimeLSPrecool")
    public static Object[][] leadTimeLSPrecool(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Cancel event draft.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "cancelEventDraft")
    public static Object[][] cancelEventDraft(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Cancel event scheduled.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "cancelEventScheduled")
    public static Object[][] cancelEventScheduled(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Completed event report.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "completedEventReport")
    public static Object[][] completedEventReport(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Event list less than10.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventListLessThan10")
    public static Object[][] eventListLessThan10(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Event list more than10.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "eventListMoreThan10")
    public static Object[][] eventListMoreThan10(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }

    /**
     * Group list.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "groupList")
    public static Object[][] groupList(Method m) {

        Object[][] data = { { demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_USRNAME),
                demandSideTestConfig.get(DemandSideTestConfig.USER_SUPPORT_PSWD),
                demandSideTestConfig.get(DemandSideTestConfig.PROGRAM_NAME) } };
        return data;
    }
}
