package ca.mcmaster.se2aa4.island.team44.explorer;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;
import ca.mcmaster.se2aa4.island.team44.site.Creek;
import ca.mcmaster.se2aa4.island.team44.site.EmergencySite;

// Enum to represent the different phases in the forward exploration
enum forwardPhases {
    SCAN, // Scan phase
    FLY,  // Fly phase
}

public class ExploreForward implements ExplorerPhase {
    // Initial phase is scan
    private forwardPhases phase = forwardPhases.SCAN;
    
    // JSON data adapter for parsing JSON data
    private JSONDataAdapter translator = new JSONDataParser();
    
    // Drone instance
    private Drone d;

    // Constructor to initialize the ExploreForward with a drone
    public ExploreForward(Drone d) {
        phase = forwardPhases.SCAN;
        this.d = d;
    }

    @Override
    public boolean getResponse(JSONObject response) {
        // Deduct cost from battery
        d.deductCost(translator.getCost(response));
    
        if (this.phase == forwardPhases.SCAN) {
            // Check for emergency sites and creeks in the response
            if (translator.getSiteIDs(response) != null) {
                EmergencySite esite = new EmergencySite(translator.getSiteIDs(response), d.getLocation());
                d.addEmergencySite(esite);
            }

            if (translator.getCreekIDs(response) != null) {
                Creek creek = new Creek(translator.getCreekIDs(response), d.getLocation());
                d.addCreek(creek);
            }
                
            // Check if the response indicates an ocean
            if (translator.hasOcean(response)) {
                return true;    
            } else {
                phase = forwardPhases.FLY;
            }
        } else if (this.phase == forwardPhases.FLY) {
            phase = forwardPhases.SCAN;
        }  
        return false;
    } 

    @Override
    public String getDecision() {
        // Stop if battery is low
        if (!d.sufficientBattery()) {
            return d.stop();
        }
        if (phase == forwardPhases.SCAN) {
            return d.scan();
        } else if (phase == forwardPhases.FLY) {
            return d.fly();
        }
        return d.stop();
    }
}