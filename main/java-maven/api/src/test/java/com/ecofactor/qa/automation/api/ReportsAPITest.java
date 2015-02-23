/*
 * ReportsAPITest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.api;

import static org.testng.Reporter.*;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.analytics.AggDailyStatsThermostatWeather;
import com.ecofactor.common.pojo.analytics.AggMonthlyStatsThermostatWeather;
import com.ecofactor.common.pojo.analytics.QuantReport;
import com.ecofactor.qa.automation.dao.DailyReportDao;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.HourlyReportDao;
import com.ecofactor.qa.automation.dao.MonthlyReportDao;
import com.ecofactor.qa.automation.dao.SavingsReportDao;
import com.ecofactor.qa.automation.pojo.AggHourlyStatsThermostatWeather;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.HttpUtil;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * The Class ReportsAPITest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, ReportsAPIModule.class })
public class ReportsAPITest {

    @Inject
    private ReportsAPIConfig reportsAPIConfig;
    @Inject
    private MonthlyReportDao monthlyReportDao;
    @Inject
    private DailyReportDao dailyReportDao;
    @Inject
    private HourlyReportDao hourlyReportDao;
    @Inject
    private SavingsReportDao savingsReportDao;
    @Inject
    private TestLogUtil testLogUtil;
    private long start;

    /**
     * Inits the method.
     * @param method the method
     * @param param the param
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(Method method, Object[] param) {

        testLogUtil.logStart(method);
        start = System.currentTimeMillis();
        DriverConfig.setLogString("Build Id: "+System.getProperty("buildId"), true);
        DriverConfig.setLogString("Build Number: "+System.getProperty("buildNumber"), true);
    }

    /**
     * End method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(Method method) {

        long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * Invalid location id location usage months.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationIdLocationUsageMonths() {

        log("", true);
        String content = HttpUtil.get(getLocationUsageURL("00"), getMonthParams(5), 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ERROR), mesg);
        }
    }

    /**
     * Invalid location id location usage days.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationIdLocationUsageDays() {

        String content = HttpUtil.get(getLocationUsageURL("00"), getDayParams(), 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ERROR), mesg);
        }
    }

    /**
     * Invalid location id location usage hours.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationIdLocationUsageHours() {

        String content = HttpUtil.get(getLocationUsageURL("00"), getHourParams(), 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ERROR), mesg);
        }
    }

    /**
     * Missing start date location usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateLocationUsageDays() {

        HashMap<String, String> params = getDayParams();
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_START_DATE_ERROR));
        }
    }

    /**
     * Missing start date location usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateLocationUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_START_DATE_ERROR));
        }
    }

    /**
     * Missing start date location usage hours.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateLocationUsageHours() {

        HashMap<String, String> params = getHourParams();
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_START_DATE_ERROR));
        }
    }

    /**
     * Missing end date location usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateLocationUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_END_DATE_ERROR));
        }
    }

    /**
     * Missing end date location usage days.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateLocationUsageDays() {

        HashMap<String, String> params = getDayParams();
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_END_DATE_ERROR));
        }
    }

    /**
     * Missing end date location usage hours.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateLocationUsageHours() {

        HashMap<String, String> params = getHourParams();
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_END_DATE_ERROR));
        }
    }

    /**
     * Missing interval location usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingIntervalLocationUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.remove(ReportsAPIConfig.INTERVAL);
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_ERROR));
        }
    }

    /**
     * Invalid interval location usage months.
     */
    @Test(groups = { "sanity1" })
    public void invalidIntervalLocationUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.put(ReportsAPIConfig.INTERVAL, "MONTH");
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_INVALID));
        }
    }

    /**
     * Invalid interval location usage days.
     */
    @Test(groups = { "sanity1" })
    public void invalidIntervalLocationUsageDays() {

        HashMap<String, String> params = getDayParams();
        params.put(ReportsAPIConfig.INTERVAL, "DAY");
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_INVALID));
        }
    }

    /**
     * Invalid interval location usage hours.
     */
    @Test(groups = { "sanity1" })
    public void invalidIntervalLocationUsageHours() {

        HashMap<String, String> params = getHourParams();
        params.put(ReportsAPIConfig.INTERVAL, "HOUR");
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_INVALID));
        }
    }

    /**
     * Location usage months.
     */
    @Test(groups = { "sanity1" })
    public void locationUsageMonths() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), getMonthParams(5), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertMonthlyLocationUsageData(object, 5, 2013);
    }

    /**
     * Location usage days.
     */
    @Test(groups = { "sanity1" })
    public void locationUsageDays() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), getDayParams(), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertDailyLocationUsageData(object, 1, 5, 2013);
    }

    /**
     * Location usage hours.
     */
    @Test(groups = { "sanity1" })
    public void locationUsageHours() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationUsageURL(id), getHourParams(), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertHourlyLocationUsageData(object, 12, 25, 5, 2013);
    }

    /**
     * Invalid user id user usage months.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserIdUserUsageMonths() {

        String content = HttpUtil.get(getUserUsageURL("00"), getMonthParams(5), 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get("Error Messages"));
        JSONArray mesgs = (JSONArray) object.get("Error Messages");
        for (Object mesg : mesgs) {
            Assert.assertEquals("User with Id 00 not found.", mesg);
        }
    }

    /**
     * Invalid user id user usage days.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserIdUserUsageDays() {

        String content = HttpUtil.get(getUserUsageURL("00"), getDayParams(), 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get("Error Messages"));
        JSONArray mesgs = (JSONArray) object.get("Error Messages");
        for (Object mesg : mesgs) {
            Assert.assertEquals("User with Id 00 not found.", mesg);
        }
    }

    /**
     * Invalid user id user usage hours.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserIdUserUsageHours() {

        String content = HttpUtil.get(getUserUsageURL("00"), getHourParams(), 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get("Error Messages"));
        JSONArray mesgs = (JSONArray) object.get("Error Messages");
        for (Object mesg : mesgs) {
            Assert.assertEquals("User with Id 00 not found.", mesg);
        }
    }

    /**
     * Missing start date user usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateUserUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_START_DATE_ERROR));
        }
    }

    /**
     * Missing start date user usage days.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateUserUsageDays() {

        HashMap<String, String> params = getDayParams();
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_START_DATE_ERROR));
        }
    }

    /**
     * Missing start date user usage hours.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateUserUsageHours() {

        HashMap<String, String> params = getHourParams();
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_START_DATE_ERROR));
        }
    }

    /**
     * Missing end date user usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateUserUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_END_DATE_ERROR));
        }
    }

    /**
     * Missing end date user usage days.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateUserUsageDays() {

        HashMap<String, String> params = getDayParams();
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_END_DATE_ERROR));
        }
    }

    /**
     * Missing end date user usage hours.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateUserUsageHours() {

        HashMap<String, String> params = getHourParams();
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_END_DATE_ERROR));
        }
    }

    /**
     * Missing interval user usage months.
     */
    @Test(groups = { "sanity1" })
    public void missingIntervalUserUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.remove(ReportsAPIConfig.INTERVAL);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_ERROR));
        }
    }

    /**
     * Invalid interval user usage months.
     */
    @Test(groups = { "sanity1" })
    public void invalidIntervalUserUsageMonths() {

        HashMap<String, String> params = getMonthParams(5);
        params.put(ReportsAPIConfig.INTERVAL, "MONTH");
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_INVALID));
        }
    }

    /**
     * Invalid interval user usage days.
     */
    @Test(groups = { "sanity1" })
    public void invalidIntervalUserUsageDays() {

        HashMap<String, String> params = getDayParams();
        params.put(ReportsAPIConfig.INTERVAL, "DAY");
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_INVALID));
        }
    }

    /**
     * Invalid interval user usage hours.
     */
    @Test(groups = { "sanity1" })
    public void invalidIntervalUserUsageHours() {

        HashMap<String, String> params = getHourParams();
        params.put(ReportsAPIConfig.INTERVAL, "HOUR");
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.USAGE_INTERVAL_INVALID));
        }
    }

    /**
     * User usage months.
     */
    @Test(groups = { "sanity1" })
    public void userUsageMonths() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), getMonthParams(5), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertMonthlyUserUsageData(object, 5, 2013);
    }

    /**
     * User usage days.
     */
    @Test(groups = { "sanity1" })
    public void userUsageDays() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), getDayParams(), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertDailyUserUsageData(object, 1, 5, 2013);
    }

    /**
     * User usage hours.
     */
    @Test(groups = { "sanity1" })
    public void userUsageHours() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserUsageURL(id), getHourParams(), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertHourlyUserUsageData(object, 12, 25, 5, 2013);
    }

    /**
     * Location savings.
     */
    @Test(groups = { "sanity1" })
    public void locationSavings() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationSavingsURL(id), getNotAccumulatedParams(5), 200);
        JSONObject object = JsonUtil.parseObject(content);

        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.MONTH, 5 - 1);
        start.set(Calendar.DATE, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Calendar end = DateUtil.getUTCCalendar();
        end.set(Calendar.MONTH, 5 - 1);
        end.set(Calendar.DATE, end.getMaximum(Calendar.DATE));
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 0);
        assertLocationSavingsData(object, start, end);
    }

    /**
     * Location savings accumulated.
     */
    @Test(groups = { "sanity1" })
    public void locationSavingsAccumulated() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationSavingsURL(id), getAccumulatedParams(5), 200);
        JSONObject object = JsonUtil.parseObject(content);
        assertAccumulatedLocationSavingsData(object);
    }

    /**
     * Invalid location savings.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        params.remove(ReportsAPIConfig.ACCUMULATED);
        params.put(ReportsAPIConfig.ACCUMULATED, "");
        String id = reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ID);
        String content = HttpUtil.get(getLocationSavingsURL(id), params, 500);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertTrue(mesg.toString().startsWith(reportsAPIConfig.get(ReportsAPIConfig.INTERNAL_ERROR)));
        }
    }

    /**
     * Invalid location id location savings.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationIdLocationSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        String id = "00";
        String content = HttpUtil.get(getLocationSavingsURL(id), params, 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ERROR), mesg);
        }
    }

    /**
     * Invalid location id location savings accumulated.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationIdLocationSavingsAccumulated() {

        HashMap<String, String> params = getAccumulatedParams(5);
        String id = "00";
        String content = HttpUtil.get(getLocationSavingsURL(id), params, 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.LOCATION_ERROR), mesg);
        }
    }

    /**
     * Invalid location savings accumulated.
     */
    @Test(groups = { "sanity1" })
    public void invalidLocationSavingsAccumulated() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ReportsAPIConfig.ACCUMULATED, "");
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getLocationSavingsURL(id), params, 500);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertTrue(mesg.toString().startsWith(reportsAPIConfig.get(ReportsAPIConfig.INTERNAL_ERROR)));
        }
    }

    /**
     * Missing start date location savings.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateLocationSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getLocationSavingsURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.SVG_START_DATE_ERROR));
        }
    }

    /**
     * Missing end date location savings.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateLocationSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getLocationSavingsURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.SVG_END_DATE_ERROR));
        }
    }

    /**
     * User savings.
     */
    @Test(groups = { "sanity1" })
    public void userSavings() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserSavingsURL(id), getNotAccumulatedParams(5), 200);
        JSONObject object = JsonUtil.parseObject(content);

        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.MONTH, 5 - 1);
        start.set(Calendar.DATE, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Calendar end = DateUtil.getUTCCalendar();
        end.set(Calendar.MONTH, 5 - 1);
        end.set(Calendar.DATE, end.getMaximum(Calendar.DATE));
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 0);
        assertUserSavingsData(object, start, end);
    }

    /**
     * User savings accumulated.
     */
    @Test(groups = { "sanity1" })
    public void userSavingsAccumulated() {

        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserSavingsURL(id), getAccumulatedParams(5), 200);
        JSONObject object = JsonUtil.parseObject(content);

        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.MONTH, 5 - 1);
        start.set(Calendar.DATE, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Calendar end = DateUtil.getUTCCalendar();
        end.set(Calendar.MONTH, 5 - 1);
        end.set(Calendar.DATE, end.getMaximum(Calendar.DATE));
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 0);

        assertAccumulatedUserSavingsData(object, start, end);
    }

    /**
     * Invalid user savings accumulated.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserSavingsAccumulated() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ReportsAPIConfig.ACCUMULATED, "");
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserSavingsURL(id), params, 500);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertTrue(mesg.toString().startsWith(reportsAPIConfig.get(ReportsAPIConfig.INTERNAL_ERROR)));
        }
    }

    /**
     * Invalid user savings.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        params.remove(ReportsAPIConfig.ACCUMULATED);
        params.put(ReportsAPIConfig.ACCUMULATED, "");
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserSavingsURL(id), params, 500);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertTrue(mesg.toString().startsWith(reportsAPIConfig.get(ReportsAPIConfig.INTERNAL_ERROR)));
        }
    }

    /**
     * Invalid user id user savings.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserIdUserSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        String id = "00";
        String content = HttpUtil.get(getUserSavingsURL(id), params, 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.USER_ERROR), mesg);
        }
    }

    /**
     * Invalid user id user savings accumulated.
     */
    @Test(groups = { "sanity1" })
    public void invalidUserIdUserSavingsAccumulated() {

        HashMap<String, String> params = getAccumulatedParams(5);
        String id = "00";
        String content = HttpUtil.get(getUserSavingsURL(id), params, 404);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(reportsAPIConfig.get(ReportsAPIConfig.USER_ERROR), mesg);
        }
    }

    /**
     * Missing start date user savings.
     */
    @Test(groups = { "sanity1" })
    public void missingStartDateUserSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        params.remove(ReportsAPIConfig.START_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserSavingsURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.SVG_START_DATE_ERROR));
        }
    }

    /**
     * Missing end date user savings.
     */
    @Test(groups = { "sanity1" })
    public void missingEndDateUserSavings() {

        HashMap<String, String> params = getNotAccumulatedParams(5);
        params.remove(ReportsAPIConfig.END_DATE);
        String id = reportsAPIConfig.get(ReportsAPIConfig.USER_ID);
        String content = HttpUtil.get(getUserSavingsURL(id), params, 400);
        JSONObject object = JsonUtil.parseObject(content);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG)));
        JSONArray mesgs = (JSONArray) object.get(reportsAPIConfig.get(ReportsAPIConfig.ERROR_MSG));
        for (Object mesg : mesgs) {
            Assert.assertEquals(mesg.toString().trim(), reportsAPIConfig.get(ReportsAPIConfig.SVG_END_DATE_ERROR));
        }
    }

    /**
     * Gets the user usage url.
     * @param id the id
     * @return the user usage url
     */
    private String getUserUsageURL(String id) {

        String url = reportsAPIConfig.get(ReportsAPIConfig.API);
        url += reportsAPIConfig.get(ReportsAPIConfig.USER_USAGE);
        url = url.replaceFirst("id", id);
        return url;
    }

    /**
     * Gets the location usage url.
     * @param id the id
     * @return the location usage url
     */
    private String getLocationUsageURL(String id) {

        String url = reportsAPIConfig.get(ReportsAPIConfig.API);
        url += reportsAPIConfig.get(ReportsAPIConfig.LOCATION_USAGE);
        url = url.replaceFirst("id", id);
        return url;
    }

    /**
     * Gets the user savings url.
     * @param id the id
     * @return the user savings url
     */
    private String getUserSavingsURL(String id) {

        String url = reportsAPIConfig.get(ReportsAPIConfig.API);
        url += reportsAPIConfig.get(ReportsAPIConfig.USER_SAVINGS);
        url = url.replaceFirst("id", id);
        return url;
    }

    /**
     * Gets the location savings url.
     * @param id the id
     * @return the location savings url
     */
    private String getLocationSavingsURL(String id) {

        String url = reportsAPIConfig.get(ReportsAPIConfig.API);
        url += reportsAPIConfig.get(ReportsAPIConfig.LOCATION_SAVINGS);
        url = url.replaceFirst("id", id);
        return url;
    }

    /**
     * Gets the accumulated params.
     * @param month the month
     * @return the accumulated params
     */
    private HashMap<String, String> getAccumulatedParams(int month) {

        HashMap<String, String> params = new HashMap<String, String>();
        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.MONTH, month - 1);
        start.set(Calendar.DATE, 1);
        Calendar end = DateUtil.getUTCCalendar();
        end.set(Calendar.MONTH, month - 1);
        end.set(Calendar.DATE, end.getMaximum(Calendar.DATE));
        params.put(ReportsAPIConfig.START_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.END_DATE, DateUtil.format(end, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.ACCUMULATED, "true");
        return params;
    }

    /**
     * Gets the not accumulated params.
     * @param month the month
     * @return the not accumulated params
     */
    private HashMap<String, String> getNotAccumulatedParams(int month) {

        HashMap<String, String> params = new HashMap<String, String>();
        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.MONTH, month - 1);
        start.set(Calendar.DATE, 1);
        Calendar end = DateUtil.getUTCCalendar();
        end.set(Calendar.MONTH, month - 1);
        end.set(Calendar.DATE, end.getMaximum(Calendar.DATE));
        params.put(ReportsAPIConfig.START_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.END_DATE, DateUtil.format(end, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.ACCUMULATED, "false");
        return params;
    }

    /**
     * Gets the month params.
     * @param month the month
     * @return the month params
     */
    private HashMap<String, String> getMonthParams(int month) {

        HashMap<String, String> params = new HashMap<String, String>();
        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.MONTH, month - 1);
        start.set(Calendar.DATE, 1);
        start.set(Calendar.HOUR, 12);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.AM_PM, 0);
        Calendar end = DateUtil.getUTCCalendar();
        end.set(Calendar.MONTH, month - 1);
        end.set(Calendar.DATE, end.getMaximum(Calendar.DATE));
        end.set(Calendar.HOUR, 12);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        start.set(Calendar.AM_PM, 0);
        params.put(ReportsAPIConfig.START_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.END_DATE, DateUtil.format(end, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.INTERVAL, "MONTHS");
        return params;
    }

    /**
     * Gets the day params.
     * @return the day params
     */
    private HashMap<String, String> getDayParams() {

        HashMap<String, String> params = new HashMap<String, String>();
        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.MONTH, 4);
        start.set(Calendar.AM_PM, 0);
        params.put(ReportsAPIConfig.START_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.END_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.INTERVAL, "DAYS");
        return params;
    }

    /**
     * Gets the hour params.
     * @return the hour params
     */
    private HashMap<String, String> getHourParams() {

        HashMap<String, String> params = new HashMap<String, String>();
        Calendar start = DateUtil.getUTCCalendar();
        start.set(Calendar.DAY_OF_MONTH, 25);
        start.set(Calendar.MONTH, 4);
        start.set(Calendar.HOUR, 12);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.AM_PM, 0);
        params.put(ReportsAPIConfig.START_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.END_DATE, DateUtil.format(start, DateUtil.DATE_FMT));
        params.put(ReportsAPIConfig.INTERVAL, "HOURS");
        return params;
    }

    /**
     * Assert monthly location usage data.
     * @param object the object
     * @param month the month
     * @param year the year
     */
    private void assertMonthlyLocationUsageData(JSONObject object, int month, int year) {

        Assert.assertNotNull(object);
        JSONObject response = (JSONObject) object.get("response");
        int locationId = new Integer(response.get("id").toString());
        List<AggMonthlyStatsThermostatWeather> stats = monthlyReportDao.listByMonthLocation(locationId, month, year);
        double hvacOnTime = 0;

        for (AggMonthlyStatsThermostatWeather stat : stats) {
            hvacOnTime += stat.getHvacStateOnMinutes();
        }

        JSONArray infoList = (JSONArray) response.get("runtimeUsageInfo");
        for (Object info : infoList) {
            JSONObject jsonObject = (JSONObject) info;
            Assert.assertNotNull(jsonObject.get("datetime"));
            Assert.assertNotNull(jsonObject.get("hvacRuntime"));
            Assert.assertEquals(jsonObject.get("hvacRuntime"), hvacOnTime);
            Assert.assertNotNull(jsonObject.get("accessPercentage"));
            Assert.assertNotNull(jsonObject.get("avgIndoorTemperature"));
            Assert.assertNotNull(jsonObject.get("outsideLowTemperature"));
            Assert.assertNotNull(jsonObject.get("outsideHighTemperature"));
            Assert.assertNotNull(jsonObject.get("hasData"));
        }
    }

    /**
     * Assert daily location usage data.
     * @param object the object
     * @param day the day
     * @param month the month
     * @param year the year
     */
    private void assertDailyLocationUsageData(JSONObject object, int day, int month, int year) {

        Assert.assertNotNull(object);
        JSONObject response = (JSONObject) object.get("response");
        int locationId = new Integer(response.get("id").toString());
        List<AggDailyStatsThermostatWeather> stats = dailyReportDao.listByDayLocation(locationId, day, month, year);
        double hvacOnTime = 0;
        double inTempAvg = 0.0;
        double outTempLow = 0.0;
        double outTempHigh = 0.0;
        double accessPercent = 0.0;

        for (AggDailyStatsThermostatWeather stat : stats) {
            hvacOnTime += stat.getHvacStateOnMinutes();
            inTempAvg += stat.getTempInAvg();
            outTempLow += stat.getTempOutLow();
            outTempHigh += stat.getTempOutHigh();
            accessPercent += stat.getAccsPercent();
        }

        inTempAvg = inTempAvg / stats.size();
        outTempLow = outTempLow / stats.size();
        outTempHigh = outTempHigh / stats.size();
        accessPercent = accessPercent / stats.size();

        JSONArray infoList = (JSONArray) response.get("runtimeUsageInfo");
        for (Object info : infoList) {
            JSONObject jsonObject = (JSONObject) info;
            Assert.assertNotNull(jsonObject.get("datetime"));
            Assert.assertNotNull(jsonObject.get("hvacRuntime"));
            Assert.assertEquals(jsonObject.get("hvacRuntime"), hvacOnTime);
            Assert.assertNotNull(jsonObject.get("accessPercentage"));
            Assert.assertEquals(jsonObject.get("accessPercentage"), accessPercent);
            Assert.assertNotNull(jsonObject.get("avgIndoorTemperature"));
            Assert.assertEquals(jsonObject.get("avgIndoorTemperature"), inTempAvg);
            Assert.assertNotNull(jsonObject.get("outsideLowTemperature"));
            Assert.assertEquals(jsonObject.get("outsideLowTemperature"), outTempLow);
            Assert.assertNotNull(jsonObject.get("outsideHighTemperature"));
            Assert.assertEquals(jsonObject.get("outsideHighTemperature"), outTempHigh);
            Assert.assertNotNull(jsonObject.get("hasData"));
        }
    }

    /**
     * Assert hourly location usage data.
     * @param object the object
     * @param hour the hour
     * @param day the day
     * @param month the month
     * @param year the year
     */
    private void assertHourlyLocationUsageData(JSONObject object, int hour, int day, int month, int year) {

        Assert.assertNotNull(object);
        JSONObject response = (JSONObject) object.get("response");
        int locationId = new Integer(response.get("id").toString());
        List<AggHourlyStatsThermostatWeather> stats = hourlyReportDao.listByHourLocation(locationId, hour, day, month, year);
        double hvacOnTime = 0;
        double inTempAvg = 0.0;
        double outTempLow = 0.0;
        double outTempHigh = 0.0;
        double accessPercent = 0.0;

        for (AggHourlyStatsThermostatWeather stat : stats) {
            hvacOnTime += stat.getHvacStateOnMinutes();
            inTempAvg += stat.getTempInAvg();
            outTempLow += stat.getTempOutLow();
            outTempHigh += stat.getTempOutHigh();
            accessPercent += stat.getAccsPercent();
        }

        inTempAvg = inTempAvg / stats.size();
        outTempLow = outTempLow / stats.size();
        outTempHigh = outTempHigh / stats.size();
        accessPercent = accessPercent / stats.size();

        JSONArray infoList = (JSONArray) response.get("runtimeUsageInfo");
        for (Object info : infoList) {
            JSONObject jsonObject = (JSONObject) info;
            Assert.assertNotNull(jsonObject.get("datetime"));
            Assert.assertNotNull(jsonObject.get("hvacRuntime"));
            Assert.assertEquals(jsonObject.get("hvacRuntime"), hvacOnTime);
            Assert.assertNotNull(jsonObject.get("accessPercentage"));
            Assert.assertEquals(jsonObject.get("accessPercentage"), accessPercent);
            Assert.assertNotNull(jsonObject.get("avgIndoorTemperature"));
            Assert.assertEquals(jsonObject.get("avgIndoorTemperature"), inTempAvg);
            Assert.assertNotNull(jsonObject.get("outsideLowTemperature"));
            Assert.assertEquals(jsonObject.get("outsideLowTemperature"), outTempLow);
            Assert.assertNotNull(jsonObject.get("outsideHighTemperature"));
            Assert.assertEquals(jsonObject.get("outsideHighTemperature"), outTempHigh);
            Assert.assertNotNull(jsonObject.get("hasData"));
        }
    }

    /**
     * Assert monthly user usage data.
     * @param object the object
     * @param month the month
     * @param year the year
     */
    private void assertMonthlyUserUsageData(JSONObject object, int month, int year) {

        Assert.assertNotNull(object);
        JSONObject response = (JSONObject) object.get("response");
        int userId = new Integer(response.get("userId").toString());
        List<AggMonthlyStatsThermostatWeather> stats = monthlyReportDao.listByMonthUser(userId, month, year);
        double hvacOnTime = 0;

        for (AggMonthlyStatsThermostatWeather stat : stats) {
            hvacOnTime += stat.getHvacStateOnMinutes();
        }

        JSONArray array = (JSONArray) response.get("locationsRuntimeUsageInfoList");
        JSONArray infoList = (JSONArray) ((JSONObject) array.get(0)).get("runtimeUsageInfo");

        for (Object info : infoList) {
            JSONObject jsonObject = (JSONObject) info;
            Assert.assertNotNull(jsonObject.get("datetime"));
            Assert.assertNotNull(jsonObject.get("hvacRuntime"));
            Assert.assertEquals(jsonObject.get("hvacRuntime"), hvacOnTime);
            Assert.assertNotNull(jsonObject.get("accessPercentage"));
            Assert.assertNotNull(jsonObject.get("avgIndoorTemperature"));
            Assert.assertNotNull(jsonObject.get("outsideLowTemperature"));
            Assert.assertNotNull(jsonObject.get("outsideHighTemperature"));
            Assert.assertNotNull(jsonObject.get("hasData"));
        }
    }

    /**
     * Assert daily user usage data.
     * @param object the object
     * @param day the day
     * @param month the month
     * @param year the year
     */
    private void assertDailyUserUsageData(JSONObject object, int day, int month, int year) {

        Assert.assertNotNull(object);
        JSONObject response = (JSONObject) object.get("response");
        int userId = new Integer(response.get("userId").toString());
        List<AggDailyStatsThermostatWeather> stats = dailyReportDao.listByDayUser(userId, day, month, year);
        double hvacOnTime = 0;
        double inTempAvg = 0.0;
        double outTempLow = 0.0;
        double outTempHigh = 0.0;
        double accessPercent = 0.0;

        for (AggDailyStatsThermostatWeather stat : stats) {
            hvacOnTime += stat.getHvacStateOnMinutes();
            inTempAvg += stat.getTempInAvg();
            outTempLow += stat.getTempOutLow();
            outTempHigh += stat.getTempOutHigh();
            accessPercent += stat.getAccsPercent();
        }

        inTempAvg = inTempAvg / stats.size();
        outTempLow = outTempLow / stats.size();
        outTempHigh = outTempHigh / stats.size();
        accessPercent = accessPercent / stats.size();

        JSONArray array = (JSONArray) response.get("locationsRuntimeUsageInfoList");
        JSONArray infoList = (JSONArray) ((JSONObject) array.get(0)).get("runtimeUsageInfo");

        for (Object info : infoList) {
            JSONObject jsonObject = (JSONObject) info;
            Assert.assertNotNull(jsonObject.get("datetime"));
            Assert.assertNotNull(jsonObject.get("hvacRuntime"));
            Assert.assertEquals(jsonObject.get("hvacRuntime"), hvacOnTime);
            Assert.assertNotNull(jsonObject.get("accessPercentage"));
            Assert.assertEquals(jsonObject.get("accessPercentage"), accessPercent);
            Assert.assertNotNull(jsonObject.get("avgIndoorTemperature"));
            Assert.assertEquals(jsonObject.get("avgIndoorTemperature"), inTempAvg);
            Assert.assertNotNull(jsonObject.get("outsideLowTemperature"));
            Assert.assertEquals(jsonObject.get("outsideLowTemperature"), outTempLow);
            Assert.assertNotNull(jsonObject.get("outsideHighTemperature"));
            Assert.assertEquals(jsonObject.get("outsideHighTemperature"), outTempHigh);
            Assert.assertNotNull(jsonObject.get("hasData"));
        }
    }

    /**
     * Assert hourly user usage data.
     * @param object the object
     * @param hour the hour
     * @param day the day
     * @param month the month
     * @param year the year
     */
    private void assertHourlyUserUsageData(JSONObject object, int hour, int day, int month, int year) {

        Assert.assertNotNull(object);
        JSONObject response = (JSONObject) object.get("response");
        int userId = new Integer(response.get("userId").toString());
        List<AggHourlyStatsThermostatWeather> stats = hourlyReportDao.listByHourUser(userId, hour, day, month, year);
        double hvacOnTime = 0;
        double inTempAvg = 0.0;
        double outTempLow = 0.0;
        double outTempHigh = 0.0;
        double accessPercent = 0.0;

        for (AggHourlyStatsThermostatWeather stat : stats) {
            hvacOnTime += stat.getHvacStateOnMinutes();
            inTempAvg += stat.getTempInAvg();
            outTempLow += stat.getTempOutLow();
            outTempHigh += stat.getTempOutHigh();
            accessPercent += stat.getAccsPercent();
        }

        inTempAvg = inTempAvg / stats.size();
        outTempLow = outTempLow / stats.size();
        outTempHigh = outTempHigh / stats.size();
        accessPercent = accessPercent / stats.size();

        JSONArray array = (JSONArray) response.get("locationsRuntimeUsageInfoList");
        JSONArray infoList = (JSONArray) ((JSONObject) array.get(0)).get("runtimeUsageInfo");

        for (Object info : infoList) {
            JSONObject jsonObject = (JSONObject) info;
            Assert.assertNotNull(jsonObject.get("datetime"));
            Assert.assertNotNull(jsonObject.get("hvacRuntime"));
            Assert.assertEquals(jsonObject.get("hvacRuntime"), hvacOnTime);
            Assert.assertNotNull(jsonObject.get("accessPercentage"));
            Assert.assertEquals(jsonObject.get("accessPercentage"), accessPercent);
            Assert.assertNotNull(jsonObject.get("avgIndoorTemperature"));
            Assert.assertEquals(jsonObject.get("avgIndoorTemperature"), inTempAvg);
            Assert.assertNotNull(jsonObject.get("outsideLowTemperature"));
            Assert.assertEquals(jsonObject.get("outsideLowTemperature"), outTempLow);
            Assert.assertNotNull(jsonObject.get("outsideHighTemperature"));
            Assert.assertEquals(jsonObject.get("outsideHighTemperature"), outTempHigh);
            Assert.assertNotNull(jsonObject.get("hasData"));
        }
    }

    /**
     * Assert location savings data.
     * @param object the object
     * @param startDate the start date
     * @param endDate the end date
     */
    private void assertLocationSavingsData(JSONObject object, Calendar startDate, Calendar endDate) {

        Assert.assertNotNull(object);
        JSONArray array = (JSONArray) object.get("response");
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray list = (JSONArray) jsonObject.get("thermostatEnergySavingsList");
            for (Object aObj : list) {
                JSONObject json = (JSONObject) aObj;
                Assert.assertNotNull(json.get("thermostatId"));
                int thermostatId = new Integer(json.get("thermostatId").toString());
                Assert.assertNotNull(json.get("runtimeWithEcoFactor"));
                double runtime = new Double(json.get("runtimeWithEcoFactor").toString());
                Assert.assertNotNull(jsonObject.get("runtimeSavings"));
                double savings = new Double(json.get("runtimeSavings").toString());
                verifyDB(thermostatId, startDate, endDate, runtime, savings);
            }
        }
    }

    /**
     * Assert accumulated location savings data.
     * @param object the object
     */
    private void assertAccumulatedLocationSavingsData(JSONObject object) {

        int thermostatId = 0;
        double runtime = 0;
        double savings = 0;

        Assert.assertNotNull(object);
        JSONObject obj = (JSONObject) object.get("response");
        JSONArray list = (JSONArray) obj.get("locationEnergySavingsInfo");
        for (Object aObj : list) {
            JSONObject jsonObject = (JSONObject) aObj;
            JSONArray thermostatList = (JSONArray) jsonObject.get("thermostatEnergySavingsList");
            for (Object thermostat : thermostatList) {
                JSONObject json = (JSONObject) thermostat;
                Assert.assertNotNull(json.get("thermostatId"));
                thermostatId = new Integer(json.get("thermostatId").toString());
                Assert.assertNotNull(json.get("runtimeWithEcoFactor"));
                runtime += new Double(json.get("runtimeWithEcoFactor").toString());
                Assert.assertNotNull(jsonObject.get("runtimeSavings"));
                savings += new Double(json.get("runtimeSavings").toString());
            }
        }

        verifyDB(thermostatId, runtime, savings);
    }

    /**
     * Assert user savings data.
     * @param object the object
     * @param startDate the start date
     * @param endDate the end date
     */
    private void assertUserSavingsData(JSONObject object, Calendar startDate, Calendar endDate) {

        Assert.assertNotNull(object);
        JSONArray array = (JSONArray) ((JSONObject) object.get("response")).get("locationEnergySavingsInfoList");
        for (Object obj : array) {
            JSONArray list = (JSONArray) obj;
            for (Object aObj : list) {
                JSONObject jsonObject = (JSONObject) aObj;
                JSONArray thermostatList = (JSONArray) jsonObject.get("thermostatEnergySavingsList");
                for (Object thermostat : thermostatList) {
                    JSONObject json = (JSONObject) thermostat;
                    Assert.assertNotNull(json.get("thermostatId"));
                    int thermostatId = new Integer(json.get("thermostatId").toString());
                    Assert.assertNotNull(json.get("runtimeWithEcoFactor"));
                    double runtime = new Double(json.get("runtimeWithEcoFactor").toString());
                    Assert.assertNotNull(jsonObject.get("runtimeSavings"));
                    double savings = new Double(json.get("runtimeSavings").toString());
                    verifyDB(thermostatId, startDate, endDate, runtime, savings);
                }
            }
        }
    }

    /**
     * Verify db.
     * @param thermostatId the thermostat id
     * @param startDate the start date
     * @param endDate the end date
     * @param runtime the runtime
     * @param savings the savings
     */
    private void verifyDB(int thermostatId, Calendar startDate, Calendar endDate, double runtime, double savings) {

        List<QuantReport> reports = savingsReportDao.listThermostatSavings(thermostatId, startDate, endDate);
        for (QuantReport report : reports) {
            String data = report.getReportData();
            JSONObject object = JsonUtil.parseObject(data);
            JSONObject heat = (JSONObject) object.get("Heat");
            JSONObject cool = (JSONObject) object.get("Cool");

            double totalRuntimeHours = new Double(heat.get("fullRuntimeHours").toString()) + new Double(cool.get("fullRuntimeHours").toString());
            double totalSavedHours = new Double(heat.get("fullSavedHours").toString()) + new Double(cool.get("fullSavedHours").toString());

            Assert.assertEquals(runtime, totalRuntimeHours);
            Assert.assertEquals(savings, totalSavedHours);
        }
    }

    /**
     * Assert accumulated user savings data.
     * @param object the object
     * @param startDate the start date
     * @param endDate the end date
     */
    private void assertAccumulatedUserSavingsData(JSONObject object, Calendar startDate, Calendar endDate) {

        Assert.assertNotNull(object);

        int thermostatId = 0;
        double runtime = 0;
        double savings = 0;

        JSONArray array = (JSONArray) ((JSONObject) object.get("response")).get("locationEnergySavingsInfoList");
        for (Object obj : array) {
            JSONArray list = (JSONArray) ((JSONObject) obj).get("locationEnergySavingsInfo");
            for (Object aObj : list) {
                JSONObject jsonObject = (JSONObject) aObj;
                JSONArray thermostatList = (JSONArray) jsonObject.get("thermostatEnergySavingsList");
                for (Object thermostat : thermostatList) {
                    JSONObject json = (JSONObject) thermostat;
                    Assert.assertNotNull(json.get("thermostatId"));
                    thermostatId = new Integer(json.get("thermostatId").toString());
                    Assert.assertNotNull(json.get("runtimeWithEcoFactor"));
                    runtime += new Double(json.get("runtimeWithEcoFactor").toString());
                    Assert.assertNotNull(jsonObject.get("runtimeSavings"));
                    savings += new Double(json.get("runtimeSavings").toString());
                }
            }
        }

        verifyDB(thermostatId, runtime, savings);
    }

    /**
     * Verify db.
     * @param thermostatId the thermostat id
     * @param runtime the runtime
     * @param savings the savings
     */
    private void verifyDB(int thermostatId, double runtime, double savings) {

        double totalSavedHours = 0;

        List<QuantReport> reports = savingsReportDao.listThermostatSavings(thermostatId);
        for (QuantReport report : reports) {
            String data = report.getReportData();
            JSONObject object = JsonUtil.parseObject(data);
            JSONObject heat = (JSONObject) object.get("Heat");
            JSONObject cool = (JSONObject) object.get("Cool");

            totalSavedHours += (new Double(heat.get("fullSavedHours").toString()) + new Double(cool.get("fullSavedHours").toString()));
        }

        Assert.assertEquals(savings, totalSavedHours);
    }
}
