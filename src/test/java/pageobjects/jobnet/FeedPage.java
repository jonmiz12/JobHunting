package pageobjects.jobnet;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.OptionalInt;

public class FeedPage extends Header{

    @FindBy (css = ".inerbox")
    List<WebElement> jobs;
    @FindBy (css = "[itemprop=\"title\"]")
    List<WebElement> jobTitles;
    @FindBy (css = "#ucSendCV_divDistributeS>label")
    List<WebElement> distributionCheckboxes;
    @FindBy (css = "[data-target=\"#cvupload\"]:not(#btnopencv)")
    List<WebElement> jobsApplyCVBtn;
    @FindBy (css = "#ucSendCV_txtRemarks")
    WebElement fieldPreviewLetter;
    @FindBy (css = ".sendcvSubmit")
    WebElement send;
    @FindBy (css = ".modal-header .close")
    WebElement closePrompt;
    @FindBy (css = "#ContentPlaceHolder1_ucSearhRes_rptPaging_btnPage_6")
    WebElement nextPage;
    @FindBy (css = ".breadcrumbs>ol>li")
    List<WebElement> searchTree;
    @FindBy (css = ".btnPaging:last-child")
    WebElement lastPagingBtn;
    @FindBy (css = "#cvupload .close-popup")
    WebElement uploadOverlayXBtn;
    @FindBy (css = "#frmSendCv")
    WebElement sendCVFrame;
    @FindBy (css = "[id^=\"ucSendCV_dlQuestions_divRBQ_\"]")
    List<WebElement> questions;
    @FindBy (css = "#cvupload .close-popup")
    WebElement applyOverlayXBtn;
    @FindBy (css = "#ucSendCV_ddlFiles>option")
    List<WebElement> CVs;
    @FindBy (css = "#deleteCookie")
    WebElement exposeSelectCV;
    @FindBy (css = ".modal-content .bootstrap-dialog-body")
    WebElement sendError;

    public FeedPage(WebDriver driver) {
        super(driver);
    }

    public boolean findJobAndSendCVByContent (String[] jobsKeys, String previewLetter, String[] distributions, WebDriver driver, String CV) {
        int size = jobs.size();
        for (int i=0; i<jobs.size(); i++) {
            if (jobIsValid(jobsKeys, i)) {
                scrollIntoView(jobs.get(i));
                String originalWindow = driver.getWindowHandle();
                click(jobsApplyCVBtn.get(i));
                if (isXwindows(driver, originalWindow) && applyOverlayXBtn.isDisplayed()) {
                    click(applyOverlayXBtn);
                    sleep(500);
                    click(jobsApplyCVBtn.get(i));
                }
                fillApplyOverlay(previewLetter, distributions, driver, CV);
            }
        }
        if (!lastPagingBtn.getAttribute("class").contains("Selected")) {
            scrollIntoView(lastPagingBtn);
            waitFor(lastPagingBtn);
            click(lastPagingBtn);
            return findJobAndSendCVByContent(jobsKeys, previewLetter, distributions, driver, CV);
        } else {
            return true;
        }
    }

    public boolean jobIsValid(String[] jobKeys, int i) {
        for (String jobKey:jobKeys) {
            if (StringUtils.containsIgnoreCase(jobTitles.get(i).getText(), jobKey)) {
                return true;
            }
        }
        return false;
    }

    public boolean fillApplyOverlay (String previewLetter, String[] distrubutions, WebDriver driver, String CV) {
        try {
            waitFor(sendCVFrame);
        } catch (Exception e) {return false;}
        driver.switchTo().frame("frmSendCv");
        sleep(2000);
        fillText(fieldPreviewLetter,previewLetter);
        click(exposeSelectCV);
        for (WebElement option : CVs) {
            if (option.getText().contains(CV)){
                click(option);
                break;
            }
        }
        if (distributionCheckboxes.size()!=0) {
            for (WebElement box : distributionCheckboxes) {
                for (String distribution : distrubutions) {
                    if (box.getText().contains(distribution)) {
                        click(box);
                    }
                }
            }
        }

        for (WebElement question : questions) {
            question.findElement(By.cssSelector(" td")).click();
        }
        click(send);
        WebElement[] els = new WebElement[] {closePrompt, sendError};
        waitFor(els);
        click(closePrompt);
        driver.switchTo().defaultContent();

        sleep(700);
        driver.switchTo().defaultContent();
        takeScreeshot();
        return true;
    }

    public boolean assertSearchfield (String field) {
        waitFor(searchTree);
        int size = searchTree.size();
        if (searchTree.size()!=4) {return false;}
        for (int i=0; i<searchTree.size(); i++) {
            switch (i) {
                case 0 :
                    if (!searchTree.get(i).getText().contains("דף הבית")){return false;}
                    break;
                case 1 :
                    if (!searchTree.get(i).getText().contains("דרושים לפי תחומים")){return false;}
                    break;
                case 2 :
                    if (!searchTree.get(i).getText().contains(field)){return false;}
                    break;
                case 3 :
                    return true;
            }
        }
        return false;
    }
}
