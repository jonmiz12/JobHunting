package pageobjects.drushim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends Header {

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
    @FindBy (css = ".agents-table-list>div:not(:first-child):not(:last-child)")
    List<WebElement> agentsList;
    @FindBy (css = "#closeBthn")
    WebElement closeHstgOverlay;
    final static String goToAgentArrow = " .mdi-chevron-left";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getUserName(){
        String name = userName.getText();
        return name;
    }

    public boolean closeHstgOverlay () {
        try {
            waitFor(closeHstgOverlay);
            click(closeHstgOverlay);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String login (String email, String password, String userName) {
        if (!getUserName().equals(userName)) {
            click(loginWindowBtn);
            fillText(fieldEmail, email);
            fillText(fieldPassword, password);
            click(loginBtn);
            sleep(3000);
        }
        int counter = 20;
        while (getUserName().equals("התחברות") && counter!=0){
            sleep(200);
            counter--;
        }
        sleep(2000);
        String u = getUserName();
        return u;

    }

    public boolean clickAgentByContent (String agentName) {
        waitForScrollTo(agentsList);
        for (int i=0; i<agentsList.size(); i++) {
            if (agentsList.get(i).getText().contains(agentName)) {
                WebElement agentArrow = agentsList.get(i).findElement(By.cssSelector(goToAgentArrow));
                scrollToMidView(agentArrow);
                waitFor(agentArrow);
                click(agentArrow);
                sleep(2000);
                return true;
            }
        }
        return false;
    }
}
