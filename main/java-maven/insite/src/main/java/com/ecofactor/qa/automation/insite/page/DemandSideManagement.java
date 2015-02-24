/*
 * DemandSideManagement.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.ecofactor.common.pojo.EcpCoreLSProgram;
import com.ecofactor.common.pojo.LoadShapingEventReport;
import com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage;

/**
 * The Interface DemandSideManagement.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DemandSideManagement extends InsiteAuthenticatedPage {

    /**
     * Load page frame.
     */
    public void loadPageFrame();

    /**
     * Click on demand side management.
     */
    public void clickDemandSideManagement();

    /**
     * Gets the first displayed program details. Verify that the program properties listed in the
     * section of the insite is listed correctly.
     * @return the first displayed program details
     * @throws ParseException the parse exception
     */
    public Map<String, Object> getFirstDisplayedProgramDetails() throws ParseException;

    /**
     * Gets the specific program details.
     * @param programName name
     * @return the specific program details
     * @throws ParseException the parse exception
     */
    public Map<String, Object> getSpecificProgramDetails(String programName) throws ParseException;

    /**
     * Gets the program details.Retrieve the program data as displayed in the screen.
     * @return the program details
     * @throws ParseException the parse exception
     */
    public Map<String, Object> getProgramDetails() throws ParseException;

    /**
     * Test meta data verification for ls event name.
     * @param programeName the programe name
     * @param eventName the event name
     * @throws ParseException the parse exception
     */
    public void testMetaDataVerificationForLSEventName(final String programeName,
            final String eventName) throws ParseException;

    /**
     * Test meta data verification for LS event description.
     * @param programeName the programe name
     * @param eventName the event name
     * @param eventDescription the event description
     * @throws ParseException the parse exception
     */
    public void testMetaDataVerificationForLSEventDescription(final String programeName,
            final String eventName, final String eventDescription) throws ParseException;

    /**
     * Create a new event, with description according the null or empty value. Verify the event name
     * if 'checkEventName' is set to true.
     * @param newEventName the new event name
     * @param eventDescription the event description
     * @param checkEventName the check event name
     */
    public void triggerNewEvent(final String newEventName, final String eventDescription,
            final boolean checkEventName);

    /**
     * Gets the program id.
     * @return the program id
     */
    public Integer getProgramId();

    /**
     * Click first program.Find the first LS program and click it.
     * @throws ParseException the parse exception
     */
    public void clickFirstProgram() throws ParseException;

    /**
     * Pick given program.Method to select the given LS Program name from the UI.
     * @param programName the program name
     * @throws ParseException the parse exception
     */
    public void pickGivenProgram(final String programName) throws ParseException;

    /**
     * Creates the event for group.
     * @param groupName the group name
     * @return the string
     * @throws ParseException the parse exception
     */
    public String createEventForGroup(String groupName) throws ParseException;

    /**
     * Test available zipcodes are valid.
     * @param programid the programid
     * @param programName the program name
     */
    public void testAvailableZipcodesAreValid(int programid, String programName);

    /**
     * Gets the list from zip code element list.
     * @param element the element
     * @return the list from zip code element list
     */
    public List<String> getListFromZipCodeElementList(List<WebElement> element);

    /**
     * Creates the groups.
     * @return the string
     */
    public String createGroups();

    /**
     * Select options.
     * @param createGroupsFormElement the create groups form element
     * @param addBtn the add btn
     * @param testRuleElement the test rule element
     * @return the integer
     */
    public Integer selectOptions(WebElement createGroupsFormElement, WebElement addBtn,
            WebElement testRuleElement);

    /**
     * Initiate event creation. Method to generate event start time and initiate event creation.
     * @param programName the program name
     * @param duration the duration
     * @param eventMode the event mode
     * @param precoolOpted the precool opted
     * @return the map
     * @throws ParseException the parse exception
     */
    public Map<String, Object> initiateEventCreation(final String programName,
            final String duration, final String eventMode, final boolean precoolOpted)
            throws ParseException;

    /**
     * Gets the page size.
     * @param eventListTable the event list table
     * @return the page size
     */
    public Integer getPageSize(WebElement eventListTable);

    /**
     * Test event pagination limit.
     * @param programName the program name
     * @param duration the duration
     * @param eventMode the event mode
     * @param precoolOpted the precool opted
     * @param eventCheckCount the event check count
     * @throws ParseException the parse exception
     */
    public void testEventPaginationLimit(final String programName, final String duration,
            final String eventMode, final boolean precoolOpted, Integer eventCheckCount)
            throws ParseException;

    /**
     * Switchdefault content.
     */
    public void switchdefaultContent();

    /**
     * verify Displayed Properties. Compare database values with values displayed in ui.
     * @param programUiProperties the program ui properties
     * @param dbEcpCoreLSProgram the db ecp core ls program
     * @param maxOffsetValue the max offset value
     * @throws ParseException the parse exception
     */
    public void verifyDisplayedProperties(Map<String, Object> programUiProperties,
            EcpCoreLSProgram dbEcpCoreLSProgram, String maxOffsetValue) throws ParseException;

    /**
     * Verifies error message For Event Creation With Past StartDate.
     * @param ecpCoreLSProgram the ecp core ls program
     * @throws ParseException the parse exception
     */
    public void checkErrorMsgForEventCreationWithPastStartDate(EcpCoreLSProgram ecpCoreLSProgram)
            throws ParseException;

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
    public String createEvent(final boolean pastEvent, final String startDate,
            final String duration, final String mode, final boolean precoolOpted, String groupName)
            throws ParseException;

    /**
     * select Event Link Initiates event search by loading event list page.
     * @param inputEventName the input event name
     */
    public void selectEventLink(String inputEventName);

    /**
     * Iterate And Click Particular Program iterates the Pagination and click on particular program,
     * if it was found.
     * @param programeName the programe name
     */
    public void iterateAndClickParticularProgram(final String programeName);

    /**
     * Iterates programs list for a program.
     * @param programName the program name
     */
    public void iterateProgramList(final String programName);

    /**
     * iterate Event List iterates the events page list in the insite portal and selects the given
     * event.
     */
    public void iterateEventList();

    /**
     * Edits an event and saves it in either draft/scheduled mode.
     * @param mode the mode
     * @return the map
     */
    public Map<String, Object> editEvent(String mode);

    /**
     * Test Event Status Filter verifies that the event status filter shows up list of events with
     * selected event status.
     * @param programName the program name
     * @throws ParseException the parse exception
     */
    public void testEventStatusFilter(final String programName) throws ParseException;

    /**
     * Completed event status view.
     * @param programName the program name
     * @return the load shaping event report
     * @throws ParseException the parse exception
     */
    public LoadShapingEventReport completedEventStatusView(final String programName)
            throws ParseException;

    /**
     * verifies if Start Time Too Earlier Error message is displayed for earlier start time.
     * @param dbEcpCoreLSProgram the db ecp core ls program
     * @throws ParseException the parse exception
     */
    public void verifyStartTimeTooEarlierError(EcpCoreLSProgram dbEcpCoreLSProgram)
            throws ParseException;

    /**
     * Gets the program id for the displayed LS program dfrom the UI.
     * @param programName the program name
     * @return the program id from ui
     * @throws ParseException the parse exception
     */
    public int getProgramIdFromUI(String programName) throws ParseException;

    /**
     * tests if delete and cancel option works fine for draft and scheduled events.
     * @param mode the mode
     * @throws ParseException the parse exception
     */
    public void testcancelEvent(final String mode) throws ParseException;

    /**
     * Try editing scheduled event.
     * @return true, if successful
     */
    public boolean tryEditingScheduledEvent();

    /**
     * get UTC Time method gets the UTC time (including day light option) for a given local date
     * time. The timezone and calender object of the local time zone is passed as input.
     * @param timeZone the time zone
     * @param cal the cal
     * @return the uTC time
     */
    public String getUTCTime(String timeZone, Calendar cal);

    /**
     * get UTC Date method gets the UTC Date (including day light option) for a given local date
     * time. The timezone and calender object of the local time zone is passed as input.
     * @param timeZone the time zone
     * @param cal the cal
     * @return the uTC date
     */

    public Date getUTCDate(String timeZone, Calendar cal);

    /**
     * convert To Local Time method provides local time of a given timezone.
     * @param eventDate the event date
     * @param timezone the timezone
     * @return the string
     * @throws ParseException the parse exception
     */
    public String convertToLocalTime(Calendar eventDate, String timezone) throws ParseException;

    /**
     * Gets the valid event start time.
     * @param eventDate the event date
     * @param timezone the timezone
     * @param ecpCoreLSProgram the ecp core ls program
     * @return the valid event start time
     * @throws ParseException the parse exception
     */
    public String getValidEventStartTime(Calendar eventDate, String timezone,
            EcpCoreLSProgram ecpCoreLSProgram) throws ParseException;

    /**
     * Check events and groups link.
     * @return true, if successful
     */
    public boolean checkEventsAndGroupsLink();

    /**
     * Collect program names.
     * @return the list
     */
    public List<String> collectProgramNames();

    /**
     * Check if programs accesible.
     * @param programList the program list
     */
    public void checkIfProgramsAccesible(List<String> programList);

    /**
     * Iterate program page list.
     * @return the list
     */
    public List<String> iterateProgramPageList();

    /**
     * Filter status.
     * @param status the status
     */
    public void filterStatus(String status);

    /**
     * Initiate event with proper time.
     * @param programName the program name
     * @param duration the duration
     * @param eventMode the event mode
     * @param precoolOpted the precool opted
     * @return the map
     * @throws ParseException
     */
    public Map<String, Object> initiateEventWithProperTime(final String programName,
            final String duration, final String eventMode, final boolean precoolOpted)
            throws ParseException;
}
