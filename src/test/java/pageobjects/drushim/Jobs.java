package pageobjects.drushim;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

import java.util.List;

public class Jobs extends BasePage {
    @FindBy (css = ".inner-wrap.px-2 [role=\"list\"]")
    List<WebElement> jobElements;
    @FindBy (css = ".job-url.primary--text.font-weight-medium.primary--text")
    List<WebElement> jobsTitlesElements;
    @FindBy (css = ".pointer.pc-view")
    List<WebElement> jobsExpandBtn;
    @FindBy (css = ".align-baseline.wrap>div:nth-child(2)")
    List<WebElement> jobsExp;
    @FindBy (css = ".px-4.pointer")
    WebElement jobDesc;
    @FindBy (css = ".px-4.pointer")
    WebElement jobReduce;
    @FindBy (css = ".inner-link")
    List<WebElement> jobsSendCVBtn;
    @FindBy (css = "#submitApply")
    WebElement jobSendCVBtn;
    @FindBy (css = ".display-30.font-weight-medium.mb-0")
    WebElement jobTitle;
    @FindBy (css = "div[tabindex=\"0\"][role=\"document\"] .layout>div[class=\"flex mr-4\"]>p[class=\"display-18 mb-0\"]")
    WebElement cvTitle;
    @FindBy (css = ".cv-short-file>div>div>div>div:nth-child(2)>p:nth-child(1)")
    List<WebElement> cvTitles;
    @FindBy (css = "div[tabindex=\"0\"][role=\"document\"] .primary")
    WebElement showAllCV;
    final static String sent = ".display-24.pt-0.lightGreen--text";
    @FindBy (css = ".material-icons.theme--dark.primary--text")
    WebElement appliedJobXBtn;
    @FindBy (css = ".narrow.mb-6>span")
    WebElement loadMoreJobs;
    @FindBy (css = ".text--large")
    WebElement jobXBth;

    public Jobs(WebDriver driver) {
        super(driver);
    }

    public boolean findJobAndSendCVByContent(String[] jobsKeys) {
        Boolean expanded = false;
        String jobTitleString;
        int loadMore = 0;
        for (int i = 0; i<jobsTitlesElements.size(); i++) {
            if (jobsTitlesElements.get(i).isDisplayed()) {
                jobTitleString = jobsTitlesElements.get(i).getText();
                scrollIntoView(jobsTitlesElements);
                for (int k = 0; k<jobsKeys.length; k++) {
                    if (isSendEnabledTitleMatchAndSentDisplayed(jobsTitlesElements.get(i), jobsSendCVBtn.get(i), jobsKeys[k])) {
                        click(jobsExpandBtn.get(i));
                        expanded = true;
                        waitFor(jobDesc);
                    }
                }
                if (expanded) {
                    for (int j = 0; j < jobsKeys.length; j++) {
                        if (jobDesc.getText().contains(jobsKeys[j])) {
                            click(jobsSendCVBtn.get(i));
                            waitFor(jobTitle);
                            sendCV(String.valueOf(i));
                        }
                    }
                    click(jobReduce);
                    expanded = false;
                }
            }else {
                if (!scrollIntoView(loadMoreJobs)) {
                    System.out.println(loadMore);
                    return true;
                }
                loadMore++;
                click(loadMoreJobs);
                sleep(1500);
            }
        }
        return false;
    }

    public boolean sendCV(String jobNum) {
        if (!selectEnglishCV()) {
            return false;
        }
        click(jobSendCVBtn);
        sleep(3000);
        click(appliedJobXBtn);
        takeScreeshot(jobNum);
        return true;
    }

    public boolean selectEnglishCV() {
        click(showAllCV);
        for (int i = 0; i < cvTitles.size(); i++) {
            if (cvTitles.get(i).getText().equals("(A) CV - Jonathan Mi")) {
                click(cvTitles.get(i));
                return true;
            }
        }
        return false;
    }

    public void switchCV(String jobTitle) {
        click(showAllCV);
        for (int i = 0; i<cvTitles.size(); i++) {
            if (!cvTitles.get(i).equals(jobTitle)) {
                click(cvTitles.get(i));
            }
        }
    }

    public boolean isSentCVDisplayed(WebElement job) {
        try {
            job.findElement(By.cssSelector(sent)).getText().contains("שלחת");
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean isEnabled (WebElement el) {
        return el.isEnabled();
    }

    public boolean isSendEnabledTitleMatchAndSentDisplayed(WebElement el, WebElement sendCVBtn, String jobKey) {
        return el.getText().contains(jobKey) &&
                !isSentCVDisplayed(el) &&
                isEnabled(sendCVBtn);
    }

    public void takeScreeshot(String jobTitle) {
        byte[] screenshot1 = takeScreenshotAsByteArray();
        attachScreenshot("Step 1 Screenshot", screenshot1);
    }

    @Attachment(value = "{name}", type = "image/png")
    public byte[] attachScreenshot(String name, byte[] screenshot) {
        return screenshot;
    }
}
