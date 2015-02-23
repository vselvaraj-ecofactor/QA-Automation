package com.ecofactor.qa.automation.util.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.reporters.DotTestListener;

/**
 * <b>TestResultSetUp</b> Listener to setUp the test case result in a matrix
 * fromat
 *
 * @author Aximsoft
 *
 */
public class TestResultSetUp extends DotTestListener implements ITestListener, ISuiteListener {

	private String suiteName = "";
	public static Map<String, Integer> passMap = new HashMap<String, Integer>();
	public static Map<String, Integer> skipMap = new HashMap<String, Integer>();
	public static Map<String, Integer> failMap = new HashMap<String, Integer>();
	public static Map<String, Integer> totalResult = new HashMap<String, Integer>();

	public static List<String> retryPassList = new ArrayList<String>();
	public static List<String> retrySkipList = new ArrayList<String>();
	public static List<String> retryFailList = new ArrayList<String>();

	static int skipVal = 0;
	static int passVal = 0;
	static int failVal = 0;

	/**
	 * <p>
	 * Read the test failures and handles in a variable
	 * </p>
	 */
	public void onTestFailure(ITestResult tr) {

		if (!retryFailList.contains(tr.getMethod().toString())) {
			retryFailList.add(tr.getMethod().toString());
			if (failMap.get(suiteName) != null) {
				failVal = failMap.get(suiteName) + 1;
			} else {
				failVal = 1;
			}

			failMap.put(suiteName, failVal);
		}

	}

	/**
	 * <p>
	 * Read the test skipped values and handles in a variable
	 * </p>
	 */
	public void onTestSkipped(ITestResult tr) {

		if (!retryFailList.contains(tr.getMethod().toString())) {
			retryFailList.add(tr.getMethod().toString());
			if (failMap.get(suiteName) != null) {
				failVal = failMap.get(suiteName) + 1;
			} else {
				failVal = 1;
			}

			failMap.put(suiteName, failVal);
		}

		/*
		 * if(!retrySkipList.contains(tr.getMethod().toString())) {
		 * retrySkipList.add(tr.getMethod().toString());
		 * if(skipMap.get(suiteName)!=null) { skipVal= skipMap.get(suiteName)+1;
		 * } else { skipVal=1; } skipMap.put(suiteName, skipVal); }
		 */

	}

	/**
	 * <p>
	 * Read the test passed values and handles in a variable
	 * </p>
	 */
	public void onTestSuccess(ITestResult tr) {
		if (!retryPassList.contains(tr.getMethod().toString())) {
			retryPassList.add(tr.getMethod().toString());
			if (passMap.get(suiteName) != null) {
				passVal = passMap.get(suiteName) + 1;
			} else {
				passVal = 1;
			}

			if (retryFailList.contains(tr.getMethod().toString())) {
				int failVal = failMap.get(suiteName);
				failMap.put(suiteName, failVal - 1);
			}

			passMap.put(suiteName, passVal);
		}
	}

	/**
	 * <p>
	 * On Suite in ended the listener take care of test results and populate
	 * into a file
	 * </p>
	 */
	public void onFinish(ISuite arg0) {
		String htmlContent = "";

		Iterator<Entry<String, Integer>> iterator = passMap.entrySet().iterator();
		int pass = 0;
		int skip = 0;
		int fail = 0;
		int suiteTotalTestCase = 0;
		while (iterator.hasNext()) {
			Entry<String, Integer> pairs = iterator.next();

			if (passMap.get(pairs.getKey()) != null) {
				pass = passMap.get(pairs.getKey());
			}

			if (skipMap.get(pairs.getKey()) != null) {
				skip = skipMap.get(pairs.getKey());
			}

			if (failMap.get(pairs.getKey()) != null) {
				fail = failMap.get(pairs.getKey());
			}

			suiteTotalTestCase = pass + skip + fail;

			htmlContent += "<tr><td>" + pairs.getKey() + "</td> <td>" + suiteTotalTestCase
			        + "</td> <td><font style='color:green'>" + pass + "</font></td> <td><font style='color:red'>"
			        + fail + "</font></td><td><font style='color:blue'>" + skip + "</font></td></tr>";
			iterator.remove(); // avoids a ConcurrentModificationException
		}

		if (totalResult.get("totalTest") == null) {
			totalResult.put("totalTest", suiteTotalTestCase);
		} else {
			totalResult.put("totalTest", totalResult.get("totalTest") + suiteTotalTestCase);
		}

		if (totalResult.get("passTest") == null) {
			totalResult.put("passTest", pass);
		} else {
			totalResult.put("passTest", totalResult.get("passTest") + pass);
		}

		if (totalResult.get("failTest") == null) {
			totalResult.put("failTest", fail);
		} else {
			totalResult.put("failTest", totalResult.get("failTest") + fail);
		}

		if (totalResult.get("skipTest") == null) {
			totalResult.put("skipTest", skip);
		} else {
			totalResult.put("skipTest", totalResult.get("skipTest") + skip);
		}

		writeOutputToAFile(htmlContent);
	}

	/**
	 * <p>
	 * To get the suit name
	 * </p>
	 */
	public void onStart(ISuite isuite) {
		setSuiteName(isuite.getName());
	}

	/**
	 * <p>
	 * Set the suitName to the class variable
	 * </p>
	 *
	 * @param suitName
	 */
	private void setSuiteName(String suitName) {
		this.suiteName = suitName;
	}

	/**
	 * Write the output to a file in the target folder.
	 *
	 * @param content
	 */
	private void writeOutputToAFile(String content) {
		try {
			File dir = new File(".");
			String path = dir.getAbsolutePath();
			path = path.substring(0, path.length() - 1);
			path += "target\\surefire-reports\\custom\\";

			File f = new File(path);
			if (!f.exists())
				f.mkdirs();

			File readProps = new File(path + "testResult.properties");

			Properties props = new Properties();

			if (readProps != null && readProps.isFile()) {
				props.load(new FileInputStream(readProps));
			} else {
				readProps.createNewFile();
			}

			props.setProperty("totalTestCase", totalResult.get("totalTest").toString());
			props.setProperty("skip", totalResult.get("skipTest").toString());
			props.setProperty("pass", totalResult.get("passTest").toString());
			props.setProperty("fail", totalResult.get("failTest").toString());
			props.store(new FileOutputStream(new File(path + "testResult.properties")), "");

			path += "customTestReportResult" + suiteName + ".html";

			// Create file
			FileWriter fstream = new FileWriter(path);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);

			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
