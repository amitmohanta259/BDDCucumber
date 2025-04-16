package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ExcelUtil;



public class LoginPage {
	WebDriver driver;
	ExcelUtil reusableMethod;
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this); 
		reusableMethod = new ExcelUtil();
		this.driver = driver;
	}
	
	@FindBy(xpath = "//input[@id='username']")
	WebElement username;
	
	@FindBy(xpath = "//input[@id='password']")
	WebElement password;
	
	@FindBy(xpath = "//input[@id='kc-login']")
	WebElement signIn;
	
	public void login(String user_name, String pass) throws InterruptedException {
		reusableMethod.explicitWait(driver, username);
		username.sendKeys(user_name);
		reusableMethod.explicitWait(driver, password);
		password.sendKeys(pass);
		reusableMethod.explicitWait(driver, signIn);
		signIn.click();
		Thread.sleep(4000);
	}

}
