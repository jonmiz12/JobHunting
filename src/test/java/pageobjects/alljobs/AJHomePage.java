package pageobjects.alljobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

public class AJHomePage extends BasePage {

    @FindBy (css = "#_hjSafeContext_47627041")
    WebElement overlayFrame;
    @FindBy (css = "#ZA_CAMP_BUBBLE_CLOSE_BUTTON_CID_80959")
    WebElement overlayXBtn;
    public AJHomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOverlayXBtn () {
        sleep(4000);
        waitFor(overlayXBtn);
        click(overlayXBtn);
    }

}
