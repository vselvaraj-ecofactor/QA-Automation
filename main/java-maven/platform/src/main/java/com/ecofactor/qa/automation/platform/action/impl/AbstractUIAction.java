package com.ecofactor.qa.automation.platform.action.impl;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.FileUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.action.UIAction;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.google.inject.Inject;

/**
 * The Class AbstractUIAction.
 * @author $Author: vprasannaa $
 * @version $Rev: 32085 $ $Date: 2014-09-22 18:50:18 +0530 (Mon, 22 Sep 2014) $
 */
public abstract class AbstractUIAction implements UIAction {

    /**
     * The driver ops.
     */
    @Inject
    protected TestOperations driverOps;

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUIAction.class);

    /**
     * The swiperight.
     */
    private static final String SWIPERIGHT = ".trigger('swiperight');";

    /**
     * The swipeleft.
     */
    private static final String SWIPELEFT = ".trigger('swipeleft');";

    /**
     * The custom screenshot folder.
     */
    private String screenshotFolder;

    /**
     * The Enum Direction.
     * @author $Author: vprasannaa $
     * @version $Rev: 32085 $ $Date: 2014-09-22 18:50:18 +0530 (Mon, 22 Sep 2014) $
     */
    protected enum Direction {
        LEFT, RIGHT, UP, DOWN;
    }

    /**
     * Click.
     * @param element the element
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#click(org.openqa.selenium.WebElement)
     */
    @Override
    public void click(final WebElement element) {

        saveTestFlowScreenShot();
        element.click();
    }

    /**
     * Save test flow screen shot.
     */
    protected void saveTestFlowScreenShot() {

        if (hasJenkinsSaveAllScreensParam()) {

            try {
                checkScreenshotFolder();
                final String dir = System.getProperty("user.dir");
                final Path folderName = Paths.get(dir, getCustomScreenshotFolder(), "screenshots",
                        getTestName());
                final Path subFolder = Files.createDirectories(folderName);
                final File[] noOfScreenShots = subFolder.toFile().listFiles();
                final int fileName = noOfScreenShots.length + 1;
                driverOps.takeScreenShot(getCustomScreenshotFolder(), "screenshots", getTestName(),
                        String.valueOf(fileName));
            } catch (final Exception e) {
                LOGGER.error("Error in saveTestFlowScreenShot(). Cause ::: " + e);
            }
        }
    }

    /**
     * Check screenshot folder.
     */
    private void checkScreenshotFolder() {

        if (getCustomScreenshotFolder() == null) {
            setCustomScreenshotFolder(nameScreenshotFolder());
        }
    }

    /**
     * Name screenshot folder.
     * @return the string
     */
    private String nameScreenshotFolder() {

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        final String deviceBrandModel = hasJenkinsMobileParams() ? driverOps
                .getDeviceBrandModel(System.getProperty("deviceId")) : "WEB";
        final String deviceNumber = hasJenkinsDeviceNumber() ? System.getProperty("deviceNumber")
                : deviceBrandModel.equalsIgnoreCase("WEB") ? "DESKTOP" : "NA";
        final String buildId = hasJenkinsBuildId() ? getBuildId(System.getProperty("buildId"))
                : format.format(new Date());
        final String buildNumber = hasJenkinsBuildNumber() ? System.getProperty("buildNumber")
                : "NA";
        final String jobName = hasJenkinsJobName() ? System.getProperty("jobName").replace("Test",
                "") : "NA";
        final StringBuilder buildFolderName = new StringBuilder().append(buildNumber).append("_")
                .append(jobName).append("_").append(deviceNumber).append("_")
                .append(deviceBrandModel).append("_").append(buildId);
        return buildFolderName.toString();
    }

    /**
     * Gets the build id.
     * @param buildId the build id
     * @return the build id
     */
    private String getBuildId(final String buildId) {

        final SimpleDateFormat format = new SimpleDateFormat("MMM-dd_HH-mm_a", Locale.US);
        final LocalDateTime utcDateTime = LocalDateTime.parse(buildId,
                DateTimeFormat.forPattern("YYYY-MM-dd_HH-mm-ss"));
        return format.format(convertTimezone(utcDateTime, "UTC", "America/Los_Angeles"));
    }

    /**
     * Do swipe left.
     * @param element the element
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeLeft(org.openqa.selenium.WebElement)
     */
    @Override
    public void doSwipeLeft(final WebElement element) {

        saveTestFlowScreenShot();
        final String jsToExecute = "window.jQuery(document.elementFromPoint("
                + getElementXPosition(element) + "," + getElementYPosition(element) + "))"
                + SWIPELEFT;
        ((JavascriptExecutor) driverOps.getDeviceDriver()).executeScript(jsToExecute);
    }

    /**
     * Do swipe right.
     * @param element the element
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeRight(org.openqa.selenium.WebElement)
     */
    @Override
    public void doSwipeRight(final WebElement element) {

        saveTestFlowScreenShot();
        final String javascript = "window.jQuery(document.elementFromPoint("
                + getElementXPosition(element) + "," + getElementYPosition(element) + "))"
                + SWIPERIGHT;
        ((JavascriptExecutor) driverOps.getDeviceDriver()).executeScript(javascript);
    }    

    /**
     * Return the x coordinate of the element in the screen.
     * @param elementToLocate the element to locate
     * @return the element x position
     */
    protected int getElementXPosition(final WebElement elementToLocate) {

        return elementToLocate.getLocation().getX();
    }

    /**
     * Return the y coordinate of the element in the screen.
     * @param elementToLocate the element to locate
     * @return the element y position
     */
    protected int getElementYPosition(final WebElement elementToLocate) {

        return elementToLocate.getLocation().getY();
    }

    /**
     * Gets the custom screenshot folder.
     * @return the custom screenshot folder
     */
    public String getCustomScreenshotFolder() {

        LOGGER.info("Custom screenshots folder for the build was set to : " + screenshotFolder);
        return this.screenshotFolder;
    }

    /**
     * Sets the custom screenshot folder.
     * @param folderName the folder name
     * @return the string
     */
    public void setCustomScreenshotFolder(final String folderName) {

        setLogString("Setting custom screenshots folder for the build to : " + folderName, true);
        if (folderName.contains("NA")) {
            LOGGER.warn("Warning! mandatory fields for custom screen shots feature like build id/build number/device number parameter(s) was found missing, moving"
                    + " the screenshots for the build to " + folderName);
        }
        this.screenshotFolder = folderName;
    }

    /**
     * Clear custom screenshot folder.
     */
    public void clearCustomScreenshotFolder() {

        try {
            if (System.getProperty("saveAllScreens") != null && getCustomScreenshotFolder() != null) {
                setLogString("Archieving custom screenshots folder for the build.", true);
                final String dir = System.getProperty("user.dir");
                final Path archives = Paths.get(dir, "archives");
                final Path archivesDir = Files.createDirectories(archives);
                final String customScreens = Paths.get(dir, getCustomScreenshotFolder()).toString();
                zipFolder(customScreens, archivesDir.toString() + "/" + getCustomScreenshotFolder()
                        + ".zip");
                setLogString("Clearing custom screenshots folder for the build.", true);
                clearCustomScreenFolders();
                this.screenshotFolder = null;
            }
        } catch (final Exception e) {
            LOGGER.error("Error creating archive file.", e);
        }
    }

    /**
     * Clear custom screen folders.
     */
    private void clearCustomScreenFolders() {

        try {
            final String dir = System.getProperty("user.dir");
            final Path customFolder = Paths.get(dir, getCustomScreenshotFolder());
            final Path customScreens = Paths.get(customFolder.toString(), "screenshots");
            final File[] testFolders = customScreens.toFile().listFiles();
            for (final File file : testFolders) {
                final Path testCaseDirectory = Paths.get(file.toString());
                deleteFolder(testCaseDirectory.toFile());
            }
            Files.delete(customScreens);
            Files.delete(customFolder);
        } catch (final Exception e) {
            LOGGER.error("Exception in clearCustomScreenFolders(). Cause ::: " + e);
        }
    }

    /**
     * Convert joda timezone.
     * @param date the date
     * @param srcTimeZone the src time zone
     * @param destTimeZone the dest time zone
     * @return the date
     */
    public Date convertTimezone(final LocalDateTime date, final String srcTimeZone,
            final String destTimeZone) {

        final DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(srcTimeZone));
        final DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(destTimeZone));
        return dstDateTime.toLocalDateTime().toDateTime().toDate();
    }
}
