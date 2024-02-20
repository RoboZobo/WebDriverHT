package tests;

import config_reader.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.EclipsoEuSteps;
import steps.ProtonMeSteps;

public class ProtonMeMailTest {

    private ProtonMeSteps protonMeSteps;
    private EclipsoEuSteps eclipsoEuSteps;

    @BeforeSuite
    public void getConfig() {
        ConfigReader.getConfiguration("proton");
    }
    @BeforeMethod
    public void openProtonMePage() {
        protonMeSteps = new ProtonMeSteps();
        protonMeSteps.openProtonMeMailLoginPage("chrome", ConfigReader.getProperty("loginUrl"));
    }

    @AfterMethod
    public void closePage() {
        protonMeSteps.closeProtonMeMailPage();
    }

    @DataProvider(name = "incorrectLoginPasswordPairs")
    public static Object[][] incorrectLoginPasswordPairs() {
        return new Object[][] {{ConfigReader.getProperty("login"), "123"}, {"caraqa@protn.me", ConfigReader.getProperty("password")}};
    }

    @Test(dataProvider = "incorrectLoginPasswordPairs")
    public void signInPageWithIncorrectCredsTest(String login, String password) {
        protonMeSteps.loginOnProtonMeMailPage(login, password, true);
        Assert.assertTrue(protonMeSteps.isAlertMessageDisplayed());
    }

    @Test
    public void signInPageWithEmptyCredsTest() {
        protonMeSteps.loginOnProtonMeMailPage("", "", false);
        int expectedNumberOfWarningMessages = 2;
        Assert.assertEquals(protonMeSteps.getWarningMessages(), expectedNumberOfWarningMessages);
    }

}
