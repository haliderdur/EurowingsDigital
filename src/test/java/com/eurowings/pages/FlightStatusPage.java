package com.eurowings.pages;


import com.eurowings.utilities.BrowserUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FlightStatusPage extends BasePage {

    @FindBy(xpath = "//span[@class='a-cta__text' and .='I understand']")
    private WebElement privacySettingsAgreeButton;

    @FindBy(xpath = "(//input[contains(@id,'radio-button') and @type='radio'])[1]")
    public WebElement flightRouteRadioButton;

    @FindBy(xpath = "(//input[contains(@id,'radio-button') and @type='radio'])[2]")
    public WebElement flightNumberRadioButton;

    @FindBy(xpath = "//input[contains(@id,'radio-button') and @type='radio']")
    public List<WebElement> flightRouteAndNumberRadioButtons;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button'])[1]/span")
    public WebElement departureAirportDropdown;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button'])[2]/span")
    public WebElement destinationAirportDropdown;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button']//span[@class='o-compact-search__cta-button-value'])[1]")
    public WebElement departureAirportDropdownBox;

    @FindBy(xpath = "(//button[@class='o-compact-search__cta-button-button']//span[@class='o-compact-search__cta-button-value'])[2]")
    public WebElement destinationAirportDropdownBox;

    @FindBy(xpath = "//input[contains(@id,'text-') and contains(@name,'datepicker_input_')]")
    public WebElement departureDatePicker;

    @FindBy(xpath = "//button[@type='submit' and @class='a-cta a-cta-prio1']")
    public WebElement showFlightStatusButton;

    @FindBy(xpath = "//div[@class='o-station-select__flyout-content o-station-select__flyout-content--new-design']//ul/li")
    public List<WebElement> allAirports;

    @FindBy(xpath = "//input[contains(@id,'text-') and @name='flightNumber']")
    public WebElement flightNumberInputBox;

    @FindBy(xpath = "//div[contains(@class, 'calendar-date') and not(contains(@class, 'calendar-date--unavailable'))]")
    public List<WebElement> availableDays;

    @FindBy(xpath = "//button[@class='o-search-flight-status__date-navigation__date o-search-flight-status__date-navigation__date--active o-search-flight-status__date-navigation__date--align-center']/div")
    public WebElement selectedDay;

    @FindBy(xpath = "//h2[@data-component-name='headline']")
    public WebElement noResultMessage;

    @FindBy(xpath = "//div[@class='padded-wrapper']")
    public List<WebElement> flightResultStatusCards;

    public void acceptPrivacySettings() {
        if (privacySettingsAgreeButton.isDisplayed()) {
            privacySettingsAgreeButton.click();
        }
    }

    public void selectAirport(String airportName) {
        for (WebElement eachAirport : allAirports) {
            if (eachAirport.getText().contains(airportName)) {
                eachAirport.click();
                break;
            }
        }
    }

    // clicks flight route or flight number radio buttons
    public void clickRadioButton(List<WebElement> radioButtons, String attributeValue) {
        int index = -1;
        switch (attributeValue.toUpperCase()) {
            case "FLIGHT_ROUTE":
                index = 0;
                break;
            case "FLIGHT_NUMBER":
                index = 1;
                break;
        }
        if (index != -1 && !radioButtons.get(index).isSelected()) {
            BrowserUtils.clickWithJS(radioButtons.get(index));
        }
    }

    public void verifySelectedDay(String day) {
        int actualDay = Integer.parseInt(selectedDay.getText().split(" ")[1].split("/")[0]);
        Assert.assertEquals("Expected day: " + Integer.parseInt(day) + " but found: " + actualDay, Integer.parseInt(day), actualDay);
    }

    /**
     * Clicks randomly to available days
     * Verifies that all available days enabled
     */
    public void selectAvailableDaysAndVerify() {
        List<WebElement> shuffledDays = new ArrayList<>(availableDays);
        Collections.shuffle(shuffledDays, new Random());
        for (WebElement eachRandomAvailableDay : shuffledDays) {
            eachRandomAvailableDay.click();
            BrowserUtils.verifyElementEnabled(eachRandomAvailableDay);
            departureDatePicker.click();
        }
    }

    // Method to click on given date
    public void selectDate(String day) {

        for (WebElement eachAvailableDayElement : availableDays) {
            String dayText = eachAvailableDayElement.getText().trim();

            if (!dayText.isEmpty() && dayText.equals(day)) {
                eachAvailableDayElement.click();
                System.out.println("Selected day: " + dayText);
                break;

            }
        }
    }
}