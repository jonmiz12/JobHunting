package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.drushim.HomePage;
import pageobjects.drushim.Jobs;

import java.util.Arrays;
import java.util.Iterator;

public class Drushim extends BaseTest{

    @Test(dataProvider = "getAgentNames")
    public void tc01_runDrushim(String agentName){
        HomePage hp = new HomePage(driver);
        hp.assertEquals(hp.login(email, password, userName), true);
        hp.clickSmartAgent();
        hp.assertEquals(hp.clickAgentByContent(agentName), true);
        Jobs j = new Jobs(driver);
        j.findJobAndSendCVByContent(jobskeys);
    }

    @DataProvider
    public Object[][] getAgentNames() {
        return agentNames;
    }
}
