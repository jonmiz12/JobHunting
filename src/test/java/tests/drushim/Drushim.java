package tests.drushim;

import org.testng.annotations.Test;
import pageobjects.drushim.HomePage;
import pageobjects.drushim.Jobs;

public class Drushim extends DrushimBaseTest {

    @Test
    public void tc01_runDrushim(){
        HomePage hp = new HomePage(driver);
        hp.assertEquals(hp.login(email, password, userName), true);
        hp.clickSmartAgent();
        hp.assertEquals(hp.clickAgentByContent(agentNames[0]), true);
        Jobs j = new Jobs(driver);
        j.assertEquals(j.findJobAndSendCVByContent(jobskeys, agentNames), true);
    }
}
