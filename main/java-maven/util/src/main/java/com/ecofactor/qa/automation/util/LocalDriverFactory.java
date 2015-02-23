package com.ecofactor.qa.automation.util;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

/**
 * A factory for creating LocalDriver objects.
 */
public final class LocalDriverFactory {

    public static final String BROWSER = "browser";
    public static final String FIREFOX = "firefox";
    public static final String IE = "ie";
    public static final String IE8 = "ie8";
    public static final String IE9 = "ie9";
    public static final String VISTAIE8 = "vistaie8";
    public static final String IE10 = "ie10";
    public static final String CHROME = "chrome";
    public static final String SAFARI = "safari";

    /**
     * Creates a new LocalDriver object.
     * @param browserName the browser name
     * @return the web driver
     */
    public static WebDriver createInstance(String browserName) {

        browserName = browserName.toLowerCase();
        WebDriver driver = null;
        if (browserName.equals(FIREFOX) || browserName.contains(FIREFOX)) {
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("browser.cache.disk.enable", false);
            firefoxProfile.setPreference("browser.cache.memory.enable", false);
            firefoxProfile.setPreference("browser.cache.offline.enable", false);
            firefoxProfile.setPreference("network.http.use-cache", false);
            // for Unresponsive Script Error
            firefoxProfile.setPreference("dom.max_chrome_script_run_time", 0);
            firefoxProfile.setPreference("dom.max_script_run_time", 0);
            String nodeName = System.getenv("NODE_NAME");
            if (nodeName != null && !nodeName.isEmpty()) {
                FirefoxBinary binary = new FirefoxBinary(new File(
                        "C:/Program Files (x86)/Mozilla Firefox/firefox.exe"));
                driver = new FirefoxDriver(binary, firefoxProfile);
            } else {
                driver = new FirefoxDriver(firefoxProfile);
            }

        } else if (browserName.equals(IE) || browserName.equals(IE8) || browserName.equals(IE9)
                || browserName.equals(IE10) || browserName.equals(VISTAIE8)
                || browserName.contains(IE)) {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(
                    InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
            driver = new InternetExplorerDriver(capabilities);
        } else if (browserName.equals(CHROME) || browserName.contains(CHROME)) {
            driver = new ChromeDriver();
        } else if (browserName.equals(SAFARI) || browserName.contains(SAFARI)) {
            driver = new SafariDriver();
        }
        return driver;
    }
}