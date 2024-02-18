package com.selenium.test.testng.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.time.Duration;

public class SetUp {
    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    public void setDriver (AppiumDriver driver){
        this.driver.set(driver);
    }
    public AppiumDriver getDriver(){
        return this.driver.get();
    }
    public WebDriverWait wait;

    @Parameters({"deviceName","platformVersion","platformName","portNumber"})
    @BeforeMethod
    public void setUp(String deviceName, String platformVersion, String platformName, String portNumber) throws Exception {
        startAppiumService(portNumber);
        if(platformName.equalsIgnoreCase("iOS")){
            XCUITestOptions options = new XCUITestOptions();
            options.setDeviceName(deviceName)
                    .setPlatformVersion(platformVersion)
                    .setPlatformName(platformName)
                    .setBundleId("com.apple.AppStore")
                    .setUdid("3627fc75276cdbbe7d1055f8743fb4ca2adb8acd")
                    .setNewCommandTimeout(Duration.ofSeconds(180));

            setDriver(new IOSDriver(new URL("http://0.0.0.0:"+portNumber+"/"), options));
        }
        else if(platformName.equalsIgnoreCase("Android")) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setDeviceName(deviceName)
                    .setPlatformVersion(platformVersion)
                    .setPlatformName(platformName)
                    .setAppPackage("com.android.vending")
                    .setAppActivity("com.google.android.finsky.activities.MainActivity")
                    .setNewCommandTimeout(Duration.ofSeconds(180));
            setDriver(new AndroidDriver(new URL("http://0.0.0.0:"+portNumber+"/"), options));
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        //getDriver().quit();
    }

    public void startAppiumService(String portNumber){
        AppiumDriverLocalService service;
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(Integer.parseInt(portNumber));
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        System.out.println("Service has been started with :" + portNumber);
    }
}
