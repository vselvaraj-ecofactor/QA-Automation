/*
 * DemandSideManagementConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class DemandSideManagementConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DemandSideManagementConfig  extends BaseConfig  {

	public static final String PROGRAM_LIST_FORM="programListForm";
	public static final String MENU_ID="menuId";
	public static final String PROGRAM_LIST_FORM_TABLEID="programListFormTableId";
	public static final String PROGRAM_DIV_CLASS="programDivClass";
	public static final String FORM_ID="formId";
	public static final String PROGRAM_TITLE_SPAN_ID="programTitleSpanId";
	public static final String PROGRAM_PROPERTIES_DIV_CLASS="programPropertiesDivClass";
	public static final String PROGRAM_ID_FIELDS_ID="programIdfieldsId";
	public static final String DEMANDSIDE_TAB="demandSideTab";
	public static final String LOADSHAPPING="loadShaping";
	public static final String POPUP_FORM="popUpForm";

	public static final String PAGINATION_NO_FIELD="paginationNoField";
	public static final String NEXT_PAGE_HOVER="nextPaginationHover";
	public static final String NEXT_PAGE_WITHOUT_HOVER="nextPaginationWithoutHover";
	public static final String NEW_EVENT="newEvent";


	public static final String LAST_PAGE_HOVER="lastPageWithHover";
	public static final String LAST_PAGE_WITHOUT_HOVER="lastPageWithoutHover";
	public static final String LAST_PAGE_DISABLED="lastPageDisabled";
	public static final String PREVIOUS_PAGE_HOVER="previousPageHover";
	public static final String PREVIOUS_PAGE_WITHOUT_HOVER="previousPageWithoutHover";
	public static final String PREVIOUS_PAGE_DISABLED="previousPageDisabled";
	public static final String PROGRAM_PAGINATION_BOTTOM_ELEMENT="programPaginationBottomElement";
	public static final String NEXT_PAGE_DISABLED="nextPageDisabled";
	public static final String EVENT_PAGINATION_BOTTOM_ELEMENT="paginationBottomElement";

	public static final String EVENT_TYPE_DRAFT="eventTypeDraft";
	public static final String EVENT_TYPE_SCHEDULED="eventTypeScheduled";
	public static final String EVENT_TYPE_CANCEL="eventTypeCancel";
	public static final String EVENT_TYPE_DELETED="eventTypeDeleted";

	public static final String EVENT_NAME_FIELD="eventNameField";
	public static final String EVENT_DESCRIPTION_FIELD="eventDescriptionField";
	public static final String PRECOOL="precool";
	public static final String PRECOOL_OFF="precoolOff";

	public static final String START_TIME="startTime";
	public static final String EVENT_DURATION_ELEMENT="eventDurationElement";

	public static final String EVENT_NAME="eventName";
	public static final String EVENT_DESC="eventDesc";
	public static final String EVENT_DURATION="eventDuration";

	public static final String SAVE_DRAFT="saveDraft";
	public static final String EVENT_SAVE_BTN="eventSaveBtn";
	public static final String EVENT_SCHEDULED_BTN="eventScheduleBtn";
	public static final String EVENT_CANCEL_BTN="eventCancelBtn";
	public static final String EVENT_DELETE_BTN="eventDeleteBtn";
	public static final String ERROR_MSG_CLASS="errorMessageClass";

	public static final String CANCEL_EVENT_BTN="cancelEventBtn";
	public static final String DELETE_EVENT_BTN="deleteEventBtn";
	public static final String CONFIRM_CANCEL_EVENT_BTN="confirmCancelEventBtn";


	public static final String ADD_EVENT_HOUR_PART="addEventHourPart";
	public static final String ADD_EVENT_DATE_PART="addEventDatePart";
	public static final String EVENT_LIST_PAGE_FORM_NAME="eventListPageFormName";
	public static final String EVENT_CREATE_FORM_NAME="eventCreateFormName";
	public static final String EVENT_STATUS_CLASS="eventStatusClass";
	public static final String CLOSE_EVENT_WIZARD_BTN="closeEventWizardBtn";
	public static final String DISABLED="disabled";
	public static final String EVENT_STATUS_FILTER="eventStatusFilter";
	public static final String CONFIRM_DELETE_DIALOG="confirmDeleteDialog";
	public static final String CONFIRM_CANCEL_DIALOG="confirmCancelDialog";
	public static final String LS_MAX_SHED_OFFSET="lsMaxShedOffset";
	public static final String EVENT_STATUS_LINK_CLASS="eventStatusClassLinkClass";

	public static final String PROGRAM="program";
	public static final String EVENT="event";
	public static final String EVENT_PERIOD="eventPeriod";
	public static final String EVENT_STATUS="eventStatus";
	public static final String LOCATIONS_TARGETED="locationsTargeted";
	public static final String LOCATIONS_PARTICIPATED="locationsParticipated";

	public static final String CREATE_EVENT_MAIN_FORM="createEventMainForm";
	public static final String LS_EVENTS_LINK="lsEventsLink";
	public static final String GROUPS_LINK="groupsLink";
	public static final String CREATE_NEW_GROUP_LINK="createNewGroupLink";
	public static final String GROUP_LIST_FORM="groupListForm";
	public static final String CREATE_GROUP_FORM="createGroupForm";
	public static final String RULE_NAME="ruleName";
	public static final String RULE_DESC="ruleDesc";
	public static final String TEST_RULE_LINK="testRuleLink";
	public static final String SAVE_RULE_BTN="ruleSaveBtn";
	public static final String NO_LOCATIONS_ELEMENT="numberOfLocationsElement";
	public static final String CURRENT_PAGINATOR="currentPaginator";
	public static final String FORM_EVENT_LIST_TABLE="formEventListTable";
	public static final String FORM_EVENT_LIST_TABLE_PAGINATOR_BOTTOM="formEventListTblPaginatorBottom";
	public static final String FORM_EVENT_LIST_PAGINATION_DATA="formEventListPaginationData";
	public static final String LAST_PAGE_ICON="paginationLastPageIcon";
	public static final String REPORT_DISPLAY_FORM="reportDisplayForm";
	public static final String SELECT_GROUPS_CHECKBOX="selectGroupsCheckBox";
	public static final String GROUP_LIST_TABLE ="groups_list_table";
	public static final String DISABLED_CLASS="disabledClass";

	/**
     * Instantiates a new demand side management config.
     * @param driverConfig the driver config
     */
	@Inject
	public DemandSideManagementConfig(DriverConfig driverConfig) {

		super(driverConfig.get(DriverConfig.ENV));
		load("demandSideManagement.properties");
	}
}


