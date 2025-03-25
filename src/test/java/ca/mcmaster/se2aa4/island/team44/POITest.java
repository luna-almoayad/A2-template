package ca.mcmaster.se2aa4.island.team44;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team44.navigation.Location;
import ca.mcmaster.se2aa4.island.team44.site.Creek;
import ca.mcmaster.se2aa4.island.team44.site.EmergencySite;
import ca.mcmaster.se2aa4.island.team44.site.POI;

public class POITest {
    
  
    private POI poi;
    private Location esiteLocation;
    private EmergencySite esite;
    private Creek creek1;
    private Creek creek2;

    @BeforeEach
    public void setUp() {
        // Initialize objects that are common for all tests
        poi = new POI();
        esiteLocation = new Location(1, 1);  // Emergency site location
        esite = new EmergencySite("E1", esiteLocation);  // Emergency site
        poi.addEmergencySite(esite);  // Adding emergency site to POI

        // Create creeks with different locations
        Location creekLocation1 = new Location(1, 1);
        Location creekLocation2 = new Location(4, 4);
        creek1 = new Creek("C1", creekLocation1);
        creek2 = new Creek("C2", creekLocation2);

        // Add creeks to POI
        poi.addCreek(creek1);
        poi.addCreek(creek2);
    }

    @Test
    public void testAddCreek() {
        assertEquals(2, poi.getCreeks().size());
        assertEquals(creek1, poi.getClosestCreek());
    }

    @Test
    public void testAddEmergencySite() {
        poi.addEmergencySite(esite);  // Adding emergency site to POI
        assertEquals(esite, poi.getEmergencySites());
    }

    @Test
    public void testFindClosestCreek() {
        // We have already set up creeks and emergency site
        Creek closestCreek = poi.getClosestCreek();
        assertEquals(creek1, closestCreek);
    }

    @Test
    public void testGetClosestCreek() {
        Creek closestCreek = poi.getClosestCreek();
        assertEquals(creek1, closestCreek);
    }

    @Test
    public void testGetCreek() {
        // We have already set up creeks
        List<Creek> creeks = poi.getCreeks();
        assertEquals(2, creeks.size());
        assertTrue(creeks.contains(creek1));
        assertTrue(creeks.contains(creek2));
    }

    @Test
    public void testGetEmergencySites() {
        // Emergency site should already be added
        assertEquals(esite, poi.getEmergencySites());
    }
}