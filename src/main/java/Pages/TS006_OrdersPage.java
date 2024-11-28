package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;

import Base.SeleniumBase;

public class TS006_OrdersPage extends SeleniumBase {

	public TS006_OrdersPage(RemoteWebDriver driver, ExtentTest eachNode) {

		this.driver = driver;
		this.eachNode = eachNode;
	}

	public TS006_OrdersPage verifyOrder() {

		String product = getElementText(locateElement("xpath", "//tbody/tr/td[2]"));
		Assert.assertEquals(product, prop.getProperty("product"));
		click(locateElement("xpath", "//button[text()=' Sign Out ']")); //
		webDriverWaitTillElementVisible(locateElement("id", "toast-container"));
		verifyIsDisplayed(locateElement("id", "toast-container"), "Logout Successfully");

		return this;

	}
}
