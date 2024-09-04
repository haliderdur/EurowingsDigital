package ui.eurowings.utilities;


import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class BrowserUtils {

    /**
     * verifyTitle() method accepts a string as an argument and comapres page title with given argument
     *
     * @param string expected page title
     */
    public static void verifyTitle(String expectedTitle) {
        Assert.assertEquals(Driver.getDriver().getTitle(), expectedTitle);
    }

    /**
     * clickWithJS() method accepts a WebElement as an argument and clicks on element using JavaScript
     *
     * @param WebElement
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * verifyElementDisplayed() method accepts a WebElement as an argument and verifies if element is visible or not
     *
     * @param WebElement
     * @throws AssertionError if the element is not found or not displayed
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);
        }
    }

    /**
     * verifyElementDisplayedAndEnabled() method accepts a WebElement as an argument and verifies if element is visible and interactable
     *
     * @param WebElement
     * @throws AssertionError if the element is not found or not enabled
     */
    public static void verifyElementDisplayedAndEnabled(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
            Assert.assertTrue("Element not enabled: " + element, element.isEnabled());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);
        } catch (AssertionError e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }


    /**
     * verifyElementEnabled() method accepts a WebElement as an argument and verifies if element is enabled
     *
     * @param WebElement
     * @throws AssertionError if the element is not found or not enabled
     */
    public static void verifyElementEnabled(WebElement element) {
        try {
            Assert.assertTrue("Element not enabled: " + element, element.isEnabled());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);
        }
    }

    /**
     * verifyElementNotEnabled() method accepts a WebElement as an argument and verifies if element is NOT enabled
     *
     * @param WebElement
     * @throws AssertionError if the element is not found or enabled
     */
    public static void verifyElementNotEnabled(WebElement element) {
        try {
            Assert.assertFalse("Element enabled: " + element, element.isEnabled());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);
        }
    }

    /**
     * scrollToElement() method accepts a WebElement and scrolls to element using JavaScript
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
