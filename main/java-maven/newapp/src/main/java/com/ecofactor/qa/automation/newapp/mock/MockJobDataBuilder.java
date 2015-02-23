/*
 * MockJobDataBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.ecofactor.qa.automation.util.mock.MockJobData;
import com.google.inject.Inject;

/**
 * The Class MockJobDataBuilder.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MockJobDataBuilder {

    @Inject
    private MockJobDataConfig mockJobDataConfig;

    /**
     * Builds the.
     * @param blocks the blocks
     * @param duration the duration
     * @param gap the gap
     * @param position the position
     * @param back the back
     * @param next the next
     * @param timeZoneId the time zone id
     * @return the list
     */
    public List<MockJobData> build(int blocks, int duration, int gap, int position, int back, int next, int blackout, int recovery, String timeZoneId) {

        List<MockJobData> jobData = new ArrayList<MockJobData>();
        Calendar start = (Calendar) Calendar.getInstance(TimeZone.getTimeZone("UTC")).clone();

        if (position == 0) {
            if (next > 0) {
                start.add(Calendar.MINUTE, next);
            }
        } else if (position > blocks) {
            if (back > 0) {
                start.add(Calendar.MINUTE, -back);
                for (int y = blocks; y > 0; y--) {
                    if (gap > 0 && y != blocks) {
                        start.add(Calendar.MINUTE, -gap);
                    }
                    start.add(Calendar.MINUTE, -duration);
                }
            }
        } else {
            if (next > 0) {
                start.add(Calendar.MINUTE, next);
                for (int x = position; x > 0; x--) {
                    if (gap > 0 && x != position) {
                        start.add(Calendar.MINUTE, -gap);
                    }
                    start.add(Calendar.MINUTE, -duration);
                }
            }

        }

        for (int i = 1; i <= blocks; ++i) {
            MockJobData mockJobData = new MockJobData();
            start = (Calendar) start.clone();

            if (i != 1 && gap > 0) {
                start.add(Calendar.MINUTE, gap);
            }

            mockJobData.setStart(start);
            start = (Calendar) start.clone();
            start.add(Calendar.MINUTE, duration);
            mockJobData.setEnd(start);
            mockJobData.setBlackout(blackout);
            mockJobData.setRecovery(recovery);
            mockJobData.setDelta(Double.valueOf(i));
            jobData.add(mockJobData);
        }

        return jobData;
    }

    /**
     * Builds the.
     * @param method the method
     * @param timeZoneId the time zone id
     * @return the list
     */
    public List<MockJobData> build(String method, String timeZoneId) {

        int blocks = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_BLOCKS);
        int duration = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_DURATION);
        int gap = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_GAP);
        int position = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_POSITION);
        int back = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_BACK);
        int next = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_NEXT);
        int blackout = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_BLACKOUT);
        int recovery = mockJobDataConfig.getInt(method + MockJobDataConfig.TEST_RECOVERY);

        return build(blocks, duration, gap, position, back, next, blackout, recovery, timeZoneId);
    }

    /**
     * Gets the base temp.
     * @param method the method
     * @return the base temp
     */
    public Double getBaseTemp(String method) {

        double baseTemp = mockJobDataConfig.getDouble(method + MockJobDataConfig.TEST_BASE_TEMP);
        return baseTemp;
    }
}
