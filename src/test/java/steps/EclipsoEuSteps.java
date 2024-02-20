package steps;

import components.LoginOnPage;
import lombok.extern.java.Log;
import org.openqa.selenium.StaleElementReferenceException;
import pages.EclipsoEuMailPage;

import java.util.logging.Level;

@Log
public class EclipsoEuSteps {

    public EclipsoEuMailPage eclipsoEuMailPage;

    public void openEclipsoEuMailPage(String driverName, String loginUrl) {
        eclipsoEuMailPage = new EclipsoEuMailPage(driverName);
        eclipsoEuMailPage.openPage(loginUrl);
    }

    public void acceptAllCookies() {
        eclipsoEuMailPage.getAcceptCookies().click();
    }

    public void loginOnEclipsoEuMailPage(String login, String password) {
        LoginOnPage.builder().loginInputField(eclipsoEuMailPage.getLoginInputField())
                .passwordInputField(eclipsoEuMailPage.getPasswordInputField())
                .build().inputLogin(login).inputPassword(password);
        log.info("LogIn with login: " + login + ", pass: " + password);
        eclipsoEuMailPage.clickWithJavascriptOnElement(eclipsoEuMailPage.getSignInButton());
    }

    public void closeEclipsoEuMailPage() {
        log.info("...closing of WebPage");
        eclipsoEuMailPage.closePage();
    }

    public String getTitleOfEclipsoEuMailPage() {
        String titleOfPage = eclipsoEuMailPage.getTitleOfPage();
        log.info("Title of page is: " + titleOfPage);
        return titleOfPage;
    }

    public boolean isNoticeDisplayed() {
        log.log(Level.ALL, "Waiting for Notice on Web Page");
        boolean isDisplayed = eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getNoticeMessage(), 5).isDisplayed();
        log.log(Level.INFO, "The Notice is " + (isDisplayed ? "visible" : "NOT visible"));
        return isDisplayed;
    }

    public boolean isLoginInputFieldDisplayed() {
        log.log(Level.ALL, "Waiting for Input field");
        boolean isDisplayed = eclipsoEuMailPage.getLoginInputField().isDisplayed();
        log.log(Level.INFO, "The Input field is " + (isDisplayed ? "visible" : "NOT visible"));
        return isDisplayed;
    }

    public void openInboxOnEclipsoEuMailPage() {
        log.info("Opening Inbox mail folder");
        eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getMenu(), 3).click();
        eclipsoEuMailPage.getMailIcon().click();
        eclipsoEuMailPage.removeAdsWithJavascript("ads");
        eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getMailIcon(), 10).click();
        eclipsoEuMailPage.waitForElementWithClick(eclipsoEuMailPage.getLastEmail(), eclipsoEuMailPage.getUpdateReceivedEmails(), 50, 5);
    }

    public boolean isEmailUnread() {
        log.log(Level.ALL, "Waiting for unread mail");
        boolean isDisplayed = eclipsoEuMailPage.getLastEmail().isDisplayed();
        log.log(Level.INFO, "The unread mail is " + (isDisplayed ? "visible" : "NOT visible"));
        return isDisplayed;
    }

    public void clickOnUreadEmail() {
        for (int i = 0; i < 5; i++) {
            try {
                log.info(i + " time trying to click on unread mail...");
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
