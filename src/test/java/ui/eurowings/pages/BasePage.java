package ui.eurowings.pages;

import ui.eurowings.utilities.Driver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
}
