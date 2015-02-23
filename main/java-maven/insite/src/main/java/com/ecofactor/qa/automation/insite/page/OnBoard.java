/*
 * OnBoard.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface OnBoard.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface OnBoard extends InsiteAuthenticatedPage {

    /**
     * Upload csv.
     * @param csvPath the csv path
     * @param testScenario the test scenario
     */
    public void verifyFileUpload(String csvPath, String testScenario);

    /**
     * Upload csv and get fields.
     */
    public void verifySameEmailOverridesOldAccount();

    /**
     * Generate filepath.
     * @param extension the extension
     * @return the string
     */
    public String generateFilepath(String extension);

    /**
     * Generate valid csv.
     * @param noOfRecords the no of records
     * @param path the path
     * @return the string
     */
    public String generateValidCSV(int noOfRecords, String path,String ecpId);

    /**
     * Upload and submit file.
     * @param path the path
     */
    public void uploadAndSubmitFile(String path);

    /**
     * Verify duplicate file.
     * @return true, if successful
     */
    public boolean verifyDuplicateFile();

    /**
     * Verify invalid csv.
     * @return true, if successful
     */
    public boolean verifyInvalidCsv();

    /**
     * Verify user.
     * @param userName the user name
     */
    public void verifyUser(String userName);

    /**
     * Verify address look up.
     * @param fileName the file name
     */
    public void verifyAddressLookUp(String fileName);

}
