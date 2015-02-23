/*
 * DynamicUserConfigForTests.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ecofactor.qa.automation.newapp.model.ThermostatDetails;

/**
 * ConvertUserDataToTestData. Creates a new test-data.xml in consumer resource folder. Contains
 * automation user details with relevant elements.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DynamicUserConfigForTests {

    private String environment = "";
    private static final String FILEPATH = "src/main/resources/test-data/";
    private static final String FILENAME = "_test-data.xml";
    private static final String XSLT_INDENT = "{http://xml.apache.org/xslt}indent-amount";
    private static final String SLAVES_FILE = "slaves.properties";
    private static final String USERS_XML = "mobileUsers.xml";
    private static final String PLAT_PATH = "../algorithm/src/main/resources/";
    private static final String PATH_URL = "src/main/resources/";

    /**
     * Creates the properties for test.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void createPropsForTest() throws IOException {

        environment = System.getProperty("env");
        final Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream(SLAVES_FILE));
        final String slaves = properties.getProperty("slaves");
        final String[] temp = slaves.split(",");
        final String dateXmlPath = new StringBuilder(FILEPATH).append(environment).append(FILENAME)
                .toString();
        String[] xmlFileArray = null;

        if (environment.equalsIgnoreCase("QA-APPS")) {
            xmlFileArray = new String[] { USERS_XML };
        }

        final List<TestDataAutoConfig> availableTestList = new ArrayList<>();

        final List<TestData> testDataList = readTestDataXml(dateXmlPath);

        for (final String slave : temp) {
            for (final String fileName : xmlFileArray) {

                final String moduleXmlFile = new StringBuilder(FILEPATH).append(fileName)
                        .toString();
                final List<TestDataAutoConfig> testList = readModuleTestData(dateXmlPath,
                        moduleXmlFile);

                for (final TestDataAutoConfig dataAutoConfig : testList) {

                    availableTestList.add(dataAutoConfig);
                    final List<TestDataConfig> dataConfigList = dataAutoConfig.getTestDataConfigs();

                    for (final TestDataConfig testDataConfig : dataConfigList) {
                        final List<UserConfig> userConfigs = testDataConfig.getUserConfigs();

                        for (final UserConfig userConfig : userConfigs) {
                            final List<String> featuresList = userConfig.getFeatures();
                            holdUsersForTest(featuresList, testDataList, userConfig,
                                    testDataConfig.getGlobalFeatures());
                        }
                    }
                }
                markAvailableUsers(availableTestList, environment, slave);
                unUsedUsers(testDataList, environment);
            }
        }
    }

    /**
     * Hold users for test.
     * @param featuresList the features list
     * @param testDataList the test data list
     * @param userConfig the user config
     * @param globalFeatureList the global feature list
     * @return true, if successful
     */
    private void holdUsersForTest(final List<String> featuresList, final List<TestData> testDataList,
            final UserConfig userConfig, final List<String> globalFeatureList) {

        for (final TestData testData : testDataList) {
            if (!testData.isUserMarked()) {
                final List<String> duplicateRemovalList = new ArrayList<>();
                final List<String> testDatafeaturesList = testData.getFeatures();

                final List<String> optionalFeatures = userConfig.getOptionalFeatures();
                final List<String> featuresNotNeeded = new ArrayList<>();

                for (final String feature : globalFeatureList) {
                    if (!featuresList.contains(feature)) {
                        if (!optionalFeatures.contains(feature))
                            featuresNotNeeded.add(feature);
                    }
                }

                int flag = 0;
                for (final String featureNotNeeded : featuresNotNeeded) {
                    if (testData.getFeatures().contains(featureNotNeeded)) {
                        flag = 1;
                    }
                }

                for (final String feature : testDatafeaturesList) {
                    if (featuresList.contains(feature)) {
                        duplicateRemovalList.add(feature);
                    }
                }

                final List<String> copy = new ArrayList<String>();
                for (final String globalFeature : globalFeatureList) {

                    if (!featuresList.contains(globalFeature)
                            && !optionalFeatures.contains(globalFeature)) {
                        copy.add(globalFeature);
                    }
                }

                boolean breakIt = false;
                for (final String copyValue : copy) {
                    if (testDatafeaturesList.contains(copyValue)) {
                        breakIt = true;
                        break;
                    }
                }

                if (!breakIt && duplicateRemovalList.size() == featuresList.size() && flag == 0) {
                    testData.setUserMarked(true);
                    userConfig.setTestData(testData);
                    break;
                }
            }
        }
    }

    /**
     * Un used users.
     * @param testDataList the test data list
     * @param env the env
     */
    private void unUsedUsers(final List<TestData> testDataList, final String env) {

        try {

            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            final Document unUsedDoc = docBuilder.newDocument();
            final Element unRootElement = unUsedDoc.createElement("users");
            unUsedDoc.appendChild(unRootElement);

            for (final TestData testData : testDataList) {

                if (!testData.isUserMarked()) {
                    final Element userElement = unUsedDoc.createElement("user");

                    unRootElement.appendChild(userElement);

                    final Element userIdElement = unUsedDoc.createElement("userId");
                    userIdElement.setTextContent(testData.getUserId());
                    userElement.appendChild(userIdElement);

                    final Element passwordElement = unUsedDoc.createElement("password");
                    passwordElement.setTextContent(testData.getUserId());
                    userElement.appendChild(passwordElement);
                }
            }

            writeToXmlFileAvailable(unUsedDoc, new StringBuilder(env).append("_UsersNotUsed")
                    .toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Mark available users.
     * @param testList the test list
     * @param env the env
     * @param slave the slave
     */
    private void markAvailableUsers(final List<TestDataAutoConfig> testList, final String env, final String slave) {

        try {

            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            final DocumentBuilderFactory unAvailableDocFactory = DocumentBuilderFactory
                    .newInstance();
            final DocumentBuilder unAvailableDocBuilder = unAvailableDocFactory
                    .newDocumentBuilder();

            // root elements
            final Document doc = docBuilder.newDocument();
            final Element rootElement = doc.createElement("test-datas");
            doc.appendChild(rootElement);

            final Document unDoc = unAvailableDocBuilder.newDocument();
            final Element unRootElement = unDoc.createElement("test-datas");
            unDoc.appendChild(unRootElement);

            for (final TestDataAutoConfig testDataAutoConfig : testList) {

                final List<TestDataConfig> testDataConfigList = testDataAutoConfig
                        .getTestDataConfigs();

                for (final TestDataConfig testDataConfig : testDataConfigList) {

                    // User element
                    final Element testData = doc.createElement("test-data");
                    testData.setAttribute("id", testDataConfig.getId());
                    rootElement.appendChild(testData);

                    final Element untestData = unDoc.createElement("test-data");
                    untestData.setAttribute("id", testDataConfig.getId());
                    unRootElement.appendChild(untestData);

                    final Element configs = doc.createElement("configs");
                    testData.appendChild(configs);

                    final Element unConfigs = unDoc.createElement("configs");
                    untestData.appendChild(unConfigs);

                    final List<UserConfig> userConfigList = testDataConfig.getUserConfigs();
                    final boolean userUnavailable = true;
                    for (final UserConfig userConfig : userConfigList) {
                        if (userConfig.getTestData() != null
                                && userConfig.getTestData().isUserMarked()) {
                            final Element config = doc.createElement("config");
                            config.setAttribute("id", userConfig.getUserIdKey());
                            config.setTextContent(userConfig.getTestData().getUserId());
                            configs.appendChild(config);

                            final Element config2 = doc.createElement("config");
                            config2.setAttribute("id", userConfig.getPasswordKey());
                            config2.setTextContent(userConfig.getTestData().getPassword());
                            configs.appendChild(config2);

                            if (userConfig.getThermostatIdKey() != null
                                    && !userConfig.getThermostatIdKey().isEmpty()) {

                                final Element config3 = doc.createElement("config");
                                config3.setAttribute("id", userConfig.getThermostatIdKey());
                                if ("QA-APPS".equalsIgnoreCase(environment)) {
                                    if (userConfig.getTestData().getThermostats() != null
                                            && userConfig.getTestData().getThermostats().size() > 1) {
                                        config3.setTextContent(new StringBuilder(userConfig
                                                .getTestData().getThermostats().get(0)
                                                .getThermostatId().toString())
                                                .append(",")
                                                .append(userConfig.getTestData().getThermostats()
                                                        .get(1).getThermostatId().toString())
                                                .toString());
                                        configs.appendChild(config3);
                                    }
                                } else {
                                    config3.setTextContent(userConfig.getTestData()
                                            .getThermostats().get(0).getThermostatId().toString());
                                    configs.appendChild(config3);
                                }
                            }

                        } else {
                            if (userUnavailable) {
                                final Element configVal = unDoc.createElement("config");
                                configVal.setAttribute("id", userConfig.getUserIdKey());
                                configVal.setTextContent("");
                                unConfigs.appendChild(configVal);

                                final Element config2 = unDoc.createElement("config");
                                config2.setAttribute("id", userConfig.getPasswordKey());
                                config2.setTextContent("");
                                unConfigs.appendChild(config2);

                                final Element configDummy = doc.createElement("config");
                                configDummy.setAttribute("id", userConfig.getUserIdKey());
                                configDummy.setTextContent("dummy@ecofactor.com");
                                configs.appendChild(configDummy);

                                final Element configPwdDummy = doc.createElement("config");
                                configPwdDummy.setAttribute("id", userConfig.getPasswordKey());
                                configPwdDummy.setTextContent("dummy");
                                configs.appendChild(configPwdDummy);

                                if (userConfig.getThermostatIdKey() != null
                                        && !userConfig.getThermostatIdKey().isEmpty()) {

                                    final Element configThermostatDummy = doc
                                            .createElement("config");
                                    configThermostatDummy.setAttribute("id",
                                            userConfig.getThermostatIdKey());
                                    configThermostatDummy.setTextContent("1111");
                                    configs.appendChild(configThermostatDummy);
                                }

                            }
                        }
                    }

                    final Map<String, String> configMap = testDataConfig.getConfigs();
                    final Iterator<Entry<String, String>> entries = configMap.entrySet().iterator();
                    while (entries.hasNext()) {
                        final Entry<String, String> thisEntry = (Entry<String, String>) entries
                                .next();
                        final String key = (String) thisEntry.getKey();
                        final String value = (String) thisEntry.getValue();

                        final Element config = doc.createElement("config");
                        config.setAttribute("id", key);
                        config.setTextContent(value);
                        configs.appendChild(config);
                    }
                }
            }

            writeToXmlFileAvailable(doc, new StringBuilder(env).append("_UserList").toString());
            writeToXmlFileAvailable(unDoc, new StringBuilder(env).append("_UnavailableTests")
                    .toString());
            createPropertiesForEach(doc, env, slave);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Read test data xml.
     * @param xmlPath the xml path
     * @return the list
     */
    private List<TestData> readTestDataXml(final String xmlPath) {

        final List<TestData> testDataList = new ArrayList<TestData>();
        try {

            final File fXmlFile = new File(xmlPath);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            final NodeList nList = doc.getElementsByTagName("user");

            for (int node = 0; node < nList.getLength(); node++) {

                final Node nNode = nList.item(node);
                final Element eElement = (Element) nNode;
                final Element userId = (Element) eElement.getElementsByTagName("userId").item(0);
                final Element password = (Element) eElement.getElementsByTagName("password").item(0);
                final List<String> featuresList = getFeaturesList(eElement);
                final List<ThermostatDetails> thermostats = getThermostatList(eElement);

                final TestData testData = new TestData();
                testData.setUserId(userId.getTextContent());
                testData.setPassword(password.getTextContent());
                testData.setFeatures(featuresList);
                testData.setThermostats(thermostats);

                testDataList.add(testData);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return testDataList;

    }

    /**
     * Read module test data.(For Example : home.xml, st3Cool.xml..)
     * @param xmlPath the xml path
     * @param moduleXmlPath the module xml path
     * @return the list
     */
    private List<TestDataAutoConfig> readModuleTestData(final String xmlPath, final String moduleXmlPath) {

        final List<TestDataAutoConfig> testDataAutoList = new ArrayList<TestDataAutoConfig>();
        try {

            final File fXmlFile = new File(moduleXmlPath);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            final List<UserConfig> userConfigList = new ArrayList<>();

            final Node rootNode = doc.getElementsByTagName("test-data").item(0);
            final Element parentElement = (Element) rootNode;

            final List<String> globalFeatures = getGlobalFeaturesList(doc.getDocumentElement());

            final NodeList nList = doc.getElementsByTagName("user");

            for (int node = 0; node < nList.getLength(); node++) {

                final UserConfig userConfig = new UserConfig();
                final Node nNode = nList.item(node);
                final Element eElement = (Element) nNode;
                final Element userIdkey = (Element) eElement.getElementsByTagName("userId-key").item(0);
                final Element passwordKey = (Element) eElement.getElementsByTagName("password-key").item(
                        0);
                final Element thermostatIdKey = (Element) eElement.getElementsByTagName("thermostat-id")
                        .item(0);
                final List<String> featuresList = getFeaturesList(eElement);
                final List<String> optionalFeaturesList = getOptionalFeaturesList(eElement);
                if (userIdkey != null) {
                    userConfig.setUserIdKey(userIdkey.getTextContent());
                    userConfig.setPasswordKey(passwordKey.getTextContent());
                    if (thermostatIdKey != null && !thermostatIdKey.getTextContent().isEmpty()) {
                        userConfig.setThermostatIdKey(thermostatIdKey.getTextContent());
                    }
                    userConfig.setFeatures(featuresList);
                    userConfig.setOptionalFeatures(optionalFeaturesList);
                    userConfigList.add(userConfig);
                }
            }

            final Map<String, String> configs = getConfigsList(moduleXmlPath);

            final TestDataAutoConfig testDataAutoConfig = new TestDataAutoConfig();
            final TestDataConfig testDataConfig = new TestDataConfig();
            testDataConfig.setUserConfigs(userConfigList);
            testDataConfig.setConfigs(configs);
            testDataConfig.setId(parentElement.getAttribute("id"));
            testDataConfig.setGlobalFeatures(globalFeatures);
            final List<TestDataConfig> testDataConfigList = new ArrayList<>();
            testDataConfigList.add(testDataConfig);
            testDataAutoConfig.setTestDataConfigs(testDataConfigList);
            testDataAutoList.add(testDataAutoConfig);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return testDataAutoList;
    }

    /**
     * Gets the configs list.
     * @param xmlPath the xml path
     * @return the configs list
     */
    private Map<String, String> getConfigsList(final String xmlPath) {

        final Map<String, String> configs = new HashMap<String, String>();
        try {

            final File fXmlFile = new File(xmlPath);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            final NodeList configList = doc.getElementsByTagName("configs");
            for (int node = 0; node < configList.getLength(); node++) {

                final NodeList list = doc.getElementsByTagName("config");
                for (int configNode = 0; configNode < list.getLength(); configNode++) {
                    final Node nNode = list.item(configNode);
                    final Element eElement = (Element) nNode;
                    configs.put(eElement.getAttribute("id"), eElement.getTextContent());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return configs;
    }

    /**
     * Gets the thermostat list.
     * @param rootElement the root element
     * @return the thermostat list
     */
    private List<ThermostatDetails> getThermostatList(final Element rootElement) {

        final List<ThermostatDetails> thermostats = new ArrayList<ThermostatDetails>();
        final Element thermostatList = (Element) rootElement.getElementsByTagName("thermostats").item(0);
        if (thermostatList.hasChildNodes()) {

            final NodeList thermostatListNode = thermostatList.getElementsByTagName("thermostat");
            for (int count = 0; count < thermostatListNode.getLength(); count++) {
                final Node thNode = thermostatListNode.item(count);
                final ThermostatDetails thDetails = new ThermostatDetails();
                final Element thermostatElement = (Element) thNode;
                thDetails.setThermostatId(Integer.parseInt(thermostatElement.getAttribute("id")));

                final Element thermostatStatus = (Element) thermostatElement.getElementsByTagName(
                        "status").item(0);
                thDetails.setThermostatStatus(thermostatStatus.getTextContent());

                final NodeList modeList = thermostatElement.getElementsByTagName("mode");
                for (int modeCount = 0; modeCount < modeList.getLength(); modeCount++) {
                    final Node modeNode = modeList.item(modeCount);
                    final Element thermostatMode = (Element) modeNode;
                    if (thermostatMode.getTextContent().equalsIgnoreCase("Cool")) {
                        thDetails.setCoolMode(true);
                    } else if (thermostatMode.getTextContent().equalsIgnoreCase("Heat")) {
                        thDetails.setHeatMode(true);
                    }
                }
                thermostats.add(thDetails);
            }
        }
        return thermostats;
    }

    /**
     * Gets the features list.
     * @param rootElement the root element
     * @return the features list
     */
    private List<String> getFeaturesList(final Element rootElement) {

        final Element features = (Element) rootElement.getElementsByTagName("features").item(0);
        final List<String> featuresList = new ArrayList<>();

        if (features != null && features.hasChildNodes()) {
            final NodeList featuresListNode = features.getElementsByTagName("feature");
            for (int count = 0; count < featuresListNode.getLength(); count++) {
                final Node featureNode = featuresListNode.item(count);
                final Element featureElement = (Element) featureNode;
                featuresList.add(featureElement.getTextContent());
            }
        }
        return featuresList;
    }

    /**
     * Write to file available.
     * @param doc the doc
     * @param fileName the file name
     */
    private void writeToXmlFileAvailable(final Document doc, final String fileName) {

        try {

            // write the content into xml file
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(doc);
            final StreamResult result = new StreamResult(new File(new StringBuilder(FILEPATH)
                    .append(fileName).append(".xml").toString()));
            final StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(XSLT_INDENT, "2");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the properties for each.
     * @param doc the doc
     * @param env the env
     * @param slave the slave
     */
    private void createPropertiesForEach(final Document doc, final String env, final String slave) {

        try {

            final NodeList parentList = doc.getElementsByTagName("test-data");
            final StringBuilder platPath = new StringBuilder();
            final StringBuilder pathUrl = new StringBuilder();

            for (int parentNode = 0; parentNode < parentList.getLength(); parentNode++) {

                Properties props = new Properties();
                final Node rootNode = parentList.item(parentNode);
                final Element parentElement = (Element) rootNode;

                final NodeList nList = parentElement.getElementsByTagName("configs");

                for (int node = 0; node < nList.getLength(); node++) {

                    final Node nNode = nList.item(node);
                    final Element eElement = (Element) nNode;
                    final NodeList configList = eElement.getElementsByTagName("config");
                    for (int configNode = 0; configNode < configList.getLength(); configNode++) {

                        final Node config = configList.item(configNode);
                        final Element configElement = (Element) config;
                        props.setProperty(configElement.getAttribute("id").trim(), configElement
                                .getTextContent().trim());
                    }
                }

                if ("QA-PLAT".equalsIgnoreCase(env)) {

                    props.store(
                            new FileOutputStream(platPath.append(PLAT_PATH).append(env).append("_")
                                    .append(parentElement.getAttribute("id")).append(".properties")
                                    .toString()), null);

                } else {
                    props.store(
                            new FileOutputStream(pathUrl.append(PATH_URL).append(env).append("_")
                                    .append(slave).append("_")
                                    .append(parentElement.getAttribute("id")).append(".properties")
                                    .toString()), null);
                }
                platPath.setLength(0);
                pathUrl.setLength(0);

                props.clear();
                props = null;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the global features.
     * @param xmlFilePath the xml file path
     * @return the global features
     */
    @SuppressWarnings("unused")
    private List<String> getGlobalFeatures(final String xmlFilePath) {

        final List<String> globalFeaturesList = new ArrayList<>();
        try {

            final File fXmlFile = new File(xmlFilePath);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            final Element globalFeatureNode = (Element) doc.getElementsByTagName("global-features").item(
                    0);
            final NodeList nList = globalFeatureNode.getElementsByTagName("feature");
            for (int node = 0; node < nList.getLength(); node++) {
                final Node nNode = nList.item(node);
                final Element eElement = (Element) nNode;
                globalFeaturesList.add(eElement.getTextContent());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return globalFeaturesList;

    }

    /**
     * Gets the global features list.
     * @param rootElement the root element
     * @return the global features list
     */
    private List<String> getGlobalFeaturesList(final Element rootElement) {

        final Element features = (Element) rootElement.getElementsByTagName("global-features").item(0);
        final List<String> featuresList = new ArrayList<>();

        if (features != null && features.hasChildNodes()) {
            final NodeList featuresListNode = features.getElementsByTagName("feature");
            for (int count = 0; count < featuresListNode.getLength(); count++) {
                final Node featureNode = featuresListNode.item(count);
                final Element featureElement = (Element) featureNode;
                featuresList.add(featureElement.getTextContent());
            }
        }
        return featuresList;
    }

    /**
     * Gets the optional features list.
     * @param rootElement the root element
     * @return the optional features list
     */
    private List<String> getOptionalFeaturesList(final Element rootElement) {

        final Element features = (Element) rootElement.getElementsByTagName("optional-features").item(0);
        final List<String> featuresList = new ArrayList<>();

        if (features != null && features.hasChildNodes()) {
            final NodeList featuresListNode = features.getElementsByTagName("feature");
            for (int count = 0; count < featuresListNode.getLength(); count++) {
                final Node featureNode = featuresListNode.item(count);
                final Element featureElement = (Element) featureNode;
                featuresList.add(featureElement.getTextContent());
            }
        }
        return featuresList;
    }
}
