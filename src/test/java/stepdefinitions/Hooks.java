package stepdefinitions;



import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverManager;

public class Hooks {
	@Before
    public void setUp() {
        DriverManager.initializeDriver();
    }

//    @After
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}
