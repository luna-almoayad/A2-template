package ca.mcmaster.se2aa4.island.team44;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CreeksTest {
    private Creeks creek;
    private Location location; 

    @Before 
    public void setUp(){
        location = new Location(10,10);
        creek = new Creeks("ABCD", location);
    }

    @Test 
    public void testGetLocation(){
        assertEquals(location, creek.getLocation());
    }

    @Test
    public void testGetID(){
        assertEquals("ABCD", creek.getID());
    }
    
}
