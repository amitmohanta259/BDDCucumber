package stepdefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.LoginPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ExcelUtil;

public class LoginSteps {
	LoginPage loginPage;
	ExcelUtil reusableMethod;
    @Given("user is on login page")
    public void user_on_login_page() {
        DriverManager.getDriver().get(ConfigReader.getProperty("stageAdminUrl"));
        loginPage = new LoginPage(DriverManager.getDriver());
    }

    @When("user logs in with valid credentials")
    public void user_logs_in() throws InterruptedException, IOException {
    	reusableMethod = new ExcelUtil();
        List<HashMap<String, String>> data = reusableMethod.accessDataFromSheet("Login Creds", "Therapist");
        // Convert List of Lists to a 2D Object array
        Object[][] dataArray = new Object[data.size()][2];
        for (HashMap<String, String> dataSet : data) {
			int i = 0;
			dataArray[i][0] = dataSet.get("Username"); // Username
            dataArray[i][1] = dataSet.get("Password"); // Password
		}
        String username = dataArray[1][0].toString();
        String password = dataArray[1][1].toString();
        loginPage.login(username, password);
    }

    @Then("user should land on homepage")
    public void verify_homepage() {
        Assert.assertTrue(DriverManager.getDriver().getTitle().contains("Orthocare"));
    }
}
