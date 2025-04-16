package utils;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static WebDriver driver;
	
	public 	static void initializeDriver() {
		String browser = ConfigReader.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
        	driver = WebDriverManager.chromedriver().create();
        } else if (browser.equalsIgnoreCase("firefox")) {
        	driver = WebDriverManager.firefoxdriver().create();
        }
		driver.manage().window().maximize();
		System.out.println(ConfigReader.getProperty("stageUrl"));
		driver.get(ConfigReader.getProperty("stageUrl"));
	}
	
	public static WebDriver getDriver() {
        return driver;
    }

//    public static void quitDriver() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
