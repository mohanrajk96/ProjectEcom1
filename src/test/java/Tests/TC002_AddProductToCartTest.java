package Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.ProjectSpecificMethods;
import Pages.TS001_LoginPage;
import Pages.TS002_AddProductToCart;

public class TC002_AddProductToCartTest extends ProjectSpecificMethods {

	@BeforeTest
	public void setvalues() {
		testCaseName = "Add product to cart";
		testDescription = "Adding product to cart";
		authorName = "Raj";
		category = "SMOKE";
		environment = "STAGE";
		dataSheetName = "UserData";
		sheetName = "sheet1";

	}

	@Test  (dataProvider="fetchData") 
	public void addProduct(String username, String password) throws InterruptedException {

		
		new TS001_LoginPage(driver, eachNode) 
		.LoginPage(username, password);
		 

		new  TS002_AddProductToCart(driver, eachNode)
		.addProduct();
	}
}
