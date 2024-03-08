package stepdefenitions;

import config_reader.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import steps.ProtonMeSteps;

public class ProtonMailStepDef {
    private ProtonMeSteps protonMeSteps;

    @After
    public void closePage() {
        protonMeSteps.closeProtonMeMailPage();
    }

    @Given("open Eclipso mail page")
    public void openEclipsoMailPage() {
        ConfigReader.getInstance().getConfiguration("proton");
        protonMeSteps = new ProtonMeSteps();
        protonMeSteps.openProtonMeMailLoginPage("chrome", ConfigReader.getInstance().getProperty("loginUrl"));
    }

    @Given("signin with empty login and password")
    public void signinWithEmptyLoginAndPassword() {
        protonMeSteps.loginOnProtonMeMailPage("", "", false);
    }

    @Then("^receive (\\d) warning messages$")
    public void receiveWarningMessages(int expectedNumberOfWarningMessages) {
        Assert.assertEquals(protonMeSteps.getWarningMessages(), expectedNumberOfWarningMessages);
    }

    @When("signin with {string} login and {string} password")
    public void signinWithLoginLoginAndPasswordPassword(String login, String password) {
        protonMeSteps.loginOnProtonMeMailPage(login, password, true);
    }

    @Then("alert message is displayed")
    public void alertMessageIsDisplayed() {
        Assert.assertTrue(protonMeSteps.isAlertMessageDisplayed());
    }

}
