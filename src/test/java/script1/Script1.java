package script1;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import generic1.BaseTest;
import generic1.Util;

public class Script1 extends BaseTest{

	
	@Test
	public void testA() {
//accessing the data from the excel , script1 is the naming the script in the excel file
		String data =Util.getxlData("./data/input.xlsx", "script1", 1, 0);
		t.log(LogStatus.PASS, data);
		
//it is taken from the extentTest which is present in the extentReport
		t.log(LogStatus.PASS, driver.getTitle());
		//Assert.fail("home page is not displayed");
	}
}
