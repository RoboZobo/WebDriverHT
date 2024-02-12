package components;

import lombok.Builder;
import lombok.Getter;
import org.openqa.selenium.WebElement;

import static pages.BasePage.cleanInputElement;

@Getter
@Builder
public class LoginOnPage {
    private WebElement loginInputField;
    private WebElement passwordInputField;

    public LoginOnPage clearInputLogin() {
        cleanInputElement(getLoginInputField());
        return this;
    }

    public LoginOnPage clearInputPassword() {
        cleanInputElement(getPasswordInputField());
        return this;
    }

    public LoginOnPage inputLogin(String login) {
        loginInputField.sendKeys(login);
        return this;
    }

    public void inputPassword(String password) {
        passwordInputField.sendKeys(password);
    }
}
