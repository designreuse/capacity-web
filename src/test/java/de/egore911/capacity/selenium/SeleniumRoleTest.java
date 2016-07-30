package de.egore911.capacity.selenium;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Ignore("Requires UI access not available on build server")
public class SeleniumRoleTest extends AbstractSeleniumTest {

    @Test
    public void listRoles() {
        // when: we navigate to the roles administration
        clickNavigationAndCheck("menu_roles", "Roles");

        // then: the site tells us two roles exist
        List<WebElement> subheadline = driver.findElements(By.xpath("//div[@id='page-wrapper']/h2[contains(text(),'Listing (2)')]"));
        assertThat(subheadline, hasSize(1));

        // then: We find the roles in the alphabetical order
        List<WebElement> roles = driver.findElements(By.xpath("//div[@id='page-wrapper']/table/tbody/tr/td/a"));
        assertThat(roles, hasSize(2));
        assertThat(roles.get(0).getText(), equalTo("Administrators"));
        assertThat(roles.get(1).getText(), equalTo("Users"));
    }

    @Test
    public void filterRoles() {
        // when: we navigate to the roles administration
        clickNavigationAndCheck("menu_roles", "Roles");

        // then: the site tells us two roles where found
        WebElement siteSearchElement = driver.findElement(By.id("site_search"));
        assertThat(siteSearchElement, notNullValue());

        // when: we search for 'Admin'
        siteSearchElement.sendKeys("Admin");

        // then: the site still tells us that two roles exist
        List<WebElement> subheadline = driver.findElements(By.xpath("//div[@id='page-wrapper']/h2[contains(text(),'Listing (2)')]"));
        assertThat(subheadline, hasSize(1));

        // then: we only get the role 'Administrators' listed
        List<WebElement> roles = driver.findElements(By.xpath("//div[@id='page-wrapper']/table/tbody/tr/td/a"));
        assertThat(roles, hasSize(1));
        assertThat(roles.get(0).getText(), equalTo("Administrators"));

        // when: we clear the search field
        siteSearchElement.clear();

        // then: the site tells us two roles exist
        subheadline = driver.findElements(By.xpath("//div[@id='page-wrapper']/h2[contains(text(),'Listing (2)')]"));
        assertThat(subheadline, hasSize(1));

        // then: We find the roles in the alphabetical order
        roles = driver.findElements(By.xpath("//div[@id='page-wrapper']/table/tbody/tr/td/a"));
        assertThat(roles, hasSize(2));
        assertThat(roles.get(0).getText(), equalTo("Administrators"));
        assertThat(roles.get(1).getText(), equalTo("Users"));
    }

}
