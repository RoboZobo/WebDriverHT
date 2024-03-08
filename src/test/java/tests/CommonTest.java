package tests;

import config_reader.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.EclipsoEuSteps;
import steps.ProtonMeSteps;

public class CommonTest {

    //provided SRP
    private EclipsoEuSteps eclipsoEuSteps;
    private ProtonMeSteps protonMeSteps;
    private String protonUrl;
    private String protonLogin;
    private String protonPass;
    private String eclipsoUrl;
    private String eclipsoLogin;
    private String eclipsoPass;

    private void sendEmail(String sender, String pass, String receiver, String subject, String message) {
        protonMeSteps.openProtonMeMailLoginPage("firefox", protonUrl);
        protonMeSteps.loginOnProtonMeMailPage(sender, pass, true);
        protonMeSteps.clickOnNewMessageButton();
        protonMeSteps.fillEmailAddress(receiver);
        protonMeSteps.fillEmailSubject(subject);
        protonMeSteps.fillMessageBodyWithText(message);
        protonMeSteps.clickOnSendMessageButton();
        protonMeSteps.closeProtonMeMailPage();
    }

    @BeforeMethod
    public void setUp() {
        protonMeSteps = new ProtonMeSteps();
        eclipsoEuSteps = new EclipsoEuSteps();

        loadConfigurations();
    }

    private void loadConfigurations() {
        ConfigReader configReader = ConfigReader.getInstance();

        configReader.getConfiguration("proton");
        protonUrl = configReader.getProperty("loginUrl");
        protonLogin = configReader.getProperty("login");
        protonPass = configReader.getProperty("password");

        configReader.getConfiguration("eclipso");
        eclipsoUrl = configReader.getProperty("loginUrl");
        eclipsoLogin = configReader.getProperty("login");
        eclipsoPass = configReader.getProperty("password");
    }

    @AfterMethod
    public void tearDown() {
        if (eclipsoEuSteps != null) {
            eclipsoEuSteps.closeEclipsoEuMailPage();
        }
    }

    @Test
    public void checkSendingEmail() {
        sendEmail(protonLogin, protonPass, eclipsoLogin, "Hello!", "Hello, world!");

        eclipsoEuSteps.openEclipsoEuMailPage("chrome", eclipsoUrl);
        eclipsoEuSteps.acceptAllCookies();
        eclipsoEuSteps.loginOnEclipsoEuMailPage(eclipsoLogin, eclipsoPass);
        eclipsoEuSteps.openInboxOnEclipsoEuMailPage();

        Assert.assertTrue(eclipsoEuSteps.isEmailUnread());
        eclipsoEuSteps.clickOnUreadEmail();

        Assert.assertTrue(eclipsoEuSteps.getSenderMail().contains(protonLogin));
        Assert.assertTrue(eclipsoEuSteps.getTextFromIframeMail().contains("Hello, world!"));
    }

}
