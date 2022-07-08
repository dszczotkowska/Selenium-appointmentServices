package AppointmentPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    By loginFieldLocator = By.id("txt-username");
    By passwordFieldLOcator = By.id("txt-password");
    By loginButton = By.id("btn-login");
    By makeAppointmentButton = By.id("btn-make-appointment");



    public LoginPage logIn(String userName, String password) {

        driver.findElement(loginFieldLocator).sendKeys(userName);
        driver.findElement(passwordFieldLOcator).sendKeys(password);
        driver.findElement(loginButton).click();
        return this;
    }

    public LoginPage goToLoginPage(String mainPageURL) {
        driver.navigate().to(mainPageURL);
        driver.findElement(makeAppointmentButton).click();
        return this;
    }
}
