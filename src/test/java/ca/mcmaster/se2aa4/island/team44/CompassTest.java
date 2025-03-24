package ca.mcmaster.se2aa4.island.team44;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.se2aa4.island.team44.navigation.Compass;

public class CompassTest {
    @Test
    public void rightTest() {
        assertEquals(Compass.E, Compass.N.right());
        assertEquals(Compass.S, Compass.E.right());
        assertEquals(Compass.W, Compass.S.right());
        assertEquals(Compass.N, Compass.W.right());
    }

    @Test 
    public void leftTest() {
        assertEquals(Compass.W, Compass.N.left());
        assertEquals(Compass.N, Compass.E.left());
        assertEquals(Compass.E, Compass.S.left());
        assertEquals(Compass.S, Compass.W.left());


}
}
