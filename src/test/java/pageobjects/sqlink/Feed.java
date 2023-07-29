package pageobjects.sqlink;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

import java.util.List;

public class Feed extends BasePage {
    @FindBy (css = "#searchResultsList>div")
    List<WebElement> jobs;
    @FindBy (css = ".article>a")
    List<WebElement> jobsTitles;
    @FindBy (css = ".action #sendPopupCVinner")
    List<WebElement> sendCVBtns;
    @FindBy (css = ".uploadCV .file>input")
    WebElement uploadCVBtn;
    @FindBy (css = ".uploadButton")
    WebElement sendCVBtn;
    @FindBy (css = ".arrow.arrowLeft >a")
    WebElement leftArrow;
    @FindBy (css = "#menu_toogle")
    WebElement hamburgerMenuBtn;
    public Feed(WebDriver driver) {
        super(driver);
    }

    public boolean sendCVByKeys (String jobKeys[]) {
        waitFor(jobs);
        for (int i = 0; i<jobs.size(); i++) {
            scrollIntoView(jobsTitles.get(i));
            for (int j =0; j<jobKeys.length; j++) {
                if (jobsTitles.get(i).getText().contains(jobKeys[j])) {
                    click(sendCVBtns.get(i));
                    uploadCV();
                    break;
                }
            }
        }
        if (leftArrow.isDisplayed()) {
            scrollIntoView(leftArrow);
            click(leftArrow);
            return sendCVByKeys(jobKeys);
        } else {
            return true;
        }
    }

    public boolean uploadCV() {
        uploadCVBtn.sendKeys("C:\\Users\\jonmi\\Desktop\\קבצי קוח\\(A) CV - Jonathan Mizrahi.docx");
        click(sendCVBtn);
        backButton();
        waitFor(hamburgerMenuBtn);
        waitFor(jobs);
        click(hamburgerMenuBtn);
        click(hamburgerMenuBtn);
        return true;
    }
}
