/*
 * UserRoleManagementImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.ecofactor.common.pojo.PartnerAccountUser;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.insite.config.UserConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class UserRoleManagementImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserRoleManagementImpl extends InsiteAuthenticatedPageImpl implements
        UserRoleManagement {

    @Inject
    private InsiteConfig appConfig;
    @Inject
    private InsiteLoginConfig loginConfig;
    @Inject
    private UserConfig userConfig;
    @Inject
    private RoleManagementImpl roleManagement;
    @Inject
    private PartnerAccountUser partnerAccountUser;

    Date currentDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    String dateTimeStamp = formatter.format(currentDate);

    /**
     * Load page.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPageImpl#loadPage(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void loadPage(String userId, String password) {

        if (insiteLogin.isAuthenticatedUser() && !insiteLogin.isAuthenticatedUser(userId)) {
            DriverConfig.setLogString("Authenticated User is different, so logout", true);
            logout();
        }
        if (!insiteLogin.isAuthenticatedUser()) {
            insiteLogin.login(userId, password);
            insiteLogin.verifyLogin(userId);
        }
        if (!DriverConfig.getDriver().getCurrentUrl()
                .equalsIgnoreCase(appConfig.get(INSITE_URL) + appConfig.get(ADMIN_PAGE)))
            loadURL(DriverConfig.getDriver(), appConfig.get(INSITE_URL) + appConfig.get(ADMIN_PAGE));
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        DriverConfig.getDriver().switchTo().defaultContent();
        loadPage();
    }

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    @SuppressWarnings("static-access")
    @Override
    public void loadPage() {

        String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.ADMIN_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            DriverConfig.getDriver().get(url);
        }

        clickUserManagement();
    }

    /**
     * Search menu.
     * @param pageName the page name
     * @return true, if successful
     */
    private boolean searchMenu(String pageName) {

        DriverConfig.setLogString("Search Menu " + pageName, true);
        boolean foundSubMenu = false;

        List<WebElement> pageLink = retrieveElementsByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                pageName);
        DriverConfig.setLogString("Required : " + pageName + "; found:" + pageLink.size(), true);
        foundSubMenu = (pageLink.isEmpty()) ? false : true;
        return foundSubMenu;

    }

    /**
     * Test ecp roles.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testEcpRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testEcpRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertTrue("search menu is not available", searchMenu("Support"));
        Assert.assertTrue("search menu is not available", searchMenu("Installation"));
        Assert.assertTrue("search menu is not available", searchMenu("Demand Side Management"));
        Assert.assertTrue("search menu is not available", searchMenu("On Boarding"));

    }

    /**
     * Test install roles.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testInstallRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testInstallRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertFalse("search menu is available", searchMenu("Support"));
        Assert.assertTrue("search menu is not available", searchMenu("Installation"));
        Assert.assertFalse("search menu is available", searchMenu("Demand Side Management"));
        Assert.assertFalse("search menu is not available", searchMenu("On Boarding"));

    }

    /**
     * Test service roles.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testServiceRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testServiceRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertFalse("search menu is available", searchMenu("Support"));
        Assert.assertFalse("search menu is available", searchMenu("Installation"));
        Assert.assertFalse("search menu is available", searchMenu("Demand Side Management"));
        Assert.assertFalse("search menu is available", searchMenu("On Boarding"));

    }

    /**
     * Test util roles.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testUtilRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testUtilRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertFalse("search menu is available", searchMenu("Support"));
        Assert.assertFalse("search menu is available", searchMenu("Installation"));
        Assert.assertTrue("search menu is not available", searchMenu("Demand Side Management"));
        Assert.assertFalse("search menu is available", searchMenu("On Boarding"));

    }

    /**
     * Test custom roles.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testCustomRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testCustomRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertTrue("search menu is not available", searchMenu("Support"));
        Assert.assertFalse("search menu is available", searchMenu("Installation"));
        Assert.assertFalse("search menu is available", searchMenu("Demand Side Management"));
        Assert.assertTrue("search menu is not available", searchMenu("On Boarding"));

    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.InsitePageImpl#clickAboutEcofactor()
     */
    @Override
    public void clickAboutEcofactor() {

        // Auto-generated method stub

    }

    /**
     * @param userId
     * @param password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testCustomerCareRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testCustomerCareRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertTrue("search menu is not available", searchMenu("Support"));
        Assert.assertTrue("search menu is not available", searchMenu("On Boarding"));
        Assert.assertFalse("search menu is available", searchMenu("Demand Side Management"));
        Assert.assertFalse("search menu is available", searchMenu("Installation"));
        clickUserManagement();
        Assert.assertFalse("search menu is available", searchMenu("Role Management"));
        Assert.assertTrue("search menu is not available", searchMenu("On Boarding"));
        clickOnBoard();
        Assert.assertTrue("search menu is not available", searchMenu("Errors to be fixed"));
        Assert.assertTrue("search menu is not available", searchMenu("Bulk Uploads"));
        Assert.assertTrue("search menu is not available", searchMenu("History"));

    }

    /**
     * @param userId
     * @param password
     * @see com.ecofactor.qa.automation.insite.page.UserRoleManagement#testEFAdminRoles(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void testEFAdminRoles(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertTrue("search menu is not available", searchMenu("Support"));
        Assert.assertTrue("search menu is not available", searchMenu("Installation"));
        Assert.assertTrue("search menu is not available", searchMenu("Demand Side Management"));
        Assert.assertTrue("search menu is not available", searchMenu("On Boarding"));
        clickUserManagement();
        clickRoleManagement();
        clickInstallation();
        clickScheduling();
        clickPreConfiguration();
        clickOnBoard();
        clickBulkUpload();
        clickHistory();

    }

    @Override
    public void testTwoRolesAddedPagesUser(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertTrue("search menu is not available", searchMenu("Demand Side Management"));
        clickUserManagement();
        clickDemandSideManagement();
    }

    @Override
    public void testTwoRolesRemovedPagesUser(String userId, String password) {

        Assert.assertTrue("search menu is not available", searchMenu("Admin"));
        Assert.assertFalse("search menu is available", searchMenu("Demand Side Management"));
        clickUserManagement();

    }

}
