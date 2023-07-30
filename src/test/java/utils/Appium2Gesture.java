package utils;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Appium2Gesture {

    AppiumDriver driver;


    @BeforeTest
    public void initializeDriver(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:device", "2B291FDH3006PE");
        capabilities.setCapability("appium:appPackage", "com.wdiodemoapp");
        capabilities.setCapability("appium:appActivity", "com.wdiodemoapp.MainActivity");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:noReset", "true");
        capabilities.setCapability("appium:unicodeKeyboard", true);
        //AppiumDriver driver;

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void sampleTest(){
        try {
            driver.findElement(By.xpath("//android.widget.ScrollView[@content-desc=\"Home-screen\"]/android.view.ViewGroup/android.widget.TextView[1]"));
            Thread.sleep(500);
            driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Swipe\"]")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            RemoteWebElement carousel = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("Carousel")));


            driver.executeScript("gesture: swipe", ImmutableMap.of("elementId", carousel.getId(), "percentage", 50, "direction", "left"));

            //RemoteWebElement carousel = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("Carousel")));

            driver.executeScript("gesture: swipe", ImmutableMap.of("elementId", carousel.getId(), "percentage", 50, "direction", "right"));


            RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("Swipe-screen")));

            driver.executeScript("gesture: swipe", ImmutableMap.of("elementId", scrollView.getId(),
                    "percentage", 50,
                    "direction", "up"));


            driver.executeScript("gesture: swipe", ImmutableMap.of("elementId", scrollView.getId(),
                    "percentage", 50,
                    "direction", "down"));

           // RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("Swipe-screen")));



        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
