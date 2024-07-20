package pageobjects.Jobmaster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.swing.*;

public class HomePage extends Header {

    @FindBy (css = ".title")
    WebElement userName;
    @FindBy (css = "#q")
    WebElement fieldSearch;
    @FindBy (css = ".red")
    WebElement searchBtn;
    @FindBy (css = "#h1Header")
    WebElement searchTerm;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getUserName () {
        sleep(3000);
        waitFor(userName);
        String actualName = userName.getText();
        return actualName;
    }

    public boolean search (String searchQuary) {
        fillText(fieldSearch, searchQuary);
        click(searchBtn);
        return searchTerm.getText().contains(searchQuary);
    }
}
