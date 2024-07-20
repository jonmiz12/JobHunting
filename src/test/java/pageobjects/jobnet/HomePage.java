package pageobjects.jobnet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends Header {
    @FindBy (css = ".dropdown.mobileHidden")
    WebElement privateZoneBtn;
    @FindBy (css = "#loginmyprofile>span")
    WebElement openLoginOverlayBtn;
    @FindBy (css = "#emailBlock")
    WebElement fieldEmail;
    @FindBy (css = "#passwordBlock")
    WebElement fieldPassword;
    @FindBy (css = ".submit")
    WebElement loginBtn;
    @FindBy (css = "#searchbtn")
    WebElement searchBtn;
    @FindBy (css = ".radius")
    WebElement openJobFieldsBtn;
    @FindBy (css = "#mainP646")
    List<WebElement> jobFields;
    @FindBy (css = ".cx_drop_list2>ul>li")
    List<WebElement> searchFields;
    static final String subSearchFields = ".cx_drop_menu>div>label";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean login (String email, String password, String userName) {
        waitFor(openJobFieldsBtn);
        sleep(200);
        click(openLoginOverlayBtn);
        sleep(200);
        waitFor(fieldEmail);
        fillText(fieldEmail, email);
        fillText(fieldPassword, password);
        sleep(3000);
        click(loginBtn);
        waitFor(openLoginOverlayBtn);
        String name = privateZoneBtn.getText();
        return privateZoneBtn.getText().contains(userName);
    }

    public boolean isValidSearchQuary() {
        click(openJobFieldsBtn);
        for (WebElement jobField:jobFields) {
            if (jobField.getText().contains("אבטחת איכות")) {
                click(jobField);
            }
        }
        return false;
    }

    public boolean search(String searchField, String[] subSearchFields, WebDriver driver) {
        click(openJobFieldsBtn);
        if (!addFieldsAndSubFields(searchField, subSearchFields)){
            return false;
        }
        click(openJobFieldsBtn);

        click(searchBtn);
        FeedPage fp = new FeedPage(driver);
        return fp.assertSearchfield(searchField);
    }

    public boolean clickSubSearchFields (String[] subSearchFieldsArray, WebElement father) {
        List<WebElement> subSearchFieldsList = father.findElements(By.cssSelector(subSearchFields));
        int counter=0;
        String subFieldText;
        waitFor(subSearchFieldsList);
        for (WebElement subFieldEl:subSearchFieldsList) {
            for (String reqField:subSearchFieldsArray) {
                subFieldText = subFieldEl.getText();
                if (subFieldEl.getText().contains(reqField)) {
                    click(subFieldEl);
                    counter++;
                }
            }
        }
        return counter!=subSearchFieldsArray.length+1;
    }

    public boolean addFieldsAndSubFields (String searchField, String[] subSearchFields) {
        for (WebElement field:searchFields) {
            if (field.getText().contains(searchField)) {
                click(field);
                return clickSubSearchFields(subSearchFields, field);
            }
        }
        return false;
    }
}
