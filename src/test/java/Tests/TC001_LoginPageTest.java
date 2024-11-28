package Tests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.ProjectSpecificMethods;
import Pages.TS001_LoginPage;

public class TC001_LoginPageTest extends ProjectSpecificMethods {
	
	@BeforeTest
	public void setvalues() {
		
		testCaseName = "Login Application";
		testDescription = "Logging into the Application";
		authorName = "Raj";
		category = "SMOKE";
		environment = "STAGE";
        dataSheetName = "UserData"; 
        sheetName = "sheet1";

	}
	
	
	  @Test(dataProvider="fetchData")
	  
	  public void loginApp(String username,String
	  password) {
	  
	  new TS001_LoginPage(driver, eachNode)
	  . LoginPage(username, password); 
	  
	  }
	 

}
