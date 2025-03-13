package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public interface ExplorerPhase{


    public JSONObject getDecision(Drone d);

    public void readDecision(JSONObject response, Drone d); 

}