package utils;

import io.qameta.allure.Attachment;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ListenerClass extends TestListenerAdapter {

    @Attachment
    static byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void onTestFailure(ITestContext context) {
        Object webDriverAttribute = context.getAttribute("WebDriver");
        if (webDriverAttribute instanceof WebDriver) {
            attachScreenshot((WebDriver) webDriverAttribute);
        }
    }

}
