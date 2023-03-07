package generic;

import java.io.FileInputStream;
import java.util.Properties;

public class Util {
	public static String getProperty()
	{
		String value="";
		try 
	{
		Properties p=new Properties();
			p.load(new FileInputStream(path));
			value=p.getProperty(key);
			System.out.println(value);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return value;
	}
}
