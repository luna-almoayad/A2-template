package ca.mcmaster.se2aa4.island.team44;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class DroneTest {
    private Drone drone; 

    @BeforeEach
    public void setUp(){
        drone = new Drone(100); //fake battery val
    }
    
    @Test 
    public void testInitial(){
        assertEquals(new Location(0,0), drone.getLocation());
        assertEquals(100, drone.checkBattery());
        assertEquals(Compass.E,drone.getDirection());
    }

    @Test 
    public void testFly(){
        Location start = drone.getLocation();
        drone.fly();
        assertNotEquals(start, drone.getLocation());
    }

    @Test 
    public void testChangeCost(){
        drone.changeCost(20);
        assertEquals(80, drone.checkBattery());
    }

    @Test
    public void testRight(){
        drone.right();
        assertEquals(Compass.S, drone.getDirection());
    }

    @Test
    public void testLeft(){
        drone.left();
        drone.left();
        drone.left();
        assertEquals(Compass.S, drone.getDirection());
    }


}
