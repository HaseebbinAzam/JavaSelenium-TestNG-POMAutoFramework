package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	/**
	 * This is used to initialize the driver based on given browser name.
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {
		// cross browser logic
		
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		
		String browserName = prop.getProperty("browser");
		System.out.println("browser name is: " + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;

		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;

		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			System.out.println("pls pass the right browser... " + browserName);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}
	
	/**
	 * get the local thread copy of the driver
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This is used to initialize the properties from the properties file
	 * @return this returns properties (prop)
	 */
	public Properties initProp() {
		
		prop = new Properties();
		FileInputStream ip= null;
		// cross prop logic
		//mvn clean install -Denv="qa"
		
		String envName = System.getProperty("env");
		System.out.println("Running the test suite on env: " +envName);
		
		if(envName== null) {
			System.out.println("Environment parameter missing, falling back to QA environment");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
		try {
		switch (envName.trim().toLowerCase()) {
		case "qa":
			 ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			break;
		case "dev":
			 ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
			break;
		case "stage":
			 ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
			break;
		case "prod":
			 ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
			break;
		case "uat":
			 ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
			break;

		default:
			System.out.println("please pass the right env name.." +envName);
			throw new FrameworkException("====WRONGENVPASSED====");
		}
		}
	 catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;

	}
	
	
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir")+"/screenshots/"+methodName+"_"+System.currentTimeMillis()+".png";
		
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
	


}
