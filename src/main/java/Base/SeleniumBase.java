
package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import Utils.Reporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumBase extends Reporter {

	public RemoteWebDriver driver;
	public static Properties prop;

	public void startApplication(String browser, String url) {

		try {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();				
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("Edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			driver.get(url);
			reportSteps("The Browser " + browser + " launched successfully", "PASS");
		} catch (WebDriverException e) {
			reportSteps("The Browser " + browser + " could not be launched successfully", "FAIL");
		}
	}

	public WebElement locateElement(String locator, String locatorValue) {
		try {
			switch (locator.toLowerCase()) {
			case "id":
				return driver.findElement(By.id(locatorValue));
			case "class":
				return driver.findElement(By.className(locatorValue));
			case "name":
				return driver.findElement(By.name(locatorValue));
			case "link":
				return driver.findElement(By.linkText(locatorValue));
			case "partiallink":
				return driver.findElement(By.partialLinkText(locatorValue));
			case "tagname":
				return driver.findElement(By.tagName(locatorValue));
			case "css":
				return driver.findElement(By.cssSelector(locatorValue));
			case "xpath":
				return driver.findElement(By.xpath(locatorValue));
			}
		} catch (NoSuchElementException e) {
			reportSteps("The Element with locator" + locatorValue + "not found", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while finding" + locator + "with value" + locatorValue, "FAIL");
		}
		return null;
	}

	public List<WebElement> locateElements(String locator, String locatorValue) {
		try {
			switch (locator.toLowerCase()) {
			case "id":
				return driver.findElements(By.id(locatorValue));
			case "class":
				return driver.findElements(By.className(locatorValue));
			case "name":
				return driver.findElements(By.name(locatorValue));
			case "link":
				return driver.findElements(By.linkText(locatorValue));
			case "partiallink":
				return driver.findElements(By.partialLinkText(locatorValue));
			case "tagname":
				return driver.findElements(By.tagName(locatorValue));
			case "css":
				return driver.findElements(By.cssSelector(locatorValue));
			case "xpath":
				return driver.findElements(By.xpath(locatorValue));
			}
		} catch (NoSuchElementException e) {
			reportSteps("The Elements with locator" + locatorValue + "not found", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while finding" + locator + "with value" + locatorValue, "FAIL");
		}
		return null;
	}

	public void loadData(String fileName) throws IOException {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\Data\\"+fileName+".properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			reportSteps("The file " + fileName + " is not found", "FAIL");
		} catch (IOException e) {
			reportSteps(e.getMessage(), "FAIL");
		}
	}

	public void unloadData() {
		prop = null;
	}

	public long takeSnap() {

		long number = (long) Math.floor(Math.random() * 900000000L) + 100000000L;
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("./Report/" + number + ".png"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

	public void click(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			ele.click();
			reportSteps("The element " + text + " is clicked", "PASS");
		} catch (InvalidElementStateException e) {
			reportSteps("The element " + text + " could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while clicking the field", "FAIL");
		}
	}

	public void click(WebElement ele, String elementName) {
		try {

			ele.click();
			reportSteps("The element " + elementName + " is clicked", "PASS");
		} catch (InvalidElementStateException e) {
			reportSteps("The element " + elementName + " could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while clicking the field", "FAIL");
		}
	}

	public String getTitle() {
		String title = "";
		try {
			title = driver.getTitle();
			reportSteps("The title of the page is " + title, "PASS");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while fetching the title", "FAIL");
		}
		return title;
	}

	public boolean verifyTitle(String title) {
		boolean bReturn = false;
		if (getTitle().equals(title)) {
			reportSteps("The title of the page " + driver.getTitle() + " matches with the value" + title, "PASS");
			bReturn = true;
		} else {
			reportSteps("The title of the page " + driver.getTitle() + " doesn't matche with the value" + title,
					"FAIL");
		}
		return bReturn;
	}

	public void clearAndType(WebElement ele, String text) {
		try {
			ele.clear();
			ele.sendKeys(text);
			reportSteps("The value " + text + " is entered in the text box", "PASS");
		} catch (StaleElementReferenceException e) {
			reportSteps("The element " + ele + " is not interactable", "FAIL");
		}
	}

	public boolean verifyIsDisplayed(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			if (ele.isDisplayed()) {
				reportSteps("The element " + text + " is visible", "PASS");
				return true;
			} else {
				reportSteps("The element " + text + " is not visible", "FAIL");
			}
		} catch (WebDriverException e) {
			reportSteps("WebdriverException: " + e.getMessage(), "FAIL");
		}
		return false;
	}

	public boolean verifyIsDisplayed(WebElement ele, String elementName) {
		try {

			if (ele.isDisplayed()) {
				reportSteps("The element " + elementName + " is visible", "PASS");
				return true;
			} else {
				reportSteps("The element " + elementName + " is not visible", "FAIL");
			}
		} catch (WebDriverException e) {
			reportSteps("WebdriverException: " + e.getMessage(), "FAIL");
		}
		return false;
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
	}

	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");
	}

	public void scrollToElement(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}
	
	public void scrollToElementAndClick(WebElement ele) throws InterruptedException {
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
			webDriverWaitTillElementVisible(ele);
			ele.click();
		}catch (InvalidElementStateException e) {
			reportSteps("The element " + ele.getText() + " could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while fetching the title", "FAIL");
		}
		
	}

	public void moveToElement(WebElement ele) {
		try {
			Actions hover = new Actions(driver);
			hover.moveToElement(ele).build().perform();
		} catch (NoSuchElementException e) {
			reportSteps("The element" + ele.getText() + " is not found", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured", "FAIL");
		}
	}

	public void moveToElementAndClick(WebElement ele) {
		try {
			Actions a = new Actions(driver);
			a.moveToElement(ele).click().build().perform();
			reportSteps("The element " + ele.getText() + " is clicked", "PASS");
		} catch (InvalidElementStateException e) {
			reportSteps("The element " + ele.getText() + " could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while fetching the title", "FAIL");
		}
	}

	public void moveToElementAndClick(WebElement ele, String elementName) {

		try {
			Actions a = new Actions(driver);
			a.moveToElement(ele).click().build().perform();
			reportSteps("The element " + elementName + " is clicked", "PASS");
		} catch (InvalidElementStateException e) {
			reportSteps("The element " + elementName + " could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while fetching the title", "FAIL");
		}
	}

	public String getElementText(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			reportSteps("The element text is" + text, "PASS");
		} catch (WebDriverException e) {
			reportSteps("The element " + text + "could not be found", "FAIL");
		}
		return text;
	}

	public String getElementText(WebElement ele, String elementName) {

		String text = "";
		try {
			text = ele.getText();
			reportSteps("The element text is" + elementName, "PASS");
		} catch (WebDriverException e) {
			reportSteps("The element " + elementName + "could not be found", "FAIL");
		}
		return text;

	}

	public boolean isElementPresentClick(WebElement ele, String elementName) {
		try {
			if (ele.isDisplayed()) {
				ele.click();
				reportSteps("The element " + elementName + " is displayed and clicked", "PASS");
				return true;
			} else {
				reportSteps("The element " + elementName + " is not displayed ", "FAIL");
			}
		} catch (InvalidElementStateException e) {
			reportSteps("The element " + elementName + " could not be clicked ", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while clickin the element" + elementName, "FAIL");
		}
		return false;
	}

	public void selectDropdownByIndex(WebElement ele, int index) {
		try {
			Select dropDown = new Select(ele);
			dropDown.selectByIndex(index);
			reportSteps("The dropdown is selected with index" + index, "PASS");
		} catch (WebDriverException e) {
			reportSteps("The element with text " + index + " is not found", "FAIL");
		}
	}

	public void selectDropdownByText(WebElement ele, String text) {
		try {
			Select dropDown = new Select(ele);
			dropDown.selectByVisibleText(text);
			reportSteps("The dropdown is selected with text" + text, "PASS");
		} catch (WebDriverException e) {
			reportSteps("The element with text " + text + " is not found", "FAIL");
		}
	}

	public void selectDropdownByValue(WebElement ele, String value) {
		try {
			Select dropDown = new Select(ele);
			dropDown.selectByValue(value);
			reportSteps("The dropdown is selected with value " + value, "PASS");
		} catch (WebDriverException e) {
			reportSteps("The element with text " + value + " is not found", "FAIL");
		}
	}

	public void dynamicDropDown(WebElement ele, List<WebElement> eles, String text) {
		try {
			ele.sendKeys(text);
			for (int i = 0; i < eles.size(); i++) {
				String value = eles.get(i).getText();
				//System.out.println(value);
				if (value.equalsIgnoreCase(text)) {
					eles.get(i).click();
				}
			}
			/*
			 * for(WebElement e:eles) { String value = e.getText();
			 * if(value.equalsIgnoreCase(text)) { e.click();
			 * reportSteps("The drop down is selected with value "+ value ,"PASS"); } }
			 */
		} catch (NoSuchElementException e) {
			reportSteps("The Elements with locator" + ele + "not found", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("Unknown exception occured while finding" + ele, "FAIL");
		}

	}

	public void switchToWindow(String title) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
				if (driver.getTitle().equals(title)) {
					break;
				}
			}
			reportSteps("The window with title " + title + " is switched", "PASS");
		} catch (NoSuchWindowException e) {
			reportSteps("The window with title " + title + " not found", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("WebDriverException:" + e.getMessage(), "FAIL");
		}
	}

	public void switchToWindow(int index) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			List<String> allHandles = new ArrayList<>();
			allHandles.addAll(allWindows);
			driver.switchTo().window(allHandles.get(index));
			reportSteps("The window with index " + index + " is switched", "PASS");
		} catch (NoSuchWindowException e) {
			reportSteps("The window with title " + index + " not found", "FAIL");
		} catch (WebDriverException e) {
			reportSteps("WebDriverException:" + e.getMessage(), "FAIL");
		}

	}

	public void printList(List<WebElement> ele) {
		try {
			for (int i = 0; i < ele.size(); i++) {
				String text = ele.get(i).getText();
				reportSteps("The list is:" + text, "PASS");
			}
		} catch (NoSuchElementException e) {
			reportSteps("The list element:" + ele + " is not found", "FAIL");
		}
	}

	public void closeBrowser() {
		try {
			driver.close();
			reportSteps("The browser is closed", "PASS");
		} catch (Exception e) {
			reportSteps("The browser could not be closed", "FAIL", false);
		}
	}

	public boolean isImageBroken(WebElement ele) {
		try {
			if (ele.getAttribute("naturalWidth").equals("0")) {
				reportSteps("The image is broken", "FAIL");
				return false;
			} else {
				reportSteps("The image is visible", "PASS");

			}
		} catch (Exception e) {
			reportSteps("Exception:" + e.getMessage(), "FAIL");
		}
		return true;
	}

	public void webDriverWaitTillElementVisible(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (NoSuchElementException e) {
			reportSteps("The element is not found", "FAIL");
		}

	}

	public void webDriverWaitTillElementInvisible(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.invisibilityOf(ele));
		} catch (NoSuchElementException e) {
			reportSteps("The element is not found", "FAIL");
		}

	}

	public void AssertEquals(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			reportSteps("The " + actual + " and " + expected + " values are matching", "PASS");
		} catch (AssertionError e) {
			reportSteps("The " + actual + " and " + expected + " values are not matching", "FAIL");
		}
	}

	public void softAssert(String actual, String expected) {
		SoftAssert a = new SoftAssert();
		try {
			a.assertEquals(actual, expected);
			reportSteps("The " + actual + " and " + expected + " values are matching", "PASS");
		} catch (AssertionError e) {
			reportSteps("The " + actual + " and " + expected + " values are not matching", "FAIL");
		}
		a.assertAll();
	}

	public void acceptAlert() {
		try {
			Alert a = driver.switchTo().alert();
			a.accept();
			reportSteps("Alert accepted", "PASS");
		} catch (NoAlertPresentException e) {
			reportSteps("No alert present", "FAIL");
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
	}

	public String getAlertText() {
		try {
			Alert a = driver.switchTo().alert();
			return a.getText();
		} catch (NoAlertPresentException e) {
			reportSteps("No alert present", "FAIL");
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
		return null;
	}

	public void typeAlert(String text) {
		try {
			Alert a = driver.switchTo().alert();
			a.sendKeys(text);
			reportSteps("The Text" + text + " is send to alert", "PASS");
		} catch (NoAlertPresentException e) {
			reportSteps("No alert present", "FAIL", false);
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
	}

	public void dismissAlert() {
		try {
			Alert a = driver.switchTo().alert();
			a.dismiss();
			reportSteps("The alert dismissed", "PASS");
		} catch (NoAlertPresentException e) {
			reportSteps("No alert present", "FAIL", false);
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
	}

	public void switchToFrameByIndex(int index) {
		try {
			driver.switchTo().frame(index);
			reportSteps("Switched to frame with index " + index, "PASS");
		} catch (NoSuchFrameException e) {
			reportSteps("The frame with index " + index + " is not presnt", "FAIL");
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
	}

	public void switchToFrameByname(String name) {
		try {
			driver.switchTo().frame(name);
			reportSteps("Switched to frame with name " + name, "PASS");
		} catch (NoSuchFrameException e) {
			reportSteps("The frame with name " + name + " is not presnt", "FAIL");
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
	}

	public void switchToFrameByElement(WebElement ele) {
		try {
			driver.switchTo().frame(ele);
			reportSteps("Switched to frame with elemrnt " + ele, "PASS");
		} catch (NoSuchFrameException e) {
			reportSteps("The frame with element " + ele + " is not presnt", "FAIL");
		} catch (Exception e) {
			reportSteps(e.getMessage(), "FAIL");
		}
		driver.switchTo().frame(ele);
	}

	public void defaultContent() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			reportSteps("Exception:" + e.getMessage(), "FAIL");
		}
	}

	public void DragAndDrop(WebElement drag, WebElement drop) {
		try {
			Actions a = new Actions(driver);
			a.dragAndDrop(drag, drop).build().perform();
			reportSteps("The drag and drop done successfully", "PASS");
		} catch (Exception e) {
			reportSteps("Exception:" + e.getMessage(), "FAIL");
		}
	}

	public boolean isEnabled(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			if (ele.isEnabled()) {
				reportSteps("The element with text " + text + " is enabled", "PASS");
				return true;
			} else {
				reportSteps("The element with text " + text + " is not enabled", "FAIL");
			}
		} catch (Exception e) {
			reportSteps("Exception:" + e.getMessage(), "FAIL");
		}
		return false;
	}

	public boolean isEnabled(WebElement ele, String elementName) {

		try {
			if (ele.isEnabled()) {
				reportSteps("The element " + elementName + " is enabled", "PASS");
				return true;
			} else {
				reportSteps("The element " + elementName + " is not enabled", "FAIL");
			}
		} catch (Exception e) {
			reportSteps("Exception:" + e.getMessage(), "FAIL");
		}
		return false;
	}

}
