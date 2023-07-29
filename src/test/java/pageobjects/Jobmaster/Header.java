package pageobjects.Jobmaster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

public class Header extends BasePage {

    @FindBy (css = ".TopSargelWhiteItem.TopIconMe")
    WebElement loginBtn;

    public Header(WebDriver driver) {
        super(driver);
    }

    public void clickLoginBtn () {
        click(loginBtn);
    }
}
