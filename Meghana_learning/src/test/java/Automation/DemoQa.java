package Automation;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

import org.openqa.selenium.WebDriver;

public class DemoQa {

	@Test
	
	public  void test() {
		
	//public static void main(String[] args) {
		
        // Set the path to the ChromeDriver executable
     //   System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");

		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBrowserVersion("116");
        
    //chromeOptions.setExperimentalOption("prefs", prefs);
			chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);			
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--test-type");
			chromeOptions.addArguments("--disable-extensions");	
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-popup-blocking");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--dns-prefetch-disable");
			chromeOptions.addArguments("--always-authorize-plugins");
			chromeOptions.addArguments("--disable-browser-side-navigation");
			chromeOptions.addArguments("--ignore-certificate-errors");
			chromeOptions.addArguments("--remote-allow-origins=*");

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver(chromeOptions);
        System.out.println("chrome is launched");
        
        // Open a website
        driver.get("https://demoqa.com/");
        System.out.println("url is launched");
        
        driver.manage().window().maximize();

        // Close the browser
        //driver.quit();
    }
}
