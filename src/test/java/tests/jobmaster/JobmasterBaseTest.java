package tests.jobmaster;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class JobmasterBaseTest {

    String site = "jobmaster";
    String CVName = Utils.readProperty("CVName", site);
    String fullName = Utils.readProperty("username", site);
    String previewLetter = Utils.readProperty("previewletter", site);
    String email = Utils.readProperty("email", site);
    String searchQuary[] = Utils.readProperty("searchQuary", site).split(",");
    String password = Utils.readProperty("password", site);
    String agentNames [][] = {{"Automation Engineer"},{"מפתח/ת אוטומציה"},{"מהנדס/ת אוטומציה"},{"Automation Developer"}};
    String[] jobskeys = Utils.readProperty("jobkeys", site).split(",");

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
