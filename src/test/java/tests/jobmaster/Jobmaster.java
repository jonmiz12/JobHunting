package tests.jobmaster;

import org.testng.annotations.Test;
import pageobjects.Jobmaster.FeedPage;
import pageobjects.Jobmaster.HomePage;
import pageobjects.Jobmaster.LoginPage;

public class Jobmaster extends JobmasterBaseTest {

    @Test
    public void tc01_RunJobMaster () {
        HomePage hp = new HomePage(driver);
        hp.clickLoginBtn();
        LoginPage lp = new LoginPage(driver);
        hp.assertEquals(lp.login(email, password, fullName, driver), true);
        hp.assertEquals(hp.search(searchQuary[0]),true);
        FeedPage fp = new FeedPage(driver);
        fp.searchByArrayAndCallSendCV(searchQuary, jobskeys, CVName, previewLetter, driver);
    }
}
