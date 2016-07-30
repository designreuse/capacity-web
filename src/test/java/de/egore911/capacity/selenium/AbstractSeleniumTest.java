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
        driver.close();
    }

    protected final void clickAndCheck(String elementToClick, String textToBePresent) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(elementToClick)));
        driver.findElement(By.id(elementToClick)).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@id='page-wrapper']/*[contains(text(),'" + textToBePresent + "')]"), 1));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='page-wrapper']/*[contains(text(),'" + textToBePresent + "')]"));
        assertThat(elements, not(empty()));
        assertThat(elements, hasSize(1));
    }

}
