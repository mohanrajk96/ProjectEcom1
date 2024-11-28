package Utils;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public abstract class Reporter {

	public ExtentSparkReporter reporter;
	public static ExtentReports extent;
	public ExtentTest eachNode, test;
	public String testCaseName,testDescription,authorName,category,environment,dataSheetName,sheetName;
	

	@BeforeSuite
	public void startReport() {

		reporter = new ExtentSparkReporter("./Report/report.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	@BeforeClass
	public void report() {

		test = extent.createTest(testCaseName, testDescription);
		test.assignAuthor("Author =" + authorName);
		test.assignCategory("Category=" + category);
		test.assignDevice("Environment =" + environment);
	}

	public abstract long takeSnap();

	public void reportSteps(String desc, String status, boolean bSnap) {

		if (status.equalsIgnoreCase("PASS")) {
			eachNode.pass(desc);
		} else if (status.equalsIgnoreCase("FAIL")) {
			Media img = null;
			if (bSnap && !status.equalsIgnoreCase("INFO")) {

				long snapNumber = 100000L;
				snapNumber = takeSnap();
				img = MediaEntityBuilder.createScreenCaptureFromPath("./../Report/" + snapNumber + ".png").build();
			}
			eachNode.fail(desc, img);
			//throw new RuntimeException();
		} else if (status.equalsIgnoreCase("WARNING")) {
			eachNode.warning(desc);

		} else if (status.equalsIgnoreCase("INFO")) {
			eachNode.info(desc);
		}
	}

	public void reportSteps(String description, String status) {
		reportSteps(description, status, true);
	}

	@AfterSuite
	public void stopReport() {
		extent.flush();
	}
}
