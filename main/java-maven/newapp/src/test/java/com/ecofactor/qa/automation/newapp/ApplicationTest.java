package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.ops.impl.IOSOperations;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.google.inject.Inject;

@Guice(modules = { MobileModule.class, DaoModule.class })
public class ApplicationTest {

    @Inject
    protected TestOperations testOps;
    @Inject
    protected IOSOperations iOSOps;

    @Test(groups = { Groups.SANITY1, Groups.SANITY2, Groups.SMOKE, Groups.STUB, Groups.ANDROID,
            Groups.IOS })
    public void displayApkBuildVersion() throws Exception {

        testOps.displayAppInfo();
        LogUtil.setHeading("Build Number : " + System.getProperty("buildCode"), true);
        LogUtil.setHeading("App version Name : " + System.getProperty("buildVersion"), true);        
    }

    /**
     * Download configurable.
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.SANITY2, Groups.SMOKE, Groups.STUB, Groups.ANDROID,
            Groups.IOS })
    public void downloadConfigurable() throws Exception {

        if (hasDownloadParam()) {
            getLastestAppIntoFolder();
        }
    }

    /**
     * Install uninstall app.
     * @throws Exception the exception
     */
    @Test
    public void installUninstallApp() throws Exception {

        if (hasDownloadParam()) {
            if (System.getProperty("downloadApp") != getAppVersion("buildVersion")) {
                getLastestAppIntoFolder();
            }
            writePropertiesToFile(iOSOps.getAppDetails(), "appDetails.properties",
                    "IOS App Details");
        }
        iOSOps.installUnInstallAppOnDevice();
    }
}
