package com.browserstack.run_first_test;

//import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class VysorConn {
    public static AndroidDriver driver;
//    public static DesiredCapabilities caps;
    @BeforeClass
    public static void setup() throws MalformedURLException{


        DesiredCapabilities caps = new DesiredCapabilities();


        caps.setCapability("appium:platformName", "android");
        caps.setCapability("appium:platformVersion", "11");
        caps.setCapability("appium:deviceName", "eeiz7tmnvk59y94d");
        caps.setCapability("appium:appPackage", "com.google.android.apps.maps");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appActivity", "com.google.android.maps.MapsActivity");
        caps.setCapability("appium:newCommandTimeout", 3600);
        caps.setCapability("appium:connectHardwareKeyboard", true);
//        caps.setCapability(MobileCapabilityType.FULL_RESET, false);
//        caps.setCapability(MobileCapabilityType.NO_RESET, false);


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
//        driver.installApp("src/Apps/com.google.android.apps.maps_11.102.0101.apk");
//        boolean isInstalled = driver.isAppInstalled("com.google.android.apps.maps");


//        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);




    }

    @AfterClass
    public void teardown() throws InterruptedException {
//        driver.removeApp("com.google.android.apps.maps");
        sleep(5000);


        driver.quit();
    }


    }
