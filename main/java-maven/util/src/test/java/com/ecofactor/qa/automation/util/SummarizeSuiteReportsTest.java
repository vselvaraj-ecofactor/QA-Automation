/*
 * SummarizeSuiteReportsTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.testng.annotations.Test;

/**
 * The Class SummarizeSuiteReportsTest.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SummarizeSuiteReportsTest {

    /**
     * Consolidate suite reports.
     */
    @Test
    public void consolidateSuiteReports() {

       // LogUtil.log("Consolidate Suite Reports", LogLevel.HIGH);
        try {
            final Path directory = Paths.get("../", System.getProperty("artifactId"), "testresult");
            final Path summaryFile = FileUtil.createNIOFile(directory.toAbsolutePath().toString(), true, "summary.html");
            //LogUtil.log("Summary File:" + summaryFile, LogLevel.HIGH);
            writeHeader(summaryFile);
            final Path suiteFolder = Paths.get("../" +System.getProperty("SUITE_PARENT")); 
            final PathOperator operator = new SuiteReportsOperator(summaryFile, suiteFolder);
            // define the starting file tree
            final DirectoryTree traverser = new DirectoryTree(operator);
            // instantiate the walk
            Files.walkFileTree(suiteFolder, traverser);

            // start the walk
        } catch (IOException e) {
           // LogUtil.log("Exception in File consolidation. Cause :" + e, LogLevel.HIGH);
        }
    }    
    
    /**
     * Write header.
     * 
     * @param filePath the file path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeHeader(Path filePath) throws IOException {

        final StringBuilder content = new StringBuilder();
        writeDocumentHead(content);
        writeBody(content);
        closeBody(content);
        Files.write(Paths.get(filePath.toUri()), content.toString().getBytes(), StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    /**
     * Write document head.
     * 
     * @param content the content
     * @return the string
     */
    private static void writeDocumentHead(final StringBuilder content) {

        content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        content.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        content.append("<head>");
        content.append("<title>Header</title>");
        content.append("</head><body>");
    }

    /**
     * Write body.
     * 
     * @param content the content
     * @return the string
     */
    private static void writeBody(final StringBuilder content) {

        final String moduleName = SystemUtil.getProperty("jobName");
        if (moduleName != null && !moduleName.isEmpty()) {
            content.append("<table width=\"100%\" style=\"border:1px solid #009;border-bottom-style:none;border-bottom:none;border-collapse:collapse;\" border=\"1px\">");
            content.append("<tr>");
            content.append("<th style=\"height:40px;background-color:#a8a89d\">" + getModuleName().toUpperCase() + "</th>");
            content.append("</tr>");
            content.append("</table>");
        }
    }

    /**
     * Close body.
     * 
     * @param content the content
     * @return the string
     */
    private static void closeBody(final StringBuilder content) {

        content.append("</body>");
        content.append("</html>");
    }

    /**
     * Gets the module name.
     * 
     * @return the module name
     */
    private static String getModuleName() {

        // Convert artifact names to current portal version
        String moduleName = System.getProperty("artifactId");
        if (moduleName != null) {
            switch (moduleName) {
            case "insite":
                moduleName = "INSITE";
                break;

            case "consumer":
                moduleName = "CONSUMER";
                break;

            case "new app":
                moduleName = "NEW APP";
                break;

            default:
                break;
            }
        }
        return moduleName == null || moduleName.contains("${") ? "-" : moduleName;
    }
}

