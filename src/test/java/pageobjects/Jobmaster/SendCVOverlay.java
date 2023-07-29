package pageobjects.Jobmaster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

import java.util.List;

public class SendCVOverlay extends BasePage {

    @FindBy (css = ".FileNameSpan")
    List<WebElement> CVs;
    @FindBy (css = ".editOptions")
    WebElement addPreviewLetterBtn;
    @FindBy (css = "#letter")
    WebElement fieldPreviewLetter;
    @FindBy (css = ".WithCancel")
    WebElement sendCVBtn;
    @FindBy (css = "#modal_window .bttn[value=\"סגור\"]")
    WebElement overSentCloseeBtn;
    @FindBy (css = ".FilterQuestions")
    List<WebElement> jobQuestions;
    @FindBy (css = ".CancelButton")
    WebElement jobCacnelBtn;

    public SendCVOverlay(WebDriver driver) {
        super(driver);
    }

    public boolean sendCV(String CVName, String previewLetter) {
        try {
            waitFor(overSentCloseeBtn);
            click(overSentCloseeBtn);
            return false;
        }catch (Exception e) {
            try {
                if (jobQuestions.get(0).isDisplayed()) {
                    click(jobCacnelBtn);
                    return false;
                }
            }catch (Exception ex) {}
        }
            for (int i = 0; i < CVs.size(); i++) {
                if (CVs.get(i).getText().contains(CVName)) {
                    click(CVs.get(i));
                    click(addPreviewLetterBtn);
                    fillText(fieldPreviewLetter, previewLetter);
                    click(sendCVBtn);
                }
            }
        return true;
    }
}
