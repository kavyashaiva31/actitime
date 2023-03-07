package script1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Demo1 {
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		//this properties is from the java collection
		Properties p = new Properties();
		//this will load the prperties
		p.load(new FileInputStream("./config.properties"));
		//this will get the value of property
		String v = p.getProperty("ETO");
		System.out.println(v);
		
		
	}

}
