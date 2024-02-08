package pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class EclipsoEuMailPage extends BasePage{

    @FindBy(name = "email_full")
    public WebElement loginInputField;

    @FindBy(name = "password")
    public WebElement passwordInputField;

    @FindBy(xpath = "//button[@id='loginForm']/i")
    public WebElement signInButton;

    @FindBy(xpath = "//a[@aria-label='allow cookies']")
    public WebElement acceptCookies;

    @FindBy(xpath = "//div[contains(@class,'modal-dialog')]")
    public WebElement noticeMessage;

    @FindBy(xpath = "//div[@class='dropdownNavbar']//a[@class='logo']")
    public WebElement menu;

    @FindBy(xpath = "//a[@title='Email']")
    public WebElement mailIcon;

    @FindBy(xpath = "//tr//div[@class='sender unread']")
    public WebElement unreadMail;

    @FindBy(xpath = "//div[@class='previewMailHeader']//div[2]/a[1]")
    public WebElement sender;

    @FindBy(xpath = "//iframe[@id='textArea']")
    public WebElement iframeMessage;

    @FindBy(xpath = "//div[contains(@id,'MailText')]")
    public WebElement iframeTextMessage;

}
