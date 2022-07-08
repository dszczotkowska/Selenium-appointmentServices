package AppointmentTests;

import AppointmentPages.AppointmentPage;
import AppointmentPages.ConfirmationPage;
import AppointmentPages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AppointmentBookingTest extends BaseTest {
    private String userName = "John Doe";
    private String userPassword = "ThisIsNotAPassword";
    private String mainPageURL = "https://katalon-demo-cura.herokuapp.com/";
    private String expectedFailedLoginMessage = "Login failed! Please ensure the username and password are valid.";
    private String expectedLoggedUserMessage = "Make Appointment";
    private By confirmationFacilityLocator = By.id("facility");
    private By hospitalCheckboxLocator = By.id("hospital_readmission");
    private By medProgramLocator = By.id("program");
    private By visitDateLocator = By.id("visit_date");
    private By commentBoxLocator = By.id("comment");
    private By homePageHeader = By.xpath(".//h2[text()='Make Appointment']");
    private By wrongLoginHeader = By.cssSelector("p[class='lead text-danger']");


    @DisplayName("loginWithIncorrectData")
    @ParameterizedTest(name = "Login: \"{0}\" and Password: \"{1}\"")
    @CsvSource({"'', wrongPassword",
            "wrongLogin, ''",
            "'wrongLogin', 'wrongPassword'"})
    void loginWithIncorrectData(String login, String password) {
        LoginPage loginPage = new LoginPage(driver).goToLoginPage(mainPageURL);
        loginPage.logIn(login, password);

        Assertions.assertTrue(driver.findElement(wrongLoginHeader).isDisplayed());
        Assertions.assertEquals(expectedFailedLoginMessage, driver.findElement(wrongLoginHeader).getText());
    }

    @Test
    public void loginWithCorrectData() {
        LoginPage loginPage = new LoginPage(driver).goToLoginPage(mainPageURL).logIn(userName, userPassword);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(homePageHeader)));

        Assertions.assertEquals(expectedLoggedUserMessage, driver.findElement(homePageHeader).getText());
    }

    @Test
    public void bookAnAppointment() {
        LoginPage loginPage = new LoginPage(driver).goToLoginPage(mainPageURL);
        AppointmentPage appointmentPage = new AppointmentPage(driver);
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);

        loginPage.logIn(userName, userPassword);
        appointmentPage.setFacility("Seoul CURA Healthcare Center")
                .applyHospitalCheckbox(true)
                .chooseHealthCareProgram("Medicaid")
                .selectDate("7", "May", "2023")
                .addComment("It's text in comment box")
                .bookAppointment();

        String expectedFacility = "Seoul CURA Healthcare Center";
        String expectedHospitalCheckbox = "Yes";
        String expectedHealthCare = "Medicaid";
        String expectedVisitDate = "07/05/2023";
        String expectedCommentBoxValue = "It's text in comment box";

        String actualFacility = driver.findElement(confirmationFacilityLocator).getText();
        String actualHospitalReadmission = driver.findElement(hospitalCheckboxLocator).getText();
        String actualMedProgram = driver.findElement(medProgramLocator).getText();
        String actualVisitDate = driver.findElement(visitDateLocator).getText();
        String actualCommentBox = driver.findElement(commentBoxLocator).getText();

        Assertions.assertAll("x",
                () -> Assertions.assertEquals(expectedFacility, actualFacility, "Facility doesn't match."),
                () -> Assertions.assertEquals(expectedHospitalCheckbox, actualHospitalReadmission, "Checkbox wasn't checked."),
                () -> Assertions.assertEquals(expectedHealthCare, actualMedProgram, "Healthcare program doesn't match. Actual value is " + actualMedProgram),
                () -> Assertions.assertEquals(expectedVisitDate, actualVisitDate, "Date doesn't match. Actual value is " + actualVisitDate),
                () -> Assertions.assertEquals(expectedCommentBoxValue, actualCommentBox, "Text in comment box doesn't match. Actual value is " + actualCommentBox));

        confirmationPage.goToHomepage();
        Assertions.assertTrue(driver.findElement(homePageHeader).isDisplayed());
    }

}
