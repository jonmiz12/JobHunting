package pageobjects.alljobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.BasePage;

import java.util.List;

public class AJFeedPage extends BasePage {
    @FindBy (css = "[id^='job-box-container']")
    List<WebElement> jobs;
    @FindBy (css = "[class^='job-content-top-title']")
    List<WebElement> jobsTitles;
    @FindBy (css = "[class^=\"job-button-send\"] .send-cv-btn")
    List<WebElement> jobsSendCV;
    final static String vip = " .job-content-top-vip";
    public AJFeedPage(WebDriver driver) {
        super(driver);
    }

    public boolean clickJobByArray (String[]jobKeys) {
        waitFor(jobs);
        for (int i = 0; i<jobs.size(); i++) {
            if (!jobs.get(i).isDisplayed()){
                waitFor(jobs.get(i));
            }
            scrollIntoView(jobsTitles.get(i));
            if (i%13==0 && i!=0){
                sleep(2500);
            }
            if (isVipDisplayed(jobs.get(i))) {continue;}

            for (int j = 0; j<jobKeys.length; j++) {
                if (jobsTitles.get(i).getText().contains(jobKeys[j])) {
                    takeScreeshot();
                    scrollIntoView(jobs.get(i));
                    click(jobsSendCV.get(i));
                    sleep(8000);
                }
            }
        }
        return true;
    }

    public boolean isVipDisplayed (WebElement job) {
        try {
            return job.findElement(By.cssSelector(vip)).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
}
