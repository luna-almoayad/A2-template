package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public interface ExplorerPhase{

    String getDecision();

    boolean getResponse(JSONObject response); 

}