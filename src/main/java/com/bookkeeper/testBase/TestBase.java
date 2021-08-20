package com.bookkeeper.testBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.log4testng.Logger;

import com.bookkeeper.constants.Constants;
import com.bookkeeper.listeners.WebEventListener;
import com.bookkeeper.utils.TestUtility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties property;
	public static ChromeOptions chromeOptions;
	public static EventFiringWebDriver e_driver;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static WebEventListener eventListener;
	public static Logger log;

	public static String userId;
	public static String pwd;

	public TestBase() {
		log = Logger.getLogger(this.getClass());
		try {
			property = new Properties();
			FileInputStream inputStream = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/resources/Configuration.properties");
			property.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeSuite
	public void setLog4j() {
		TestUtility.setDateForLog4j();

		extent = new ExtentReports(
				System.getProperty("user.dir") + "/AutomationReport/Report_" + TestUtility.getSystemDate() + ".html");
		extent.addSystemInfo("Host Name", "Windows System");
		extent.addSystemInfo("User Name", "user");
		extent.addSystemInfo("Environment", "Automation Test Report");

	}

	public static void initialization(String bookkeeperRole) {
		String broswerName = property.getProperty("browser");
//				System.getProperty("browser");
		log.info("launching " + broswerName + " browser");

		if (broswerName.equalsIgnoreCase("Chrome")) {
			chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("useAutomationExtension", false);
			chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			chromeOptions.setExperimentalOption("prefs", prefs);

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
		} else if (broswerName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (broswerName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			System.out.println("Path of Driver Executable is not Set for any Browser");
		}

		e_driver = new EventFiringWebDriver(driver);

		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);

		if (bookkeeperRole.equalsIgnoreCase("admin"))
			driver.get(property.getProperty("Url"));
		else
			driver.get(property.getProperty("bookKeeperFlowUrl"));

		userId = property.getProperty(bookkeeperRole + "Id");
		pwd = property.getProperty(bookkeeperRole + "Pwd");

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {
		System.out.println(result);
		log.info("Browser Terminated");
		log.info("-----------------------------------------------");
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getName()); // To Add Name in Extent Report.
			extentTest.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable()); // To Add Errors and
			String screenshotPath = TestUtility.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // To Add Screenshot in Extent
																							// Report.
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
		}
		extent.endTest(extentTest); // Ending Test and Ends the Current Test and Prepare to Create HTML Report.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("Browser Terminated");
		log.info("-----------------------------------------------");
	}

	@AfterClass
	public void tearDown() {
//		driver.close();
		driver.quit();
	}

	@AfterSuite
	public void endReport() {
		extent.flush();
		extent.close();

	}

}
