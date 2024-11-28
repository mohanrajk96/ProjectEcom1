package Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.ProjectSpecificMethods;
import Pages.TS001_LoginPage;
import Pages.TS002_AddProductToCart;
import Pages.TS003_MyCart;
import Pages.TS004_CheckOut;
import Pages.TS005_ConfirmationPage;
import Pages.TS006_OrdersPage;

public class TC006_OrdersPageTest extends ProjectSpecificMethods{
	
	@BeforeTest
	public void setvalues() {
		testCaseName = "Verify orders Page";
		testDescription = "Verify orders Page";
		authorName = "Raj";
		category = "SMOKE";
		environment = "STAGE";
		dataSheetName = "UserData";
		sheetName = "sheet1";

	}

	@Test(dataProvider = "fetchData")

	public void placeOrder(String username, String password) throws InterruptedException {

		new TS001_LoginPage(driver, eachNode).LoginPage(username, password);

		new TS002_AddProductToCart(driver, eachNode).addProduct();

		new TS003_MyCart(driver, eachNode).checkout();

		new TS004_CheckOut(driver, eachNode).placeOrder();

		new TS005_ConfirmationPage(driver, eachNode).verifyConfimationPage();
		
		new TS006_OrdersPage(driver, eachNode).verifyOrder();

	}


}
