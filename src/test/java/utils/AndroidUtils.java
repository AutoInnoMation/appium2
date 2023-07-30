package utils;

/**
 * Owned By : Arnab Majumder
 * This class contains all utility files for android
 */

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AndroidUtils {

private AppiumDriver driver;
	

	/**
	 * Constructor to initialize the {@link } object
	 * 
	 * @param driver The {@link AppiumDriver} object
	 */
	public AndroidUtils(AppiumDriver driver) {
		this.driver = driver;
	}

	/**
	 * Function to pause the execution for the specified time period
	 * 
	 * @param milliSeconds The wait time in milliseconds
	 */
	public void waitFor(long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to wait until the specified element is located
	 * 
	 * @param by               The {@link AppiumDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementLocated(By by, long timeOutInSeconds) {
		(new WebDriverWait(driver,  Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * Function to wait until the specified element is visible
	 * 
	 * @param by               The {@link AppiumDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementVisible(By by, long timeOutInSeconds) {
		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	/**
	 * Function to wait until the specified element is not visible
	 * 
	 * @param by               The {@link AppiumDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementInvisible(By by, long timeOutInSeconds) {
		(new WebDriverWait(driver,  Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * Function to wait until the specified element is enabled
	 * 
	 * @param by               The {@link AppiumDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementEnabled(By by, long timeOutInSeconds) {
		(new WebDriverWait(driver,  Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * Function to wait until the specified element is disabled
	 * 
	 * @param by               The {@link AppiumDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementDisabled(By by, long timeOutInSeconds) {
		Boolean until = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
				.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by)));
	}

	/**
	 * Function to select the specified value from a listbox
	 * 
	 * @param by   The {@link AppiumDriver} locator used to identify the listbox
	 * @param item The value to be selected within the listbox
	 */
	public void selectListItem(By by, String item) {
		Select dropDownList = new Select(driver.findElement(by));
		dropDownList.selectByVisibleText(item);
	}

	public void screenshot(String path_screenshot) throws IOException {
		File srcFile=driver.getScreenshotAs(OutputType.FILE);
		String filename=Math.random()+"";
		File targetFile=new File(path_screenshot + filename +".jpg");
		FileUtils.copyFile(srcFile,targetFile);
	}

	/**
	 * Function to verify whether the specified object exists within the current
	 * page
	 * 
	 * @param by The {@link AppiumDriver} locator used to identify the element
	 * @return Boolean value indicating whether the specified object exists
	 */
	public Boolean objectExists(By by) {
		try {
			if (driver.findElements(by).size() == 0) {
				return false;
			} else {
				return true;
			}
		}catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	public boolean enterValueInTextBox(String text, By by) {
		boolean flag = false;
		try {
			if (driver.findElement(by).isDisplayed()) {
				driver.findElement(by).click();
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(text);
				//driver.hideKeyboard();
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Function to check if an element displayed or not then click on that element
	 * 
	 * param by  The {@link WebDriver} locator used to identify the
	 *                         element
	 * throwing Exception if element is not visible
	 */
	public void clickOnElement(By by) {
		if (driver.findElement(by).isDisplayed()) {
			driver.findElement(by).click();
		} else {
			//throw new ElementNotVisibleException("Element Not Found");
		}

	}
	/**
	 * Function to get the text of an element. Ex : get title or header
	 * 
	 * param by  The {@link WebDriver} locator used to identify the
	 *                         element
	 * throwing Exception if element is not visible
	 */
	public String getElementText(By by) {
		if (driver.findElement(by).isDisplayed()) {
			return driver.findElement(by).getText();
		} else {
			//throw new ElementNotVisibleException("Element Not Found");
		}
		return null;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void selectDropdown(By by, String xpath, String selectedValue) {
		if(driver.findElement(by).isDisplayed() && driver.findElement(by).isEnabled()) {
			//Click on Dropdown
			driver.findElement(by).click();
			//Click on value
			if(driver.findElement(By.xpath(xpath)).getAttribute("scrollable").equals(true)) {
				driver.findElement(MobileBy.AndroidUIAutomator(
				        "new UiScrollable(new UiSelector().scrollable(true))" +
				         ".scrollIntoView(new UiSelector().text("+selectedValue+"))"));
				driver.findElement(By.xpath(xpath)).click();
			}else {
				driver.findElement(By.xpath(xpath)).click();
			}
			
		}else {
			//throw new ElementNotVisibleException("Element Not Found");
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean scrollToFind(By by,String text) {
		
		try {
			boolean isScrollable = Boolean.valueOf(driver.findElement(by).getAttribute("scrollable"));
			if(isScrollable) {
				
				driver.findElement(MobileBy.AndroidUIAutomator(
				        "new UiScrollable(new UiSelector().scrollable(true))" +
				         ".scrollIntoView(new UiSelector().text(\"+text+\"))"));
				
				
				
				return true;
			}else {
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}
			
	}
}
