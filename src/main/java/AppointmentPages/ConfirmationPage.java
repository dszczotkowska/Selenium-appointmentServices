package AppointmentPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends BasePage {
    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    By goToHomepageButton = By.cssSelector("a[class='btn btn-default']");

    public void goToHomepage(){
        driver.findElement(goToHomepageButton).click();

    }
}
