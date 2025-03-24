package ca.mcmaster.se2aa4.island.team44;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MissionReportTest {

    private Drone drone;
    private MissionReport report;

    @BeforeEach
    void setUp() {
        //create mock object of drone class 
        drone = mock(Drone.class);
        report = new MissionReport(drone);
    }

    @Test
    void testGenerateReport() {
        EmergencySite site = new EmergencySite("site", new Location(3, 3));
        Creeks creek = new Creeks("creek", new Location(4, 4));

        when(drone.sufficientBattery()).thenReturn(true);
        when(drone.ifPossiblyFound()).thenReturn(true);
        when(drone.getClosestCreek()).thenReturn(creek);
        when(drone.getESite()).thenReturn(site);

        String output = report.generateReport();
        assertTrue(output.contains("Creek ID:creek"));
        assertTrue(output.contains("Emergency Site ID:site"));
    }

    @Test
    void testGenerateReportLowBattery() {

        when(drone.sufficientBattery()).thenReturn(false);
        String output = report.generateReport();
        assertEquals("Insufficent Battery: Return to Base", output);
    }

    @Test
    void testGenerateReportNoneFound() {
        //battery present but no sites located 
        when(drone.sufficientBattery()).thenReturn(true);
        when(drone.ifPossiblyFound()).thenReturn(false);

        String output = report.generateReport();
        assertEquals("Creek and Emergency Site were not found: Return to Base", output);
    }
}
