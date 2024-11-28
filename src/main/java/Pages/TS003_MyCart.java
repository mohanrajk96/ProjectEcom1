package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import Base.ProjectSpecificMethods;
import Base.SeleniumBase;

public class TS003_MyCart extends SeleniumBase {
	
	public  TS003_MyCart(RemoteWebDriver driver,ExtentTest eachNode) {
		
		this.driver = driver;
		this.eachNode = eachNode;
	}
	
	public TS003_MyCart checkout() {
		
		WebElement spinner = locateElement("css", "[class*='spinner']");
		webDriverWaitTillElementInvisible(spinner);
		WebElement toaster = locateElement("css", "[class*='toast-message']");
		verifyIsDisplayed(toaster,"Product Added To Cart");
		WebElement cart = locateElement("css", "[routerlink*='cart']");
		click(cart);
		WebElement productName = locateElement("css", "[class*='items'] h3");
		AssertEquals(getElementText(productName), prop.getProperty("product"));
		WebElement checkOut = locateElement("css", "[class*='subtotal '] button");
		scrollToElement(checkOut);
		click(checkOut);
		return this;
		 
		
	}

}
