package AppointmentPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AppointmentPage extends BasePage {
    public AppointmentPage(WebDriver driver) {
        super(driver);
    }


    private By hospitalCheckboxLocator = By.id("chk_hospotal_readmission");
    private By calendarLocator = By.id("txt_visit_date");
    private By commentFieldLocator = By.id("txt_comment");
    private By bookAppointmentButton = By.id("btn-book-appointment");
    private By monthViewSelector = By.xpath(".//div[@class='datepicker-days']//th[@class='datepicker-switch']");
    private By yearViewSelector = By.xpath(".//div[@class='datepicker-months']//th[@class='datepicker-switch']");

    private String chooseProgramSelector = "input[value='<programName>']";
    private String chooseYearSelecor = ".//span[text()='<year>' and @class='year']";

    private String chooseMonthSelector = ".//span[text()='<mth>' and @class='month']";

    private String chooseDaySelector = ".//td[text()='<day>' and @class='day']";


    public AppointmentPage setFacility(String facilityName) {
        WebElement facilityElement = driver.findElement(By.id("combo_facility"));
        Select facilitySelect = new Select(facilityElement);
        facilitySelect.selectByValue(facilityName);
        return this;
    }

    public AppointmentPage applyHospitalCheckbox(boolean selected) {
        if (selected == true) {
            driver.findElement(hospitalCheckboxLocator).click();
        }
        return this;
    }

    public AppointmentPage chooseHealthCareProgram(String programName) {
        By medicalProgram = By.cssSelector(chooseProgramSelector.replace("<programName>", programName));
        driver.findElement(medicalProgram).click();
        return this;
    }

    public AppointmentPage selectDate(String day, String mth, String year) {
        By appointmentDayLocator = By.xpath(chooseDaySelector.replace("<day>", day));
        By appointmentMonthLocator = By.xpath(chooseMonthSelector.replace("<mth>", mth));
        By appointmentYearLocator = By.xpath(chooseYearSelecor.replace("<year>", year));

        driver.findElement(calendarLocator).click();
        driver.findElement(monthViewSelector).click();
        driver.findElement(yearViewSelector).click();

        driver.findElement(appointmentYearLocator).click();
        driver.findElement(appointmentMonthLocator).click();
        driver.findElement(appointmentDayLocator).click();

        return this;
    }

    public AppointmentPage addComment(String text) {
        driver.findElement(commentFieldLocator).sendKeys(text);
        return this;
    }

    public ConfirmationPage bookAppointment() {
        driver.findElement(bookAppointmentButton).click();

        return new ConfirmationPage(driver);
    }

}

