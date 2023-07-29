package tests.jobnet;

import org.testng.annotations.Test;
import pageobjects.jobnet.FeedPage;
import pageobjects.jobnet.HomePage;

public class Jobnet extends JobnetBaseTest {

    @Test
    public void tc01_RunJobnet () {
        HomePage hp = new HomePage(driver);
        hp.assertEquals(hp.login(email, password, userName), true);
        hp.assertEquals(hp.search(searchField, subSearchFields, driver), true);
        FeedPage fp = new FeedPage(driver);
        fp.assertEquals(fp.findJobAndSendCVByContent(jobskeys, previewLetter, distributions, driver), true);
    }
}
