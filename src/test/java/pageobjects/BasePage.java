package pageobjects;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

import java.util.List;

import java.util.Set;


public class BasePage {
	WebDriver driver;
	JavascriptExecutor js; 
	Actions actions;
	WebDriverWait wait;
	int time = 5;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		js=(JavascriptExecutor)driver;
		PageFactory.initElements(driver, this);
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
	}

	public void fillText (WebElement el, String text) {
		highlightElement(el, "lightblue");
		el.clear();
		el.sendKeys(text);
	}

	public void switchToFrame (WebElement frameEl) {
		driver.switchTo().frame(frameEl);
	}
	
	public void movetTo (WebElement el) {
		actions.moveToElement(el).build().perform();
	}
	
	public void sleep (long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}

	public void waitFor(WebElement el) {
		wait.until(ExpectedConditions.elementToBeClickable(el));
	}

	public void waitFor (WebElement[] els) {
		try {
			waitFor(els[0]);
		} catch (Exception e) {}
		for (WebElement el : els ) {
			try {
				if(el.isDisplayed()){
					break;
				}
			} catch (Exception e){}
		}
	}

	public boolean waitForBool (WebElement el) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean findInElement (WebElement father, String childString) {
		WebElement child = null;
		try {
			child = father.findElement(By.cssSelector(childString));
		} catch (Exception e) {
			return false;
		}
		return isDisplayedBool(child);
	}

	public boolean isDisplayedBool(WebElement el) {
		try {
			el.isDisplayed();
		} catch (Exception e) {return false;}
		return true;
	}
	
	public void waitFor(List<WebElement> el) {
		wait.until(ExpectedConditions.visibilityOfAllElements(el));
	}

	public boolean scrollIntoView(WebElement el) {
		try {
			js.executeScript("arguments[0].scrollIntoView({block: \"center\"});", el);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean scrollToMidView (WebElement el) {
		try {
			js.executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center' });", el);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean scrollIntoView(List<WebElement> els) {
		try {
			js.executeScript("arguments[0].scrollIntoView(true);", els);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForElementToDisappear(WebElement el) {
		int counter = 0;
		while (el!=null && counter<15){
			sleep(200);
			counter++;
		}
	}

	public void waitForScrollTo(List<WebElement> els) {
		waitFor(els);
		scrollIntoView(els);
	}

	public boolean waitForElementAttribute(WebElement el, String attribute, String value) {
		int counter = time*5;
		while (counter!=0) {
			if (el.getAttribute(attribute).contains(value)) {
				return true;
			}
			sleep(200);
			counter--;
		}
		return false;
	}
	
	public void click(WebElement el) {
		highlightElement(el, "yellow");
		el.click();
	}
	
	public void assertEquals (String actual, String expected) {
		Assert.assertEquals(actual, expected);
	}
	
	public void assertEquals (Boolean actual, Boolean expected) {
		Assert.assertEquals(actual, expected);
	}

	public boolean isXwindows(WebDriver driver, String originalWindow) {
		boolean popup=false;
		if (driver.getWindowHandles().size()>1) {
			int size = driver.getWindowHandles().size();
			Set<String> windowHandles = driver.getWindowHandles();
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
				if (!handle.equals(originalWindow)) {
					driver.close();
					popup=true;
				}
			}
			driver.switchTo().window(originalWindow);
		}
		sleep(2000);
		return popup;
	}
	
	public void assertEquals (int actual, int expected) {
		Assert.assertEquals(actual, expected);
	}
	
	private void highlightElement(WebElement element, String color) {
		String originalStyle = element.getAttribute("style");
		String newStyle = "border: 3px solid blue; background-color:" + color + ";" + originalStyle;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '" + newStyle + "');},0);", element);
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '" + originalStyle + "');},200);", element);
	}

	public void refresh() {
		driver.navigate().refresh();
	}

	public byte[] takeScreenshotAsByteArray() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public void takeScreeshot() {
		byte[] screenshot1 = takeScreenshotAsByteArray();
		attachScreenshot("Step 1 Screenshot", screenshot1);
	}

	public void backButton() {
		driver.navigate().back();
	}

	@Attachment(value = "{name}" + "", type = "image/png")
	public byte[] attachScreenshot(String name, byte[] screenshot) {
		return screenshot;
	}
}
