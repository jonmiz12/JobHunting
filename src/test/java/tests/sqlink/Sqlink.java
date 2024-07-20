package tests.sqlink;

import org.testng.annotations.Test;
import pageobjects.sqlink.Feed;

public class Sqlink extends SqlinkBaseTest{
    @Test
    public void tc01_runSqlink() {
        Feed f = new Feed(driver);
        f.assertEquals(f.sendCVByKeys(jobskeys, CVName),true);
    }
}
