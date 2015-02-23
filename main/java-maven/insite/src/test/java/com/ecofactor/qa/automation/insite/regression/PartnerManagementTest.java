package com.ecofactor.qa.automation.insite.regression;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.data.PartnerDataProvider;

import com.ecofactor.qa.automation.insite.page.PartnerManagement;

import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;

import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class PartnerManagementTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class PartnerManagementTest {

    /** The current date. */
    Date currentDate = new Date();

    /** The formatter. */
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    /** The date time stamp. */
    String dateTimeStamp = formatter.format(currentDate);

    /** The test log util. */
    @Inject
    private TestLogUtil testLogUtil;

    /** The partner management. */
    @Inject
    private PartnerManagement partnerManagement;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(PartnerManagementTest.class);

    /** The start. */
    private long start;

    /**
     * Inits the method.
     * @param param the param
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(Object[] param, Method method) {

        testLogUtil.logStart(method, param);
        start = System.currentTimeMillis();

        try {
            partnerManagement.loadPage((String) param[0], (String) param[1]);
        } catch (Throwable e) {
            logger.error("Error in before method " + e.getMessage());
        }
    }

    /**
     * End method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(final Method method) {

        final long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * End Class.
     */
    @AfterClass(alwaysRun = true)
    public void endClass() {

        try {
            partnerManagement.logout();
        } catch (Throwable e) {
            logger.error("Error in after class " + e.getMessage());
        }
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            partnerManagement.end();
        } catch (Throwable e) {
            logger.error("Error in after suite " + e.getMessage());
        }
    }

    /**
     * Test the account link in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "defaultUser", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void verifyCancelPartnerCreation(final String userId, final String password) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.clickCancel();
    }

    /**
     * Creates the new partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "createPartner", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void createNewPartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillPartnerDetails(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Creates the in active partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "createInactivePartner", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void createInActivePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillPartnerDetails(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Test the account link in the insite home page.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "createPartner", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void verifyResetPartnerCreation(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillPartnerDetails(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickReset();
    }

    /**
     * Click save without partner name.
     * @param userId the user id
     * @param password the password
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "withoutPartnerName", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void clickSaveWithoutPartnerName(final String userId, final String password,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillDetailsWithoutPartnerName(DriverConfig.getDriver(), emailId,
                activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerNameAlert();
    }

    /**
     * Click save without partner type.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     */
    @Test(dataProvider = "withoutPartnerType", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void clickSaveWithoutPartnerType(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillDetailsWithoutPartnerType(DriverConfig.getDriver(), partnerName
                + dateTimeStamp, emailId, activeUser, streetAddress1, streetAddress2, city, state,
                zip, country, primaryContactName, phoneNumber);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerTypeAlert();
    }

    /**
     * Click save without partner name type.
     * @param userId the user id
     * @param password the password
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     */
    @Test(dataProvider = "withoutPartnerNameType", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void clickSaveWithoutPartnerNameType(final String userId, final String password,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillDetailsWithoutPartnerNameType(DriverConfig.getDriver(), emailId,
                activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerNameAndTypeAlert();
    }

    /**
     * Navigate tabs.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "defaultUser", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void navigateTabs(final String userId, final String password) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        Assert.assertTrue("Account Detials Tab is not displayed",
                partnerManagement.isAccountTabDisplayed());
        partnerManagement.clickContactInformation();
        Assert.assertTrue("Contact Information Tab is not displayed",
                partnerManagement.isContactTabDisplayed());
        partnerManagement.clickPartnerTypes();
        Assert.assertTrue("Partner Types Tab is not displayed",
                partnerManagement.isPartnerTypesTabDisplayed());
    }

    /**
     * Highlighted tabs.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "defaultUser", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void highlightedTabs(final String userId, final String password) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        Assert.assertTrue("Account Detials Tab is not displayed",
                partnerManagement.isAccountTabDisplayed());
        partnerManagement.clickContactInformation();
        Assert.assertTrue("Contact Information Tab is not displayed",
                partnerManagement.isContactTabHighlighted());
        partnerManagement.clickPartnerTypes();
        Assert.assertTrue("Partner Types Tab is not displayed",
                partnerManagement.isPartnerTypesTabHighlighted());
        partnerManagement.clickAccountTab();
        Assert.assertTrue("Partner Types Tab is not displayed",
                partnerManagement.isAccountTabHighlighted());
    }

    /**
     * Find valid partner.
     * @param userId the user id
     * @param password the password
     * @param validPartnerName the valid partner name
     */
    @Test(dataProvider = "findValidPartner", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void findValidPartner(final String userId, final String password,
            final String validPartnerName) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.searchByName(validPartnerName);
        partnerManagement.clickFind();
        partnerManagement.isDisplayedTopFirstPartner(validPartnerName);
    }

    /**
     * Find invalid partner.
     * @param userId the user id
     * @param password the password
     * @param invalidPartnerName the invalid partner name
     */
    @Test(dataProvider = "findInValidPartner", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void findInValidPartner(final String userId, final String password,
            final String invalidPartnerName) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.searchByName(invalidPartnerName);
        partnerManagement.clickFind();
        Assert.assertEquals("No results found.", partnerManagement.getResultMessage());
    }

    /**
     * Find partner valid email.
     * @param userId the user id
     * @param password the password
     * @param partnerEmail the partner email
     */
    @Test(dataProvider = "findPartnerByValidEmail", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void findPartnerByValidEmail(final String userId, final String password,
            final String partnerEmail) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.searchByEmail(partnerEmail);
        partnerManagement.clickFind();
        partnerManagement.isDisplayedTopFirstPartner(partnerEmail);
    }

    /**
     * Find partner in valid email.
     * @param userId the user id
     * @param password the password
     * @param inValidPartnerEmail the invalid partner email
     */
    @Test(dataProvider = "findPartnerByInValidEmail", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void findPartnerByInValidEmail(final String userId, final String password,
            final String inValidPartnerEmail) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.searchByEmail(inValidPartnerEmail);
        partnerManagement.clickFind();
        Assert.assertEquals("No results found.", partnerManagement.getResultMessage());
    }

    /**
     * Verify partner display.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "createPartner", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void verifyPartnerDisplay(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillPartnerDetails(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
        partnerManagement.isDisplayedTopFirstPartner(partnerName);
    }

    /**
     * Assigned partners.
     * @param userId the user id
     * @param password the password
     * @param partnerType the partner type
     */
    @Test(dataProvider = "fillPartnerType", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void checkForAssignedPartnerTypes(final String userId, final String password,
            final String partnerType) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillPartnerTypes(DriverConfig.getDriver(), partnerType);
    }

    /**
     * Skip email id save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutEmailId", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipEmailIdSavePartner(final String userId, final String password,
            final String partnerName, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutEmailId(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip street address one save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutStreetAddress", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipStreetAddressSavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutStreetAddress(DriverConfig.getDriver(), partnerName
                + dateTimeStamp, emailId, activeUser, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip street address two save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutCity", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipCitySavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String state,
            final String zip, final String country, final String primaryContactName,
            final String phoneNumber, final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutCity(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip state save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutState", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipStateSavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String zip, final String country, final String primaryContactName,
            final String phoneNumber, final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutState(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip zip save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutZip", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipZipSavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String country, final String primaryContactName,
            final String phoneNumber, final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutZip(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip country save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutCountry", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipCountrySavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String primaryContactName,
            final String phoneNumber, final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutCountry(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, zip,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip primary contact save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutPrimaryContact", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipPrimaryContactSavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country, final String phoneNumber,
            final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutPrimaryContactName(DriverConfig.getDriver(), partnerName
                + dateTimeStamp, emailId, activeUser, streetAddress1, streetAddress2, city, state,
                zip, country, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Skip phone number save partner.
     * @param userId the user id
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param availablePartnerType the available partner type
     */
    @Test(dataProvider = "saveWithoutPhoneNumber", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void skipPhoneNumberSavePartner(final String userId, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String availablePartnerType) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.saveWithoutPhoneNumber(DriverConfig.getDriver(), partnerName
                + dateTimeStamp, emailId, activeUser, streetAddress1, streetAddress2, city, state,
                zip, country, primaryContactName, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }

    /**
     * Find valid partner invalid email.
     * @param userId the user id
     * @param password the password
     * @param validPartnerName the valid partner name
     * @param invalidEmailId the invalid email id
     */
    @Test(dataProvider = "findValidPartnerInvalidEmail", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void findValidPartnerInvalidEmail(final String userId, final String password,
            final String validPartnerName, final String invalidEmailId) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.searchByName(validPartnerName);
        partnerManagement.searchByEmail(invalidEmailId);
        partnerManagement.clickFind();
        Assert.assertEquals("No results found.", partnerManagement.getResultMessage());
    }

    /**
     * Find in valid partner valid email.
     * @param userId the user id
     * @param password the password
     * @param invalidPartnerName the invalid partner name
     * @param validEmailId the valid email id
     */
    @Test(dataProvider = "findInValidPartnerValidEmail", dataProviderClass = PartnerDataProvider.class, groups = { "sanity" })
    public void findInValidPartnerValidEmail(final String userId, final String password,
            final String invalidPartnerName, final String validEmailId) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.searchByName(invalidPartnerName);
        partnerManagement.searchByEmail(validEmailId);
        partnerManagement.clickFind();
        Assert.assertEquals("No results found.", partnerManagement.getResultMessage());
    }

}
