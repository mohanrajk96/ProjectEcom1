package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import Base.SeleniumBase;

public class TS004_CheckOut extends SeleniumBase {

	public TS004_CheckOut(RemoteWebDriver driver, ExtentTest eachNode) {

		this.driver = driver;
		this.eachNode = eachNode;
	}

	public TS004_CheckOut placeOrder() {

		scrollDown();
		WebElement month = locateElement("css", "[class*='input ddl']:first-of-type");
		selectDropdownByText(month, "08");

		WebElement date = locateElement("css", "[class*='input ddl']:last-of-type");
		selectDropdownByText(date, "12");

		WebElement cvv = locateElement("xpath", "(//div[@class='field small']//input)[1]");
		clearAndType(cvv, "584");

		WebElement cardName = locateElement("xpath", "(//div[@class='field'])[2]/input");
		clearAndType(cardName, "Raj");

		WebElement userEmail = locateElement("xpath", "//div[contains(@class,'user__name')]/label");
		AssertEquals(userEmail.getText(), prop.getProperty("username"));

		WebElement country = locateElement("css", "[placeholder*='Country']");
		List<WebElement> countries = locateElements("xpath", "//span[@class='ng-star-inserted']");

		clearAndType(country, "ind");

		
		  driver.findElement(By.xpath(
		  "/html/body/app-root/app-order/section/div/div/div[2]/div/div/div[3]/div[2]/div[2]/div/div[1]/div/section/button[2]/span/i"
		  )).click();
		 
		
		scrollToElement(locateElement("xpath", "//a[text()='Place Order ']"));
		click(locateElement("xpath", "//a[text()='Place Order ']"));
		return this;

	}

}
