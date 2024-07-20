package tests.alljobs;

import org.testng.annotations.Test;
import pageobjects.alljobs.*;

public class AllJobs extends AllJobsBaseTest{

    @Test
    public void tc01_runAllJobs(){
        AJHeader h = new AJHeader(driver);
        h.clickLogin();
        AJLoginPage lp = new AJLoginPage(driver);
        lp.login(email, password);
        AJHomePage hp = new AJHomePage(driver);
//        hp.clickOverlayXBtn();
//        h.assertEquals(h.isUserNameMatch(userName), true);
        driver.get(agentsUrl);
//        h.clickAgents(agentsUrl);
        AJAgentsPage ap = new AJAgentsPage(driver);
        ap.clickFeed();
        AJFeedPage f = new AJFeedPage(driver);
        f.assertEquals(f.clickJobByArray(jobskeys), true);
    }
}
