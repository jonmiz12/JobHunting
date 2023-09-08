package tests.alljobs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import utils.Utils;

import java.io.IOException;

public class AllJobsBaseTest {

    String site = "alljobs";
    String userName = Utils.readProperty("username", site);
    String email = Utils.readProperty("email", site);
    String password = Utils.readProperty("password", site);
    String agentNames [] = Utils.readProperty("agentsnames", site).split(",");
    String[] jobskeys = Utils.readProperty("jobskeys", site).split(",");

    WebDriver driver;

    @BeforeMethod
    public void setup(ITestContext testContext){
        try {
            Runtime.getRuntime().exec("taskkill.exe /F /IM chromedriver.exe /T" + "cmd.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
