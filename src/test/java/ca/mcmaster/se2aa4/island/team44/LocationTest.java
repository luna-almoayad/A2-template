package ca.mcmaster.se2aa4.island.team44;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LocationTest {
    @Test
    public void testLocationConstructor() {
        Location location = new Location(3, 4);
        assertEquals(3, location.getXCoord());
        assertEquals(4, location.getYCoord());
    }

    @Test
    public void testMakeMoveNorth() {
        Location location = new Location(3, 4);
        Location newLocation = location.makeMove(Compass.N);
        assertEquals(3, newLocation.getXCoord());
        assertEquals(5, newLocation.getYCoord());
    }

    @Test
    public void testMakeMoveSouth() {
        Location location = new Location(3, 4);
        Location newLocation = location.makeMove(Compass.S);
        assertEquals(3, newLocation.getXCoord());
        assertEquals(3, newLocation.getYCoord());
    }

    @Test
    public void testMakeMoveWest() {
        Location location = new Location(3, 4);
        Location newLocation = location.makeMove(Compass.W);
        assertEquals(2, newLocation.getXCoord());
        assertEquals(4, newLocation.getYCoord());
    }

    @Test
    public void testMakeMoveEast() {
        Location location = new Location(3, 4);
        Location newLocation = location.makeMove(Compass.E);
        assertEquals(4, newLocation.getXCoord());
        assertEquals(4, newLocation.getYCoord());
    }

    @Test
    public void testCalculateDistance() {
        Location location1 = new Location(1, 2);
        Location location2 = new Location(4, 6);
        assertEquals(5, location1.calculateDistance(location2));
    }

    @Test
    public void testGetYDist() {
        Location location1 = new Location(3, 4);
        Location location2 = new Location(3, 8);
        assertEquals(4, location1.getYDist(location2));
    }

    @Test
    public void testGetXDist() {
        Location location1 = new Location(3, 4);
        Location location2 = new Location(7, 4);
        assertEquals(4, location1.getXDist(location2));
    }


    @Test
    public void testToString() {
        Location location = new Location(3, 4);
        assertEquals("(3,4)", location.toString());
    }
}

