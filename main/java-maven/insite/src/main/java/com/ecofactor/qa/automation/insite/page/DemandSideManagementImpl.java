/*
 * DemandSideManagementImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.DemandSideManagementConfig.*;
import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Reporter.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.common.pojo.EcpCoreLSProgram;
import com.ecofactor.common.pojo.LoadShapingEventReport;
import com.ecofactor.common.pojo.Region;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.qa.automation.dao.LSProgramDao;
import com.ecofactor.qa.automation.dao.RegionDao;
import com.ecofactor.qa.automation.insite.config.DemandSideManagementConfig;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class DemandSideManagementImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DemandSideManagementImpl extends InsiteAuthenticatedPageImpl implements DemandSideManagement {

    private static Logger logger = LoggerFactory.getLogger(DemandSideManagementImpl.class);
    @Inject
    private LSProgramDao lsProgramDao;
    @Inject
    private InsiteLoginConfig loginConfig;
    @Inject
    private InsiteConfig appConfig;
    @Inject
    private DemandSideManagementConfig demandSideManagementConfig;
    @Inject
    private RegionDao regionDao;

    private WebElement newEventWizardForm = null;
    private String inputEventName;
    private int lsProgramId;
    private Calendar eventDate;
    private EcpCoreLSProgram ecpCoreLSProgram = new EcpCoreLSProgram();
    private LoadShapingEventReport lsEventReport = new LoadShapingEventReport();
    private LoadShapingEventReport lsEventReportUI = new LoadShapingEventReport();
    private Map<String, Object> programPropertiesMap = new HashMap<String, Object>();

    private DateFormat formatter;
    private WebElement formElement = null;

    private WebElement nextPageElement;
    private WebElement previousPageElement;

    private String inputEventDesc;
    private Calendar lastUpdated;

    private WebElement lastPageElement;
    private boolean programfound = false;
    private boolean eventfound = false;
    public String userName;
    public String userPassword;

    WebElement mainformElement = null;
    List<WebElement> availableZipCodes = null;
    WebElement availableZipListElement = null;
    WebElement selectedZipListElement = null;
    List<WebElement> availableZipCodeElements = null;

    /**
     * ProgramProperties Enum for Program Properties .
     * @author aximsoft
     */
    public enum ProgramProperties {
        ProgramId, ProgramName, Active, Program_Start_Date, Program_End_Date, Earliest_Event_Start, Latest_Event_End, Max_Shed_Offset;
    };

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */

    @Override
    public void loadPage() {

        String url = appConfig.get(INSITE_URL) + appConfig.get(DEMANDSIDE_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            DriverConfig.getDriver().get(url);
        }
        clickDemandSideManagement();

    }

    /**
     * loadPage Method to switch to form element in the newly loaded page.
     */
    public void loadPageFrame() {

        DriverConfig.getDriver().switchTo().defaultContent();
        smallWait();
        DriverConfig.getDriver().switchTo().frame(0);
        smallWait();
    }

    /**
     * Test meta data verification for ls event name.
     * @param programName the program name
     * @param eventName the event name
     * @throws ParseException the parse exception
     */
    public void testMetaDataVerificationForLSEventName(final String programName, final String eventName)
            throws ParseException {

        pickGivenProgram(programName);
        WaitUtil.waitUntil(10000);

        DriverConfig.setLogString("Trigger new event.", true);
        triggerNewEvent(eventName, "", true);
    }

    /**
     * Test meta data verification for LS event description.
     * @param programName the program name
     * @param eventName the event name
     * @param eventDescription the event description
     * @throws ParseException the parse exception
     */
    public void testMetaDataVerificationForLSEventDescription(final String programName, final String eventName,
            final String eventDescription) throws ParseException {

        pickGivenProgram(programName);
        WaitUtil.waitUntil(10000);
        // WaitUtil.waitUntil(15000);
        DriverConfig.setLogString("Trigger new event.", true);
        triggerNewEvent(eventName, eventDescription, false);
    }

    /**
     * Create a new event, with description according the null or empty value. Verify the event name
     * if 'checkEventName' is set to true.
     * @param newEventName the new event name
     * @param eventDescription the event description
     * @param checkEventName the check event name
     */
    public void triggerNewEvent(final String newEventName, final String eventDescription, final boolean checkEventName) {

        logger.info("check if create new event link available.");
        WebElement newEventElement = retrieveElementByLinkText(DriverConfig.getDriver(), demandSideManagementConfig.get(NEW_EVENT),
                SHORT_TIMEOUT);
        DriverConfig.setLogString("click on create new event link.", true);
        newEventElement.click();
        largeWait();
        logger.info("check if create new event form displayed.");
        WebElement formElementPopUp = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(POPUP_FORM)));
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String dateValue = dateFormat.format(date);

        WaitUtil.waitUntil(5000);
        DriverConfig.setLogString("Enter event name - " + newEventName + dateValue, true);
        formElementPopUp.findElement(By.id(demandSideManagementConfig.get(EVENT_NAME_FIELD))).sendKeys(
                newEventName + dateValue);
        if (eventDescription != null && !eventDescription.isEmpty()) {
            DriverConfig.setLogString("Enter the description - " + eventDescription + dateValue, true);
            formElementPopUp.findElement(By.name(demandSideManagementConfig.get(EVENT_DESCRIPTION_FIELD))).sendKeys(
                    eventDescription + dateValue);
        }

        logger.info("Verify the event name length.");
        int lengthCheck = formElementPopUp.findElement(By.id(demandSideManagementConfig.get(EVENT_NAME_FIELD)))
                .getAttribute(ATTR_VALUE).length();

        if (checkEventName && lengthCheck == 50) {
            logger.info("Assert the length is not more than 50.", true);
            Assert.assertEquals(lengthCheck == 50, true, "Length is more than 50");
        }

        DriverConfig.setLogString("Save as draft event.", true);
        formElementPopUp.findElement(By.id(demandSideManagementConfig.get(SAVE_DRAFT))).click();
        smallWait();
    }

    /**
     * Gets the first displayed program details. Verify that the program properties listed in the
     * section of the insite is listed correctly.
     * @return the first displayed program details
     * @throws ParseException the parse exception
     */
    public Map<String, Object> getFirstDisplayedProgramDetails() throws ParseException {

        DriverConfig.getDriver().switchTo().defaultContent();
        DriverConfig.setLogString("select a program.", true);
        clickFirstProgram();
        DriverConfig.getDriver().switchTo().defaultContent();
        DriverConfig.setLogString("get program details.", true);
        return getProgramDetails();
    }

    public Map<String, Object> getSpecificProgramDetails(String programName) throws ParseException {

        DriverConfig.getDriver().switchTo().defaultContent();
        DriverConfig.setLogString("select given program.", true);
        pickGivenProgram(programName);
        DriverConfig.getDriver().switchTo().defaultContent();
        DriverConfig.setLogString("get program details.", true);
        return getProgramDetails();
    }

    /**
     * Gets the program details.Retrieve the program data as displayed in the screen.
     * @return the program details
     * @throws ParseException the parse exception
     */
    public Map<String, Object> getProgramDetails() throws ParseException {

        formatter = new SimpleDateFormat("MM-dd-yyyy");
        DriverConfig.getDriver().switchTo().defaultContent();
        smallWait();
        DriverConfig.getDriver().switchTo().frame(0);
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        logger.info("check if the form is displayed.");
        WebElement form1 = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(FORM_ID)));
        WebElement programId = form1.findElement(By.id(demandSideManagementConfig.get(PROGRAM_ID_FIELDS_ID)));

        logger.info("programid: " + programId.getAttribute(ATTR_VALUE));
        lsProgramId = Integer.parseInt(programId.getAttribute(ATTR_VALUE));

        programPropertiesMap.put("ProgramId", programId.getAttribute(ATTR_VALUE));

        WebElement propertiesFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig
                .get(PROGRAM_TITLE_SPAN_ID)));
        logger.info("program name as displayed in Ui : " + propertiesFormElement.getText());

        programPropertiesMap.put("ProgramName", propertiesFormElement.getText());
        WebElement programProperties = DriverConfig.getDriver().findElements(
                By.className(demandSideManagementConfig.get(PROGRAM_PROPERTIES_DIV_CLASS))).get(1);
        List<WebElement> propertyList = programProperties.findElements(By.tagName(TAG_TD));

        Iterator<WebElement> iterator = propertyList.listIterator();

        while (iterator.hasNext()) {
            programPropertiesMap.put(iterator.next().getText().replaceAll(" ", "_"), iterator.next().getText());
        }

        return programPropertiesMap;

    }

    /**
     * Gets the program id.
     * @return the program id
     */
    public Integer getProgramId() {

        formatter = new SimpleDateFormat("MM-dd-yyyy");
        DriverConfig.getDriver().switchTo().defaultContent();
        smallWait();
        DriverConfig.getDriver().switchTo().frame(0);
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        WebElement form1 = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(FORM_ID)));
        WebElement programId = form1.findElement(By.id(demandSideManagementConfig.get(PROGRAM_ID_FIELDS_ID)));

        logger.info("programid: " + programId.getAttribute(ATTR_VALUE));
        lsProgramId = Integer.parseInt(programId.getAttribute(ATTR_VALUE));
        return Integer.valueOf(lsProgramId);
    }

    /**
     * Click first program.Find the first LS program and click it.
     * @throws ParseException the parse exception
     */
    public void clickFirstProgram() throws ParseException {

        WaitUtil.waitUntil(SHORT_TIMEOUT);
        DriverConfig.getDriver().switchTo().frame(0);
        logger.info("check if program list form is displayed.");
        formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(PROGRAM_LIST_FORM)));

        WebElement tableElement = formElement.findElement(By.id(demandSideManagementConfig
                .get(PROGRAM_LIST_FORM_TABLEID)));
        DriverConfig.setLogString("select the first program.", true);
        List<WebElement> columnElements = tableElement.findElements(By.className(demandSideManagementConfig
                .get(PROGRAM_DIV_CLASS)));
        if(columnElements.size()>0) {
        	DriverConfig.setLogString("Program Name: " + columnElements.get(0).findElement(By.tagName(TAG_ANCHOR)).getText(), true);
            columnElements.get(0).findElement(By.tagName(TAG_ANCHOR)).click();
        }

    }

    /**
     * Pick given program.Method to select the given LS Program name from the UI.
     * @param programName the program name
     * @throws ParseException the parse exception
     */
    public void pickGivenProgram(final String programName) throws ParseException {

    	DriverConfig.setLogString("Select given Program - " + programName, true);
        smallWait();
        isDisplayedByTagName(DriverConfig.getDriver(), TAG_IFRAME, SHORT_TIMEOUT);
        DriverConfig.getDriver().switchTo().frame(0);
        iterateAndClickParticularProgram(programName);
    }

    /**
     * Creates the event for group.
     * @param groupName the group name
     * @return the string
     * @throws ParseException the parse exception
     */
    public String createEventForGroup(String groupName) throws ParseException {

        ecpCoreLSProgram = new EcpCoreLSProgram();
        lsProgramId = getProgramId();
        ecpCoreLSProgram = (EcpCoreLSProgram) lsProgramDao.getEcpCoreLSProgram(lsProgramId).get(0);
        logger.info("check if groups link available and click it.");
        mainformElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(CREATE_EVENT_MAIN_FORM)));
        WebElement eventsLink = mainformElement.findElement(By.id(demandSideManagementConfig.get(GROUPS_LINK)));
        eventsLink.click();
        smallWait();
        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));
        eventDate = Calendar.getInstance();
        eventDate.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));

        eventDate.add(Calendar.DATE, Integer.parseInt(demandSideManagementConfig.get(ADD_EVENT_DATE_PART)));
        eventDate.set(Calendar.HOUR, ecpCoreLSProgram.getMinStartHour());

        if (ecpCoreLSProgram.getMinStartHour() >= 12)
            eventDate.set(Calendar.AM_PM, 1);
        else
            eventDate.set(Calendar.AM_PM, 0);

        return createEvent(false, formatter.format(eventDate.getTime()), "60", "SCHEDULED", false, groupName);
    }

    /**
     * Test available zipcodes are valid.
     * @param programid the programid
     * @param programName the program name
     */
    public void testAvailableZipcodesAreValid(int programid, String programName) {

        loadPageFrame();
        DriverConfig.setLogString("select groups tab.", true);
        mainformElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(CREATE_EVENT_MAIN_FORM)));
        WebElement GroupsLink = mainformElement.findElement(By.id(demandSideManagementConfig.get(GROUPS_LINK)));
        GroupsLink.click();
        loadPageFrame();
        WaitUtil.waitUntil(5000);
        WebElement newFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(GROUP_LIST_FORM)));
        logger.info("check if create group link available and click it.", true);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(CREATE_NEW_GROUP_LINK), SHORT_TIMEOUT);
        WebElement groupsFormElement = retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), newFormElement,
                TAG_ANCHOR, ATTR_ID, demandSideManagementConfig.get(CREATE_NEW_GROUP_LINK));
        groupsFormElement.click();

        WaitUtil.waitUntil(SHORT_TIMEOUT);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(CREATE_GROUP_FORM), SHORT_TIMEOUT);
        WebElement createGroupsFormElement = DriverConfig.getDriver()
                .findElement(By.id(demandSideManagementConfig.get(CREATE_GROUP_FORM)));
        WaitUtil.waitUntil(5000);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(RULE_NAME), SHORT_TIMEOUT);
        availableZipCodes = createGroupsFormElement.findElements(By.tagName("ul"));
        availableZipListElement = availableZipCodes.get(0);
        availableZipCodeElements = availableZipListElement.findElements(By.tagName("li"));
        DriverConfig.setLogString("get available zip codes from database.", true);
        List<Region> rules = regionDao.getAvailableZipcodes(lsProgramDao.findById(EcpCoreLSProgram.class, programid)
                .getId());
        int count = 0;
        DriverConfig.setLogString("check if all the available zipcodes are displayed in screen.", true);
        while (rules.iterator().hasNext()) {
            Assert.assertTrue(getListFromZipCodeElementList(availableZipCodeElements).contains(
                    rules.get(count).getZip()), "Zipcodes are not available");
            count++;
        }
    }

    /**
     * Gets the list from zip code element list.
     * @param element the element
     * @return the list from zip code element list
     */
    @SuppressWarnings("null")
    public List<String> getListFromZipCodeElementList(List<WebElement> element) {

        List<String> lstString = null;
        for (WebElement elementZip : element) {
            lstString.add(elementZip.getText());
        }
        return lstString;
    }

    /**
     * Creates the groups.
     * @return the string
     */
    public String createGroups() {

        loadPageFrame();

        ecpCoreLSProgram = new EcpCoreLSProgram();
        lsProgramId = getProgramId();
        DriverConfig.setLogString("get program data from DB.", true);
        ecpCoreLSProgram = (EcpCoreLSProgram) lsProgramDao.getEcpCoreLSProgram(lsProgramId).get(0);
        DriverConfig.setLogString("click create groups link.", true);
        mainformElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(CREATE_EVENT_MAIN_FORM)));
        WebElement GroupsLink = mainformElement.findElement(By.id(demandSideManagementConfig.get(GROUPS_LINK)));
        GroupsLink.click();

        WaitUtil.waitUntil(SHORT_TIMEOUT);
        WaitUtil.waitUntil(5000);
        logger.info("check if create new group form is displayed.");
        WebElement newFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(GROUP_LIST_FORM)));
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(CREATE_NEW_GROUP_LINK), SHORT_TIMEOUT);
        WebElement groupsFormElement = retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), newFormElement,
                TAG_ANCHOR, ATTR_ID, demandSideManagementConfig.get(CREATE_NEW_GROUP_LINK));
        groupsFormElement.click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(CREATE_GROUP_FORM), SHORT_TIMEOUT);
        WebElement createGroupsFormElement = DriverConfig.getDriver()
                .findElement(By.id(demandSideManagementConfig.get(CREATE_GROUP_FORM)));
        WaitUtil.waitUntil(5000);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(RULE_NAME), SHORT_TIMEOUT);
        DriverConfig.setLogString("find the group name field and provide a name.", true);
        WebElement groupName = createGroupsFormElement.findElement(By.id(demandSideManagementConfig.get(RULE_NAME)));
        formatter = new SimpleDateFormat("MMddyyyyhhmma");
        eventDate = Calendar.getInstance();
        eventDate.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));
        eventDate.set(Calendar.DATE, Integer.parseInt(demandSideManagementConfig.get(ADD_EVENT_DATE_PART)));
        eventDate.set(Calendar.HOUR, ecpCoreLSProgram.getMinStartHour());

        if (ecpCoreLSProgram.getMinStartHour() >= 12)
            eventDate.set(Calendar.AM_PM, 1);
        else
            eventDate.set(Calendar.AM_PM, 0);

        String group_Name = "GT".concat(formatter.format(eventDate.getTime()));
        DriverConfig.setLogString("enter group name.", true);
        groupName.sendKeys(group_Name);
        DriverConfig.setLogString("enter group description.", true);
        createGroupsFormElement.findElement(By.id(demandSideManagementConfig.get(RULE_DESC))).sendKeys(
                "GT" + formatter.format(eventDate.getTime()));

        availableZipCodes = createGroupsFormElement.findElements(By.tagName("ul"));
        availableZipListElement = availableZipCodes.get(0);
        selectedZipListElement = availableZipCodes.get(1);
        availableZipCodeElements = availableZipListElement.findElements(By.tagName("li"));

        WebElement addBtn = retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), createGroupsFormElement,
                TAG_BUTTON, ATTR_TITLE, "Add");
        WebElement testRuleElement = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), createGroupsFormElement,
                TAG_ANCHOR, ATTR_ID, demandSideManagementConfig.get(TEST_RULE_LINK), SHORT_TIMEOUT);
        int locationsFound = 0;
        DriverConfig.setLogString("select available zipcodes.", true);
        while (locationsFound < 6) {
            createGroupsFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(CREATE_GROUP_FORM)));
            addBtn = retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), createGroupsFormElement, TAG_BUTTON,
                    ATTR_TITLE, "Add");
            testRuleElement = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), createGroupsFormElement, TAG_ANCHOR,
                    ATTR_ID, demandSideManagementConfig.get(TEST_RULE_LINK), SHORT_TIMEOUT);
            locationsFound = selectOptions(createGroupsFormElement, addBtn, testRuleElement);
        }
        DriverConfig.setLogString("click save group button.", true);
        retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), createGroupsFormElement, TAG_BUTTON, ATTR_ID,
                demandSideManagementConfig.get(SAVE_RULE_BTN), SHORT_TIMEOUT).click();
        return group_Name;
    }

    /**
     * Select options.
     * @param createGroupsFormElement the create groups form element
     * @param addBtn the add btn
     * @param testRuleElement the test rule element
     * @return the integer
     */
    public Integer selectOptions(WebElement createGroupsFormElement, WebElement addBtn, WebElement testRuleElement) {

        availableZipCodes = createGroupsFormElement.findElements(By.tagName("ul"));
        availableZipListElement = availableZipCodes.get(0);
        selectedZipListElement = availableZipCodes.get(1);
        availableZipCodeElements = availableZipListElement.findElements(By.tagName("li"));

        WebElement listElement = null;
        @SuppressWarnings("rawtypes")
        ListIterator element = availableZipCodeElements.listIterator();
        listElement = (WebElement) element.next();
        logger.info("zip code" + listElement.getText());
        listElement.click();
        smallWait();
        addBtn.click();
        smallWait();
        testRuleElement.click();
        smallWait();
        return Integer.parseInt(retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), createGroupsFormElement,
                "label", ATTR_ID, demandSideManagementConfig.get(NO_LOCATIONS_ELEMENT)).getText().split(" ")[0].trim());
    }

    /**
     * Initiate event creation. Method to generate event start time and initiate event creation.
     * @param programName the program name
     * @param duration the duration
     * @param eventMode the event mode
     * @param precoolOpted the precool opted
     * @return the map
     * @throws ParseException the parse exception
     */
    public Map<String, Object> initiateEventCreation(final String programName, final String duration,
            final String eventMode, final boolean precoolOpted) throws ParseException {

        Map<String, Object> eventDetails = new HashMap<String, Object>();

        pickGivenProgram(programName);

        ecpCoreLSProgram = new EcpCoreLSProgram();
        lsProgramId = getProgramId();
        DriverConfig.setLogString("get program details from DB.", true);
        ecpCoreLSProgram = (EcpCoreLSProgram) lsProgramDao.getEcpCoreLSProgram(lsProgramId).get(0);

        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));
        eventDate = Calendar.getInstance();
        eventDate.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));
        eventDate.add(Calendar.DATE, 1);
        eventDate.set(Calendar.HOUR, ecpCoreLSProgram.getMinStartHour());

        if (ecpCoreLSProgram.getMinStartHour() >= 12)
            eventDate.set(Calendar.AM_PM, 1);
        else
            eventDate.set(Calendar.AM_PM, 0);

        loadPageFrame();
        eventDetails.put(
                "eventName",
                createEvent(false, formatter.format(eventDate.getTime()),
                        demandSideManagementConfig.get(EVENT_DURATION), eventMode, precoolOpted, null));
        lastUpdated = Calendar.getInstance();
        eventDetails.put("eventDesc", inputEventDesc);
        eventDetails.put("lastUpdated", lastUpdated);
        eventDetails.put("eventDate", eventDate);
        eventDetails.put("eventDuration", demandSideManagementConfig.get(EVENT_DURATION));
        return eventDetails;
    }

    /**
     * Gets the page size.
     * @param eventListTable the event list table
     * @return the page size
     */
    public Integer getPageSize(WebElement eventListTable) {

        DriverConfig.setLogString("get the page size displayed in screen.", true);
        WebElement pageSize = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), eventListTable, TAG_SPAN,
                ATTR_CLASS, demandSideManagementConfig.get(CURRENT_PAGINATOR), LONG_TIMEOUT);
        String eventListElement = pageSize.getText();
        log(eventListElement.substring(eventListElement.indexOf("of ") + 3, eventListElement.length() - 1));
        String pageLength = eventListElement.substring(eventListElement.indexOf("of ") + 3,
                eventListElement.length() - 1);
        WaitUtil.waitUntil(1000);
        logger.info("get the paginator Bottom UI Element.");
        WebElement paginatorBottomElement = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), eventListTable, "td",
                ATTR_ID, demandSideManagementConfig.get(EVENT_PAGINATION_BOTTOM_ELEMENT), LONG_TIMEOUT);
        WaitUtil.waitUntil(1000);
        List<WebElement> paginatorItems = paginatorBottomElement.findElements(By.tagName(TAG_SPAN));
        WaitUtil.waitUntil(1000);
        paginatorItems.get(paginatorItems.size() - 1).click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        logger.info("page size is " + pageLength);
        return Integer.parseInt(pageLength);
    }

    /**
     * Test event pagination limit.
     * @param programName the program name
     * @param duration the duration
     * @param eventMode the event mode
     * @param precoolOpted the precool opted
     * @param eventCheckCount the event check count
     * @throws ParseException the parse exception
     */

    public void testEventPaginationLimit(final String programName, final String duration, final String eventMode,
            final boolean precoolOpted, Integer eventCheckCount) throws ParseException {

        int i = 0;
        int eventCount = 0;
        int rowCount = 0;
        WebElement eventListTable = null;
        WebElement eventListTableDataRows = null;
        List<WebElement> paginationSection = null;
        List<WebElement> eventRows;
        Integer totalPagesDisplayed = 0;

        pickGivenProgram(programName);
        WaitUtil.waitUntil(SHORT_TIMEOUT);

        ecpCoreLSProgram = new EcpCoreLSProgram();
        lsProgramId = getProgramId();
        DriverConfig.setLogString("get program details from DB.", true);
        ecpCoreLSProgram = (EcpCoreLSProgram) lsProgramDao.getEcpCoreLSProgram(lsProgramId).get(0);

        logger.info("get the event list page form element.");
        formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_LIST_PAGE_FORM_NAME)));
        eventListTable = formElement.findElement(By.id(demandSideManagementConfig.get(FORM_EVENT_LIST_TABLE)));
        paginationSection = eventListTable.findElements(By.id(demandSideManagementConfig
                .get(FORM_EVENT_LIST_TABLE_PAGINATOR_BOTTOM)));

        if (paginationSection.size() > 0)
            totalPagesDisplayed = getPageSize(eventListTable);
        mediumWait();

        formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_LIST_PAGE_FORM_NAME)));
        eventListTable = formElement.findElement(By.id(demandSideManagementConfig.get(FORM_EVENT_LIST_TABLE)));

        eventListTableDataRows = formElement.findElement(By.id(demandSideManagementConfig
                .get(FORM_EVENT_LIST_PAGINATION_DATA)));
        eventRows = eventListTableDataRows.findElements(By.tagName("tr"));
        rowCount = eventRows.size();

        eventCount = 10 - rowCount;

        if (eventCheckCount == 11) {
            eventCount = 10 - rowCount;
            eventCount++;
        }

        logger.info("event Check Count: " + eventCheckCount);
        logger.info("event Count: " + eventCount);
        logger.info("pagination Section size: " + paginationSection.size());
        logger.info("Total pages: " + totalPagesDisplayed);
        logger.info("Row count: " + rowCount);

        while (i < eventCount) {
            formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            eventDate = Calendar.getInstance();
            eventDate.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));
            logger.info("program  minimum start time :" + ecpCoreLSProgram.getMinStartHour());
            eventDate.add(Calendar.DATE, Integer.parseInt(demandSideManagementConfig.get(ADD_EVENT_DATE_PART)));
            eventDate.set(Calendar.HOUR, ecpCoreLSProgram.getMinStartHour());

            if (ecpCoreLSProgram.getMinStartHour() >= 12)
                eventDate.set(Calendar.AM_PM, 1);
            else
                eventDate.set(Calendar.AM_PM, 0);

            createEvent(false, formatter.format(eventDate.getTime()), duration, eventMode, precoolOpted, null);
            WaitUtil.waitUntil(5000);
            i++;
        }
        smallWait();
        eventListTable = formElement.findElement(By.id(demandSideManagementConfig.get(FORM_EVENT_LIST_TABLE)));
        paginationSection = eventListTable.findElements(By.id(demandSideManagementConfig
                .get(FORM_EVENT_LIST_TABLE_PAGINATOR_BOTTOM)));
        DriverConfig.setLogString("check if new page displayed if event count greater than 10.", true);
        if (eventCheckCount == 10) {
            if (paginationSection.size() > 0) {
                Assert.assertTrue(totalPagesDisplayed.equals(getPageSize(eventListTable)), "page size is different");
            } else {
                Assert.assertTrue(totalPagesDisplayed == 0, "page count is not zero");
            }
        } else {
            if (paginationSection.size() > 0) {
                Assert.assertFalse(totalPagesDisplayed.equals(getPageSize(eventListTable)), "page size is different");
            } else {
                Assert.assertTrue(totalPagesDisplayed > 0, "page count is less than zero");
            }
        }

    }

    /**
     * Switchdefault content.
     */
    public void switchdefaultContent() {

        DriverConfig.getDriver().switchTo().defaultContent();
    }

    /**
     * verify Displayed Properties. Compare database values with values displayed in ui.
     * @param programUiProperties the program ui properties
     * @param dbEcpCoreLSProgram the db ecp core ls program
     * @param maxOffsetValue the max offset value
     * @throws ParseException the parse exception
     */
    @SuppressWarnings("rawtypes")
    public void verifyDisplayedProperties(Map<String, Object> programUiProperties, EcpCoreLSProgram dbEcpCoreLSProgram,
            String maxOffsetValue) throws ParseException {

        String eventTime;
        Iterator it = programPropertiesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            ProgramProperties _programProperties = (ProgramProperties) (pairs.getKey() != null
                    && pairs.getKey().toString().length() > 0 ? ProgramProperties.valueOf(pairs.getKey().toString())
                    : ProgramProperties.Active);

            switch (_programProperties) {
            case ProgramId:
                DriverConfig.setLogString("verify program id.", true);
                Assert.assertTrue(Integer.toString(dbEcpCoreLSProgram.getId()).equalsIgnoreCase(
                        pairs.getValue().toString()), "program id is different");
                break;

            case ProgramName:
                DriverConfig.setLogString("verify program name.", true);
                Assert.assertTrue(dbEcpCoreLSProgram.getName().equalsIgnoreCase(pairs.getValue().toString()), "program name is different");
                break;

            case Active:
                DriverConfig.setLogString("verify program status.", true);
                if (dbEcpCoreLSProgram.getStatus().toString().equalsIgnoreCase("ACTIVE"))
                    Assert.assertTrue("YES".equalsIgnoreCase(pairs.getValue().toString().toUpperCase()), "program status is different");
                else
                    Assert.assertTrue("NO".equalsIgnoreCase(pairs.getValue().toString().toUpperCase()), "program status is different");
                break;

            case Program_Start_Date:
                DriverConfig.setLogString("verify program startdate.", true);
                formatter.format(dbEcpCoreLSProgram.getStartDate().getTime());
                Assert.assertTrue(formatter.format(dbEcpCoreLSProgram.getStartDate().getTime()).equalsIgnoreCase(
                        pairs.getValue().toString()), "program start date is different");
                break;

            case Program_End_Date:
                DriverConfig.setLogString("verify program end date.", true);
                formatter.format(dbEcpCoreLSProgram.getEndDate().getTime());
                Assert.assertTrue(formatter.format(dbEcpCoreLSProgram.getEndDate().getTime()).equalsIgnoreCase(
                        pairs.getValue().toString()), "program end date is different");
                break;

            case Earliest_Event_Start:
                DriverConfig.setLogString("verify program start time.", true);
                eventTime = dbEcpCoreLSProgram.getMinStartHour() > 12 ? Integer.toString(
                        dbEcpCoreLSProgram.getMinStartHour() - 12).concat(":00 pm") : Integer.toString(
                        dbEcpCoreLSProgram.getMinStartHour()).concat(":00 am");
                Assert.assertTrue(eventTime.equalsIgnoreCase(pairs.getValue().toString()), "program start time is different");
                break;

            case Latest_Event_End:
                DriverConfig.setLogString("verify program end time.", true);
                eventTime = dbEcpCoreLSProgram.getMaxEndHour() > 12 ? Integer.toString(
                        dbEcpCoreLSProgram.getMaxEndHour() - 12).concat(":00 pm") : Integer.toString(
                        dbEcpCoreLSProgram.getMaxEndHour()).concat(":00 am");
                Assert.assertTrue(eventTime.equalsIgnoreCase(pairs.getValue().toString()), "program end time is different");
                break;

            case Max_Shed_Offset:
                /*
                 * entityAttribute=getMaxShedOffset(dbEcpCoreLSProgram.getId(),LS_MAX_SHED_OFFSET).get
                 * (0); log(pairs.getValue().toString()+" :Max_Shed_Offset: "+maxOffsetValue,true);
                 * Assert.assertTrue(maxOffsetValue.equalsIgnoreCase(pairs.getValue().toString()));
                 */
                break;

            }
        }
    }

    /**
     * Verifies error message For Event Creation With Past StartDate.
     * @param ecpCoreLSProgram the ecp core ls program
     * @throws ParseException the parse exception
     */
    public void checkErrorMsgForEventCreationWithPastStartDate(EcpCoreLSProgram ecpCoreLSProgram) throws ParseException {

        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Calendar eventDate = Calendar.getInstance();
        eventDate.setTimeZone(TimeZone.getTimeZone(ecpCoreLSProgram.getTimeZone()));
        eventDate.setTime(ecpCoreLSProgram.getStartDate().getTime());
        eventDate.add(Calendar.DATE, -Integer.parseInt(demandSideManagementConfig.get(ADD_EVENT_DATE_PART)));
        DriverConfig.setLogString("The start Date for selected LS Program  is: "
                + formatter.format(ecpCoreLSProgram.getStartDate().getTime()), true);
        DriverConfig.setLogString("The new events start Date is: " + formatter.format(eventDate.getTime()), true);
        loadPageFrame();
        createEvent(true, formatter.format(eventDate.getTime()), demandSideManagementConfig.get(EVENT_DURATION),
                demandSideManagementConfig.get(EVENT_TYPE_DRAFT), false, null);
        // WaitUtil.waitUntil(5000);
        WaitUtil.waitUntil(20000);
        DriverConfig.setLogString("check if error message is displayed for invalid start date since the event date is earlier than program's start date.",
                true);
        isFormDisplayedByClassName(DriverConfig.getDriver(), newEventWizardForm, demandSideManagementConfig.get(ERROR_MSG_CLASS),
                SHORT_TIMEOUT);
        WebElement errorMsg = newEventWizardForm.findElement(By.className(demandSideManagementConfig
                .get(ERROR_MSG_CLASS)));
        Assert.assertFalse(errorMsg.getText().isEmpty(), "Error message is not displayed");
    }

    /**
     * Create Event Method creates LS Event in a given LS Program. Start date , duration and mode
     * (draft/save & schedule) for event creation are provided as input .
     * @param pastEvent the past event
     * @param startDate the start date
     * @param duration the duration
     * @param mode the mode
     * @param precoolOpted the precool opted
     * @param groupName the group name
     * @return the string
     * @throws ParseException the parse exception
     */
    public String createEvent(final boolean pastEvent, final String startDate, final String duration,
            final String mode, final boolean precoolOpted, String groupName) throws ParseException {

        DriverConfig.setLogString("Start create new event.", true);
        formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        WaitUtil.waitUntil(18000);
        // WaitUtil.waitUntil(10000);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_LIST_PAGE_FORM_NAME), SHORT_TIMEOUT);
        formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_LIST_PAGE_FORM_NAME)));
        DriverConfig.setLogString("click create new event link.", true);
        WebElement newEventLink = formElement.findElement(By.linkText(demandSideManagementConfig.get(NEW_EVENT)));
        newEventLink.click();

        inputEventName = "Test_" + formatter.format(new java.util.Date());
        inputEventDesc = "Description for Test_" + formatter.format(new java.util.Date());
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_CREATE_FORM_NAME), SHORT_TIMEOUT);

        newEventWizardForm = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_CREATE_FORM_NAME)));
        WaitUtil.waitUntil(5000);
        DriverConfig.setLogString("find event name field.", true);
        WebElement eventName = newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(EVENT_NAME)));
        DriverConfig.setLogString("Enter event name." + inputEventName, true);
        eventName.sendKeys(inputEventName);
        DriverConfig.setLogString("find event description field & send values to it.", true);
        newEventWizardForm.findElement(By.tagName(TAG_TEXTAREA)).sendKeys(inputEventDesc);
        DriverConfig.setLogString("Event Name provided :" + inputEventName, true);
        DriverConfig.setLogString("Event Desc provided :" + inputEventDesc, true);
        DriverConfig.setLogString("Event Start date provided :" + startDate, true);
        DriverConfig.setLogString("find start date field and enter start date.", true);
        newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(START_TIME))).clear();
        newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(START_TIME))).sendKeys(startDate);
        DriverConfig.setLogString("find event duration field.", true);
        isFormDisplayedById(DriverConfig.getDriver(), newEventWizardForm, demandSideManagementConfig.get(EVENT_DURATION_ELEMENT),
                SHORT_TIMEOUT);
        DriverConfig.setLogString("select duration.", true);
        selectOptionByValue(newEventWizardForm, demandSideManagementConfig.get(EVENT_DURATION_ELEMENT), duration);
        DriverConfig.setLogString("Event Duration provided :" + duration + " minutes.",true);

        WaitUtil.waitUntil(1000);
        WebElement precoolOption = newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(PRECOOL)));

        newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(EVENT_DURATION_ELEMENT))).click();
        DriverConfig.setLogString("select precool option if needed.", true);
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        if (precoolOpted) {
            isFormElementClickableById(DriverConfig.getDriver(), demandSideManagementConfig.get(PRECOOL), MEDIUM_TIMEOUT);
            if (precoolOption.isSelected() == false)
                precoolOption.click();
        }
        smallWait();
        DriverConfig.setLogString("select groups for event." + groupName, true);
        // select groups
        WebElement selectGroupsForm = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(POPUP_FORM)));
        if (groupName != null) {
            retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), selectGroupsForm, TAG_SPAN, ATTR_CLASS,
                    demandSideManagementConfig.get(SELECT_GROUPS_CHECKBOX)).click();
            WebElement groupsTableElement = retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), selectGroupsForm,
                    TAG_TBODY, ATTR_ID, demandSideManagementConfig.get(GROUP_LIST_TABLE));
            List<WebElement> groupRows = groupsTableElement.findElements(By.tagName("tr"));
            for (WebElement element : groupRows) {
                List<WebElement> groupColumns = element.findElements(By.tagName("td"));
                if (!groupColumns.isEmpty()) {
                    WebElement checkBoxElement = groupColumns.get(0).findElement(By.tagName(TAG_INPUT));
                    WebElement groupNameElement = groupColumns.get(1);
                    if (groupNameElement.findElement(By.tagName(TAG_DIV)).getText().equalsIgnoreCase(groupName))
                        // select the group name check box
                        checkBoxElement.click();
                }
            }
        }
        // End Select groups
        DriverConfig.setLogString("Mode " + mode, true);
        smallWait();
        isFormDisplayedById(DriverConfig.getDriver(), newEventWizardForm, demandSideManagementConfig.get(EVENT_SAVE_BTN), MEDIUM_TIMEOUT);
        if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_DRAFT))) {
            WaitUtil.waitUntil(SHORT_TIMEOUT);
            DriverConfig.setLogString("click draft button.", true);
            newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(EVENT_SAVE_BTN))).click();
        } else if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_SCHEDULED))) {
            WaitUtil.waitUntil(SHORT_TIMEOUT);
            DriverConfig.setLogString("click schedule button.", true);
            newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(EVENT_SCHEDULED_BTN))).click();
        } else {
            smallWait();
            DriverConfig.setLogString("click cancel button.", true);
            newEventWizardForm.findElement(By.id(demandSideManagementConfig.get(EVENT_CANCEL_BTN))).click();
        }

        return inputEventName;
    }

    /**
     * select Event Link Initiates event search by loading event list page.
     */
    public void selectEventLink(String inputEventName) {

        DriverConfig.setLogString("Select the event " + inputEventName + " from the list.", true);
        loadPageFrame();
        WaitUtil.waitUntil(10000);
        logger.info("check if event list form displayed.");
        WebElement formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_LIST_PAGE_FORM_NAME)));
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_LIST_PAGE_FORM_NAME), SHORT_TIMEOUT);
        mediumWait();
        logger.info("check if event pagination element is displayed.", true);
        if (formElement.findElements(By.id(demandSideManagementConfig.get(EVENT_PAGINATION_BOTTOM_ELEMENT))).size() > 0) {
            isFormDisplayedById(DriverConfig.getDriver(), formElement, demandSideManagementConfig.get(EVENT_PAGINATION_BOTTOM_ELEMENT),
                    SHORT_TIMEOUT);
            WebElement eventIndexElement = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TD, ATTR_ID,
                    demandSideManagementConfig.get(EVENT_PAGINATION_BOTTOM_ELEMENT));
            if (eventIndexElement != null) {
                WebElement pageIndexElement = formElement.findElement(By.id(demandSideManagementConfig
                        .get(EVENT_PAGINATION_BOTTOM_ELEMENT)));
                List<WebElement> pageIndexes = pageIndexElement.findElements(By.tagName(TAG_SPAN));
                lastPageElement = pageIndexes.get(pageIndexes.size() - 1);
                if (!lastPageElement.getAttribute(ATTR_CLASS).endsWith(demandSideManagementConfig.get(DISABLED))) {
                    lastPageElement.click();
                    smallWait();
                }
                nextPageElement = pageIndexes.get(pageIndexes.size() - 4);
                iterateEventList();
            }
        } else {
            logger.info("there is no pagination select event in the default page.");
            WebElement eventElement = retrieveElementByTagTextForSubElement(DriverConfig.getDriver(), formElement, TAG_ANCHOR,
                    inputEventName);
            if (eventElement != null) {
                smallWait();
                DriverConfig.setLogString("select the event link.", true);
                eventElement.click();
                eventfound = true;
            }
        }
        if (eventfound == false) {
            DriverConfig.setLogString("Event name '" + inputEventName + "' not found.", true);
        }
        loadPageFrame();
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_CREATE_FORM_NAME), SHORT_TIMEOUT);
    }

    /**
     * iterateAndClickParticularProgram Iterate the Pagination and click on particular program, if
     * it was found.
     * @param programeName the programe name
     */
    public void iterateAndClickParticularProgram(final String programeName) {

        DriverConfig.setLogString("Iterate the program list and click on specified program.", true);
        // WaitUtil.waitUntil(30000);
        WaitUtil.waitUntil(20000);
        logger.info("check if program list form displayed.");
        formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(PROGRAM_LIST_FORM)));
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(PROGRAM_LIST_FORM), LONG_TIMEOUT);
        List<WebElement> lastPageDisabledState = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS,
                demandSideManagementConfig.get(LAST_PAGE_DISABLED));
        logger.info("check if program pagination element displayed.");
        if (formElement.findElements(By.id(demandSideManagementConfig.get(PROGRAM_PAGINATION_BOTTOM_ELEMENT))).size() > 0
                && lastPageDisabledState.size() == 0) {

            isFormDisplayedById(DriverConfig.getDriver(), formElement, demandSideManagementConfig.get(PROGRAM_PAGINATION_BOTTOM_ELEMENT),
                    SHORT_TIMEOUT);
            WebElement paginationBottomElement = formElement.findElement(By.id(demandSideManagementConfig
                    .get(PROGRAM_PAGINATION_BOTTOM_ELEMENT)));
            logger.info("check if last page button displayed.");
            List<WebElement> paginationElements = paginationBottomElement.findElements(By.tagName(TAG_SPAN));
            for (WebElement items : paginationElements) {
                if (items.getAttribute(ATTR_CLASS).equalsIgnoreCase(demandSideManagementConfig.get(LAST_PAGE_ICON))
                        || items.getAttribute(ATTR_CLASS).equalsIgnoreCase(
                                demandSideManagementConfig.get(LAST_PAGE_WITHOUT_HOVER))) {
                    logger.info("select last page.");
                    items.click();
                    logger.info("Last page is clicked." + items.getAttribute(ATTR_CLASS));
                    break;
                }
            }

            retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS,
                    demandSideManagementConfig.get(LAST_PAGE_DISABLED));
            WebElement programIndexElement = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TD, ATTR_ID,
                    demandSideManagementConfig.get(PROGRAM_PAGINATION_BOTTOM_ELEMENT));
            if (programIndexElement != null) {
                WebElement pageIndexElement = formElement.findElement(By.id(demandSideManagementConfig
                        .get(PROGRAM_PAGINATION_BOTTOM_ELEMENT)));
                List<WebElement> pageIndexes = pageIndexElement.findElements(By.tagName(TAG_SPAN));
                previousPageElement = pageIndexes.get(pageIndexes.size() - 4);
                iterateProgramList(programeName);
            }

        } else {
            logger.info("There are no multiple pages available. Select from the default page.");
            WebElement programElement = retrieveElementByTagTextForSubElement(DriverConfig.getDriver(), formElement, TAG_ANCHOR,
                    programeName);
            if (programElement != null) {
                smallWait();
                DriverConfig.setLogString("click the program element.", true);
                programElement.click();
                programfound = true;
            }

        }

        if (programfound == false) {
            DriverConfig.setLogString("Load shapping program '" + programeName + "' not found.", true);
        }

    }

    /**
     * Iterates programs list for a program.
     * @param programName the program name
     */
    public void iterateProgramList(final String programName) {

        do {
            WaitUtil.waitUntil(10000);
            WebElement programeElementLink = retrieveElementByTagTextForSubElement(DriverConfig.getDriver(), formElement, TAG_ANCHOR,
                    programName);
            if (programeElementLink != null) {
                smallWait();
                DriverConfig.setLogString("select program '" + programName + "' link.", true);
                programeElementLink.click();
                programfound = true;
                break;
            }
            logger.info("click previous page icon.", true);
            WebElement previousElement = null;
            if (retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), formElement, TAG_SPAN, ATTR_CLASS,
                    demandSideManagementConfig.get(PREVIOUS_PAGE_HOVER)) != null) {
                previousElement = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), formElement, TAG_SPAN,
                        ATTR_CLASS, demandSideManagementConfig.get(PREVIOUS_PAGE_HOVER), MEDIUM_TIMEOUT);
                previousElement.click();
            } else {
                previousElement = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), formElement, TAG_SPAN,
                        ATTR_CLASS, demandSideManagementConfig.get(PREVIOUS_PAGE_WITHOUT_HOVER), MEDIUM_TIMEOUT);
                previousElement.click();
            }

            smallWait();
            programeElementLink = retrieveElementByTagTextForSubElement(DriverConfig.getDriver(), formElement, TAG_ANCHOR, programName);
            if (programeElementLink != null) {
                smallWait();
                DriverConfig.setLogString("Load Shapping program '" + programName + "' found. ", true);
                programeElementLink.click();
                programfound = true;
                break;
            }
        } while (!previousPageElement.getAttribute(ATTR_CLASS).contains(
                demandSideManagementConfig.get(PREVIOUS_PAGE_DISABLED)));
    }

    /**
     * iterate Event List iterates the events page list in the insite portal and selects the given
     * event.
     */
    public void iterateEventList() {

        DriverConfig.setLogString("iterate the event list and select the event.", true);
        do {
            WaitUtil.waitUntil(20000);
            WebElement programeElementLink = retrieveElementByTagTextForSubElement(DriverConfig.getDriver(), formElement, TAG_ANCHOR,
                    inputEventName);
            if (programeElementLink != null) {
                WaitUtil.waitUntil(20000);
                programeElementLink.click();
                eventfound = true;
                break;
            }

            WaitUtil.waitUntil(10000);
            WebElement nextElement2 = null;
            if (retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), formElement, TAG_SPAN, ATTR_CLASS,
                    demandSideManagementConfig.get(PREVIOUS_PAGE_HOVER)) != null) {
                nextElement2 = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), formElement, TAG_SPAN, ATTR_CLASS,
                        demandSideManagementConfig.get(PREVIOUS_PAGE_HOVER), MEDIUM_TIMEOUT);
                nextElement2.click();
            } else {
                WaitUtil.waitUntil(20000);
                nextElement2 = retrieveElementByAttributeValueForSubElement(DriverConfig.getDriver(), formElement, TAG_SPAN, ATTR_CLASS,
                        demandSideManagementConfig.get(PREVIOUS_PAGE_WITHOUT_HOVER), MEDIUM_TIMEOUT);
                nextElement2.click();
            }

            WaitUtil.waitUntil(20000);
            programeElementLink = retrieveElementByTagTextForSubElement(DriverConfig.getDriver(), formElement, TAG_ANCHOR, inputEventName);
            if (programeElementLink != null) {
                WaitUtil.waitUntil(20000);
                DriverConfig.setLogString("Load shapping event '" + inputEventName + "' found.", true);
                programeElementLink.click();
                eventfound = true;
                break;
            }
        } while (!nextPageElement.getAttribute(ATTR_CLASS).endsWith(demandSideManagementConfig.get(DISABLED)));

    }

    /**
     * Edits an event and saves it in either draft/scheduled mode.
     * @param mode the mode
     * @return the map
     */
    public Map<String, Object> editEvent(String mode) {

        DriverConfig.setLogString("start edit event.", true);
        Map<String, Object> updatedEventDetails = new HashMap<String, Object>();
        logger.info("check if edit event form visible.", true);
        WebElement newFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_CREATE_FORM_NAME)));
        String updatedEventDesc;
        if (newFormElement != null) {

            updatedEventDesc = formatter.format(new Date());
            WebElement saveBtnLink = null;
            WaitUtil.waitUntil(10000);
            DriverConfig.setLogString("check if event description element is available.", true);
            WebElement eventDescElement = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), newFormElement,
                    TAG_TEXTAREA, "name", demandSideManagementConfig.get(EVENT_DESC), SHORT_TIMEOUT);
            eventDescElement.clear();

            eventDescElement.sendKeys(updatedEventDesc);
            updatedEventDetails.put("eventDesc", updatedEventDesc);
            DriverConfig.setLogString("check if precool selection is available.", true);
            WebElement preCool = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), newFormElement,
                    TAG_INPUT, ATTR_ID, demandSideManagementConfig.get(PRECOOL), SHORT_TIMEOUT);
            DriverConfig.setLogString("select precool option", true);
            if(isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(PRECOOL), SHORT_TIMEOUT))
            preCool.click();

            DriverConfig.setLogString("Mode " + mode, true);
            if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_DRAFT))) {
                DriverConfig.setLogString("check if save draft button is available.", true);
                saveBtnLink = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), newFormElement, TAG_BUTTON,
                        ATTR_ID, demandSideManagementConfig.get(SAVE_DRAFT), SHORT_TIMEOUT);
                DriverConfig.setLogString("click save draft button.", true);
                saveBtnLink.click();
            } else {
                DriverConfig.setLogString("check if schedule button is available.", true);
                saveBtnLink = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), newFormElement, TAG_BUTTON,
                        ATTR_ID, demandSideManagementConfig.get(EVENT_SCHEDULED_BTN), SHORT_TIMEOUT);
                DriverConfig.setLogString("click schedule draft.", true);
                saveBtnLink.click();

            }
            smallWait();
            DriverConfig.setLogString("Completed editing the event.", true);
        }

        return updatedEventDetails;
    }

    /**
     * Test Event Status Filter verifies that the event status filter shows up list of events with
     * selected event status.
     * @param programName the program name
     * @throws ParseException the parse exception
     */
    public void testEventStatusFilter(final String programName) throws ParseException {

        pickGivenProgram(programName);
        loadPageFrame();
        logger.info("check if event list form available.", true);
        isDisplayedById(DriverConfig.getDriver(), "form", SHORT_TIMEOUT);

        formElement = DriverConfig.getDriver().findElement(By.id("form"));
        DriverConfig.setLogString("find the event filter element.", true);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_STATUS_FILTER), SHORT_TIMEOUT);
        DriverConfig.setLogString("select options displayed in event status filter element and check if resulting event list displays events correspondingly.",
                true);
        WebElement select = formElement.findElement(By.id(demandSideManagementConfig.get(EVENT_STATUS_FILTER)));
        List<WebElement> options = select.findElements(By.tagName("option"));
        int i = 0;
        for (WebElement option : options) {

            selectOptionByText(formElement, demandSideManagementConfig.get(EVENT_STATUS_FILTER), option.getText());
            if (option.isSelected()) {
                WaitUtil.waitUntil(2000);
                DriverConfig.setLogString("filter for " + option.getText() + ".", true);
                List<WebElement> listedEventStatus = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS,
                        demandSideManagementConfig.get(EVENT_STATUS_CLASS));

                for (WebElement listItem : listedEventStatus) {
                    WaitUtil.waitUntil(1000);
                    log(listItem.getText());
                    if (i > 0) {
                        Assert.assertTrue(listItem.getText().equalsIgnoreCase(option.getText()), "Status are different");
                    }
                }
            }
            i++;
        }
    }

    /**
	 * Filter status.
	 *
	 * @param status the status
	 */
    public void filterStatus(String status){

		formElement = DriverConfig.getDriver().findElement(By.id("form"));
		DriverConfig.setLogString("find the event filter element.", true);
		isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_STATUS_FILTER), SHORT_TIMEOUT);
		DriverConfig.setLogString("select options displayed in event status filter element and check if resulting event list displays events correspondingly.", true);
		WebElement select = formElement.findElement(By.id(demandSideManagementConfig.get(EVENT_STATUS_FILTER)));
		List<WebElement> options = select.findElements(By.tagName("option"));
		int i = 0;
		for (WebElement option : options) {

			if (option.getText().equalsIgnoreCase(status)) {
				selectOptionByText(formElement, demandSideManagementConfig.get(EVENT_STATUS_FILTER), option.getText());
				if (option.isSelected()) {
					WaitUtil.waitUntil(2000);
					DriverConfig.setLogString("filter for " + option.getText() + ".", true);
					List<WebElement> listedEventStatus = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS, demandSideManagementConfig.get(EVENT_STATUS_CLASS));

					for (WebElement listItem : listedEventStatus) {
						WaitUtil.waitUntil(1000);
						log(listItem.getText());
						if (i > 0) {
							Assert.assertTrue(listItem.getText().equalsIgnoreCase(option.getText()), "Status are different");
						}
					}
				}
				i++;
			}
		}
    }

    /**
     * Completed event status view.
     * @param programName the program name
     * @return the load shaping event report
     * @throws ParseException the parse exception
     */
    public LoadShapingEventReport completedEventStatusView(final String programName) throws ParseException {

        List<String> eventReport = new ArrayList<String>();
        pickGivenProgram(programName);
        loadPageFrame();
        logger.info("check if event list form is available.", true);
        isDisplayedById(DriverConfig.getDriver(), "form", SHORT_TIMEOUT);
        formElement = DriverConfig.getDriver().findElement(By.id("form"));
        logger.info("check if event status filter is available.", true);
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(EVENT_STATUS_FILTER), SHORT_TIMEOUT);

        WebElement select = formElement.findElement(By.id(demandSideManagementConfig.get(EVENT_STATUS_FILTER)));
        List<WebElement> options = select.findElements(By.tagName("option"));

        for (WebElement option : options) {
            if (option.getAttribute("value").contains(Status.COMPLETED.toString())) {
                selectOptionByText(formElement, demandSideManagementConfig.get(EVENT_STATUS_FILTER), option.getText());
                WaitUtil.waitUntil(2000);
                List<WebElement> listedEventStatus = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_ANCHOR,
                        ATTR_CLASS, demandSideManagementConfig.get(EVENT_STATUS_LINK_CLASS));
                logger.info("size:" + listedEventStatus.size());
                for (WebElement listItem : listedEventStatus) {
                    WaitUtil.waitUntil(1000);
                    // DriverConfig.setLogString("listItem " + listItem.getAttribute(ATTR_ID));
                    if (listItem.getText().equalsIgnoreCase(Status.COMPLETED.toString())) {
                        listItem.click();
                        WaitUtil.waitUntil(1000);
                        WebElement spanForm = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig
                                .get(REPORT_DISPLAY_FORM)));
                        WaitUtil.waitUntil(1000);
                        List<WebElement> labelElements = spanForm.findElements(By.tagName("label"));

                        for (WebElement labels : labelElements) {
                            logger.info("Labels: " + labels.getText().toString());
                            eventReport.add(labels.getText());
                        }
                        break;
                    }
                }
            }
        }

        Iterator<String> iterator = eventReport.iterator();
        Calendar rDate = Calendar.getInstance();
        while (iterator.hasNext()) {
            // PROGRAM,EVENT,EVENT_PERIOD,EVENT_STATUS ,LOCATIONS_TARGETED, LOCATIONS_PARTICIPATED
            formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            String labelText = iterator.next();
            String labelValue = null;
            logger.info("Label text: " + labelText);
            if (labelText.equalsIgnoreCase(demandSideManagementConfig.get(PROGRAM))) {
                labelValue = iterator.next();
                lsEventReportUI.getLoadShapingEvent().getEcpCoreLSProgram().setName(labelValue);
            } else if (labelText.equalsIgnoreCase(demandSideManagementConfig.get(EVENT))) {
                labelValue = iterator.next();
                lsEventReportUI.getLoadShapingEvent().setName(labelValue);
            } else if (labelText.equalsIgnoreCase(demandSideManagementConfig.get(LOCATIONS_TARGETED))) {
                labelValue = iterator.next();
                lsEventReportUI.setNumExpectedLocations(Integer.parseInt(labelValue));

            } else if (labelText.equalsIgnoreCase(demandSideManagementConfig.get(LOCATIONS_PARTICIPATED))) {
                labelValue = iterator.next();
                lsEventReportUI.setNumActualLocations(Integer.parseInt(labelValue));

            } else if (labelText.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_STATUS))) {
                labelValue = iterator.next();
                lsEventReportUI.getLoadShapingEvent().setStatus(Status.valueOf(labelValue));

            } else if (labelText.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_PERIOD))) {
                labelValue = iterator.next();
                rDate.setTime(formatter.parse(labelValue.trim()));
                lsEventReportUI.getLoadShapingEvent().setStartDate(rDate);
                labelValue = iterator.next();
                String endTimePart = labelValue.trim();

                rDate.set(Calendar.HOUR, Integer.parseInt(endTimePart.split(":")[0]));
                rDate.set(Calendar.MINUTE, Integer.parseInt(endTimePart.substring(endTimePart.indexOf(":") + 1, 5)));

                if (endTimePart.substring(endTimePart.indexOf(" ") + 1, 8).equalsIgnoreCase("AM")) {
                    rDate.set(Calendar.AM, 1);
                } else {
                    rDate.set(Calendar.PM, 1);
                }
                lsEventReport.getLoadShapingEvent().setEndDate(rDate);

                /*
                 * rDate.setTime(formatter.parse(iterator.next().split("-")[1]));
                 * lsEventReport.getLoadShapingEvent().setEndDate(rDate);
                 * rDate.setTimeZone(TimeZone.
                 * getTimeZone(lsEventReportUI.getLoadShapingEvent().getTimeZone()));
                 */
            }
        }

        return lsEventReportUI;
    }

    /**
     * verifies if Start Time Too Earlier Error message is displayed for earlier start time.
     * @param dbEcpCoreLSProgram the db ecp core ls program
     * @throws ParseException the parse exception
     */
    public void verifyStartTimeTooEarlierError(EcpCoreLSProgram dbEcpCoreLSProgram) throws ParseException {

        loadPageFrame();
        eventDate = Calendar.getInstance();
        createEvent(false, convertToLocalTime(eventDate, dbEcpCoreLSProgram.getTimeZone()),
                demandSideManagementConfig.get(EVENT_DURATION), demandSideManagementConfig.get(EVENT_TYPE_DRAFT),
                false, null);
        // WaitUtil.waitUntil(5000);
        WaitUtil.waitUntil(10000);
        DriverConfig.setLogString("check if 'Start Time Too Earlier Error' message displayed.", true);
        isFormDisplayedByClassName(DriverConfig.getDriver(), newEventWizardForm, demandSideManagementConfig.get(ERROR_MSG_CLASS),
                SHORT_TIMEOUT);
        WebElement errorMsg = newEventWizardForm.findElement(By.className(demandSideManagementConfig
                .get(ERROR_MSG_CLASS)));
        Assert.assertFalse(errorMsg.getText().isEmpty(), "Error message is not displayed");
        mediumWait();
        logger.info("check if event wizard close button is available.", true);
        WebElement closeEventWizardBtn = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS,
                demandSideManagementConfig.get(CLOSE_EVENT_WIZARD_BTN));
        closeEventWizardBtn.click();
        mediumWait();

        logger.info("Event Start time is too earlier. Unable to create event.", true);
    }

    /**
     * Gets the program id for the displayed LS program dfrom the UI.
     * @param programName the program name
     * @return the program id from ui
     * @throws ParseException the parse exception
     */
    public int getProgramIdFromUI(String programName) throws ParseException {

        DriverConfig.getDriver().switchTo().defaultContent();
        pickGivenProgram(programName);
        formElement = DriverConfig.getDriver().findElement(By.id("form"));
        WebElement programId = formElement.findElement(By.id(demandSideManagementConfig.get(PROGRAM_ID_FIELDS_ID)));
        lsProgramId = Integer.parseInt(programId.getAttribute(ATTR_VALUE));
        logger.info("programid: " + programId.getAttribute(ATTR_VALUE));
        lsProgramId = Integer.parseInt(programId.getAttribute(ATTR_VALUE));
        DriverConfig.getDriver().switchTo().defaultContent();
        return lsProgramId;
    }

    /**
     * tests if delete and cancel option works fine for draft and scheduled events.
     * @param mode the mode
     * @throws ParseException the parse exception
     */
    public void testcancelEvent(final String mode) throws ParseException {

        String cancelElement = null;
        logger.info("get the event list page form element.", true);
        WebElement newFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_CREATE_FORM_NAME)));
        WebElement deleteBtnLink = null;

        if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_DRAFT))) {
            WaitUtil.waitUntil(5000);
            isFormDisplayedById(DriverConfig.getDriver(), newFormElement, demandSideManagementConfig.get(EVENT_DELETE_BTN), SHORT_TIMEOUT);
            cancelElement = demandSideManagementConfig.get(EVENT_DELETE_BTN);
        } else if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_SCHEDULED))) {
            WaitUtil.waitUntil(5000);
            isFormDisplayedById(DriverConfig.getDriver(), newFormElement, demandSideManagementConfig.get(CANCEL_EVENT_BTN), SHORT_TIMEOUT);
            cancelElement = demandSideManagementConfig.get(CANCEL_EVENT_BTN);
        } else {
            WaitUtil.waitUntil(5000);
            isFormDisplayedById(DriverConfig.getDriver(), newFormElement, demandSideManagementConfig.get(CANCEL_EVENT_BTN), SHORT_TIMEOUT);
            cancelElement = demandSideManagementConfig.get(CANCEL_EVENT_BTN);
        }
        logger.info("check if delete/cancel button displayed.", true);
        deleteBtnLink = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), newFormElement, TAG_BUTTON,
                ATTR_ID, cancelElement, SHORT_TIMEOUT);
        deleteBtnLink.click();
        loadPageFrame();

        List<WebElement> popUpDiv = null;
        DriverConfig.setLogString("Mode " + mode, true);
        if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_DRAFT))) {
            popUpDiv = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_DIV, ATTR_ID,
                    demandSideManagementConfig.get(CONFIRM_DELETE_DIALOG));
            cancelElement = demandSideManagementConfig.get(DELETE_EVENT_BTN);
        } else if (mode.equalsIgnoreCase(demandSideManagementConfig.get(EVENT_TYPE_SCHEDULED))) {
            popUpDiv = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_DIV, ATTR_ID,
                    demandSideManagementConfig.get(CONFIRM_CANCEL_DIALOG));
            cancelElement = demandSideManagementConfig.get(CONFIRM_CANCEL_EVENT_BTN);
        } else {
            popUpDiv = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_DIV, ATTR_ID,
                    demandSideManagementConfig.get(CONFIRM_CANCEL_DIALOG));
            cancelElement = demandSideManagementConfig.get(CONFIRM_CANCEL_EVENT_BTN);
        }

        DriverConfig.setLogString("click delete/cancel button.", true);
        if (popUpDiv.size() > 0) {
            WebElement lastDiv = popUpDiv.get(popUpDiv.size() - 1);
            lastDiv.findElement(By.id(cancelElement)).click();
        }

    }

    /**
     * Try editing scheduled event.
     * @return true, if successful
     */
    public boolean tryEditingScheduledEvent() {

        boolean enabled = false;
        logger.info("check if edit event form is available.", true);
        WebElement newFormElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(EVENT_CREATE_FORM_NAME)));
        if (newFormElement != null) {

            DriverConfig.setLogString("check if edit event is enabled.", true);
            WebElement eventDescElement = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), newFormElement,
                    TAG_TEXTAREA, "name", demandSideManagementConfig.get(EVENT_DESC), SHORT_TIMEOUT);
            logger.info("Inside editing scheduled event:" + eventDescElement.getText());
            logger.info("Test enabled: " + eventDescElement.isEnabled());
            enabled = eventDescElement.isEnabled();
        }
        return enabled;
    }

    /**
     * get UTC Time method gets the UTC time (including day light option) for a given local date
     * time. The timezone and calender object of the local time zone is passed as input.
     * @param timeZone the time zone
     * @param cal the cal
     * @return the uTC time
     */
    public String getUTCTime(String timeZone, Calendar cal) {

        String strUTCDate = null;
        Calendar tempCalendar;
        tempCalendar = cal;
        DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        gmtFormat.setTimeZone(TimeZone.getTimeZone("UTC/GMT"));

        strUTCDate = gmtFormat.format(tempCalendar.getTime());
        return strUTCDate;
    }

    /**
     * get UTC Date method gets the UTC Date (including day light option) for a given local date
     * time. The timezone and calender object of the local time zone is passed as input.
     * @param timeZone the time zone
     * @param cal the cal
     * @return the uTC date
     */

    public Date getUTCDate(String timeZone, Calendar cal) {

        Calendar tempCalendar;
        tempCalendar = cal;
        String TimeZoneIds[] = TimeZone.getAvailableIDs();

        for (int i = 0; i < TimeZoneIds.length; i++) {

            TimeZone tz = TimeZone.getTimeZone(TimeZoneIds[i]);
            int rawOffset = tz.getRawOffset();
            int hour = rawOffset / (60 * 60 * 1000);
            int minute = Math.abs(rawOffset / (60 * 1000)) % 60;

            if (TimeZoneIds[i].contains(timeZone)) {
                tempCalendar.add(Calendar.HOUR_OF_DAY, -(hour));
                tempCalendar.add(Calendar.MINUTE, -(minute));
            }
        }

        return tempCalendar.getTime();
    }

    /**
     * convert To Local Time method provides local time of a given timezone.
     * @param eventDate the event date
     * @param timezone the timezone
     * @return the string
     * @throws ParseException the parse exception
     */
    public String convertToLocalTime(Calendar eventDate, String timezone) throws ParseException {

        String DATEFORMAT = "MM/dd/yyyy hh:mm a";
        SimpleDateFormat localDateFormat = new SimpleDateFormat(DATEFORMAT);
        localDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));

        Calendar testDate = Calendar.getInstance();
        testDate.setTime(eventDate.getTime());

        Date newDate = localDateFormat.parse(localDateFormat.format(testDate.getTime()));

        logger.info("Converted Local Time: " + localDateFormat.format(newDate.getTime()));
        return localDateFormat.format(newDate.getTime());
    }

    /**
     * Gets the valid event start time.
     * @param eventDate the event date
     * @param timezone the timezone
     * @param ecpCoreLSProgram the ecp core ls program
     * @return the valid event start time
     * @throws ParseException the parse exception
     */

    @SuppressWarnings("static-access")
    public String getValidEventStartTime(Calendar eventDate, String timezone, EcpCoreLSProgram ecpCoreLSProgram)
            throws ParseException {

        String DATEFORMAT = "MM/dd/yyyy hh:mm a";
        SimpleDateFormat localDateFormat = new SimpleDateFormat(DATEFORMAT);
        localDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));

        Calendar testDate = (Calendar) eventDate.clone();

        logger.info("ecpCoreLSProgram.getMinStartHour() :" + localDateFormat.format(testDate.getTime()));

        if (testDate.HOUR_OF_DAY >= ecpCoreLSProgram.getMaxEndHour()) {
            testDate.add(Calendar.DATE, 1);
            testDate.add(Calendar.HOUR, ecpCoreLSProgram.getMinStartHour());
        }

        if (testDate.HOUR_OF_DAY < ecpCoreLSProgram.getMinStartHour()) {
            testDate.add(Calendar.HOUR, ecpCoreLSProgram.getMinStartHour());
        }

        DriverConfig.setLogString("Valid start date should be '" + localDateFormat.format(testDate.getTime())+"' and above.",true);
        return localDateFormat.format(testDate.getTime());
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.insite.page.DemandSideManagement#checkEventsAndGroupsLink()
     */
    @Override
    public boolean checkEventsAndGroupsLink() {

        boolean linksAccesible = false;
        WebElement groupsLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "Groups");
        groupsLink.click();
        smallWait();
        List<WebElement> newGroupElement = retrieveElementsByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "New Group");
        linksAccesible = newGroupElement.size() > 0 ? true : false;

        WebElement eventsLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "Load Shaping Events");
        eventsLink.click();
        smallWait();

        List<WebElement> newEventLink = retrieveElementsByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "New Event");
        linksAccesible = newEventLink.size() > 0 ? true : false;

        return linksAccesible;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.insite.page.DemandSideManagement#iterateProgramPageList()
     */
    @Override
    public List<String> iterateProgramPageList() {

        List<String> programs = new ArrayList<String>();
        loadForm();
        smallWait();
        List<WebElement> lastPageDisabledState = retrieveElementsByAttributeValueList(DriverConfig.getDriver(), TAG_SPAN, ATTR_CLASS,
                demandSideManagementConfig.get(LAST_PAGE_DISABLED));
        logger.info("check if program pagination element displayed.");
        if (formElement.findElements(By.id(demandSideManagementConfig.get(PROGRAM_PAGINATION_BOTTOM_ELEMENT))).size() > 0
                && lastPageDisabledState.size() == 0) {

            isFormDisplayedById(DriverConfig.getDriver(), formElement, demandSideManagementConfig.get(PROGRAM_PAGINATION_BOTTOM_ELEMENT),
                    SHORT_TIMEOUT);
            logger.info("check if last page button displayed.");

            do {
                logger.info("entered do");
                smallWait();
                programs.addAll(collectProgramNames());
                logger.info("exit do");
            } while (getNextProgramButton());

        } else {
            programs.addAll(collectProgramNames());

        }

        return programs;
    }

    private void loadForm() {
        DriverConfig.getDriver().switchTo().defaultContent();
        smallWait();
        DriverConfig.getDriver().switchTo().frame(0);
        formElement = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(PROGRAM_LIST_FORM)));
        isDisplayedById(DriverConfig.getDriver(), demandSideManagementConfig.get(PROGRAM_LIST_FORM), LONG_TIMEOUT);
    }

    /**
     * Gets the next program button.
     * @return the next program button
     */
    private boolean getNextProgramButton() {
        DriverConfig.setLogString("click next page element.", true);
        boolean nextpageFound = false;
        WebElement paginationBottom = formElement.findElement(By.id(demandSideManagementConfig
                .get(PROGRAM_PAGINATION_BOTTOM_ELEMENT)));

        List<WebElement> pageLists = paginationBottom.findElements(By.tagName(TAG_SPAN));

        WebElement nextpage = pageLists.get(8);
        String classDisplayed=nextpage.getAttribute("class");
        logger.info(DISABLED_CLASS+" : nextpage.getAttribute(class): "+classDisplayed,true);
        nextpageFound = classDisplayed.endsWith(demandSideManagementConfig.get(DISABLED_CLASS)) ? true : false;

        if(nextpageFound == false) {
            logger.info(nextpage.isEnabled() + "next page found!");
            nextpage.click();
            DriverConfig.setLogString("next page clicked!", true);
        }

        return !nextpageFound;

    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.insite.page.DemandSideManagement#collectProgramNames()
     */
    @Override
    public List<String> collectProgramNames() {
        logger.info("entered collect pgms");
        if(formElement==null)
        loadForm();
        List<String> pgmNames = new ArrayList<String>();

        WebElement pgmListForm = DriverConfig.getDriver().findElement(By.id(demandSideManagementConfig.get(PROGRAM_LIST_FORM)));
        WebElement programsListTable = retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), pgmListForm,
                TAG_TBODY, ATTR_ID, demandSideManagementConfig.get(PROGRAM_LIST_FORM_TABLEID), SHORT_TIMEOUT);

        List<WebElement> programsList = programsListTable.findElements(By.tagName(TAG_ANCHOR));

        for (WebElement programElement : programsList) {
            pgmNames.add(programElement.getText());
            logger.info("Program Name: " + programElement.getText());
            logger.info("programElement.getText(): "+programElement.getText());
        }
        logger.info("exit collect pgms");
        return pgmNames;
    }

    /**
     * @param programList
     * @see com.ecofactor.qa.automation.insite.page.DemandSideManagement#checkIfProgramsAccesible(java.util.List)
     */
    public void checkIfProgramsAccesible(List<String> programList) {
        DriverConfig.setLogString("check if programs are accesible.",true);
        WebElement pgmElement = null;
        List<WebElement> groupsLink = null;
        for (String prgmElement : programList) {
        	waitUntil(2);
        	pgmElement = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR, prgmElement, SHORT_TIMEOUT);
            waitUntil(1);
            pgmElement.click();
            smallWait();
            groupsLink = retrieveElementsByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "Groups");
            Assert.assertTrue(groupsLink.size() > 0, "Group link size is 0");
            DriverConfig.getDriver().navigate().back();
        }
    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.InsitePageImpl#clickAboutEcofactor()
     */
    @Override
    public void clickAboutEcofactor() {

    }

}
