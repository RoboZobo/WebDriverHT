package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.EclipsoEuSteps;
import steps.ProtonMeSteps;

public class ProtonMeMailTest {

    private ProtonMeSteps protonMeSteps;
    private EclipsoEuSteps eclipsoEuSteps;
    private static final String PASSWORD = "justForTest999_";
    public static final String PROTONME_LOGIN = "caraqa@proton.me";

    @BeforeMethod
    public void openProtonMePage() {
        protonMeSteps = new ProtonMeSteps();
        protonMeSteps.openProtonMeMailLoginPage("chrome");
    }

    @AfterMethod
    public void closePage() {
        protonMeSteps.closeProtonMeMailPage();
    }

    @DataProvider(name = "incorrectLoginPasswordPairs")
    public static Object[][] incorrectLoginPasswordPairs() {
        return new Object[][] {{PROTONME_LOGIN, "123"}, {"caraqa@protn.me", PASSWORD}};
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
