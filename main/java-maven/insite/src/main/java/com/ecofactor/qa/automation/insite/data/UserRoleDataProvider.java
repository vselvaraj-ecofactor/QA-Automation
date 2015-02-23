/*
 * UserRoleDataProvider.java
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

import com.ecofactor.qa.automation.insite.config.UserRoleTestConfig;
import com.google.inject.Inject;

/**
 * The Class UserRoleDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserRoleDataProvider {

    @Inject
    private static UserRoleTestConfig userRoleTestConfig;

    /**
     * Teste cp roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testEcpRoles")
    public static Object[][] testeCPRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.ECP_USER),
                userRoleTestConfig.get(UserRoleTestConfig.ECP_PASSWORD) } };
        return data;
    }

    /**
     * Test install roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testInstallRoles")
    public static Object[][] testInstallRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.INSTALL_USER),
                userRoleTestConfig.get(UserRoleTestConfig.INSTALL_PASSWORD) } };
        return data;
    }

    /**
     * Test service roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testServiceRoles")
    public static Object[][] testServiceRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.SERVICE_USER),
                userRoleTestConfig.get(UserRoleTestConfig.SERVICE_PASSWORD) } };
        return data;
    }

    /**
     * Test util roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testUtilRoles")
    public static Object[][] testUtilRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.UTIL_USER),
                userRoleTestConfig.get(UserRoleTestConfig.UTIL_PASSWORD) } };
        return data;
    }

    /**
     * Test custom roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testCustomRoles")
    public static Object[][] testCustomRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_USER),
                userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_PASSWORD) } };
        return data;
    }

    /**
     * Test user roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testUserRoles")
    public static Object[][] testUserRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.VALID_LOGIN_USER),
                userRoleTestConfig.get(UserRoleTestConfig.VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Test customer care roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testCustomerCareRoles")
    public static Object[][] testCustomerCareRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_USER),
                userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_PASSWORD) } };
        return data;
    }

    /**
     * Test ef admin roles.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testEFAdminRoles")
    public static Object[][] testEFAdminRoles(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.EF_ADMIN_USERID),
                userRoleTestConfig.get(UserRoleTestConfig.EF_ADMIN_PSWD) } };
        return data;
    }


    /**
     * Test custom add pages user role test.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testCustomAddPagesUserRoleTest")
    public static Object[][] testCustomAddPagesUserRoleTest(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_USR_ADD_USERID),
                userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_USR_ADD_PASSWORD) } };
        return data;
    }


    /**
     * Test custom remve pages user role test.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testCustomRemvePagesUserRoleTest")
    public static Object[][] testCustomRemvePagesUserRoleTest(Method m) {

        Object[][] data = { { userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_USR_REMVE_USERID),
                userRoleTestConfig.get(UserRoleTestConfig.CUSTOM_USR_REMVE_PASSWORD) } };
        return data;
    }

}
