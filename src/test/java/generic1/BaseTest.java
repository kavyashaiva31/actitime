package generic1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;
	public static ExtentReports extent;
	public ExtentTest t;
	
	
//this is the folder name where all the details are stored
//we make the config as final becze we have created the different options as qa , staging , so we make the config as constant
	public final String PPT_PATH = "./properties/config.properties";
	public String REPORT_PATH = "./target/spark.html";
	
	
//this we make static cze it will open once, here once the webmanager is added in thepom.xml it will automaticaly add the chrome and firefox
	static {
		WebDriverManager.chromedriver().setup(); 
		WebDriverManager.firefoxdriver().setup(); 
	}
	
//this extentreport creates the object , report needs to  print once  so we are putiing inside the @Beforesuite
	@BeforeSuite
	public void createReport() {
		extent = new ExtentReports("REPORT_PATH");
	}
	
//this is used to publish the report
	@AfterSuite
    public void publishReport() {
		extent.flush();
	}
	
	
//here we are adding the logstatus becze we also need the information of the @BeforeMethod
	@Parameters({"config"})
    @BeforeMethod
	public void openApp(@Optional(PPT_PATH) String path , Method testname) throws MalformedURLException {
//properties is the folder
		path="./properties/"+path;
//name of the method is generated in the extent report
    	t = extent.startTest(testname.getName());
    	t.log(LogStatus.INFO,"property file:" +path);
    	
        //taking the value from the util .ppt_path is a config file , remaining all are key 
  //this is taking all the vales/properties from the qa/staging/cofig prperties which is sended from the testNG
    	String usegrid = Util.getProperty(path, "USEGRID");
    	String remote = Util.getProperty(path, "REMOTE");
    	String browser = Util.getProperty(path, "BROWSER");
    	String  APPURL = Util.getProperty(path, "APPURL");
    	String sITO = Util.getProperty(path, "ITO");
    	String sETO = Util.getProperty(path, "ETO");
    	
    	t.log(LogStatus.INFO, "open" +browser+ "browser");
    	
    	if(usegrid.equalsIgnoreCase("yes"))
    	         {
    	 	    t.log(LogStatus.INFO, "browser" +browser+ "Browser in Remote system");
    	
    		    DesiredCapabilities  capability = new DesiredCapabilities();;
    		    capability.setBrowserName(browser);
    		    driver = new RemoteWebDriver(new URL(remote), capability);
    	         }
    	else
    	      {
    		t.log(LogStatus.INFO, "browser" +browser+ "Browser in local system");
    	
    	     if(browser.equals("chrome"))
    	       {
    		       driver = new ChromeDriver();
    	        }
    	     else
    	       {
    		      driver = new FirefoxDriver();
    	       }
    	driver = new ChromeDriver();
    	driver.manage().window().maximize();
		driver.get("https://demo.actitime.com/login.do");
//here we are converting the string to long
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.valueOf(sITO)));
		wait = new WebDriverWait(driver , Duration.ofSeconds(1000));
	}
   }
    
    
    @AfterMethod
    public void closeApp(ITestResult result) throws IOException {
    	
    	int status = result.getStatus();
    	if(status == 2) {
    				String errorDetails = 	result.getThrowable().getMessage();
    				//it will get the failed method name
    				String testname = result.getName();
    				t.log(LogStatus.FAIL, errorDetails);
    //this will get the screenshot of the failed method and display under the target
    				TakesScreenshot screenshot= (TakesScreenshot) driver;
    				File srcImg = screenshot.getScreenshotAs(OutputType.FILE);
    				FileUtils.copyFile(srcImg, new File("./target/" +testname+ ".png"));
   //it will take the screenshot of the failed methods  and display in the report
    				String htmlcode = t.addScreenCapture(testname+".png");
    				t.log(LogStatus.FAIL, htmlcode);
    	}
    	
    	t.log(LogStatus.INFO, "close the chrome browser");
    	driver.quit();
    }
	
}
