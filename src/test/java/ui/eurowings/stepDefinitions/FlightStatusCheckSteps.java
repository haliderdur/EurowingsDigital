package ui.eurowings.stepDefinitions;


import ui.eurowings.pages.FlightStatusPage;
import ui.eurowings.utilities.BrowserUtils;
import ui.eurowings.utilities.ConfigurationReader;
import ui.eurowings.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class FlightStatusCheckSteps {

    FlightStatusPage fsPage = new FlightStatusPage();

    @Given("User navigates to flight status check page")
    public void user_navigates_to_flight_status_check_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }

    @Given("User verifies page title is {string}")
    public void user_verifies_page_title_is(String pageTitle) {
        BrowserUtils.verifyTitle(pageTitle);
    }

    @Given("User accepts privacy settings")
    public void user_accepts_privacy_settings() {
        fsPage.acceptPrivacySettings();
    }

    @Then("The Show flight status button should be disabled by default")
    public void the_show_flight_status_button_should_be_disabled_by_default() {
        BrowserUtils.verifyElementNotEnabled(fsPage.showFlightStatusButton);
    }

    @When("User clicks on the {string} radio button")
    public void user_clicks_on_the_radio_button(String radioButtonValue) {
        BrowserUtils.scrollToElement(fsPage.flightRouteRadioButton);
        fsPage.clickRadioButton(fsPage.flightRouteAndNumberRadioButtons, radioButtonValue);
    }

    @When("User clicks on the {string} dropdown")
    public void user_clicks_on_the_dropdown(String dropdownName) {
        fsPage.airportPicker(dropdownName);
    }

    @When("User selects on the {string} as the airport name")
    public void user_selects_on_the_as_the_airport_name(String airportName) {
        fsPage.selectAirport(airportName);
    }

    @Then("User should see {string} in departure airport dropdown")
    public void user_should_see_in_departure_airport_dropdown(String departureAirportName) {
        String actualDepartureAirportName = fsPage.departureAirportDropdownBox.getText();
        Assert.assertEquals(
                "Expected departure airport name: " + departureAirportName + " but found: " + actualDepartureAirportName,
                departureAirportName, actualDepartureAirportName);
    }

    @Then("User should see {string} in destination airport dropdown")
    public void user_should_see_in_destination_airport_dropdown(String destinationAirportName) {
        String actualDestinationAirportName = fsPage.destinationAirportDropdownBox.getText();
        Assert.assertEquals(
                "Expected destination airport name: " + destinationAirportName + " but found: " + actualDestinationAirportName,
                destinationAirportName, actualDestinationAirportName);
    }

    @When("User clicks on the date picker")
    public void user_clicks_on_the_date_picker() {
        fsPage.departureDatePicker.click();
    }

    @Then("User should be able to select dates from today up to 3 days in the future and past")
    public void user_should_be_able_to_select_dates_from_today_up_to_3_days_in_the_future_and_past() {
        fsPage.selectAvailableDaysAndVerify();
    }

    @Then("User selects {string} as the departure date")
    public void user_selects_as_the_departure_date(String day) {
        fsPage.selectDate(day);
    }

    @When("User clicks the show flight status button")
    public void user_clicks_the_show_flight_status_button() {
        fsPage.showFlightStatusButton.click();
    }

    @Then("System should display correct {string} and {string}")
    public void system_should_display_correct_and(String departureAirportName, String destinationAirportName) {
        if (!fsPage.flightResultStatusCards.isEmpty()) {
            for (WebElement eachStatusCard : fsPage.flightResultStatusCards) {
                boolean departureMatched = eachStatusCard.getText().contains(departureAirportName);
                boolean destinationMatched = eachStatusCard.getText().contains(destinationAirportName);
                Assert.assertTrue("Departure airport not matched: " + eachStatusCard.getText(), departureMatched);
                Assert.assertTrue("Destination airport not matched: " + eachStatusCard.getText(), destinationMatched);
            }
        } else {
            BrowserUtils.verifyElementDisplayed(fsPage.noResultMessage);
        }
    }

    @Then("Departure date and flight number input fields should be displayed")
    public void departure_date_and_flight_number_input_fields_should_be_displayed() {
        BrowserUtils.verifyElementDisplayedAndEnabled(fsPage.flightNumberInputBox);
        BrowserUtils.verifyElementDisplayedAndEnabled(fsPage.departureDatePicker);
    }

    @Then("Departure and destination airport fields and departure date field should be displayed")
    public void departure_and_destination_airport_fields_and_departure_date_field_should_be_displayed() {
        BrowserUtils.verifyElementDisplayedAndEnabled(fsPage.departureAirportDropdown);
        BrowserUtils.verifyElementDisplayedAndEnabled(fsPage.destinationAirportDropdown);
        BrowserUtils.verifyElementDisplayedAndEnabled(fsPage.departureDatePicker);
    }

    @Then("User should see {string} in result")
    public void user_should_see_in_result(String day) {
        fsPage.verifySelectedDay(day);
    }

    @When("User enters {string} as the flight number")
    public void user_enters_as_the_flight_number(String flightNumber) {
        fsPage.flightNumberInputBox.sendKeys(flightNumber);
    }

    @Then("System should display correct {string} and {string} and {string}")
    public void system_should_display_correct_and_and(String departureAirportName, String destinationAirportName, String flightNumber) {
        if (!fsPage.flightResultStatusCards.isEmpty()) {
            for (WebElement eachStatusCard : fsPage.flightResultStatusCards) {
                boolean departureMatched = eachStatusCard.getText().contains(departureAirportName);
                boolean destinationMatched = eachStatusCard.getText().contains(destinationAirportName);
                boolean flightNumberMatched = eachStatusCard.getText().contains(flightNumber);
                Assert.assertTrue("Departure airport not matched: " + eachStatusCard.getText(), departureMatched);
                Assert.assertTrue("Destination airport not matched: " + eachStatusCard.getText(), destinationMatched);
                Assert.assertTrue("Flight number not matched: " + eachStatusCard.getText(), flightNumberMatched);
            }
        } else {
            BrowserUtils.verifyElementDisplayed(fsPage.noResultMessage);
        }
    }
}
