package tests.sqlink;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utils;

import java.io.IOException;

public class SqlinkBaseTest {

    String site = "sqlink";

    WebDriver driver;
    String CVName = Utils.readProperty("CVName", site);
    String[] jobskeys = Utils.readProperty("jobkeys", site).split(",");

    @BeforeMethod
    public void setup(ITestContext testContext){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Utils.readProperty("url", site));
    }
//    @AfterMethod
    public void TearDown(){
        driver.quit();
    }
}
