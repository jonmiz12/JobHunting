package tests.jobnet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class JobnetBaseTest {

    String site = "jobnet";
    String previewLetter = Utils.readProperty("previewletter", site);
    String[] distributions = Utils.readProperty("distributions", site).split(",");
    String userName = Utils.readProperty("username", site);
    String email = Utils.readProperty("email", site);
    String password = Utils.readProperty("password", site);
    String[] jobskeys = Utils.readProperty("jobskeys", site).split(",");
    String searchField = Utils.readProperty("searchField", site);
    String[] subSearchFields = Utils.readProperty("subSearchFields", site).split(",");
    String CV = Utils.readProperty("CV", site);

    WebDriver driver;

    @BeforeMethod
    public void setup(ITestContext testContext){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Utils.readProperty("url", site));
    }

    //    @AfterMethod
    public void TearDown(){
        driver.quit();
    }
}
