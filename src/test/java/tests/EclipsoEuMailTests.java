package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.EclipsoEuSteps;

public class EclipsoEuMailTests {

    EclipsoEuSteps eclipsoEuSteps;
    public static final String PASSWORD = "justForTest999_";
    public static final String ECLIPSO_LOGIN = "johnqa@eclipso.eu";

    @BeforeMethod
    public void openPage() {
        eclipsoEuSteps = new EclipsoEuSteps();
        eclipsoEuSteps.openEclipsoEuMailPage();
        eclipsoEuSteps.acceptAllCookies();
    }

    @AfterMethod
    public void closePage() {
        eclipsoEuSteps.closeEclipsoEuMailPage();
    }

    @DataProvider(name = "incorrectLoginPasswordPairs")
    public static Object[][] incorrectLoginPasswordPairs() {
        return new Object[][] {{ECLIPSO_LOGIN, "123"}, {"johnqa@ecliso.eu", PASSWORD}};
    }

    @DataProvider(name = "emptyLoginPasswordPairs")
    public static Object[][] emptyLoginPasswordPairs() {
        return new Object[][] {{"", "123"}, {"caraqa@protn.me", ""}};
    }

    @Test(dataProvider = "incorrectLoginPasswordPairs")
    public void signInPageWithIncorrectCredsTest(String login, String password) {
        eclipsoEuSteps.loginOnEclipsoEuMailPage(login, password);
        Assert.assertTrue(eclipsoEuSteps.isNoticeDisplayed());
    }

    @Test(dataProvider = "emptyLoginPasswordPairs")
    public void signInPageWithEmptyCredsTest(String login, String password) {
        eclipsoEuSteps.loginOnEclipsoEuMailPage(login, password);
        Assert.assertTrue(eclipsoEuSteps.isLoginInputFieldDisplayed());
    }

}
