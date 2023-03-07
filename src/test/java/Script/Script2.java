package Script;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import generic.BaseTest;

public class Script2 extends BaseTest {

	@Test
	public void deleteProduct()
	{
		test.log(LogStatus.PASS,driver.getTitle());
	}
}
