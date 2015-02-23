package com.ecofactor.qa.automation.platform;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * The Class SwipeableWebDriver.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SwipeableWebDriver extends RemoteWebDriver implements HasTouchScreen, TakesScreenshot {
    private RemoteTouchScreen touch;

    /**
     * Instantiates a new swipeable web driver.
     */
    public SwipeableWebDriver() {

        super();
    }

    /**
     * Instantiates a new swipeable web driver.
     * @param remoteAddress the remote address
     * @param desiredCapabilities the desired capabilities
     */
    public SwipeableWebDriver(final URL remoteAddress, final Capabilities capabilities) {

        super(remoteAddress, capabilities);
        touch = new RemoteTouchScreen(getExecuteMethod());
    }

    /**
     * Gets the touch.
     * @return the touch
     * @see org.openqa.selenium.HasTouchScreen#getTouch()
     */
    public TouchScreen getTouch() {

        return touch;
    }

    /**
     * Gets the screenshot as.
     * @param <X> the
     * @param target the target
     * @return the screenshot as
     * @throws WebDriverException the web driver exception
     * @see org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)
     */
    @Override
    public <X> X getScreenshotAs(final OutputType<X> target) throws WebDriverException {

        final String base64Png = execute(DriverCommand.SCREENSHOT).getValue().toString();
        return target.convertFromBase64Png(base64Png);
    }
}
