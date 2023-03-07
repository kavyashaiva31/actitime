package Script;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import generic.BaseTest;

public class script1 extends BaseTest{

	
	

	@Test
	public void createCustomer()
	{
		test.log(LogStatus.PASS,driver.getTitle());
		Assert.fail("home page is not displayed");
	}
}
