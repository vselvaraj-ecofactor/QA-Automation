/*
 * SuiteReportsOperator.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.apache.log4j.lf5.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class SuiteReportsOperator.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SuiteReportsOperator implements PathOperator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuiteReportsOperator.class);

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private Path consolidatedSummary;

    private Path suiteParentFolder;

    /**
     * Instantiates a new suite reports operator.
     * 
     * @param consolidatedSummary the consolidated summary
     * @param suiteParentFolder the suite parent folder
     */
    public SuiteReportsOperator(final Path consolidatedSummary, final Path suiteParentFolder) {

        super();
        this.consolidatedSummary = consolidatedSummary;
        this.suiteParentFolder = suiteParentFolder;
    }

    /*
     * Method override
     */
    /**
     * @param path
     * @see com.ecofactor.qa.automation.util.PathOperator#operateOnDirectory(java.nio.file.Path)
     */
    @Override
    public void operateOnDirectory(Path path) {

        //LogUtil.log("Path: " + path.toAbsolutePath(), LogLevel.HIGH);
        //LogUtil.log("suiteParentFolder: " + suiteParentFolder.toAbsolutePath(), LogLevel.HIGH);
        if (path.toAbsolutePath().equals(suiteParentFolder.toAbsolutePath())) {
            if (outputStream.size() > 0) {
                try {

                    //LogUtil.log("Writing to consolidated summary file.", LogLevel.HIGH);
                    // final Path filePath =
                    // FileUtil.createNIOFile(parentFolder.toAbsolutePath().toString(),
                    // false, "summary.html");
                    Files.write(Paths.get(consolidatedSummary.toUri()), outputStream.toByteArray(), StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND);
                    //LogUtil.log("Done.", LogLevel.HIGH);
                    LOGGER.info("Done.");
                } catch (IOException e) {
                    LOGGER.error("Error writing file data from buffer. Cause ::: " + e);
                }
            } else {
                try {
                    //LogUtil.log("Deleting consolidated summary.html: " + consolidatedSummary.toAbsolutePath(), LogLevel.HIGH);
                    Files.deleteIfExists(consolidatedSummary);
                    //LogUtil.log("Consolidated summary.html: " + consolidatedSummary.toAbsolutePath()
                           // + " is successfully deleted.", LogLevel.HIGH);
                } catch (IOException e) {
                    LOGGER.error("Could not delete consolidated summary.html: " + consolidatedSummary.toAbsolutePath()
                            + ". Cause: " + e);
                    // //LogUtil.log("Could not delete consolidated summary.html: "
                    // + consolidatedSummary.toAbsolutePath(), LogLevel.LOW);
                }
            }
        }
    }

    /*
     * Method override
     */
    /**
     * @param path
     * @see com.ecofactor.qa.automation.util.PathOperator#operateOnFile(java.nio.file.Path)
     */
    @Override
    public void operateOnFile(final Path path) {

        if (path.getFileName().toString().equalsIgnoreCase("summary.html")) {
            try {
                final List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
                boolean hasFailures = false;
                for (final String line : lines) {
                    System.out.println(line);
                    if (line.contains("<th>Screenshot</th>")) {
                        hasFailures = true;
                        break;
                    }
                }
                if (!hasFailures) {
                    outputStream.write(Files.readAllBytes(path));
                }
            } catch (IOException e) {
                LOGGER.error("Error reading and storing file data. Cause ::: " + e);
            }
        }
    }

}