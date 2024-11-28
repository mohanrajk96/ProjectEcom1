package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import Base.SeleniumBase;

public class TS002_AddProductToCart extends SeleniumBase {

	
	  public TS002_AddProductToCart(RemoteWebDriver driver,ExtentTest eachNode) {
	  this.driver = driver; 
	  this.eachNode = eachNode;
	  
	  }
	 
	
	public TS002_AddProductToCart addProduct() throws InterruptedException {
		
		
		
	List<WebElement> products = locateElements("css", "[class='card'] b");
	for(WebElement e:products) {
		String text  = e.getText();
		if(text.equalsIgnoreCase(prop.getProperty("product"))) {
			WebElement addToProduct = driver.findElement(By.xpath("//*[@id=\"products\"]/div[1]/div[2]/div[2]/div/div/button[2]"));
			click(addToProduct," Add to product");
			 break;
		}
	}
	return this;
	}
	
}
