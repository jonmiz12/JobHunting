package pageobjects.alljobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.css.Counter;
import pageobjects.BasePage;

import javax.print.attribute.standard.JobName;
import java.util.List;

public class AJFeedPage extends BasePage {
    @FindBy (css = "[id^='job-box-container']")
    List<WebElement> jobs;
    @FindBy (css = jobsTitlesCSS)
    List<WebElement> jobsTitles;
    @FindBy (css = "[class^=\"job-button-send\"] .send-cv-btn")
    List<WebElement> jobsSendCV;
    @FindBy (css = ".gad.job-setting")
    List<WebElement> jobsSendSettings;
    @FindBy (css = ".job-user-cv-name>div")
    List<WebElement> CVNames;
    @FindBy (css = ".job-user-cv-ctxt")
    WebElement CVDropdown;
    @FindBy (css = ".job-sendcv-container .job-button-send>a")
    WebElement overlaySendBtn;
    @FindBy (css = "#ZA_CAMP_BUBBLE_CLOSE_BUTTON_CID_80959")
    WebElement popUpCloseBtn;
    @FindBy (css = "#job-sendcv-qs")
    WebElement overlayQs;
    @FindBy (css = "#job-sendcv-form>div>img")
    WebElement sentXBtn;
    @FindBy (css = ".job-sendcv-boxclose")
    WebElement overlaySendClose;

    final static String externalLink = ".job-border-highlight";
    final static String jobsTitlesCSS = "[class^='job-content-top-title']";
    final static String VIPJob = ".job-content-top-vip";
    public AJFeedPage(WebDriver driver) {
        super(driver);
    }

    public boolean clickJobByArray (String[][] jobsKeys) {
        if (waitForBool(popUpCloseBtn)) {
            click(popUpCloseBtn);
        }
        waitFor(jobs);
        for (int i = 0; i<jobs.size(); i++) {
            if (!jobs.get(i).isDisplayed()){
                waitFor(jobs.get(i));
            }
            scrollIntoView(jobs.get(i).findElement(By.cssSelector(jobsTitlesCSS)));
            if (i%13==0 && i!=0){
                sleep(2500);
            }
            if (isVipDisplayed(jobs.get(i))) {continue;}
            if (isExternalLink(jobs.get(i))) {continue;}

            sendCVByCVName(jobsKeys[0][0], i);
        }
        return true;
    }

    public boolean sendCVByCVName (String CVName, int i) {
        takeScreeshot();
        scrollIntoView(jobs.get(i));
        click(jobsSendSettings.get(i));
        waitFor(CVDropdown);
        if (isDisplayedBool(overlayQs)) {
            click(overlaySendClose);
            return false;
        }
        click(CVDropdown);
        for (int j = 0; j<CVNames.size(); j++) {
            if (CVNames.get(j).getText().contains(CVName)){
                click(CVNames.get(j));
            }
        }
        scrollIntoView(overlaySendBtn);
        click(overlaySendBtn);
        sleep(8000);
        if (waitForBool(sentXBtn)){
            click(sentXBtn);
        };
        sleep(1500);
        return true;
    }

    public boolean isVipDisplayed (WebElement job) {
        try {
            return job.findElement(By.cssSelector(VIPJob)).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isExternalLink (WebElement job) {
        try {
            return job.findElement(By.cssSelector(externalLink)).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
}
