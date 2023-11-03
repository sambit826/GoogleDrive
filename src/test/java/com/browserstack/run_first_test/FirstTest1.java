package com.browserstack.run_first_test;

import com.google.common.collect.ImmutableMap;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
//import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.collections4.Get;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import org.testng.mustache.Value;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class FirstTest1 extends VysorConn {




    int SimulateWait = 1000;

    @DataProvider(name = "test-data")
    public Object[][] dataProvFunc() throws URISyntaxException {
        List<String[]> addresses = ReadCsv();
        Object[][] testData = new Object[addresses.size() - 1][];
        for (int i = 0; i < addresses.size() - 1; i++) {
            testData[i] = new Object[1];
            testData[i][0] = addresses.get(i + 1);
        }

        System.out.println("In Data Provider" + addresses);
        return testData;
    }

    @Test(dataProvider = "test-data")
    public void test(String[] Parts) throws Exception {

        System.out.println("#############Test Started#######");

        SetWait();

        pressFlightMode(2000);
        performLocation(Parts);
        Login(Parts);
//        SkipLogin();
        sleep(5000);
        performTest(Parts);
        //       i++;
//            if(i<Addresses.size())
//            {
//                if(driver!=null)
//                { driver.quit();
//                  Thread.sleep(10000);
//                }
//                NewDriver();
//            }
//        }while(i<Addresses.size());
        System.out.println("#############Test Finished#############");

    }
    public void performLocation(String Parts[]) throws InterruptedException {
        List<LatLng> latLngs = DirectionPolyline.GetDirections(Parts[5], Parts[6]);
//        System.out.println(latLngs);
        float Lat ,Lng,Alt;
        Thread.sleep(5000);
        driver.setLocation(new Location(latLngs.get(0).getLat(), latLngs.get(0).getLng(),0));
        System.out.println("New location=           "+ latLngs.get(0).getLat() +","+ latLngs.get(0).getLng());
//        System.out.println("Location: "+Double.parseDouble(Parts[5])+","+ Double.parseDouble(Parts[6]));
    }


    public static List<String[]> ReadCsv() throws URISyntaxException {
        Path fileName = Paths.get("src/test/resources/Directions.csv");
//      Path fileName = Paths.get("C:\\Users\\Patil\\Downloads\\Directions.csv");

        try (Reader reader = Files.newBufferedReader(fileName)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    Wait wait;
    void SetWait()
    {
//        wait = new FluentWait(driver)
//                .withTimeout(90, SECONDS)
//                .pollingEvery(3, SECONDS)
//                .ignoring(Exception.class);

        wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(90))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);


    }


    public void performTest(String[] Parts) throws Exception
    {
//        Get list of lat long
        List<LatLng> latLngs = DirectionPolyline.GetDirections(Parts[5], Parts[6]);
        System.out.println("This is json " + latLngs);
        sleep(4000);
        Scrolls=0;
        float Lat,Lng,Alt;
//        System.out.println("Location: "+Double.parseDouble(Parts[5])+","+ Double.parseDouble(Parts[6]));
        System.out.println("Location from : "+Parts[5]+","+ Parts[6]);
        Thread.sleep(2000);

        driver.setLocation(new Location(latLngs.get(0).getLat(), latLngs.get(0).getLng(),0));

//        locationActivity();
//        SearchKeyword(Parts[3]);
//        System.out.println(Parts[3]);
//        if(!FindShop(Parts[4],Parts[6],Integer.parseInt(Parts[8]))){ return; }
//        StartDirectionsFromList();
//        SimulateLocations(Parts, latLngs );
//        RestartDirection();
//        FinishDirections();
//        SaveToTimeLine(Parts[4]);
//        sleep(5000);
////        Review(Parts);
        sleep(5000);
//        ResetApp();

    }

    @SuppressWarnings("rawtypes")
    public void pressFlightMode(int waitingTime){
        try {
            System.out.println("Enabling flight Mode");
            sleep(5000);
            ((AndroidDriver)driver).toggleAirplaneMode();
            System.out.println("#############Airplane mode on#############");
            sleep(5000);
            System.out.println("#############Airplane mode off#############");
            ((AndroidDriver)driver).toggleAirplaneMode();
            sleep(2000);
            Runtime.getRuntime().exec("adb shell cmd -w wifi set-wifi-enabled disabled");
            System.out.println("Wifi disabled");

            } catch (Exception e ) {
            e.printStackTrace();
            System.out.println("Error turning on flight mode.");
            }
    }

    void Login(String[] Parts) throws Exception {
        WebElement SignInButton = GetElement(wait,0,"Sign in","android.widget.Button","");
//        WebElement SignInButton = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button"));
        SignInButton.click();
        sleep(6000);

        WebElement AddAccount = GetElement(wait,0,"add account","android.widget.TextView","");
//        WebElement AddAccount = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout/android.widget.TextView"));
        AddAccount.click();
        sleep(10000);
        Thread.sleep(5000);


//        WebElement EmailIdTextBox = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.widget.TextView[2]"));
//        WebElement EmailIdTextBox = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.widget.TextView[2]"));
        tap(650,650);
        WebElement EmailIdTextBox = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.widget.EditText");

        System.out.println(EmailIdTextBox.getText());
        EmailIdTextBox.click();
        sleep(2000);
        //EmailIdTextBox.sendKeys("automationtest023@gmail.com");
        driver.getKeyboard().pressKey(Parts[0]);
        sleep(5000);

//        if (EmailIdTextBox.getText().equals(Parts[0])){
//            System.out.println("value in text box is-->" + EmailIdTextBox.getText());
//            System.out.println("email id entered");
//        }
//        else {
//            EmailIdTextBox.click();
//            driver.getKeyboard().pressKey(Parts[0]);
//        }


//        WebElement SINextButton = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"));
        WebElement SINextButton = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button");
        SINextButton.click();


        sleep(5000);

        WebElement EIdPassword = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.EditText");
//        WebElement EIdPassword = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.EditText"));
        EIdPassword.sendKeys(Parts[1]);
//        EIdPassword.sendKeys(Parts[1]);
        sleep(5000);

        WebElement PSNextButton = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button");
//        WebElement PSNextButton = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button"));
        PSNextButton.click();
        sleep(5000);

        WebElement EnterAuthCodeLabel = GetElement(wait, 0, "Get a verification code from the Google Authenticator app@Check your", "android.widget.TextView", "");
//        WebElement EnterAuthCodeLabel = driver.findElement(By.xpath("//android.view.View[@content-desc=\"Get a verification code from the Google Authenticator app\"]/android.widget.TextView"));
//        WebElement EnterAuthCodeLabel = driver.findElement(By.xpath("*//android.view.View[@content-desc=\"Get a verification code from the Google Authenticator app\"]/android.widget.TextView"));
        System.out.println("enter 2FA Page");

        if (EnterAuthCodeLabel != null)
        {
            System.out.println("enter 2FA Page first IF Enter Auth code label is not null");
//            if (EnterAuthCodeLabel.getText().contains("Check your")) {
//                System.out.println("enter 2FA Page Secound IF");
//                //Try Another
//                FindEle(wait, FirstTest1.SearchBy.ByXPath, "//android.view.View[@content-desc=\"Get a verification code from the Google Authenticator app\"]/android.widget.TextView").click();
//
//            }
//            EnterAuthCodeLabel = GetElement(wait, 0, "Get a verification code from the Google Authenticator app", "android.widget.TextView", "");
            EnterAuthCodeLabel.click();
        }
        sleep(5000);

//        WebElement AuthCodeTextBox = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText");
        WebElement AuthCodeTextBox = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View/android.widget.TextView[2]"));
        System.out.println("AuthCode Text Box found");
//        System.out.println(TOTPGenerator.getTwoFactorCode(Parts[2]));
        AuthCodeTextBox.click();


//        AuthCodeTextBox.sendKeys(TOTPGenerator.getTwoFactorCode(Parts[2]));
        driver.getKeyboard().pressKey(TOTPGenerator.getTwoFactorCode(Parts[2]));
        System.out.println("OTP IS:" + TOTPGenerator.getTwoFactorCode(Parts[2]));
        WebElement AuthCodeNext = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button[2]"));
        AuthCodeNext.click();
        sleep(5000);

        try {
            AuthCodeTextBox = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText");
            AuthCodeTextBox.sendKeys(TOTPGenerator.getTwoFactorCode(Parts[2]));
            System.out.println("OTP IS:" + TOTPGenerator.getTwoFactorCode(Parts[2]));
            FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button").click();
            sleep(5000);
        } catch (Exception E) {
        }

//        Add phone Number
//        try {
//            WebElement Add_Phn_No = GetElement(wait, 0, "Add phone number?", "android.widget.TextView", "");
//            if (Add_Phn_No != null) {
//                if (Add_Phn_No.getText().contains("Add phone number?"))
//                {
//                    //Skip Button
////                    ScrollForLoctionHistory();
//                    FindEle(wait, FirstTest1.SearchBy.ByXPath, "").click();
//                    sleep(4000);
//                }
//            }
//        } catch (Exception e) {
//        }
//
//        //Never lose your contacts
//        try {
//            WebElement Never_lose_Contact = GetElement(wait, 0, "Never lose your contacts", "android.widget.TextView", "");
//            if (Never_lose_Contact != null) {
//                if (Never_lose_Contact.getText().contains("Never lose your contacts")) {
//                    //Don't turn On Button
//                    FindEle(wait, FirstTest1.SearchBy.ByXPath, "").click();
//                    sleep(4000);
//                }
//            }
//        } catch (Exception e) {
//        }

        //I Agree
        sleep(5000);
        WebElement IAgree = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button"));
//        WebElement IAgree = FindEle(wait, FirstTest1.SearchBy.ByButton, "I agree");
//
        if (IAgree != null) {
            IAgree.click();
        }else {
            driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/" +
                    "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout" +
                    "/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button"));
        }
        sleep(5000);

        Scroll(); // Scroll for getting the Accept element
        Thread.sleep(5000);
        // Accept Backup and Storage
        WebElement IAccept_drive = GetElement(wait,0,"Accept","android.widget.Button","");
//        WebElement IAccept_drive = FindEle(wait, FirstTest1.SearchBy.ByXPath, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button");
        try{
            if (IAccept_drive!= null){

                sleep(5000);
                IAccept_drive.click();
            }else {Scroll();
                Thread.sleep(5000);
            IAccept_drive.click();}
        }catch (NoSuchElementException e){e.printStackTrace();}
    }

    void SkipLogin() throws Exception
    {
        WebElement SkipButton = GetElement(wait,0,"skip","android.widget.Button","");
        SkipButton.click();
    }

//Search keywords in google maps

    WebElement ActivateSearchBox() throws InterruptedException
    {
        Thread.sleep(1000);
        System.out.println("I am in Activation Search box");
        //WebElement SearchLocationTextView = driver.findElement(By.xpath("//android.widget.EditText[@content-desc="Search here")]/android.widget.TextView"));
        WebElement SearchLocationTextView = GetElement(wait,0,"Search here","android.widget.TextView","");
        System.out.println("element located ="+SearchLocationTextView);
        SearchLocationTextView.click();

        //WebElement SearchLocationEditText = driver.findElement(By.xpath("//android.widget.EditText[@content-desc="Search here"]/android.widget.TextView"));
        WebElement SearchLocationEditText = GetElement(wait,0,"Search here","android.widget.EditText","");
        SearchLocationEditText.click();
        sleep(200);
        return SearchLocationEditText;
    }

    void SearchKeyword(String Keyword) throws InterruptedException {
        System.out.println("I am search method");
        WebElement SearchLocationEditText = ActivateSearchBox();

        SearchLocationEditText.sendKeys(Keyword);
        System.out.println(Keyword);
        sleep(1000);
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));

    }
    // Search ends

    // Location Service Starts

    void locationActivity() throws InterruptedException {

        String AppPackage= driver.getCurrentPackage();
        System.out.println(AppPackage);
        ScreenOrientation orientation=  driver.getOrientation();
        WebElement More_Options = FindEle(wait, FirstTest1.SearchBy.ByXPath,"//*[(@content-desc='Menu')]");
        More_Options.click();
        sleep(2000);
//        WebElement More_Options = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ImageView");
//        More_Options.click();
//        WebElement Expand_Acc_Details_Option = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageButton");
//        Expand_Acc_Details_Option.click();
        ScrollForLoctionHistory();
        WebElement Setting_Options = FindEle(wait, FirstTest1.SearchBy.ByXPath,"\t/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[3]");
        Setting_Options.click();
        sleep(4000);
        WebElement Google_Location_Setting = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.TextView");
        Google_Location_Setting.click();
        sleep(4000);
//        WebElement Timeline_Options = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.RelativeLayout[2]/android.widget.LinearLayout/android.widget.TableLayout/android.widget.TableRow/android.widget.TextView");
//        Timeline_Options.click();

        WebElement Advance_Options = GetElement(wait,0,"Advanced","android.widget.TextView","");
        Advance_Options.click();
        sleep(4000);
        //Simple Scrool
        ScrollForLoctionHistory();
        WebElement Google_Loc_History_Options = GetElement(wait,0,"Google Location History","android.widget.TextView","");
//        WebElement Google_Loc_History_Options = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[7]/android.widget.LinearLayout[2]/android.widget.TextView");
        sleep(2000);
        Google_Loc_History_Options.click();
        WebElement Loc_History_TurnOn_Options = GetElement(wait,0,"Turn","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.widget.Button","");
        String LocationStatus = Loc_History_TurnOn_Options.getText();
        if(LocationStatus.equals("Turn on"))
        {
            Loc_History_TurnOn_Options.click();
            WebElement Turn_On_Button = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.app.Dialog/android.view.View/android.view.View[3]/android.view.View[2]/android.widget.Button");
            do {
                ScrollForLoctionHistory();
            }while(!Turn_On_Button.isEnabled());
            Turn_On_Button.click();
            System.out.println("*****************Web Activity and Location History Turned On*********");
            WebElement Gotit_Loc_On_Options = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.app.Dialog/android.view.View/android.view.View[3]/android.view.View/android.widget.Button");
            Gotit_Loc_On_Options.click();
            sleep(5000);
            //driver.pressKeyCode(187);
            driver.activateApp(AppPackage);
            driver.rotate(orientation);
            //driver.pressKeyCode(4);

        }else {
            sleep(5000);
//            driver.activateApp(AppPackage);(Activate this)
            driver.rotate(orientation);
            System.out.println("*****************Web Activity and Location History is Already On*********");
//            driver.pressKeyCode(4);
        }

        sleep(2000);
        WebElement Back_button_Loc_On = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageButton");
        Back_button_Loc_On.click();
    }

    // Scroll for Location History
    void ScrollForLoctionHistory() throws InterruptedException {
        //WebElement RecyclerView = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.support.v7.widget.RecyclerView");

        TouchAction action = new TouchAction(driver);

        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;


        PointOption to= PointOption.point(100,100) ;
        PointOption from= PointOption.point(100,height-100) ;
        action.longPress(from)
                .moveTo(to).release().perform();

    }

    void SimulateLocations(String[] Parts,List<LatLng> latLngs) throws Exception
    {
        for (int i=0;i<latLngs.size();i++)
        {
            driver.setLocation(new Location(latLngs.get(i).getLat(), latLngs.get(i).getLng(),0));
            sleep(Integer.parseInt(Parts[7]));
        }

//        driver.setLocation(new Location(latLngs.get(latLngs.size()-1).getLat(), latLngs.get(latLngs.size()-1).getLng(),0));
//        Thread.sleep(Integer.parseInt(Parts[7]));
//        driver.setLocation(new Location(latLngs.get((latLngs.size()/6)*1).getLat(), latLngs.get((latLngs.size()/6)*1).getLng(),0));
//        Thread.sleep(Integer.parseInt(Parts[7]));
//
//        driver.setLocation(new Location(latLngs.get((latLngs.size()/6)*2).getLat(), latLngs.get((latLngs.size()/6)*2).getLng(),0));
//        Thread.sleep(Integer.parseInt(Parts[7]));
//
//        driver.setLocation(new Location(latLngs.get((latLngs.size()/6)*3).getLat(), latLngs.get((latLngs.size()/6)*3).getLng(),0));
//        Thread.sleep(Integer.parseInt(Parts[7]));
//
//        driver.setLocation(new Location(latLngs.get((latLngs.size()/6)*4).getLat(), latLngs.get((latLngs.size()/6)*4).getLng(),0));
//        Thread.sleep(Integer.parseInt(Parts[7]));
//
//        driver.setLocation(new Location(latLngs.get((latLngs.size()/6)*5).getLat(), latLngs.get((latLngs.size()/6)*5).getLng(),0));
//        Thread.sleep(Integer.parseInt(Parts[7]));


        sleep(1000);
    }

//    public boolean moveToShopName(WebDriver driver, String shopName, int MaxScrollCount) throws Exception {
//        try {
//            WebElement ActualShopElement = driver.findElement(By.xpath("//android.widget.TextView[@content-desc='"+shopName+"']"));
//            WebElement ShopElementFromView = GetElement(wait,0,shopName,"android.widget.TextView","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout[5]/android.widget.ScrollView/android.support.v7.widget.RecyclerView");
//            System.out.println(ActualShopElement);
//            return ActualShopElement.isDisplayed();
//            boolean elementFound = false;
//            int currentScrollCount = 0;
//            sleep(1);
//            while (currentScrollCount < MaxScrollCount) {
//                // Check if the ActualShopElement is visible
//
//
//                }
//                currentScrollCount++;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//
//
//    }





    int Scrolls=0;
    boolean FindShop(String ShopName,String Address,int MaxScrollCount) throws InterruptedException {

        System.out.println("*****************Searching for:"+ShopName+"*********");
        try {
//            WebElement SearchLocationTextView = GetElement(wait,0,ShopName,"android.widget.TextView","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout[5]/android.widget.ScrollView/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout");
//            WebElement SearchLocationTextView = GetElement(wait,0,ShopName,"android.widget.TextView","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout[6]/android.widget.ScrollView/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout");

//            WebElement SearchLocationTextView = driver.findElement(By.xpath("//android.widget.TextView[@content-desc="+ShopName+"]"));



            WebElement SearchLocationTextView = GetElement(wait,0,ShopName,"android.widget.TextView","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout[5]/android.widget.ScrollView/android.support.v7.widget.RecyclerView");
            System.out.println("Search Location Text view found"+ SearchLocationTextView);
            if(SearchLocationTextView!=null)
            {
                SearchLocationTextView.click();
                System.out.println("Search location clicked");
                // new code to match address------------------
                ScrollForShopAddress();
                List<AndroidElement> ListElement = driver.findElements(By.className("android.widget.TextView"));
                for(WebElement we : ListElement)
                {

                    System.out.println(we.getText().replace(","," ").replaceAll("\\s", "").toLowerCase());
                    System.out.println(Address.replace(","," ").replaceAll("\\s", "").toLowerCase());
                    if(we.getText().replace(","," ").replaceAll("\\s", "").toLowerCase().contains(Address.substring(0,20).replaceAll("\\s", "").toLowerCase()))
                    {
                        System.out.println(we.getText().replace(","," ").replaceAll("\\s","").toLowerCase()+"web element converted");
                        System.out.println("*****************Address Matched*********");
                        return true;
                    }
                }

                System.out.println("*****************Address not Match*********");
                //Click Back Button
                FindEle(wait, FirstTest1.SearchBy.ByXPath,"//android.widget.Button[@content-desc=\"Back to Search\"]/android.widget.ImageView").click();
                sleep(2000);
                Scroll();
                Scrolls++;
                return FindShop(ShopName,Address,MaxScrollCount);
                // new code to match address------------------
            }
        }catch (Exception e){
            if(Scrolls<MaxScrollCount)
            {
                Scroll();
                Scrolls++;
                return FindShop(ShopName,Address,MaxScrollCount);
            }
            else{
//                driver.navigate().back();
//                driver.navigate().back();
                System.out.println("Location Not found: "+ShopName);
                //go to home
                //FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageView").click();

            }
        }

        return false;
    }

    void Scroll() throws InterruptedException {
        //WebElement RecyclerView = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.support.v7.widget.RecyclerView");

        TouchAction action = new TouchAction(driver);

        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

//        action. press(PointOption.point(RecyclerView.getLocation().getX()+150,RecyclerView.getLocation().getY()-150)).release().perform();
//        WebElement ShowList = GetElement(wait,0,"show list","android.widget.TextView");
//        ShowList.click();

        // List<AndroidElement> ListElementa =  RecyclerView.findElements(By.className("android.widget.TextView"));

//        PointOption to= PointOption.point(ListElementa.get(0).getLocation()) ;
//        PointOption from= PointOption.point(ListElementa.get(0).getLocation().getX(),height-100) ;
        sleep(5000);

        PointOption to= PointOption.point(100,100) ;
        PointOption from= PointOption.point(100,height-300) ;
        action.longPress(from)
                .moveTo(to).release().perform();


    }
    //Tap Action on Screen by Coordinates
    public void tap(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    void ScrollForShopAddress() throws InterruptedException {
        //WebElement RecyclerView = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.support.v7.widget.RecyclerView");

        TouchAction action = new TouchAction(driver);

        int height = driver.manage().window().getSize().height;
        //int width = driver.manage().window().getSize().width;


        PointOption to= PointOption.point(100,height-100) ;
        PointOption from= PointOption.point(100,height-100) ;
        action.longPress(from)
                .moveTo(to).release().perform();

    }

    // Starts direction

    void StartDirectionsFromList() throws InterruptedException {

        WebElement DirectionsButton = GetElement(wait,0,"Directions","android.view.View","");
        System.out.println(DirectionsButton.getText());
        if(DirectionsButton!=null)
        {

            DirectionsButton.click();
            ChooseStartLocation();
        }
    }

    void ChooseStartLocation() throws InterruptedException {
        //Click on Choose your starting point
//        FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.EditText[1]/android.widget.LinearLayout/android.widget.TextView").click();
        sleep(5000);
        WebElement ChooseStartLocation = GetElement(wait,0,"Choose start location","android.widget.TextView","");
        ChooseStartLocation.click();
        //Select your current location
        sleep(5000);
        WebElement YourLocation = GetElement(wait,0,"Your location","android.widget.TextView","");
        YourLocation.click();
        sleep(2000);
        //Click on only this time
        WebElement AllowOnlyThisTime = GetElement(wait,0,"ONLY THIS TIME","android.widget.Button","");
        AllowOnlyThisTime.click();
        //Click On Start
        sleep(5000);
//        FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();
        WebElement Start = GetElement(wait,0,"Start","android.view.View","");
        sleep(2000);
        Start.click();
        //Got It
        try {
            sleep(5000);
//           FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button").click();
            WebElement GotIt = GetElement(wait,0,"GOT IT","android.widget.Button","");
            sleep(2000);
            GotIt.click();
            sleep(2000);
            WebElement DismissButton = GetElement(wait,0,"Dismiss","android.view.View","");
            DismissButton.click();
            Thread.sleep(5000);
//            WebElement ContinueButton = FindEle(wait, SearchBy.ByXPath,"//android.widget.RelativeLayout[@content-desc=\"Activate to open step list\"]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageView");
//            if (ContinueButton.isDisplayed()){
//                sleep(5000);
//                ContinueButton.click();
//            }else{
//                sleep(5000);
//                System.out.println("Continue button is not there Starting the Navigation");
//            }


        }catch(Exception e)
        {
            e.printStackTrace();

        }
    }

    //Restrart directions

    void RestartDirection()
    {
        try {
            Thread.sleep(5000);
            //Close navigation
            FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.view.ViewGroup/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.ImageView[1]").click();

            //Click On Start
            FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();

        }catch (Exception e)
        {}
    }

    void FinishDirections() throws InterruptedException {
        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //After Reaching Click on Done
//       WebElement DoneButton= FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView");
//        WebElement DoneButton= FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView");
        WebElement DoneButton = GetElement(wait,0,"DONE","android.widget.TextView","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout");

        if(DoneButton!=null)
        {
            try {Thread.sleep(5000);
                DoneButton.click();
                FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView").click();

            }catch (Exception e)
            {}
            Thread.sleep(10000);
//            DoneButton.click();
        }
//        WebElement element= GetElement(wait,0,"Done","android.widget.TextView");
//        element
    }

    void SaveToTimeLine(String ShopName) throws InterruptedException {
        WebElement SearchLocationEditText = ActivateSearchBox();
//        String shopnameSearchedForReview = SearchLocationEditText.getText();
//        String shopnameForReview = shopnameSearchedForReview+ShopName;
//        System.out.println(shopnameForReview);

        //Click on first element from search history

        WebElement ShopFromSearch = GetElement(wait,0,ShopName,"android.widget.TextView","");
        Thread.sleep(1000);
        ShopFromSearch.click();
        tap(450,450);
        sleep(5000);
//        GetElement(wait,1,ShopName,"android.widget.TextView","").click();
         // tap to first search result. need to append this


        try {
//            WebElement hereNow= GetElement(wait,0,"Are you here now?","android.widget.TextView","");
//            hereNow.click();
//
//            WebElement YesButton= GetElement(wait,0,"yes","android.widget.Button","");
//            YesButton.click();
        }catch (Exception e)
        {}
        sleep(1000);
    }

    void Review(String[] Parts) throws InterruptedException {
        if(Parts.length>9 && !Parts[9].isEmpty())
        {
            String ShopName=Parts[5];

            //old xpath review
//            WebElement ReviewButton = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView");
//            ReviewButton.click();
            WebElement ShopNameElement = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout[5]");
            if (ShopNameElement.isDisplayed()){
                ShopNameElement.click();
            }else {tap(600,1200);}
            Thread.sleep(5000);

//            Scroll();
//            WebElement ReviewButton = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //wait for the Dom to load
            WebElement ReviewButton = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.LinearLayout");
            ReviewButton.click();
            try{

            }
            catch(Exception ex){
                System.out.println("Review Exception"+ex);
            }

//            WebElement Five_Star = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout[2]/android.support.v4.view.ViewPager/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[2]/android.view.ViewGroup/android.widget.ImageView[5]");
            WebElement Five_Star = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout[1]/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.SeekBar/android.widget.ImageView[5]");
            sleep(2000);
            Five_Star.click();

            Thread.sleep(5000);

//            WebElement Add_comment = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.EditText");

            try{
                WebElement ClosePubliclyPostButton = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Close\"]"));
                if (ClosePubliclyPostButton.isDisplayed()){
                    ClosePubliclyPostButton.click();
                    Thread.sleep(1000);
                }
            }catch (Exception e ){
                System.out.println( " web element not found");

            }
                WebElement Add_commentWithId = driver.findElement(By.id("com.google.android.apps.maps:id/contentEditText"));
                Add_commentWithId.click();
                Thread.sleep(5000);
                driver.getKeyboard().pressKey(Parts[9]);
//                WebElement Add_comment = GetElement(wait,5,"Share details of your own experience at this place","android.widget.EditText","");
//                sleep(2000);
//                if (Add_comment!=null){
//                    Add_comment.click();
//                    System.out.println("Review is " +Parts[9]);
//                    Add_comment.sendKeys(Parts[9]);
//                }else {
//                    Add_commentWithId.click();
//                    driver.getKeyboard().pressKey(Parts[9]);
//                }

//            WebElement Post_Button = FindEle(wait, FirstTest1.SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.Button");
                WebElement Post_Button = GetElement(wait,0,"Post","android.widget.Button","");
                Post_Button.click();
                sleep(1000);

            }


    }
    Void ResetApp() throws InterruptedException, IOException {
        Runtime.getRuntime().exec("adb shell am start com.android.settings");
        Thread.sleep(5000);
//        WebElement ValidateApp = GetElement(wait,0,"Settings","android.widget.TextView","");
        WebElement SearchSettings = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Search\"]"));
//        WebElement SearchSettings = GetElement(wait,1,"Search settings","android.widget.TextView","");
        SearchSettings.click();
        SearchSettings.sendKeys("Accounts & sync");
        Thread.sleep(2000);
        WebElement AccountsAndSync = GetElement(wait,0,"Accounts & sync","android.widget.TextView","");
        Thread.sleep(1000);
        AccountsAndSync.click();
        Scroll();
        Thread.sleep(1000);
        Scroll();
        WebElement GoogleButton = GetElement(wait,0,"Google","android.widget.TextView","");
        GoogleButton.click();
        WebElement MoreButton = GetElement(wait,1,"More","android.widget.TextView","");
        Thread.sleep(2000);
        MoreButton.click();
        WebElement RemoveAccount = GetElement(wait,0,"Remove account","android.widget.TextView","");
            Thread.sleep(10000);
            WebElement Remove_Account_confirmation = driver.findElement(By.id("android:id/button1"));
            Remove_Account_confirmation.click();
            Thread.sleep(20000);


        return null;
    }






    //To Find and search element
    public WebElement FindEle(Wait wait , FirstTest1.SearchBy searchBy, String XpathOrId)
    {
        System.out.println("I am in FindEle method");
        wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(90))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);
        return (WebElement) wait.until(new Function<AndroidDriver, WebElement>() {
            @Override
            public WebElement apply(AndroidDriver driver) {
                if(searchBy== FirstTest1.SearchBy.ById)
                {
//                    System.out.println("I am in the Apply findele method");
                    WebElement ele = driver.findElement(By.id(XpathOrId));
//                    System.out.println( "Found element is "+ele);
                    return driver.findElement(By.id(XpathOrId));
                }
                else if (searchBy== SearchBy.ByXPath){
                    return driver.findElement(By.xpath(XpathOrId));
                }
                else if (searchBy== SearchBy.ByLinkText){
                    List<WebElement> ListElement=  driver.findElements(By.className("android.widget.TextView"));

                    WebElement ele=  ListElement.stream().filter((item -> ((WebElement)item).getText().contains(XpathOrId))).collect(Collectors.toList()).get(0);
                    //  driver.findElements(By.className("android.widget.TextView")).stream().filter((item -> ((WebElement)item).getText().contains("Google Authenticator"))).collect(Collectors.toList());
                    return ele;
                    // return ListElement.get(0);
                    //(WebElement) driver.findElements(By.className("android.widget.TextView")).stream().filter((item -> ((WebElement)item).getText().equals("google"))).collect(Collectors.toList());
                }
                else if (searchBy== SearchBy.ByButton){
                    List<WebElement> ListElement=  driver.findElements(By.className("android.widget.Button"));

                    for(WebElement we : ListElement)
                    {
                        for(String str :XpathOrId.split("@"))
                        {
                            if(we.getText().contains(str))
                            {
                                System.out.println(we);
                                return  we;
                            }
                        }
                    }
                    return null;
                }

                return null;
            }
        });
    }
    enum SearchBy {ById, ByXPath, ByLinkText, ByButton}


    public WebElement GetElement(Wait wait,int index,String Label,String Xpath,String Parent)
    {
        System.out.println("I am in GetElement method");
        wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);

        return (WebElement) wait.until(new Function<WebDriver, WebElement>() {

            @Override
            public WebElement apply(WebDriver driver) {
                System.out.println("I am in method Apply get element method");
                String[] XpathParts=Xpath.split("/");
                WebElement ParentElement=null;
                List<WebElement> ListElement ;
                if(!Parent.isEmpty())
                {
                    System.out.println("----------Parent Found-------------");
                    ParentElement  =driver.findElement(By.xpath(Parent));
                    System.out.println("parent element is " + ParentElement);
                    ListElement =  ParentElement.findElements(By.className(XpathParts[XpathParts.length-1]));
                }
                else {
                    ListElement =  driver.findElements(By.className(XpathParts[XpathParts.length-1]));
//                    System.out.println("List of element = " + ListElement);
                }
                int indx=index;
                for(WebElement we : ListElement)
                {
                    System.out.println("Webelement:"+we.getText());
                    for(String str :Label.split("@"))
                    {
                        if(we.getText().toLowerCase().contains(str.toLowerCase()))
                        {
                            if(indx==0)
                            {System.out.println(we);
                                return  we;
                            }
                            indx-=indx;
                        }
                    }
                }
                return null;
            }
        });
    }






}
