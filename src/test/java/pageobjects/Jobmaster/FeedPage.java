package pageobjects.Jobmaster;

import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FeedPage extends Header{

    @FindBy (css = "[id^=\"misra\"]")
    List<WebElement> jobs;
    @FindBy (css = "[id^=\"misra\"] .CardHeader")
    List<WebElement> jobTitles;
    @FindBy (css = "[id^=\"misra\"] .bottomItemsSend")
    List<WebElement> jobsSendCVBtns;
    @FindBy (css = ".paging")
    List<WebElement> movePageBtns ;
    @FindBy (css = "#jobFullDetails")
    WebElement jobDesc;
    @FindBy (css = "#q")
    WebElement fieldSearch;
    @FindBy (css = ".red")
    WebElement submit;

    public FeedPage(WebDriver driver) {
        super(driver);
    }

    public void sendCVByKeys(String[] jobKeys, String CVName, String previewLetter, WebDriver driver) {
        waitFor(jobTitles);
        for (int i = 0; i < jobs.size(); i++) {
            for (int j = 0; j < jobKeys.length; j++) {
                if (jobTitles.get(i).getText().contains(jobKeys[j])) {
                    SendCVOverlay cvo = new SendCVOverlay(driver);
                    click(jobsSendCVBtns.get(i));
                    sleep(1000);
                    if (cvo.sendCV(CVName, previewLetter)) {
                        scrollIntoView(jobTitles.get(i));
                        takeScreeshot();
                    }
                }
            }
        }
        waitFor(movePageBtns);
        for (int k = movePageBtns.size()-1; k > -1; k--) {
            if (movePageBtns.get(k).getText().contains("הבא")) {
                click(movePageBtns.get(k));
                sendCVByKeys(jobKeys, CVName, previewLetter, driver);
            }
        }
    }

    public void search(String searchQuary) {
        fillText(fieldSearch, searchQuary);
        click(submit);
    }
    public void searchByArrayAndCallSendCV(String searchQuary[], String[] jobKeys, String CVName, String previewLetter, WebDriver driver) {
        for (int i = 0; i<searchQuary.length; i++) {
            if (i!=0) {
                search(searchQuary[i]);
                sleep(2000);
            }
            sendCVByKeys(jobKeys, CVName, previewLetter, driver);
        }
    }
}
