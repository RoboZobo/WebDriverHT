package pages;

import lombok.Data;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Data
public class ProtonMeMailPage extends BasePage {

    public final String newMessageButtonXpathLocator = "//button[contains(text(),'New message')]";
    public final String alertMessageXpathLocator = "//div[contains(@class,'alert')]";

    @FindBy(xpath = "//input[@autocomplete='username']")
    public WebElement loginInputField;

    @FindBy(xpath = "//input[@autocomplete='current-password']")
    public WebElement passwordInputField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement signInButton;

    @FindBy(xpath = newMessageButtonXpathLocator)
    public WebElement newMessageButton;

    @FindBy(xpath = alertMessageXpathLocator)
    public WebElement alertMessage;

    @FindBy(xpath = "//div[contains(@class,'field-two-assist')]/*[name()='svg']")
    public List<WebElement> warningMessages;

    @FindBy(xpath = "//input[@placeholder='Email address']")
    public WebElement inputEmailAddress;

    @FindBy(xpath = "//input[@placeholder='Subject']")
    public WebElement inputEmailSubject;

    @FindBy(xpath = "//iframe[contains(@title,'Email composer')]")
    public WebElement messageBody;

    @FindBy(xpath = "//button[contains(@class,'send-button')]")
    public WebElement sendEmailButton;

    @FindBy(xpath = "//span[contains(@class,'notification__content')]")
    public WebElement notification;

}
