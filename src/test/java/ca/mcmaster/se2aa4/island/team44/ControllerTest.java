package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.mcmaster.se2aa4.island.team44.control.*;
import ca.mcmaster.se2aa4.island.team44.navigation.Compass;
import ca.mcmaster.se2aa4.island.team44.explorer.*;
import ca.mcmaster.se2aa4.island.team44.site.*;
import ca.mcmaster.se2aa4.island.team44.drones.*;
import ca.mcmaster.se2aa4.island.team44.navigation.Location;


class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller(200, Compass.N);
    }

    @Test
    void testInitialDecision() {
        String decision = controller.getDecision();
        //ensure decision exists 
        assertNotNull(decision);
        assertTrue(
            decision.contains("scan") || 
            decision.contains("echo") || 
            decision.contains("fly") || 
            decision.contains("stop") 
        );
    }

    @Test
    void testPhaseSwitchingWithMockedPhase() {
        //mock phase an drone trigger switch 
        ExplorerPhase mockPhase = mock(ExplorerPhase.class);
        Drone mockDrone = new Drone(200, Compass.E);

        Controller testController = new Controller(200, Compass.E);
        testController.d = mockDrone; 
        testController.actionPhase = mockPhase;

        JSONObject mockResponse = new JSONObject().put("cost", 5);
        when(mockPhase.getResponse(mockResponse)).thenReturn(true); 

        Phases oldPhase = testController.dronePhase;
        testController.getResponse(mockResponse);
        Phases newPhase = testController.dronePhase;

        //old and new phase must not be the same & new phase should provide valid decision
        assertNotEquals(oldPhase, newPhase);
        assertNotNull(testController.getDecision());
    }

 
    @Test
    void testFinishMissionNoneFound() {
        JSONObject response = new JSONObject()
        .put("cost", 5)
        .put("status", "OK")
        .put("heading", "N")
        .put("extras", new JSONObject()
            .put("found", "GROUND") 
            .put("range", 3));
        controller.getResponse(response);

        String report = controller.finishMission();
        assertTrue(report.contains("Creek and Emergency Site were not found"));
    }

    @Test
    void testFinishMissionValid() {
        Drone customDrone = new Drone(200, Compass.N);
        EmergencySite site = new EmergencySite("site", new Location(0, 0));
        Creek creek = new Creek("creek", new Location(1, 1));
        customDrone.addEmergencySite(site);
        customDrone.addCreek(creek);

        Controller testController = new Controller(200, Compass.N);
        testController.d = customDrone;
        testController.report = new MissionReport(customDrone);

        String report = testController.finishMission();
        assertTrue(report.contains("Creek ID:creek"));
        assertTrue(report.contains("Emergency Site ID:site"));
    }
}
