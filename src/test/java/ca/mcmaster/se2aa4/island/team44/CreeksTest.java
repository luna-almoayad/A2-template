package ca.mcmaster.se2aa4.island.team44;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ca.mcmaster.se2aa4.island.team44.site.Creek;
import ca.mcmaster.se2aa4.island.team44.navigation.Location;


public class CreeksTest {
    private Creek creek;
    private Location location; 

    @Before 
    public void setUp(){
        location = new Location(10,10);
        creek = new Creek("ABCD", location);
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
