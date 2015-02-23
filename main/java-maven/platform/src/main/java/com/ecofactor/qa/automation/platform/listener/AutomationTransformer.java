/*
 * AutomationTransformer.java Copyright (c) 2012, EcoFactor, All Rights Reserved. This software is
 * the confidential and proprietary information of EcoFactor ("Confidential Information"). You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with EcoFactor.
 */
package com.ecofactor.qa.automation.platform.listener;

import static com.ecofactor.qa.automation.platform.constants.Groups.*;
import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.ecofactor.qa.automation.platform.util.SystemUtil;

/**
 * The Class AutomationTransformer.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AutomationTransformer implements IAnnotationTransformer {

    /**
     * Transform.
     * @param annotation the annotation
     * @param testClass the test class
     * @param testConstructor the test constructor
     * @param testMethod the test method
     * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation,
     *      java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void transform(final ITestAnnotation annotation, final Class testClass,
            final Constructor testConstructor, final Method testMethod) {

        final List<String> groupList = new ArrayList<>(arrayToList(annotation.getGroups()));
        if (!isTestSupported(groupList)) {
            annotation.setGroups(null);
        }
    }

    /**
     * Checks if is test supported.
     * @param groupList the group list
     * @return true, if is test supported
     */
    private boolean isTestSupported(final List<String> groupList) {

        return (hasJenkinsDeviceNameParam() && (isAndroidTest(groupList) || isMacTest(groupList)))
                || isBrowserOnlyTest(groupList);
    }

    /**
     * Checks if is browser only test.
     * @param groupList the group list
     * @return true, if is browser only test
     */
    private boolean isBrowserOnlyTest(final List<String> groupList) {

        return !hasJenkinsDeviceNameParam() && hasJenkinsBrowserParam()
                && groupList.contains(BROWSER);
    }

    /**
     * Checks if is mac test.
     * @param groupList the group list
     * @return true, if is mac test
     */
    private boolean isMacTest(final List<String> groupList) {

        return SystemUtil.isMac() && groupList.contains(IOS);
    }

    /**
     * Checks if is android test.
     * @param groupList the group list
     * @return true, if is android test
     */
    private boolean isAndroidTest(final List<String> groupList) {

        return SystemUtil.isWindows() && groupList.contains(ANDROID);
    }
}
