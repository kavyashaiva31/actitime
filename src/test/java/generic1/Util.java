package generic1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//to call  the config.properties again and again here we are writing the code
//./congif.properties means its cume under the java

public class Util {
	
//here path is the , path of the file
	public static String  getxlData(String path , String sheet , int r , int c) {
		
		String value="";
		try {
//to handle the excel workbookfactory are used
	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
	   value =   wb.getSheet(sheet).getRow(r).getCell(c).toString();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}
	

//here we are passing the argument and key , path=pp_path, key means anything in propertiess
public static String getProperty(String path , String key) {
	String value ="";

	//this will load the prperties
	try {
		//in order to call the value from the config we are using the properties , properties cuming under the collection 
		Properties p = new Properties();
		p.load(new FileInputStream(path));
		value = p.getProperty(key);
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return value;
}
}
