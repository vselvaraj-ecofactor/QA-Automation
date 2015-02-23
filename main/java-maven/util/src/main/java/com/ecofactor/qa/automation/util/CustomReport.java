/*
 * CustomReport.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTimeZone;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;

/**
 * The Class CustomReport.
 * @author $Author: vselvaraj $
 * @version $Rev: 68160 $ $Date: 2014-10-24 12:40:00 +0530 (Fri, 24 Oct 2014) $
 */
public class CustomReport implements IReporter {

    private static final Logger LOG = Logger.getLogger(CustomReport.class);

    protected PrintWriter writer;
    protected PrintWriter summaryWritter;
    protected PrintWriter exceptionWriter;

    protected List<SuiteResult> suiteResults = Lists.newArrayList();

    // Reusable buffer
    private StringBuilder buffer = new StringBuilder();

    /*
     * Method override
     */
    /**
     * @param xmlSuites
     * @param suites
     * @param outputDirectory
     * @see org.testng.IReporter#generateReport(java.util.List, java.util.List, java.lang.String)
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        try {

            summaryWritter = createWriterForSummary(outputDirectory);

        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Unable to create output file", e);
            return;
        }
        for (ISuite suite : suites) {
            suiteResults.add(new SuiteResult(suite));
        }

        writeDocumentStart();
        writeHead();

        writeBody();

        summaryWritter.close();

    }

    /**
     * Creates the writer for summary.
     * @param outdir the outdir
     * @return the prints the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected PrintWriter createWriterForSummary(String outdir) throws IOException {

        String suiteLevel = System.getProperty("suiteLevel");
        DriverConfig.setLogString("Create Suite directory if suite level is true (" + suiteLevel
                + ")", true);
        if (suiteLevel != null && !suiteLevel.isEmpty() && suiteLevel.equalsIgnoreCase("true")) {
            outdir += "/suites";
        }
        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "summary.html"))));
    }

    /**
     * Creates the writer for exceptions.
     * @param outdir the outdir
     * @return the prints the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected PrintWriter createWriterForExceptions(String outdir) throws IOException {

        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(
                new FileWriter(new File(outdir, "exception.html"))));
    }

    /**
     * Write document exception start.
     */
    protected void writeDocumentExceptionStart() {

        exceptionWriter
                .println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        exceptionWriter.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        exceptionWriter.print("<head>");
        exceptionWriter.print("<title>Failure Exception Stacktrace</title>");
        exceptionWriter.print("</head><body><table>");
    }

    /**
     * Creates the writer.
     * @param outdir the outdir
     * @return the prints the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected PrintWriter createWriter(String outdir) throws IOException {

        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "report.html"))));
    }

    /**
     * Write document start.
     */
    protected void writeDocumentStart() {

        summaryWritter
                .println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        summaryWritter.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    }

    /**
     * Write head.
     */
    protected void writeHead() {

        summaryWritter.print("<head>");
        summaryWritter.print("<title>TestNG Report</title>");
        summaryWritter.print("</head>");

    }

    /**
     * Write body.
     */
    protected void writeBody() {

        summaryWritter.print("<body>");

        writeParamDetails();
        writeResultSummary();
        writeScenarioSummary();

        String suiteLevel = System.getProperty("suiteLevel");
        if (suiteLevel == null || suiteLevel.isEmpty() || suiteLevel.equalsIgnoreCase("false")) {
            summaryWritter.print("</body>");
        } else {
            summaryWritter.print("</body>");
        }

    }

    /**
     * Write param details.
     */
    protected void writeParamDetails() {

        long totalDuration = 0;
        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                totalDuration += testResult.getDuration();
            }
        }

        String env = SystemUtil.getProperty("TestEnv");
        // String schema = ParamUtil.getSchema();

        String slave = SystemUtil.getProperty("label");
        if (slave.equalsIgnoreCase("-")) {
            slave = SystemUtil.getProperty("nodeName");
        }
        long endVal = Calendar.getInstance().getTimeInMillis();
        long startVal = endVal - totalDuration;

        String suiteLevel = System.getProperty("suiteLevel");
        if (suiteLevel == null || suiteLevel.isEmpty() || suiteLevel.equalsIgnoreCase("false")) {
            summaryWritter.print(GenerateHeader.writeBody(getJenkinsJobName()));
        }

        String startTime = DateUtil.formatToZone(startVal,
                DateTimeZone.forID("America/Los_Angeles"), DateUtil.LARGE_FORMAT);
        String endTime = DateUtil.formatToZone(endVal, DateTimeZone.forID("America/Los_Angeles"),
                DateUtil.HOUR_FORMAT);
        String resultMessage = "<a style=\"font-weight:bold\" id=\"summary\" name=\"summary\">Results executed on "
                + startTime + " to " + endTime + "</a>";

        String os = SystemUtil.getOSType().toString();
        String browser = SystemUtil.getProperty("browser");

        String jobName = getJenkinsJobName();
        String jenkinsUrl = SystemUtil.getProperty("jenkinsUrl");
        String consoleURL = "-";
        if (!jenkinsUrl.equalsIgnoreCase("-")) {
            consoleURL = "<a style=\"font-weight:bold\" target=\"_blank\" href=\"" + jenkinsUrl
                    + "console\">Console</a>";
            jenkinsUrl = "<a style=\"font-weight:bold\" target=\"_blank\" href=\"" + jenkinsUrl
                    + "\">Jenkins</a>";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<table width=\"100%\" style=\"border-collapse:collapse;empty-cells:show\">"
                + "<tr><td colspan=\"10\" style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">"
                + resultMessage
                + "</td></tr><tr><td colspan=\"10\" align=\"center\" style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom;font-weight:bold;background-color:#dededa\">Setup Summary</td></tr>"
                + "<tr>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">Environment</th>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">Slave</th>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">OS</th>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">Browser</th>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">Job Name</th>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">Jenkins URL</th>"
                + "<th style=\"border:1px solid #009;padding:.25em .5em;vertical-align:bottom\">Console URL</th></tr>"
                + "<tr>"
                + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top\"> "
                + env
                + "</td>"
                + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top\">"
                + slave
                + "</td> <td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top\">"
                + os
                + "</td>"
                + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top\">"
                + browser
                + "</td>"
                + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top;font-weight:bold;background-color:#8FF3F8\">"
                + jobName
                + "</td>"
                + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top\">"
                + jenkinsUrl
                + "</td>"
                + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top\">"
                + consoleURL + "</td></tr></table>");

        summaryWritter.print(builder.toString());
    }

    /**
     * Gets the jenkins job name.
     * @return the jenkins job name
     */
    private String getJenkinsJobName() {

        String jobName = SystemUtil.getProperty("jobName");
        /*
         * if (!jobName.equalsIgnoreCase("-")) { if (jobName.contains("/")) { String newValue =
         * jobName.substring(jobName.lastIndexOf("/") + 1, jobName.length()); jobName = newValue; }
         * }
         */
        return jobName;
    }

    /**
     * Write document end.
     */

    /**
     * Write result summary.
     */
    protected void writeResultSummary() {

        summaryWritter
                .print("<table width=\"100%\" style=\"border-collapse:collapse;empty-cells:show;border:1px solid #009;\" border=\"1px\">");
        summaryWritter.print("<tr>");
        summaryWritter
                .print("<th colspan=\"4\" style=\"background-color:#dededa\">Result Summary</th>");
        summaryWritter.print("</tr>");
        summaryWritter.print("<tr>");
        summaryWritter.print("<th style=\"background-color: #11ca0d\">Total Passed</th>");
        summaryWritter.print("<th style=\"background-color: #fc2203\">Total Failed</th>");
        summaryWritter.print("<th style=\"background-color: #fbf537\">Total Skipped</th>");
        summaryWritter.print("<th style=\"background-color: #d2d5ca\">Total Duration(secs)</th>");
        ;
        summaryWritter.print("</tr>");

        int totalPassedTests = 0;
        int totalFailedTests = 0;
        int totalSkippedTests = 0;
        long totalDuration = 0;

        for (SuiteResult suiteResult : suiteResults) {

            for (TestResult testResult : suiteResult.getTestResults()) {

                int passedTests = testResult.getPassedTestCount();
                int failedTests = testResult.getFailedTestCount();
                int skippedTests = testResult.getSkippedTestCount();
                long duration = testResult.getDuration();

                totalPassedTests += passedTests;
                totalFailedTests += failedTests;
                totalSkippedTests += skippedTests;
                totalDuration += duration;
            }
        }

        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalDuration);

        summaryWritter
                .print("<tr><td  style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top;text-align:center\">"
                        + totalPassedTests
                        + "</td>"
                        + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top;text-align:center\">"
                        + totalFailedTests
                        + "</td>"
                        + "<td  style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top;text-align:center\">"
                        + totalSkippedTests
                        + "</td>"
                        + "<td style=\"border:1px solid #009;padding:.25em .5em;vertical-align:top;text-align:center\">"
                        + seconds + "</td>" + "</tr></table>");

    }

    /**
     * Write suite summary.
     */
    protected void writeSuiteSummary() {

        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        NumberFormat decimalFormat = NumberFormat.getNumberInstance();

        int totalPassedTests = 0;
        int totalSkippedTests = 0;
        int totalFailedTests = 0;
        long totalDuration = 0;

        int testIndex = 0;
        for (SuiteResult suiteResult : suiteResults) {

            for (TestResult testResult : suiteResult.getTestResults()) {
                int passedTests = testResult.getPassedTestCount();
                int skippedTests = testResult.getSkippedTestCount();
                int failedTests = testResult.getFailedTestCount();
                long duration = testResult.getDuration();

                buffer.setLength(0);
                writeTableData(buffer.append("<a style=\"font-weight:bold\" href=\"#t")
                        .append(testIndex).append("\">")
                        .append(Utils.escapeHtml(testResult.getTestName())).append("</a>")
                        .toString());
                writeTableData(integerFormat.format(passedTests), "text-align:right");
                writeTableData(integerFormat.format(skippedTests),
                        (skippedTests > 0 ? "text-align:right; background-color: #F33"
                                : "text-align:right"));
                writeTableData(integerFormat.format(failedTests),
                        (failedTests > 0 ? "text-align:right; background-color: #F33"
                                : "text-align:right"));
                writeTableData(decimalFormat.format(duration), "text-align:right");
                writeTableData(testResult.getIncludedGroups());
                writeTableData(testResult.getExcludedGroups());

                totalPassedTests += passedTests;
                totalSkippedTests += skippedTests;
                totalFailedTests += failedTests;
                totalDuration += duration;

                testIndex++;
            }
        }

    }

    /**
     * Writes a summary of all the test scenarios.
     */
    protected void writeScenarioSummary() {

        summaryWritter
                .print("<table width=\"100%\" border=\"1px\" style=\"border-collapse:collapse;empty-cells:show;border:1px solid #009;\" >");

        int testIndex = 0;
        int scenarioIndex = 0;
        for (SuiteResult suiteResult : suiteResults) {

            boolean hasFailures = false;
            for (TestResult testResult : suiteResult.getTestResults()) {
                if (testResult.getFailedConfigurationResults().size() > 0
                        || testResult.getFailedTestResults().size() > 0
                        || testResult.getSkippedConfigurationResults().size() > 0
                        || testResult.getSkippedTestResults().size() > 0) {
                    hasFailures = true;
                }
            }

            if (hasFailures) {

                hasFailures = false;
            }

            for (TestResult testResult : suiteResult.getTestResults()) {
                /*
                 * writer.print("<tbody id=\"t"); writer.print(testIndex); writer.print("\">");
                 */

                summaryWritter.print("<tbody id=\"t");
                summaryWritter.print(testIndex);
                summaryWritter.print("\">");

                scenarioIndex += writeScenarioSummary("FAILED (configuration methods)",
                        testResult.getFailedConfigurationResults(), "failed", scenarioIndex);
                scenarioIndex += writeScenarioSummary("FAILED", testResult.getFailedTestResults(),
                        "failed", scenarioIndex);
                scenarioIndex += writeScenarioSummary("SKIPPED (configuration methods)",
                        testResult.getSkippedConfigurationResults(), "skipped", scenarioIndex);
                scenarioIndex += writeScenarioSummary("SKIPPED",
                        testResult.getSkippedTestResults(), "skipped", scenarioIndex);
                scenarioIndex += writeScenarioSummary("PASSED", testResult.getPassedTestResults(),
                        "passed", scenarioIndex);

                // writer.print("</tbody>");
                summaryWritter.print("</tbody>");

                testIndex++;
            }
        }

        // writer.print("</table>");
        summaryWritter.print("</table>");
    }

    /**
     * Writes the scenario summary for the results of a given state for a single test.
     * @param description the description
     * @param classResults the class results
     * @param cssClassPrefix the css class prefix
     * @param startingScenarioIndex the starting scenario index
     * @return the int
     */
    private int writeScenarioSummary(String description, List<ClassResult> classResults,
            String cssClassPrefix, int startingScenarioIndex) {

        int scenarioCount = 0;
        if (!classResults.isEmpty()) {

            String backgroundColor = "";
            if (cssClassPrefix.contains("failed")) {
                backgroundColor = "#fc2203";
            } else if (cssClassPrefix.contains("passed")) {
                backgroundColor = "#11ca0d";
            } else if (cssClassPrefix.contains("skipped")) {
                backgroundColor = "#fbf537";
            }

            int scenarioIndex = startingScenarioIndex;
            // int classIndex = 0;
            for (ClassResult classResult : classResults) {

                String cssClass = "";
                buffer.setLength(0);

                int scenariosPerClass = 0;
                int methodIndex = 0;
                for (MethodResult methodResult : classResult.getMethodResults()) {
                    List<ITestResult> results = methodResult.getResults();
                    int resultsCount = results.size();
                    assert resultsCount > 0;

                    ITestResult firstResult = results.get(0);
                    String methodName = Utils.escapeHtml(firstResult.getMethod().getMethodName());
                    long start = firstResult.getStartMillis();
                    DateTimeZone zone = DateTimeZone.forID("America/Los_Angeles");
                    String startTime = DateUtil.formatToZone(start, zone, DateUtil.MM_DD_YY);
                    long duration = firstResult.getEndMillis() - start;
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);

                    // The first method per class shares a row with the class
                    // header
                    if (methodIndex > 0) {
                        buffer.append("<tr style=\"").append(cssClass.toLowerCase()).append("\">");

                    }

                    scenarioIndex++;

                    // Write the remaining scenarios for the method
                    for (int i = 1; i < resultsCount; i++) {
                        firstResult = results.get(i);
                        start = firstResult.getStartMillis();
                        zone = DateTimeZone.forID("America/Los_Angeles");
                        startTime = DateUtil.formatToZone(start, zone, DateUtil.MM_DD_YY);

                        scenarioIndex++;
                    }

                    scenariosPerClass += resultsCount;
                    methodIndex++;
                }

            }
            scenarioCount = scenarioIndex - startingScenarioIndex;
        }
        return scenarioCount;
    }

    /**
     * Write reporter messages.
     * @param reporterMessages the reporter messages
     */
    protected void writeReporterMessages(List<String> reporterMessages) {

        StringBuilder content = new StringBuilder();
        String timeContent = "";
        content.append("<div class=\"messages\">");
        Iterator<String> iterator = reporterMessages.iterator();
        assert iterator.hasNext();
        content.append(iterator.next());
        while (iterator.hasNext()) {
            String sValue = iterator.next();
            content.append("<br/>");
            if (!sValue.contains("############")) {
                content.append(sValue);
            } else {
                timeContent = sValue;
            }
        }
        content.append("</div>");

        if (timeContent != null && !timeContent.isEmpty()) {
            String startTime = timeContent.substring(0, timeContent.lastIndexOf("############"));
            String endTime = timeContent.substring(timeContent.lastIndexOf("############") + 12,
                    timeContent.length());
            content.replace(0, 22, "<div class=\"messages\">" + startTime);
            content.append("<br/>" + endTime);
        }
        writer.print(content.toString());
    }

    /**
     * Write stack trace.
     * @param throwable the throwable
     */
    protected void writeStackTrace(Throwable throwable) {

        /*
         * exceptionWriter.print("<div style=\"white-space:pre;font-family:monospace\">");
         * exceptionWriter.print(Utils.stackTrace(throwable, true)[0]);
         * exceptionWriter.print("</div>");
         */
    }

    /**
     * Writes a TH element with the specified contents and CSS class names.
     * @param html the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no classes to apply
     */
    protected void writeTableHeader(String html, String cssClasses) {

        // writeTag("th", html, cssClasses);
    }

    /**
     * Writes a TD element with the specified contents.
     * @param html the HTML contents
     */
    protected void writeTableData(String html) {

        writeTableData(html, null);
    }

    /**
     * Writes a TD element with the specified contents and CSS class names.
     * @param html the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no classes to apply
     */
    protected void writeTableData(String html, String cssClasses) {

        // writeTag("td", html, cssClasses);
    }

    /**
     * Writes an arbitrary HTML element with the specified contents and CSS class names.
     * @param tag the tag name
     * @param html the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no classes to apply
     */
    protected void writeTag(String tag, String html, String cssClasses) {

        writer.print("<");
        writer.print(tag);
        if (cssClasses != null) {
            writer.print(" style=\"");
            writer.print(cssClasses);
            writer.print("\"");
        }
        writer.print(">");
        writer.print(html);
        writer.print("</");
        writer.print(tag);
        writer.print(">");
    }

    /**
     * Groups {@link TestResult}s by suite.
     * @author $Author: vselvaraj $
     * @version $Rev: 68160 $ $Date: 2014-08-28 14:41:02 +0530 (Thu, 28 Aug 2014) $
     */
    protected static class SuiteResult {
        private final String suiteName;
        private final List<TestResult> testResults = Lists.newArrayList();

        /**
         * Instantiates a new suite result.
         * @param suite the suite
         */
        public SuiteResult(ISuite suite) {

            suiteName = suite.getName();
            for (ISuiteResult suiteResult : suite.getResults().values()) {
                testResults.add(new TestResult(suiteResult.getTestContext()));
            }
        }

        /**
         * Gets the suite name.
         * @return the suite name
         */
        public String getSuiteName() {

            return suiteName;
        }

        /**
         * Gets the test results.
         * @return the test results (possibly empty)
         */
        public List<TestResult> getTestResults() {

            return testResults;
        }
    }

    /**
     * Groups {@link ClassResult}s by test, type (configuration or test), and status.
     * @author $Author: vselvaraj $
     * @version $Rev: 68160 $ $Date: 2014-08-28 14:41:02 +0530 (Thu, 28 Aug 2014) $
     */
    protected static class TestResult {
        /**
         * Orders test results by class name and then by method name (in lexicographic order).
         */
        protected static final Comparator<ITestResult> RESULT_COMPARATOR = new Comparator<ITestResult>() {
            @Override
            public int compare(ITestResult o1, ITestResult o2) {

                int result = o1.getTestClass().getName().compareTo(o2.getTestClass().getName());
                if (result == 0) {
                    result = o1.getMethod().getMethodName()
                            .compareTo(o2.getMethod().getMethodName());
                }
                return result;
            }
        };

        private final String testName;
        private final List<ClassResult> failedConfigurationResults;
        private final List<ClassResult> failedTestResults;
        private final List<ClassResult> skippedConfigurationResults;
        private final List<ClassResult> skippedTestResults;
        private final List<ClassResult> passedTestResults;
        private final int failedTestCount;
        private final int skippedTestCount;
        private final int passedTestCount;
        private final long duration;
        private final String includedGroups;
        private final String excludedGroups;

        /**
         * Instantiates a new test result.
         * @param context the context
         */
        public TestResult(ITestContext context) {

            testName = context.getName();

            Set<ITestResult> failedConfigurations = context.getFailedConfigurations()
                    .getAllResults();
            Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
            Set<ITestResult> skippedConfigurations = context.getSkippedConfigurations()
                    .getAllResults();
            Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
            Set<ITestResult> passedTests = context.getPassedTests().getAllResults();

            failedConfigurationResults = groupResults(failedConfigurations);
            failedTestResults = groupResults(failedTests);
            skippedConfigurationResults = groupResults(skippedConfigurations);
            skippedTestResults = groupResults(skippedTests);
            passedTestResults = groupResults(passedTests);

            failedTestCount = failedTests.size();
            skippedTestCount = skippedTests.size();
            passedTestCount = passedTests.size();

            duration = context.getEndDate().getTime() - context.getStartDate().getTime();

            includedGroups = formatGroups(context.getIncludedGroups());
            excludedGroups = formatGroups(context.getExcludedGroups());
        }

        /**
         * Groups test results by method and then by class.
         * @param results the results
         * @return the list
         */
        protected List<ClassResult> groupResults(Set<ITestResult> results) {

            List<ClassResult> classResults = Lists.newArrayList();
            if (!results.isEmpty()) {
                List<MethodResult> resultsPerClass = Lists.newArrayList();
                List<ITestResult> resultsPerMethod = Lists.newArrayList();

                List<ITestResult> resultsList = Lists.newArrayList(results);
                Collections.sort(resultsList, RESULT_COMPARATOR);
                Iterator<ITestResult> resultsIterator = resultsList.iterator();
                assert resultsIterator.hasNext();

                ITestResult result = resultsIterator.next();
                resultsPerMethod.add(result);

                String previousClassName = result.getTestClass().getName();
                String previousMethodName = result.getMethod().getMethodName();
                while (resultsIterator.hasNext()) {
                    result = resultsIterator.next();

                    String className = result.getTestClass().getName();
                    if (!previousClassName.equals(className)) {
                        // Different class implies different method
                        assert !resultsPerMethod.isEmpty();
                        resultsPerClass.add(new MethodResult(resultsPerMethod));
                        resultsPerMethod = Lists.newArrayList();

                        assert !resultsPerClass.isEmpty();
                        classResults.add(new ClassResult(previousClassName, resultsPerClass));
                        resultsPerClass = Lists.newArrayList();

                        previousClassName = className;
                        previousMethodName = result.getMethod().getMethodName();
                    } else {
                        String methodName = result.getMethod().getMethodName();
                        if (!previousMethodName.equals(methodName)) {
                            assert !resultsPerMethod.isEmpty();
                            resultsPerClass.add(new MethodResult(resultsPerMethod));
                            resultsPerMethod = Lists.newArrayList();

                            previousMethodName = methodName;
                        }
                    }
                    resultsPerMethod.add(result);
                }
                assert !resultsPerMethod.isEmpty();
                resultsPerClass.add(new MethodResult(resultsPerMethod));
                assert !resultsPerClass.isEmpty();
                classResults.add(new ClassResult(previousClassName, resultsPerClass));
            }
            return classResults;
        }

        /**
         * Gets the test name.
         * @return the test name
         */
        public String getTestName() {

            return testName;
        }

        /**
         * Gets the failed configuration results.
         * @return the results for failed configurations (possibly empty)
         */
        public List<ClassResult> getFailedConfigurationResults() {

            return failedConfigurationResults;
        }

        /**
         * Gets the failed test results.
         * @return the results for failed tests (possibly empty)
         */
        public List<ClassResult> getFailedTestResults() {

            return failedTestResults;
        }

        /**
         * Gets the skipped configuration results.
         * @return the results for skipped configurations (possibly empty)
         */
        public List<ClassResult> getSkippedConfigurationResults() {

            return skippedConfigurationResults;
        }

        /**
         * Gets the skipped test results.
         * @return the results for skipped tests (possibly empty)
         */
        public List<ClassResult> getSkippedTestResults() {

            return skippedTestResults;
        }

        /**
         * Gets the passed test results.
         * @return the results for passed tests (possibly empty)
         */
        public List<ClassResult> getPassedTestResults() {

            return passedTestResults;
        }

        /**
         * Gets the failed test count.
         * @return the failed test count
         */
        public int getFailedTestCount() {

            return failedTestCount;
        }

        /**
         * Gets the skipped test count.
         * @return the skipped test count
         */
        public int getSkippedTestCount() {

            return skippedTestCount;
        }

        /**
         * Gets the passed test count.
         * @return the passed test count
         */
        public int getPassedTestCount() {

            return passedTestCount;
        }

        /**
         * Gets the duration.
         * @return the duration
         */
        public long getDuration() {

            return duration;
        }

        /**
         * Gets the included groups.
         * @return the included groups
         */
        public String getIncludedGroups() {

            return includedGroups;
        }

        /**
         * Gets the excluded groups.
         * @return the excluded groups
         */
        public String getExcludedGroups() {

            return excludedGroups;
        }

        /**
         * Formats an array of groups for display.
         * @param groups the groups
         * @return the string
         */
        protected String formatGroups(String[] groups) {

            if (groups.length == 0) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            builder.append(groups[0]);
            for (int i = 1; i < groups.length; i++) {
                builder.append(", ").append(groups[i]);
            }
            return builder.toString();
        }
    }

    /**
     * Groups {@link MethodResult}s by class.
     * @author $Author: vselvaraj $
     * @version $Rev: 68160 $ $Date: 2014-08-28 14:41:02 +0530 (Thu, 28 Aug 2014) $
     */
    protected static class ClassResult {
        private final String className;
        private final List<MethodResult> methodResults;

        /**
         * Instantiates a new class result.
         * @param className the class name
         * @param methodResults the non-null, non-empty {@link MethodResult} list
         */
        public ClassResult(String className, List<MethodResult> methodResults) {

            this.className = className;
            this.methodResults = methodResults;
        }

        /**
         * Gets the class name.
         * @return the class name
         */
        public String getClassName() {

            return className;
        }

        /**
         * Gets the method results.
         * @return the non-null, non-empty {@link MethodResult} list
         */
        public List<MethodResult> getMethodResults() {

            return methodResults;
        }
    }

    /**
     * Groups test results by method.
     * @author $Author: vselvaraj $
     * @version $Rev: 68160 $ $Date: 2014-08-28 14:41:02 +0530 (Thu, 28 Aug 2014) $
     */
    protected static class MethodResult {
        private final List<ITestResult> results;

        /**
         * Instantiates a new method result.
         * @param results the non-null, non-empty result list
         */
        public MethodResult(List<ITestResult> results) {

            this.results = results;
        }

        /**
         * Gets the results.
         * @return the non-null, non-empty result list
         */
        public List<ITestResult> getResults() {

            return results;
        }
    }
}