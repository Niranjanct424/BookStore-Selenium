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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\dell\\Desktop\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\dell\\Desktop\\geckodriver.exe");
//		WebDriverManager.chromedriver().setup();
//		driver = new ChromeDriver();
		driver = new FirefoxDriver();
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
	
	@DataProvider(name = "user-register")
	public String[][] registerDataProvider() {
		return new String[][] { { "Niranjan@12","niranjan246800@gmail.com", "niranjan12","niranjan12","8880846463" }, 
			{"Acharya","niranjan.amca.16@acharya.ac.in","niranjan12","niranjan12","8880846463" } };
	}
	

	@Test(dataProvider = "user-register")
	public void registration(String name, String email, String pass,String conpass,String pnoneno)
			throws InterruptedException {

		test = extent.createTest("LoginTest");
		driver.get("http://localhost:4200/register");
	
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='regname']")).sendKeys(name);
		driver.findElement(By.xpath("//input[@id='regemail']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='regpass']")).sendKeys(pass);
		driver.findElement(By.xpath("//input[@id='regconfpass']")).sendKeys(conpass);
		driver.findElement(By.xpath("//input[@id='regpn']")).sendKeys(pnoneno);
		driver.findElement(By.xpath("//mat-radio-button[@id='reguse']")).click();

		Thread.sleep(10000);

		driver.findElement(By.xpath("//span[@class='mat-button-wrapper']")).click();
		Thread.sleep(25000);

		driver.quit();

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

		test = extent.createTest("sortBooks test");
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();

		Thread.sleep(20000);

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

		test = extent.createTest("addAndRemoveBookToCart");
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

		driver.findElement(By.xpath("//div[4]//mat-card[1]//mat-card-content[1]//div[5]//div[1]//button[1]//span[1]"))
				.click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'add_shopping_cart')]")).click();
		Thread.sleep(8000);
		driver.findElement(By.xpath("//button[contains(text(),'Remove')]")).click();
		Thread.sleep(5000);
		driver.quit();

	}

	@DataProvider(name = "forgot-password-data-provider")
	public String[][] forgotPasswordProvider() {
		return new String[][] { { "niranjan246800@gmail.com" },
				{ "niranjan.amca.16@acharya.ac.in" } };
	}
	
	@Test(dataProvider = "forgot-password-data-provider")
	public void forgotPassword(String email) throws InterruptedException {

		test = extent.createTest("forgot Password");
		driver.get("http://localhost:4200/forget-password");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='forgotemail']")).sendKeys(email);
		driver.findElement(By.xpath("//button[@id='forgotsub']")).click();
		Thread.sleep(15000);
		driver.quit();

	}
	
	
	@Test(dataProvider = "user-login-info-excel-data-provider")
	public void PeginationTestForBooks(String userId, String password) throws InterruptedException {

		test = extent.createTest("PeginationTestForBooks");
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();
		Thread.sleep(20000);
		
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(15000);
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//a[contains(text(),'>')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[contains(text(),'<')]")).click();
		Thread.sleep(5000);
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(5000);
		driver.quit();

	}
	@Test(dataProvider = "user-login-info-excel-data-provider")
	public void SearchBookByBookName(String userId, String password) throws InterruptedException {

		test = extent.createTest("SearchBookByBookName");
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();
		Thread.sleep(20000);
		
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(17000);
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("In Search of Lost Time");
		Thread.sleep(10000);
	
		driver.quit();

	}
	
	@Test(dataProvider = "user-login-info-excel-data-provider")
	public void SearchBookByBookAuthor(String userId, String password) throws InterruptedException {

		test = extent.createTest("SearchBookByBookAuthor");
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();
		Thread.sleep(20000);
		
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(17000);
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("Miguel de Cervantes");
		Thread.sleep(10000);
		
	
		driver.quit();

	}
	
	@DataProvider(name = "user-login-for-place-order")
	public String[][] orderDataProvider() {
		return new String[][] { { "niranjan246800@gmail.com", "niranjan" }, };
//					{"niranjan.amca.16@acharya.ac.in", "niranjan" } 
	}
	
	@Test(dataProvider = "user-login-for-place-order")
	public void placeOrderOfBook(String userId, String password) throws InterruptedException {

		test = extent.createTest("placeOrderOfBook");
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();
		Thread.sleep(20000);

		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(15000);
	
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//a[contains(text(),'>')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[3]//mat-card[1]//mat-card-content[1]//div[5]//div[1]//button[1]//span[1]")).click();
		Thread.sleep(7000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'add_shopping_cart')]")).click();
		Thread.sleep(7000);
		driver.findElement(By.xpath("//button[contains(text(),'+')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'+')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'-')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'-')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'-')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'-')]")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@class='button1 ng-star-inserted']")).click();
		Thread.sleep(5000);
		System.out.println("scrool down");
		js.executeScript("window.scrollBy(0,1000)");
		driver.findElement(By.xpath("//button[@class='button3']")).click();
		System.out.println("continue 2");
		Thread.sleep(5000);
		System.out.println("scrool down completed");
		driver.findElement(By.cssSelector("body.mat-typography:nth-child(2) app-cart.ng-star-inserted:nth-child(2) div.main-conatiner.ng-star-inserted:nth-child(3) div.row2 div:nth-child(1) div.column.ng-star-inserted:nth-child(2) div.ng-star-inserted div:nth-child(4) > button.button2")).click();
		Thread.sleep(45000);
		System.out.println("order placed");
	
		driver.quit();

	}
	
	

	@DataProvider(name = "user-rate-for-placed-order")
	public String[][] checkRatingDataProvider() {
		return new String[][] { { "niranjan.amca.16@acharya.ac.in", "niranjan" }, };
//					{"niranjan.amca.16@acharya.ac.in", "niranjan" } 
	}
	
	@Test(dataProvider = "user-rate-for-placed-order")
	public void checkRateAndReviewForBook(String userId, String password) throws InterruptedException {

		test = extent.createTest("rate and review for Book");
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		driver.get("http://localhost:4200/books/rateandreview/5/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6NH0.h7VwD4ozcWqsKh-cW0Y-10n3xWH2AwaDU6zN9YAqZvi9vVx8aVE8LMngw1_4MIajQ9aZI9XTgGw05-1I9V3reQ");
		System.out.println("in review page");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//button[@id='star_3']//mat-icon[@class='mat-icon notranslate material-icons mat-icon-no-color'][contains(text(),'star_border')]")).click();
		System.out.println("rate taken");
		Thread.sleep(7000);
		driver.findElement(By.xpath("//textarea[@placeholder='Review here........']")).sendKeys("This book had very boring content cant read find single page thrilling..... ");
		System.out.println("got review");
		Thread.sleep(7000);
		driver.findElement(By.xpath("//span[contains(text(),'Submit')]")).click();
		System.out.println("submit clicked");
		Thread.sleep(15000);
		driver.quit();
	}
	
	@DataProvider(name = "review-for-placed-order")
	public String[][] reviewDataProvider() {
		return new String[][] { { "niranjan246800@gmail.com", "niranjan" }, };
	}
	
	@Test(dataProvider = "review-for-placed-order")
	public void rateAndReviewForBook(String userId, String password) throws InterruptedException {

		test = extent.createTest("Reviews of books test");
		JavascriptExecutor js = (JavascriptExecutor) driver; 

		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();
		Thread.sleep(25000);
		
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(15000);
		driver.findElement(By.xpath("//div[8]//mat-card[1]//mat-card-title[1]//img[1]")).click();
		Thread.sleep(5000);
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(5000);
		
		driver.quit();
	}
	
	@Test(dataProvider = "review-for-placed-order")
	public void addBookToWishList(String userId, String password) throws InterruptedException {

		test = extent.createTest("Reviews of books test");
		JavascriptExecutor js = (JavascriptExecutor) driver; 

		driver.get("http://localhost:4200/login");
		System.out.println("http://localhost:4200/login  url");
//		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
//		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//mat-radio-button[@id='userbutton']")).click();
		Thread.sleep(25000);
		
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(15000);
		
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//span[contains(text(),'WISHLIST')]")).click();
		Thread.sleep(7000);
		js.executeScript("window.scrollBy(0,250)");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'favorite_border')]")).click();
		Thread.sleep(7000);
		
		
		
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
