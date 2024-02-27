package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.extern.java.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GetScreenShot;
import util.GetScreenShotDecorator;

import java.time.Duration;
import java.util.List;

@Getter
@Log
public abstract class BasePage extends PageFactory {
    protected Actions actions;
    protected WebDriver driver;

    protected GetScreenShot getScreenShot;

    //Factory method
    protected WebDriver createDriver(String driverName) {
        if ("firefox".equalsIgnoreCase(driverName)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);
            return new FirefoxDriver(options);
        } else if ("chrome".equalsIgnoreCase(driverName)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);
            return new ChromeDriver(options);
        } else {
            throw new IllegalArgumentException("Invalid driver name: " + driverName);
        }
    }

    public BasePage(String driverName) {
        this.driver = createDriver(driverName);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        getScreenShot = new GetScreenShot(driver);
    }

    public void openPage(String URL) {
        driver.get(URL);
    }

    public void clickWithJavascriptOnElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void removeAdsWithJavascript(String textInAdsClasses) {
        ((JavascriptExecutor) driver).executeScript("var elements = document.querySelectorAll(`[class*=\"" + textInAdsClasses + "\"]`); for (var i = 0; i < elements.length; i++) {elements[i].style.display = 'none';};");
    }

    public WebElement waitForElement(WebElement element, int waitInSeconds) {
        new FluentWait<>(driver).withTimeout(Duration.ofSeconds(waitInSeconds))
                .until(e ->
                    isDisplayedElementIgnoringStaleReferenceException(element));
        return element;
    }

    public void waitForElementWithClick(WebElement element, WebElement elementForClick, int waitInSeconds, int repeatEveryNSeconds) {
        new FluentWait<>(driver).withTimeout(Duration.ofSeconds(waitInSeconds))
                .pollingEvery(Duration.ofSeconds(repeatEveryNSeconds))
                .ignoring(NoSuchElementException.class)
                .until(e -> {
                    elementForClick.click();
                    return isDisplayedElementIgnoringStaleReferenceException(element);
                });
    }

    private Boolean isDisplayedElementIgnoringStaleReferenceException(WebElement element) {
        boolean isDisplayedElement = false;
        try {
            isDisplayedElement = element.isDisplayed();
        } catch (StaleElementReferenceException err) {
            log.info("Element is NOT displayed yet" + err);
        } finally {
            return isDisplayedElement;
        }
    }

    public void waitForElementsDisappearing(List<WebElement> element, int waitInSeconds) {
        new WebDriverWait(driver, waitInSeconds).until(ExpectedConditions.invisibilityOfAllElements(element));
    }

    public void waitForOneOfElements(String firstElementLocator, String secondElementLocator, int waitInSeconds) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitInSeconds).getSeconds());
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

    public String createDecoratedScreenShot(String name) {
        return new GetScreenShotDecorator(getScreenShot).capture(name);
    }
}
