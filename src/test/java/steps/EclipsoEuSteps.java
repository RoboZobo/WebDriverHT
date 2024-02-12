package steps;

import components.LoginOnPage;
import lombok.extern.java.Log;
import org.openqa.selenium.StaleElementReferenceException;
import pages.EclipsoEuMailPage;

@Log
public class EclipsoEuSteps {

    public EclipsoEuMailPage eclipsoEuMailPage;

    public void openEclipsoEuMailPage(String driverName) {
        eclipsoEuMailPage = new EclipsoEuMailPage(driverName);
        eclipsoEuMailPage.openPage("https://www.eclipso.eu/login");
    }

    public void acceptAllCookies() {
        eclipsoEuMailPage.getAcceptCookies().click();
    }

    public void loginOnEclipsoEuMailPage(String login, String password) {
        LoginOnPage.builder().loginInputField(eclipsoEuMailPage.getLoginInputField())
                .passwordInputField(eclipsoEuMailPage.getPasswordInputField())
                .build().inputLogin(login).inputPassword(password);
        eclipsoEuMailPage.clickWithJavascriptOnElement(eclipsoEuMailPage.getSignInButton());
    }

    public void closeEclipsoEuMailPage() {
        eclipsoEuMailPage.closePage();
    }

    public String getTitleOfEclipsoEuMailPage() {
        return eclipsoEuMailPage.getTitleOfPage();
    }

    public boolean isNoticeDisplayed() {
        return eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getNoticeMessage(), 5).isDisplayed();
    }

    public boolean isLoginInputFieldDisplayed() {
        return eclipsoEuMailPage.getLoginInputField().isDisplayed();
    }

    public void openInboxOnEclipsoEuMailPage() {
        eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getMenu(), 3).click();
        eclipsoEuMailPage.getMailIcon().click();
        eclipsoEuMailPage.removeAdsWithJavascript("ads");
        eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getMailIcon(), 10).click();
        eclipsoEuMailPage.waitForElementWithClick(eclipsoEuMailPage.getLastEmail(), eclipsoEuMailPage.getUpdateReceivedEmails(), 50, 5);
    }

    public boolean isEmailUnread() {
        return eclipsoEuMailPage.getLastEmail().isDisplayed();
    }

    public void clickOnUreadEmail() {
        for (int i = 0; i < 5; i++) {
            try {
                eclipsoEuMailPage.getLastEmail().click();
            } catch (StaleElementReferenceException e) {
                log.info("Unread Mail is unreacheable: " + e);
            }
        }
    }

    public String getSenderMail() {
        return eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getSender(), 3).getAttribute("onClick");
    }

    public String getTextFromIframeMail() {
        eclipsoEuMailPage.getDriver().switchTo().frame(eclipsoEuMailPage.getIframeMessage());
        String textFromIframeMail = eclipsoEuMailPage.getIframeTextMessage().getText();
        eclipsoEuMailPage.getDriver().switchTo().defaultContent();
        return textFromIframeMail;
    }
}
