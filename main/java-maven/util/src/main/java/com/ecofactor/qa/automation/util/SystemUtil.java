package com.ecofactor.qa.automation.util;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SystemUtil.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SystemUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtil.class);

    /**
     * Instantiates a new system util.
     */
    private SystemUtil() {

    }

    /**
     * Gets the property.
     * 
     * @param property the property
     * @return the property
     */
    public static String getProperty(String property) {

        String value = System.getProperty(property);
        return value == null || value.isEmpty() ? "-" : value;
    }

    /**
     * Gets the system os.
     * 
     * @return the system os
     */
    public static String getSystemOs() {

        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * Checks if is windows.
     * 
     * @return true, if is windows
     */
    public static boolean isWindows() {

        return DesktopOSType.WINDOWS == getOSType();
    }

    /**
     * Checks if is mac.
     * 
     * @return true, if is mac
     */
    public static boolean isMac() {

        return DesktopOSType.MAC == getOSType();
    }

    /**
     * Checks if is unix.
     * 
     * @return true, if is unix
     */
    public static boolean isUnix() {

        return DesktopOSType.LINUX == getOSType();
    }

    /**
     * Gets the system os type as an enum.
     * 
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

    /**
     * Gets the host name.
     * 
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
     * The Enum DesktopOSType.
     * 
     * @author $Author:$
     * @version $Rev:$ $Date:$
     */
    public enum DesktopOSType {

        WINDOWS, LINUX, MAC
    }

}
