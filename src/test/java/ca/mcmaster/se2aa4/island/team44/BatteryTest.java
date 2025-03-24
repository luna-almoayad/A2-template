package ca.mcmaster.se2aa4.island.team44;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


public class BatteryTest {
    private Battery battery;

    @BeforeEach
    public void setUp(){
        battery = new Battery(100);
    }
    
    @Test
    public void testStartBudget(){
        assertEquals(100, battery.getCurrentBudget());
    }

    @Test
    public void testUseBudget(){
        battery.useBudget(20);
        assertEquals(80, battery.getCurrentBudget());
    }

    @Test
    public void testSufficientBatteryTrue() {
        // sufficient battery is 100
        Battery battery = new Battery(120);
        assertTrue(battery.sufficientBattery());
    }

    @Test
    public void testSufficientBatteryFalse() {
        // sufficient battery is 100
        Battery battery = new Battery(80);
        assertFalse(battery.sufficientBattery());
    }


}
