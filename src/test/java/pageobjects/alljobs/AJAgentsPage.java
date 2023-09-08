package pageobjects.alljobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.drushim.Header;

public class AJAgentsPage extends AJHeader {
    @FindBy (css = ".feed-link")
    WebElement feedBtn;
    public AJAgentsPage(WebDriver driver) {
        super(driver);
    }

    public void clickFeed () {
        waitFor(feedBtn);
        click(feedBtn);
    }
}
