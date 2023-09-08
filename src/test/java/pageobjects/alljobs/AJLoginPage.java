package pageobjects.alljobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

public class AJLoginPage extends BasePage {

    @FindBy (css = "#inputEmail")
    WebElement fieldEmail;
    @FindBy (css = "#inputPassword")
    WebElement fieldPassword;
    @FindBy (css = ".btn-log-in")
    WebElement loginBtn;
    @FindBy (css = "#signin_iframe")
    WebElement loginFrame;
    @FindBy (css = "a[href=\"javascript:history.go(-1)\"]")
    WebElement blockedBackButton;
    public AJLoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean login (String email, String password) {
        isAutoBlocked();
        waitFor(fieldEmail);
        fillText(fieldEmail, email);
        fillText(fieldPassword, password);
        click(loginBtn);
        return true;
    }

    public boolean isAutoBlocked () {
        try {
            switchToFrame(loginFrame);
            waitFor(loginBtn);
        } catch (Exception e) {
            return blockedBackButton.isDisplayed();
        }
        return true;
    }
}
