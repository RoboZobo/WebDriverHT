package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import steps.EclipsoEuSteps;
import steps.ProtonMeSteps;

import static tests.EclipsoEuMailTest.ECLIPSO_LOGIN;
import static tests.EclipsoEuMailTest.PASSWORD;
import static tests.ProtonMeMailTest.PROTONME_LOGIN;

public class CommonTest {

    public void sendingOfEmail(String sender, String pass, String reciever, String subject, String message) {
        ProtonMeSteps protonMeSteps = new ProtonMeSteps();
        protonMeSteps.openProtonMeMailLoginPage();
        protonMeSteps.loginOnProtonMeMailPage(sender, pass, true);
        protonMeSteps.clickOnNewMessageButton();
        protonMeSteps.fillEmailAddress(reciever);
        protonMeSteps.fillEmailSubject(subject);
        protonMeSteps.fillMessageBodyWithText(message);
        protonMeSteps.clickOnSendMessageButton();
        protonMeSteps.closeProtonMeMailPage();
    }

    @Test
    public void checkOfSendingEmail() {
        EclipsoEuSteps eclipsoEuSteps = new EclipsoEuSteps();
        String message = "Hello, world!";
        String subject = "Hello!";
        sendingOfEmail(PROTONME_LOGIN, PASSWORD, ECLIPSO_LOGIN, subject, message);

        eclipsoEuSteps.openEclipsoEuMailPage();
        eclipsoEuSteps.acceptAllCookies();
        eclipsoEuSteps.loginOnEclipsoEuMailPage(ECLIPSO_LOGIN, PASSWORD);
        eclipsoEuSteps.openInboxOnEclipsoEuMailPage();
        Assert.assertTrue(eclipsoEuSteps.isEmailUnread());
        eclipsoEuSteps.clickOnUreadEmail();
        Assert.assertTrue(eclipsoEuSteps.getSenderMail().contains(PROTONME_LOGIN));
        Assert.assertTrue(eclipsoEuSteps.getTextFromIframeMail().contains(message));
        eclipsoEuSteps.closeEclipsoEuMailPage();
    }
}
