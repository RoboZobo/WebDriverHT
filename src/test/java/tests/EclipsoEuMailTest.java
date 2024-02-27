package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config_reader.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import report.ExtentReportTest;
import steps.EclipsoEuSteps;

public class EclipsoEuMailTest extends ExtentReportTest {

    EclipsoEuSteps eclipsoEuSteps;

    @BeforeTest
    public void getConfig() {
        ConfigReader.getInstance().getConfiguration("eclipso");
    }

    @BeforeMethod
    public void openPage() {
        eclipsoEuSteps = new EclipsoEuSteps();
        eclipsoEuSteps.openEclipsoEuMailPage("firefox", ConfigReader.getInstance().getProperty("loginUrl"));
        eclipsoEuSteps.acceptAllCookies();
    }

    @AfterMethod
    public void closePage() {
        eclipsoEuSteps.closeEclipsoEuMailPage();
    }

    @DataProvider(name = "incorrectLoginPasswordPairs")
    public static Object[][] incorrectLoginPasswordPairs() {
        return new Object[][] {{ConfigReader.getInstance().getProperty("login"), "123"}, {"johnqa@ecliso.eu", ConfigReader.getInstance().getProperty("password")}};
    }

    @DataProvider(name = "emptyLoginPasswordPairs")
    public static Object[][] emptyLoginPasswordPairs() {
        return new Object[][] {{"", "123"}, {ConfigReader.getInstance().getProperty("login"), ""}};
    }

    public void assertTrueWithReport(ExtentTest logger, boolean condition) {
        if (condition) {
            logger.log(Status.PASS, MarkupHelper.createLabel(logger.getModel().getDescription(), ExtentColor.GREEN));
        } else {
            logger.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(eclipsoEuSteps.createScreenShot()).build());
        }
        Assert.assertTrue(condition);
    }

    @Test(dataProvider = "incorrectLoginPasswordPairs")
    public void signInPageWithIncorrectCredsTest(String login, String password) {
        ExtentTest test = extent.createTest("signInPageWithIncorrectCredsTest", "Incorrect login and password pairs");
        eclipsoEuSteps.loginOnEclipsoEuMailPage(login, password);
        assertTrueWithReport(test, eclipsoEuSteps.isNoticeDisplayed());
    }

    @Test(dataProvider = "emptyLoginPasswordPairs")
    public void signInPageWithEmptyCredsTest(String login, String password) {
        ExtentTest test = extent.createTest("signInPageWithEmptyCredsTest", "Empty login and password pairs");
        eclipsoEuSteps.loginOnEclipsoEuMailPage(login, password);
        assertTrueWithReport(test, eclipsoEuSteps.isLoginInputFieldDisplayed());
    }

}
