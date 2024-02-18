package com.selenium.test.testng.tests;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.Theories;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;



public class iosInstalledAppEvent{

    public AppiumDriver driver;
    public WebDriverWait wait;

    final String AppName = "SnapChat";
    final String installedAppPackage = "com.toyopagroup.picaboo";
    final String userName = "sushant233646";
    final String password = "myanmars";

    //Elements of AppStore
    String AppStoreSearchBox = "UIA.AppStore.TabBar.search";
    String AppStoreSearchTextField = "UIA.AppStore.SearchTextField";
    String AppStoreTextView = "Snapchat, Share the moment";
    String AppStoreButton = "UIA.AppStore.OfferButton";
    String AppStoreButtonReDownload = "label == 're-download'";
    String AppStoreButtonOPEN = "label == 'OPEN'";


    //Elements of SNapchat
    String TestAppLogin = "LOG IN";
    String TestAppEmailiOs = "username";
    String TestAppPasswordiOs = "PASSWORD";
    String TestAppLoginButton = "login_btn_login";
    String TestAppAccessAllow = "Allow";
    String TestAppAccessOK= "OK";
    String TestAppNotNowButton = "Not Now";
    String TestAppProfile = "profile";
    String TestAppSetting = "//XCUIElementTypeOther[@name='unified_profile_icon_button_settings']/XCUIElementTypeImage";
    String TestAppLogout = "Log Out";

    public iosInstalledAppEvent(AppiumDriver driver){
        this.driver=driver;
    }


    public void installAppFromAppStore() throws Exception {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(AppStoreSearchBox))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(AppStoreSearchTextField))))
                .sendKeys(AppName);

        driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND label == 'search'")).click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(AppStoreTextView))))
                .click();

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        String AppStoreButtonText = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(MobileBy.AccessibilityId(AppStoreButton)))).getText();
        System.out.println("Actual AppStore download button text:  " + AppStoreButtonText);

        if(AppStoreButtonText.equals("re-download")){

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.iOSNsPredicateString(AppStoreButtonReDownload))).click();

            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            new WebDriverWait(driver, Duration.ofSeconds(800)).until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.iOSNsPredicateString(AppStoreButtonOPEN)));

            System.out.println("Installed " + AppName);
            driver.quit();

            LoginInstallediosApp();

        }
        else{
            System.out.println("Already installed!");
            driver.quit();
        }

    }

    public void LoginInstallediosApp() throws Exception {
        this.driver = new IOSDriver(new URL("http://0.0.0.0:4723/"), installedTestAppCap());

        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppLogin))))
                .click();

        WebElement userNameField = driver.findElement(MobileBy.AccessibilityId(TestAppEmailiOs));
        wait.until(ExpectedConditions.elementToBeClickable(userNameField));
        userNameField.click();
        for (char character : userName.toCharArray()) {
            userNameField.sendKeys(Character.toString(character));
            Thread.sleep(100);
        }

        driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND label == 'next'")).click();

        WebElement passwordField = driver.findElement(MobileBy.AccessibilityId(TestAppPasswordiOs));
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.click();

        for (char character : password.toCharArray()) {
            passwordField.sendKeys(Character.toString(character));
            Thread.sleep(100);
        }

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(MobileBy.AccessibilityId(TestAppLoginButton)))).click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppAccessAllow))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppAccessOK))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppAccessOK))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppAccessOK))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppNotNowButton))))
                .click();


        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppProfile))))
                .click();

        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(TestAppSetting))))
                .click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        this.scroll(this.driver, 0.25, 1.0, 0.25, 0.0);
        this.scroll(this.driver, 0.25, 1.0, 0.25, 0.0);
        this.scroll(this.driver, 0.25, 1.0, 0.25, 0.0);

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(MobileBy.AccessibilityId(TestAppLogout))))
                .click();

        ((IOSDriver) driver).removeApp(installedAppPackage);
        System.out.println("Uninstalled "+ AppName);
    }


    private XCUITestOptions installedTestAppCap() throws Exception {

        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName("iPhoneX")
                .setPlatformVersion("16.6.1")
                .setPlatformName("ios")
                .setBundleId(installedAppPackage)
                .setUdid("3627fc75276cdbbe7d1055f8743fb4ca2adb8acd")
                .setNewCommandTimeout(Duration.ofSeconds(180));

        return options;
    }


    public void scroll(AppiumDriver driver, double start_xd, double start_yd, double end_xd, double end_yd) throws InterruptedException {
        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) (dimension.width * start_xd);
        int start_y = (int) (dimension.height * start_yd);

        int end_x = (int) (dimension.width * end_xd);
        int end_y = (int) (dimension.height * end_yd);

        TouchAction touchAction = new TouchAction ((PerformsTouchActions) driver);
        touchAction.press(PointOption.point(start_x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(end_x, end_y)).release().perform();

        Thread.sleep(3000);
    }

}

