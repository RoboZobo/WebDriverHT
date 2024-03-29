package steps;

import components.LoginOnPage;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import pages.ProtonMeMailPage;

import static pages.BasePage.cleanInputElement;

public class ProtonMeSteps {

    public ProtonMeMailPage protonMeMailPage;

    public void openProtonMeMailLoginPage() {
        protonMeMailPage = new ProtonMeMailPage();
        protonMeMailPage.openPage("https://account.proton.me/login");
        protonMeMailPage.waitForElement(protonMeMailPage.getLoginInputField(), 5);
    }

    public void loginOnProtonMeMailPage(String login, String password, boolean withWait) {
        LoginOnPage.builder().loginInputField(protonMeMailPage.getLoginInputField())
                .passwordInputField(protonMeMailPage.getPasswordInputField())
                .build().clearInputLogin().inputLogin(login).clearInputPassword().inputPassword(password);
        protonMeMailPage.getSignInButton().click();
        if (withWait) {
            protonMeMailPage.waitForOneOfElements(protonMeMailPage.getAlertMessageXpathLocator(),
                    protonMeMailPage.getNewMessageButtonXpathLocator(), 17);
        }
    }

    public String getTitleOfProtonMeMailPage() {
        return protonMeMailPage.getTitleOfPage();
    }

    public void closeProtonMeMailPage() {
        protonMeMailPage.closePage();
    }

    public boolean isAlertMessageDisplayed() {
        return protonMeMailPage.getAlertMessage().isDisplayed();
    }

    public int getWarningMessages() {
        return protonMeMailPage.getWarningMessages().size();
    }

    public void clickOnNewMessageButton() {
        protonMeMailPage.getNewMessageButton().click();
        protonMeMailPage.waitForElement(protonMeMailPage.getMessageBody(), 5);
    }

    public void fillEmailAddress(String address) {
        protonMeMailPage.getInputEmailAddress().sendKeys(address);
    }

    public void fillMessageBodyWithText(String message) {
        protonMeMailPage.cleanInputElementWithAction(protonMeMailPage.getMessageBody());
        protonMeMailPage.sendKeysWithAction(protonMeMailPage.getMessageBody(), message);
    }

    public void clickOnSendMessageButton() {
        protonMeMailPage.getSendEmailButton().click();
        protonMeMailPage.waitForElementDisappearing(protonMeMailPage.getNotification(), 25);
    }

    public void fillEmailSubject(String subject) {
        protonMeMailPage.getInputEmailSubject().sendKeys(subject);
    }
}
