package ca.mcmaster.se2aa4.island.team44;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team44.drones.Battery;

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
