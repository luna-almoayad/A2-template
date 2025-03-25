package ca.mcmaster.se2aa4.island.team44.explorer;

import org.json.JSONObject;

// Interface to define the phases of the explorer
public interface ExplorerPhase {

    // Method to get the decision for the next action
    String getDecision();
    
    // Method to process the response from the drone's action
    boolean getResponse(JSONObject response); 

}