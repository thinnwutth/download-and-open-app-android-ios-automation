package com.selenium.test.testng.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class androidInstalledAppEvent{

    private AppiumDriver driver;
    private WebDriverWait wait;
    final String AppName = "TestApp.io";
    final String searchKeyword = "TestApp.io";
    final String installedAppPackage = "testapp.io";
    final String installedAppActivity = "com.testappio.MainActivity";
    final String userName = "thinwutt.hmone.mhs@gmail.com";
    final String password = "My@nm@r2023";

    //Elements of PlayStore
    String PlayStoreClickSearchBox = "//android.widget.TextView[@text='Search apps & games']";
    String PlayStoreEditSearchTextBox = "android.widget.EditText";
    String PlayStoreTextView = "(//android.widget.TextView[@text='"+AppName+"'])[1]";
    String PlayStoreAppInstall = "//android.widget.Button[@text='Install']";
    String PlayStoreAppUnintall = "//android.widget.Button[@text='Uninstall']";

    //Elements of TestApp.io for Android
    String TestAppSkip = "//android.widget.TextView[@text='skip']";
    String TestAppEmail = "//android.widget.EditText[@text='Email Address']";
    String TestAppPassword = "//android.widget.EditText[@text='Enter your password']";
    String TestAppSignIn = "//android.widget.TextView[@text='Sign In']";
    String TestAppBurgerMenu = "(//android.widget.TextView)[1]";
    String TestAppAccountSubMenu = "//android.view.ViewGroup[@resource-id='ACCOUNT_AND_SETTINGS_DRAWER_ITEM']";
    String TestAppSignOut = "//android.widget.TextView[@text='Sign Out']";

    public androidInstalledAppEvent(AppiumDriver driver){
        this.driver=driver;
    }

    public void installAppFromGooglePlayStore() throws Exception {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(PlayStoreClickSearchBox))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.className(PlayStoreEditSearchTextBox))))
                .sendKeys(searchKeyword);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElement(By.xpath(PlayStoreTextView)).click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(PlayStoreAppInstall))))
                .click();

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        new WebDriverWait(driver, Duration.ofSeconds(800)).until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(PlayStoreAppUnintall)))
                .click();

        System.out.println("Installed " + AppName);
        driver.quit();

        LoginInstalledAndroidApp();

    }

    public void LoginInstalledAndroidApp() throws Exception {
        this.driver = new AndroidDriver(new URL("http://0.0.0.0:4723/"), installedTestAppCap());

        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(TestAppSkip)))
                .click();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(TestAppEmail))))
                .sendKeys(userName);

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(TestAppPassword))))
                .sendKeys(password);

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(TestAppSignIn))))
                .click();

        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(TestAppBurgerMenu)))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(TestAppAccountSubMenu))))
                .click();

        wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(TestAppSignOut))))
                .click();

        System.out.println("Successful Logout!");

        ((AndroidDriver) driver).removeApp(installedAppPackage);
        System.out.println("Uninstalled "+ AppName);
    }

    private UiAutomator2Options installedTestAppCap() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel-7")
                .setPlatformName("Android")
                .setAppPackage(installedAppPackage)
                .setAppActivity(installedAppActivity)
                .setNewCommandTimeout(Duration.ofSeconds(180));

        return options;
    }
}