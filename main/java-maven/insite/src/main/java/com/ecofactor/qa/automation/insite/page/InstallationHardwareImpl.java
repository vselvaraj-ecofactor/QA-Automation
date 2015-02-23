/*
 * InstallationHardwareImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.InstallationConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InstallationConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class InstallationHardwareImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InstallationHardwareImpl extends InsiteAuthenticatedPageImpl implements InstallationHardware {

    @Inject
    private InstallationConfig installationConfig;
    @Inject
    private InsiteConfig appConfig;
    private static Logger logger = LoggerFactory.getLogger(InstallationHardwareImpl.class);

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    @SuppressWarnings("static-access")
    public void loadPage() {

        String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.INSTALLATION_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            logger.info("Load installation page.", true);
            clickInstallation();
        }

    }

    /**
     * Test installation stuff.
     * @param streetAddressValue the street address value
     * @see com.ecofactor.qa.automation.insite.page.InstallationHardware#testInstallationStuff(java.lang.String)
     */
    public void testInstallationStuff(final String streetAddressValue) {

        logger.info("Processing Instalaltion Wizard ..");
        logger.info("click installation page.");
        clickInstallation();
        DriverConfig.setLogString("check look up address.", true);
        checkLookUpAddress(streetAddressValue);
        DriverConfig.setLogString("review home profile.", true);
        reviewHomeProfile();
        DriverConfig.setLogString("check and define HVAC system.", true);
        checkDefineHVACSystem();
        DriverConfig.setLogString("check link gateway.", true);
        checkLinkGateway();
        DriverConfig.setLogString("install thermostats.", true);
        checkInstallThermostat();
        DriverConfig.setLogString("test thermostats.", true);
        checkTestThermostats();

        DriverConfig.setLogString("Finished processing Instalaltion Wizard ..", true);
    }

    /**
     * Check the 1st step Look Up address.
     * @param streetAddressValue the street address value
     */
    public void checkLookUpAddress(final String streetAddressValue) {

        DriverConfig.setLogString("Select Wizard 1 : LookUp Address",true);
        logger.info("check if street address is displayed.");
        isDisplayedById(DriverConfig.getDriver(), installationConfig.get(STREET_ADDRESS), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("send values to street address." + streetAddressValue, true);
        DriverConfig.getDriver().findElement(By.id("ef_street_address")).sendKeys(streetAddressValue);
        mediumWait();

        DriverConfig.setLogString("Select the top 1st Value from the dropDown.", true);
        WebElement selectedElement = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_LI, ATTR_CLASS,
                installationConfig.get(STREET_DROP_DOWN_CLASS));
        selectedElement.click();
        largeWait();
        DriverConfig.setLogString("check if ok button visible.", true);
        isEnabledById(DriverConfig.getDriver(), installationConfig.get(OK_BUTTON), MEDIUM_TIMEOUT);

        if (DriverConfig.getDriver().findElement(By.id(installationConfig.get(OK_BUTTON))).isEnabled()) {
            DriverConfig.setLogString("Click on View Account", true);
            DriverConfig.getDriver().findElement(By.id(installationConfig.get(OK_BUTTON))).click();
            smallWait();
        }

        DriverConfig.setLogString("Check the HVAC System and Network Availability...", true);
        List<WebElement> networkAvailabilityList = DriverConfig.getDriver()
                .findElements(By.id(installationConfig.get(INTERNET_QUESTION)));
        for (WebElement webElement : networkAvailabilityList) {
            if (webElement.getAttribute(ATTR_VALUE).equalsIgnoreCase("4")) {
                DriverConfig.setLogString("Click on Working Properly..", true);
                webElement.click();
                smallWait();
                break;
            }
        }
        smallWait();
        DriverConfig.setLogString("find and click on Continue button", true);
        isDisplayedById(DriverConfig.getDriver(), installationConfig.get(START_INSTALLATION), MEDIUM_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id(installationConfig.get(START_INSTALLATION))).click();
    }

    /**
     * Step 2 : Review Home Profile.
     */
    private void reviewHomeProfile() {

        DriverConfig.setLogString("Select Wizard 2 : Review Home profile",true);
        DriverConfig.setLogString("check if Define HVAC System button is available.", true);
        isDisplayedById(DriverConfig.getDriver(), installationConfig.get(NEXT_STEP_BUTTON), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("click Define HVAC System button.", true);
        DriverConfig.getDriver().findElement(By.id(installationConfig.get(NEXT_STEP_BUTTON))).click();
    }

    /**
     * Step 3 : Define HVAC System.
     */
    private void checkDefineHVACSystem() {

        DriverConfig.setLogString("Select Wizard 3 :  Define HVAC System",true);
        logger.info("find anything window.");
        isDisplayedByClassName(DriverConfig.getDriver(), installationConfig.get(ANYTHING_WINDOW), MEDIUM_TIMEOUT);

        DriverConfig.setLogString("Verify the done button visibility and click it.", true);
        WebElement doneElement = retrieveElementByContainsOfAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Done! ",
                MEDIUM_TIMEOUT);
        doneElement.click();

        DriverConfig.setLogString("Verify the Link Gateway button visibility in HVAC system and click it.", true);
        isEnabledById(DriverConfig.getDriver(), "linkgatewayButton", SHORT_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id("linkgatewayButton")).click();
    }

    /**
     * Wizard 4 : Link Gateway.
     */
    private void checkLinkGateway() {

        DriverConfig.setLogString("Select Wizard 4 :  Link Gateway",true);

        logger.info("Verify the MAC Address textBox visbility.");
        isDisplayedById(DriverConfig.getDriver(), "ef_mac_address", MEDIUM_TIMEOUT);

        DriverConfig.setLogString("Click Link Gateway.", true);
        isDisplayedById(DriverConfig.getDriver(), "nextstep5", SHORT_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id("nextstep5")).click();

        WaitUtil.waitUntil(18000);
        DriverConfig.setLogString("Wait until Install Thermostat button is enabled and click it", true);
        isEnabledById(DriverConfig.getDriver(), installationConfig.get(NEXT_STEP_BUTTON), VERY_LONG_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id(installationConfig.get(NEXT_STEP_BUTTON))).click();
    }

    /**
     * Wizard 5: Install Thermostat.
     */
    private void checkInstallThermostat() {

        DriverConfig.setLogString("Select Wizard 5 :  Install Thermostat",true);
        DriverConfig.setLogString("Wait until 'Test Thermostat' button is enabled and click it.", true);
        isEnabledById(DriverConfig.getDriver(), installationConfig.get(NEXT_STEP_BUTTON), LONG_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id(installationConfig.get(NEXT_STEP_BUTTON))).click();
        smallWait();
    }

    /**
     * Wizard 6: Test Thermostat.
     */
    private void checkTestThermostats() {

        DriverConfig.setLogString("Select Wizard 6 : Test Thermostat",true);
        DriverConfig.setLogString("Verify the 'Run Test' button is enabled and Click it.", true);
        isDisplayedById(DriverConfig.getDriver(), "tstatSelect_0", MEDIUM_TIMEOUT);
        isEnabledById(DriverConfig.getDriver(), "runTest", MEDIUM_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id("runTest")).click();

        DriverConfig.setLogString("Verify the PopUp is opened", true);
        isEnabledByClassName(DriverConfig.getDriver(), "boxy-content", MEDIUM_TIMEOUT);

        WebElement boxyContent = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TABLE, ATTR_CLASS, "boxy-wrapper fixed");

        if (boxyContent.isEnabled()) {
            DriverConfig.setLogString("Click check box and verify the confirm buton is enabled, and click it.", true);
            boxyContent.findElement(By.id("confirmeval")).click();
            smallWait();
            DriverConfig.setLogString("Click check box and verify the Agree buton is enabled, and click it.", true);
            isEnabledByIdSubElement(DriverConfig.getDriver(), boxyContent, "iagree_eval", SHORT_TIMEOUT);
            boxyContent.findElement(By.id("iagree_eval")).click();

            DriverConfig.setLogString("Wait until Selection is displayed in Status Column.", true);
            isDisplayedByTagName(DriverConfig.getDriver(), "select", VERY_LONG_TIMEOUT);

            DriverConfig.setLogString("Select Cool Air", true);
            Select select = new Select(DriverConfig.getDriver().findElement(By.tagName("select")));
            select.selectByVisibleText("Cool Air");
            smallWait();

            DriverConfig.setLogString("Click on Re-Test button and wait until selection is re-Enabled.", true);
            WebElement reTest = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Re-Test");
            reTest.click();
            isDisplayedByTagName(DriverConfig.getDriver(), "select", MEDIUM_TIMEOUT);

            DriverConfig.setLogString("Select Hot Air and wait until selection is re-Enabled.", true);
            Select select2 = new Select(DriverConfig.getDriver().findElement(By.tagName("select")));
            select2.selectByVisibleText("Hot Air");
            isDisplayedByTagName(DriverConfig.getDriver(), "select", MEDIUM_TIMEOUT);

            DriverConfig.setLogString("Select again Cool Air wait until Next bnutton is enabled.", true);
            Select select3 = new Select(DriverConfig.getDriver().findElement(By.tagName("select")));
            select3.selectByVisibleText("Cool Air");
            smallWait();

            DriverConfig.setLogString("Click on Next button", true);
            isEnabledById(DriverConfig.getDriver(), installationConfig.get(NEXT_STEP_BUTTON), MEDIUM_TIMEOUT);
            WebElement nextElement = retrieveElementByContainsOfAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Next ",
                    MEDIUM_TIMEOUT);
            nextElement.click();
            smallWait();

            DriverConfig.setLogString("Verify the Title message in the 7th Wizard.", true);
            isDisplayedByText(DriverConfig.getDriver(), "titleMsg", "one last thing before", MEDIUM_TIMEOUT);
            DriverConfig.setLogString("Verification Mesage - " + DriverConfig.getDriver().findElement(By.id("titleMsg")).getText() + "", true);
            smallWait();
        }
    }

    /**
     * Click about ecofactor.
     * @see com.ecofactor.qa.automation.insite.page.InsitePageImpl#clickAboutEcofactor()
     */
    @Override
    public void clickAboutEcofactor() {

        // TODO Auto-generated method stub

    }

    /**
     * Save thermostat name.
     * @param thermostatName the thermostat name
     * @see com.ecofactor.qa.automation.insite.page.InstallationHardware#saveThermostatName(java.lang.String)
     */
    @Override
    public void saveThermostatName(String thermostatName) {
        DriverConfig.setLogString("Provide name to existig thermostat.",true);
        smallWait();
        WebElement tblHomeProfile=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TABLE, ATTR_ID, "homeProfile");
        logger.info("tblHomeProfile: "+tblHomeProfile.getAttribute("class"));
        WebElement hiddenElement=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), tblHomeProfile, "input", "type", "hidden");
                //ForSubElement(driver, tblHomeProfile, TAG_INPUT, "type", "hidden", SHORT_TIMEOUT);
        logger.info("Hidden value: "+hiddenElement.getAttribute("value"));
        String thermostatFld=hiddenElement.getAttribute("value");
        DriverConfig.setLogString("Enter value for thermostat name as " + thermostatName + ".",true);
        WebElement fldThermostatName=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT,ATTR_ID , thermostatFld);
        fldThermostatName.clear();
        fldThermostatName.sendKeys(thermostatName);
        DriverConfig.setLogString("Click link gateway",true);
        WebElement LinkGateWayBtn=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_ID, "nextStepButton");
        LinkGateWayBtn.click();
        smallWait();

    }

    /* (non-Javadoc)
     * @see com.ecofactor.qa.automation.insite.page.InstallationHardware#addThermostat(java.lang.String)
     */
    @Override
    public void addThermostat(String thermostatName) {
        smallWait();
        WebElement getAddressDiv=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_DIV, ATTR_ID, "getAddressDiv");
        logger.info("getAddressDiv ID: "+getAddressDiv.getAttribute("id"));

        WebElement tblHomeProfile=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TABLE, ATTR_ID, "homeProfile");
        logger.info("tblHomeProfile: "+tblHomeProfile.getAttribute("class"));

        List<WebElement> newlyAddedTstats=retrieveElementsByContainsOfAttributeValue(DriverConfig.getDriver(), TAG_TD, ATTR_CLASS, "ef_dataTable toCountThermostats");
        WebElement newlyAddedTstat=newlyAddedTstats.get(newlyAddedTstats.size()-1);

        logger.info("Newly Added thermostat: "+newlyAddedTstat.getTagName(),true);
        DriverConfig.setLogString("Enter name for existing thermostat field as 'TestTstat'.",true);
        WebElement newTstatName=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), newlyAddedTstat, TAG_INPUT, ATTR_TYPE, "text");
        newTstatName.clear();
        newTstatName.sendKeys("TestTstat");
        tinyWait();

        //click add link
        DriverConfig.setLogString("Click add thermostat link.",true);
        WebElement addThermostatElement=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), getAddressDiv, TAG_ANCHOR, ATTR_ID, "addThermostat");
        addThermostatElement.click();
        tinyWait();

        tblHomeProfile=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TABLE, ATTR_ID, "homeProfile");
        logger.info("tblHomeProfile: "+tblHomeProfile.getAttribute("class"),true);

        newlyAddedTstats=retrieveElementsByContainsOfAttributeValue(DriverConfig.getDriver(), TAG_TD, ATTR_CLASS, "ef_dataTable toCountThermostats");
        newlyAddedTstat=newlyAddedTstats.get(newlyAddedTstats.size()-1);

        logger.info("newlyAddedTstat: "+newlyAddedTstat.getTagName(),true);
        DriverConfig.setLogString("Enter name for newly added thermostat"+thermostatName+".",true);
        WebElement newTstatNameFld=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), newlyAddedTstat, TAG_INPUT, ATTR_TYPE, "text");
        newTstatNameFld.sendKeys(thermostatName);
        DriverConfig.setLogString("Click link gateway",true);
        WebElement LinkGateWayBtn=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_ID, "nextStepButton");
        LinkGateWayBtn.click();
        tinyWait();
    }

    /**
     * Verify thermostat name.
     *
     * @param thermostatName the thermostat name
     * @see com.ecofactor.qa.automation.insite.page.InstallationHardware#verifyThermostatName(java.lang.String)
     */
    @Override
    public void verifyThermostatName(String thermostatName) {
        DriverConfig.setLogString("verify thermostat '"+thermostatName+"' exists.",true);
        smallWait();
        WebElement tblHomeProfile=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TABLE, ATTR_ID, "homeProfile");
        logger.info("tblHomeProfile: "+tblHomeProfile.getAttribute("class"));
        WebElement hiddenElement=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), tblHomeProfile, "input", "type", "hidden");

        logger.info("Hidden value: "+hiddenElement.getAttribute("value"),true);
        String thermostatFld=hiddenElement.getAttribute("value");

        WebElement fldThermostatName=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT,ATTR_ID , thermostatFld);
        Assert.assertTrue("Thermostat name is different", fldThermostatName.getAttribute("value").equalsIgnoreCase(thermostatName));
    }

    /* (non-Javadoc)
     * @see com.ecofactor.qa.automation.insite.page.InstallationHardware#removeThermostat()
     */
    @Override
    public String removeThermostat() {

    	smallWait();
        WebElement getAddressDiv=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_DIV, ATTR_ID, "getAddressDiv");
        logger.info("remove getAddressDiv ID: "+getAddressDiv.getAttribute("id"),true);

        WebElement tblHomeProfile=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_TABLE, ATTR_ID, "homeProfile");
        logger.info("remove tblHomeProfile: "+tblHomeProfile.getAttribute("class"),true);

        //get thermostat name , which will be removed.
        List<WebElement> tagTDElements=retrieveElementsByContainsOfAttributeValue(DriverConfig.getDriver(), TAG_TD, ATTR_CLASS, "ef_dataTable toCountThermostats");
        WebElement tstatTDElement=tagTDElements.get(tagTDElements.size()-1);

        WebElement lastThermostatNameElement=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), tstatTDElement, TAG_INPUT, ATTR_TYPE, "text");
        String thermostatName=lastThermostatNameElement.getAttribute("value");
        DriverConfig.setLogString("Thermostat to be removed: "+thermostatName,true);

        //select tstat to remove
        DriverConfig.setLogString("Select thermostat to remove.",true);
        smallWait();
        //WebElement selectTstatCheckBox=retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_TYPE, "checkbox", LONG_TIMEOUT);
        WebElement selectTstatCheckBox = DriverConfig.getDriver().findElement(By.cssSelector(installationConfig.get(CHECKBOX)));
        selectTstatCheckBox.click();
        logger.info("CheckBox: "+selectTstatCheckBox.getAttribute("type"),true);

        //click remove link
        DriverConfig.setLogString("Click remove link.",true);
        WebElement removeThermostatLink=retrieveElementByAttributeValueByPassingElement(DriverConfig.getDriver(), getAddressDiv, TAG_ANCHOR, ATTR_ID, "delThermostat");
        logger.info("Remove Link id: "+removeThermostatLink.getAttribute("id"), true);
        removeThermostatLink.click();
        smallWait();

        return thermostatName;

    }

      /* (non-Javadoc)
       * @see com.ecofactor.qa.automation.insite.page.InstallationHardware#verifyNoThermostatWithName(java.lang.String)
       */
      public void verifyNoThermostatWithName(String thermostatName){
          DriverConfig.setLogString("Verify no thermostat with name '"+thermostatName+"' exists.",true);
    	//get list of thermostats , iterate & check to verify the removed tstats dosen't exists.
        List<WebElement> tstatNames=retrieveElementsByContainsOfAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_TYPE, "text");
        for(WebElement tstat:tstatNames)
        {
            logger.info("Tstat names:"+tstat.getAttribute("value"),true);
       	 Assert.assertFalse("Thermostat name is different", tstat.getAttribute("value").equalsIgnoreCase(thermostatName));
        }
    }

}
