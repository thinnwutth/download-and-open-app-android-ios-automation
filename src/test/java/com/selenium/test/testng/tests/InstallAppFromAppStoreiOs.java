package com.selenium.test.testng.tests;

import io.appium.java_client.*;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.selenium.test.testng.tests.iosInstalledAppEvent;

import java.net.URL;
import java.time.Duration;


public class InstallAppFromAppStoreiOs extends SetUp {
    @Test
    public void iosExecutionMethod() throws Exception {
        iosInstalledAppEvent iosAppEvent_obj = new iosInstalledAppEvent(getDriver());
        iosAppEvent_obj.installAppFromAppStore();
    }


}