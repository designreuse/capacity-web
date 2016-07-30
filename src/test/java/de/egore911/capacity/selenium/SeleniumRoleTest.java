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
import org.openqa.selenium.support.ui.ExpectedConditions;

@Ignore("Requires UI access not available on build server")
public class SeleniumRoleTest extends AbstractSeleniumTest {

    private void assertTwoRolesExist() {
        // given: a clickable element
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@id='page-wrapper']/h2[contains(text(),'Listing (2)')]"), 1));
    }

    private void assertRolesVisibleInOrder(String... roleNames) {
        List<WebElement> roles = driver.findElements(By.xpath("//div[@id='page-wrapper']/table/tbody/tr/td/a"));
        assertThat(roles, hasSize(roleNames.length));
        for (int i = 0; i < roleNames.length; i++) {
            assertThat(roles.get(i).getText(), equalTo(roleNames[i]));
        }
    }

    @Test
    public void listRoles() {
        // when: we navigate to the roles administration
        clickNavigationAndCheck("menu_roles", "Roles");

        // then: the site tells us two roles exist
        assertTwoRolesExist();

        // then: We find the roles in the alphabetical order
        assertRolesVisibleInOrder("Administrators", "Users");
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

        // then: the site tells us two roles exist
        assertTwoRolesExist();

        // then: we only get the role 'Administrators' listed
        assertRolesVisibleInOrder("Administrators");

        // when: we clear the search field
        siteSearchElement.clear();

        // then: the site tells us two roles exist
        assertTwoRolesExist();

        // then: We find the roles in the alphabetical order
        assertRolesVisibleInOrder("Administrators", "Users");
    }

    @Test
    public void sortRoles() {
        // when: we navigate to the roles administration
        clickNavigationAndCheck("menu_roles", "Roles");

        WebElement sortNameElement = driver.findElement(By.id("sort_name"));
        WebElement sortedByName = sortNameElement.findElement(By.className("sortorder"));
        WebElement sortIdElement = driver.findElement(By.id("sort_id"));
        WebElement sortedById = sortIdElement.findElement(By.className("sortorder"));

        // then: we are sorted by name by default
        assertThat(sortedByName.isDisplayed(), equalTo(true));
        assertThat(sortedById.isDisplayed(), equalTo(false));

        // then: We find the roles in the alphabetical order
        assertRolesVisibleInOrder("Administrators", "Users");

        // when: we click sort by name
        sortNameElement.click();

        // then: we are still sorted by name
        assertThat(sortedByName.isDisplayed(), equalTo(true));
        assertThat(sortedById.isDisplayed(), equalTo(false));

        // then: We find the roles in the alphabetical order
        assertRolesVisibleInOrder("Users", "Administrators");

        // when: we click sort by name
        sortNameElement.click();

        // then: we are still sorted by name
        assertThat(sortedByName.isDisplayed(), equalTo(true));
        assertThat(sortedById.isDisplayed(), equalTo(false));

        // then: We find the roles in the alphabetical order
        assertRolesVisibleInOrder("Administrators", "Users");
    }

}
