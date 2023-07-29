package pageobjects.drushim;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

public class Header extends BasePage {

    @FindBy(css = "#mobile-smart-btn")
    WebElement smartAgentBtn;

    public Header(WebDriver driver) {
        super(driver);
    }
    public void clickSmartAgent () {
        click(smartAgentBtn);
    }
}
