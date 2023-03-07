package generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;
	public static ExtentReports extent;
	public ExtentTest test;
	public String PPT_PATH="./config.properties";
	static
	{
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
	}
	
	@BeforeSuite
	public void createReport() {
		extent = new ExtentReports("target/Spark.html");
	}
	
	@AfterSuite
	public void publishReport() {
		extent.flush();
	}
	
	@BeforeMethod
	public void openApp(Method testMethod)
	{
		test = extent.startTest(testMethod.getName());
		
		
		String browser = Util.getProperty(PPT_PATH,"BROWSER");
		String appUrl = Util.getProperty(PPT_PATH,"APPURL");
		String sITO = Util.getProperty(PPT_PATH,"ITO");
		String sETO =  Util.getProperty(PPT_PATH,"ETO");
		
		test.log(LogStatus.INFO, "Open the "+browser+" Browser");
		
		if(browser.equals("chrome"))
		{
			driver=new ChromeDriver();
		}
		else
		{
			driver=new FirefoxDriver();
		}
		test.log(LogStatus.INFO, "Maximize the Browser");
		driver.manage().window().maximize();
		
		test.log(LogStatus.INFO, "Enter the url:"+appUrl);
		driver.get(appUrl);
		
		test.log(LogStatus.INFO, "Set implicitlyWait:"+sITO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.valueOf(sITO)));
		
		test.log(LogStatus.INFO, "Set ExplicitWait"+sETO);
		wait=new WebDriverWait(driver,Duration.ofSeconds(Long.valueOf(sETO)));
	}
	
	
	@AfterMethod
	public void closeApp(ITestResult result) throws IOException
	{
		
		int status = result.getStatus();
		if(status==2)
		{
			String errDetails = result.getThrowable().getMessage();
			String testName=result.getName();
			test.log(LogStatus.FAIL, errDetails);
		
			TakesScreenshot t=(TakesScreenshot)driver;
			File srcIMG = t.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcIMG, new File("./target/"+testName+".png"));
			String htmlcode = test.addScreenCapture(testName+".png");
			test.log(LogStatus.FAIL, htmlcode);
		}
		
		test.log(LogStatus.INFO, "close the Browser");
		driver.quit();
	}
}
