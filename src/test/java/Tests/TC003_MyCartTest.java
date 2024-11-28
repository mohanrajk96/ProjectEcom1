package Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.ProjectSpecificMethods;
import Pages.TS001_LoginPage;
import Pages.TS002_AddProductToCart;
import Pages.TS003_MyCart;

public class TC003_MyCartTest extends ProjectSpecificMethods{
	
	@BeforeTest
	public void setvalues() {
		testCaseName = "Verify Cart";
		testDescription = "Verifying the Cart";
		authorName = "Raj";
		category = "SMOKE";
		environment = "STAGE";
		dataSheetName = "UserData";
		sheetName = "sheet1";

	}

	@Test  (dataProvider="fetchData") 
	
	public void verifyCart(String username, String password) throws InterruptedException {
		
		new TS001_LoginPage(driver, eachNode)
		.LoginPage(username, password);
		
		new TS002_AddProductToCart(driver, eachNode)
		.addProduct();
		
		new TS003_MyCart(driver, eachNode)
		.checkout();
		
		
	}

}
