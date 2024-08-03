package com.eurowings.pages;

import com.eurowings.utilities.BrowserUtils;
import com.eurowings.utilities.Driver;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightStatusPage extends BasePage {

    @FindBy(xpath = "//span[@class='a-cta__text' and .='I understand']")
    private WebElement privacySettingsAgreeButton;

    public void acceptPrivacySettings() {
        if (privacySettingsAgreeButton.isDisplayed()) {
            privacySettingsAgreeButton.click();
        }
    }

    // dynamic id element
    @FindBy(xpath = "(//input[contains(@id,'radio-button') and @type='radio'])[1]")
    public WebElement flightRouteRadioButton;

    // dynamic id element
    @FindBy(xpath = "(//input[contains(@id,'radio-button') and @type='radio'])[2]")
    public WebElement flightNumberRadioButton;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button'])[1]/span")
    public WebElement departureAirportDropdown;


    @FindBy(xpath = "//div[@class='o-layer__content o-compact-search__layer-component__content']")
    public WebElement departureAirportsTable;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button'])[2]/span")
    public WebElement destinationAirportDropdown;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button']//span[@class='o-compact-search__cta-button-value'])[1]")
    public WebElement departureAirportDropdownBox;
    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button']//span[@class='o-compact-search__cta-button-value'])[2]")
    public WebElement destinationAirportDropdownBox;

    @FindBy(xpath = "//div[@class='o-compact-search__layer']")
    public WebElement destinationAirportsTable;

    @FindBy(xpath = "//div[@class='m-form-datepicker__calendar']")
    public WebElement datePickerTable;

    // dynamic id and name elements
    @FindBy(xpath = "//input[contains(@id,'text-') and contains(@name,'datepicker_input_')]")
    public WebElement flightRouteDepartureDateField;

    @FindBy(xpath = "//button[@type='submit' and @class='a-cta a-cta-prio1']")
    public WebElement showFlightStatusButton;

    @FindBy(xpath = "(//ul[@class='o-station-select__new-station-list'])[1]//li")
    public List<WebElement> nationalAirports;

    @FindBy(xpath = "//div[@class='o-station-select__flyout-content o-station-select__flyout-content--new-design']//ul/li")
    public List<WebElement> allAirports;

    @FindBy(xpath = "(//ul[@class='o-station-select__new-station-list'])[2]//li")
    public List<WebElement> internationalAirports;

    @FindBy(xpath = "//ul[@class='o-station-select__new-station-list']//li")
    public List<WebElement> destinationAirports;

    public List<String> getAirportNames(List<WebElement> airportElements) {
        List<String> airportNames = new ArrayList<>();
        for (WebElement eachAirport : airportElements) {
            try {
                airportNames.add(eachAirport.getText().trim());
            } catch (RuntimeException e) {
                e.getMessage();
            }
        }
        return airportNames;
    }

    // Method to get the list of national airport names
    public List<String> getNationalAirportNames() {
        return getAirportNames(nationalAirports);
    }

    // Method to get the list of international airport names
    public List<String> getInternationalAirportNames() {
        return getAirportNames(internationalAirports);
    }

    // dynamic id element
    @FindBy(xpath = "//input[contains(@id,'text-') and @name='flightNumber']")
    public WebElement flightNumberInputBox;

    // dynamic id and name elements
    @FindBy(xpath = "//input[contains(@id,'text-') and contains(@name,'datepicker_input_')]")
    public WebElement flightNumberDepartureDatePicker;

    @FindBy(xpath = "//table[@class='calendar-table']//td[div[contains(@class, 'calendar-date')]]//input")
    public List<WebElement> calendarDays;


    // Method to click on today's date
    public void selectDate(String today, int endDay) {
        today = String.valueOf(getTodayDate());
        endDay = getTodayDate() + endDay;
        for (int i = Integer.parseInt(today); i <= endDay; i++) {
            calendarDays.get(i - 1).click();
            System.out.println("Selected day: " + calendarDays.get(i - 1).getAttribute("value"));
            flightRouteDepartureDateField.click();
        }
    }

    // method to return today's date
    public Integer getTodayDate() {
        return LocalDate.now().getDayOfMonth();
    }

    // Method to return a date with a specified number of days added to the current date
    public Integer getDateWithOffset(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).getDayOfMonth();
    }

    // Method to select a date based on the input string
    public void selectDate(String day) {
        int daysToAdd;
        switch (day.toLowerCase()) {
            case "yesterday":
                daysToAdd = -1;
                break;
            case "today":
                daysToAdd = 0;
                break;
            case "tomorrow":
                daysToAdd = 1;
                break;
            default:
                try {
                    int targetDay = Integer.parseInt(day);
                    int today = getTodayDate();
                    daysToAdd = targetDay - today;

                    // Check if the number of days to add is within the valid range
                    if (daysToAdd < 0 || daysToAdd > 31) {
                        throw new IllegalArgumentException("Invalid day to select: " + day + ". Please select a day between 0 and 31");
                    }
                    // Check if the day is within 3 days before or after today
                    if (daysToAdd < -3 || daysToAdd > 3) {
                        throw new IllegalArgumentException("Invalid day to select: " + day + ". You can not select a day 4 days before and after today. Today is: " + getTodayDate());
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid date to select: " + day);
                }
                break;
        }
        // Get the day of the month based on the offset
        int dayOfMonth = getDateWithOffset(daysToAdd);
        // Attempt to click the calendar day and handle exceptions
        try {
            calendarDays.get(dayOfMonth - 1).click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Failed to click the calendar day: " + e.getMessage());
        }
    }

    @FindBy(xpath = "//button[@class='o-search-flight-status__date-navigation__date o-search-flight-status__date-navigation__date--active o-search-flight-status__date-navigation__date--align-center']/div")
    public WebElement selectedDay;

    @FindBy(xpath = "//div[@class='o-search-flight-status__card-airports']/p[1]")
    public List<WebElement> departureResult;

    @FindBy(xpath = "//div[@class='o-search-flight-status__card-airports']/p[2]")
    public List<WebElement> destinationResult;

    @FindBy(xpath = "//h2[@data-component-name='headline']")
    public WebElement noResultMessage;

    @FindBy(xpath = "//div[@class='padded-wrapper']")
    public List<WebElement> flightResultStatusCards;
}
