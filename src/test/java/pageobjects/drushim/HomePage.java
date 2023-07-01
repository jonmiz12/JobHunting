package pageobjects.drushim;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;
import java.util.List;

public class HomePage extends BasePage {

    @FindBy (css = ".login-text")
    WebElement userName;
    @FindBy (css = "#email-login-field")
    WebElement fieldEmail;
    @FindBy (css = "#password-login-field")
    WebElement fieldPassword;
    @FindBy (css = "#user-menu-wrapper")
    WebElement loginWindowBtn;
    @FindBy (css = "#submit-login-btn")
    WebElement loginBtn;
    @FindBy (css = "#mobile-smart-btn")
    WebElement smartAgentBtn;
    @FindBy (css = ".agents-table-list>div:not(:first-child):not(:last-child)")
    List<WebElement> agentsList;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getUserName(){
        String name = userName.getText();
        return name;
    }

    public boolean login (String email, String password, String userName) {
        if (!getUserName().equals(userName)) {
            click(loginWindowBtn);
            fillText(fieldEmail, email);
            fillText(fieldPassword, password);
            click(loginBtn);
            sleep(3000);
        }
        while (getUserName().equals("התחברות")){
            sleep(200);
        }
        return getUserName().equals(userName);

    }

    public void clickSmartAgent () {
        click(smartAgentBtn);
    }

    public boolean clickAgentByContent (String agentName) {
        waitForScrollTo(agentsList);
        for (int i=0; i<agentsList.size(); i++) {
            if (agentsList.get(i).getText().contains(agentName)) {
                click(agentsList.get(i));
                sleep(2000);
                return true;
            }
        }
        return false;
    }
}
