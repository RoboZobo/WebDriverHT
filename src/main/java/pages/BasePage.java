package pages;

import components.LoginOnPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Getter
public abstract class BasePage extends PageFactory {

    protected WebDriver driver;
    protected Actions actions;

    public BasePage() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        this.driver = new ChromeDriver(chromeOptions);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    public void openPage(String URL) {
        driver.get(URL);
    }

    public void clickWithJavascriptOnElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public WebElement waitForElement(WebElement element, int waitInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(waitInSeconds)).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementDisappearing(WebElement element, int waitInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(waitInSeconds)).until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForOneOfElements(String firstElementLocator, String secondElementLocator, int waitInSeconds) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitInSeconds));
        wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstElementLocator)),
                ExpectedConditions.visibilityOfElementLocated(By.xpath(secondElementLocator))));
    }

    public String getTitleOfPage() {
        return driver.getTitle();
    }

    public void closePage() {
        driver.close();
    }

    public static void cleanInputElement(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
    }

    public void cleanInputElementWithAction(WebElement element) {
        actions.click(element).doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE).build().perform();
    }

    public void sendKeysWithAction(WebElement element, String message) {
        actions.click(element).doubleClick().sendKeys(message).build().perform();
    }
}
