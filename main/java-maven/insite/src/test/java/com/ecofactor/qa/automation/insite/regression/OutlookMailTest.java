package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static org.testng.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.mail.OutlookMail;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class OutlookMailTest {

	@Inject
	private InsiteConfig appConfig;
	@Inject
	private OutlookMail outlookMail;
	@Inject
	private InsiteLogin insiteLogin;
	private static final Logger logger = LoggerFactory.getLogger(OutlookMailTest.class);

	@BeforeSuite(alwaysRun = true)
	public void initSuite() {
		HttpURLConnection urlConnection = null;
		String insiteURLString = null;
		int status = -1;
		try {
			insiteURLString = appConfig.get(INSITE_URL)
					+ appConfig.get(INSITE_LOGIN_URL);
			URL insiteURL = new URL(insiteURLString);
			urlConnection = (HttpURLConnection) insiteURL.openConnection();
			urlConnection.setReadTimeout(5000);
			status = urlConnection.getResponseCode();
		} catch (IOException e) {
			if (status != HttpURLConnection.HTTP_OK) {
				fail("Unable to connect insite portal '" + insiteURLString
						+ "'. The site is down!");
			}
		}
	}


	@Test(groups = {"mailTest" })
	public void getChangedPassword() {
		String url = "https://login.microsoftonline.com/";
		String emailUserName = "ecofactorqaautomation@aximsoft.com";
		String emailPassword = "Axims0ft123";
		String subject = "Welcome to EcoFactor Insite - Your Temporary Password";
		int boldIndex = 0;
		String newPswd=outlookMail.getChangedPassword(url, emailUserName, emailPassword, subject, boldIndex);
		DriverConfig.setLogString("newPswd: "+newPswd,true);
	}

	@AfterClass(alwaysRun = true)
    public void endClass() {

        try {
        	insiteLogin.logout();
        } catch (Throwable e) {
            logger.error("Error in after class " + e.getMessage(), true);
        }
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
        	insiteLogin.end();
        } catch (Throwable e) {
            logger.error("Error in after suite " + e.getMessage(), true);
        }
    }

}
