/*
 * UserRoleManagement.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface UserRoleManagement.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface UserRoleManagement extends InsiteAuthenticatedPage {

    /**
     * Load page.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage(java.lang.String,
     *      java.lang.String)
     */
    public void loadPage(String userId, String password);

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    public void loadPage();

    /**
     * Test ecp roles.
     * @param userId the user id
     * @param password the password
     */
    public void testEcpRoles(String userId, String password);

    /**
     * Test install roles.
     * @param userId the user id
     * @param password the password
     */
    public void testInstallRoles(String userId, String password);

    /**
     * Test service roles.
     * @param userId the user id
     * @param password the password
     */
    public void testServiceRoles(String userId, String password);

    /**
     * Test util roles.
     * @param userId the user id
     * @param password the password
     */
    public void testUtilRoles(String userId, String password);

    /**
     * Test custom roles.
     * @param userId the user id
     * @param password the password
     */
    public void testCustomRoles(String userId, String password);

    /**
     * Test customer care roles.
     * @param userId the user id
     * @param password the password
     */
    public void testCustomerCareRoles(String userId, String password);

    /**
     * Test ef admin roles.
     * @param userId the user id
     * @param password the password
     */
    public void testEFAdminRoles(String userId, String password);

    /**
     * Test two roles added pages user.
     * @param userId the user id
     * @param password the password
     */
    public void testTwoRolesAddedPagesUser(String userId, String password);

    /**
     * Test two roles removed pages user.
     * @param userId the user id
     * @param password the password
     */
    public void testTwoRolesRemovedPagesUser(String userId, String password);

}
