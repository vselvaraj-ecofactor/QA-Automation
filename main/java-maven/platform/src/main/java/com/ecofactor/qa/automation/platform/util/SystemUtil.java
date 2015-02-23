/*
 * SystemUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.enums.DesktopOSType;

/**
 * The Class SystemUtil. It has util methods to obtain information from the System/PC.
 * @author $Author: vprasannaa $
 * @version $Rev: 30842 $ $Date: 2014-05-28 12:46:31 +0530 (Wed, 28 May 2014) $
 */
public final class SystemUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtil.class);

    /**
     * Instantiates a new system util.
     */
    private SystemUtil() {

        super();
    }

    /**
     * Gets the host name.
     * @return the host name
     */
    public static String getHostName() {

        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.error("Could not obtain the host name of the System.", e);
            return "localhost";
        }
    }

    /**
     * Gets the system os.
     * @return the system os
     */
    public static String getSystemOs() {

        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * Checks if is windows.
     * @return true, if is windows
     */
    public static boolean isWindows() {

        return DesktopOSType.WINDOWS == getOSType();
    }

    /**
     * Checks if is mac.
     * @return true, if is mac
     */
    public static boolean isMac() {

        return DesktopOSType.MAC == getOSType();
    }

    /**
     * Checks if is unix.
     * @return true, if is unix
     */
    public static boolean isUnix() {

        return DesktopOSType.LINUX == getOSType();
    }

    /**
     * Gets the system os type as an enum.
     * @return the system os type
     */
    public static DesktopOSType getOSType() {

        if (getSystemOs().contains("mac")) {
            return DesktopOSType.MAC;
        } else if (getSystemOs().contains("nux")) {
            return DesktopOSType.LINUX;
        }
        return DesktopOSType.WINDOWS;
    }

}
