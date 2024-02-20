package tests;

import config_reader.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.EclipsoEuSteps;
import steps.ProtonMeSteps;

public class CommonTest {

    EclipsoEuSteps eclipsoEuSteps;
    private String protonUrl;
    private String protonLogin;
    private String protonPass;
    private String eclipsoUrl;
    private String eclipsoLogin;
    private String eclipsoPass;

    public void sendingOfEmail(String sender, String pass, String reciever, String subject, String message) {
        ProtonMeSteps protonMeSteps = new ProtonMeSteps();
        protonMeSteps.openProtonMeMailLoginPage("firefox", protonUrl);
        protonMeSteps.loginOnProtonMeMailPage(sender, pass, true);
        protonMeSteps.clickOnNewMessageButton();
        protonMeSteps.fillEmailAddress(reciever);
        protonMeSteps.fillEmailSubject(subject);
        protonMeSteps.fillMessageBodyWithText(message);
        protonMeSteps.clickOnSendMessageButton();
        protonMeSteps.closeProtonMeMailPage();
    }

    @BeforeMethod
    public void getConfig() {
        ConfigReader.getConfiguration("proton");
        protonUrl = ConfigReader.getProperty("loginUrl");
        protonLogin = ConfigReader.getProperty("login");
        protonPass = ConfigReader.getProperty("password");
        ConfigReader.getConfiguration("eclipso");
        eclipsoUrl = ConfigReader.getProperty("loginUrl");
        eclipsoLogin = ConfigReader.getProperty("login");
        eclipsoPass = ConfigReader.getProperty("password");
    }

    @AfterMethod
    public void closePage() {
        eclipsoEuSteps.closeEclipsoEuMailPage();
    }

    @Test
    public void checkOfSendingEmail() {
        eclipsoEuSteps = new EclipsoEuSteps();
        String message = "Hello, world!";
        String subject = "Hello!";
        sendingOfEmail(protonLogin, protonPass, eclipsoLogin, subject, message);

        eclipsoEuSteps.openEclipsoEuMailPage("chrome", eclipsoUrl);
        eclipsoEuSteps.acceptAllCookies();
        eclipsoEuSteps.loginOnEclipsoEuMailPage(eclipsoLogin, eclipsoPass);
        eclipsoEuSteps.openInboxOnEclipsoEuMailPage();
        Assert.assertTrue(eclipsoEuSteps.isEmailUnread());
        eclipsoEuSteps.clickOnUreadEmail();
        Assert.assertTrue(eclipsoEuSteps.getSenderMail().contains(protonLogin));
        Assert.assertTrue(eclipsoEuSteps.getTextFromIframeMail().contains(message));
    }
}
