package ca.mcmaster.se2aa4.island.team44;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class DroneTest {
    
    private Drone drone;

    @BeforeEach
    void setup() {
        drone = new Drone(150, Compass.N);
    }

    @Test
    void testInitialLocation() {
        Location location = drone.getLocation();
        assertEquals(0, location.getXCoord());
        assertEquals(0, location.getYCoord());
    }

    @Test
    void testInitialDirection() {
        assertEquals(Compass.N, drone.getDirection());
    }

    @Test
    void testFlyMovesNorth() {
        drone.fly();
        Location newLocation = drone.getLocation();
        assertEquals(0, newLocation.getXCoord());
        assertEquals(1, newLocation.getYCoord());
    }

    @Test
    void testRight() {
        drone.right(); 
        assertEquals(Compass.E, drone.getDirection());
    }

    @Test
    void testLeft() {
        drone.left();  
        assertEquals(Compass.W, drone.getDirection());
    }

    @Test
    void testSetDirection() {
        drone.setDirection(Compass.S);
        assertEquals(Compass.S, drone.getDirection());
    }

    @Test
    void testBatteryDeductCost() {
        int initialBattery = drone.checkBattery();
        drone.deductCost(20);
        assertEquals(initialBattery - 20, drone.checkBattery());
    }

    @Test
    void testSufficientBatteryTrue() {
        assertTrue(drone.sufficientBattery());
    }

    @Test
    void testSufficientBatteryFalse() {
        drone.deductCost(1000);
        assertFalse(drone.sufficientBattery());
    }

    @Test
    void testAddAndGetEmergency() {
        EmergencySite site = new EmergencySite("ABCD", new Location(5, 5));
        drone.addEmergencySite(site);
        assertEquals(site, drone.getESite());
    }

    @Test
    void testAddAndRetrieveCreek() {
        Creeks creek = new Creeks("ABCD", new Location(2, 2));
        drone.addCreek(creek);
        assertTrue(drone.getCreek().contains(creek));
    }

    @Test
    void testIfPossiblyFound() {
        EmergencySite site = new EmergencySite("ABCD", new Location(1, 1));
        Creeks creek = new Creeks("EFG", new Location(2, 2));
        drone.addEmergencySite(site);
        drone.addCreek(creek);

        assertTrue(drone.ifPossiblyFound());
    }

    @Test
    void testToStringContainsLocationAndDirection() {
        String output = drone.toString();
        assertTrue(output.contains("Location"));
        assertTrue(output.contains("Direction"));
    }
}