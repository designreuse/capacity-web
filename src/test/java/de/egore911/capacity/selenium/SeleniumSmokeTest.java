package de.egore911.capacity.selenium;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("Requires UI access not available on build server")
public class SeleniumSmokeTest extends AbstractSeleniumTest {

    @Test
    public void clickHome() {
        clickAndCheck("menu_home", "Welcome");
    }

    @Test
    public void clickEmployees() {
        clickAndCheck("menu_employees", "Employees");
    }

    @Test
    public void clickEpisodes() {
        clickAndCheck("menu_episodes", "Episodes");
    }

    @Test
    public void clickCalendar() {
        clickAndCheck("menu_calendar", "Calendar");
    }

    @Test
    public void clickIcalImports() {
        clickAndCheck("menu_ical_imports", "iCal Imports");
    }

    @Test
    public void clickCapacity() {
        clickAndCheck("menu_capacity", "Capacity");
    }

    @Test
    public void clickAbsence() {
        clickAndCheck("menu_absence", "Absences");
    }

    @Test
    public void clickRoles() {
        clickAndCheck("menu_roles", "Roles");
    }
}

