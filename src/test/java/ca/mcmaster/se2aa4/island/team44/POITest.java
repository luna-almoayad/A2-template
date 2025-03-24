package ca.mcmaster.se2aa4.island.team44;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
public class POITest {
    
  
    private POI poi;
    private Location esiteLocation;
    private EmergencySite esite;
    private Creeks creek1;
    private Creeks creek2;

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
        creek1 = new Creeks("C1", creekLocation1);
        creek2 = new Creeks("C2", creekLocation2);

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
        Creeks closestCreek = poi.findClosestCreek();
        assertEquals(creek1, closestCreek);
    }

    @Test
    public void testGetClosestCreek() {
        Creeks closestCreek = poi.getClosestCreek();
        assertEquals(creek1, closestCreek);
    }

    @Test
    public void testGetCreeks() {
        // We have already set up creeks
        List<Creeks> creeks = poi.getCreeks();
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