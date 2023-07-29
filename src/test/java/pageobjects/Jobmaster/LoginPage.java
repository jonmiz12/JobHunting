package pageobjects.Jobmaster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

import java.sql.Driver;

public class LoginPage extends BasePage {
    @FindBy (css = "#email")
    WebElement fieldEmail;
    @FindBy (css = "#password")
    WebElement fieldPassword;
    @FindBy (css = ".bttnBig")
    WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean login (String email, String password, String fullName, WebDriver driver) {
        fillText(fieldEmail, email);
        fillText(fieldPassword, password);
        click(loginBtn);
        HomePage hp = new HomePage(driver);
        return hp.getUserName().equals(fullName);
    }
}
