/*
 * ConvertUserDataToTestData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ecofactor.qa.automation.newapp.model.ThermostatData;
import com.ecofactor.qa.automation.newapp.model.UserData;

/**
 * ConvertUserDataToTestData. Creates a new test-data.xml in consumer resource folder. Contains
 * automation user details with relevant elements.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ConvertUserDataToTestData {

    private static final String FILEPATH = "src/main/resources/test-data/test-data.xml";
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final String FEATURE = "feature";

    /**
     * Convert the given list data to an xml.
     * @param userDatas the user datas
     */
    @Test
    public void convertToXml(List<UserData> userDatas) {

        try {

            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final StringBuilder modeString = new StringBuilder();
            // root elements
            final Document doc = docBuilder.newDocument();
            final Element rootElement = doc.createElement("users");
            doc.appendChild(rootElement);

            for (final UserData userData : userDatas) {

                // User element
                final Element user = doc.createElement("user");
                rootElement.appendChild(user);

                // UserId element
                final Element userId = doc.createElement("userId");
                userId.setTextContent(userData.getUserName());
                user.appendChild(userId);

                // Password element
                final Element password = doc.createElement("password");
                password.setTextContent(userData.getPassword());
                user.appendChild(password);

                // Features element
                final Element features = doc.createElement("features");
                user.appendChild(features);

                // Login feature
                if (userData.isLogin()) {
                    final Element login = doc.createElement(FEATURE);
                    login.setTextContent("login.valid");
                    features.appendChild(login);
                }

                // thermostats element
                final Element thermostats = doc.createElement("thermostats");
                user.appendChild(thermostats);

                final List<ThermostatData> thList = userData.getThermostatDatas();
                if (thList != null && thList.size() > 0) {

                    if (thList.size() > ONE) {
                        final Element tstatFetaure = doc.createElement(FEATURE);
                        tstatFetaure.setTextContent("thermostat.multiple");
                        features.appendChild(tstatFetaure);
                    } else {
                        final Element tstatFetaure = doc.createElement(FEATURE);
                        tstatFetaure.setTextContent("thermostat.single");
                        features.appendChild(tstatFetaure);
                    }

                    boolean hasDataCollection = false;

                    final List<String> statusList = new ArrayList<>();
                    final Map<Integer, List<String>> modeMap = new HashMap<Integer, List<String>>();

                    for (final ThermostatData thermostatData : thList) {
                        final Element thermostat = doc.createElement("thermostat");
                        thermostat.setAttribute("id",
                                String.valueOf(thermostatData.getThermostatId()));
                        thermostats.appendChild(thermostat);

                        if (thermostatData.isDataCollection()) {
                            if (!hasDataCollection) {
                                hasDataCollection = true;
                            }
                        }

                        final Element status = doc.createElement("status");
                        if (thermostatData.isConnected()) {
                            status.setTextContent("Connected");
                            statusList.add("Connected");
                            final List<String> modeList = new ArrayList<>();
                            for (final String modeValue : thermostatData.getModes()) {
                                if (modeValue != null && !modeValue.isEmpty()) {
                                    final Element mode = doc.createElement("mode");
                                    mode.setTextContent(modeValue);
                                    thermostat.appendChild(mode);
                                    modeList.add(modeValue);
                                }
                            }

                            modeMap.put(thermostatData.getThermostatId(), modeList);
                        } else {
                            status.setTextContent("NotConnected");
                            statusList.add("NotConnected");
                        }
                        thermostat.appendChild(status);
                    }

                    if (hasDataCollection) {
                        final Element tstatData = doc.createElement(FEATURE);
                        tstatData.setTextContent("thermostat.dataCollection");
                        features.appendChild(tstatData);
                    }

                    final Iterator<Entry<Integer, List<String>>> entries = modeMap.entrySet()
                            .iterator();

                    while (entries.hasNext()) {
                        final Entry<Integer, List<String>> thisEntry = (Entry<Integer, List<String>>) entries
                                .next();
                        final List<String> valueList = (List<String>) thisEntry.getValue();
                        if (valueList.size() == TWO) {
                            final Element heatMode = doc.createElement(FEATURE);
                            heatMode.setTextContent("mode.heat");
                            final Element coolMode = doc.createElement(FEATURE);
                            coolMode.setTextContent("mode.cool");
                            final Element thermostatOffMode = doc.createElement(FEATURE);
                            thermostatOffMode.setTextContent("mode.off");
                            features.appendChild(heatMode);
                            features.appendChild(coolMode);
                            features.appendChild(thermostatOffMode);
                            break;
                        }

                        if (valueList.size() == ONE) {
                            final Element thermostatMode = doc.createElement(FEATURE);
                            thermostatMode.setTextContent(modeString.append("mode.")
                                    .append(valueList.get(0)).toString());
                            features.appendChild(thermostatMode);
                            final Element thermostatOffMode = doc.createElement(FEATURE);
                            thermostatOffMode.setTextContent("mode.off");
                            features.appendChild(thermostatOffMode);
                        }
                        modeString.setLength(0);
                    }

                    if (!statusList.contains("NotConnected")) {
                        final Element thermostatsstatus = doc.createElement(FEATURE);
                        thermostatsstatus.setTextContent("thermostat.connected");
                        features.appendChild(thermostatsstatus);
                    } else {
                        final Element thermostatsstatus = doc.createElement(FEATURE);
                        thermostatsstatus.setTextContent("thermostat.disconnected");
                        features.appendChild(thermostatsstatus);
                    }

                    if (userData.getNoOfLocation() > ONE) {
                        final Element locationFeature = doc.createElement(FEATURE);
                        locationFeature.setTextContent("location.multiple");
                        features.appendChild(locationFeature);
                    } else if (userData.getNoOfLocation() == ONE) {
                        final Element locationFeature = doc.createElement(FEATURE);
                        locationFeature.setTextContent("location.single");
                        features.appendChild(locationFeature);
                    }
                }

            }

            writeToFile(doc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Write to file.
     * @param doc the doc
     */
    public void writeToFile(Document doc) {

        try {

            // write the content into xml file
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(doc);
            final File file = new File(FILEPATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            final StreamResult result = new StreamResult(file);
            // StreamResult result = new StreamResult(new
            // File("src\\main\\resources\\test-data\\test-data.xml"));
            final StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
