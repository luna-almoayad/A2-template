package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public interface ExplorerPhase{


    public String getDecision(Drone d);

    public boolean getResponse(JSONObject response, Drone d); 

}