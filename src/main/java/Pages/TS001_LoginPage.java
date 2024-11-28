package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import Base.SeleniumBase;

public class TS001_LoginPage extends SeleniumBase {
	
	public TS001_LoginPage(RemoteWebDriver driver,ExtentTest eachNode) {
		this.driver = driver;
		this.eachNode = eachNode;
	}
	
	public TS001_LoginPage LoginPage(String username,String passWord) {
		
		WebElement email = locateElement("id", "userEmail");
		clearAndType(email, username);
		WebElement password = locateElement("id", "userPassword");
		clearAndType(password, passWord);
		WebElement submit = locateElement("id", "login");
		click(submit,"Signin button");
		webDriverWaitTillElementVisible(locateElement("css", "[class*='toast-title']"));
		verifyIsDisplayed(locateElement("css", "[class*='toast-title']"), "Login Successfully");
		return this ;
	}
	
	

	
}
