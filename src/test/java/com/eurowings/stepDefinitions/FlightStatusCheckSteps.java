package com.eurowings.stepDefinitions;

import com.eurowings.pages.FlightStatusPage;
import com.eurowings.utilities.BrowserUtils;
import com.eurowings.utilities.ConfigurationReader;
import com.eurowings.utilities.Driver;
import com.eurowings.utilities.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FlightStatusCheckSteps {

    FlightStatusPage flightStatusPage = new FlightStatusPage();

    @Given("User navigates to flight status check page")
    public void user_navigates_to_flight_status_check_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        flightStatusPage.acceptPrivacySettings();
    }

    @When("User clicks on the {string} radio button")
    public void user_clicks_on_the_radio_button(String radioButtonName) {
        BrowserUtils.scrollToElement(flightStatusPage.flightRouteRadioButton);
        switch (radioButtonName) {
            case "Flight route":
                if (!flightStatusPage.flightRouteRadioButton.isSelected()) {
                    flightStatusPage.flightRouteRadioButton.click();
                }
                break;
            case "Flight number":
                flightStatusPage.flightNumberRadioButton.click();
                break;
        }
    }

    @Then("Departure date and flight number input fields should be displayed")
    public void departure_date_and_flight_number_input_fields_should_be_displayed() {
        Assert.assertTrue(flightStatusPage.flightNumberInputBox.isDisplayed());
        Assert.assertTrue(flightStatusPage.flightNumberDepartureDatePicker.isDisplayed());
    }

    @Then("Departure and destination airport fields and departure date field should be displayed")
    public void departure_and_destination_airport_fields_and_departure_date_field_should_be_displayed() {
        Assert.assertTrue(flightStatusPage.departureAirportDropdown.isDisplayed());
        Assert.assertTrue(flightStatusPage.destinationAirportDropdown.isDisplayed());
        Assert.assertTrue(flightStatusPage.flightRouteDepartureDateField.isDisplayed());
    }

    @When("User clicks on the {string} dropdown")
    public void user_clicks_on_the_dropdown(String dropdownName) {
        BrowserUtils.scrollToElement(flightStatusPage.flightRouteRadioButton);
        switch (dropdownName) {
            case "departure airport":
                flightStatusPage.departureAirportDropdown.click();
                break;
            case "destination airport":
                flightStatusPage.destinationAirportDropdown.click();
                break;
        }
    }

    @Then("List of available departure airports should be displayed")
    public void list_of_available_departure_airports_should_be_displayed() {
        Assert.assertTrue(flightStatusPage.nationalAirports.stream()
                .allMatch(WebElement::isDisplayed));
        Assert.assertTrue(flightStatusPage.internationalAirports.stream()
                .allMatch(WebElement::isDisplayed));
        flightStatusPage.nationalAirports.get(0).click();
    }

    @Then("List of available destination airports should be displayed")
    public void list_of_available_destination_airports_should_be_displayed() {
        Assert.assertTrue(flightStatusPage.destinationAirports.stream()
                .allMatch(WebElement::isDisplayed));
        flightStatusPage.destinationAirports.get(0).click();
    }

    @When("User clicks on the date picker")
    public void user_clicks_on_the_date_picker() {
        flightStatusPage.flightRouteDepartureDateField.click();
    }

    @Then("User should be able to select dates from {string} up to {int} days in the future")
    public void user_should_be_able_to_select_dates_from_up_to_days_in_the_future(String today, int day) {
        flightStatusPage.selectDate(today, 3);
        for (int i = flightStatusPage.getTodayDate(); i < flightStatusPage.getTodayDate() + 3; i++) {
            Assert.assertTrue(flightStatusPage.calendarDays.get(i).isEnabled());
        }
    }

    @Then("Dates {int} days in the future should be disabled")
    public void dates_4_days_in_the_future_should_be_disabled(int day) {
        Assert.assertFalse(flightStatusPage.calendarDays.get(flightStatusPage.getTodayDate() + day).isEnabled());
    }

    @When("User has not filled in all required fields")
    public void user_has_not_filled_in_all_required_fields() {
        flightStatusPage.showFlightStatusButton.click();
    }

    @Then("The Show flight status button should be disabled")
    public void the_show_flight_status_button_should_be_disabled() {
        Assert.assertFalse(flightStatusPage.showFlightStatusButton.isEnabled());
    }

    @When("User has filled in all required fields")
    public void user_has_filled_in_all_required_fields() {
        flightStatusPage.departureAirportDropdown.click();
        flightStatusPage.nationalAirports.get(0).click();
    }

    @Then("The Show flight status button should be enabled")
    public void the_show_flight_status_button_should_be_enabled() {
        Assert.assertTrue(flightStatusPage.showFlightStatusButton.isEnabled());
    }


    String currentAirportName = "";

    @When("User selects on the {string} as the airport")
    public void user_selects_on_the_as_the_airport(String airportName) {

        for (WebElement eachAirport : flightStatusPage.allAirports) {
            if (eachAirport.getText().contains(airportName)) {
                System.out.println(eachAirport.getText());
                currentAirportName = eachAirport.getText();
                eachAirport.click();
                break;
            }
        }
    }

    @Then("User should see {string} in departure airport dropdown")
    public void user_should_see_in_departure_airport_dropdown(String departureAirport) {
        Assert.assertEquals(departureAirport, flightStatusPage.departureAirportDropdownBox.getText());
    }

    @Then("User should see {string} in destination airport dropdown")
    public void user_should_see_in_destination_airport_dropdown(String destinationAirport) {
        Assert.assertEquals(destinationAirport, flightStatusPage.destinationAirportDropdownBox.getText());
    }

    @When("User selects {string} as the departure date")
    public void user_selects_as_the_departure_date(String day) throws InterruptedException {
        flightStatusPage.selectDate(day);
        Thread.sleep(4000);
    }


    @When("User clicks the {string} button")
    public void user_clicks_the_button(String string) {
        flightStatusPage.showFlightStatusButton.click();
    }

    @Then("System should display correct {string} and {string} flights")
    public void system_should_display_correct_flights(String departureAirportName, String destinationAirportName) {
        if (flightStatusPage.flightResultStatusCards.size() > 0) {
            for (WebElement eachFlightStatusCard : flightStatusPage.flightResultStatusCards) {
                for (WebElement eachDepartureResult : flightStatusPage.departureResult) {
                    Assert.assertTrue(eachFlightStatusCard.getText().contains(eachDepartureResult.getText()));
                    System.out.println("Departure = " + eachDepartureResult.getText());
                }
                for (WebElement eachDestinationResult : flightStatusPage.destinationResult) {
                    Assert.assertTrue(eachFlightStatusCard.getText().contains(eachDestinationResult.getText()));
                    System.out.println("Destination = " + eachDestinationResult.getText());
                }
            }
        } else {
            Assert.assertTrue(flightStatusPage.noResultMessage.getText().equals("Unfortunately, we could not find any results for your search."));
        }
    }


}
