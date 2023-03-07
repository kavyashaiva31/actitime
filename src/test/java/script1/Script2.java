package script1;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import generic1.BaseTest;

public class Script2  extends BaseTest{
	@Test
	public void testB() {
//it is taken from the extentTest which is present in the extentReport
		t.log(LogStatus.PASS, driver.getTitle());
		
	}
}
