package de.egore911.capacity.selenium;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractSeleniumTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeClass
    public static void before() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);

        // Open the page logged in as admin
        driver.get("http://admin:admin@localhost:8080/capacity/#/");

        // Firefox 47.0.1 warns us that the server didn't ask for credentials
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    @AfterClass
    public static void after() {
        driver.quit();
    }

    protected final void clickNavigationAndCheck(String elementToClick, String textToBePresent) {
        // given: a clickable element
        wait.until(ExpectedConditions.elementToBeClickable(By.id(elementToClick)));

        // when: we click the element
        driver.findElement(By.id(elementToClick)).click();

        // then: the router navigates us to our target and we find the text expexted
        By xpath = By.xpath("//div[@id='page-wrapper']/*[contains(text(),'" + textToBePresent + "')]");
        wait.until(ExpectedConditions.numberOfElementsToBe(xpath, 1));
        List<WebElement> elements = driver.findElements(xpath);
        assertThat(elements, not(empty()));
        assertThat(elements, hasSize(1));
    }

}
