package pageobjects.drushim;

import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Jobs extends Header {
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
    @FindBy (css = ".agent-btn")
    List<WebElement> agents;
    @FindBy (css = ".col .mdi-chevron-right.theme--light")
    List<WebElement> agentsSlideBtns;
    @FindBy (css = ".loading-page")
    WebElement loadingPageOverlay;


    public Jobs(WebDriver driver) {
        super(driver);
    }
    
    public void selectAgent(String agentName) {
        for (int p = 0; p < agents.size(); p++) {
            if (StringUtils.containsIgnoreCase(agents.get(p).getText(), agentName)) {
                if (agents.get(p).isDisplayed()) {
                    click(agents.get(p));
                    break;
                } else {
                    for (WebElement btn : agentsSlideBtns) {
                        if (btn.isEnabled()) {
                            click(btn);
                            click(agents.get(p));
                            waitForELementToDisappear(loadingPageOverlay);
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean findJobAndSendCVByContent(String[] jobsKeys, String [] agentNames) {
        for (int g = 0; g<agentNames.length; g++) {
            if (g==0){continue;}
            selectAgent(agentNames[g]);
            for (int i = 0; i < jobsTitlesElements.size(); i++) {
                if (isJobValid(jobsKeys, i)) {
                    click(jobsSendCVBtn.get(i));
                    waitFor(jobTitle);
                    sendCV(String.valueOf(i));
                    click(jobReduce);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isJobValid(String[] jobsKeys, int i) {
        if (jobsTitlesElements.get(i).isDisplayed()) {
            scrollIntoView(jobsTitlesElements.get(i));
            if (!isSendEnabledAndSentDisplayed(jobsTitlesElements.get(i), jobsSendCVBtn.get(i))) {
                return false;
            }
            return isTitleMatchSoExpand(jobsKeys, i);
        } else {
            try {
                click(loadMoreJobs);
            } catch (Exception e) {
                return true;
            }
            sleep(1500);
            return isJobValid(jobsKeys, i);
        }
    }

    private Boolean isTitleMatchSoExpand(String[] jobsKeys, int i) {
        for (String jobkey:jobsKeys) {
            if (matchTexts(jobsTitlesElements.get(i), jobkey)) {
                click(jobsExpandBtn.get(i));
                waitFor(jobDesc);
                return true;
            }
        }
        return false;
    }

    private boolean matchTexts(WebElement el, String jobKey) {
        return StringUtils.containsIgnoreCase(el.getText(), jobKey);
    }

    public boolean sendCV(String jobNum) {
        if (!selectEnglishCV()) {
            return false;
        }
        click(jobSendCVBtn);
        waitFor(appliedJobXBtn);
        click(appliedJobXBtn);
        takeScreeshot();
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

    public boolean isSendEnabledAndSentDisplayed(WebElement el, WebElement sendCVBtn) {
        return !isSentCVDisplayed(el) &&
                isEnabled(sendCVBtn);
    }
}
