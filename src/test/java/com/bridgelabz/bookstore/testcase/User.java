package com.bridgelabz.bookstore.testcase;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bridgelabz.bookstore.util.ExcelReadUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class User {

	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\dell\\Desktop\\chromedriver.exe");
//		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@BeforeTest
	public void beforeTest() {
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/test-output/BookStoreUserModulerRport.html");

		htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
		htmlReporter.config().setReportName("BookStore User Module Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Passing General information
		extent.setSystemInfo("Host-name ", "localhost");
		extent.setSystemInfo("OS ", "Windows 10");
		extent.setSystemInfo("Tester-Name ", "Niranjan");
		extent.setSystemInfo("Browser ", "Crome");
		extent.setSystemInfo("Application-Name ", "BookStoreApplication");
		System.out.println("inside method");

	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}

	// Create the Data Provider and give the data provider a name
	@DataProvider(name = "user-ids-passwords-excel-data-provider")
	public String[][] userIdsAndPasswordsDataProvider() {
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/login-data.xlsx", "Sheet1", 3);
	}

	// Use the data provider
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void login(String userId, String password, String isLoginExpectedToBeSuccessfulString)
			throws InterruptedException {

		test = extent.createTest("LoginTest");
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();

		Thread.sleep(10000);

		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(25000);
		String expected = driver.getCurrentUrl();
		String actual = "http://localhost:4200/books";
		assertEquals(actual, expected);
		Thread.sleep(10000);

		driver.quit();

	}

	@DataProvider(name = "user-login-info-excel-data-provider")
	public String[][] loginDataProvider() {
		return new String[][] { { "niranjan246800@gmail.com", "niranjan" }, };
//					{"niranjan.amca.16@acharya.ac.in", "niranjan" } 
	}

	@Test(dataProvider = "user-login-info-excel-data-provider")
	public void sortBooks(String userId, String password) throws InterruptedException {

		test = extent.createTest("LoginTest");
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();

		Thread.sleep(10000);

		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(15000);
		String expected = driver.getCurrentUrl();
		String actual = "http://localhost:4200/books";
		assertEquals(actual, expected);
		Thread.sleep(5000);

		driver.findElement(By.xpath("//select[@id='sort']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//option[@id='opt1']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//option[@id='opt2']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//option[@id='opt3']")).click();
		Thread.sleep(5000);
		driver.quit();

	}
	
	@Test(dataProvider = "user-login-info-excel-data-provider")
	public void addAndRemoveBookToCart(String userId, String password) throws InterruptedException {

		test = extent.createTest("LoginTest");
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();

		Thread.sleep(10000);

		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(15000);
		String expected = driver.getCurrentUrl();
		String actual = "http://localhost:4200/books";
		assertEquals(actual, expected);
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//div[4]//mat-card[1]//mat-card-content[1]//div[5]//div[1]//button[1]//span[1]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'add_shopping_cart')]")).click();
		Thread.sleep(8000);
		driver.findElement(By.xpath("//button[contains(text(),'Remove')]")).click();
		Thread.sleep(5000);
		driver.quit();

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent
																					// report
			String screenshotPath = User.getScreenshot(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
		}
		driver.quit();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	/**
	 * Test to display user credentials fetching from xlsx sheet deepToString() to
	 * display details which array contains all user data like username and password
	 */
	@Test
	public void readFromExcel() {

		String[][] data = ExcelReadUtil.readExcelInto2DArray("./src/test/resources/login-data.xlsx", "Sheet1", 3);
		System.out.println(Arrays.deepToString(data));

		System.out.println(Arrays.deepToString(data));

	}

}
