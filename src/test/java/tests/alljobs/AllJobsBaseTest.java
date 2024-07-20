package tests.alljobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utils;
//import utils.VideoListener;

import java.io.IOException;

public class AllJobsBaseTest {

    String site = "alljobs";
    String userName = Utils.readProperty("username", site);
    String email = Utils.readProperty("email", site);
    String password = Utils.readProperty("password", site);
    String[][]  jobskeys = {Utils.readProperty("jobskeysAuto", site).split(",")};
    String agentsUrl = Utils.readProperty("agentsUrl", site);

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
//        stopRecording();
    }
}
