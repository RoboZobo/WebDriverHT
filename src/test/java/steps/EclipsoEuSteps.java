package steps;

import components.LoginOnPage;
import pages.EclipsoEuMailPage;

public class EclipsoEuSteps {

    public EclipsoEuMailPage eclipsoEuMailPage;

    public void openEclipsoEuMailPage() {
        eclipsoEuMailPage = new EclipsoEuMailPage();
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
        return eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getNoticeMessage(), 5)
                .isDisplayed();
    }

    public boolean isLoginInputFieldDisplayed() {
        return eclipsoEuMailPage.getLoginInputField().isDisplayed();
    }

    public void openInboxOnEclipsoEuMailPage() {
        eclipsoEuMailPage.getMenu().click();
        eclipsoEuMailPage.getMailIcon().click();
        eclipsoEuMailPage.waitForElement(eclipsoEuMailPage.getUnreadMail(), 30);
    }

    public boolean isEmailUnread() {
        return eclipsoEuMailPage.getUnreadMail().isDisplayed();
    }

    public void clickOnUreadEmail() {
        eclipsoEuMailPage.getUnreadMail().click();
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
