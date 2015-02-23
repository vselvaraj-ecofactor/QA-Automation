/*
 * RoleManagement.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * The Interface RoleManagement.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface RoleManagement extends InsiteAuthenticatedPage {

    /**
     * Populate and create new role.
     * @param roleName the role name
     * @param roleDescription the role description
     * @param unAssignedPermission the un assigned permission
     */
    public void populateAndCreateNewRole(final String roleName, final String roleDescription,
            final String unAssignedPermission);

    /**
     * Checks if is menu displayed.
     * @param menu the menu
     * @return true, if is menu displayed
     */
    public boolean isMenuDisplayed(final String menu);

    /**
     * Checks if is sub menu displayed.
     * @param subMenu the sub menu
     * @return true, if is sub menu displayed
     */
    public boolean isSubMenuDisplayed(final String subMenu);

    /**
     * Gets the menu.
     * @return the menu
     */
    public List<WebElement> getMenu();

    /**
     * Gets the sub menu.
     * @return the sub menu
     */
    public List<WebElement> getSubMenu();

    /**
     * Change away date value.
     * @param linkTextValue the link text value
     */
    public void changeAwayDateValue(final String linkTextValue);

    /**
     * Click date picker.
     */
    public void clickDatePicker();

    /**
     * Click away date next day.
     */
    public void clickDateNextDay();

    /**
     * Click top first user.
     */
    public void clickTopFirstUser();

    /**
     * Creates the new role.
     * @param roleName the role name
     * @param roleDescription the role description
     * @param unAssignedPermission the un assigned permission
     * @param partnerType the partner type
     */
    public void createNewRole(final String roleName, final String roleDescription,
            final String unAssignedPermission,final int partnerType);

    /**
     * Enter role name.
     * @param roleName the role name
     * @return the string
     */
    public String enterRoleName(final String roleName);

    /**
     * Enter role description.
     * @param roleDescription the role description
     */
    public void enterRoleDescription(final String roleDescription);

    /**
     * Select partner type.
     * @param partnerType the partner type
     */
    public void selectPartnerType(final Integer partnerType);

    /**
     * Click top first role.
     */
    public void clickTopFirstRole();

    /**
     * Click permissions tab.
     */
    public void clickPermissionsTab();

    /**
     * Verify permissions mode.
     */
    public void verifyPermissionsMode();
    
    /**
     * Click find.
     */
    public void clickFind();
    
    /**
     * Check ecp.
     * @return true, if successful
     */
    public boolean checkEcp();
    
    
    /**
     * Click menu.
     * @param menu the menu
     */
    public void  clickMenu(String menu);
    
    /**
     * Click sub menu.
     * @param subMenu the sub menu
     */
    public void  clickSubMenu(String subMenu);
    
    
    /**
     * Check uploaded file in history.
     * @param fileName the file name
     * @return true, if successful
     */
    public boolean checkUploadedFileInHistory(String fileName);
    
    /**
     * Sets the tstat id.
     * @param tstatId the new tstat id
     */
    public void setTstatId(String tstatId);
    
    /**
     * Gets the result.
     * @return the result
     */
    public String getResult();
    
    /**
     * Click subscribe.
     */
    public void clickSubscribe();
    
    /**
     * Click un subscribe.
     */
    public void clickUnSubscribe();
}
