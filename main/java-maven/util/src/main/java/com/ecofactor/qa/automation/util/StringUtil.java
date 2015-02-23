/*
 * StringUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.util.Iterator;
import java.util.List;

public class StringUtil {

    /**
     * List to string.
     * @param queryList the query list
     * @param delimitedString the delimited string
     * @return the string
     */
    public static String listToString(List<String> queryList, String delimitedString) {

        String newString = "";
        for (Iterator<String> it = queryList.iterator(); it.hasNext();) {
            newString += it.next();
            if (it.hasNext()) {
                newString += delimitedString;
            }
        }
        return newString;
    }
}
