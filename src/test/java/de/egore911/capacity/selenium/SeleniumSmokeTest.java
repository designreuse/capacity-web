package de.egore911.capacity.selenium;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("Requires UI access not available on build server")
public class SeleniumSmokeTest extends AbstractSeleniumTest {

    @Test
    public void clickHome() {
        clickNavigationAndCheck("menu_home", "Welcome");
    }

    @Test
    public void clickEmployees() {
        clickNavigationAndCheck("menu_employees", "Employees");
    }

    @Test
    public void clickEpisodes() {
        clickNavigationAndCheck("menu_episodes", "Episodes");
    }

    @Test
    public void clickCalendar() {
        clickNavigationAndCheck("menu_calendar", "Calendar");
    }

    @Test
    public void clickIcalImports() {
        clickNavigationAndCheck("menu_ical_imports", "iCal Imports");
    }

    @Test
    public void clickCapacity() {
        clickNavigationAndCheck("menu_capacity", "Capacity");
    }

    @Test
    public void clickAbsence() {
        clickNavigationAndCheck("menu_absence", "Absences");
    }

    @Test
    public void clickRoles() {
        clickNavigationAndCheck("menu_roles", "Roles");
    }

    @Test
    public void clickUsers() {
        clickNavigationAndCheck("menu_users", "Users");
    }

}

