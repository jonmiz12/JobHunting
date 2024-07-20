package pageobjects.alljobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

public class AJHeader extends BasePage {

    @FindBy (css = ".login-section")
    WebElement openLoginOverlayBtn;
    @FindBy (css = "#nav-item-user-area")
    WebElement userDropdown;
    @FindBy (css = "#nav-item-user-area .sub-menu-container>a:nth-child(4)")
    WebElement agentsBtn;
    @FindBy (css = "#_userName")
    WebElement userName;

    public AJHeader(WebDriver driver) {
        super(driver);
    }

    public boolean clickLogin() {
        waitFor(openLoginOverlayBtn);
        click(openLoginOverlayBtn);
        return true;
    }

    public void clickAgents (String agentsUrl){
        sleep(5000);

//        waitFor(userDropdown);
//        movetTo(userDropdown);
//        waitFor(agentsBtn);
//        click(agentsBtn);
    }

    public boolean isUserNameMatch(String expectedUserName) {
        waitFor(userName);
        String actualUserName = userName.getText();
        return actualUserName.equals(expectedUserName);
    }
}
