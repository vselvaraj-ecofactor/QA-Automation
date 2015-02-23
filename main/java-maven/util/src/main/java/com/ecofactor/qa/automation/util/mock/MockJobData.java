/*
 * MockJobData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mock;

import java.util.Calendar;

import com.ecofactor.qa.automation.util.DateUtil;

/**
 * The Class MockJobData.
 * @author $Author:$
 * @version $Rev:$ $Calendar:$
 */
public class MockJobData {

    private Calendar cutoff;
    private int blackout;
    private int recovery;
    private Double delta = 1.0;
    private Calendar start;
    private Calendar end;

    /**
     * Gets the cutoff.
     * @return the cutoff
     */
    public Calendar getCutoff() {

        return cutoff;
    }

    /**
     * Sets the cutoff.
     * @param cutoff the new cutoff
     */
    public void setCutoff(Calendar cutoff) {

        this.cutoff = cutoff;
    }

    /**
     * Gets the blackout.
     * @return the blackout
     */
    public int getBlackout() {

        return blackout;
    }

    /**
     * Sets the blackout.
     * @param blackout the new blackout
     */
    public void setBlackout(int blackout) {

        this.blackout = blackout;
    }

    /**
     * Gets the recovery.
     * @return the recovery
     */
    public int getRecovery() {

        return recovery;
    }

    /**
     * Sets the recovery.
     * @param recovery the new recovery
     */
    public void setRecovery(int recovery) {

        this.recovery = recovery;
    }

    /**
     * Gets the delta.
     * @return the delta
     */
    public Double getDelta() {

        return delta;
    }

    /**
     * Sets the delta.
     * @param delta the new delta
     */
    public void setDelta(Double delta) {

        this.delta = delta;
    }

    /**
     * Gets the start.
     * @return the start
     */
    public Calendar getStart() {

        return start;
    }

    /**
     * Sets the start.
     * @param start the new start
     */
    public void setStart(Calendar start) {

        this.start = start;
    }

    /**
     * Gets the end.
     * @return the end
     */
    public Calendar getEnd() {

        return end;
    }

    /**
     * Sets the end.
     * @param end the new end
     */
    public void setEnd(Calendar end) {

        this.end = end;
    }

    public String toString() {

        StringBuffer buffer = new StringBuffer("|");
        buffer.append(DateUtil.format(start, "HH:mm"));
        buffer.append("|");
        buffer.append(DateUtil.format(end, "HH:mm"));
        buffer.append("|");
        buffer.append(delta);
        buffer.append("|");
        buffer.append(cutoff == null ? "00:00" : DateUtil.format(end, "HH:mm"));
        buffer.append("|");
        buffer.append(blackout);
        buffer.append("|");
        buffer.append(recovery);
        buffer.append("|");
        return buffer.toString();
    }
}
