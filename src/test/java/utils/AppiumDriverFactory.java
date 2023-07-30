package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;


public class AppiumDriverFactory {
	
	private static AppiumDriverFactory instanceOfAppiumDriverFactory = null;
	private AppiumDriver driver;
	
	
	private static String appPackage = ConfigFileReader.getConfigPropertyVal("appPackage");
	private static String appActivity = ConfigFileReader.getConfigPropertyVal("appActivity");
	private static String deviceName = ConfigFileReader.getConfigPropertyVal("deviceName");
	
	
	// Declaring constructor as private to restrict object creation outside of class
	private AppiumDriverFactory() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("appium:device", deviceName);
		capabilities.setCapability("appium:appPackage", appPackage);
		capabilities.setCapability("appium:appActivity", appActivity);
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

	public static AppiumDriverFactory getInstanceOfAppiumDriverFactory() {
		if (instanceOfAppiumDriverFactory == null)
			instanceOfAppiumDriverFactory = new AppiumDriverFactory();

		return instanceOfAppiumDriverFactory;
	}

	// To get driver
	public AppiumDriver getDriver() {
		
		return driver;
	}
}
