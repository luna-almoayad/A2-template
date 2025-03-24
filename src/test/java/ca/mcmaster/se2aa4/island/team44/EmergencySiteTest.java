package ca.mcmaster.se2aa4.island.team44;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import ca.mcmaster.se2aa4.island.team44.navigation.Location;
import ca.mcmaster.se2aa4.island.team44.site.EmergencySite;

public class EmergencySiteTest {
    private EmergencySite emergencySite;
    private Location location;

    @Before
    public void setUp() {
        location = new Location(10, 10); 
        emergencySite = new EmergencySite("ABCD", location);
    }

    @Test
    public void testGetLocation() {
        assertEquals(location, emergencySite.getLocation());
    }

    @Test
    public void testSetLocation() {
        Location newLocation = new Location(30, 40);
        emergencySite.setLocation(newLocation);
        assertEquals(newLocation, emergencySite.getLocation());
    }

    @Test
    public void testGetID() {
        assertEquals("ABCD", emergencySite.getID());
    }
}

