package pageobjects.drushim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Jobs extends Header {
    @FindBy (css = ".job-item")
    List<WebElement> jobElements;
    @FindBy (css = jobsTitlesElementsString)
    List<WebElement> jobsTitlesElements;
    @FindBy (css = ".pointer.pc-view")
    List<WebElement> jobsExpandBtn;
    @FindBy (css = ".align-baseline.wrap>div:nth-child(2)")
    List<WebElement> jobsExp;
    @FindBy (css = ".px-4.pointer")
    WebElement jobDesc;
    @FindBy (css = ".px-4.pointer")
    WebElement jobReduce;
    @FindBy (css = jobsSendCVBtnString)
    List<WebElement> jobsSendCVBtn;
    @FindBy (css = ".v-dialog__content.v-dialog__content--active #submitApply")
    WebElement jobSendCVBtn;
    @FindBy (css = ".cv-content")
    List <WebElement> jobContent;
    @FindBy (css = "div[tabindex=\"0\"][role=\"document\"] .primary")
    WebElement showAllCV;
    @FindBy (css = ".material-icons.theme--dark.primary--text")
    WebElement appliedJobXBtn;
    @FindBy (css = ".narrow.mb-6>span")
    WebElement loadMoreJobs;
    @FindBy (css = ".text--large")
    WebElement jobOverlayXBth;
    @FindBy (css = ".agent-btn>span")
    List<WebElement> agents;
    @FindBy (css = ".col div[class^=\"v-slide-group\"]>i:not(.v-icon--disabled)")
    WebElement activeAgentsSlideBtn;
    @FindBy (css = ".loading-page")
    WebElement loadingPageOverlay;
    @FindBy (css = ".cv-short-view .layout .mr-4>p:nth-child(1)")
    List<WebElement> expandedCVsNames;
    @FindBy (css = ".v-dialog__content--active .displaySelected .layout .display-18")
    WebElement jobOverlaySelectedCV;
    @FindBy (css = ".px-4.col.col-12")
    WebElement quetionaireBox;
    final static String jobsSendCVBtnString = ".inner-link";
    final static String jobsTitlesElementsString = ".job-url.primary--text.font-weight-medium.primary--text";
    final static String sent = ".display-24.pt-0.lightGreen--text";
    final static  String questionaireCheckbox = ".v-input--selection-controls__input";

    public Jobs(WebDriver driver) {
        super(driver);
    }
    
    public void selectAgent(String agentName) {
        for (int p = 0; p < agents.size(); p++) {
            String agentNameToComapare = agents.get(p).getText();
            if (agentNameToComapare.equals("")) {
                click(activeAgentsSlideBtn);
            }
            if (StringUtils.containsIgnoreCase(agents.get(p).getText(), agentName)) {
                if (agents.get(p).isDisplayed()) {
                    click(agents.get(p));
                    waitForElementToDisappear(loadingPageOverlay);
                    break;
                } else {
                    System.err.println("The agent " + agentName + "was not found even after allegedly scrolling");
                }
            }
        }
    }

    public boolean findJobAndSendCVByContent(String[] jobsKeys, String [] agentNames, String CV) {
        sleep(3000);
        waitFor(jobsTitlesElements.get(0));
        for (int g = 0; g<agentNames.length; g++) {
            if (g>0) {
                selectAgent(agentNames[g]);
            }
            int sendCounter = 0;
            for (int i = 0; i <= jobsTitlesElements.size(); i++) {
                if (!jobsTitlesElements.get(i).isDisplayed()) {
                    if (waitForBool(loadMoreJobs)) {
                        scrollToMidView(loadMoreJobs);
                        click(loadMoreJobs);
                        sleep(3000);
                    } else {
                        break;
                    }
                } else if (i==jobsTitlesElements.size()-1) {
                    break;
                }
                if (isJobValid(jobsKeys, i-sendCounter)) {
                    sleep(1000);
                    click(jobsSendCVBtn.get(i-sendCounter -1));
                    sleep(2000);
//                    for (int i=0; i<jobContent.size(); i++) {
//                        if (isDisplayedBool(jobContent.get(i)) {}

                    sleep(3000);
                    sendCV(String.valueOf(i), CV);
                    sendCounter ++;
                }
            }
        }
        return true;
    }
    
    public boolean isJobValid(String[] jobsKeys, int i) {
        if (jobsTitlesElements.get(i).isDisplayed()) {
            scrollIntoView(jobsTitlesElements.get(i));
            if (!isSendEnabledAndSentDisplayed(jobElements.get(i))) {
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
                return true;
            }
        }
        return false;
    }

    private boolean matchTexts(WebElement el, String jobKey) {
        String elText = el.getText();
        return StringUtils.containsIgnoreCase(elText, jobKey);
    }

    public boolean sendCV(String jobNum, String CV) {
        if (!selectCV(CV)) {
            System.err.println("'SelectCV' function failed");
            return false;
        }
        ;
        if(waitForBool(quetionaireBox)) {
            if (isDisplayedBool(quetionaireBox.findElement(By.cssSelector(questionaireCheckbox)))) {
                click(quetionaireBox.findElement(By.cssSelector(questionaireCheckbox)));
            }
        }
        click(jobSendCVBtn);
        waitFor(appliedJobXBtn);
        click(appliedJobXBtn);
        takeScreeshot();
        return true;
    }

    public boolean selectCV(String CV) {
        WebElement displayedSelectedCV = null;
        waitFor(jobOverlaySelectedCV);
        sleep(2000);
        if (jobOverlaySelectedCV.getText().contains(CV)) {
            return true;
        }
        waitFor(showAllCV);
        click(showAllCV);
        sleep(500);
        for (WebElement cv : expandedCVsNames) {
            if (cv.getText().contains(CV)) {
                click(cv);
                break;
            }
        }
        while (true) {
            try {
                if (expandedCVsNames.get(3).isDisplayed()){}
            } catch (Exception e) {
                    break;
            }
        }
        return jobOverlaySelectedCV.getText().contains(CV);
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

    public boolean isSendEnabledAndSentDisplayed(WebElement jobEl) {
        return !isSentCVDisplayed(jobEl) &&
                findInElement(jobEl, jobsSendCVBtnString);
    }
}
