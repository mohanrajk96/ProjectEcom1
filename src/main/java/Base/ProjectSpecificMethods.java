package Base;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import Utils.DataLibrary;

public class ProjectSpecificMethods extends SeleniumBase {

	@BeforeSuite
	public void loadUserData() throws IOException {
		loadData("config");
	}

	@BeforeClass
	public void setUp() {
		eachNode = test.createNode(testCaseName);
		startApplication(prop.getProperty("browser"), prop.getProperty("url"));

	}

	@AfterClass
	public void tearDown() {
		closeBrowser();
	}

	@DataProvider(name = "fetchData")
	public Object[][] fetchData() throws IOException {
		return DataLibrary.readExcelData(dataSheetName, sheetName);
	}

	@AfterSuite
	public void unloadUserData() {
		unloadData();
	}
}
