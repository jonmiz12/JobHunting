package pageobjects.drushim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;

import java.util.List;

public class Header extends BasePage {

    @FindBy(css = "#mobile-smart-btn")
    WebElement smartAgentBtn;
    @FindBy (css = ".user-icon")
    WebElement profileBtn;
    @FindBy (css = ".flex.xs12.mb-2")
    List<WebElement> profileMenuBtns;

    public Header(WebDriver driver) {
        super(driver);
    }
    public void clickMenuItemByName (String itemText) {
        if (!profileMenuBtns.get(0).isDisplayed()){
            click(profileBtn);
        }
        waitFor(profileMenuBtns);
        for (int i=0; i< profileMenuBtns.size(); i++) {
            if (profileMenuBtns.get(i).getText().contains(itemText)){
                sleep(3000);
                click(profileMenuBtns.get(i).findElement(By.cssSelector("a")));
            }
        }
    }
}
