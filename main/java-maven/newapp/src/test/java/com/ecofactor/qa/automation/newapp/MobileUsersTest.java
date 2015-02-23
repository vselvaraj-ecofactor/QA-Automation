/*
 * MobileUsersTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import static org.testng.Reporter.*;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.Location;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.common.pojo.ThermostatAlgorithm;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.LocationDao;
import com.ecofactor.qa.automation.dao.ThermostatAlgorithmDao;
import com.ecofactor.qa.automation.dao.ThermostatDao;
import com.ecofactor.qa.automation.dao.ThermostatRangeDataDao;
import com.ecofactor.qa.automation.newapp.data.MobileUserDataProvider;
import com.ecofactor.qa.automation.newapp.model.ThermostatData;
import com.ecofactor.qa.automation.newapp.model.UserData;
import com.ecofactor.qa.automation.newapp.page.LoginPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.util.ConvertUserDataToTestData;
import com.ecofactor.qa.automation.newapp.util.mail.MobileMailUtil;
import com.ecofactor.qa.automation.platform.action.UIAction;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mail.TestType;
import com.google.inject.Inject;

/**
 * The Class MobileUsersTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
public class MobileUsersTest {

    /** The Constant HEAT. */
    private static final String HEAT = "Heat";

    /** The Constant COOL. */
    private static final String COOL = "Cool";

    /** The Constant THERMOSTAT_CONTAINER. */
    private static final String THERMOSTAT_CONTAINER = "thermostat_container";

    /** The Constant LOCATION_NAME. */
    private static final String LOCATION_NAME = "location_name";

    /** The Constant TH_AXIS_CONTAINER. */
    private static final String TH_AXIS_CONTAINER = "temperature_axis_container";

    /** The Constant UNUSED_BTM_CONTAINER. */
    private static final String UNUSED_BTM_CONTAINER = "unused_bottom_container";

    /** The test ops. */
    @Inject
    private TestOperations testOps;

    /** The ui action. */
    @Inject
    private UIAction uiAction;

    /** The login page. */
    @Inject
    private LoginPage loginPage;

    /** The th page ui. */
    @Inject
    private ThermostatPageUI thPageUI;

    /** The th page ops. */
    @Inject
    private ThermostatPageOps thPageOps;

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The savings page. */
    @Inject
    private SavingsPage savingsPage;

    /** The thermostat dao. */
    @Inject
    private ThermostatDao thermostatDao;

    /** The location dao. */
    @Inject
    private LocationDao locationDao;

    /** The mail util. */
    @Inject
    MobileMailUtil mailUtil;

    /** The mobile user test data config. */
    @Inject
    private MobileUserTestDataConfig mobileUserTestDataConfig;

    /** The convert user data to test data. */
    @Inject
    private ConvertUserDataToTestData convertUserDataToTestData;

    /** The thermostat range data dao. */
    @Inject
    private ThermostatRangeDataDao thermostatRangeDataDao;

    /** The th algorithm dao. */
    @Inject
    private ThermostatAlgorithmDao thAlgorithmDao;

    /** The user datas. */
    private static List<UserData> userDatas = new LinkedList<UserData>();

    /**
     * Before suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

        System.out.println("Before Suite...");
    }

    /**
     * Before method.
     * @param method the method
     * @param param the param
     * @throws DeviceException the device exception
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final Method method, final Object[] param) throws DeviceException {

        System.out.println("Before Method...");
        LogUtil.setLogString(LogSection.START, "Setup ", true);
        testOps.cleanup();
        testOps.switchToWebView();
        LogUtil.setLogString(LogSection.END, "Setup Completed", true);

    }

    /**
     * After method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method) {

        DriverConfig.setLogString("Completed test " + method.getDeclaringClass().getSimpleName()
                + "." + method.getName(), true);
        DriverConfig.setLogString("-------------------------------------------------------", true);

    }

    /**
     * After suite.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

        convertUserDataToTestData.convertToXml(userDatas);
        DriverConfig.setLogString("Populate the mail content and send mail", true);
        mailUtil.mobileUserSendMail(TestType.CONSUMER, mobileUserTestDataConfig.getEnvironment(),
                userDatas);
    }

    /**
     * Mobile user details.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "mobileUsers", dataProviderClass = MobileUserDataProvider.class, groups = { "sanity1" })
    public void mobileUserDetails(String userName, String password) {

        try {
            UserData userData = new UserData();
            doLogin(userName, password);
            boolean isPageloaded = isPageLoaded();
            userData.setLogin(isPageloaded);
            userData.setUserName(userName);
            userData.setPassword(password);
            List<ThermostatData> thermostatDatas = new LinkedList<ThermostatData>();
            if (isPageloaded) {
                isDisplayed(testOps.getDeviceDriver(),
                        By.xpath("//*[contains(@class, '" + THERMOSTAT_CONTAINER + "')]"),
                        TINY_TIMEOUT);

                List<WebElement> thermostatContainer = testOps.getDeviceDriver().findElements(
                        By.xpath("//*[contains(@class, '" + THERMOSTAT_CONTAINER + "')]"));
                /*
                 * testOps.getDeviceDriver().findElements( By.xpath("//*[contains(@class, '" +
                 * THERMOSTAT_CONTAINER + "')]"));
                 */

                System.out.println("thermostatContainer.size()#==" + thermostatContainer.size());
                if (thermostatContainer.size() > 1) {
                    for (int i = 0; i < thermostatContainer.size(); i++) {
                        System.out.println("i=======================" + i);
                        WebElement page = testOps.getDeviceDriver().findElement(
                                By.xpath("//html/body/div[1]"));
                        LogUtil.setLogString("Swipe Left for a thermostat ", true);
                        uiAction.doSwipeLeft(page);
                        WaitUtil.tinyWait();
                        thermostatDatas = getThermostatDetails(userName, thermostatDatas);
                    }
                } else {
                    thermostatDatas = getThermostatDetails(userName, thermostatDatas);
                }
                userData.setThermostatDatas(thermostatDatas);
            }
            userData = verifyUserStatus(userData);
            List<Location> locationList = locationDao.getLocationByUserName(userName);
            userData.setNoOfLocation(locationList.size());
            userData.setNoOfThermostats(thermostatDatas.size());
            userDatas.add(userData);
            if (userData.isLogin()) {
                doLogout();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the thermostat details.
     * @param userName the user name
     * @param thermostatDatas the thermostat datas
     * @return the thermostat details
     */
    private List<ThermostatData> getThermostatDetails(String userName,
            List<ThermostatData> thermostatDatas) {

        ThermostatData thermostatData = new ThermostatData();
        String thermostatName = thPageUI.getCurrentThermostatName();
        thermostatData.setThermostatName(thermostatName);
        int thId = getTstatIdForUser(userName, thermostatName);
        boolean thStatus = thId == 0 ? true : thPageUI.isThermostatOffline();
        thermostatData.setThermostatId(thId);
        boolean isTstatInstalled = thPageUI.isThermostatNotInstalled();
        thermostatData.setConnected(!thStatus && !isTstatInstalled);
        if (!thStatus && !isTstatInstalled) {
            thPageOps.openTstatController();
            WaitUtil.tinyWait();
            List<String> mode = thCtrlUIPage.getAvailableModes();
            if (mode.contains(HEAT.toLowerCase())) {
                thermostatData.getModes()[0] = "Heat";
            }
            if (mode.contains(COOL.toLowerCase())) {
                thermostatData.getModes()[1] = "Cool";
            }
            thCtrlOpsPage.closeThermostatControl();
            thermostatData.setSubsribedAlgorithms(getSubscribedAlgo(thId));
            thermostatData.setDataCollection(hasDataCollection(thId));
            thermostatData.setLocationName(getLocationName(thId));
            thermostatData.setSavingsValue(getSavingsValue());
        }

        thermostatDatas.add(thermostatData);
        return thermostatDatas;
    }

    /**
     * Do login.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    private void doLogin(final String userName, final String password) throws Exception {

        System.setProperty("userName", userName);
        System.setProperty("password", password);

        loginPage.login(userName, password);
        WaitUtil.smallWait();
        if (loginPage.isLoggedIn()) {
            thPageOps.closeHelp();
            WaitUtil.tinyWait();
        }
    }

    /**
     * Do login.
     * @throws Exception the exception
     */
    private void doLogout() throws Exception {

        thPageOps.clickMenu();
        menuPage.isPageLoaded();
        thPageOps.logout();

        if (!loginPage.isPageLoaded()) {
            LogUtil.setLogString(Marker.START, "Verify the page is redirected to Login Screen",
                    true);
            thPageUI.isPageLoaded();
            thPageOps.clickMenu();
            menuPage.isPageLoaded();
            menuPage.clickLogoutMenuItem();
            LogUtil.setLogString(Marker.END, "Completed", true);
        }
        WaitUtil.smallWait();
    }

    /**
     * Gets the tstat id for user.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @return the tstat id for user
     */
    private Integer getTstatIdForUser(final String userName, String thermostatName) {

        List<Integer> thList = thermostatDao.getThermostatIdForUser(userName);
        Integer thId = 0;
        if (thList.size() == 1) {
            thId = thList.get(0);
        } else {
            for (Integer thermostatId : thList) {
                Thermostat tstat = thermostatDao.findByThermostatId(String.valueOf(thermostatId));
                if (tstat.getName().trim().equalsIgnoreCase(thermostatName.trim())) {
                    thId = thermostatId;
                    break;
                }
            }
        }
        LogUtil.setLogString("Thermostat Id: " + thId, true);
        return thId;
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @throws Exception the exception
     */
    private boolean isPageLoaded() throws Exception {

        LogUtil.setLogString("Verify Login is Success", true);
        isDisplayed(testOps.getDeviceDriver(), By.className(UNUSED_BTM_CONTAINER), SHORT_TIMEOUT);
        boolean isBackgroundDisplayed = isDisplayed(testOps.getDeviceDriver(),
                By.className(TH_AXIS_CONTAINER), TINY_TIMEOUT);
        boolean isPageDisplayed = isDisplayed(testOps.getDeviceDriver(),
                By.className(LOCATION_NAME), TINY_TIMEOUT);
        if (!isBackgroundDisplayed || !isPageDisplayed) {
            LogUtil.setLogString(LogSection.END, "Login Failed", true);
        } else {
            LogUtil.setLogString(LogSection.END, "Login Success", true);
        }
        loginPage.setLoggedIn(isPageDisplayed && isBackgroundDisplayed);
        return isPageDisplayed;
    }

    /**
     * Verify user status.
     * @param userData the user data
     * @return the user data
     */
    private UserData verifyUserStatus(UserData userData) {

        if (!userData.isLogin()) {
            userData.setHasFailures(true);
        } else if (userData.getThermostatDatas() == null
                || userData.getThermostatDatas().size() == 0) {
            userData.setHasFailures(true);
        } else if (userData.getThermostatDatas() != null
                && userData.getThermostatDatas().size() > 0) {
            for (ThermostatData thermostatData : userData.getThermostatDatas()) {

                if (!thermostatData.isConnected()) {
                    userData.setHasFailures(true);
                    break;
                }
                if (thermostatData.getModes()[0] != "Heat") {
                    userData.setHasFailures(true);
                    break;
                }

                if (thermostatData.getModes()[1] != "Cool") {
                    userData.setHasFailures(true);
                    break;
                }

                if (!thermostatData.isDataCollection()) {
                    userData.setHasFailures(true);
                    break;
                }
            }
        }
        return userData;
    }

    /**
     * Checks for data collection.
     * @param thermostatId the thermostat id
     * @return true, if successful
     */
    private boolean hasDataCollection(Integer thermostatId) {

        boolean hasDataCollection = false;

        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);

        Calendar startCalendar = DateUtil.parseToCalendar(currentUTCTime, DateUtil.DATE_FMT_FULL);
        startCalendar.add(Calendar.MINUTE, -60);

        Calendar endCalendar = DateUtil.parseToCalendar(currentUTCTime, DateUtil.DATE_FMT_FULL);

        List<PartitionedThermostatRangeData> rangeDataList = thermostatRangeDataDao
                .listByThermostatAndStartTimeRange(thermostatId, startCalendar, endCalendar);
        hasDataCollection = rangeDataList != null && !rangeDataList.isEmpty() ? true : false;
        return hasDataCollection;
    }

    /**
     * Gets the location name.
     * @param thermostatId the thermostat id
     * @return the location name
     */
    private String getLocationName(Integer thermostatId) {

        Location location = locationDao.getLocationForAThermostat(thermostatId);
        String locationName = "";
        if (location != null) {
            locationName = location.getName();
        }
        return locationName;
    }

    /**
     * Gets the savings value.
     * @return the savings value
     */
    private String getSavingsValue() {

        return thPageUI.getSavingsAmount();
    }

    /**
     * Gets the subscribed algo.
     * @param thermostatId the thermostat id
     * @return the subscribed algo
     */
    public String[] getSubscribedAlgo(Integer thermostatId) {

        log("Get subscribed algorithms", true);

        List<ThermostatAlgorithm> thAlgoList = thAlgorithmDao.findByThermostatId(thermostatId);
        String[] subscribedAlgo = new String[thAlgoList.size()];
        int index = 0;
        for (ThermostatAlgorithm thermostatAlgorithm : thAlgoList) {
            subscribedAlgo[index++] = thermostatAlgorithm.getAlgorithm().getName();
        }

        return subscribedAlgo;
    }
}
