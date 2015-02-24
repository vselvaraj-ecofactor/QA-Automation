package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.util.WaitUtil.tinyWait;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.PartnerAccountUserDao;
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.data.UserDataProvider;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.insite.page.UserManagement;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mail.GmailForNewUser;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class UserManagementNegativeScenarioTest.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class UserManagementNegativeScenarioTest {

	/** The current date. */
	Date currentDate = new Date();

	/** The formatter. */
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

	/** The date time stamp. */
	String dateTimeStamp = formatter.format(currentDate);

	/** The insite login. */
	@Inject
	private InsiteLogin insiteLogin;

	/** The user management. */
	@Inject
	private UserManagement userManagement;

	/** The partner account user dao. */
	@SuppressWarnings("unused")
	@Inject
	private PartnerAccountUserDao partnerAccountUserDao;

	/** The gmail. */
	@Inject
	private GmailForNewUser gmail;

	/** The test log util. */
	@Inject
	private TestLogUtil testLogUtil;

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(UserManagementTest.class);

	/** The start. */
	private long start;

	/**
	 * Inits the method.
	 * 
	 * @param param
	 *            the param
	 * @param method
	 *            the method
	 */
	@BeforeMethod(alwaysRun = true)
	public void initMethod(Object[] param, Method method) {

		testLogUtil.logStart(method, param);
		start = System.currentTimeMillis();

		try {
			if (method.getName() == "testCreateNewUserInActive"
					|| method.getName() == "testCreateNewUser") {
				gmail.deleteOldMails((String) param[4], (String) param[5],
						(String) param[6], (String) param[7]);
				insiteLogin.clearUser();
			}
			userManagement.loadPage((String) param[0], (String) param[1]);
		} catch (Throwable e) {
			logger.error("Error in before method " + e.getMessage());
		}
	}

	/**
	 * End method.
	 * 
	 * @param method
	 *            the method
	 */
	@AfterMethod(alwaysRun = true)
	public void endMethod(Method method) {

		long duration = (System.currentTimeMillis() - start) / 1000;
		testLogUtil.logEnd(method, duration);
	}

	/**
	 * End Class.
	 */
	@AfterClass(alwaysRun = true)
	public void endClass() {

		try {
			userManagement.logout();
		} catch (Throwable e) {
			logger.error("Error in after class method " + e.getMessage());
		}
	}

	/**
	 * End suite.
	 */
	@AfterSuite(alwaysRun = true)
	public void endSuite() {

		try {
			userManagement.end();
		} catch (Throwable e) {
			logger.error("Error in after suite method " + e.getMessage());
		}
	}

	/**
	 * Test create new user in active.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserNegative", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 1)
	public void testCreateNewUserInActive(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.createNewUser(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		userManagement.logout();
		String temporaryPassword = gmail.getChangedPassword(emailUrl,
				emailUserName, emailPassword, subject, 0, 0);
		logger.info("Temporary password received-" + temporaryPassword, true);
		userManagement.loadInsiteURL();
		if (insiteLogin.isAuthenticatedUser()) {
			insiteLogin.logout();
		}
		tinyWait();
		DriverConfig.setLogString("Login with the temporary password.", true);
		insiteLogin.login(accountUserName + dateTimeStamp, temporaryPassword);

		insiteLogin.changeNewPassword(newPassword);
		WaitUtil.waitUntil(60000);

		DriverConfig.setLogString(
				"Login with the changed new password, username:"
						+ accountUserName + dateTimeStamp + ", password:"
						+ newPassword, true);
		insiteLogin.login(accountUserName + dateTimeStamp, newPassword);
		insiteLogin.verifyLogin(accountUserName + dateTimeStamp);

	}

	/**
	 * Test create new user empty first name.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserEmptyFirstName", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 2)
	public void testCreateNewUserEmptyFirstName(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains(
						"Firstname cannot be null"), "Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user empty last name.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserEmptyLastName", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 3)
	public void testCreateNewUserEmptyLastName(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains(
						"Lastname cannot be null"), "Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user empty email.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserEmptyEmail", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 4)
	public void testCreateNewUserEmptyEmail(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains(
						"Email cannot be null"), "Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user empty user name.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserEmptyUserName", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 6)
	public void testCreateNewUserEmptyUserName(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement.fillNewUserClickSave(firstName, lastName, emailAddress,
				accountUserName, activeUser, partnerType, partner,
				streetAddress1, streetAddress2, city, state, zip, country,
				mobilePhoneNumber, homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user empty partner.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserEmptyPartner", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 5)
	public void testCreateNewUserEmptyPartner(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user empty role.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserEmptyRole", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 7)
	public void testCreateNewUserEmptyRole(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains(
						"Atleast one role has to be assigned to the User"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user state number.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserStateNumber", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 8)
	public void testCreateNewUserStateNumber(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user country number.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserCountryNumber", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 9)
	public void testCreateNewUserCountryNumber(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user zip char.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserZipChar", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 10)
	public void testCreateNewUserZipChar(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new mobile number char.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserMobileNumberChar", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 11)
	public void testCreateNewMobileNumberChar(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user phone number char.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserPhoneNumberChar", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 12)
	public void testCreateNewUserPhoneNumberChar(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Test create new user fax number char.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param emailAddress
	 *            the email address
	 * @param accountUserName
	 *            the account user name
	 * @param activeUser
	 *            the active user
	 * @param partnerType
	 *            the partner type
	 * @param partner
	 *            the partner
	 * @param streetAddress1
	 *            the street address1
	 * @param streetAddress2
	 *            the street address2
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zip
	 *            the zip
	 * @param country
	 *            the country
	 * @param mobilePhoneNumber
	 *            the mobile phone number
	 * @param homePhoneNumber
	 *            the home phone number
	 * @param fax
	 *            the fax
	 * @param availableRole
	 *            the available role
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "createUserFaxNumberChar", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 13)
	public void testCreateNewUserFaxNumberChar(final String userName,
			final String password, final String firstName,
			final String lastName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String emailAddress,
			final String accountUserName, final String activeUser,
			final String partnerType, final String partner,

			final String streetAddress1, final String streetAddress2,
			final String city, final String state, final String zip,
			final String country, final String mobilePhoneNumber,
			final String homePhoneNumber, final String fax,
			final String availableRole, final String newPassword) {

		currentDate = new Date();
		dateTimeStamp = formatter.format(currentDate);
		userManagement
				.fillNewUserClickSave(firstName, lastName, emailAddress,
						accountUserName + dateTimeStamp, activeUser,
						partnerType, partner, streetAddress1, streetAddress2,
						city, state, zip, country, mobilePhoneNumber,
						homePhoneNumber, fax, availableRole);
		Assert.assertTrue(
				userManagement.getUserSaveError().contains("User save failed"),
				"Expected Text Not Found");
		userManagement.logout();

	}

	/**
	 * Admin invalid email address.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param emailAddress
	 *            the email address
	 * @param invalidEmail
	 *            the invalid email
	 * @param msg
	 *            the msg
	 */
	@Test(dataProvider = "invalidEmail", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 14)
	public void adminInvalidEmailAddress(String userName, String password,
			String emailAddress, String invalidEmail, String msg) {

		userManagement.searchByEmail(emailAddress);
		userManagement.clickEditIcon();
		userManagement.editEmailId(invalidEmail);
		userManagement.clickSave();
		Assert.assertTrue(userManagement.getErrorMessage()
				.equalsIgnoreCase(msg));
	}

	/**
	 * Test non exsisting email.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 */
	@Test(dataProvider = "searchNonExsistingEmail", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 15)
	public void testNonExsistingEmail(final String userName,
			final String password, final String searchEmailId) {

		userManagement.searchByEmail(searchEmailId);
		Assert.assertTrue(userManagement.isNoResultDisplayed(),
				"No Results Found Text Not Displayed");

	}

	/**
	 * Test non exsisting name.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchName
	 *            the search name
	 */
	@Test(dataProvider = "searchNonExsistingName", dataProviderClass = UserDataProvider.class, groups = { "negative" }, priority = 16)
	public void testNonExsistingName(final String userName,
			final String password, final String searchName) {

		userManagement.searchByName(searchName);
		Assert.assertTrue(userManagement.isNoResultDisplayed(),
				"No Results Found Text Not Displayed");

	}
}
